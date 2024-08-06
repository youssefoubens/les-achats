package net.youssef.gestion_achats;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.youssef.gestion_achats.controller.DashboardController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class JavaFXApp extends Application {

    private static ApplicationContext context;
    private Stage primaryStage;

    @Autowired
    public void setApplicationContext(ApplicationContext context) {
        JavaFXApp.context = context;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Gestion des Achats");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dashboard.fxml"));

        if (context == null) {
            throw new IllegalStateException("ApplicationContext is not initialized");
        }

        loader.setControllerFactory(context::getBean);
        Parent root = loader.load();
        DashboardController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/views/dashboard.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard");
        primaryStage.setMinWidth(1160);
        primaryStage.setMinHeight(700);

        primaryStage.show();
    }

    @Override
    public void stop() {
        Platform.exit();
    }

    public static void main(String[] args) {
        Application.launch(JavaFXApp.class, args);
    }
}
