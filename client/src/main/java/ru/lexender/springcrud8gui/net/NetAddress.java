package ru.lexender.springcrud8gui.net;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
@PropertySource("classpath:application.properties")
public class NetAddress {
    String address;

    public NetAddress(@Value("${client.net.hostname}") String hostname,
                      @Value("${client.net.port}") Integer port) {
        this.address = "http://%s:%d".formatted(hostname, port);
    }

    public String get() {
        return address;
    }
}
