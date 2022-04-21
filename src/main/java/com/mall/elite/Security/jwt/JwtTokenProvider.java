package com.mall.elite.Security.jwt;

import com.mall.elite.Entity.User;
import com.mall.elite.Security.UserDetail;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    //! This is the secret key for JWT
    @Value("${JWT_SERCET}")
    private String JWT_SECRET;
    //!Time for a key to expire
    @Value("${JWT_EXPIRATION_MIN}")
    private Long JWT_EXPIRATION_MIN;
    @Value("${JWT_EXPIRATION_DAY}")
    private Long JWT_EXPIRATION_DAY;
    public String generateTokenFromUser(UserDetail userDetail){
        Date date = new Date();
        Date expiryDate =new Date(date.getTime()+JWT_EXPIRATION_DAY);
        return Jwts.builder()
                .setSubject(userDetail.getUser().getUsername())
                .setIssuedAt(date)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }
    public String generateRefreshTokenFromUser(UserDetail userDetail){
        Date date = new Date();
        Date expiryDate = new Date(date.getTime() + JWT_EXPIRATION_MIN);
        return Jwts.builder()
                .setSubject(userDetail.getUser().getUsername())
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

