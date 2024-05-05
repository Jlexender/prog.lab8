package ru.lexender.springcrud8.transfer;

import ru.lexender.springcrud8.dto.MovieDTO;

import java.util.Arrays;
import java.util.List;

public record CommandRequest(String message, List<MovieDTO> movies) {
    public List<String> args() {
        return Arrays.asList(message.split(" "));
    }
}
