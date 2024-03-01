package com.ayushsingh.cacmp_backend.config.security.util;

import com.ayushsingh.cacmp_backend.constants.AppConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtUtil {

    public static String extractUsername(String token) {
        String subject = extractClaim(token, Claims::getSubject);
        System.out.println("Extracted subject: " + subject);
        return subject;
    }

    public static String extractEntityType(String token){
        final Claims claims=extractAllClaims(token);
        return (String) claims.get(AppConstants.ENTITY_TYPE);
    }

    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token) {

        Claims parsedClaims = Jwts.parser().setSigningKey(AppConstants.SECRET_KEY).parseClaimsJws(token).getBody();
        System.out.println("Parsed Claims: " + parsedClaims.getSubject());
        return parsedClaims;
    }

    private static Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public static String generateToken(String username, String entityType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(AppConstants.ENTITY_TYPE,entityType);
        return createToken(claims, username);
    }

    private static String createToken(Map<String, Object> claims, String subject) {
        Date issueDate = new Date(System.currentTimeMillis());
        System.out.println("issueDate: " + issueDate + " time: " + issueDate.getTime() + " issueDate formatted: "
                + issueDate);
        Date expirationDate = new Date(System.currentTimeMillis() + AppConstants.EXPIRATION_TIME
        );
        System.out.println("Expiration date: " + expirationDate + " formatted: " + expirationDate);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(issueDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, AppConstants.SECRET_KEY).compact()
                ;
    }


    public static Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        boolean isUsernameValid = username.equals(userDetails.getUsername());
        boolean isJwtTtokenExpired = isTokenExpired(token);
        System.out.println("Is token expired: " + isJwtTtokenExpired + " is username valid: " + isUsernameValid);
        if (!isUsernameValid) {
            System.out.println("Username in the token is invalid");
        }
        if (isJwtTtokenExpired) {
            System.out.println("Token is expired!");
        }
        return (isUsernameValid && !isJwtTtokenExpired);
    }

    public static String[] decodedBase64(String token) {

        byte[] decodedBytes = Base64.getDecoder().decode(token);
        String pairedCredentials = new String(decodedBytes);

        return pairedCredentials.split(":", 2);

    }

}
