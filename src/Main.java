
import controller.ProductController;
import view.ProductView;

public class Main {
    public static void main(String[] args) {
        System.out.println("123");
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ProductView view = new ProductView();
                ProductController controller = new ProductController(view);
                controller.showProductView();
            }
        });
    }
}