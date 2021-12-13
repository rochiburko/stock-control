package rochi.burko.Meli.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rochi.burko.Meli.dto.SearchStockDTO;
import rochi.burko.Meli.dto.StockDTO;
import rochi.burko.Meli.model.Location;
import rochi.burko.Meli.model.Stock;
import rochi.burko.Meli.model.Warehouse;
import rochi.burko.Meli.repository.LocationsRepository;
import rochi.burko.Meli.repository.StockRepository;
import rochi.burko.Meli.repository.WarehouseRepository;
import rochi.burko.Meli.requests.StockOperationRequest;
import rochi.burko.Meli.utils.IsoUtil;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private ProductService productService;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private LocationsRepository locationsRepository;

    @Autowired
    private StockRepository stockRepository;


    /**
     * Valida la capacidad
     * @param quantity
     * @param locationId
     * @return
     */
    private boolean isValidQuantity(int quantity, String locationId){

        List<Stock> query = stockRepository.findByLocation(locationId);

        // La suma de las cantidades no puede ser mayor a 100 unidades.
        int totalQuantity = 0;
        for (Stock stock:query)
        {
            totalQuantity += stock.getQuantity();
        }

        // No se pueden colocar más de 3 productos distintos en una ubicación.
        List<String> productIdList = new ArrayList<>();
        for (Stock stock:query)
        {
            if (productIdList.isEmpty() || !productIdList.contains(stock.getProduct())) {
                productIdList.add(stock.getProduct());
            } else {
                    // Do nothing
            }
        }

        boolean isValid = totalQuantity + quantity <= 100 && productIdList.size()<3;

        return isValid;
    }

    /**
     * Devuelve la cantidad en stock para la ubicacion indicada
     * @param location
     * @return
     */
    private int getLocationStockQuantity(Location location){
        int totalQuantity = 0;

        for (Stock stock: location.getStock())
        {
            totalQuantity += stock.getQuantity();
        }
        return totalQuantity;
    }

    /**
     * Consulta a la API y valida si el producto puede estar o no almacenado en los depositos
     * @param productId
     * @return
     */
    private boolean isValidProduct(String productId){
        return productService.checkIsStored(productId);
    }

    private Location getLocationFromWarehouse(Warehouse warehouse, String locationCode){
        Location toBeStored = null;

        for(Location location: warehouse.getLocations()){
            if(location.toString().equals(locationCode)){
                toBeStored = location;
                break;
            }
        }

        return toBeStored;
    }

    /**
     * Devuelve cantidad en stock del producto para la ubicacion indicada
     * @param location
     * @param productId
     * @return
     */
    private Stock getStockFromLocation(Location location, String productId){
        Stock existingStock = null;
        for (Stock stock: location.getStock()) {
            if (stock.getProduct().equals(productId)){
                existingStock = stock;
                break;
            }
        }
        return existingStock;
    }

    /**
     * Agrega el o los productos a la ubicacion indicada
     * @param request
     */
    public void addProduct(StockOperationRequest request){

        String ISO = request.getWarehouseCode().substring(0, 2);
        int number = Integer.valueOf(request.getWarehouseCode().substring(2));

        if(!IsoUtil.isValidISOCountry(ISO))
            throw new RuntimeException(
                    String.format("The warehouse code %s inserted is invalid", request.getWarehouseCode())
            );

        Warehouse warehouse = warehouseRepository.findOneByISOAndNumber(ISO,number);
        if(warehouse == null)
            throw new RuntimeException("The requested warehouse does not exists");

        Location location = getLocationFromWarehouse(warehouse, request.getLocationCode());
        if(location == null)
            throw new RuntimeException("The requested warehouse does not exists");

        if (isValidProduct(request.getProductId())){
            Stock stock = getStockFromLocation(location, request.getProductId());

            //No se pueden colocar más de 3 productos distintos en una ubicación.
            if(stock == null && location.getStock().size() == 3){
                throw new RuntimeException("Location already has reach max product stock limit (3 products)");
            }

            int stockedQuantity = getLocationStockQuantity(location);
            int toBeStoredQuantity = stockedQuantity + request.getQuantity();
            if(toBeStoredQuantity > 100){
                throw new RuntimeException("The maximum stocked quantity will be exceeded.");
            }

            if(stock == null){
                stock = new Stock(
                        request.getProductId(),
                        request.getQuantity(),
                        location
                );
            } else {
                int newQuantity = stock.getQuantity() + request.getQuantity();
                stock.setQuantity(newQuantity);
            }

            stockRepository.save(stock);

        } else {
            throw new RuntimeException("The requested product can not be stored");
        }
    }

    /**
     * Elimina el o los porductos de la ubicacion indicada.
     * @param request
     */
    public void removeProduct(StockOperationRequest request){

        String ISO = request.getWarehouseCode().substring(0, 2);
        int number = Integer.valueOf(request.getWarehouseCode().substring(2));

        if(!IsoUtil.isValidISOCountry(ISO))
            throw new RuntimeException(
                    String.format("The warehouse code %s inserted is invalid", request.getWarehouseCode())
            );

        Warehouse warehouse = warehouseRepository.findOneByISOAndNumber(ISO,number);
        if(warehouse == null)
            throw new RuntimeException("The requested warehouse does not exists");

        Location location = getLocationFromWarehouse(warehouse, request.getLocationCode());
        if(location == null)
            throw new RuntimeException("The requested warehouse does not exists");

        if (isValidProduct(request.getProductId())){
            Stock stock = getStockFromLocation(location, request.getProductId());

            //Si el producto no se encuentra en la ubicacion indicada
            if(stock == null){
                throw new RuntimeException("The requested product does not exist in the specified location");
            }

            //Si el producto se encuentra en la ubicacion indicada verifico la cantidad en stock
            int stockedQuantity = getLocationStockQuantity(location);
            int leftQuantity = stockedQuantity - request.getQuantity();
            if(leftQuantity < 0){
                throw new RuntimeException("The specified quantity exceeds the current stock.");
            }

            if(leftQuantity == 0){
                stockRepository.deleteById(stock.getId());
            } else {
                int newQuantity = stock.getQuantity() - request.getQuantity();
                stock.setQuantity(newQuantity);
                stockRepository.save(stock);
            }

        } else {
            throw new RuntimeException("The requested product can not be stored");
        }
    }

    /**
     * Lista los productos y cantidad que hay en la ubicacion indicada.
     * @param warehouseCode
     * @param locationCode
     * @return
     */
    public List<StockDTO> listProduct(String warehouseCode, String locationCode) {

        String ISO = warehouseCode.substring(0, 2);
        int number = Integer.valueOf(warehouseCode.substring(2));

        if (!IsoUtil.isValidISOCountry(ISO))
            throw new RuntimeException(
                    String.format("The warehouse code %s inserted is invalid", warehouseCode)
            );

        Warehouse warehouse = warehouseRepository.findOneByISOAndNumber(ISO, number);
        if (warehouse == null)
            throw new RuntimeException("The requested warehouse does not exists");

        Location location = getLocationFromWarehouse(warehouse, locationCode);
        if (location == null)
            throw new RuntimeException("The requested warehouse does not exists");

        List<StockDTO> results = new ArrayList<>();
        for (Stock stock : location.getStock()) {
            StockDTO productStock = new StockDTO(stock.getProduct(), stock.getQuantity());
            results.add(productStock);
        }

        return results;
    }

    /**
     * Devuelve las posibles ubicaciones y cantidad del producto ingresado
     * @param warehouseCode
     * @param productId
     * @return
     */
    public List<SearchStockDTO> searchProduct(String warehouseCode, String productId) {

        String ISO = warehouseCode.substring(0, 2);
        int number = Integer.valueOf(warehouseCode.substring(2));

        if (!IsoUtil.isValidISOCountry(ISO))
            throw new RuntimeException(
                    String.format("The warehouse code %s inserted is invalid", warehouseCode)
            );

        Warehouse warehouse = warehouseRepository.findOneByISOAndNumber(ISO, number);
        if (warehouse == null)
            throw new RuntimeException("The requested warehouse does not exists");

        List<SearchStockDTO> results = new ArrayList<>();
        for (Location location: warehouse.getLocations()) {
            for (Stock stock: location.getStock()) {
                if (stock.getProduct().equals(productId)){
                    results.add(
                            new SearchStockDTO(location.toString(),stock.getQuantity())
                    );
                }
            }
        }

        return results;
    }

}
