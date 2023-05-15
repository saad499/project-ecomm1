package producf.kammous.product.web;

import lombok.AllArgsConstructor;
//import org.junit.platform.commons.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import producf.kammous.product.DTOs.ProduitDTO;
import producf.kammous.product.entities.Produit;
import producf.kammous.product.services.productService;

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
    public ProduitDTO getProduitById(@PathVariable(name="id") Long id){
        return productService.getProduitById(id);
    }
    @PostMapping("/produit")
    public Produit saveProduit(@RequestBody Produit produit){
        return productService.ajouterProduit(produit);
    }


    @PutMapping("/produit/{id}")
    public Produit updateProduit(@PathVariable(name="id") Long id, @RequestBody Produit produit){
        produit.setId(id);
        return productService.modifierProduit(produit);
    }
    @PatchMapping("/produit/{id}")
    public ResponseEntity<Produit> updateSupProduit(@PathVariable(name="id") Long id, @RequestBody ProduitDTO produitDTO){
        produitDTO.setId(id);
        Produit produits = productService.modifierLigne(produitDTO);
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

    /*
    @PatchMapping("/categorie/{id}")
    public ResponseEntity<Categorie> updateSupCategorie(@PathVariable(name="id") Long id, @RequestBody CategorieDTO categorieDTO){
        categorieDTO.setId(id);
        Categorie categorie = categorieServices.modifierLigne(categorieDTO);
        return ResponseEntity.ok(categorie);
    }
    * */

}
