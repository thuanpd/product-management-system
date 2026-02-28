package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.Product;
import service.IProductService;
import service.ProductServiceImpl;
import view.ProductView;

public class ProductController {
    private ProductView view;
    // Controller CHỈ gọi Interface, không quan tâm bên dưới là CSV hay Database
    private IProductService productService;

    public ProductController(ProductView view) {
        this.view = view;
        // Khởi tạo Service (Dependency Injection thủ công)
        this.productService = new ProductServiceImpl();

        // Load dữ liệu lên View
        loadDataToTable();

        // Gắn sự kiện
        this.view.addAddListener(new AddProductListener());
        this.view.addEditListener(new EditProductListener());
        this.view.addDeleteListener(new DeleteProductListener());
        this.view.addClearListener(e -> this.view.clearForm());
        this.view.addListSelectionListener(new TableSelectionListener());
    }

    public void showProductView() {
        view.setVisible(true);
    }

    private void loadDataToTable() {
        // Dọn sạch bảng trước (nếu cần) rồi add lại
        List<Product> products = productService.getAllProducts();
        for (Product p : products) {
            view.addRow(p);
        }
    }

    class AddProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Product p = view.getProductFromForm();
            if (p != null) {
                try {
                    productService.addProduct(p); // Gọi Service
                    view.addRow(p);               // Update View
                    view.clearForm();
                    view.showMessage("Thêm sản phẩm thành công!");
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
                    // Phải set đúng ID đang được chọn trên bảng để update
                    p.setId(view.getSelectedId());
                    productService.updateProduct(p); // Gọi Service
                    view.updateRow(selectedRow, p);  // Update View
                    view.clearForm();
                    view.showMessage("Cập nhật thành công!");
                } catch (Exception ex) {
                    view.showMessage(ex.getMessage());
                }
            } else {
                view.showMessage("Hãy chọn một dòng trên bảng để sửa!");
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
                    productService.deleteProduct(idToRemove); // Gọi Service
                    view.removeRow(selectedRow);              // Update View
                    view.clearForm();
                    view.showMessage("Xóa sản phẩm thành công!");
                } catch (Exception ex) {
                    view.showMessage(ex.getMessage());
                }
            } else {
                view.showMessage("Hãy chọn một dòng trên bảng để xóa!");
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
}