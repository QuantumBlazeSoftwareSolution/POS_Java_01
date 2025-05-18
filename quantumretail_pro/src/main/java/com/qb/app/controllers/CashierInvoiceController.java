package com.qb.app.controllers;

import com.qb.app.model.ControllerClose;
import com.qb.app.model.DefaultAPI;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.SVGIconGroup;
import com.qb.app.model.entity.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class CashierInvoiceController implements Initializable, ControllerClose {

    // <editor-fold desc="FXML init component" defaultstate="collapsed">
    @FXML
    private VBox invoiceItemContainer;
    @FXML
    private ScrollPane invoiceScrollContainer;
    @FXML
    private ImageView itemImage;
    @FXML
    private ScrollBar invoiceScroller;
    @FXML
    private TextField tfBarCode;
    @FXML
    private TextField tfItemCode;
    @FXML
    private Button btnProductView;
    @FXML
    private Label invoiceNumber;
    @FXML
    private Label labelItemName;
    @FXML
    private Label labelItemPrice;
    @FXML
    private Button btnDecreaseQty;
    @FXML
    private Button btnViewQty;
    @FXML
    private Button btnIncreaseQty;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnAdd;
    @FXML
    private Label invoiceItemCount;
    @FXML
    private Label invoiceSubTotal;
    @FXML
    private Label invoiceDiscount;
    @FXML
    private Label invoiceTotal;
    @FXML
    private Button btnPayment;
    @FXML
    private Button itemPrice;
    @FXML
    private AnchorPane root;
    // </editor-fold>

    private double unitPrice = 0;
    private double itemQty = 1;
    private boolean isProductLoaded;
    private Product product;

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getItemQty() {
        return itemQty;
    }

    public void setItemQty(double itemQty) {
        this.itemQty = itemQty;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        DefaultAPI.bindTableScroll(invoiceScroller, invoiceScrollContainer, invoiceItemContainer);
        tfItemCode.setTextFormatter(DefaultAPI.createNumericTextFormatter());

        setEventListener();

//        List<String> itemList = new ArrayList<>();
//        itemList.add("Tomato 1KG");
//        itemList.add("Chocolate 100g");
//        itemList.add("Chocolate 400g");
//        itemList.add("Coca Cola 1L");
//        itemList.add("Rice 5KG");
//        itemList.add("Coconut 1 Unit");
//        itemList.add("Milk Powder 400g");
//        itemList.add("Tea Leaves 200g");
//        itemList.add("Chilli Powder 250g Chilli Powder 250g Chilli Powder 250g");
//
//        for (int i = 0; i < 20; i++) {
//            try {
//                Random random = new Random();
//
//                // Load the invoiceItem.fxml file
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/qb/app/fxmlComponent/invoiceItem.fxml"));
//                Node invoiceItem = loader.load(); // Load the node
//
//                InvoiceItemController itemController = loader.getController();
//                String itemCode = "ITEM #" + (i + 1);
//                String itemImage = getClass().getResource("/com/qb/app/assets/images/tomato.png").toExternalForm();
//                String itemName = itemList.get(random.nextInt(itemList.size()));
//                double itemPrice = 100.00 * (i + 1);
//                double quantity = i + 1;
//                itemController.InvoiceItemData(itemCode, itemImage, itemName, itemPrice, quantity);
//
//                // Add the invoice item to the VBox
//                invoiceItemContainer.getChildren().add(invoiceItem);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void close() {
    }

    @FXML
    private void handleActionEvent(ActionEvent event) {
    }

    @FXML
    private void itemCodePressed(KeyEvent event) {
        if (event.getCode() == KeyCode.SHIFT) {
            System.out.println("SHIFT KEY PRESSED");
            loadPreviewProduct();
        }
    }

    private void loadPreviewProduct() {
        String itemCode = tfItemCode.getText().trim();

        // Validate input before database query
        if (itemCode.isEmpty()) {
            System.out.println("CANNOT FIND THE PRODUCT - Empty code");
            return;
        }

        try {
            JPATransaction.runInTransaction((em) -> {
                try {
                    // Convert to proper ID type (assuming Integer)
                    Integer productId = Integer.valueOf(itemCode);
                    Product product = em.find(Product.class, productId);

                    if (product != null) {
                        this.product = product;
                        setItemQty(1);
                        btnViewQty.setText("1");

                        Platform.runLater(() -> {
                            labelItemName.setText(product.getProduct());
                            labelItemPrice.setText(String.format("%.2f", product.getSalePrice()));
                            setUnitPrice(product.getSalePrice());
                            setItemPrice();
                        });
                        isProductLoaded = true;
                    } else {
                        System.out.println("CANNOT FIND THE PRODUCT");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("CANNOT FIND THE PRODUCT - Invalid code format");
                }
            });
        } catch (Exception e) {
            System.err.println("Database error: " + e.getMessage());
            // Optionally show user-friendly error message
        }
    }

    private void setItemPrice() {
        double itemPrice = getUnitPrice() * getItemQty();
        this.itemPrice.setText(String.format("Rs. %.2f", itemPrice));
    }

    private void setEventListener() {
        root.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.PLUS || event.getCode() == KeyCode.ADD) {
                System.out.println("PLUS PRESSED");
                event.consume();
                increaseQty();
            } else if (event.getCode() == KeyCode.MINUS || event.getCode() == KeyCode.SUBTRACT) {
                System.out.println("MINUS PRESSED");
                decreaseQty();
                event.consume();
            } else if (event.getCode() == KeyCode.ENTER) {
                if (isProductLoaded) {
                    addInvoiceItem();
                    isProductLoaded = false;
                }
            }
            System.out.println("Ky Code: " + event.getCode());
        });
    }

    private void increaseQty() {
        setItemQty(getItemQty() + 1);
        setItemPrice();
        btnViewQty.setText(String.valueOf(getItemQty()));
    }

    private void decreaseQty() {
        if (getItemQty() > 1) {
            setItemQty(getItemQty() - 1);
            setItemPrice();
        } else {
            System.out.println("Quantity is less than 1");
        }
        btnViewQty.setText(String.valueOf(getItemQty()));
    }

    private void addInvoiceItem() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/qb/app/fxmlComponent/invoiceItem.fxml"));
            Node invoiceItem = loader.load();
            InvoiceItemController itemController = loader.getController();

            // Get the product image path (check for multiple possible extensions)
            String imagePath = findProductImage(this.product.getId());

            itemController.InvoiceItemData(
                    this.product.getId().toString(),
                    imagePath,
                    this.product.getProduct(),
                    this.product.getSalePrice(),
                    getItemQty()
            );

            invoiceItemContainer.getChildren().add(invoiceItem);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds the product image by checking common extensions
     *
     * @param productId The product ID to search for
     * @return The image URL if found, empty string otherwise
     */
    private String findProductImage(Integer productId) {
        // Common image extensions to check
        String[] extensions = {".png", ".jpg", ".jpeg", ".gif"};
        String basePath = "/com/qb/app/assets/images/product/product_";

        for (String ext : extensions) {
            String imagePath = basePath + productId + ext;
            try {
                URL imageUrl = getClass().getResource(imagePath);
                if (imageUrl != null) {
                    return imageUrl.toExternalForm();
                }
            } catch (Exception e) {
                // Continue to next extension if this one fails
                continue;
            }
        }

        // Return default image if none found
        return getClass().getResource("/com/qb/app/assets/images/new_product_image.png").toExternalForm();
    }
}
