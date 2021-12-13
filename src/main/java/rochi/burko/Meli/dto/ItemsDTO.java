package rochi.burko.Meli.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemsDTO {

    private String id;
    private ShippingDTO shipping;

    public ItemsDTO() {
    }

    public ItemsDTO(String id, ShippingDTO shipping) {
        this.id = id;
        this.shipping = shipping;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ShippingDTO getShipping() {
        return shipping;
    }

    public void setShipping(ShippingDTO shipping) {
        this.shipping = shipping;
    }
}




