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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class SecurityConfiguration {
    KeyFactory kf = KeyFactory.getInstance("RSA");

    @Value("${APP.PUB}")
    String pubKeyString;
    @Value("${APP.KEY}")
    String privKeyString;

    public SecurityConfiguration() throws NoSuchAlgorithmException {

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize
                        .antMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("ROLE_admin")
                        .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .antMatchers(HttpMethod.GET, "/horses").permitAll()
                        .antMatchers(HttpMethod.GET, "/").permitAll()
                        .antMatchers(HttpMethod.POST, "/horses/search").permitAll()
                        .antMatchers(HttpMethod.GET, "/horse-be.jpg").permitAll()
                        .antMatchers(HttpMethod.POST, "/users").permitAll()
                        .antMatchers(HttpMethod.GET, "/horses/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/.well-known/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/token").permitAll()
                        // Disallow everything else...
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                // .csrf((csrf) -> csrf.ignoringAntMatchers("/auth/login"))
                // .csrf((csrf) -> csrf.ignoringAntMatchers("/horses/search"))
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
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    JwtDecoder jwtDecoder() throws InvalidKeySpecException {
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(pubKeyString));
        RSAPublicKey myKey = (RSAPublicKey) kf.generatePublic(keySpecX509);
        return NimbusJwtDecoder.withPublicKey(myKey).build();
    }

    @Bean
    JwtEncoder jwtEncoder() throws InvalidKeySpecException {
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(pubKeyString));
        RSAPublicKey myKey = (RSAPublicKey) kf.generatePublic(keySpecX509);

        int mod4 = privKeyString.length() % 4;

        if (mod4 > 0) {
            privKeyString = privKeyString +
                    "=".repeat(4 - mod4);
        }
        byte[] binCpk = Base64.getDecoder().decode(privKeyString);
        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(binCpk);
        RSAPrivateKey priv2 = (RSAPrivateKey) kf.generatePrivate(keySpecPKCS8);


//        System.out.println("in the encoder private: " + priv2.toString());
//        System.out.println("in the encoder public: " + myKey.toString());

        JWK jwk = new RSAKey.Builder(myKey).privateKey(priv2).build();
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

