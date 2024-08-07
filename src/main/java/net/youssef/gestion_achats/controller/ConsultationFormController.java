package net.youssef.gestion_achats.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import net.youssef.gestion_achats.entity.*;
import net.youssef.gestion_achats.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ConsultationFormController {

    @FXML
    private ComboBox<Article> articleComboBox;

    @FXML
    private ComboBox<Article_type> typeComboBox;

    @FXML
    private ComboBox<sarticle> sousArticleComboBox;

    @FXML
    private ComboBox<ssarticle> sousSousArticleComboBox;

    @FXML
    private ComboBox<AjouteEntreprise> ajouteentrepriseComboBox;

    @FXML
    private ComboBox<Fournisseur> fournisseurComboBox;

    @FXML
    private ComboBox<BORDEREAU> bordereauComboBox;

    @FXML
    private TextArea manualConsultationData;

    @FXML
    private TextArea pieceJointeData;

    @FXML
    private Button selectFilesButton;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private Articletypeservice typeService;

    @Autowired
    private Sarticleservice sousArticleService;

    @Autowired
    private SSarticleservices sousSousArticleService;

    @Autowired
    private AjouteEntrepriseService ajouteEntrepriseService;

    @Autowired
    private FournisseurService fournisseurService;

    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private PieceJointeService pieceJointeService;

    @Autowired
    private BordereauService bordereauService;

    private List<File> selectedFiles = new ArrayList<>();

    @FXML
    public void initialize() {
        populateBordereauComboBox();
        populateArticleComboBox();
        populateFournisseurComboBox();
        populateAjouteEntrepriseComboBox();
        bordereauComboBox.setOnAction(event -> populateArticleComboBox());
        articleComboBox.setOnAction(event -> {
            populateTypeComboBox();
            populateSousArticleComboBox();
            populateAjouteEntrepriseComboBox();
        });
        typeComboBox.setOnAction(event -> populateSousArticleComboBox());
        sousArticleComboBox.setOnAction(event -> populateSousSousArticleComboBox());
        sousSousArticleComboBox.setOnAction(event -> populateAjouteEntrepriseComboBox());
        setupComboBoxConverters();
    }

    private void populateArticleComboBox() {
        BORDEREAU selectedBordereau = bordereauComboBox.getValue();
        if (selectedBordereau != null) {
            List<Article> articles = selectedBordereau.getArticles();
            articleComboBox.setItems(FXCollections.observableArrayList(articles));
            articleComboBox.getSelectionModel().clearSelection();
        }
    }

    private void populateTypeComboBox() {
        Article selectedArticle = articleComboBox.getValue();
        if (selectedArticle != null) {
            List<Article_type> types = selectedArticle.getTypes();
            typeComboBox.setItems(FXCollections.observableArrayList(types));
            typeComboBox.getSelectionModel().clearSelection();
        } else {
            typeComboBox.setItems(FXCollections.observableArrayList());
        }
    }

    private void populateSousArticleComboBox() {
        Article selectedArticle = articleComboBox.getValue();
        Article_type selectedType = typeComboBox.getValue();
        if (selectedType != null) {
            List<sarticle> sousArticles = selectedType.getSousArticles();
            sousArticleComboBox.setItems(FXCollections.observableArrayList(sousArticles));
            sousArticleComboBox.getSelectionModel().clearSelection();
        } else if (selectedArticle != null) {
            List<sarticle> sousArticles = selectedArticle.getSarticles();
            sousArticleComboBox.setItems(FXCollections.observableArrayList(sousArticles));
            sousArticleComboBox.getSelectionModel().clearSelection();
        } else {
            sousArticleComboBox.setItems(FXCollections.observableArrayList());
        }
    }

    private void populateSousSousArticleComboBox() {
        sarticle selectedSousArticle = sousArticleComboBox.getValue();
        if (selectedSousArticle != null) {
            List<ssarticle> sousSousArticles = selectedSousArticle.getSsarticles();
            sousSousArticleComboBox.setItems(FXCollections.observableArrayList(sousSousArticles));
            sousSousArticleComboBox.getSelectionModel().clearSelection();
        } else {
            sousSousArticleComboBox.setItems(FXCollections.observableArrayList());
        }
    }

    private void populateFournisseurComboBox() {
        List<Fournisseur> fournisseurs = fournisseurService.getAllFournisseurs();
        fournisseurComboBox.setItems(FXCollections.observableArrayList(fournisseurs));
        fournisseurComboBox.getSelectionModel().clearSelection();
    }

    private void populateAjouteEntrepriseComboBox() {
        Article selectedArticle = articleComboBox.getValue();
        sarticle selectedSousArticle = sousArticleComboBox.getValue();
        ssarticle selectedSousSousArticle = sousSousArticleComboBox.getValue();
        List<AjouteEntreprise> ajouteEntreprises = new ArrayList<>();
        if (selectedArticle != null) {
            ajouteEntreprises.addAll(selectedArticle.getAjouteEntreprises());
        }
        if (selectedSousArticle != null) {
            ajouteEntreprises.addAll(selectedSousArticle.getAjouteEntreprises());
        }
        if (selectedSousSousArticle != null) {
            ajouteEntreprises.addAll(selectedSousSousArticle.getAjouteEntreprises());
        }
        ajouteentrepriseComboBox.setItems(FXCollections.observableArrayList(ajouteEntreprises));
        ajouteentrepriseComboBox.getSelectionModel().clearSelection();
    }

    private void populateBordereauComboBox() {
        List<BORDEREAU> bordereaux = bordereauService.getAllBordereaux();
        bordereauComboBox.setItems(FXCollections.observableArrayList(bordereaux));
        bordereauComboBox.getSelectionModel().clearSelection();
    }

    private void setupComboBoxConverters() {
        fournisseurComboBox.setCellFactory(comboBox -> new ListCell<>() {
            @Override
            protected void updateItem(Fournisseur item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getEmail());
            }
        });
        fournisseurComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Fournisseur item) {
                return item == null ? "" : item.getEmail();
            }

            @Override
            public Fournisseur fromString(String string) {
                return null; // Not used
            }
        });

        sousSousArticleComboBox.setCellFactory(comboBox -> new ListCell<>() {
            @Override
            protected void updateItem(ssarticle item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getName());
            }
        });
        sousSousArticleComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(ssarticle item) {
                return item == null ? "" : item.getName();
            }

            @Override
            public ssarticle fromString(String string) {
                return null; // Not used
            }
        });
    }

    @FXML
    private void selectFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif", "*.xlsx"),
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );

        List<File> files = fileChooser.showOpenMultipleDialog(selectFilesButton.getScene().getWindow());
        if (files != null) {
            selectedFiles.clear();
            selectedFiles.addAll(files);
            updatePieceJointeData();
        }
    }

    private void updatePieceJointeData() {
        StringBuilder sb = new StringBuilder();
        for (File file : selectedFiles) {
            sb.append(file.getAbsolutePath()).append("\n");
        }
        pieceJointeData.setText(sb.toString());
    }

    private Set<PieceJointe> saveSelectedFiles(consultation consultationa) throws Exception {
        Set<PieceJointe> piecesJointes = new HashSet<>();
        for (File file : selectedFiles) {
            Path destinationPath = Path.of("path/to/store/" + file.getName());
            Files.copy(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            PieceJointe pieceJointe = new PieceJointe();
            pieceJointe.setFilePath(destinationPath.toString());
            pieceJointe.setFilename(file.getName());
            piecesJointes.add(pieceJointe);
            pieceJointeService.savePieceJointe(pieceJointe);
        }
        return piecesJointes;
    }

    @FXML
    private void saveConsultation() {
        consultation consultation = new consultation();

        // Check for null values before calling methods
        consultation.setArticle(articleComboBox.getValue() != null ? articleComboBox.getValue().getName() : null);
        consultation.setSarticle(sousArticleComboBox.getValue() != null ? sousArticleComboBox.getValue().getName() : null);
        consultation.setSsarticle(sousSousArticleComboBox.getValue() != null ? sousSousArticleComboBox.getValue().getName() : null);
        consultation.setAjouteEntreprise(ajouteentrepriseComboBox.getValue() != null ? ajouteentrepriseComboBox.getValue().getDescription() : null);
        consultation.setFournisseur(fournisseurComboBox.getValue());
        consultation.setBordereau(bordereauComboBox.getValue());
        consultation.setDateConsultation(LocalDateTime.now());

        try {
            Set<PieceJointe> piecesJointes = saveSelectedFiles(consultation);
            consultation.setPiecesJointes(piecesJointes);
            consultationService.saveConsultation(consultation);
            showAlert("Success", "Consultation saved successfully.");
        } catch (Exception e) {
            showAlert("Error", "Error saving consultation: " + e.getMessage());
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
