package producf.kammous.product.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import producf.kammous.product.entities.Categorie;
import producf.kammous.product.entities.Produit;

import java.util.Map;

public class ProduitSerializer implements Serializer<Produit> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public byte[] serialize(String topic, Produit data) {
        byte[] serializedData = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            serializedData = objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serializedData;
    }

    @Override
    public void close() {}

}
