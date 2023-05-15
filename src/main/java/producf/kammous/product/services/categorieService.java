package producf.kammous.product.services;

import org.springframework.data.domain.Page;
import producf.kammous.product.DTOs.CategorieDTO;
import producf.kammous.product.entities.Categorie;

public interface categorieService {
    Page<CategorieDTO> listCategories(int page, int size);
    Categorie getCategorieById(Long id);
    Categorie ajouterCategorie(Categorie categorie);
    Categorie modifierCategorie(Categorie categorie);

    Categorie modifierLigne(CategorieDTO categorieDTO);
    Page<Categorie> chercherCategorie(String keyword, int page, int size);
    Page<Categorie> listCategoriesuprpimer(int page, int size);
}
