package ru.lexender.springcrud8.command.list;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.lexender.springcrud8.auth.userdata.Userdata;
import ru.lexender.springcrud8.command.AbstractCommand;
import ru.lexender.springcrud8.command.CommandStorage;
import ru.lexender.springcrud8.transfer.CommandRequest;
import ru.lexender.springcrud8.transfer.CommandResponse;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class HelpCommand extends AbstractCommand {
    CommandStorage commands;

    @Lazy
    public HelpCommand(CommandStorage commands) {
        super("help", "Prints all available commands", Userdata.Role.ROLE_USER);
        this.commands = commands;
    }

    @Override
    public CommandResponse execute(CommandRequest query, Userdata user) {
        StringBuilder sb = new StringBuilder();
        commands.getCommandMap().values()
                .stream()
                .filter(o -> o.getPermissionLimit().compareTo(user.getRole()) <= 0)
                .forEach(
                        o -> {
                            sb.append("%s: %s (%s)\n"
                                    .formatted(o.getAbbreviation(), o.getDescription(), o.getPermissionLimit()));
                            if (o.getSyntax() != null) sb.append("SYNTAX: %s %s".formatted(o.getAbbreviation(), o.getSyntax()));
                            sb.append('\n');
                        }
                );
        return CommandResponse
                .builder()
                .status(CommandResponse.Status.OK)
                .message(sb.toString())
                .build();
    }
}
