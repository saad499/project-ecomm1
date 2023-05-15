package producf.kammous.product.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import producf.kammous.product.DTOs.CategorieDTO;
import producf.kammous.product.entities.Categorie;
import producf.kammous.product.services.categorieService;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@Slf4j
public class CategorieRestController {
    private categorieService categorieServices;


    @GetMapping("/categorie")
    public ResponseEntity<Page<CategorieDTO>> categories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Page<CategorieDTO> categorieDTOS = categorieServices.listCategories(page,size);
        return ResponseEntity.ok(categorieDTOS);
    }

    @GetMapping("/categorieNotSup")
    public ResponseEntity<Page<Categorie>> categorieNotDelete(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Page<Categorie> categorieDTOS = categorieServices.listCategoriesuprpimer(page, size);
        return  ResponseEntity.ok(categorieDTOS);
    }

    @GetMapping("/categories")
    public ResponseEntity<Page<Categorie>> chercherCategorie(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){
        Page<Categorie> categorieDTOS = categorieServices.chercherCategorie(keyword,page,size);
        return ResponseEntity.ok(categorieDTOS);
    }

    @GetMapping("/categorie/{id}")
    public Categorie getCategorieById(@PathVariable(name="id") Long id){
        return categorieServices.getCategorieById(id);
    }

    @PostMapping("/categorie")
    public Categorie saveCategorie(@RequestBody Categorie categorie){
        return categorieServices.ajouterCategorie(categorie);
    }

    @PutMapping("/categorie/{id}")
    public Categorie updateCategorie(@PathVariable(name="id") Long id, @RequestBody Categorie categorie){
        categorie.setId(id);
        return categorieServices.modifierCategorie(categorie);
    }

    @PatchMapping("/categorie/{id}")
    public ResponseEntity<Categorie> updateSupCategorie(@PathVariable(name="id") Long id, @RequestBody CategorieDTO categorieDTO){
        categorieDTO.setId(id);
        Categorie categorie = categorieServices.modifierLigne(categorieDTO);
        return ResponseEntity.ok(categorie);
    }

}
