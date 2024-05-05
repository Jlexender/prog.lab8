package ru.lexender.springcrud8gui.auth;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.lexender.springcrud8gui.net.NetConfiguration;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@ShellComponent
public class AuthShellComponent {
    AuthRestClient authRestClient;

    @ShellMethod("Login to server")
    public String login(@ShellOption String username,
                        @ShellOption String password) {
        return authRestClient.login(username, password).message();
    }

    @ShellMethod("Register to server")
    public String register(@ShellOption String username,
                        @ShellOption String password) {
        return authRestClient.register(username, password).message();
    }

    @ShellMethod("Print jwt authentication token")
    public String jwt() {
        return NetConfiguration.authToken;
    }
}
