package ru.lexender.springcrud8.command;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.lexender.springcrud8.auth.userdata.Userdata;
import ru.lexender.springcrud8.transfer.CommandRequest;
import ru.lexender.springcrud8.transfer.CommandResponse;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Log4j2
public class CommandService {
    CommandStorage storage;

    public Optional<AbstractCommand> getCommand(String commandName) {
        return Optional.ofNullable(storage.getCommandMap().get(commandName));
    }

    public boolean isEnabled(AbstractCommand abstractCommand, Userdata.Role role) {
        return abstractCommand.getPermissionLimit().compareTo(role) <= 0;
    }

    public CommandResponse execute(AbstractCommand command, CommandRequest query, Userdata author) {
        try {
            if (isEnabled(command, author.getRole()))
                return command.execute(query, author);
            else {
                log.info("Access denied: {} is disabled", command);
                return CommandResponse
                        .builder()
                        .status(CommandResponse.Status.OK)
                        .message("You don't have access to this command.")
                        .build();
            }
        } catch (Exception exception) {
            log.info("Command execution exception {}: {}", exception, exception.getMessage());
            return CommandResponse
                    .builder()
                    .status(CommandResponse.Status.OK)
                    .message(exception.getMessage())
                    .build();
        }
    }
}
