package rochi.burko.Meli.dto;

public class SearchStockDTO {

    private String locationCode;
    private int quantity;

    public SearchStockDTO() {
    }

    public SearchStockDTO(String locationCode, int quantity) {
        this.locationCode = locationCode;
        this.quantity = quantity;
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
}
