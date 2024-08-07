package net.youssef.gestion_achats.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.youssef.gestion_achats.entity.PieceJointe;
import net.youssef.gestion_achats.entity.consultation;
import net.youssef.gestion_achats.services.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ConsultationController {

    @FXML
    private TableView<consultation> consultationTable;

    @FXML
    private TableColumn<consultation, String> fournisseurColumn;

    @FXML
    private TableColumn<consultation, String> articleColumn;

    @FXML
    private TableColumn<consultation, String> sarticleColumn;

    @FXML
    private TableColumn<consultation, String> ssarticleColumn;

    @FXML
    private TableColumn<consultation, String> dateConsultationColumn;

    @FXML
    private TableColumn<consultation, String> piecesJointesColumn;

    @Autowired
    private ConsultationService consultationService;

    @FXML
    public void initialize() {
        fournisseurColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getFournisseur() != null) {
                return new SimpleStringProperty(cellData.getValue().getFournisseur().getName());
            } else {
                return new SimpleStringProperty(""); // Handle null values gracefully
            }
        });

        articleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArticle()));

        sarticleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSarticle()));

        ssarticleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSsarticle()));

        dateConsultationColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getDateConsultation() != null) {
                return new SimpleStringProperty(cellData.getValue().getDateConsultation().toString());
            } else {
                return new SimpleStringProperty(""); // Handle null values gracefully
            }
        });

        piecesJointesColumn.setCellValueFactory(cellData -> {
            Set<PieceJointe> pieces = cellData.getValue().getPiecesJointes();
            if (pieces != null && !pieces.isEmpty()) {
                return new SimpleStringProperty(pieces.stream()
                        .map(PieceJointe::getFilename) // Ensure getFilename() method exists in PieceJointe
                        .collect(Collectors.joining(", ")));
            } else {
                return new SimpleStringProperty(""); // Handle null values gracefully
            }
        });

        loadConsultations();
    }

    private void loadConsultations() {
        List<consultation> consultations = consultationService.getAllConsultations();
        consultationTable.setItems(FXCollections.observableArrayList(consultations));
    }
}
