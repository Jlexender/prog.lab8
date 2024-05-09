package ru.lexender.springcrud8.command.list;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.lexender.springcrud8.auth.userdata.Userdata;
import ru.lexender.springcrud8.command.AbstractCommand;
import ru.lexender.springcrud8.console.history.HistoryLog;
import ru.lexender.springcrud8.console.history.HistoryService;
import ru.lexender.springcrud8.transfer.CommandRequest;
import ru.lexender.springcrud8.transfer.CommandResponse;

import java.util.List;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class HistoryCommand extends AbstractCommand {
    HistoryService service;

    @Lazy
    public HistoryCommand(HistoryService service) {
        super("history", "Prints out command history", Userdata.Role.ROLE_USER);
        this.service = service;
    }

    @Override
    public CommandResponse execute(CommandRequest query, Userdata user) {
        List<HistoryLog> logs = service.findByUsername(user.getUsername());
        StringBuilder sb = new StringBuilder();
        logs.forEach(m -> sb.append(m.getCommand()).append('\n'));

        return CommandResponse
                .builder()
                .message(sb.toString())
                .status(CommandResponse.Status.OK)
                .build();
    }
}
