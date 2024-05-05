package ru.lexender.springcrud8.transfer;


public record AuthResponse(boolean invalid, String message, String token) {

}

