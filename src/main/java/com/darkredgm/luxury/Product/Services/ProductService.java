package com.darkredgm.luxury.Product.Services;

import com.darkredgm.luxury.Product.Models.Product;
import com.darkredgm.luxury.Product.ProductData;
import com.darkredgm.luxury.Product.Repository.CategoryRepository;
import com.darkredgm.luxury.Product.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Product> findAll( List<String> categories ) {

        if ( categories != null )
        {
            return productRepository.findDistinctByCategories_SlugIn( categories );
        }

        return productRepository.findAll();
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
        if (data.price() != null) product.setPrice(data.price());
        product.setImage(data.image());

        if (data.categories() != null) {
            product.setCategories(categoryRepository.findBySlugIn(data.categories()));
        }

        return productRepository.save(product);
    }

    public Optional<Product> update(Long id, ProductData productDetails) {
        return productRepository.findById(id).map(product -> {
            if (productDetails.name() != null) product.setName(productDetails.name());
            if (productDetails.slug() != null) product.setSlug(productDetails.slug());
            if (productDetails.information() != null) product.setInformation(productDetails.information());
            if (productDetails.content() != null) product.setContent(productDetails.content());
            if (productDetails.price() != null) product.setPrice(productDetails.price());
            if (productDetails.image() != null) product.setImage(productDetails.image());

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
