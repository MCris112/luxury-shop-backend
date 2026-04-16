package com.darkredgm.luxury.Product.Services;

import com.darkredgm.luxury.Product.Models.Product;
import com.darkredgm.luxury.Product.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> update(Long id, Product productDetails) {
        return productRepository.findById(id).map(product -> {
            if (productDetails.getName() != null) product.setName(productDetails.getName());
            if (productDetails.getInformation() != null) product.setInformation(productDetails.getInformation());
            if (productDetails.getContent() != null) product.setContent(productDetails.getContent());
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
