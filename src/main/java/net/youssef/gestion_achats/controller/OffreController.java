package net.youssef.gestion_achats.controller;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
    private TableColumn<offre, String> sousArticleColumn;

    @FXML
    private TableColumn<offre, String> sousSousArticleColumn;

    @Autowired
    private OffreService offreService;

    public void initialize() {
        // Set up selectColumn with CheckBoxTableCell
        selectColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        selectColumn.setCellFactory(column -> new CheckBoxTableCell<>());

        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        fournisseurColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getFournisseur() != null) {
                return new SimpleStringProperty(cellData.getValue().getFournisseur().getName());
            }
            return new SimpleStringProperty("");
        });
        articleColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getArticle() != null) {
                return new SimpleStringProperty(cellData.getValue().getArticle().getName());
            }
            return new SimpleStringProperty("");
        });
        sousArticleColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getSousArticle() != null) {
                return new SimpleStringProperty(cellData.getValue().getSousArticle().getName());
            }
            return new SimpleStringProperty("");
        });
        sousSousArticleColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getSousSousArticle() != null) {
                return new SimpleStringProperty(cellData.getValue().getSousSousArticle().getName());
            }
            return new SimpleStringProperty("");
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
                    // Update the checkbox state
                    ((CheckBoxTableCell<offre, Boolean>) selectColumn.getCellFactory().call(selectColumn)).updateItem(isSelected, false);
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
