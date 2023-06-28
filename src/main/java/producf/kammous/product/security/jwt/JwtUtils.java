package producf.kammous.product.security.jwt;

import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import producf.kammous.product.security.services.UserDtailsImpl;
import io.jsonwebtoken.*;
import java.util.logging.Logger;

import java.util.Date;

@Component
@Slf4j
public class JwtUtils {
    private static final Logger logger = Logger.getLogger(JwtUtils.class.getName());

    @Value("${kammous.app.jwtSecret}")
    private String jwtSecret;

    @Value("${kammous.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDtailsImpl userPrincipal = (UserDtailsImpl) authentication.getPrincipal();
        log.info("userPrincipal.getUsername()"+userPrincipal.getUsername());


        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public String generateTokenFromUsername(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.warning("Invalid JWT signature: " + e.getMessage());
        }catch (MalformedJwtException e) {
            logger.warning("Invalid JWT token: " + e.getMessage());
        }catch (ExpiredJwtException e) {
            logger.warning("JWT token is expired: " + e.getMessage());
        }catch (UnsupportedJwtException e) {
            logger.warning("JWT token is unsupported: " + e.getMessage());
        }catch (IllegalArgumentException e) {
            logger.warning("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }
}
