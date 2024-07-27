package net.youssef.gestion_achats.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.youssef.gestion_achats.entity.offre;
import net.youssef.gestion_achats.entity.Fournisseur;
import net.youssef.gestion_achats.entity.Article;
import net.youssef.gestion_achats.entity.sarticle;
import net.youssef.gestion_achats.entity.ssarticle;
import net.youssef.gestion_achats.services.OffreService;
import net.youssef.gestion_achats.services.FournisseurService;
import net.youssef.gestion_achats.services.ArticleService;
import net.youssef.gestion_achats.services.SSarticleservices;
import net.youssef.gestion_achats.services.Sarticleservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
@Controller

    public class AddOfferDialogController {
    @Autowired
    private DashboardController dashboardController;
        @FXML
        private TextField descriptionField;

        @FXML
        private TextField fournisseurField;

        @FXML
        private TextField articleField;

        @FXML
        private TextField sousArticleField;

        @FXML
        private TextField sousSousArticleField;

        @Autowired
        private OffreService offreService;

        @Autowired
        private FournisseurService fournisseurService;

        @Autowired
        private ArticleService articleService;

        @Autowired
        private Sarticleservice sArticleService;

        @Autowired
        private SSarticleservices ssArticleService;

        private OffreController offreController;

        public void setOffreController(OffreController offreController) {
            this.offreController = offreController;
        }

        @FXML
        private void saveOffer() {
            String description = descriptionField.getText();
            Fournisseur fournisseur = fournisseurService.findByName(fournisseurField.getText());
            Article article = articleService.findByName(articleField.getText());
            sarticle sousArticle = sArticleService.findByName(sousArticleField.getText());
            ssarticle sousSousArticle = ssArticleService.findByName(sousSousArticleField.getText());

            offre newOffre = offre.builder()
                    .description(description)
                    .fournisseur(fournisseur)
                    .article(article)
                    .sousArticle(sousArticle)
                    .sousSousArticle(sousSousArticle)
                    .build();

            offreService.saveOffre(newOffre);

            offreController.loadOffres();
            showAlert("Success", "Offer saved successfully!");

            // Load home view
            dashboardController.loadContent("/views/home.fxml");

        }

        @FXML
        private void cancel() {
            dashboardController.loadContent("/views/home.fxml");

        }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
