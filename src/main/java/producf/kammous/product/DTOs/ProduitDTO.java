package producf.kammous.product.DTOs;

import lombok.Data;
import producf.kammous.product.entities.Categorie;

import java.util.Date;

@Data
public class ProduitDTO {
    private Long id;
    private String nom;
    private String description;
    private String image;
    private String slug;
    private Date createdAt;
    private Date updatedAt;
    private  boolean supprimer;
    private Categorie categorie;
}
