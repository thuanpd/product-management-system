package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Product;

public class ProductView extends JFrame {
    // Các component
    private JTextField txtId, txtName, txtPrice;
    private JButton btnAdd, btnEdit, btnDelete, btnClear;
    private JTable productTable;
    private DefaultTableModel tableModel;

    public ProductView() {
        super("Quản Lý Sản Phẩm - MVC");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        // 1. Panel nhập liệu (Phía trên)
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Mã Sản Phẩm (ID):"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Tên Sản Phẩm:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Giá Bán:"));
        txtPrice = new JTextField();
        inputPanel.add(txtPrice);

        // 2. Panel chức năng (Các nút bấm)
        JPanel buttonPanel = new JPanel();
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnClear = new JButton("Reset");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 3. Bảng hiển thị dữ liệu (Phía dưới)
        String[] columnNames = {"ID", "Tên Sản Phẩm", "Giá"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);

        // Thêm vào JFrame
        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    // Các hàm lấy dữ liệu từ Form
    public Product getProductFromForm() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText();
            double price = Double.parseDouble(txtPrice.getText());
            return new Product(id, name, price);
        } catch (NumberFormatException e) {
            showMessage("Dữ liệu không hợp lệ! Vui lòng kiểm tra lại số.");
            return null;
        }
    }

    // Hàm hiển thị thông tin lên Form khi click vào bảng
    public void setFormInfo(Product p) {
        txtId.setText(String.valueOf(p.getId()));
        txtName.setText(p.getName());
        txtPrice.setText(String.valueOf(p.getPrice()));
        // Khóa ID lại khi đang sửa (tuỳ chọn)
        txtId.setEditable(false);
    }

    // Hàm clear form
    public void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtPrice.setText("");
        txtId.setEditable(true);
    }

    // Các hàm thao tác với bảng
    public void addRow(Product p) {
        tableModel.addRow(new Object[]{p.getId(), p.getName(), p.getPrice()});
    }

    public void updateRow(int rowIndex, Product p) {
        tableModel.setValueAt(p.getName(), rowIndex, 1);
        tableModel.setValueAt(p.getPrice(), rowIndex, 2);
    }

    public void removeRow(int rowIndex) {
        tableModel.removeRow(rowIndex);
    }

    public int getSelectedRow() {
        return productTable.getSelectedRow();
    }

    // Hàm hiển thị thông báo
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // Gán sự kiện cho các nút (Để Controller gọi)
    public void addAddListener(ActionListener log) { btnAdd.addActionListener(log); }
    public void addEditListener(ActionListener log) { btnEdit.addActionListener(log); }
    public void addDeleteListener(ActionListener log) { btnDelete.addActionListener(log); }
    public void addClearListener(ActionListener log) { btnClear.addActionListener(log); }

    // Sự kiện click vào bảng
    public void addListSelectionListener(javax.swing.event.ListSelectionListener listener) {
        productTable.getSelectionModel().addListSelectionListener(listener);
    }

    // Lấy ID từ dòng đang chọn (để Controller tìm trong list)
    public int getSelectedId() {
        int row = productTable.getSelectedRow();
        if (row >= 0) {
            return Integer.parseInt(productTable.getValueAt(row, 0).toString());
        }
        return -1;
    }
}