package com.darkredgm.luxury.Product.Controllers;

import com.darkredgm.luxury.Product.Models.Category;
import com.darkredgm.luxury.Product.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> index() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> show(@PathVariable Long id) {
        return categoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Category> store(@RequestBody Category category) {
        Category savedCategory = categoryService.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.update(id, category)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (categoryService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
