package ru.lexender.springcrud8.console;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.lexender.springcrud8.auth.userdata.Userdata;
import ru.lexender.springcrud8.auth.userdata.UserdataService;
import ru.lexender.springcrud8.command.CommandHandler;
import ru.lexender.springcrud8.transfer.CommandRequest;
import ru.lexender.springcrud8.transfer.CommandResponse;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class ConsoleLocalRunner implements Runnable {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Scanner scanner = new Scanner(System.in);
    CommandHandler handler;
    Userdata rootCredentials;

    public ConsoleLocalRunner(CommandHandler handler, UserdataService userdataService) {
        this.handler = handler;

        this.rootCredentials = Userdata
                .builder()
                .username("root")
                .role(Userdata.Role.ROLE_CONSOLE)
                .password("disabled")
                .locked(true)
                .build();

        userdataService.replaceByUsername("root", rootCredentials);
        // userdataService.save(rootCredentials);
        executor.execute(this);
    }

    public void shutdown() {
        executor.shutdown();
    }

    public void run() {
        String line;
        while (true) {
            line = scanner.nextLine();
            log.info("Inbound local message '{}'", line);
            CommandResponse response = handler.handle(new CommandRequest(line, null), rootCredentials);
            System.out.printf("[%s]\n", response.status());
            System.out.println(response.message());
        }
    }
}