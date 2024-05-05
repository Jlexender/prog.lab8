package ru.lexender.springcrud8.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.lexender.springcrud8.auth.userdata.Userdata;
import ru.lexender.springcrud8.console.history.HistoryLog;
import ru.lexender.springcrud8.console.history.HistoryService;
import ru.lexender.springcrud8.transfer.CommandRequest;
import ru.lexender.springcrud8.transfer.CommandResponse;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@RequiredArgsConstructor
@Log4j2
public class CommandHandler {
    CommandService commandService;
    HistoryService historyService;

    public CommandResponse handle(CommandRequest query, Userdata author) {
        historyService.save(HistoryLog
                .builder()
                .userdata(author)
                .command(query.message())
                .time(LocalDateTime.now())
                .build());

        Optional<AbstractCommand> commandOptional = commandService.getCommand(query.args().get(0));
        if (commandOptional.isEmpty()) {
            log.warn("'{}' not identified as any command", query);
            return CommandResponse
                    .builder()
                    .status(CommandResponse.Status.OK)
                    .message("Command not recognized. Enter 'help' for command list.")
                    .build();
        }

        AbstractCommand command = commandOptional.get();
        log.info("'{}' identified as {}", query, command);
        return commandService.execute(command, query, author);
    }
}
