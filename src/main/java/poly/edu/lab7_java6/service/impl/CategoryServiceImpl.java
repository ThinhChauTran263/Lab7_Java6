package poly.edu.lab7_java6.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.edu.lab7_java6.dao.CategoryDAO;
import poly.edu.lab7_java6.entity.Category;
import poly.edu.lab7_java6.exception.BadRequestException;
import poly.edu.lab7_java6.exception.ResourceNotFoundException;
import poly.edu.lab7_java6.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO categoryDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Category findById(String id) {
        return categoryDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
    }

    @Override
    public Category create(Category category) {
        if (categoryDAO.existsById(category.getId())) {
            throw new BadRequestException("Category id already exists: " + category.getId());
        }
        return categoryDAO.save(category);
    }

    @Override
    public Category update(String id, Category category) {
        Category existingCategory = findById(id);
        existingCategory.setName(category.getName());
        return categoryDAO.save(existingCategory);
    }

    @Override
    public void deleteById(String id) {
        Category existingCategory = findById(id);
        categoryDAO.delete(existingCategory);
    }
}

