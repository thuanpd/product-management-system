package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.Product;
import model.ProductSku;
import model.ProductSkuPrice;
import service.IProductSkuPriceService;
import service.IProductSkuService;
import service.impl.ProductSkuPriceServiceImpl;
import service.impl.ProductSkuServiceImpl;
import view.ProductDetailView;

public class ProductDetailController {
    private ProductDetailView view;
    private Product currentProduct;

    private IProductSkuService skuService;
    private IProductSkuPriceService priceService;

    public ProductDetailController(ProductDetailView view, Product currentProduct) {
        this.view = view;
        this.currentProduct = currentProduct;

        this.skuService = new ProductSkuServiceImpl();
        this.priceService = new ProductSkuPriceServiceImpl();

        this.view.addSkuAddListener(new AddSkuListener());
        this.view.addSkuEditListener(new EditSkuListener());
        this.view.addSkuDeleteListener(new DeleteSkuListener());
        this.view.addSkuClearListener(e -> this.view.clearSkuForm());
        this.view.addSkuListSelectionListener(new SkuTableSelectionListener());

        this.view.addPriceAddListener(new AddPriceListener());
        this.view.addPriceEditListener(new EditPriceListener());
        this.view.addPriceDeleteListener(new DeletePriceListener());
        this.view.addPriceClearListener(e -> this.view.clearPriceForm());
        this.view.addPriceListSelectionListener(new PriceTableSelectionListener());

        this.view.addSkuComboChangeListener(e -> loadPricesForSelectedSku());
        loadSkus();
    }

    // ==========================================================
    // LOGIC TAB 1: QUẢN LÝ SKU
    // ==========================================================
    private void loadSkus() {
        view.clearSkuTable();
        List<ProductSku> skus = skuService.getSkusByProductId(currentProduct.getId());
        for (ProductSku sku : skus) {
            view.addSkuRow(sku);
        }
        view.updateSkuComboBox(skus);
    }

    class AddSkuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ProductSku sku = view.getSkuFromForm();
            if (sku != null) {
                try {
                    skuService.addSku(sku);
                    view.addSkuRow(sku);
                    view.clearSkuForm();
                    view.updateSkuComboBox(skuService.getSkusByProductId(currentProduct.getId()));
                    view.showMessage("Thêm SKU thành công!");
                } catch (Exception ex) { view.showMessage(ex.getMessage()); }
            }
        }
    }

    class EditSkuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ProductSku sku = view.getSkuFromForm();
            int row = view.getSelectedSkuRow();
            if (row >= 0 && sku != null) {
                try {
                    sku.setId(view.getSelectedSkuId());
                    skuService.updateSku(sku);
                    view.updateSkuRow(row, sku); // Cập nhật ngay trên bảng
                    view.clearSkuForm();
                    view.updateSkuComboBox(skuService.getSkusByProductId(currentProduct.getId()));
                    view.showMessage("Sửa SKU thành công!");
                } catch (Exception ex) { view.showMessage(ex.getMessage()); }
            } else {
                view.showMessage("Hãy chọn một dòng SKU để sửa!");
            }
        }
    }

    class DeleteSkuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = view.getSelectedSkuRow();
            if (row >= 0) {
                try {
                    skuService.deleteSku(view.getSelectedSkuId());
                    view.removeSkuRow(row); // Xóa ngay trên bảng
                    view.clearSkuForm();
                    view.updateSkuComboBox(skuService.getSkusByProductId(currentProduct.getId()));
                    view.showMessage("Xóa SKU thành công!");
                } catch (Exception ex) { view.showMessage(ex.getMessage()); }
            } else {
                view.showMessage("Hãy chọn một dòng SKU để xóa!");
            }
        }
    }

    class SkuTableSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting() && view.getSelectedSkuRow() != -1) {
                BigInteger id = view.getSelectedSkuId();
                for (ProductSku s : skuService.getSkusByProductId(currentProduct.getId())) {
                    if (s.getId().equals(id)) { view.setSkuFormInfo(s); break; }
                }
            }
        }
    }

    // ==========================================================
    // LOGIC TAB 2: QUẢN LÝ GIÁ BÁN (PRICE)
    // ==========================================================
    private void loadPricesForSelectedSku() {
        String selectedSkuCode = view.getSelectedSkuCodeFromCombo();
        if (selectedSkuCode == null) return;

        List<ProductSkuPrice> prices = priceService.getPricesBySkuCode(selectedSkuCode);
        view.clearPriceTable();
        for (ProductSkuPrice p : prices) {
            view.addPriceRow(p);
        }
        view.clearPriceForm();
    }

    class AddPriceListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ProductSkuPrice price = view.getPriceFromForm();
            String selectedSkuCode = view.getSelectedSkuCodeFromCombo();

            if (price != null && selectedSkuCode != null) {
                try {
                    ProductSku sku = new ProductSku();
                    sku.setSkuCode(selectedSkuCode);
                    price.setProductSku(sku);

                    priceService.addPrice(price);
                    view.addPriceRow(price);
                    view.clearPriceForm();
                    view.showMessage("Thêm Giá thành công!");
                } catch (Exception ex) { view.showMessage(ex.getMessage()); }
            }
        }
    }

    class EditPriceListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ProductSkuPrice price = view.getPriceFromForm();
            int row = view.getSelectedPriceRow();
            String selectedSkuCode = view.getSelectedSkuCodeFromCombo();

            if (row >= 0 && price != null && selectedSkuCode != null) {
                try {
                    price.setId(view.getSelectedPriceId());

                    ProductSku sku = new ProductSku();
                    sku.setSkuCode(selectedSkuCode);
                    price.setProductSku(sku);

                    priceService.updatePrice(price);
                    view.updatePriceRow(row, price);
                    view.clearPriceForm();
                    view.showMessage("Cập nhật Giá thành công!");
                } catch (Exception ex) { view.showMessage(ex.getMessage()); }
            } else {
                view.showMessage("Hãy chọn một dòng trên bảng Giá để sửa!");
            }
        }
    }

    class DeletePriceListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = view.getSelectedPriceRow();
            if (row >= 0) {
                try {
                    priceService.deletePrice(view.getSelectedPriceId());
                    view.removePriceRow(row);
                    view.clearPriceForm();
                    view.showMessage("Xóa Giá thành công!");
                } catch (Exception ex) { view.showMessage(ex.getMessage()); }
            } else {
                view.showMessage("Hãy chọn một dòng trên bảng Giá để xóa!");
            }
        }
    }

    class PriceTableSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting() && view.getSelectedPriceRow() != -1) {
                BigInteger id = view.getSelectedPriceId();
                String selectedSkuCode = view.getSelectedSkuCodeFromCombo();
                if(selectedSkuCode != null) {
                    for (ProductSkuPrice p : priceService.getPricesBySkuCode(selectedSkuCode)) {
                        if (p.getId().equals(id)) { view.setPriceFormInfo(p); break; }
                    }
                }
            }
        }
    }
}