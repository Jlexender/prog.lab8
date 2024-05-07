package ru.lexender.springcrud8.command.list;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.lexender.springcrud8.auth.userdata.Userdata;
import ru.lexender.springcrud8.command.AbstractCommand;
import ru.lexender.springcrud8.model.movie.MovieService;
import ru.lexender.springcrud8.transfer.CommandRequest;
import ru.lexender.springcrud8.transfer.CommandResponse;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ShowCommand extends AbstractCommand {
    MovieService movieService;

    public ShowCommand(MovieService movieService) {
        super("show", "Shows collection", Userdata.Role.ROLE_USER);
        this.movieService = movieService;
    }

    @Override
    public CommandResponse execute(CommandRequest query, Userdata user) {
        StringBuilder sb = new StringBuilder();
        movieService.findAll().forEach(o -> sb.append(o).append('\n'));
        String message = sb.toString();

        return CommandResponse
                .builder()
                .status(CommandResponse.Status.OK)
                .message(message)
                .build();
    }
}
