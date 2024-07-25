package net.youssef.gestion_achats.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import net.youssef.gestion_achats.entity.*;
import net.youssef.gestion_achats.services.*;
import net.youssef.gestion_achats.util.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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

    private Scene previousScene;

    public void initialize() {
        List<Article> articles = articleService.getAllArticles();
        articleComboBox.setItems(FXCollections.observableArrayList(articles));

        List<Article_type> types = typeService.getAllTypes();
        typeComboBox.setItems(FXCollections.observableArrayList(types));

        List<sarticle> sousArticles = sousArticleService.getAllSarticles();
        sousArticleComboBox.setItems(FXCollections.observableArrayList(sousArticles));

        List<ssarticle> sousSousArticles = sousSousArticleService.getAllSSarticles();
        sousSousArticleComboBox.setItems(FXCollections.observableArrayList(sousSousArticles));

        List<Fournisseur> fournisseurs = fournisseurService.getAllFournisseurs();
        fournisseurComboBox.setItems(FXCollections.observableArrayList(fournisseurs));
    }

    @FXML
    public void sendEmail() {
        Fournisseur fournisseur = fournisseurComboBox.getValue();
        String subject = emailSubject.getText();
        String content = emailContent.getText();

        // Use the EmailSender utility to send the email
        EmailSender.sendEmail(fournisseur.getEmail(), subject, content);

        // Optionally, save the consultation details
        consultation consultation = new consultation();
        consultation.setArticle(articleComboBox.getValue());
        consultation.setSousSousArticle(sousSousArticleComboBox.getValue());
        consultation.setFournisseur(fournisseur);
        consultationService.saveConsultation(consultation);
    }

    public void goback(ActionEvent actionEvent) {
        // Retrieve the stage from the event source
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Navigate back to the previous scene
        if (previousScene != null) {
            stage.setScene(previousScene);
        } else {
            System.out.println("No previous scene available");
        }
    }

    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }
}
