package net.bmw.bmwphd.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    private JwtEncoder encoder;
    private JwtDecoder decoder;

    @Autowired
    public void setEncoder(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    @Autowired
    public void setDecoder(JwtDecoder decoder) {
        this.decoder = decoder;
    }

    public String createToken(Authentication authentication) {
        Instant now = Instant.now();
        long expiry = 60L;
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" ")); // MUST BE space-delimited!!!
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("authorities", authorities)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public boolean isValid(String token) {
        // DecodedJWT jwt = JWT.decode(token);
//        if( jwt.getExpiresAt().before(new Date())) {
//            System.out.println("token is expired");
//        }
        // if (!claims.getExpiresAt().isBefore(Instant.now())) return true;
//        else return false;
        org.springframework.security.oauth2.jwt.Jwt decodedToken = this.decoder.decode(token);
        return !Objects.requireNonNull(decodedToken.getExpiresAt()).isBefore(Instant.now());
    }
}
