package com.agustinamestre.ShiftWiseBackend.security;

import com.agustinamestre.ShiftWiseBackend.domain.Permiso;
import com.agustinamestre.ShiftWiseBackend.domain.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.agustinamestre.ShiftWiseBackend.security.SecurityConstant.*;
import static java.util.Arrays.stream;


@Component
@Log4j2
public class JWTTokenProvider {
    @Value("${jwt.secret}")
    private String secret;


    public String generateJwtToken(User usuario){

        String perfil = usuario.getPerfil().getNombre();

        List<String> authorities = usuario.getPerfil().getPermisos().stream()
                .map(Permiso::getNombre)
                .toList();

        return TOKEN_PREFIX + JWT.create()
                .withIssuer(API_SHIFTWISE)
                .withSubject(usuario.getEmail())
                .withClaim("perfil", perfil)
                .withArrayClaim(AUTHORITIES, authorities.toArray(new String[0]))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        String[] claims = getClaimsFromToken(token);
        return stream(claims)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public Authentication getAuthentication(String email, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken userPasswordAuthToken
                = new UsernamePasswordAuthenticationToken(email, null, authorities);

        userPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return userPasswordAuthToken;
    }

    public String getSubject(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        JWTVerifier verifier = getJWTVerifier();
        boolean isValid = false;
        try {
            DecodedJWT jwtClaims = verifier.verify(token);
            Date expiration = jwtClaims.getExpiresAt();
            isValid = !isTokenExpired(expiration);
        } catch (TokenExpiredException e) {
            log.error("|* - El token se encuentra expirado.");
        } catch (JWTVerificationException e) {
            log.error("|* - Hubo un error al verificar el token.");
            log.error(e.getStackTrace());
        }
        return isValid;
    }

    public String getPerfilFromToken(String token) {
        return getJWTVerifier().verify(token).getClaim("perfil").asString();
    }



    private boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

    private String[] getClaimsFromToken(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
    }

    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(API_SHIFTWISE).build();
        } catch (JWTVerificationException ex) {
            throw new JWTVerificationException(TOKEN_NO_SE_PUEDE_VERIFICAR);
        }
        return verifier;
    }


}
