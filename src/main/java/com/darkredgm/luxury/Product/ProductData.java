package com.darkredgm.luxury.Product;

import java.util.List;

public record ProductData (
        String name,
        String slug,
        String information,
        String content,
        String image,
        Float price,
        List<String> categories
){}
