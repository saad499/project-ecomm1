package producf.kammous.product.DTOs;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.util.Date;
@Data @Component
public class CategorieDTO {
    private Long id;
    private String nom;
    private String description;
    private String slug;
    private Date createdAt;
    private Date updatedAt;
    private  boolean supprimer;
}
