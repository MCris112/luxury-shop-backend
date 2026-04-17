package com.darkredgm.luxury.Product.Repository;

import com.darkredgm.luxury.Product.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    java.util.List<Category> findBySlugIn(java.util.List<String> slugs);
}
