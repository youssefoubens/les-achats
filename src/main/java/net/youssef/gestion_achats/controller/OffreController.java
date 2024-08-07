package net.youssef.gestion_achats.controller;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import net.youssef.gestion_achats.entity.offre;
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
}
