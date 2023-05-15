package producf.kammous.product.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.Category;
import org.apache.kafka.common.serialization.Serializer;
import producf.kammous.product.entities.Categorie;

import java.util.Map;

public class CategorySerializer implements Serializer<Categorie> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public byte[] serialize(String topic, Categorie data) {
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
