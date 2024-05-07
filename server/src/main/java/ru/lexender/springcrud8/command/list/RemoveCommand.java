package ru.lexender.springcrud8.command.list;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.lexender.springcrud8.auth.userdata.Userdata;
import ru.lexender.springcrud8.command.AbstractCommand;
import ru.lexender.springcrud8.model.movie.Movie;
import ru.lexender.springcrud8.model.movie.MovieService;
import ru.lexender.springcrud8.transfer.CommandRequest;
import ru.lexender.springcrud8.transfer.CommandResponse;

import java.util.List;
import java.util.Optional;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RemoveCommand extends AbstractCommand {
    MovieService service;

    @Lazy
    public RemoveCommand(MovieService service) {
        super("remove", "Removes object(s) by id(s)", List.of("id1", "id2", "...").toString(),
                Userdata.Role.ROLE_USER);
        this.service = service;
    }

    @Override
    public CommandResponse execute(CommandRequest query, Userdata userdata) {
        StringBuilder sb = new StringBuilder("Removed: ");
        query.args().stream().skip(1).forEach(i -> {
            Optional<Movie> optional = service.findById(Long.parseLong(i));
            if (optional.isEmpty()) return;
            Movie movie = optional.get();
            if (movie.getAuthor().getRole().compareTo(userdata.getRole()) < 0
                    || movie.getAuthor().equals(userdata)) {
                service.deleteById(Long.parseLong(i));
                sb.append(i).append(' ');
            }
        });
        sb.append('.');

        return CommandResponse
                .builder()
                .status(CommandResponse.Status.OK)
                .message(sb.toString())
                .build();
    }
}