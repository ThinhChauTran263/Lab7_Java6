package poly.edu.lab7_java6.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import poly.edu.lab7_java6.entity.AppUser;

public interface AppUserDAO extends JpaRepository<AppUser, String> {
}

