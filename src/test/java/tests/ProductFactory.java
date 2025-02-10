package tests;

import com.devsuperior.dscommerce.entities.Category;
import com.devsuperior.dscommerce.entities.Product;

public class ProductFactory {

    public static Product createProduct() {
        Product product = new Product(1L,"livro","livro de história",50.0,"imagem");
        return product;
    }

    public static Product createProductName(String name) {
        Product product = createProduct();
        product.setName(name);
        return product;
    }
}
