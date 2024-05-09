package ru.lexender.springcrud8gui.net.command;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.lexender.springcrud8.dto.MovieDTO;
import ru.lexender.springcrud8.transfer.CommandRequest;
import ru.lexender.springcrud8.transfer.CommandResponse;
import ru.lexender.springcrud8gui.net.NetAddress;
import ru.lexender.springcrud8gui.net.NetConfiguration;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@RequiredArgsConstructor
@Log4j2
public class CommandRestClient {
    RestTemplate restTemplate;
    NetAddress address;

    HttpHeaders getDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer %s".formatted(NetConfiguration.authToken));
        return headers;
    }

    public CommandResponse query(String msg, List<MovieDTO> movies) throws Exception {
        CommandRequest request = new CommandRequest(msg, movies);
        HttpEntity<CommandRequest> entity = new HttpEntity<>(request, getDefaultHeaders());
        return restTemplate.postForObject(address.get() + "/api/query", entity, CommandResponse.class);
    }
}
