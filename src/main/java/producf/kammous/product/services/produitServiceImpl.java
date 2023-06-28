package producf.kammous.product.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import producf.kammous.product.DTOs.ProduitDTO;
import producf.kammous.product.entities.Categorie;
import producf.kammous.product.entities.Produit;
import producf.kammous.product.exception.ProductNotFoundException;
import producf.kammous.product.mapper.productMapperImpl;
import producf.kammous.product.repositories.produitRepository;

import java.io.IOException;
import java.util.Date;

@Service @AllArgsConstructor @Transactional
public class produitServiceImpl implements productService{

    private produitRepository produitRepository;
    private KafkaTemplate<String, Produit> kafkaTemplate;
    private productMapperImpl dtoMapper;
    @Override
    public Page listProduit(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Produit> produit = produitRepository.findAll(pageable);

        return produit;
    }

    @Override
    public Produit getProduitById(Long id) throws ProductNotFoundException {
        if(id<=0 || !String.valueOf(id).matches("\\d+")){
            throw new IllegalArgumentException("Ivalid product Id"+id);
        }
        Produit produit = produitRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product not found with Id : "+id));
        return produit;
    }

    @Override
    public Produit ajouterProduit(Produit produit) {
        produit.setCreatedAt(new Date());
        Produit produits = produitRepository.save(produit);
        kafkaTemplate.send("save-produit", produits);
        return produits;
    }
    @Override
    public Produit modifierProduit(Produit produit) {
        produit.setUpdatedAt(new Date());
        Produit produits = produitRepository.save(produit);
        kafkaTemplate.send("update-produit",produits);
        return produits;
    }

    @Override
    public Produit HideProduit(ProduitDTO produitDTO) {
        Produit produit = produitRepository.findById(produitDTO.getId()).orElseThrow();
        produit.setSupprimer(true);
        produitRepository.save(produit);
        kafkaTemplate.send("hide-produit",produit);
        return produit;
    }

    @Override
    public Page<Produit> listProduitNoSup(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Produit> produits = produitRepository.findProduitNoSup(pageable);
        return produits;
    }

    @Override
    public void supprimerProduit(Long id) {
        produitRepository.deleteById(id);
    }

    @Override
    public Page chercherProduit(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Produit> produit = produitRepository.findProduitByNom(keyword,pageable);
        return produit;
    }
}
