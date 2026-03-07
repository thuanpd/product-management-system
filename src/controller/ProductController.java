package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.Product;
import service.IProductService;
import service.ProductServiceImpl; // Ensure correct import path
import view.ProductDetailView;     // Import to open Detail tab UI
import view.ProductView;

public class ProductController {
    private ProductView view;
    // Controller ONLY calls the interface, not caring if the backend is CSV or Database
    private IProductService productService;

    public ProductController(ProductView view) {
        this.view = view;

        // Manual Dependency Injection
        this.productService = new ProductServiceImpl();

        // Load data to View
        loadDataToTable();

        // Register events for add/edit/delete buttons
        this.view.addAddListener(new AddProductListener());
        this.view.addEditListener(new EditProductListener());
        this.view.addDeleteListener(new DeleteProductListener());
        this.view.addClearListener(e -> this.view.clearForm());

        // Event when selecting a row in the table
        this.view.addListSelectionListener(new TableSelectionListener());

        // NEW: Event when clicking "View SKU/Price" button in the action column
        this.view.addDetailInTableListener(new DetailInTableListener());
    }

    public void showProductView() {
        view.setVisible(true);
    }

    private void loadDataToTable() {
        // Clear table first (if needed) then add rows again
        List<Product> products = productService.getAllProducts();
        for (Product p : products) {
            view.addRow(p);
        }
    }

// ========================================================================
// INNER CLASSES HANDLE BASIC EVENTS
// ========================================================================

    class AddProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Product p = view.getProductFromForm();
            if (p != null) {
                try {
                    productService.addProduct(p); // Call service to save data
                    view.addRow(p);               // Update view with new row
                    view.clearForm();
                    view.showMessage("Product added successfully!");
                } catch (Exception ex) {
                    view.showMessage(ex.getMessage());
                }
            }
        }
    }

    class EditProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Product p = view.getProductFromForm();
            int selectedRow = view.getSelectedRow();

            if (selectedRow >= 0 && p != null) {
                try {
                    // Must set correct ID from selected row before updating
                    p.setId(view.getSelectedId());

                    productService.updateProduct(p); // Call service to update

                    view.updateRow(selectedRow, p);  // Update view

                    view.clearForm();

                    view.showMessage("Product updated successfully!");

                } catch (Exception ex) {
                    view.showMessage(ex.getMessage());
                }

            } else {
                view.showMessage("Please select a row to edit!");
            }
        }
    }

    class DeleteProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            int selectedRow = view.getSelectedRow();

            if (selectedRow >= 0) {
                try {

                    BigInteger idToRemove = view.getSelectedId();

                    productService.deleteProduct(idToRemove); // Call service to delete

                    view.removeRow(selectedRow);               // Remove row from view

                    view.clearForm();

                    view.showMessage("Product deleted successfully!");

                } catch (Exception ex) {
                    view.showMessage(ex.getMessage());
                }

            } else {
                view.showMessage("Please select a row to delete!");
            }
        }
    }

    class TableSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {

            if (!e.getValueIsAdjusting() && view.getSelectedRow() != -1) {

                BigInteger id = view.getSelectedId();

                for(Product p : productService.getAllProducts()) {

                    if(p.getId().equals(id)) {

                        view.setFormInfo(p);

                        break;
                    }
                }
            }
        }
    }

    class DetailInTableListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                int clickedRow = Integer.parseInt(e.getActionCommand());

                BigInteger selectedId = view.getIdAtRow(clickedRow);

                Product selectedProduct = null;

                for (Product p : productService.getAllProducts()) {

                    if (p.getId().equals(selectedId)) {

                        selectedProduct = p;

                        break;
                    }
                }

                if (selectedProduct != null) {

                    ProductDetailView detailView = new ProductDetailView(view, selectedProduct);

                    ProductDetailController detailController =
                            new ProductDetailController(detailView, selectedProduct);

                    detailView.setVisible(true);
                }

            } catch (Exception ex) {

                view.showMessage("Error opening product detail: " + ex.getMessage());
            }
        }
    }

}
