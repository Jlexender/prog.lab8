package ru.lexender.springcrud8gui.net;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Configuration
@RequiredArgsConstructor
public class NetConfiguration {
    public static volatile String authToken;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
