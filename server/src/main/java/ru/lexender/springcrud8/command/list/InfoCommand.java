package ru.lexender.springcrud8.command.list;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.lexender.springcrud8.auth.userdata.Userdata;
import ru.lexender.springcrud8.command.AbstractCommand;
import ru.lexender.springcrud8.model.movie.MovieService;
import ru.lexender.springcrud8.transfer.CommandRequest;
import ru.lexender.springcrud8.transfer.CommandResponse;

import java.util.Date;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class InfoCommand extends AbstractCommand {
    MovieService movieService;

    public InfoCommand(MovieService movieService) {
        super("info", "Prints general information", Userdata.Role.ROLE_USER);
        this.movieService = movieService;
    }

    @Override
    public CommandResponse execute(CommandRequest query, Userdata user) {
        String message = """
                Current time: %s
                Elements amount: %d
                Your username: %s
                Your role: %s
                """.formatted(new Date(), movieService.count(),
                user.getUsername(), user.getRole());
        return CommandResponse
                .builder()
                .status(CommandResponse.Status.OK)
                .message(message)
                .build();
    }
}
