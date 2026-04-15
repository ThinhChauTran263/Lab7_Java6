package poly.edu.lab7_java6.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import poly.edu.lab7_java6.dto.UserRequest;
import poly.edu.lab7_java6.entity.AppUser;
import poly.edu.lab7_java6.service.AppUserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping
    public List<AppUser> findAll() {
        return appUserService.findAll();
    }

    @GetMapping("/{username}")
    public AppUser findByUsername(@PathVariable String username) {
        return appUserService.findByUsername(username);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppUser create(@Valid @RequestBody UserRequest request) {
        return appUserService.create(request);
    }

    @PutMapping("/{username}")
    public AppUser update(@PathVariable String username, @Valid @RequestBody UserRequest request) {
        return appUserService.update(username, request);
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String username) {
        appUserService.deleteByUsername(username);
    }
}
