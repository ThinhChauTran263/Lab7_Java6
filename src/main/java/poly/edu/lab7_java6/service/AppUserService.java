package poly.edu.lab7_java6.service;

import poly.edu.lab7_java6.dto.UserRequest;
import poly.edu.lab7_java6.entity.AppUser;

import java.util.List;

public interface AppUserService {
    List<AppUser> findAll();

    AppUser findByUsername(String username);

    AppUser create(UserRequest request);

    AppUser update(String username, UserRequest request);

    void deleteByUsername(String username);
}

