package producf.kammous.product.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import producf.kammous.product.DTOs.CategorieDTO;
import producf.kammous.product.entities.Categorie;
import producf.kammous.product.exception.CategorieNotFoundException;
import producf.kammous.product.exception.ErrorResponse;
import producf.kammous.product.services.categorieService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@Slf4j
public class CategorieRestController {
    private categorieService categorieServices;


    @GetMapping("/categorie")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<CategorieDTO>> categories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Page<CategorieDTO> categorieDTOS = categorieServices.listCategories(page,size);
        return ResponseEntity.ok(categorieDTOS);
    }

    @GetMapping("/categorieNotSup")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Categorie>> categorieNotDelete(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Page<Categorie> categorieDTOS = categorieServices.listCategoriesuprpimer(page, size);
        return  ResponseEntity.ok(categorieDTOS);
    }

    @GetMapping("/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Categorie>> chercherCategorie(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){
        Page<Categorie> categorieDTOS = categorieServices.chercherCategorie(keyword,page,size);
        return ResponseEntity.ok(categorieDTOS);
    }

    @GetMapping("/categorie/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Categorie getCategorieById(@PathVariable(name="id") String id) throws CategorieNotFoundException, IllegalArgumentException {
        try{
            Long categorieIdLong = Long.parseLong(id);
            if(categorieIdLong <=0){
                throw new IllegalArgumentException("Invalid categorie Id: "+id);
            }
            Categorie categorie = categorieServices.getCategorieById(categorieIdLong);
            if(categorie == null){
                throw new CategorieNotFoundException("Categorie not found with id:"+categorieIdLong);
            }
            return categorie;
        }catch (NumberFormatException e){
            throw new IllegalArgumentException("Invalid categorie Id : "+id);
        }

    }

    @PostMapping("/categorie")
    @PreAuthorize("hasRole('ADMIN')")
    public Categorie saveCategorie(@RequestBody @Valid Categorie categorie){
        return categorieServices.ajouterCategorie(categorie);
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


    @PutMapping("/categorie/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Categorie updateCategorie(@PathVariable(name="id") Long id, @RequestBody Categorie categorie){
        categorie.setId(id);
        return categorieServices.modifierCategorie(categorie);
    }

    @PatchMapping("/categorie/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Categorie> updateSupCategorie(@PathVariable(name="id") Long id, @RequestBody CategorieDTO categorieDTO){
        categorieDTO.setId(id);
        Categorie categorie = categorieServices.HideLigneCategorie(categorieDTO);
        return ResponseEntity.ok(categorie);
    }

    @ExceptionHandler(CategorieNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleategorieNotFoundException(CategorieNotFoundException ex) {
        return new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException ex){
        return new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
