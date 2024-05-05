package ru.lexender.springcrud8gui;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Springcrud8GuiApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Springcrud8GuiApplication.class).headless(false).run(args);
    }

}
