package net.youssef.gestion_achats.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import net.youssef.gestion_achats.entity.*;
import net.youssef.gestion_achats.entity.offre;
import net.youssef.gestion_achats.repository.Article_TypeRepository;
import net.youssef.gestion_achats.repository.Articlerepository;
import net.youssef.gestion_achats.repository.SSarticleRepository;
import net.youssef.gestion_achats.repository.SarticleRepository;
import net.youssef.gestion_achats.services.OffreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OffreController {

    @FXML
    private TableView<offre> offreTable;

    @FXML
    private TableColumn<offre, Boolean> selectColumn;

    @FXML
    private TableColumn<offre, String> descriptionColumn;

    @FXML
    private TableColumn<offre, String> fournisseurColumn;

    @FXML
    private TableColumn<offre, String> articleColumn;

    @FXML
    private TableColumn<offre, String> sarticleColumn;

    @FXML
    private TableColumn<offre, String> ssarticleColumn;

    @FXML
    private TableColumn<offre, String> priceColumn;

    @FXML
    private Button saveSelectedButton;

    @Autowired
    private OffreService offreService;


    public void initialize() {
        // Set up selectColumn with CheckBoxTableCell
        selectColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        selectColumn.setCellFactory(column -> new CheckBoxTableCell<>());

        // Configure the columns
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        fournisseurColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getFournisseur() != null) {
                return new SimpleStringProperty(cellData.getValue().getFournisseur().getName());
            }
            return new SimpleStringProperty("");
        });

        articleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArticle()));
        sarticleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSarticle()));
        ssarticleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSsarticle()));

        // Set up priceColumn with PropertyValueFactory
        priceColumn.setCellValueFactory(cellData -> {
            // Format price to show two decimal places
            return new SimpleStringProperty(String.format("%.2f", cellData.getValue().getPrice()));
        });

        loadOffres();

        // Set up row factory to handle row selection and style
        offreTable.setRowFactory(tv -> {
            TableRow<offre> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    offre offre = row.getItem();
                    boolean isSelected = !offre.isSelected();
                    offre.setSelected(isSelected);
                    row.setStyle(isSelected ? "-fx-background-color: lightblue;" : "");
                }
            });
            return row;
        });

        // Set up saveSelectedButton action
        saveSelectedButton.setOnAction(event -> saveSelectedOffers());
    }

    public void loadOffres() {
        List<offre> offres = offreService.getAllOffres();
        offreTable.setItems(FXCollections.observableArrayList(offres));
    }

    // Method to get selected offers
    public List<offre> getSelectedOffers() {
        return offreTable.getItems().stream()
                .filter(offre::isSelected)
                .collect(Collectors.toList());
    }

    private void saveSelectedOffers() {
        List<offre> selectedOffers = getSelectedOffers();

        if (selectedOffers.isEmpty()) {
            Alert noSelectionAlert = new Alert(Alert.AlertType.INFORMATION);
            noSelectionAlert.setTitle("No Selection");
            noSelectionAlert.setHeaderText(null);
            noSelectionAlert.setContentText("No offers selected. Please select offers to save.");
            noSelectionAlert.showAndWait();
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Save Selected Offers");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to save the selected offers?");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Process each selected offer
                for (offre selectedOffer : selectedOffers) {
                    // Set the price in the respective entity
                    setPriceInEntity(selectedOffer);
                }
                // Show a success alert
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Selected offers saved successfully.");
                successAlert.showAndWait();
            }
        });
    }

    private void setPriceInEntity(offre selectedOffer) {
        // Logic to set price in the respective article, sarticle, or ssarticle
        String article = selectedOffer.getArticle();
        String sarticle = selectedOffer.getSarticle();
        String ssarticle = selectedOffer.getSsarticle();
        double price = selectedOffer.getPrice();

        // Assuming you have methods to update these entities
        if (article != null && !article.isEmpty()) {
            // Update the article with the price
            updateArticlePrice(article, price);
        } else if (sarticle != null && !sarticle.isEmpty()) {
            // Update the sarticle with the price
            updateSarticlePrice(sarticle, price);
        } else if (ssarticle != null && !ssarticle.isEmpty()) {
            // Update the ssarticle with the price
            updateSsarticlePrice(ssarticle, price);
        }
    }

// In OffreService or respective services
private void updateArticlePrice(String article, double price) {
    offreService.updateArticlePrice(article, price);
}

    private void updateSarticlePrice(String sarticle, double price) {
        offreService.updateSarticlePrice(sarticle, price);
    }

    private void updateSsarticlePrice(String ssarticle, double price) {
        offreService.updateSsarticlePrice(ssarticle, price);
    }

}
