package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.Product;
import view.ProductView;

public class ProductController {
    private ProductView view;
    // Sử dụng ArrayList thay cho Database để đơn giản hóa
    private List<Product> listProducts;

    public ProductController(ProductView view) {
        this.view = view;
        this.listProducts = new ArrayList<>();

        // Gắn sự kiện cho các nút bấm
        view.addAddListener(new AddProductListener());
        view.addEditListener(new EditProductListener());
        view.addDeleteListener(new DeleteProductListener());
        view.addClearListener(new ClearProductListener());

        // Gắn sự kiện click vào bảng
        view.addListSelectionListener(new TableSelectionListener());
    }

    public void showProductView() {
        view.setVisible(true);
    }

    // --- Inner classes xử lý sự kiện ---

    class AddProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Product p = view.getProductFromForm();
            if (p != null) {
                // Kiểm tra trùng ID
                for(Product exist : listProducts) {
                    if(exist.getId() == p.getId()) {
                        view.showMessage("ID đã tồn tại!");
                        return;
                    }
                }
                listProducts.add(p);
                view.addRow(p);
                view.clearForm();
                view.showMessage("Thêm thành công!");
            }
        }
    }

    class EditProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Product p = view.getProductFromForm();
            int selectedRow = view.getSelectedRow();
            if (selectedRow >= 0 && p != null) {
                // Cập nhật trong List
                for (Product item : listProducts) {
                    if (item.getId() == p.getId()) {
                        item.setName(p.getName());
                        item.setPrice(p.getPrice());
                        break;
                    }
                }
                // Cập nhật trên View
                view.updateRow(selectedRow, p);
                view.showMessage("Cập nhật thành công!");
                view.clearForm();
            } else {
                view.showMessage("Hãy chọn một dòng để sửa!");
            }
        }
    }

    class DeleteProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = view.getSelectedRow();
            if (selectedRow >= 0) {
                int idToRemove = view.getSelectedId();
                // Xóa trong List
                listProducts.removeIf(p -> p.getId() == idToRemove);
                // Xóa trên View
                view.removeRow(selectedRow);
                view.clearForm();
                view.showMessage("Xóa thành công!");
            } else {
                view.showMessage("Hãy chọn một dòng để xóa!");
            }
        }
    }

    class ClearProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.clearForm();
        }
    }

    // Sự kiện khi người dùng click vào một dòng trong bảng
    class TableSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting() && view.getSelectedRow() != -1) {
                int id = view.getSelectedId();
                for(Product p : listProducts) {
                    if(p.getId() == id) {
                        view.setFormInfo(p);
                        break;
                    }
                }
            }
        }
    }
}