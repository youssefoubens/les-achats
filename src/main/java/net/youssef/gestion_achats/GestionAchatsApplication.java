package net.youssef.gestion_achats;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GestionAchatsApplication {

    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(GestionAchatsApplication.class)
                .run(args);

        // Launch JavaFX
        Application.launch(JavaFXApp.class, args);
    }
}
