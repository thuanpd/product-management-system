package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import model.Product;

public class ProductView extends JFrame {

    private JTextField txtId, txtProductCode, txtName, txtDescription, txtCategory, txtBrand;
    private JCheckBox chkActive;

    private JButton btnAdd, btnEdit, btnDelete, btnClear;

    private JTable productTable;
    private DefaultTableModel tableModel;

    private ActionListener detailButtonListener;

    public ProductView() {
        super("Product Management");
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        inputPanel.add(new JLabel("System ID (Leave blank for new):"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Product Code:"));
        txtProductCode = new JTextField();
        inputPanel.add(txtProductCode);

        inputPanel.add(new JLabel("Product Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Description:"));
        txtDescription = new JTextField();
        inputPanel.add(txtDescription);

        inputPanel.add(new JLabel("Category:"));
        txtCategory = new JTextField();
        inputPanel.add(txtCategory);

        inputPanel.add(new JLabel("Brand:"));
        txtBrand = new JTextField();
        inputPanel.add(txtBrand);

        inputPanel.add(new JLabel("Status:"));
        chkActive = new JCheckBox("Active");
        chkActive.setSelected(true);
        inputPanel.add(chkActive);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        btnAdd = new JButton("Add");
        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Reset Form");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        String[] columnNames = {"ID", "Product Code", "Product Name", "Category", "Brand", "Active", "Action"};

        tableModel = new DefaultTableModel(columnNames, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };

        productTable = new JTable(tableModel);

        productTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        productTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        productTable.getColumnModel().getColumn(6).setPreferredWidth(120);

        productTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        productTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Product List"));

        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {

            setText("View SKU/Price");
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {

        protected JButton button;
        private int clickedRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);

            button = new JButton();
            button.setOpaque(true);

            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    fireEditingStopped();

                    if (detailButtonListener != null) {

                        ActionEvent event =
                                new ActionEvent(button,
                                        ActionEvent.ACTION_PERFORMED,
                                        String.valueOf(clickedRow));

                        detailButtonListener.actionPerformed(event);
                    }
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table,
                                                     Object value,
                                                     boolean isSelected,
                                                     int row,
                                                     int column) {

            this.clickedRow = row;
            button.setText("View SKU/Price");

            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "View SKU/Price";
        }
    }

    public Product getProductFromForm() {

        try {

            Product p = new Product();

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

            if (p.getProductCode().isEmpty()) {
                showMessage("Product code is required!");
                return null;
            }

            if (p.getName().isEmpty()) {
                showMessage("Product name is required!");
                return null;
            }

            if (p.getCategory().isEmpty()) {
                showMessage("Category is required!");
                return null;
            }

            if (p.getBrand().isEmpty()) {
                showMessage("Brand is required!");
                return null;
            }

            return p;

        } catch (NumberFormatException e) {

            showMessage("Invalid ID format!");
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

        txtId.setEditable(false);
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

    public void addRow(Product p) {

        tableModel.addRow(new Object[]{
                p.getId(),
                p.getProductCode(),
                p.getName(),
                p.getCategory(),
                p.getBrand(),
                p.isActive() ? "Yes" : "No",
                "View"
        });
    }

    public void updateRow(int rowIndex, Product p) {

        tableModel.setValueAt(p.getProductCode(), rowIndex, 1);
        tableModel.setValueAt(p.getName(), rowIndex, 2);
        tableModel.setValueAt(p.getCategory(), rowIndex, 3);
        tableModel.setValueAt(p.getBrand(), rowIndex, 4);
        tableModel.setValueAt(p.isActive() ? "Yes" : "No", rowIndex, 5);
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

            if (idObj != null) {
                return new BigInteger(idObj.toString());
            }
        }

        return null;
    }

    public BigInteger getIdAtRow(int row) {

        if (row >= 0 && row < productTable.getRowCount()) {

            Object idObj = productTable.getValueAt(row, 0);

            if (idObj != null) {
                return new BigInteger(idObj.toString());
            }
        }

        return null;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void addAddListener(ActionListener log) { btnAdd.addActionListener(log); }

    public void addEditListener(ActionListener log) { btnEdit.addActionListener(log); }

    public void addDeleteListener(ActionListener log) { btnDelete.addActionListener(log); }

    public void addClearListener(ActionListener log) { btnClear.addActionListener(log); }

    public void addListSelectionListener(javax.swing.event.ListSelectionListener listener) {
        productTable.getSelectionModel().addListSelectionListener(listener);
    }

    public void addDetailInTableListener(ActionListener listener) {
        this.detailButtonListener = listener;
    }
}