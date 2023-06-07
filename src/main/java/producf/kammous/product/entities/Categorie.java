package producf.kammous.product.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Categorie implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message="Name is required")
    @Size(max = 20, message="Name cannot exceed 20 characters")
    private String nom;
    @NotEmpty(message="Description is required")
    @Size(max = 300, message="Description cannot exceed 300 characters")
    private String description;
    @NotEmpty(message="Slug is required")
    @Size(max = 20, message="Slug cannot exceed 20 characters")
    private String slug;
    private Date createdAt;
    private Date updatedAt;
    private  boolean supprimer;
    @OneToMany(mappedBy = "categorie")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Produit> produit;
}
