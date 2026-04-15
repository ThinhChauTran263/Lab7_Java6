package poly.edu.lab7_java6.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.edu.lab7_java6.dao.CategoryDAO;
import poly.edu.lab7_java6.dao.ProductDAO;
import poly.edu.lab7_java6.dto.ProductRequest;
import poly.edu.lab7_java6.dto.ProductResponse;
import poly.edu.lab7_java6.entity.Category;
import poly.edu.lab7_java6.entity.Product;
import poly.edu.lab7_java6.exception.BadRequestException;
import poly.edu.lab7_java6.exception.ResourceNotFoundException;
import poly.edu.lab7_java6.service.ProductService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productDAO.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse findById(String id) {
        Product product = productDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        return toResponse(product);
    }

    @Override
    public ProductResponse create(ProductRequest request) {
        if (productDAO.existsById(request.getId())) {
            throw new BadRequestException("Product id already exists: " + request.getId());
        }

        Product product = new Product();
        product.setId(request.getId());
        applyRequest(product, request);

        return toResponse(productDAO.save(product));
    }

    @Override
    public ProductResponse update(String id, ProductRequest request) {
        Product existingProduct = productDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));

        if (request.getId() != null && !id.equals(request.getId())) {
            throw new BadRequestException("Product id in path and body must match");
        }

        applyRequest(existingProduct, request);
        return toResponse(productDAO.save(existingProduct));
    }

    @Override
    public void deleteById(String id) {
        Product existingProduct = productDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        productDAO.delete(existingProduct);
    }

    private void applyRequest(Product product, ProductRequest request) {
        // Category is required and acts as foreign key in Bai 2.
        Category category = categoryDAO.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + request.getCategoryId()));

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDate(request.getDate());
        product.setCategory(category);
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDate(),
                product.getCategory().getId(),
                product.getCategory().getName()
        );
    }
}
