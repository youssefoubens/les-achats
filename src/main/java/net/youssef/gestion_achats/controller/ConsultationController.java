package net.youssef.gestion_achats.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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
    private TableColumn<consultation, Long> idColumn;

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
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        // Handle null values and return a property for the cell
        fournisseurColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getFournisseur() != null) {
                return new SimpleStringProperty(cellData.getValue().getFournisseur().getEmail());
            } else {
                return null;
            }
        });

        articleColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getArticle() != null) {
                return new SimpleStringProperty(cellData.getValue().getArticle().getName());
            } else {
                return null;
            }
        });

        sousArticleColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getSsArticle() != null) {
                return new SimpleStringProperty();
            } else {
                return null;
            }
        });

        sousSousArticleColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getSousSousArticle() != null) {
                return new SimpleStringProperty(cellData.getValue().getSousSousArticle().getName());
            } else {
                return null;
            }
        });

        dateConsultationColumn.setCellValueFactory(new PropertyValueFactory<>("dateConsultation"));

        loadConsultations();
    }

    private void loadConsultations() {
        List<consultation> consultations = consultationService.getAllConsultations();
        consultationTable.setItems(FXCollections.observableArrayList(consultations));
    }
    @FXML
    public void goback(ActionEvent actionEvent) {

        dashboardController.goback(actionEvent);
    }

}
