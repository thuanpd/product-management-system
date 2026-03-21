package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Product;
import model.ProductSku;
import model.ProductSkuPrice;

public class ProductDetailView extends JDialog {
    private Product currentProduct;
    private JTabbedPane tabbedPane;

    private JTextField txtSkuId, txtSkuCode, txtColor, txtSize, txtMaterial, txtQty, txtWeight, txtBarcode;
    private JCheckBox chkSkuActive;
    private JButton btnAddSku, btnEditSku, btnDeleteSku, btnClearSku;
    private JTable skuTable;
    private DefaultTableModel skuTableModel;

    private JComboBox<String> cbSkuSelector;
    private JTextField txtPriceId, txtPrice, txtOriginalPrice, txtCurrency;
    private JCheckBox chkPriceActive;
    private JButton btnAddPrice, btnEditPrice, btnDeletePrice, btnClearPrice;
    private JTable priceTable;
    private DefaultTableModel priceTableModel;

    public ProductDetailView(JFrame parent, Product currentProduct) {
        super(parent, "Product details: " + currentProduct.getName(), true);
        this.currentProduct = currentProduct;
        this.setSize(900, 600);
        this.setLocationRelativeTo(parent);

        initUI();
    }

    private void initUI() {
        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("1. SKU Management (SKU)", createSkuTab());
        tabbedPane.addTab("2. Price History Management", createPriceTab());

        this.setLayout(new BorderLayout());
        this.add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createSkuTab() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 4, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        inputPanel.add(new JLabel("SKU ID (Auto):")); txtSkuId = new JTextField(); inputPanel.add(txtSkuId);
        inputPanel.add(new JLabel("SKU Code:")); txtSkuCode = new JTextField();inputPanel.add(txtSkuCode);
        inputPanel.add(new JLabel("Color:")); txtColor = new JTextField();inputPanel.add(txtColor);
        inputPanel.add(new JLabel("Size:")); txtSize = new JTextField();inputPanel.add(txtSize);
        inputPanel.add(new JLabel("Material:")); txtMaterial = new JTextField();inputPanel.add(txtMaterial);
        inputPanel.add(new JLabel("Stock Quantity:")); txtQty = new JTextField("0");inputPanel.add(txtQty);
        inputPanel.add(new JLabel("Weight:")); txtWeight = new JTextField("0.0");inputPanel.add(txtWeight);
        inputPanel.add(new JLabel("Barcode:")); txtBarcode = new JTextField(); inputPanel.add(txtBarcode);

        inputPanel.add(new JLabel("Status:"));
        chkSkuActive = new JCheckBox("Selling"); chkSkuActive.setSelected(true);
        inputPanel.add(chkSkuActive);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnAddSku = new JButton("Add SKU");
        btnEditSku = new JButton("Edit SKU");
        btnDeleteSku = new JButton("Delete SKU");
        btnClearSku = new JButton("Clear");
        buttonPanel.add(btnAddSku); buttonPanel.add(btnEditSku); buttonPanel.add(btnDeleteSku); buttonPanel.add(btnClearSku);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        String[] columns = {"ID", "SKU Code", "Color", "Size", "Stock", "Active"};
        skuTableModel = new DefaultTableModel(columns, 0) { @Override public boolean isCellEditable(int r, int c) { return false; }};
        skuTable = new JTable(skuTableModel);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(skuTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createPriceTab() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        inputPanel.add(new JLabel("Price ID:")); txtPriceId = new JTextField(); inputPanel.add(txtPriceId);

        inputPanel.add(new JLabel("Select SKU Code:"));
        cbSkuSelector = new JComboBox<>();
        inputPanel.add(cbSkuSelector);

        inputPanel.add(new JLabel("Current Price:")); txtPrice = new JTextField(); inputPanel.add(txtPrice);
        inputPanel.add(new JLabel("Original Price:")); txtOriginalPrice = new JTextField(); inputPanel.add(txtOriginalPrice);
        inputPanel.add(new JLabel("Currency:")); txtCurrency = new JTextField("VND"); inputPanel.add(txtCurrency);

        inputPanel.add(new JLabel("Status:"));
        chkPriceActive = new JCheckBox("Active"); chkPriceActive.setSelected(true);
        inputPanel.add(chkPriceActive);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnAddPrice = new JButton("Add Price");
        btnEditPrice = new JButton("Edit Price");
        btnDeletePrice = new JButton("Delete Price");
        btnClearPrice = new JButton("Clear");
        buttonPanel.add(btnAddPrice); buttonPanel.add(btnEditPrice); buttonPanel.add(btnDeletePrice); buttonPanel.add(btnClearPrice);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        String[] columns = {"ID", "SKU Code", "Status", "Original Price", "Currency", "Active"};
        priceTableModel = new DefaultTableModel(columns, 0) { @Override public boolean isCellEditable(int r, int c) { return false; }};
        priceTable = new JTable(priceTableModel);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(priceTable), BorderLayout.CENTER);
        return panel;
    }

    public void showMessage(String msg) { JOptionPane.showMessageDialog(this, msg); }

    public ProductSku getSkuFromForm() {
        try {
            ProductSku sku = new ProductSku();
            if (!txtSkuId.getText().trim().isEmpty()) sku.setId(new BigInteger(txtSkuId.getText().trim()));
            sku.setSkuCode(txtSkuCode.getText().trim());
            sku.setColor(txtColor.getText().trim());
            sku.setSize(txtSize.getText().trim());
            sku.setMaterial(txtMaterial.getText().trim());
            sku.setQuantityInStock(Integer.parseInt(txtQty.getText().trim().isEmpty() ? "0" : txtQty.getText().trim()));
            sku.setWeight(Double.parseDouble(txtWeight.getText().trim().isEmpty() ? "0" : txtWeight.getText().trim()));
            sku.setBarcode(txtBarcode.getText().trim());
            sku.setActive(chkSkuActive.isSelected());
            sku.setProduct(currentProduct);
            return sku;
        } catch (NumberFormatException e) {
            showMessage("Stock quantity and weight must be numeric!"); return null;
        }
    }

    public void setSkuFormInfo(ProductSku sku) {
        txtSkuId.setText(sku.getId() != null ? sku.getId().toString() : "");
        txtSkuCode.setText(sku.getSkuCode()); txtColor.setText(sku.getColor());
        txtSize.setText(sku.getSize()); txtMaterial.setText(sku.getMaterial());
        txtQty.setText(String.valueOf(sku.getQuantityInStock()));
        txtWeight.setText(String.valueOf(sku.getWeight()));
        txtBarcode.setText(sku.getBarcode());
        chkSkuActive.setSelected(sku.isActive());
        txtSkuId.setEditable(false);
    }

    public void clearSkuForm() {
        txtSkuId.setText(""); txtSkuCode.setText(""); txtColor.setText(""); txtSize.setText("");
        txtMaterial.setText(""); txtQty.setText("0"); txtWeight.setText("0.0"); txtBarcode.setText("");
        chkSkuActive.setSelected(true); txtSkuId.setEditable(true); skuTable.clearSelection();
    }

    public void addSkuRow(ProductSku sku) {
        skuTableModel.addRow(new Object[]{sku.getId(), sku.getSkuCode(), sku.getColor(), sku.getSize(), sku.getQuantityInStock(), sku.isActive() ? "Có" : "Không"});
    }

    public void updateSkuRow(int rowIndex, ProductSku sku) {
        skuTableModel.setValueAt(sku.getSkuCode(), rowIndex, 1);
        skuTableModel.setValueAt(sku.getColor(), rowIndex, 2);
        skuTableModel.setValueAt(sku.getSize(), rowIndex, 3);
        skuTableModel.setValueAt(sku.getQuantityInStock(), rowIndex, 4);
        skuTableModel.setValueAt(sku.isActive() ? "Yes" : "No", rowIndex, 5);
    }

    public void removeSkuRow(int rowIndex) {
        skuTableModel.removeRow(rowIndex);
    }

    public void clearSkuTable() {
        skuTableModel.setRowCount(0);
    }

    public int getSelectedSkuRow() { return skuTable.getSelectedRow(); }
    public BigInteger getSelectedSkuId() {
        int row = skuTable.getSelectedRow();
        return (row >= 0) ? new BigInteger(skuTable.getValueAt(row, 0).toString()) : null;
    }

    public void updateSkuComboBox(List<ProductSku> skus) {
        cbSkuSelector.removeAllItems();
        for (ProductSku sku : skus) {
            cbSkuSelector.addItem(sku.getSkuCode());
        }
    }

    public void addSkuAddListener(ActionListener log) { btnAddSku.addActionListener(log); }
    public void addSkuEditListener(ActionListener log) { btnEditSku.addActionListener(log); }
    public void addSkuDeleteListener(ActionListener log) { btnDeleteSku.addActionListener(log); }
    public void addSkuClearListener(ActionListener log) { btnClearSku.addActionListener(log); } // [THÊM MỚI]
    public void addSkuListSelectionListener(javax.swing.event.ListSelectionListener listener) {
        skuTable.getSelectionModel().addListSelectionListener(listener);
    }

    public ProductSkuPrice getPriceFromForm() {
        try {
            ProductSkuPrice p = new ProductSkuPrice();
            if (!txtPriceId.getText().trim().isEmpty()) p.setId(new BigInteger(txtPriceId.getText().trim()));
            p.setPrice(new BigDecimal(txtPrice.getText().trim()));
            if(!txtOriginalPrice.getText().trim().isEmpty()) p.setOriginalPrice(new BigDecimal(txtOriginalPrice.getText().trim()));
            p.setCurrency(txtCurrency.getText().trim());
            p.setActive(chkPriceActive.isSelected());
            return p;
        } catch (Exception e) {
            showMessage("Invalid price value!"); return null;
        }
    }

    public void setPriceFormInfo(ProductSkuPrice p) {
        txtPriceId.setText(p.getId() != null ? p.getId().toString() : "");
        cbSkuSelector.setSelectedItem(p.getProductSku().getSkuCode());
        txtPrice.setText(p.getPrice() != null ? p.getPrice().toString() : "");
        txtOriginalPrice.setText(p.getOriginalPrice() != null ? p.getOriginalPrice().toString() : "");
        txtCurrency.setText(p.getCurrency());
        chkPriceActive.setSelected(p.isActive());
        txtPriceId.setEditable(false);
    }

    public void clearPriceForm() {
        txtPriceId.setText(""); txtPrice.setText(""); txtOriginalPrice.setText("");
        txtCurrency.setText("VND"); chkPriceActive.setSelected(true);
        txtPriceId.setEditable(true); priceTable.clearSelection();
    }

    public String getSelectedSkuCodeFromCombo() {
        return (String) cbSkuSelector.getSelectedItem();
    }

    public void clearPriceTable() {
        priceTableModel.setRowCount(0);
    }

    public void addPriceRow(ProductSkuPrice p) {
        priceTableModel.addRow(new Object[]{p.getId(), p.getProductSku().getSkuCode(), p.getPrice(), p.getOriginalPrice(), p.getCurrency(), p.isActive() ? "Có" : "Không"});
    }

    public void updatePriceRow(int rowIndex, ProductSkuPrice p) {
        priceTableModel.setValueAt(p.getProductSku().getSkuCode(), rowIndex, 1);
        priceTableModel.setValueAt(p.getPrice(), rowIndex, 2);
        priceTableModel.setValueAt(p.getOriginalPrice(), rowIndex, 3);
        priceTableModel.setValueAt(p.getCurrency(), rowIndex, 4);
        priceTableModel.setValueAt(p.isActive() ? "Yes" : "No", rowIndex, 5);
    }

    public void removePriceRow(int rowIndex) {
        priceTableModel.removeRow(rowIndex);
    }

    public int getSelectedPriceRow() { return priceTable.getSelectedRow(); }
    public BigInteger getSelectedPriceId() {
        int row = priceTable.getSelectedRow();
        return (row >= 0) ? new BigInteger(priceTable.getValueAt(row, 0).toString()) : null;
    }

    public void addSkuComboChangeListener(ActionListener log) {
        cbSkuSelector.addActionListener(log);
    }

    public void addPriceAddListener(ActionListener log) { btnAddPrice.addActionListener(log); }
    public void addPriceEditListener(ActionListener log) { btnEditPrice.addActionListener(log); } // [THÊM MỚI]
    public void addPriceDeleteListener(ActionListener log) { btnDeletePrice.addActionListener(log); } // [THÊM MỚI]
    public void addPriceClearListener(ActionListener log) { btnClearPrice.addActionListener(log); } // [THÊM MỚI]
    public void addPriceListSelectionListener(javax.swing.event.ListSelectionListener listener) { // [THÊM MỚI]
        priceTable.getSelectionModel().addListSelectionListener(listener);
    }
}