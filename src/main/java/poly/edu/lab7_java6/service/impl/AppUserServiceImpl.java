package poly.edu.lab7_java6.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.edu.lab7_java6.dao.AppUserDAO;
import poly.edu.lab7_java6.dto.UserRequest;
import poly.edu.lab7_java6.entity.AppUser;
import poly.edu.lab7_java6.entity.UserRole;
import poly.edu.lab7_java6.exception.BadRequestException;
import poly.edu.lab7_java6.exception.ResourceNotFoundException;
import poly.edu.lab7_java6.service.AppUserService;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class AppUserServiceImpl implements AppUserService {

    private final AppUserDAO appUserDAO;

    @Override
    @Transactional(readOnly = true)
    public List<AppUser> findAll() {
        return appUserDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public AppUser findByUsername(String username) {
        return appUserDAO.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }

    @Override
    public AppUser create(UserRequest request) {
        if (appUserDAO.existsById(request.getUsername())) {
            throw new BadRequestException("Username already exists: " + request.getUsername());
        }

        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        applyRequest(user, request);

        return appUserDAO.save(user);
    }

    @Override
    public AppUser update(String username, UserRequest request) {
        AppUser existingUser = findByUsername(username);

        if (request.getUsername() != null && !username.equals(request.getUsername())) {
            throw new BadRequestException("Username in path and body must match");
        }

        applyRequest(existingUser, request);
        return appUserDAO.save(existingUser);
    }

    @Override
    public void deleteByUsername(String username) {
        AppUser existingUser = findByUsername(username);
        appUserDAO.delete(existingUser);
    }

    private void applyRequest(AppUser user, UserRequest request) {
        user.setPassword(request.getPassword());
        user.setFullname(request.getFullname());
        user.setEnabled(request.getEnabled());
        user.setRole(parseRole(request.getRole()));
    }

    private UserRole parseRole(String value) {
        try {
            // Accept lowercase/uppercase input from UI and normalize to enum.
            return UserRole.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (Exception ex) {
            throw new BadRequestException("Role must be USER or ADMIN");
        }
    }
}
