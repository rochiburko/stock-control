package rochi.burko.Meli.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShippingDTO {

    private String logistic_type;

    public ShippingDTO() {
    }

    public ShippingDTO(String logistic_type) {
        this.logistic_type = logistic_type;
    }

    public String getLogistic_type() {
        return logistic_type;
    }

    public void setLogistic_type(String logistic_type) {
        this.logistic_type = logistic_type;
    }
}
