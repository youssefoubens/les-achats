package net.youssef.gestion_achats.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.util.StringConverter;
import net.youssef.gestion_achats.entity.*;
import net.youssef.gestion_achats.services.*;
import net.youssef.gestion_achats.util.GmailAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.List;

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
    private ComboBox<Fournisseur> fournisseurComboBox;

    @FXML
    private TextArea emailSubject;

    @FXML
    private TextArea emailContent;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private Articletypeservice typeService;

    @Autowired
    private Sarticleservice sousArticleService;

    @Autowired
    private SSarticleservices sousSousArticleService;

    @Autowired
    private FournisseurService fournisseurService;

    @Autowired
    private ConsultationService consultationService;

    private DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public void initialize() {
        populateArticleComboBox();  // Populate article ComboBox
        populateFournisseurComboBox();  // Populate fournisseur ComboBox

        articleComboBox.setOnAction(event -> populateTypeComboBox());
        typeComboBox.setOnAction(event -> populateSousArticleComboBox());
        sousArticleComboBox.setOnAction(event -> populateSousSousArticleComboBox());

        // Set custom cell factories
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
                return null; // Not needed for this use case
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
                return null; // Not needed for this use case
            }
        });
    }

    private void populateArticleComboBox() {
        List<Article> articles = articleService.getAllArticles();
        if (articles != null) {
            articleComboBox.setItems(FXCollections.observableArrayList(articles));
        } else {
            articleComboBox.setItems(FXCollections.observableArrayList());
        }
        articleComboBox.getSelectionModel().clearSelection();
    }

    private void populateTypeComboBox() {
        Article selectedArticle = articleComboBox.getValue();
        if (selectedArticle != null) {
            List<Article_type> types = selectedArticle.getTypes();
            if (types != null) {
                typeComboBox.setItems(FXCollections.observableArrayList(types));
            } else {
                typeComboBox.setItems(FXCollections.observableArrayList());
            }
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
        Article_type selectedType = typeComboBox.getValue();
        if (selectedType != null) {
            List<sarticle> sousArticles = selectedType.getSousArticles();
            if (sousArticles != null) {
                sousArticleComboBox.setItems(FXCollections.observableArrayList(sousArticles));
            } else {
                sousArticleComboBox.setItems(FXCollections.observableArrayList());
            }
            sousArticleComboBox.getSelectionModel().clearSelection();
        } else {
            sousArticleComboBox.setItems(FXCollections.observableArrayList());
        }
    }

    private void populateSousSousArticleComboBox() {
        sarticle selectedSousArticle = sousArticleComboBox.getValue();
        if (selectedSousArticle != null) {
            List<ssarticle> sousSousArticles = selectedSousArticle.getSsarticles();
            if (sousSousArticles != null) {
                sousSousArticleComboBox.setItems(FXCollections.observableArrayList(sousSousArticles));
            } else {
                sousSousArticleComboBox.setItems(FXCollections.observableArrayList());
            }
            sousSousArticleComboBox.getSelectionModel().clearSelection();
        } else {
            sousSousArticleComboBox.setItems(FXCollections.observableArrayList());
        }
    }

    private void populateFournisseurComboBox() {
        List<Fournisseur> fournisseurs = fournisseurService.getAllFournisseurs();
        if (fournisseurs != null) {
            fournisseurComboBox.setItems(FXCollections.observableArrayList(fournisseurs));
        } else {
            fournisseurComboBox.setItems(FXCollections.observableArrayList());
        }
        fournisseurComboBox.getSelectionModel().clearSelection();
    }

    @FXML
    public void sendEmail() {
        // Get selected values
        Article selectedArticle = articleComboBox.getValue();
        sarticle selectedSousArticle = sousArticleComboBox.getValue();
        ssarticle selectedSousSousArticle = sousSousArticleComboBox.getValue();
        Fournisseur selectedFournisseur = fournisseurComboBox.getValue();

        // Create and save consultation
        consultation consultation = new consultation();
        consultation.setArticle(selectedArticle);
        consultation.setSArticle(selectedSousArticle);
        consultation.setSousSousArticle(selectedSousSousArticle);
        consultation.setFournisseur(selectedFournisseur);
        consultation.setDateConsultation(LocalDate.now());

        consultationService.saveConsultation(consultation);

        // Send email
        if (selectedFournisseur != null) {
            String to = selectedFournisseur.getEmail();  // Ensure the Fournisseur entity has an email field
            String subject = emailSubject.getText();
            String content = emailContent.getText();
            try {
                GmailAPI.sendEmail(to, subject, content);
            } catch (IOException | GeneralSecurityException | MessagingException e) {
                e.printStackTrace();
            }
        }
    }
}
