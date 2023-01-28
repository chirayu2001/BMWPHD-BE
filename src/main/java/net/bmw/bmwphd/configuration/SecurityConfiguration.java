package net.bmw.bmwphd.configuration;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class SecurityConfiguration {

    MyPrivateKeyConverter myPrivateKeyConverter = new MyPrivateKeyConverter();
    MyPublicKeyConverter myPublicKeyConverter = new MyPublicKeyConverter();
//    @Value("${pubKey}")
//    String pubKeyString;
//    @Value("${privKey}")
//    String privKeyString;
//
//    RSAPrivateKey priv = myPrivateKeyConverter.convert(privKeyString);
//    RSAPublicKey key = myPublicKeyConverter.convert(pubKeyString);


     KeyFactory kf = KeyFactory.getInstance("RSA");
//
//    PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(pubKeyString));
//    RSAPrivateKey priv = (RSAPrivateKey) kf.generatePrivate(keySpecPKCS8);
//
//    X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(privKeyString));
//    RSAPublicKey key = (RSAPublicKey) kf.generatePublic(keySpecX509);

    @Value("${jwt.public.key}")
    RSAPublicKey key;
    @Value("${jwt.private.key}")
    RSAPrivateKey priv;

    @Value("${pubKey}")
    String pubKeyString;
    @Value("${privKey}")
    String privKeyString;

    public SecurityConfiguration() throws NoSuchAlgorithmException {
    }
//
//
//    public SecurityConfiguration() throws NoSuchAlgorithmException {
//    }

//    public SecurityConfiguration() throws NoSuchAlgorithmException, InvalidKeySpecException {
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("Key from heroku: " + pubKeyString);
//        RSAPrivateKey priv = myPrivateKeyConverter.convert(privKeyString);
       // pubKeyString = pubKeyString.replaceAll("[\\s|\\t|\\r\\n]+","").trim();
        //privKeyString = privKeyString.replaceAll("\\n","");
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(pubKeyString));
        RSAPublicKey myKey = (RSAPublicKey) kf.generatePublic(keySpecX509);
        System.out.println("after conversion Public: " + myKey.toString());
        //RSAPublicKey myKey = myPublicKeyConverter.convert(pubKeyString);

        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privKeyString));
        RSAPrivateKey priv2 = (RSAPrivateKey) kf.generatePrivate(keySpecPKCS8);


        System.out.println("after conversion private: " + priv2.toString());

        // @formatter:off
        http.cors(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize
                        .antMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("ROLE_admin")
                        .antMatchers(HttpMethod.GET, "/horses").permitAll()
                        .antMatchers(HttpMethod.GET, "/").permitAll()
                        .antMatchers(HttpMethod.GET, "/horses/search").permitAll()
                        // Disallow everything else...
                        .anyRequest().authenticated()
                )
                .csrf((csrf) -> csrf.ignoringAntMatchers("/auth/login"))
                .httpBasic(Customizer.withDefaults()) // using HTTP Basic Authentication
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                );
        // @formatter:on
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.key).build();
    }
    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.key).privateKey(this.priv).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // Let’s say that that your authorization server communicates authorities in a custom claim called authorities.
        // In that case, you can configure the claim that JwtAuthenticationConverter should inspect, like so:
        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        // You can also configure the authority prefix to be different as well.
        // For example, you can change it to ROLE_ like so:
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}

