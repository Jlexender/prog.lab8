package ru.lexender.springcrud8gui.net.collection;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.lexender.springcrud8.dto.MovieDTO;
import ru.lexender.springcrud8.transfer.CommandResponse;
import ru.lexender.springcrud8gui.net.NetAddress;
import ru.lexender.springcrud8gui.net.NetConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@RequiredArgsConstructor
@Log4j2
public class CollectionRestClient {
    RestTemplate restTemplate;
    NetAddress address;

    HttpHeaders getDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer %s".formatted(NetConfiguration.authToken));
        return headers;
    }

    public List<MovieDTO> findAll() throws Exception {
        HttpEntity<String> tokenHeader = new HttpEntity<>(getDefaultHeaders());

        ResponseEntity<MovieDTO[]> response = restTemplate.exchange(
                "%s/api/db/find_all".formatted(address.get()),
                HttpMethod.GET,
                tokenHeader,
                MovieDTO[].class);

        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    public CommandResponse find(Long id) throws Exception {
        HttpEntity<String> tokenHeader = new HttpEntity<>(getDefaultHeaders());

        ResponseEntity<CommandResponse> response = restTemplate.exchange(
                "%s/api/db/find?id=%d".formatted(address.get(), id),
                HttpMethod.GET,
                tokenHeader,
                CommandResponse.class);

        return response.getBody();
    }
}
