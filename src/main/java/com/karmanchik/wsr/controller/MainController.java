package com.karmanchik.wsr.controller;

import com.karmanchik.wsr.db.service.ManufacturerService;
import com.karmanchik.wsr.db.service.ProductService;
import com.karmanchik.wsr.entity.Manufacturer;
import com.karmanchik.wsr.entity.Product;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

@Log4j2
@Component
public class MainController extends BaseController {
    private static final List<Product> TEMP_PRODUCT_LIST = new LinkedList<>();
    @FXML
    public GridPane productGrid;
    @FXML
    public TextField tfSearch;
    @FXML
    public Button btnSearch;
    @FXML
    public ComboBox<String> cbManufacturer;


    private final ProductService productService;
    private final ManufacturerService manufacturerService;

    public MainController(ProductService productService, ManufacturerService manufacturerService) {
        this.productService = productService;
        this.manufacturerService = manufacturerService;
    }

    @Override
    public void initialize() {
        List<Product> products = productService.findAll(Sort.by(Sort.Direction.ASC, "Title"));
        List<Manufacturer> manufacturers = manufacturerService.findAll(Sort.by(Sort.Direction.ASC, "id"));

        TEMP_PRODUCT_LIST.addAll(products);
        loadProduct(TEMP_PRODUCT_LIST);
        loadManufacturer(manufacturers);

        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() >= 3)
                changeProductGrid(newValue);
            if (newValue.equals("")) changeProductGrid("");
        });

        btnSearch.setOnAction(event -> {
            String text = tfSearch.getText();
            if (!text.isEmpty()) changeProductGrid(text);
        });

        cbManufacturer.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int selectedIndex = newValue.intValue();
            System.out.println(selectedIndex);
            if (selectedIndex != 0) changeProductGrid(selectedIndex);
            else loadProduct(TEMP_PRODUCT_LIST);
        });
    }

    private void changeProductGrid(int selectedIndex) {
        List<Product> newProducts = new LinkedList<>();
        for (Product product : TEMP_PRODUCT_LIST) {
            if (product.getManufacturer().getId() == selectedIndex)
                newProducts.add(product);
        }
        loadProduct(newProducts);
    }

    private void loadManufacturer(List<Manufacturer> manufacturers) {
        cbManufacturer.getItems().clear();
        cbManufacturer.getItems().add(0, "Все производители");
        manufacturers.forEach(manufacturer -> cbManufacturer.getItems().add(manufacturer.getId(), manufacturer.getName()));
        cbManufacturer.getSelectionModel().selectFirst();
    }

    private void changeProductGrid(String search) {
        List<Product> newProducts = new LinkedList<>();
        for (Product product : TEMP_PRODUCT_LIST) {
            if (!search.equals("")) {
                if (product.getTitle().toLowerCase().contains(search.toLowerCase()))
                    newProducts.add(product);
            } else {
                newProducts.add(product);
            }
        }
        loadProduct(newProducts);
    }


    private void loadProduct(List<Product> products) {
        int i = 0, j = 0;
        productGrid.getChildren().clear();
        for (Product product : products) {
            try {
                if (i >= 5) {
                    i = 0;
                    j++;
                }
                ProductItem item = new ProductItem(product).build();
                GridPane.setMargin(item, new Insets(3));
                productGrid.add(item, i, j);
                i++;
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }

    static class ProductItem extends Button {
        private final Product product;

        ProductItem(Product product) {
            this.product = product;
        }

        public ProductItem build() throws FileNotFoundException {
            @NotNull String title = product.getTitle();
            @NotNull String cost = product.getCost().toString();
            String url = "src/main/resources/img/" + product.getMainImagePath().trim();
            ProductItem button = new ProductItem(product);

            button.setId(product.getId().toString());
            button.setDisable(!product.getIsActive());
            button.setStyle(
                    "-fx-background-color: #B4B8BD;" +
                            "-fx-border-radius: 0;" +
                            "-fx-border-width: 1px;" +
                            "-fx-border-style: solid;" +
                            "-fx-border-color: black;"
            );
            button.setOnMouseEntered(event -> button.setStyle(
                    "-fx-background-color: #B4B8BD;" +
                            "-fx-border-radius: 0;" +
                            "-fx-border-width: 1px;" +
                            "-fx-border-style: solid;" +
                            "-fx-border-color: black;" +
                            "-fx-opacity: 0.8;"
            ));
            button.setOnMouseExited(event -> button.setStyle(
                    "-fx-background-color: #B4B8BD;" +
                            "-fx-border-radius: 0;" +
                            "-fx-border-width: 1px;" +
                            "-fx-border-style: solid;" +
                            "-fx-border-color: black;" +
                            "-fx-opacity: 1;"
            ));

            VBox box = new VBox(3);
            box.setStyle(
                    "-fx-border-width: 1;" +
                            "-fx-alignment: center;" +
                            "-fx-padding: 5;" +
                            "-fx-pref-width: 200;" +
                            "-fx-pref-height: 250;"
            );

            Label lbTitle = new Label();
            lbTitle.setText(title);
            lbTitle.setAlignment(Pos.CENTER);
            lbTitle.textAlignmentProperty().setValue(TextAlignment.CENTER);
            lbTitle.setWrapText(true);

            Label lbCost = new Label();
            lbCost.setText(title);
            lbCost.setText(cost + " рублей");
            lbCost.setAlignment(Pos.CENTER);
            lbCost.textAlignmentProperty().setValue(TextAlignment.CENTER);
            lbCost.setWrapText(true);

            ImageView iv = new ImageView(new Image(new FileInputStream(url)));
            iv.setFitWidth(150);
            iv.setFitHeight(150);
            VBox.setMargin(iv, new Insets(0, 0, 10, 0));
            box.getChildren().addAll(iv, lbTitle, lbCost);

            button.setGraphic(box);
            return button;
        }
    }
}
