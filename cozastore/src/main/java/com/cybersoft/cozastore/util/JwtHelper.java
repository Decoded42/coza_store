package com.cybersoft.cozastore.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtHelper {


    @Value("${custom.token.key}")
    private String secKey;

    private long expiredTime = 8 * 60 * 60 * 1000;


        public String generateToken (String data){
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secKey));

            Date date = new Date();
            long newDate = date.getTime() + expiredTime;
            Date newExpiredDate = new Date(newDate);

            String token = Jwts.builder().setSubject(data).signWith(key)
                    .setExpiration(newExpiredDate)
                    .compact();

            return token;
        }


        public String parseToken (String token){
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secKey));
            String data = Jwts.parserBuilder()
                    .setSigningKey(key).build()
                    .parseClaimsJws(token)
                    .getBody().getSubject();

            return data;
        }

}
