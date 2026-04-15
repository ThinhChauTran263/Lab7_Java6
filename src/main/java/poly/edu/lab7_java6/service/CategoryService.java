package poly.edu.lab7_java6.service;

import poly.edu.lab7_java6.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Category findById(String id);

    Category create(Category category);

    Category update(String id, Category category);

    void deleteById(String id);
}

