package ru.lexender.springcrud8.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.lexender.springcrud8.auth.userdata.Userdata;
import ru.lexender.springcrud8.transfer.CommandRequest;
import ru.lexender.springcrud8.transfer.CommandResponse;


@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Getter
@RequiredArgsConstructor
@ToString
public abstract class AbstractCommand implements Comparable<AbstractCommand> {
    String abbreviation, description, syntax;
    Userdata.Role permissionLimit;

    public AbstractCommand(String abbreviation, String description, Userdata.Role permissionLimit) {
        this(abbreviation, description, null, permissionLimit);
    }

    public abstract CommandResponse execute(CommandRequest request, Userdata user) throws Exception;

    public int compareTo(AbstractCommand command) {
        return abbreviation.compareTo(command.getAbbreviation());
    }
}
