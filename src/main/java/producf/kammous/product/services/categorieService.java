package producf.kammous.product.services;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import producf.kammous.product.DTOs.CategorieDTO;
import producf.kammous.product.entities.Categorie;
import producf.kammous.product.exception.CategorieNotFoundException;

@Service
public interface categorieService {
    Page<CategorieDTO> listCategories(int page, int size);
    Categorie getCategorieById(Long id)throws CategorieNotFoundException,IllegalArgumentException;
    Categorie ajouterCategorie(Categorie categorie);
    Categorie modifierCategorie(Categorie categorie);

    Categorie HideLigneCategorie(CategorieDTO categorieDTO);
    Page<Categorie> chercherCategorie(String keyword, int page, int size);
    Page<Categorie> listCategoriesuprpimer(int page, int size);
}
