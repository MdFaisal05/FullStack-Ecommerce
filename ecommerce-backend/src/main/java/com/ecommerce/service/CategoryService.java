package com.ecommerce.service;

import com.ecommerce.dto.CategoryRequest;
import com.ecommerce.entity.Category;

import java.util.List;

public interface CategoryService {

    Category addCategory(CategoryRequest request);

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category updateCategory(Long id, CategoryRequest request);

    String deleteCategory(Long id);
}