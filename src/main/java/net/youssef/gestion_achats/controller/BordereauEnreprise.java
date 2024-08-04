package net.youssef.gestion_achats.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import net.youssef.gestion_achats.entity.*;
import net.youssef.gestion_achats.services.ArticleService;
import net.youssef.gestion_achats.services.Sarticleservice;
import net.youssef.gestion_achats.services.SSarticleservices;
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
public class BordereauEnreprise {

    @FXML
    private TableView<AjouteEntreprise> productTableView;
    @FXML
    private TableColumn<AjouteEntreprise, String> nameColumn;
    @FXML
    private TableColumn<AjouteEntreprise, String> familleColumn;
    @FXML
    private TableColumn<AjouteEntreprise, String> nBrdVeColumn;
    @FXML
    private TableColumn<AjouteEntreprise, String> nBrdColumn;
    @FXML
    private TableColumn<AjouteEntreprise, String> designationColumn;
    @FXML
    private TableColumn<AjouteEntreprise, String> unityColumn;
    @FXML
    private TableColumn<AjouteEntreprise, Integer> quantityColumn;
    @FXML
    private Button uploadButton;
    @FXML
    private Button saveButton;
    @FXML
    private ProgressBar progressBar;

    @Autowired
    private AjouteEntrepriseService ajouteEntrepriseService;

    @Autowired
    private Sarticleservice sarticleservice;

    @Autowired
    private SSarticleservices ssarticleservices;

    @Autowired
    private ArticleService articleService;

    private List<AjouteEntreprise> tempItems = new ArrayList<>();
    private String bordereauNumber;

    public void setBordereauNumber(String bordereauNumber) {
        this.bordereauNumber = bordereauNumber;
        // Load data or perform actions based on the bordereauNumber
    }
    @FXML
    private void initialize() {
        initializeTableColumns();
        saveButton.setOnAction(event -> saveItems());
    }

    private void initializeTableColumns() {
        nameColumn.setCellValueFactory(cellData -> getPropertyValue(cellData.getValue(), "DS"));
        familleColumn.setCellValueFactory(cellData -> getPropertyValue(cellData.getValue(), "Famille"));
        nBrdVeColumn.setCellValueFactory(cellData -> getPropertyValue(cellData.getValue(), "N"));
        nBrdColumn.setCellValueFactory(cellData -> getPropertyValue(cellData.getValue(), "N"));
        designationColumn.setCellValueFactory(cellData -> getDesignation(cellData.getValue()));
        unityColumn.setCellValueFactory(cellData -> getPropertyValue(cellData.getValue(), "Unity"));
        quantityColumn.setCellValueFactory(cellData -> getQuantity(cellData.getValue()));
    }

    private SimpleStringProperty getPropertyValue(AjouteEntreprise item, String property) {
        switch (property) {
            case "DS":
                return new SimpleStringProperty(item.getDescription());
            case "Famille":
                return new SimpleStringProperty(item.getFamille());
            case "N":
                return new SimpleStringProperty(item.getN());
            case "Unity":
                return new SimpleStringProperty(item.getUnity());
            default:
                return new SimpleStringProperty("");
        }
    }

    private SimpleStringProperty getDesignation(AjouteEntreprise item) {
        if (item.getSarticle() != null) {
            return new SimpleStringProperty(item.getSarticle().getName());
        } else if (item.getSsarticle() != null) {
            return new SimpleStringProperty(item.getSsarticle().getName());
        } else if (item.getArticle() != null) {
            return new SimpleStringProperty(item.getArticle().getName());
        }
        return new SimpleStringProperty("");
    }

    private ObjectProperty<Integer> getQuantity(AjouteEntreprise item) {
        return new SimpleIntegerProperty(item.getQuantity()).asObject();
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
        List<AjouteEntreprise> allItems = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                AjouteEntreprise ajout = createAjouteEntreprise(row);
                if (ajout != null) {
                    allItems.add(ajout);
                    // Add to tempItems if the condition for saving is met
                    if (!ajout.getN().equals(getCellValue(row.getCell(2)))) {
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

        if (parts.length == 2 && parts[1].equals("0")) {
            Article article = articleService.findByN(nBrd);
            if (article != null) {
                article.setFamille(famille);
                articleService.saveArticle(article);
            }
        } else if (parts.length == 2) {
            sarticle sarticle = sarticleservice.findByN(nBrd);
            if (sarticle != null) {
                sarticle.setFamille(famille);
                sarticleservice.saveSarticle(sarticle);
            }
        } else if (parts.length == 3) {
            ssarticle ssarticle = ssarticleservices.findByN(nBrd);
            if (ssarticle != null) {
                ssarticle.setFamille(famille);
                ssarticleservices.saveSSarticle(ssarticle);
            }
        }
    }

    private AjouteEntreprise createAjouteEntreprise(Row row) {
        String ds = getCellValue(row.getCell(0));
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
        linkEntities(ajout, nBrdVe);
        return ajout;
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
        String[] parts = nBrdVe.split("\\.");

        String nBrdVe1;
        if (parts.length == 2 && parts[1].equals("0")) {
            nBrdVe1 = parts[0];

            Article article = articleService.findByN(nBrdVe1);
            if (article != null) {
                ajouteEntreprise.setArticle(article);
            }
        } else if (parts.length == 2) {
            nBrdVe1 = parts[0] + "." + parts[1];

            sarticle sarticle = sarticleservice.findByN(nBrdVe1);
            if (sarticle != null) {
                ajouteEntreprise.setSarticle(sarticle);
            }
        } else if (parts.length == 3) {
            nBrdVe1 = parts[0] + "." + parts[1] + "." + parts[2];
            ssarticle ssarticle = ssarticleservices.findByN(nBrdVe1);

            if (ssarticle != null) {
                ajouteEntreprise.setSsarticle(ssarticle);
            }
        } else {
            System.out.println("Unexpected identifier format: " + nBrdVe);
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
