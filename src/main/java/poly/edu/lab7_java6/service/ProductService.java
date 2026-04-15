package poly.edu.lab7_java6.service;

import poly.edu.lab7_java6.dto.ProductRequest;
import poly.edu.lab7_java6.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> findAll();

    ProductResponse findById(String id);

    ProductResponse create(ProductRequest request);

    ProductResponse update(String id, ProductRequest request);

    void deleteById(String id);
}

