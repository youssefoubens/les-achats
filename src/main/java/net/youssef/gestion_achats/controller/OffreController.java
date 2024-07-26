package net.youssef.gestion_achats.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.youssef.gestion_achats.entity.offre;
import net.youssef.gestion_achats.services.OffreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class OffreController {

    @FXML
    private TableView<offre> offreTable;

    @FXML
    private TableColumn<offre, Long> idColumn;

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
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        fournisseurColumn.setCellValueFactory(cellData -> {cellData.getValue().getFournisseur().getEmail();
            return null;
        });
        articleColumn.setCellValueFactory(cellData -> {cellData.getValue().getArticle().getName();
            return null;
        });
        sousArticleColumn.setCellValueFactory(cellData -> {cellData.getValue().getSousArticle().getName();
            return null;
        });
        sousSousArticleColumn.setCellValueFactory(cellData -> {cellData.getValue().getSousSousArticle().getName();
            return null;
        });

        loadOffres();
    }

    private void loadOffres() {
        List<offre> offres = offreService.getAllOffres();
        offreTable.setItems(FXCollections.observableArrayList(offres));
    }

    @FXML



    private DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }
}
