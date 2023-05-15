package producf.kammous.product.services;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import producf.kammous.product.DTOs.ProduitDTO;
import producf.kammous.product.entities.Produit;

import java.io.IOException;

public interface productService {
    Page<Produit> listProduit(int page, int size);
    ProduitDTO getProduitById(Long id);
    Produit ajouterProduit(Produit produit);

    //Produit saveProduit(Produit produit, MultipartFile file) throws IOException;
    Produit modifierProduit(Produit produit);
    Produit modifierLigne(ProduitDTO produitDTO);

    Page<Produit> listProduitNoSup(int page, int size);
    void supprimerProduit(Long id);
    Page chercherProduit(String keyword, int page, int size);
}
