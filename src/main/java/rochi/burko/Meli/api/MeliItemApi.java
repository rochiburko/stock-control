package rochi.burko.Meli.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rochi.burko.Meli.dto.ItemsDTO;

@Service
public class MeliItemApi {

    @Autowired
    private RestTemplate restTemplate;

    public ItemsDTO getItem(String itemId) {
        String uri = String.format("https://api.mercadolibre.com/items/%s", itemId);
        ItemsDTO item = restTemplate.getForObject(uri, ItemsDTO.class);
        return item;
    }
}
