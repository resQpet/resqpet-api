package service.config.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Data
@Configuration
@ConfigurationProperties(prefix = "server.jwt")
public class JWTConfig {

    private RSAPublicKey publicKey;

    private RSAPrivateKey privateKey;

    @Bean
    public JwtDecoder decoder(JWTConfig config) {
        return NimbusJwtDecoder.withPublicKey(config.getPublicKey()).build();
    }

    @Bean
    public JwtEncoder encoder(JWTConfig config) {
        final JWK jwk = new RSAKey.Builder(config.getPublicKey()).privateKey(config.getPrivateKey()).build();
        final JWKSource<SecurityContext> source = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(source);
    }
}
