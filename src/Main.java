
import controller.ProductController;
import view.ProductView;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ProductView view = new ProductView();
                ProductController controller = new ProductController(view);
                controller.showProductView();
            }
        });
    }
}