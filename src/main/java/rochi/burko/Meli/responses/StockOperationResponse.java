package rochi.burko.Meli.responses;

public class StockOperationResponse {
    private String message;

    public StockOperationResponse() {
    }

    public StockOperationResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
