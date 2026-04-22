package com.darkredgm.luxury.Product.Services;

import com.darkredgm.luxury.Product.Models.Category;
import com.darkredgm.luxury.Product.Models.Product;
import com.darkredgm.luxury.Product.ProductData;
import com.darkredgm.luxury.Product.Repository.CategoryRepository;
import com.darkredgm.luxury.Product.Repository.ProductRepository;
import jakarta.persistence.criteria.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Retrieves all products, optionally filtering by categories, search term, and price range.
     *
     * @param categories List of category slugs to filter by.
     * @param search     Search term to look for in product name, content, or information.
     * @param minPrice   Minimum price filter.
     * @param maxPrice   Maximum price filter.
     * @return List of products matching the criteria.
     */
    public List<Product> findAll(List<String> categories, String search, Double minPrice, Double maxPrice) {
        // Initialize Specification with a conjunction (always true) to avoid nullability issues
        Specification<Product> spec = (root, query, cb) -> cb.conjunction();

        // If categories are provided, filter products that belong to any of the specified slugs
        if (categories != null && !categories.isEmpty()) {
            spec = spec.and((root, query, cb) -> {
                // Ensure distinct results to avoid duplicates due to the many-to-many join
                query.distinct(true);
                Join<Product, Category> categoryJoin = root.join("categories");
                return categoryJoin.get("slug").in(categories);
            });
        }

        // If a search term is provided, look for matches in name, content, or information
        if (search != null && !search.trim().isEmpty()) {
            String searchPattern = "%" + search.toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("name")), searchPattern),
                    cb.like(cb.lower(root.get("content")), searchPattern),
                    cb.like(cb.lower(root.get("information")), searchPattern)));
        }

        // Price range filtering
        if (minPrice != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        }

        if (maxPrice != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }

        // Return the results from the repository using the built specification
        return productRepository.findAll(spec);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(ProductData data) {
        Product product = new Product();
        product.setName(data.name());
        product.setSlug(data.slug());
        product.setContent(data.content());
        product.setInformation(data.information());
        if (data.price() != null)
            product.setPrice(data.price());
        product.setImage(data.image());

        if (data.categories() != null) {
            product.setCategories(categoryRepository.findBySlugIn(data.categories()));
        }

        return productRepository.save(product);
    }

    public Optional<Product> update(Long id, ProductData productDetails) {
        return productRepository.findById(id).map(product -> {
            if (productDetails.name() != null)
                product.setName(productDetails.name());
            if (productDetails.slug() != null)
                product.setSlug(productDetails.slug());
            if (productDetails.information() != null)
                product.setInformation(productDetails.information());
            if (productDetails.content() != null)
                product.setContent(productDetails.content());
            if (productDetails.price() != null)
                product.setPrice(productDetails.price());
            if (productDetails.image() != null)
                product.setImage(productDetails.image());

            if (productDetails.categories() != null) {
                product.setCategories(categoryRepository.findBySlugIn(productDetails.categories()));
            }

            return productRepository.save(product);
        });
    }

    public boolean deleteById(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
