package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.ProductSku;
import model.ProductSkuPrice;

public class ProductSkuPriceView extends JDialog {
    private JTextField txtId, txtPrice, txtOriginalPrice, txtCurrency;
    private JCheckBox chkActive;
    private JButton btnAdd, btnEdit, btnDelete, btnClear;
    private JTable priceTable;
    private DefaultTableModel tableModel;

    private ProductSku currentSku;

    public ProductSkuPriceView(JDialog parent, ProductSku currentSku) {
        super(parent, "Thiết Lập Giá - SKU: " + currentSku.getSkuCode(), true);
        this.currentSku = currentSku;
        this.setSize(600, 400);
        this.setLocationRelativeTo(parent);

        initUI();
    }

    private void initUI() {
        JPanel inputPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("ID Giá (Trống):"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Loại Tiền Tệ:"));
        txtCurrency = new JTextField("VND");
        inputPanel.add(txtCurrency);

        inputPanel.add(new JLabel("Giá Bán Hiện Tại:"));
        txtPrice = new JTextField();
        inputPanel.add(txtPrice);

        inputPanel.add(new JLabel("Giá Gốc (Original):"));
        txtOriginalPrice = new JTextField();
        inputPanel.add(txtOriginalPrice);

        inputPanel.add(new JLabel("Trạng thái:"));
        chkActive = new JCheckBox("Đang áp dụng");
        chkActive.setSelected(true);
        inputPanel.add(chkActive);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnAdd = new JButton("Thêm Giá");
        btnEdit = new JButton("Sửa Giá");
        btnDelete = new JButton("Xóa Giá");
        btnClear = new JButton("Reset");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        String[] columnNames = {"ID", "Giá Bán", "Giá Gốc", "Tiền Tệ", "Áp dụng"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        priceTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(priceTable);

        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public ProductSkuPrice getPriceFromForm() {
        try {
            ProductSkuPrice p = new ProductSkuPrice();
            if (!txtId.getText().trim().isEmpty()) p.setId(new BigInteger(txtId.getText().trim()));
            p.setPrice(new BigDecimal(txtPrice.getText().trim()));

            if(!txtOriginalPrice.getText().trim().isEmpty()){
                p.setOriginalPrice(new BigDecimal(txtOriginalPrice.getText().trim()));
            }

            p.setCurrency(txtCurrency.getText().trim());
            p.setActive(chkActive.isSelected());
            p.setProductSku(currentSku);
            return p;
        } catch (NumberFormatException e) {
            showMessage("Vui lòng nhập định dạng số cho Giá bán và Giá gốc!");
            return null;
        }
    }

    public void setFormInfo(ProductSkuPrice p) {
        txtId.setText(p.getId() != null ? p.getId().toString() : "");
        txtPrice.setText(p.getPrice() != null ? p.getPrice().toString() : "");
        txtOriginalPrice.setText(p.getOriginalPrice() != null ? p.getOriginalPrice().toString() : "");
        txtCurrency.setText(p.getCurrency());
        chkActive.setSelected(p.isActive());
        txtId.setEditable(false);
    }

    public void clearForm() {
        txtId.setText("");
        txtPrice.setText("");
        txtOriginalPrice.setText("");
        txtCurrency.setText("VND");
        chkActive.setSelected(true);
        txtId.setEditable(true);
        priceTable.clearSelection();
    }

    public void addRow(ProductSkuPrice p) {
        tableModel.addRow(new Object[]{p.getId(), p.getPrice(), p.getOriginalPrice(), p.getCurrency(), p.isActive() ? "Có" : "Không"});
    }

    public void updateRow(int rowIndex, ProductSkuPrice p) {
        tableModel.setValueAt(p.getPrice(), rowIndex, 1);
        tableModel.setValueAt(p.getOriginalPrice(), rowIndex, 2);
        tableModel.setValueAt(p.getCurrency(), rowIndex, 3);
        tableModel.setValueAt(p.isActive() ? "Có" : "Không", rowIndex, 4);
    }

    public void removeRow(int rowIndex) { tableModel.removeRow(rowIndex); }
    public int getSelectedRow() { return priceTable.getSelectedRow(); }
    public BigInteger getSelectedId() {
        int row = priceTable.getSelectedRow();
        if (row >= 0) return new BigInteger(priceTable.getValueAt(row, 0).toString());
        return null;
    }
    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }

    public void addAddListener(ActionListener log) { btnAdd.addActionListener(log); }
    public void addEditListener(ActionListener log) { btnEdit.addActionListener(log); }
    public void addDeleteListener(ActionListener log) { btnDelete.addActionListener(log); }
    public void addClearListener(ActionListener log) { btnClear.addActionListener(log); }
    public void addListSelectionListener(javax.swing.event.ListSelectionListener listener) {
        priceTable.getSelectionModel().addListSelectionListener(listener);
    }
}