package net.youssef.gestion_achats.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import net.youssef.gestion_achats.AjouteEntrepriseResult;
import net.youssef.gestion_achats.entity.*;
import net.youssef.gestion_achats.services.ArticleService;
import net.youssef.gestion_achats.services.*;

import net.youssef.gestion_achats.services.AjouteEntrepriseService;
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
public class BordereauEntreprise {

    @FXML
    private TableView<AjouteEntrepriseResult> productTableView;
    @FXML
    private TableColumn<AjouteEntrepriseResult, String> nameColumn;
    @FXML
    private TableColumn<AjouteEntrepriseResult, String> familleColumn;
    @FXML
    private TableColumn<AjouteEntrepriseResult, String> nBrdVeColumn;
    @FXML
    private TableColumn<AjouteEntrepriseResult, String> nBrdColumn;
    @FXML
    private TableColumn<AjouteEntrepriseResult, String> designationColumn;
    @FXML
    private TableColumn<AjouteEntrepriseResult, String> unityColumn;
    @FXML
    private TableColumn<AjouteEntrepriseResult, Integer> quantityColumn;
    @FXML
    private Button uploadButton;
    @FXML
    private Button saveButton;
    @FXML
    private ProgressBar progressBar;

    @Autowired
    private AjouteEntrepriseService ajouteEntrepriseService;

    @Autowired
    private Sarticleservice sarticleService;

    @Autowired
    private SSarticleservices ssarticleService;

    @Autowired
    private ArticleService articleService;

    private List<AjouteEntreprise> tempItems = new ArrayList<>();
    private String bordereauNumber0;

    public void setBordereauNumber(String bordereauNumber) {
        this.bordereauNumber0 = bordereauNumber;
        // Load data or perform actions based on the bordereauNumber
    }

    @FXML
    private void initialize() {
        initializeTableColumns();
        saveButton.setOnAction(event -> saveItems());
    }

    private void initializeTableColumns() {
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDs()));
        familleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAjouteEntreprise().getFamille()));
        nBrdVeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAjouteEntreprise().getN()));
        nBrdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNbrd()));
        designationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAjouteEntreprise().getDescription()));
        unityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAjouteEntreprise().getUnity()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAjouteEntreprise().getQuantity()).asObject());
    }



    @FXML
    private void handleUpload() {
        if (articleService == null || ajouteEntrepriseService == null) {
            System.err.println("One or more services are not initialized.");
            return;
        }

        File file = showFileChooser();
        if (file != null) {
            processExcelFile(file);
        }
    }

    private File showFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        Window window = uploadButton.getScene().getWindow();
        return fileChooser.showOpenDialog(window);
    }

    private void processExcelFile(File file) {
        tempItems.clear(); // Clear previous items
        List<AjouteEntrepriseResult> allItems = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row
                AjouteEntrepriseResult result = createAjouteEntreprise(row);
                AjouteEntreprise ajout = result.getAjouteEntreprise();
                if (ajout != null) {
                    allItems.add(result);
                    // Add to tempItems if the condition for saving is met
                    if (!ajout.getN().equals(getCellValue(row.getCell(3)))) {
                        tempItems.add(ajout);
                    } else {
                        updateExistingEntity(getCellValue(row.getCell(2)), ajout.getFamille());
                    }
                }
            }

            // Refresh TableView
            productTableView.getItems().setAll(allItems);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error appropriately
        }
    }

    private void updateExistingEntity(String nBrd, String famille) {
        String[] parts = nBrd.split("\\.");
        String bordereauNumber = "Bordereau" + bordereauNumber0;

        if (parts.length == 2 && parts[1].equals("0")) {
            List<Article> articles = articleService.findByN(nBrd);
            for (Article article : articles) {
                if (bordereauNumber.equals(String.valueOf(article.getBordereau()))) {
                    article.setFamille(famille);
                    articleService.saveArticle(article);
                }
            }
        } else if (parts.length == 2) {
            List<sarticle> sarticles = sarticleService.findByN(nBrd);
            for (sarticle sarticle : sarticles) {
                if (bordereauNumber.equals(String.valueOf(sarticle.getArticle().getBordereau()))) {
                    sarticle.setFamille(famille);
                    sarticleService.saveSarticle(sarticle);
                }
            }
        } else if (parts.length == 3) {
            List<ssarticle> ssarticles = ssarticleService.findByN(nBrd);
            for (ssarticle ssarticle : ssarticles) {
                if (bordereauNumber.equals(String.valueOf(ssarticle.getSarticle().getArticle().getBordereau()))) {
                    ssarticle.setFamille(famille);
                    ssarticleService.saveSSarticle(ssarticle);
                }
            }
        }
    }


    private AjouteEntrepriseResult createAjouteEntreprise(Row row) {
        String ds = getCellValue(row.getCell(0));
        System.out.println("####################################3");
        System.out.println("####################################3");
        System.out.println("####################################3"+ds);
        System.out.println("####################################3");

        String famille = getCellValue(row.getCell(1));
        String nBrdVe = getCellValue(row.getCell(2));
        String nBrd = getCellValue(row.getCell(3));
        String designation = getCellValue(row.getCell(4));
        String unity = getCellValue(row.getCell(5));
        int quantity = parseQuantity(getCellValue(row.getCell(6)));

        AjouteEntreprise ajout = new AjouteEntreprise();
        ajout.setFamille(famille);
        ajout.setQuantity(quantity);
        ajout.setUnity(unity);
        ajout.setDescription(designation);
        ajout.setPrice(0); // Assuming price is 0; modify as needed
        ajout.setTotalprice(quantity * 0);
        ajout.setN(nBrdVe);
        ajout.setNumbordereau(bordereauNumber0);
        linkEntities(ajout, nBrdVe);

        // Return the result encapsulating AjouteEntreprise, ds, and nBrd
        return new AjouteEntrepriseResult(ajout, ds, nBrd);
    }

    private int parseQuantity(String quantityString) {
        int quantity = 0;
        if (!quantityString.isEmpty()) {
            try {
                quantity = Integer.parseInt(quantityString);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format for quantity: " + quantityString);
            }
        }
        return quantity;
    }

    private void linkEntities(AjouteEntreprise ajouteEntreprise, String nBrdVe) {
        String nBrdVe1;
        String bordereauNumber = "Bordereau" + bordereauNumber0;
        String[] parts = nBrdVe.split("\\.");

        if (parts.length == 2 && parts[1].equals("0")) {
            nBrdVe1 = parts[0];

            List<Article> articles = articleService.findByN(nBrdVe1);
            for (Article article : articles) {
                if (bordereauNumber.equals(String.valueOf(article.getBordereau()))) {
                    ajouteEntreprise.setArticle(article);
                    break; // Exit loop once a matching article is found
                }
            }

            if (ajouteEntreprise.getArticle() == null) {
                // Handle the case where no matching article is found
                System.err.println("No matching article found or Bordereau does not match for: " + nBrdVe);
            }
        } else if (parts.length == 3) {
            nBrdVe1 = parts[0] + "." + parts[1];

            List<sarticle> sarticles = sarticleService.findByN(nBrdVe1);
            for (sarticle sarticle : sarticles) {
                if (bordereauNumber.equals(String.valueOf(sarticle.getArticle().getBordereau()))) {
                    ajouteEntreprise.setSarticle(sarticle);
                    break; // Exit loop once a matching sarticle is found
                }
            }

            if (ajouteEntreprise.getSarticle() == null) {
                // Handle the case where no matching sarticle is found
                System.err.println("No matching sarticle found or Bordereau does not match for: " + nBrdVe);
            }
        } else if (parts.length == 4) {
            nBrdVe1 = parts[0] + "." + parts[1] + "." + parts[2];

            List<ssarticle> ssarticles = ssarticleService.findByN(nBrdVe1);
            for (ssarticle ssarticle : ssarticles) {
                if (bordereauNumber.equals(String.valueOf(ssarticle.getSarticle().getArticle().getBordereau()))) {
                    ajouteEntreprise.setSsarticle(ssarticle);
                    break; // Exit loop once a matching ssarticle is found
                }
            }

            if (ajouteEntreprise.getSsarticle() == null) {
                // Handle the case where no matching ssarticle is found
                System.err.println("No matching ssarticle found or Bordereau does not match for: " + nBrdVe);
            }
        }
    }

    @FXML
    private void saveItems() {
        for (AjouteEntreprise ajout : tempItems) {
            ajouteEntrepriseService.save(ajout);
        }
        // Optionally clear the list and refresh the TableView
        tempItems.clear();
        productTableView.getItems().clear();
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
}
