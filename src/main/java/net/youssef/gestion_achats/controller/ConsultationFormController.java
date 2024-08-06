package net.youssef.gestion_achats.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;
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

    private List<File> selectedFiles = new ArrayList<>();

    @FXML
    public void initialize() {
        populateArticleComboBox();
        populateFournisseurComboBox();
        populateAjouteEntrepriseComboBox();

        articleComboBox.setOnAction(event -> {populateTypeComboBox();populateSousArticleComboBox();});
        typeComboBox.setOnAction(event -> populateSousArticleComboBox());
        sousArticleComboBox.setOnAction(event -> populateSousSousArticleComboBox());

        setupComboBoxConverters();
    }

    private void populateArticleComboBox() {
        List<Article> articles = articleService.getAllArticles();
        articleComboBox.setItems(FXCollections.observableArrayList(articles));
        articleComboBox.getSelectionModel().clearSelection();
    }

    private void populateTypeComboBox() {
        Article selectedArticle = articleComboBox.getValue();

        if (selectedArticle != null) {
            List<Article_type> types = selectedArticle.getTypes();
            typeComboBox.setItems(FXCollections.observableArrayList(types));
            typeComboBox.getSelectionModel().clearSelection();
            sousArticleComboBox.setItems(FXCollections.observableArrayList());
            sousSousArticleComboBox.setItems(FXCollections.observableArrayList());
        } else {
            typeComboBox.setItems(FXCollections.observableArrayList());
            sousArticleComboBox.setItems(FXCollections.observableArrayList());
            sousSousArticleComboBox.setItems(FXCollections.observableArrayList());
        }
    }

    private void populateSousArticleComboBox() {
        Article selectedArticle = articleComboBox.getValue();
        Article_type selectedType = typeComboBox.getValue();

        if (selectedType != null)
        {
            List<sarticle> sousArticles = selectedType.getSousArticles();
            sousArticleComboBox.setItems(FXCollections.observableArrayList(sousArticles));
            sousArticleComboBox.getSelectionModel().clearSelection();
        }
      else if (selectedArticle != null) {
            List<sarticle> sousArticles = selectedArticle.getSarticles();
            System.out.println("Fetched sousArticles for selectedArticle: " + selectedArticle);
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
        List<AjouteEntreprise> ajouteEntreprises = ajouteEntrepriseService.getAllAjouteEntreprises();
        ajouteentrepriseComboBox.setItems(FXCollections.observableArrayList(ajouteEntreprises));
        ajouteentrepriseComboBox.getSelectionModel().clearSelection();
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
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"),
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
        Path destinationDir = Path.of("C:/User/youssef/Desktop/save/files"); // Update this path to your desired save location

        Files.createDirectories(destinationDir); // Ensure directory exists

        for (File file : selectedFiles) {
            Path destinationPath = destinationDir.resolve(file.getName());
            Files.copy(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            PieceJointe pieceJointe = PieceJointe.builder()
                    .filePath(destinationPath.toString())
                    .filename(file.getName())
                    .consultations(new HashSet<>())
                    .build();

            // Add the consultation to the pieceJointe's consultations set
            pieceJointe.getConsultations().add(consultationa);

            pieceJointeService.savePieceJointe(pieceJointe);
            piecesJointes.add(pieceJointe);
        }

        return piecesJointes;
    }

    @FXML
    private void enregistrerConsultation() {
        try {
            Fournisseur selectedFournisseur = fournisseurComboBox.getValue();
            Article selectedArticle = articleComboBox.getValue();
            sarticle selectedSousArticle = sousArticleComboBox.getValue();
            ssarticle selectedSousSousArticle = sousSousArticleComboBox.getValue();
            AjouteEntreprise selectedAjouteEntreprise = ajouteentrepriseComboBox.getValue();

            if (selectedFournisseur == null || selectedArticle == null) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Please select both Article and Fournisseur.");
                return;
            }

            // Create and save Consultation
            consultation consultationa = consultation.builder()
                    .fournisseur(selectedFournisseur)
                    .article(selectedArticle)
                    .sArticle(selectedSousArticle)
                    .sousSousArticle(selectedSousSousArticle)
                    .ajouteEntreprise(selectedAjouteEntreprise)
                    .dateConsultation(LocalDateTime.now())
                    .piecesJointes(new HashSet<>()) // Initialize the set
                    .build();

            // Save the files and create PieceJointe entities
            Set<PieceJointe> piecesJointes = saveSelectedFiles(consultationa);

            // Set the piecesJointes for the consultation
            consultationa.setPiecesJointes(piecesJointes);

            // Update the other side of the relationship
            for (PieceJointe pieceJointe : piecesJointes) {
                pieceJointe.getConsultations().add(consultationa);
                pieceJointeService.savePieceJointe(pieceJointe);
            }

            // Save the consultation
            consultationService.saveConsultation(consultationa);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Consultation saved successfully!");

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save consultation. " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
