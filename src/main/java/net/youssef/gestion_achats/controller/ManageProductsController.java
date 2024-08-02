package net.youssef.gestion_achats.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import net.youssef.gestion_achats.entity.*;
import net.youssef.gestion_achats.entity.Separator;
import net.youssef.gestion_achats.services.*;
import org.apache.commons.codec.language.bm.Lang;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private BordereauService bordereauService;
    @FXML
    private TableView<Object> productTableView;
    @FXML
    private TableColumn<Object, String> numberColumn;
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
    @FXML
    private ProgressBar progressBar;
    private ObservableList<Article> articleList = FXCollections.observableArrayList();
    private ObservableList<Article_type> articleTypeList = FXCollections.observableArrayList();
    private ObservableList<sarticle> sArticleList = FXCollections.observableArrayList();
    private ObservableList<ssarticle> ssArticleList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        productTableView.setEditable(true);
        configureTableColumns();
        productTableView.setItems(FXCollections.observableArrayList());
        //refreshDataFromDatabase(); // Load data from the database on startup
    }

    private void configureTableColumns() {
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("N"));
        numberColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        unityColumn.setCellValueFactory(new PropertyValueFactory<>("unity"));
        unityColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantityColumn.setOnEditCommit((TableColumn.CellEditEvent<Object, Integer> event) -> {
            Object item = event.getRowValue();
            if (item instanceof Article) {
                ((Article) item).setQuantity(event.getNewValue());
                // Update database
                articleService.saveArticle((Article) item);
            } else if (item instanceof sarticle) {
                ((sarticle) item).setQuantity(event.getNewValue());
                // Update database
                sArticleService.saveSarticle((sarticle) item);
            }
        });

        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        priceColumn.setOnEditCommit((TableColumn.CellEditEvent<Object, Float> event) -> {
            Object item = event.getRowValue();
            if (item instanceof Article) {
                ((Article) item).setPrice(event.getNewValue());
                // Update database
                articleService.saveArticle((Article) item);
            } else if (item instanceof sarticle) {
                ((sarticle) item).setPrice(event.getNewValue());
                // Update database
                sArticleService.saveSarticle((sarticle) item);
            }
        });

        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalprice"));
        totalPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        totalPriceColumn.setOnEditCommit((TableColumn.CellEditEvent<Object, Float> event) -> {
            Object item = event.getRowValue();
            if (item instanceof Article) {
                ((Article) item).setTotalprice(event.getNewValue());
                // Update database
                articleService.saveArticle((Article) item);
            } else if (item instanceof sarticle) {
                ((sarticle) item).setTotalprice(event.getNewValue());
                // Update database
                sArticleService.saveSarticle((sarticle) item);
            }
        });
    }

    private void refreshDataFromDatabase() {
        productTableView.setRowFactory(tableView -> new TableRow<>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    System.out.println("empty");
                }else if (item instanceof Separator) {
                    setStyle("-fx-background-color: lightgray; -fx-font-style: italic;");}
                    else {
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
        });

        Platform.runLater(() -> {
            articleTypeList.setAll(articleTypeService.getAllTypes());
            articleList.setAll(articleService.getAllArticles());
            sArticleList.setAll(sArticleService.getAllSarticles());
            ssArticleList.setAll(ssArticleService.getAllSSarticles());

            articleComboBox.setItems(articleList);
            articleTypeComboBox.setItems(articleTypeList);
            sArticleComboBox.setItems(sArticleList);

            loadData();
        });
    }

    private void loadData() {
        ObservableList<Object> items = FXCollections.observableArrayList();

        // Loop through all articles
        for (Article article : articleList) {
            items.add(article);

            // Check if the article has types
            if (article.getTypes() != null) {
                // Loop through all article types for the current article
                for (Article_type type : article.getTypes()) {
                    items.add(type);

                    // Check if the article type has sub-articles
                    if (type.getSousArticles() != null) {
                        // Loop through all sub-articles for the current type
                        for (sarticle sArticle : type.getSousArticles()) {
                            items.add(sArticle);

                            // Check if the sub-article has sub-sub-articles
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

            // Add sub-articles for the current article (if no type)
            if (article.getSarticles() != null) {
                for (sarticle sArticle : article.getSarticles()) {
                    items.add(sArticle);

                    // Add sub-sub-articles for the current sub-article
                    if (sArticle.getSsarticles() != null) {
                        for (ssarticle ssArticle : sArticle.getSsarticles()) {
                            items.add(ssArticle);
                        }
                    }
                }
            }
            items.add(new Separator("SOUS TOTAL"));
        }

        productTableView.setItems(items);
    }



    @FXML
    private void handleUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            System.out.println("Processing file: " + file.getAbsolutePath());
            BORDEREAU newBordereau = new BORDEREAU();
            bordereauService.saveBordereau(newBordereau);
            processFile(file,newBordereau);
        }
    }


    private void loadDataByBordereau(BORDEREAU bordereau) {
        if (bordereau == null) {
            productTableView.setItems(FXCollections.observableArrayList());
            return;
        }

        ObservableList<Object> items = FXCollections.observableArrayList();
        List<Article> articles = articleService.getArticlesByBordereau(bordereau);

        for (Article article : articles) {
            items.add(article);

            if (article.getTypes() != null) {
                for (Article_type type : article.getTypes()) {
                    items.add(type);

                    if (type.getSousArticles() != null) {
                        for (sarticle sArticle : type.getSousArticles()) {
                            items.add(sArticle);

                            if (sArticle.getSsarticles() != null) {
                                for (ssarticle ssArticle : sArticle.getSsarticles()) {
                                    items.add(ssArticle);
                                }
                            }
                        }
                    }
                }
            }

            if (article.getSarticles() != null) {
                for (sarticle sArticle : article.getSarticles()) {
                    items.add(sArticle);

                    if (sArticle.getSsarticles() != null) {
                        for (ssarticle ssArticle : sArticle.getSsarticles()) {
                            items.add(ssArticle);
                        }
                    }
                }
            }

            items.add(new Separator("SOUS TOTAL"));
        }

        productTableView.setItems(items);
    }

    @FXML
    private void selectBordereau() {
        List<BORDEREAU> bordereaux = bordereauService.getAllBordereaux();

        Dialog<BORDEREAU> dialog = new Dialog<>();
        dialog.setTitle("Select Bordereau");

        ComboBox<BORDEREAU> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(bordereaux); // Add all bordereaux to the ComboBox
        dialog.getDialogPane().setContent(comboBox);

        ButtonType selectButtonType = new ButtonType("Select", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(selectButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == selectButtonType) {
                return comboBox.getSelectionModel().getSelectedItem();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(selectedBordereau -> {
            System.out.println("Selected Bordereau: " + selectedBordereau);
            loadDataByBordereau(selectedBordereau);
        });
    }

    @FXML
    public void deleteBordereauAndRelatedEntities(ActionEvent actionEvent) {
        // Show a dialog to select the BORDEREAU to delete
        List<BORDEREAU> bordereaux = bordereauService.getAllBordereaux();

        Dialog<BORDEREAU> dialog = new Dialog<>();
        dialog.setTitle("Select Bordereau to Delete");

        ComboBox<BORDEREAU> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(bordereaux);
        dialog.getDialogPane().setContent(comboBox);

        ButtonType deleteButtonType = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(deleteButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == deleteButtonType) {
                return comboBox.getSelectionModel().getSelectedItem();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(selectedBordereau -> {
            if (selectedBordereau != null) {
                // Show progress bar
                progressBar.setVisible(true);
                progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);

                // Create and run a background task for deletion
                Task<Void> task = new Task<>() {
                    @Override
                    protected Void call() {
                        deleteRelatedEntities(selectedBordereau);
                        return null;
                    }

                    @Override
                    protected void succeeded() {
                        super.succeeded();
                        // Hide progress bar and refresh data
                        progressBar.setVisible(false);
                        bordereauService.deleteBordereau(selectedBordereau);
                        refreshDataFromDatabase();
                    }

                    @Override
                    protected void failed() {
                        super.failed();
                        // Hide progress bar and handle error
                        progressBar.setVisible(false);
                       // showError("Deletion failed.");
                    }
                };

                // Run the task in a new thread
                new Thread(task).start();
            }
        });
    }

    private void deleteRelatedEntities(BORDEREAU bordereau) {
        List<Article> articles = articleService.getArticlesByBordereau(bordereau);

        for (Article article : articles) {
            List<Article_type> types = article.getTypes();
            for (Article_type type : types) {
                List<sarticle> sArticles = type.getSousArticles();
                for (sarticle sArticle : sArticles) {
                    List<ssarticle> ssArticles = sArticle.getSsarticles();
                    for (ssarticle ssArticle : ssArticles) {
                        ssArticleService.deleteSSarticle(ssArticle.getId());
                    }
                    sArticleService.deleteSarticle(sArticle.getId());
                }
                articleTypeService.deleteType(type.getId());
            }

            List<sarticle> sArticles = article.getSarticles();
            for (sarticle sArticle : sArticles) {
                List<ssarticle> ssArticles = sArticle.getSsarticles();
                for (ssarticle ssArticle : ssArticles) {
                    ssArticleService.deleteSSarticle(ssArticle.getId());
                }
                sArticleService.deleteSarticle(sArticle.getId());
            }

            articleService.deleteArticle(article.getId());
        }
    }

    private void processFile(File file,BORDEREAU bordereau) {
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            List<Article> articles = new ArrayList<>();
            List<Article_type> articleTypes = new ArrayList<>();
            List<sarticle> sArticles = new ArrayList<>();
            List<ssarticle> ssArticles = new ArrayList<>();

            Article currentArticle = null;
            Article_type currentArticleType = null;
            sarticle currentSArticle = null;

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    System.out.println("==========================================");
                    continue; // Skip header row
                }

                Cell firstCell = row.getCell(0);
                String cellValue = firstCell != null ? getCellValue(firstCell).trim() : "";

                // Handle "sous total"
                if (cellValue.equalsIgnoreCase("SOUS TOTAL")) {
                    currentArticle = null;
                    currentArticleType = null;
                    currentSArticle = null;
                    continue; // Move to next row
                }

                // Convert the cell value to a number for easier processing
                Double cellNumber = getNumericCellValue(firstCell);

                // Handle Article
                if (cellNumber != null && cellNumber.intValue() > 0) {
                    System.out.println("Processing Article: " + cellValue);
                    currentArticle = Article.builder()
                            .N(cellValue)
                            .name(getCellValue(row.getCell(1)))
                            .unity(getCellValue(row.getCell(2)))
                            .quantity((int) getNumericCellValue(row.getCell(3)))
                            .price((float) getNumericCellValue(row.getCell(4)))
                            .totalprice((float) getNumericCellValue(row.getCell(5)))
                            .bordereau(bordereau)
                            .build();
                    articles.add(currentArticle);
                    // Reset currentArticleType and currentSArticle since we're starting a new article
                    currentArticleType = null;
                    currentSArticle = null;
                    continue;
                }

                // Handle ArticleType
                if (firstCell == null || cellValue.isEmpty()) {
                    if (currentArticle != null ) {
                        System.out.println("Processing ArticleType for Article: " + (currentArticle != null ? currentArticle.getN() : "null"));
                        currentArticleType = Article_type.builder()
                                .name(getCellValue(row.getCell(1)))
                                .article(currentArticle)
                                .build();
                        articleTypes.add(currentArticleType);
                        // Reset currentSArticle since we're starting a new article type
                        currentSArticle = null;
                        continue;
                    }
                }

                // Handle SArticle
                if (cellValue.chars().filter(ch -> ch == '.').count() == 1) {
                    System.out.println("Processing SArticle: " + cellValue);
                    currentSArticle = sarticle.builder()
                            .N(cellValue)
                            .name(getCellValue(row.getCell(1)))
                            .type(currentArticleType)
                            .article(currentArticle)
                            .quantity((int) getNumericCellValue(row.getCell(3)))
                            .price((float) getNumericCellValue(row.getCell(4)))
                            .totalprice((float) getNumericCellValue(row.getCell(5)))
                            .build();
                    sArticles.add(currentSArticle);
                    continue;
                }

                // Handle SSArticle
                if (cellValue.chars().filter(ch -> ch == '.').count() == 2) {
                    System.out.println("Processing SSArticle: " + cellValue);
                    ssarticle newSSArticle = ssarticle.builder()
                            .N(cellValue)
                            .name(getCellValue(row.getCell(1)))
                            .sarticle(currentSArticle)
                            .quantity((int) getNumericCellValue(row.getCell(3)))
                            .price((float) getNumericCellValue(row.getCell(4)))
                            .totalprice((float) getNumericCellValue(row.getCell(5)))
                            .build();
                    ssArticles.add(newSSArticle);
                }
            }

            // Save entities to the database
            for (Article article : articles) {
                articleService.saveArticle(article);
            }
            for (Article_type type : articleTypes) {
                articleTypeService.saveType(type);
            }
            for (sarticle sArticle : sArticles) {
                sArticleService.saveSarticle(sArticle);
            }
            for (ssarticle ssArticle : ssArticles) {
                ssArticleService.saveSSarticle(ssArticle);
            }

            // Refresh UI
            Platform.runLater(() -> {
                refreshDataFromDatabase();
                // Optionally, you can select the first item in the TableView if needed
                if (!articleList.isEmpty()) {
                    productTableView.getSelectionModel().selectFirst();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("File Error", "Could not read the file: " + file.getName());
        }
    }

    private double getNumericCellValue(Cell cell) {
        if (cell != null && cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        }
        return 0; // Default value for non-numeric cells
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void ModifierBordereau(ActionEvent actionEvent) {
        System.out.println("modifier");
    }
}
