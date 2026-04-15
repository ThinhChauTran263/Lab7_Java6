package poly.edu.lab7_java6.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.lab7_java6.entity.Product;

public interface ProductDAO extends JpaRepository<Product, String> {
}

