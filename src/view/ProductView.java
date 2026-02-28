package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Product;

public class ProductView extends JFrame {
    // Các component cho Product Entity
    private JTextField txtId, txtProductCode, txtName, txtDescription, txtCategory, txtBrand;
    private JCheckBox chkActive;
    private JButton btnAdd, btnEdit, btnDelete, btnClear, btnManageSku;
    private JTable productTable;
    private DefaultTableModel tableModel;

    public ProductView() {
        super("Quản Lý Sản Phẩm (Master) - MVC");
        this.setSize(900, 600); // Tăng kích thước vì có nhiều trường hơn
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        // 1. Panel nhập liệu (Phía trên) - Tăng thành 7 hàng
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        inputPanel.add(new JLabel("ID Hệ Thống (Trống khi thêm mới):"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Mã Sản Phẩm (Product Code):"));
        txtProductCode = new JTextField();
        inputPanel.add(txtProductCode);

        inputPanel.add(new JLabel("Tên Sản Phẩm:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Mô Tả:"));
        txtDescription = new JTextField();
        inputPanel.add(txtDescription);

        inputPanel.add(new JLabel("Danh Mục (Category):"));
        txtCategory = new JTextField();
        inputPanel.add(txtCategory);

        inputPanel.add(new JLabel("Thương Hiệu (Brand):"));
        txtBrand = new JTextField();
        inputPanel.add(txtBrand);

        inputPanel.add(new JLabel("Trạng thái:"));
        chkActive = new JCheckBox("Đang hoạt động");
        chkActive.setSelected(true); // Mặc định là true theo model
        inputPanel.add(chkActive);

        // 2. Panel chức năng (Các nút bấm)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnClear = new JButton("Reset Form");

        // Nút gợi ý cho việc mở quản lý SKU (sẽ code ở View khác)
        btnManageSku = new JButton("Quản Lý SKU/Giá");
        btnManageSku.setToolTipText("Chọn 1 sản phẩm ở bảng dưới rồi bấm nút này");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPanel.add(btnManageSku);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 3. Bảng hiển thị dữ liệu (Phía dưới)
        String[] columnNames = {"ID", "Mã SP", "Tên Sản Phẩm", "Danh Mục", "Thương Hiệu", "Hoạt động"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho sửa trực tiếp trên bảng
            }
        };
        productTable = new JTable(tableModel);

        // Chỉnh kích thước cột cho đẹp
        productTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        productTable.getColumnModel().getColumn(2).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh Sách Sản Phẩm"));

        // Thêm vào JFrame
        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    // --- Các hàm lấy/đẩy dữ liệu từ Form ---

    public Product getProductFromForm() {
        try {
            Product p = new Product();

            // Xử lý ID (BigInteger)
            String idStr = txtId.getText().trim();
            if (!idStr.isEmpty()) {
                p.setId(new BigInteger(idStr));
            }

            p.setProductCode(txtProductCode.getText().trim());
            p.setName(txtName.getText().trim());
            p.setDescription(txtDescription.getText().trim());
            p.setCategory(txtCategory.getText().trim());
            p.setBrand(txtBrand.getText().trim());
            p.setActive(chkActive.isSelected());

            // Validate cơ bản
            if (p.getProductCode().isEmpty() || p.getName().isEmpty()) {
                showMessage("Mã sản phẩm và Tên sản phẩm không được để trống!");
                return null;
            }

            return p;
        } catch (NumberFormatException e) {
            showMessage("ID không hợp lệ! Vui lòng chỉ nhập số.");
            return null;
        }
    }

    public void setFormInfo(Product p) {
        txtId.setText(p.getId() != null ? p.getId().toString() : "");
        txtProductCode.setText(p.getProductCode());
        txtName.setText(p.getName());
        txtDescription.setText(p.getDescription());
        txtCategory.setText(p.getCategory());
        txtBrand.setText(p.getBrand());
        chkActive.setSelected(p.isActive());

        txtId.setEditable(false); // ID hệ thống thường không cho sửa
    }

    public void clearForm() {
        txtId.setText("");
        txtProductCode.setText("");
        txtName.setText("");
        txtDescription.setText("");
        txtCategory.setText("");
        txtBrand.setText("");
        chkActive.setSelected(true);

        txtId.setEditable(true);
        productTable.clearSelection();
    }

    // --- Các hàm thao tác với JTable ---

    public void addRow(Product p) {
        tableModel.addRow(new Object[]{
                p.getId(),
                p.getProductCode(),
                p.getName(),
                p.getCategory(),
                p.getBrand(),
                p.isActive() ? "Có" : "Không"
        });
    }

    public void updateRow(int rowIndex, Product p) {
        tableModel.setValueAt(p.getProductCode(), rowIndex, 1);
        tableModel.setValueAt(p.getName(), rowIndex, 2);
        tableModel.setValueAt(p.getCategory(), rowIndex, 3);
        tableModel.setValueAt(p.getBrand(), rowIndex, 4);
        tableModel.setValueAt(p.isActive() ? "Có" : "Không", rowIndex, 5);
    }

    public void removeRow(int rowIndex) {
        tableModel.removeRow(rowIndex);
    }

    public int getSelectedRow() {
        return productTable.getSelectedRow();
    }

    public BigInteger getSelectedId() {
        int row = productTable.getSelectedRow();
        if (row >= 0) {
            Object idObj = productTable.getValueAt(row, 0);
            return new BigInteger(idObj.toString());
        }
        return null;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // --- Đăng ký sự kiện cho Controller ---

    public void addAddListener(ActionListener log) { btnAdd.addActionListener(log); }
    public void addEditListener(ActionListener log) { btnEdit.addActionListener(log); }
    public void addDeleteListener(ActionListener log) { btnDelete.addActionListener(log); }
    public void addClearListener(ActionListener log) { btnClear.addActionListener(log); }

    // Nút này dùng để Controller bắt sự kiện mở form Quản lý SKU
    public void addManageSkuListener(ActionListener log) { btnManageSku.addActionListener(log); }

    public void addListSelectionListener(javax.swing.event.ListSelectionListener listener) {
        productTable.getSelectionModel().addListSelectionListener(listener);
    }
}