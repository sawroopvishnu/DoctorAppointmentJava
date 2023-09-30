package com.doctorappointmentapp.securityconfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.doctorappointmentapp.entity.Doctor;
import com.doctorappointmentapp.entity.Patient;


import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenHelper {

    public static final long JWT_TOKEN_VALIDITY = 5*60*60;

    private String secret = "jwtTokenKey";
    @Value("${jwt.jwtExpiration}")
    private long jwtExpiration;

    public String getUserNameFromToken(String token){
//        return getClaimFromToken(token, Claims::getSubject);
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims  claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token the secret key is required
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJwt(token).getBody();
    }

    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //check token expired or not
    public Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

//    generate token from user
//    public String generateToken(UserDetails userDetails){
//        Map<String, Object> claims = new HashMap<>();
//        return doGenerateToken(claims, userDetails.getUsername());
//    }

    public String generateJwtToken(Authentication authentication) {

        Patient userPrincipal = (Patient) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime()+jwtExpiration))
                .setExpiration(new Date((new Date()).getTime()))
                .compact();
    }
    
    public String generateJwtTokenDoctor(Authentication authentication) {

        Doctor userPrincipal = (Doctor) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime()+jwtExpiration))
                .setExpiration(new Date((new Date()).getTime()))
                .compact();
    }

    private String doGenerateToken(Map<String, Object> claims, String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*100))
                .signWith(SignatureAlgorithm.ES512, secret).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails){
        final String userName = getUserNameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
