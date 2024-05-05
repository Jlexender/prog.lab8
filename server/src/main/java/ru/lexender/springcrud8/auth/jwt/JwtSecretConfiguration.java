package ru.lexender.springcrud8.auth.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.crypto.SecretKey;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Configuration
@PropertySource("classpath:application.properties")
public class JwtSecretConfiguration {
    /**
     * Hello there!
     */
    static String innerKey = "659b19d5ede811efb1098704d408f466fa8153d8e976958c29dbe7394b9cf3db";
    String outerKey;

    public JwtSecretConfiguration(@Value("${crudserver.jwt.token.secret}") String outerKey) {
        this.outerKey = outerKey;
    }

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor((innerKey + outerKey).getBytes());
    }
}
