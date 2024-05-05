package ru.lexender.springcrud8gui.command;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@ShellComponent
public class CommandShellComponent {
    CommandRestClient commandRestClient;

    @ShellMethod("Make a query to server")
    public String s(@ShellOption String command) {
        return commandRestClient.query(command, null).message();
    }
}
