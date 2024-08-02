package net.youssef.gestion_achats.controller;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import net.youssef.gestion_achats.entity.Fournisseur;
import net.youssef.gestion_achats.services.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FournisseurController {

    @Autowired
    private FournisseurService fournisseurService;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TableView<Fournisseur> fournisseurTable;

    @FXML
    private TableColumn<Fournisseur, Long> idColumn;

    @FXML
    private TableColumn<Fournisseur, String> nameColumn;

    @FXML
    private TableColumn<Fournisseur, String> emailColumn;

    @FXML
    public void initialize() {
        // Initialize the TableView columns
        idColumn.setCellValueFactory(cellData -> new SimpleLongProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        // Load initial data
        updateTable();
    }

    @FXML
    private void handleAdd() {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setName(nameField.getText());
        fournisseur.setEmail(emailField.getText());
        fournisseurService.saveFournisseur(fournisseur);
        updateTable();
    }

    @FXML
    private void handleUpdate() {
        Fournisseur selectedFournisseur = fournisseurTable.getSelectionModel().getSelectedItem();
        if (selectedFournisseur != null) {
            selectedFournisseur.setName(nameField.getText());
            selectedFournisseur.setEmail(emailField.getText());
            fournisseurService.saveFournisseur(selectedFournisseur);
            updateTable();
        }
    }

    @FXML
    private void handleDelete() {
        Fournisseur selectedFournisseur = fournisseurTable.getSelectionModel().getSelectedItem();
        if (selectedFournisseur != null) {
            fournisseurService.deleteFournisseur(selectedFournisseur.getId());
            updateTable();
        }
    }

    @FXML
    private void handleFind() {
        String name = nameField.getText();
        Fournisseur fournisseur = fournisseurService.findByName(name);
        if (fournisseur != null) {
            fournisseurTable.getItems().clear();
            fournisseurTable.getItems().add(fournisseur);
        }
    }

    private void updateTable() {
        List<Fournisseur> fournisseurs = fournisseurService.getAllFournisseurs();
        fournisseurTable.getItems().setAll(fournisseurs);
    }
}
