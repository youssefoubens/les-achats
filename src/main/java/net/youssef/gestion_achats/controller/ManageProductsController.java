package net.youssef.gestion_achats.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import net.youssef.gestion_achats.entity.*;
import net.youssef.gestion_achats.services.*;
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
    private TreeTableView<Object> productTreeTable;
    @FXML
    private TreeTableColumn<Object, Long> idColumn;
    @FXML
    private TreeTableColumn<Object, String> nameColumn;
    @FXML
    private TreeTableColumn<Object, String> unityColumn;
    @FXML
    private TreeTableColumn<Object, Integer> quantityColumn;
    @FXML
    private TreeTableColumn<Object, Float> priceColumn;
    @FXML
    private TreeTableColumn<Object, Float> totalPriceColumn;

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

    private ObservableList<Article_type> articleTypeList;
    private ObservableList<Article> articleList;
    private ObservableList<sarticle> sArticleList;
    private ObservableList<ssarticle> ssArticleList;

    @FXML
    public void initialize() {
        // Set up TreeTableColumn bindings
        idColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        unityColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("unity"));
        quantityColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("price"));
        totalPriceColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("totalprice"));

        // Set up ComboBoxes
        articleTypeList = FXCollections.observableArrayList(articleTypeService.getAllTypes());
        articleList = FXCollections.observableArrayList(articleService.getAllArticles());
        sArticleList = FXCollections.observableArrayList(sArticleService.getAllSarticles());
        ssArticleList = FXCollections.observableArrayList();
        articleComboBox.setItems(articleList);

        // Add listener to articleComboBox to update articleTypeComboBox items
        articleComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Selected Article: " + newValue.getName());
                updateArticleTypeComboBox(newValue);
            }
        });

        // Load data into the TreeTableView
        loadData();
    }

    private void updateArticleTypeComboBox(Article selectedArticle) {
        // Filter article types based on the selected article
        ObservableList<Article_type> filteredArticleTypes = FXCollections.observableArrayList();
        for (Article_type type : articleTypeList) {
            if (type.getArticle().equals(selectedArticle)) {
                filteredArticleTypes.add(type);
            }
        }
        articleTypeComboBox.setItems(filteredArticleTypes);

        // Optionally update another ComboBox or list for sarticle
        ObservableList<sarticle> filteredSArticles = FXCollections.observableArrayList();
        for (sarticle sArticle : sArticleList) {
            if (sArticle.getArticle().equals(selectedArticle)) {
                filteredSArticles.add(sArticle);
            }
        }
        sArticleComboBox.setItems(filteredSArticles);
    }

    private void loadData() {
        TreeItem<Object> rootItem = new TreeItem<>(new Object());
        productTreeTable.setRoot(rootItem);
        productTreeTable.setShowRoot(false);

        // Loop through all articles
        for (Article article : articleList) {
            TreeItem<Object> articleItem = new TreeItem<>(article);
            rootItem.getChildren().add(articleItem);

            if (article.getTypes() != null) {
                // Loop through all article types for the current article
                for (Article_type type : article.getTypes()) {
                    TreeItem<Object> typeItem = new TreeItem<>(type);
                    articleItem.getChildren().add(typeItem);

                    if (type.getSousArticles() != null) {
                        // Loop through all sub-articles for the current type
                        for (sarticle sArticle : type.getSousArticles()) {
                            TreeItem<Object> sArticleItem = new TreeItem<>(sArticle);
                            typeItem.getChildren().add(sArticleItem);

                            if (sArticle.getSsarticles() != null) {
                                // Loop through all sub-sub-articles for the current sub-article
                                for (ssarticle ssArticle : sArticle.getSsarticles()) {
                                    TreeItem<Object> ssArticleItem = new TreeItem<>(ssArticle);
                                    sArticleItem.getChildren().add(ssArticleItem);
                                }
                            }
                        }
                    }
                }
            }
        }

        productTreeTable.refresh();
    }

    @FXML
    public void addArticle() {
        String name = nameField.getText();
        String unity = unityField.getText();
        String quantityText = quantityField.getText();
        String priceText = priceField.getText();
        String totalPriceText = totalPriceField.getText();

        if (name.isEmpty() || quantityText.isEmpty()) {
            showAlert("Validation Error", "Name and Quantity are required.");
            return;
        }

        try {
            Article.ArticleBuilder articleBuilder = Article.builder()
                    .name(name)
                    .unity(unity)
                    .quantity(Integer.parseInt(quantityText));

            if (!priceText.isEmpty()) {
                articleBuilder.price(Float.parseFloat(priceText));
            }
            if (!totalPriceText.isEmpty()) {
                articleBuilder.totalprice(Float.parseFloat(totalPriceText));
            }

            Article newArticle = articleBuilder.build();
            articleService.saveArticle(newArticle);
            articleList.add(newArticle);
            loadData();
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
        updateArticleTypeComboBox(selectedArticle); // Update the ComboBox to include the new type
        typeNameField.clear();
        clearFields();
        refreshDataFromDatabase();
        loadData();


    }
    private void refreshDataFromDatabase() {
        articleTypeList.clear();
        articleTypeList.addAll(articleTypeService.getAllTypes());

        articleList.clear();
        articleList.addAll(articleService.getAllArticles());

        sArticleList.clear();
        sArticleList.addAll(sArticleService.getAllSarticles());
        ssArticleList.clear();
        ssArticleList.addAll(ssArticleService.getAllSSarticles());
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

        if (name.isEmpty() || unity.isEmpty() || quantityText.isEmpty() || article == null) {
            showAlert("Validation Error", "Please fill all required fields.");
            return;
        }

        try {
            sarticle newSArticle = sarticle.builder()
                    .name(name)
                    .unity(unity)
                    .quantity(Integer.parseInt(quantityText))
                    .price(!priceText.isEmpty() ? Float.parseFloat(priceText) : 0)
                    .totalprice(!totalPriceText.isEmpty() ? Float.parseFloat(totalPriceText) : 0)
                    .article(article)
                    .type(type)
                    .build();

            sArticleService.saveSarticle(newSArticle);
            sArticleList.add(newSArticle);
            sArticleComboBox.setItems(sArticleList);

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numbers for Quantity, Price, and Total Price.");
        }
        refreshDataFromDatabase();
        loadData();
        clearFields();
    }

    @FXML
    public void addSSArticle() {
        String name = nameField.getText();
        String unity = unityField.getText();
        String quantityText = quantityField.getText();
        String priceText = priceField.getText();
        String totalPriceText = totalPriceField.getText();
        sarticle sArticle = sArticleComboBox.getValue();

        if (name.isEmpty() || unity.isEmpty() || quantityText.isEmpty() || sArticle == null) {
            showAlert("Validation Error", "Please fill all required fields.");
            return;
        }

        try {
            ssarticle newSSArticle = ssarticle.builder()
                    .name(name)
                    .unity(unity)
                    .quantity(Integer.parseInt(quantityText))
                    .price(!priceText.isEmpty() ? Float.parseFloat(priceText) : 0)
                    .totalprice(!totalPriceText.isEmpty() ? Float.parseFloat(totalPriceText) : 0)
                    .sarticle(sArticle)
                    .build();

            ssArticleService.saveSSarticle(newSSArticle);
            refreshDataFromDatabase();
            loadData();
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numbers for Quantity, Price, and Total Price.");
        }
    }

    @FXML
    public void modifyProduct() {
        TreeItem<Object> selectedItem = productTreeTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Object selectedProduct = selectedItem.getValue();
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
        // Implement logic to modify Article
    }

    private void modifyArticleType(Article_type articleType) {
        // Implement logic to modify Article_type
    }

    private void modifySArticle(sarticle sArticle) {
        // Implement logic to modify sarticle
    }

    private void modifySSArticle(ssarticle ssArticle) {
        // Implement logic to modify ssarticle
    }

    @FXML
    public void deleteProduct() {
        TreeItem<Object> selectedItem = productTreeTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            Object selectedProduct = selectedItem.getValue();
            if (selectedProduct instanceof Article) {
                articleService.deleteArticle(((Article) selectedProduct).getId());
                articleList.remove(selectedProduct);
            } else if (selectedProduct instanceof Article_type) {
                articleTypeService.deleteType(((Article_type) selectedProduct).getId());
                articleTypeList.remove(selectedProduct);
            } else if (selectedProduct instanceof sarticle) {
                sArticleService.deleteSarticle(((sarticle) selectedProduct).getId());
                sArticleList.remove(selectedProduct);
            } else if (selectedProduct instanceof ssarticle) {
                ssArticleService.deleteSSarticle(((ssarticle) selectedProduct).getId());
                loadData();
            }
        }
    }

    private void clearFields() {
        nameField.clear();
        unityField.clear();
        quantityField.clear();
        priceField.clear();
        totalPriceField.clear();
        typeNameField.clear();
        articleComboBox.getSelectionModel().clearSelection();
        articleTypeComboBox.getSelectionModel().clearSelection();
        sArticleComboBox.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
