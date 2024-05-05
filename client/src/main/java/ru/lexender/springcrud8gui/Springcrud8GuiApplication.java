package ru.lexender.springcrud8gui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Springcrud8GuiApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Springcrud8GuiApplication.class).headless(false).run(args);
    }

}
