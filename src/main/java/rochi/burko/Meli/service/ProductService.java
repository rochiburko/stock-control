package rochi.burko.Meli.service;

//Conexion con api externa

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rochi.burko.Meli.api.MeliItemApi;
import rochi.burko.Meli.dto.ItemsDTO;

@Service
public class ProductService {

    @Autowired
    private MeliItemApi productAPI;

    public boolean checkIsStored(String idProducto){
        ItemsDTO item = productAPI.getItem(idProducto);
        try {
            boolean isStored = item.getShipping().getLogistic_type().equals("fulfillment");
            return isStored;
        } catch (Exception e) {
            throw new RuntimeException("The requested product can not be stored");
        }
    }

    public String getLogisticType(String idProducto){
        ItemsDTO item = productAPI.getItem(idProducto);
        return item.getShipping().getLogistic_type();
    }
}
