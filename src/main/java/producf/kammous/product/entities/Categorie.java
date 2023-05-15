package producf.kammous.product.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Categorie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private String slug;
    private Date createdAt;
    private Date updatedAt;
    private  boolean supprimer;
    @OneToMany(mappedBy = "categorie")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Produit> produit;
}
