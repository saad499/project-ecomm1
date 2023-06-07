package producf.kammous.product.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Produit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message="Name is required")
    @Size(max = 20, message="Name cannot exceed 20 characters")
    private String nom;
    @NotEmpty(message="Description is required")
    @Size(max = 300, message="Description cannot exceed 300 characters")
    private String description;
    @NotEmpty(message="photo is required")
    private String image;
    @NotEmpty(message="Slug is required")
    @Size(max = 20, message="Slug cannot exceed 20 characters")
    private String slug;
    private Date createdAt;
    private Date updatedAt;
    private  boolean supprimer;
    @ManyToOne
    private Categorie categorie;
}
