package ru.lexender.springcrud8.command.list;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.lexender.springcrud8.auth.userdata.Userdata;
import ru.lexender.springcrud8.command.AbstractCommand;
import ru.lexender.springcrud8.model.movie.MovieService;
import ru.lexender.springcrud8.transfer.CommandRequest;
import ru.lexender.springcrud8.transfer.CommandResponse;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class AddCommand extends AbstractCommand {
    MovieService movieService;

    public AddCommand(MovieService movieService) {
        super("add", "Adds objects to the collection", Userdata.Role.ROLE_USER);
        this.movieService = movieService;
    }

    @Override
    public CommandResponse execute(CommandRequest query, Userdata user) {

        return CommandResponse
                .builder()
                .status(CommandResponse.Status.OK)
                .build();
    }
}
