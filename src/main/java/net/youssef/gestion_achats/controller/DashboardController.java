package net.youssef.gestion_achats.controller;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.youssef.gestion_achats.util.FxmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class DashboardController {

    @Autowired
    private FxmlLoader fxmlLoader;

    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void viewConsultations(ActionEvent event) throws IOException {
        primaryStage.setScene(new Scene(fxmlLoader.load("/views/consultations.fxml")));
        primaryStage.setTitle("Liste des Consultations");
        primaryStage.show();
    }

    public void viewOffers(ActionEvent event) throws IOException {
        primaryStage.setScene(new Scene(fxmlLoader.load("/views/offers.fxml")));
        primaryStage.setTitle("Liste des Offres");
        primaryStage.show();
    }

    public void consult(ActionEvent event) throws IOException {
        Scene currentScene = primaryStage.getScene();

        Parent root = fxmlLoader.load("/views/consultationForm.fxml");
        Scene newScene = new Scene(root);

        ConsultationFormController controller = (ConsultationFormController) fxmlLoader.getController("/views/consultationForm.fxml");
        controller.setPreviousScene(currentScene);

        primaryStage.setScene(newScene);
        primaryStage.setTitle("Consulter");
        primaryStage.show();
    }

    public void handleButtonClicks(ActionEvent actionEvent) {
        System.out.println("hellow");
    }
}
