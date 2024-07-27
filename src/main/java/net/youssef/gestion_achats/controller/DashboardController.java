package net.youssef.gestion_achats.controller;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.youssef.gestion_achats.util.FxmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.io.IOException;

@Controller
public class DashboardController {
    @Autowired
    private HomeController homeController;
    @Autowired
    private FxmlLoader fxmlLoader;
    @FXML
    private ImageView logoImageView;
    private Stage primaryStage;
    private Scene previousScene;

    @FXML
    private AnchorPane contentPane;
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }
    @FXML
    public void initialize() {
        // Load the logo image
        Image logoImage = new Image(getClass().getResourceAsStream("/images/logo.png"));
        logoImageView.setImage(logoImage);

        // Set DashboardController in HomeController
        homeController.setDashboardController(this);

        // Load the home view by default
        loadContent("/views/home.fxml");
    }
    @FXML
    public void viewConsultations(ActionEvent event) {
        loadContent("/views/consultations.fxml");
    }

    @FXML
    public void viewOffers(ActionEvent event) {
        loadContent("/views/offers.fxml");
    }

    @FXML
    public void consult(ActionEvent event) {
        loadContent("/views/consultationForm.fxml");
    }

    @FXML
    public void showHome(ActionEvent event) {
        loadContent("/views/home.fxml");
    }

    @FXML
    public void exitApplication(ActionEvent event) {
        System.exit(0);
    }


    private void setPreviousScene() {
        if (primaryStage != null) {
            this.previousScene = primaryStage.getScene();
        }
    }

    public void loadContent(String fxmlPath) {
        try {
            Parent newContent = fxmlLoader.load(fxmlPath);
            contentPane.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
