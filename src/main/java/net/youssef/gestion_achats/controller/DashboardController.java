package net.youssef.gestion_achats.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
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
    private Scene previousScene;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void viewConsultations(ActionEvent event) throws IOException {
        setPreviousScene();
        Parent root = fxmlLoader.load("/views/consultations.fxml");
        Scene newScene = new Scene(root, 800, 600); // Adjust size as needed
        ConsultationController controller = (ConsultationController) fxmlLoader.getController("/views/consultations.fxml");
        controller.setDashboardController(this);
        primaryStage.setScene(newScene);
        primaryStage.setTitle("Liste des Consultations");
        primaryStage.show();
    }

    public void viewOffers(ActionEvent event) throws IOException {
        setPreviousScene();
        Parent root = fxmlLoader.load("/views/offers.fxml");
        Scene newScene = new Scene(root, 800, 600); // Adjust size as needed
        OffreController controller = (OffreController) fxmlLoader.getController("/views/offers.fxml");
        controller.setDashboardController(this);
        primaryStage.setScene(newScene);
        primaryStage.setTitle("Liste des Offres");
        primaryStage.show();
    }

    public void consult(ActionEvent event) throws IOException {
        setPreviousScene();
        Parent root = fxmlLoader.load("/views/consultationForm.fxml");
        Scene newScene = new Scene(root);
        ConsultationFormController controller = (ConsultationFormController) fxmlLoader.getController("/views/consultationForm.fxml");
        controller.setDashboardController(this);
        primaryStage.setScene(newScene);
        primaryStage.setTitle("Consulter");
        primaryStage.show();
    }

    public void showHome(ActionEvent event) throws IOException {
        setPreviousScene();
        Parent root = fxmlLoader.load("/views/home.fxml");
        Scene newScene = new Scene(root);
        primaryStage.setScene(newScene);
        primaryStage.setTitle("Home");
        primaryStage.show();
    }

    public void exitApplication(ActionEvent event) {
        System.exit(0);
    }

    public void goback(ActionEvent actionEvent) {
        if (previousScene != null) {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(previousScene);
        } else {
            System.out.println("No previous scene available");
        }
    }

    private void setPreviousScene() {
        if (primaryStage != null) {
            this.previousScene = primaryStage.getScene();
        }
    }

    public void loadContent(String s) {
    }
}
