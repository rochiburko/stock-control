package rochi.burko.Meli.requests;

import javax.validation.constraints.*;

public class StockOperationRequest {

    @NotNull
    @NotBlank
    @Pattern(regexp = "[A-Z]{2}[0-9]{2}")
    private String warehouseCode;

    @NotNull
    @NotBlank
    @Pattern(regexp = "[A-Z]{2}-[0-9]{2}-[0-9]{2}-(IZ|DE)")
    private String locationCode;

    @NotNull
    @Min(0)
    @Max(100)
    private int quantity;

    @NotNull
    @NotBlank
    private String productId;

    public StockOperationRequest() {
    }

    public StockOperationRequest(String warehouseCode, String locationCode, int quantity, String productId) {
        this.warehouseCode = warehouseCode;
        this.locationCode = locationCode;
        this.quantity = quantity;
        this.productId = productId;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
