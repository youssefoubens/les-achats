package net.youssef.gestion_achats.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.youssef.gestion_achats.entity.Article;
import net.youssef.gestion_achats.entity.Article_type;
import net.youssef.gestion_achats.entity.sarticle;
import net.youssef.gestion_achats.entity.ssarticle;
import net.youssef.gestion_achats.services.ArticleService;
import net.youssef.gestion_achats.services.Articletypeservice;
import net.youssef.gestion_achats.services.Sarticleservice;
import net.youssef.gestion_achats.services.SSarticleservices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ManageProductsController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private Articletypeservice articleTypeService;
    @Autowired
    private Sarticleservice sArticleService;
    @Autowired
    private SSarticleservices ssArticleService;

    @FXML
    private TableView<Object> productTable;
    @FXML
    private TableColumn<Object, Long> idColumn;
    @FXML
    private TableColumn<Object, String> nameColumn;
    @FXML
    private TableColumn<Object, String> unityColumn;
    @FXML
    private TableColumn<Object, Integer> quantityColumn;
    @FXML
    private TableColumn<Object, Float> priceColumn;
    @FXML
    private TableColumn<Object, Float> totalPriceColumn;

    @FXML
    private TextField nameField;
    @FXML
    private TextField unityField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField totalPriceField;
    @FXML
    private TextField typeNameField;
    @FXML
    private ComboBox<Article_type> articleTypeComboBox;
    @FXML
    private ComboBox<Article> articleComboBox;
    @FXML
    private ComboBox<sarticle> sArticleComboBox;

    private ObservableList<Object> productList;
    private ObservableList<Article_type> articleTypeList;
    private ObservableList<Article> articleList;
    private ObservableList<sarticle> sArticleList;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        unityColumn.setCellValueFactory(new PropertyValueFactory<>("unity"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalprice"));

        productList = FXCollections.observableArrayList();
        productTable.setItems(productList);

        articleTypeList = FXCollections.observableArrayList(articleTypeService.getAllTypes());
        articleTypeList.addAll(articleTypeService.getAllTypes());
        articleTypeComboBox.setItems(articleTypeList);

        articleList = FXCollections.observableArrayList(articleService.getAllArticles());
        articleList.addAll(articleService.getAllArticles());
        articleComboBox.setItems(articleList);

        sArticleList = FXCollections.observableArrayList(sArticleService.getAllSarticles());
        sArticleList.addAll(sArticleService.getAllSarticles());
        sArticleComboBox.setItems(sArticleList);
    }

    @FXML
    public void addArticle() {
        String name = nameField.getText();
        String unity = unityField.getText();
        String quantityText = quantityField.getText();
        String priceText = priceField.getText();
        String totalPriceText = totalPriceField.getText();

        StringBuilder missingFields = new StringBuilder();

        if (name.isEmpty()) missingFields.append("Name\n");
        if (unity.isEmpty()) unity = null; // Allow unity to be null
        if (quantityText.isEmpty()) quantityText = null; // Allow quantity to be null
        if (priceText.isEmpty()) priceText = null; // Allow price to be null
        if (totalPriceText.isEmpty()) totalPriceText = null; // Allow total price to be null

        if (missingFields.length() > 0) {
            showAlert("Validation Error", "Please fill the following fields:\n" + missingFields.toString());
            return;
        }

        try {
            Article.ArticleBuilder articleBuilder = Article.builder()
                    .name(name);

            if (unity != null) {
                articleBuilder.unity(unity);
            }
            if (quantityText != null) {
                articleBuilder.quantity(Integer.parseInt(quantityText));
            }
            if (priceText != null) {
                articleBuilder.price(Float.parseFloat(priceText));
            }
            if (totalPriceText != null) {
                articleBuilder.totalprice(Float.parseFloat(totalPriceText));
            }

            Article newArticle = articleBuilder.build();
            articleService.saveArticle(newArticle);
            articleList.add(newArticle);
            productList.add(newArticle);
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numbers for Quantity, Price, and Total Price.");
        }
    }

    @FXML
    public void addArticleType() {
        String typeName = typeNameField.getText();
        Article selectedArticle = articleComboBox.getValue();

        if (typeName.isEmpty() || selectedArticle == null) {
            showAlert("Validation Error", "Please enter the type name and select an article.");
            return;
        }

        Article_type newType = Article_type.builder()
                .name(typeName)
                .article(selectedArticle)
                .build();

        articleTypeService.saveType(newType);
        articleTypeList.add(newType);
        articleTypeComboBox.setItems(articleTypeList);
        typeNameField.clear();
    }

    @FXML
    public void addSArticle() {
        String name = nameField.getText();
        String unity = unityField.getText();
        String quantityText = quantityField.getText();
        String priceText = priceField.getText();
        String totalPriceText = totalPriceField.getText();
        Article_type type = articleTypeComboBox.getValue();
        Article article = articleComboBox.getValue();

        StringBuilder missingFields = new StringBuilder();

        if (name.isEmpty()) missingFields.append("Name\n");
        if (unity.isEmpty()) missingFields.append("Unity\n");
        if (quantityText.isEmpty()) missingFields.append("Quantity\n");
        if (priceText.isEmpty()) missingFields.append("Price\n");
        if (totalPriceText.isEmpty()) missingFields.append("Total Price\n");
        if (article == null) missingFields.append("Article\n");

        if (missingFields.length() > 0) {
            showAlert("Validation Error", "Please fill the following fields:\n" + missingFields.toString());
            return;
        }

        try {
            sarticle newSArticle = sarticle.builder()
                    .name(name)
                    .unity(unity)
                    .quantity(Integer.parseInt(quantityText))
                    .price(Float.parseFloat(priceText))
                    .totalprice(Float.parseFloat(totalPriceText))
                    .article(article)
                    .type(type)
                    .build();
            sArticleService.saveSarticle(newSArticle);
            sArticleList.add(newSArticle);
            sArticleComboBox.setItems(sArticleList);
            productList.add(newSArticle);
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numbers for Quantity, Price, and Total Price.");
        }
    }

    @FXML
    public void addSSArticle() {
        String name = nameField.getText();
        String unity = unityField.getText();
        String quantityText = quantityField.getText();
        String priceText = priceField.getText();
        String totalPriceText = totalPriceField.getText();
        sarticle sArticle = sArticleComboBox.getValue();

        StringBuilder missingFields = new StringBuilder();

        if (name.isEmpty()) missingFields.append("Name\n");
        if (unity.isEmpty()) missingFields.append("Unity\n");
        if (quantityText.isEmpty()) missingFields.append("Quantity\n");
        if (priceText.isEmpty()) missingFields.append("Price\n");
        if (totalPriceText.isEmpty()) missingFields.append("Total Price\n");
        if (sArticle == null) missingFields.append("Sub-Article\n");

        if (missingFields.length() > 0) {
            showAlert("Validation Error", "Please fill the following fields:\n" + missingFields.toString());
            return;
        }

        try {
            ssarticle newSSArticle = ssarticle.builder()
                    .name(name)
                    .unity(unity)
                    .quantity(Integer.parseInt(quantityText))
                    .price(Float.parseFloat(priceText))
                    .totalprice(Float.parseFloat(totalPriceText))
                    .sarticle(sArticle)
                    .build();

            ssArticleService.saveSSarticle(newSSArticle);
            productList.add(newSSArticle);
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numbers for Quantity, Price, and Total Price.");
        }
    }

    @FXML
    public void modifyProduct() {
        Object selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            if (selectedProduct instanceof Article) {
                modifyArticle((Article) selectedProduct);
            } else if (selectedProduct instanceof Article_type) {
                modifyArticleType((Article_type) selectedProduct);
            } else if (selectedProduct instanceof sarticle) {
                modifySArticle((sarticle) selectedProduct);
            } else if (selectedProduct instanceof ssarticle) {
                modifySSArticle((ssarticle) selectedProduct);
            }
        }
    }

    private void modifyArticle(Article article) {
        // Your modification logic here
    }

    private void modifyArticleType(Article_type articleType) {
        // Your modification logic here
    }

    private void modifySArticle(sarticle sArticle) {
        // Your modification logic here
    }

    private void modifySSArticle(ssarticle ssArticle) {
        // Your modification logic here
    }

    private void clearFields() {
        nameField.clear();
        unityField.clear();
        quantityField.clear();
        priceField.clear();
        totalPriceField.clear();
        typeNameField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
