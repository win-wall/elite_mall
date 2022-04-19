package com.mall.elite.Security.jwt;

import com.mall.elite.Security.UserDetail;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    //! This is the secret key for JWT
    private final String JWT_SECRET = "elite";
    //!Time for a key to expire
    private final Long JWT_EXPIRATION = 604800000L;

    public String generateTokenFromUser(UserDetail userDetail){
        Date date = new Date();
        Date expiryDate =new Date(date.getTime()+JWT_EXPIRATION);
        return Jwts.builder()
                .setSubject(Long.toString(userDetail.getUser().getId()))
                .setIssuedAt(date)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }
    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }

        return false;
    }
}

