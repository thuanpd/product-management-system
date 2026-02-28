package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Product;
import model.ProductSku;

public class ProductSkuView extends JDialog {
    private JTextField txtId, txtSkuCode, txtColor, txtSize, txtMaterial, txtQuantity, txtWeight, txtBarcode;
    private JCheckBox chkActive;
    private JButton btnAdd, btnEdit, btnDelete, btnClear, btnManagePrice;
    private JTable skuTable;
    private DefaultTableModel tableModel;

    private Product currentProduct;

    public ProductSkuView(JFrame parent, Product currentProduct) {
        super(parent, "Quản Lý SKU - Sản phẩm: " + currentProduct.getName(), true);
        this.currentProduct = currentProduct;
        this.setSize(850, 550);
        this.setLocationRelativeTo(parent);

        initUI();
    }

    private void initUI() {
        JPanel inputPanel = new JPanel(new GridLayout(5, 4, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        inputPanel.add(new JLabel("ID SKU (Trống khi thêm):"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Mã SKU:"));
        txtSkuCode = new JTextField();
        inputPanel.add(txtSkuCode);

        inputPanel.add(new JLabel("Màu sắc (Color):"));
        txtColor = new JTextField();
        inputPanel.add(txtColor);

        inputPanel.add(new JLabel("Kích thước (Size):"));
        txtSize = new JTextField();
        inputPanel.add(txtSize);

        inputPanel.add(new JLabel("Chất liệu:"));
        txtMaterial = new JTextField();
        inputPanel.add(txtMaterial);

        inputPanel.add(new JLabel("Tồn kho (Qty):"));
        txtQuantity = new JTextField();
        inputPanel.add(txtQuantity);

        inputPanel.add(new JLabel("Trọng lượng (Weight):"));
        txtWeight = new JTextField();
        inputPanel.add(txtWeight);

        inputPanel.add(new JLabel("Mã vạch (Barcode):"));
        txtBarcode = new JTextField();
        inputPanel.add(txtBarcode);

        inputPanel.add(new JLabel("Trạng thái:"));
        chkActive = new JCheckBox("Đang bán");
        chkActive.setSelected(true);
        inputPanel.add(chkActive);

        inputPanel.add(new JLabel(""));
        inputPanel.add(new JLabel(""));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnAdd = new JButton("Thêm SKU");
        btnEdit = new JButton("Sửa SKU");
        btnDelete = new JButton("Xóa SKU");
        btnClear = new JButton("Reset");
        btnManagePrice = new JButton("Cập Nhật Giá Bán");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPanel.add(btnManagePrice);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        String[] columnNames = {"ID", "Mã SKU", "Màu", "Size", "Tồn Kho", "Barcode", "Hoạt động"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        skuTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(skuTable);

        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public ProductSku getSkuFromForm() {
        try {
            ProductSku sku = new ProductSku();
            if (!txtId.getText().trim().isEmpty()) {
                sku.setId(new BigInteger(txtId.getText().trim()));
            }
            sku.setSkuCode(txtSkuCode.getText().trim());
            sku.setColor(txtColor.getText().trim());
            sku.setSize(txtSize.getText().trim());
            sku.setMaterial(txtMaterial.getText().trim());
            sku.setQuantityInStock(Integer.parseInt(txtQuantity.getText().trim().isEmpty() ? "0" : txtQuantity.getText().trim()));
            sku.setWeight(Double.parseDouble(txtWeight.getText().trim().isEmpty() ? "0" : txtWeight.getText().trim()));
            sku.setBarcode(txtBarcode.getText().trim());
            sku.setActive(chkActive.isSelected());

            sku.setProduct(currentProduct);
            return sku;
        } catch (NumberFormatException e) {
            showMessage("Tồn kho và Trọng lượng phải là số!");
            return null;
        }
    }

    public void setFormInfo(ProductSku sku) {
        txtId.setText(sku.getId() != null ? sku.getId().toString() : "");
        txtSkuCode.setText(sku.getSkuCode());
        txtColor.setText(sku.getColor());
        txtSize.setText(sku.getSize());
        txtMaterial.setText(sku.getMaterial());
        txtQuantity.setText(String.valueOf(sku.getQuantityInStock()));
        txtWeight.setText(String.valueOf(sku.getWeight()));
        txtBarcode.setText(sku.getBarcode());
        chkActive.setSelected(sku.isActive());
        txtId.setEditable(false);
    }

    public void clearForm() {
        txtId.setText("");
        txtSkuCode.setText("");
        txtColor.setText("");
        txtSize.setText("");
        txtMaterial.setText("");
        txtQuantity.setText("0");
        txtWeight.setText("0.0");
        txtBarcode.setText("");
        chkActive.setSelected(true);
        txtId.setEditable(true);
        skuTable.clearSelection();
    }

    public void addRow(ProductSku sku) {
        tableModel.addRow(new Object[]{ sku.getId(), sku.getSkuCode(), sku.getColor(), sku.getSize(), sku.getQuantityInStock(), sku.getBarcode(), sku.isActive() ? "Có" : "Không" });
    }

    public void updateRow(int rowIndex, ProductSku sku) {
        tableModel.setValueAt(sku.getSkuCode(), rowIndex, 1);
        tableModel.setValueAt(sku.getColor(), rowIndex, 2);
        tableModel.setValueAt(sku.getSize(), rowIndex, 3);
        tableModel.setValueAt(sku.getQuantityInStock(), rowIndex, 4);
        tableModel.setValueAt(sku.getBarcode(), rowIndex, 5);
        tableModel.setValueAt(sku.isActive() ? "Có" : "Không", rowIndex, 6);
    }

    public void removeRow(int rowIndex) { tableModel.removeRow(rowIndex); }
    public int getSelectedRow() { return skuTable.getSelectedRow(); }
    public BigInteger getSelectedId() {
        int row = skuTable.getSelectedRow();
        if (row >= 0) return new BigInteger(skuTable.getValueAt(row, 0).toString());
        return null;
    }
    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }

    public void addAddListener(ActionListener log) { btnAdd.addActionListener(log); }
    public void addEditListener(ActionListener log) { btnEdit.addActionListener(log); }
    public void addDeleteListener(ActionListener log) { btnDelete.addActionListener(log); }
    public void addClearListener(ActionListener log) { btnClear.addActionListener(log); }
    public void addManagePriceListener(ActionListener log) { btnManagePrice.addActionListener(log); }
    public void addListSelectionListener(javax.swing.event.ListSelectionListener listener) {
        skuTable.getSelectionModel().addListSelectionListener(listener);
    }
}