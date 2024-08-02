package net.youssef.gestion_achats.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.youssef.gestion_achats.entity.consultation;
import net.youssef.gestion_achats.services.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ConsultationController {

    @FXML
    private TableView<consultation> consultationTable;



    @FXML
    private TableColumn<consultation, String> fournisseurColumn;

    @FXML
    private TableColumn<consultation, String> articleColumn;

    @FXML
    private TableColumn<consultation, String> sousArticleColumn;

    @FXML
    private TableColumn<consultation, String> sousSousArticleColumn;

    @FXML
    private TableColumn<consultation, String> dateConsultationColumn;

    @Autowired
    private ConsultationService consultationService;

    private DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public void initialize() {

        fournisseurColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getFournisseur() != null) {
                return new SimpleStringProperty(cellData.getValue().getFournisseur().getEmail());
            } else {
                return new SimpleStringProperty(""); // Handle null values gracefully
            }
        });

        articleColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getArticle() != null) {
                return new SimpleStringProperty(cellData.getValue().getArticle().getName());
            } else {
                return new SimpleStringProperty(""); // Handle null values gracefully
            }
        });

        sousArticleColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getSArticle() != null) {
                return new SimpleStringProperty(cellData.getValue().getSArticle().getName()); // Assuming getName() method exists
            } else {
                return new SimpleStringProperty(""); // Handle null values gracefully
            }
        });

        sousSousArticleColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getSousSousArticle() != null) {
                return new SimpleStringProperty(cellData.getValue().getSousSousArticle().getName());
            } else {
                return new SimpleStringProperty(""); // Handle null values gracefully
            }
        });

        dateConsultationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateConsultation().toString()));

        loadConsultations();
    }

    private void loadConsultations() {
        List<consultation> consultations = consultationService.getAllConsultations();
        consultationTable.setItems(FXCollections.observableArrayList(consultations));
    }
}
