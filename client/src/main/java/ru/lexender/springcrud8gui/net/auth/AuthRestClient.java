package ru.lexender.springcrud8gui.net.auth;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.lexender.springcrud8.transfer.AuthResponse;
import ru.lexender.springcrud8gui.net.NetAddress;
import ru.lexender.springcrud8gui.net.NetConfiguration;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
@Service
public class AuthRestClient {
    RestTemplate restTemplate;
    NetAddress address;


    public AuthResponse login(String username, String password) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("username", username);
        map.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, new HttpHeaders());
        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(address.get() + "/login", request, AuthResponse.class);

        if (response.hasBody() && !response.getBody().invalid()) {
            NetConfiguration.authToken = response.getBody().token();
        }
        return response.getBody();
    }

    public AuthResponse register(String username, String password) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("username", username);
        map.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, new HttpHeaders());
        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(address.get() + "/register", request, AuthResponse.class);

        if (response.hasBody() && !response.getBody().invalid()) {
            NetConfiguration.authToken = response.getBody().token();
        }
        return response.getBody();
    }


}
