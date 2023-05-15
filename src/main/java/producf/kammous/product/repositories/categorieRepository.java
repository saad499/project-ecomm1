package producf.kammous.product.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import producf.kammous.product.DTOs.CategorieDTO;
import producf.kammous.product.entities.Categorie;

import java.util.List;

@Repository
public interface categorieRepository extends JpaRepository<Categorie,Long> {

    @Query("select c from Categorie c where c.slug like %:slug% and c.supprimer= false order by c.id desc")
    Page<Categorie> findCategorieByNom(@Param("slug") String keyword, Pageable pageable);
    @Query("select c from Categorie c where c.supprimer = false order by c.id desc")
    Page<Categorie> findCategorieNoSupprimer(Pageable pageable);

    //CategorieDTO ajouterCategorie(CategorieDTO categorieDTO);

}
