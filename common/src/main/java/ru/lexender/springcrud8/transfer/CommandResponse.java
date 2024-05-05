package ru.lexender.springcrud8.transfer;

import lombok.Builder;
import ru.lexender.springcrud8.dto.MovieDTO;

import java.util.List;

@Builder
public record CommandResponse(Status status, String message, List<MovieDTO> movies) {

    public enum Status {
        OK,
        ERROR,
        MESSAGE
    }
}
