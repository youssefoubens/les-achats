package net.youssef.gestion_achats.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import net.youssef.gestion_achats.util.FxmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class HomeController {

    @Autowired
    private FxmlLoader fxmlLoader;

    private DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @FXML
    public void addOffre(ActionEvent event) throws IOException {
        System.out.println("hello");
        // Load the Add Offre scene
        dashboardController.loadContent("/views/addoffer.fxml");
    }

    @FXML
    public void manageFournisseurs(ActionEvent event) throws IOException {
        // Load the Manage Fournisseurs scene
        dashboardController.loadContent("/views/fournisseur_management.fxml");
    }

    @FXML
    public void manageProduits(ActionEvent event) throws IOException {
        // Load the Manage Produits scene
        dashboardController.loadContent("/views/manageProducts.fxml");
    }
}
