/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anhquoc0304.components;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 *
 * @author Admin
 */
@Component
public class JWTService {
    public static final String SECRET_KEY = "UE*^**(*bbb556ff%%&(*@!%&**(*NNN&&&&";
    public static final byte[] SHARED_SECRET_KEY = SECRET_KEY.getBytes();
    public static final int EXPIRE_TIME = 86400000;
    
    public String genarateTokenLogin(String username) {
        String token = null;
        try {
            JWSSigner signer = new MACSigner(SHARED_SECRET_KEY);
            
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
            builder.claim("username", username);
            builder.expirationTime(new Date(System.currentTimeMillis() + EXPIRE_TIME));
            JWTClaimsSet claimSet = builder.build();
            SignedJWT signed = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimSet);
            signed.sign(signer);
            token = signed.serialize();
        } catch (JOSEException ex) {
            ex.printStackTrace();
        }
        return token;
    }
    
    private JWTClaimsSet getClaimsFromToken(String token) throws ParseException {
        JWTClaimsSet claims = null;
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SHARED_SECRET_KEY);
            if(signedJWT.verify(verifier)) {
                claims = signedJWT.getJWTClaimsSet();
            }
        } catch (JOSEException ex) {
            ex.printStackTrace();
        }
        return claims;
    }
    
    private Date getExpriratonDateFromToken(String token) throws ParseException {
        JWTClaimsSet claims = getClaimsFromToken(token);
        return claims.getExpirationTime();
    }
    
    public String getUsernameFromToken(String token) throws ParseException {
        String username = null;
        JWTClaimsSet claims = getClaimsFromToken(token);
        return claims.getStringClaim("username"); 
    }
    
    private Boolean isTokenExpired(String token) throws ParseException {
        return getExpriratonDateFromToken(token).before(new Date());
    }
    
    public Boolean validateTokenLogin(String token) throws ParseException {
        if (token == null || token.trim().length() == 0)
            return false;
        String username = getUsernameFromToken(token);
        return !(username == null || username.isEmpty() || isTokenExpired(token));
    }
}
