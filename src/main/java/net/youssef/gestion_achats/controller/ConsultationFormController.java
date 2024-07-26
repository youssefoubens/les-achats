package net.youssef.gestion_achats.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
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

    private DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

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


        // Optionally, save the consultation details
        consultation consultation = new consultation();
        consultation.setArticle(articleComboBox.getValue());
        consultation.setSousSousArticle(sousSousArticleComboBox.getValue());
       // consultation.setFournisseur(fournisseur);
        consultationService.saveConsultation(consultation);
    }

    @FXML
    public void goback(ActionEvent actionEvent) {

        dashboardController.goback(actionEvent);
    }
}
