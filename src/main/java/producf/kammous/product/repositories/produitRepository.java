package producf.kammous.product.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import producf.kammous.product.entities.Produit;

@Repository
public interface produitRepository extends JpaRepository<Produit, Long> {

    @Query("select p from Produit p where p.slug like %:slug% and p.supprimer= false order by p.id desc")
    Page<Produit> findProduitByNom(@Param("slug") String keyword, Pageable pageable);
    @Query("select p from Produit p where p.supprimer = false order by p.id desc")
    Page<Produit> findProduitNoSup(Pageable pageable);
}
