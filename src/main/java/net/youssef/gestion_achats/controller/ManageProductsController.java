package net.youssef.gestion_achats.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
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
    private TableView<Object> productTableView;
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
    private ComboBox<Article> articleComboBox;
    @FXML
    private ComboBox<Article_type> articleTypeComboBox;
    @FXML
    private ComboBox<sarticle> sArticleComboBox;

    private ObservableList<Article> articleList = FXCollections.observableArrayList();
    private ObservableList<Article_type> articleTypeList = FXCollections.observableArrayList();
    private ObservableList<sarticle> sArticleList = FXCollections.observableArrayList();
    private ObservableList<ssarticle> ssArticleList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up TableColumn bindings
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        unityColumn.setCellValueFactory(new PropertyValueFactory<>("unity"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalprice"));

        // Set up cell factory to handle hierarchical data
        productTableView.setRowFactory(new Callback<TableView<Object>, TableRow<Object>>() {
            @Override
            public TableRow<Object> call(TableView<Object> tableView) {
                return new TableRow<>() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setStyle("");
                        } else {
                            if (item instanceof Article) {
                                setStyle("-fx-background-color: lightblue;");
                            } else if (item instanceof Article_type) {
                                setStyle("-fx-background-color: lightgreen;");
                            } else if (item instanceof sarticle) {
                                setStyle("-fx-background-color: lightyellow;");
                            } else if (item instanceof ssarticle) {
                                setStyle("-fx-background-color: lightcoral;");
                            }
                        }
                    }
                };
            }
        });

        // Load and bind data
        refreshDataFromDatabase();

        loadData();

        // Bind ComboBox events
        articleComboBox.setOnAction(event -> onArticleSelection());
    }

    private void refreshDataFromDatabase() {
        articleTypeList.setAll(articleTypeService.getAllTypes());
        articleList.setAll(articleService.getAllArticles());
        sArticleList.setAll(sArticleService.getAllSarticles());
        ssArticleList.setAll(ssArticleService.getAllSSarticles());

        // Bind data to ComboBoxes
        articleComboBox.setItems(articleList);
        articleTypeComboBox.setItems(articleTypeList);
        sArticleComboBox.setItems(sArticleList);

        // Set default prompt text

    }

    private void updateArticleTypeComboBox(Article selectedArticle) {
        if (selectedArticle != null) {
            ObservableList<Article_type> filteredArticleTypes = FXCollections.observableArrayList();
            for (Article_type type : articleTypeList) {
                if (type.getArticle().equals(selectedArticle)) {
                    filteredArticleTypes.add(type);
                }
            }
            articleTypeComboBox.setItems(filteredArticleTypes);
        }
    }

    private void updateSArticleComboBox(Article selectedArticle) {
        if (selectedArticle != null) {
            ObservableList<sarticle> filteredSArticles = FXCollections.observableArrayList();
            for (sarticle sArticle : sArticleList) {
                if (sArticle.getArticle().equals(selectedArticle)) {
                    filteredSArticles.add(sArticle);
                }
            }
            sArticleComboBox.setItems(filteredSArticles);
        }
    }

    @FXML
    public void onArticleSelection() {
        Article selectedArticle = articleComboBox.getValue();
        updateArticleTypeComboBox(selectedArticle);
        updateSArticleComboBox(selectedArticle);
    }

    private void loadData() {
        ObservableList<Object> items = FXCollections.observableArrayList();

        // Loop through all articles
        for (Article article : articleList) {
            items.add(article);

            if (article.getTypes() != null) {
                // Loop through all article types for the current article
                for (Article_type type : article.getTypes()) {
                    items.add(type);

                    if (type.getSousArticles() != null) {
                        // Loop through all sub-articles for the current type
                        for (sarticle sArticle : type.getSousArticles()) {
                            items.add(sArticle);

                            if (sArticle.getSsarticles() != null) {
                                // Loop through all sub-sub-articles for the current sub-article
                                for (ssarticle ssArticle : sArticle.getSsarticles()) {
                                    items.add(ssArticle);
                                }
                            }
                        }
                    }
                }
            }
        }

        productTableView.setItems(items);
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
    }

    @FXML
    public void addSSArticle() {
        String name = nameField.getText();
        String unity = unityField.getText();
        String quantityText = quantityField.getText();
        String priceText = priceField.getText();
        String totalPriceText = totalPriceField.getText();
        sarticle selectedSArticle = sArticleComboBox.getValue();

        if (name.isEmpty() || unity.isEmpty() || quantityText.isEmpty() || selectedSArticle == null) {
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
                    .sarticle(selectedSArticle)
                    .build();

            ssArticleService.saveSSarticle(newSSArticle);
            ssArticleList.add(newSSArticle);

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numbers for Quantity, Price, and Total Price.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        nameField.clear();
        unityField.clear();
        quantityField.clear();
        priceField.clear();
        totalPriceField.clear();
        typeNameField.clear();

    }

    public void modifyProduct(ActionEvent actionEvent) {
        System.out.println("modify");
    }

    public void deleteProduct(ActionEvent actionEvent) {
        System.out.println("delete");
    }
}
