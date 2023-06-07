package producf.kammous.product.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
//import org.junit.platform.commons.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import producf.kammous.product.DTOs.ProduitDTO;
import producf.kammous.product.entities.Produit;
import producf.kammous.product.exception.CategorieNotFoundException;
import producf.kammous.product.exception.ErrorResponse;
import producf.kammous.product.exception.ProductNotFoundException;
import producf.kammous.product.services.productService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@Slf4j
public class ProduitRestController {
    private productService productService;
    //private KafkaListennnersProduit kafkaListennnersProduit;

    /*@PostMapping("addkafkaProd")
    public ResponseEntity<Produit> ajouterProduit(@RequestBody Produit produit){
        kafkaListennnersProduit.sendMessage(produit);
        return ResponseEntity.ok(produit);
    }*/

    @GetMapping("/produit")
    public ResponseEntity<Page<Produit>> produit(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Page<Produit> produitDTOS = productService.listProduitNoSup(page, size);
        return ResponseEntity.ok(produitDTOS);
    }
    @GetMapping("/produit/{id}")
    public Produit getProduitById(@PathVariable(name="id") Long id){
        return productService.getProduitById(id);
    }
    @PostMapping("/produit")
    public Produit saveProduit(@RequestBody @Valid Produit produit){
        return productService.ajouterProduit(produit);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex){
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        List<String> errorMessages = new ArrayList<>();
        for(FieldError error : fieldErrors){
            errorMessages.add(error.getDefaultMessage());
        }
        return new ErrorResponse("Validation error", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/produit/{id}")
    public Produit updateProduit(@PathVariable(name="id") Long id, @RequestBody Produit produit){
        produit.setId(id);
        return productService.modifierProduit(produit);
    }
    @PatchMapping("/produit/{id}")
    public ResponseEntity<Produit> updateSupProduit(@PathVariable(name="id") Long id, @RequestBody ProduitDTO produitDTO){
        produitDTO.setId(id);
        Produit produits = productService.HideProduit(produitDTO);
        return ResponseEntity.ok(produits);
    }
    @GetMapping("/produits")
    public ResponseEntity<Page<Produit>> chercherProduit(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){
        Page<Produit> produits = productService.chercherProduit(keyword,page,size);
        return ResponseEntity.ok(produits);
    }
    @ExceptionHandler(CategorieNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleategorieNotFoundException(ProductNotFoundException ex) {
        return new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException ex){
        return new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
