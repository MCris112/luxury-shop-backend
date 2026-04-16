package com.darkredgm.luxury.Product.Repository;

import com.darkredgm.luxury.Product.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
