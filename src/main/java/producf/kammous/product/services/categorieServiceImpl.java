package producf.kammous.product.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import producf.kammous.product.DTOs.CategorieDTO;
import producf.kammous.product.entities.Categorie;
import producf.kammous.product.mapper.productMapperImpl;
import producf.kammous.product.repositories.*;

import java.util.Date;

@Service
@Transactional
@AllArgsConstructor
public class categorieServiceImpl implements categorieService{

    private categorieRepository categorieRepository;

    private KafkaTemplate<String, Categorie> kafkaTemplate;
    private productMapperImpl dtoMapper;
    @Override
    public Page listCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Categorie> listCategorie = categorieRepository.findAll(pageable);
        return listCategorie;
    }


    @Override
    public Categorie getCategorieById(Long id) {
        Categorie categorie = categorieRepository.findById(id).orElseThrow();
        return categorie;
    }

    @Override
    public Categorie ajouterCategorie(Categorie categorie) {
       // Categorie categorie = dtoMapper.fromCategorieDTO(categorie);
        categorie.setCreatedAt(new Date());
        Categorie saveCategorie = categorieRepository.save(categorie);
        kafkaTemplate.send("categorie",saveCategorie);
        return saveCategorie;
    }

    @Override
    public Categorie modifierCategorie(Categorie categorie) {
        categorie.setUpdatedAt(new Date());
        Categorie categories = categorieRepository.save(categorie);
        kafkaTemplate.send("categorie",categories);
        return categories;
    }

    @Override
    public Categorie modifierLigne(CategorieDTO categorieDTO) {
        Categorie categorie = categorieRepository.findById(categorieDTO.getId()).orElseThrow();
        categorie.setSupprimer(true);
        categorieRepository.save(categorie);
        kafkaTemplate.send("categorie",categorie);
        return categorie;
    }
    @Override
    public Page chercherCategorie(String keyword , int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Categorie> categories = categorieRepository.findCategorieByNom(keyword,pageable);
        return categories;
    }

    @Override
    public Page<Categorie> listCategoriesuprpimer(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Categorie> listCategorieSup = categorieRepository.findCategorieNoSupprimer(pageable);
        return listCategorieSup;
    }
}
