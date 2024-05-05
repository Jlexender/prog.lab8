package ru.lexender.springcrud8.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.lexender.springcrud8.command.exception.CommandAbbreviationCollisionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@Log4j2
@Component
public class CommandStorage {
    Map<String, AbstractCommand> commandMap = new HashMap<>();

    @Autowired
    public CommandStorage(List<AbstractCommand> allCommands) {
        allCommands.forEach(c -> {
            if (commandMap.containsKey(c.getAbbreviation()))
                throw new CommandAbbreviationCollisionException("Abbreviation " + c.getAbbreviation() + " is not unique");
            commandMap.put(c.getAbbreviation(), c);
        });
    }
}
