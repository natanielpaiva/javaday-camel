package javaday.produto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    // Lista estática de produtos para simular um banco de dados
    private static final List<Product> products = new ArrayList<>();

    static {
        products.add(new Product(1L, "Product A", 10.0));
        products.add(new Product(2L, "Product B", 20.0));
        products.add(new Product(3L, "Product C", 30.0));
    }

    // Endpoint para listar todos os produtos
    @GetMapping
    public List<Product> getAllProducts() {
        return products;
    }

    // Endpoint para buscar um produto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
