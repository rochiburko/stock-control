package rochi.burko.Meli.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import rochi.burko.Meli.dto.SearchStockDTO;
import rochi.burko.Meli.dto.StockDTO;
import rochi.burko.Meli.repository.StockRepository;
import rochi.burko.Meli.requests.StockOperationRequest;
import rochi.burko.Meli.responses.StockOperationResponse;
import rochi.burko.Meli.service.StockService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StockController {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    private StockService stockService;

    @PostMapping("/addProductInLocation")
    @ResponseBody
    public ResponseEntity<StockOperationResponse> addProduct(@Valid @RequestBody StockOperationRequest request){
        try{
            stockService.addProduct(request);
            StockOperationResponse response = new StockOperationResponse("The product was successfully added");
            return ResponseEntity.ok(response);
        } catch (RuntimeException exception){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    exception.getMessage(),
                    exception
            );
        }
    }

    @PostMapping("/removeProductFromLocation")
    @ResponseBody
    public ResponseEntity<StockOperationResponse> removeProduct(@Valid @RequestBody StockOperationRequest request){
        try{
            stockService.removeProduct(request);
            StockOperationResponse response = new StockOperationResponse("The product was successfully removed");
            return ResponseEntity.ok(response);
        } catch (RuntimeException exception){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    exception.getMessage(),
                    exception
            );
        }
    }

    @GetMapping("/listProductFromLocation")
    @ResponseBody
    public ResponseEntity<List<StockDTO>> listProduct(
            @RequestParam  @NotNull @NotBlank @Pattern(regexp = "[A-Z]{2}[0-9]{2}") String warehouse,
            @RequestParam @NotNull @NotBlank @Pattern(regexp = "[A-Z]{2}-[0-9]{2}-[0-9]{2}-(IZ|DE)") String location){
        try{
            List<StockDTO> productsFromLocation = stockService.listProduct(warehouse,location);
            return ResponseEntity.ok(productsFromLocation);
        } catch (RuntimeException exception){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    exception.getMessage(),
                    exception
            );
        }
    }

    @GetMapping("/searchProductFromWarehouse")
    @ResponseBody
    public ResponseEntity<List<SearchStockDTO>> searchProduct(
            @RequestParam @NotNull @NotBlank @Pattern(regexp = "[A-Z]{2}[0-9]{2}") String warehouse,
            @RequestParam @NotNull @NotBlank String product){
        try{
            List<SearchStockDTO> productsFromLocation = stockService.searchProduct(warehouse,product);
            return ResponseEntity.ok(productsFromLocation);
        } catch (RuntimeException exception){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    exception.getMessage(),
                    exception
            );
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}


