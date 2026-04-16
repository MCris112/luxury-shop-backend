package com.darkredgm.luxury.Product.Repository;

import com.darkredgm.luxury.Product.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // The underscore tells Spring to look inside the "categories" object
    // for the "slug" property.
    List<Product> findDistinctByCategories_SlugIn(List<String> slugs);

}
