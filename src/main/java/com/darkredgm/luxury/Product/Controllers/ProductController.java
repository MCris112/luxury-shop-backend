package com.darkredgm.luxury.Product.Controllers;

import com.darkredgm.luxury.Product.Models.Product;
import com.darkredgm.luxury.Product.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> index(@RequestParam(required = false) List<String> categories) {
        return productService.findAll(categories);
    }

    /**
     * kfdjklsdjf sdk
     * @param id jlsdjgklfsdfgfsdljkl
     * @return dfgjkdfklgjkl
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> show(@PathVariable Long id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> store(@RequestBody Product product) {
        Product savedProduct = productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        return productService.update(id, product)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (productService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
