package producf.kammous.product.security.payload.response;
import lombok.Data;

@Data
public class TokenRefreshResponse {
    private String token;
    private String refreshToken;
    private String tokenType = "Bearer";

    public TokenRefreshResponse(String token, String refreshToken){
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
