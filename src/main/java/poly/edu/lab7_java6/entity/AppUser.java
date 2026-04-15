package poly.edu.lab7_java6.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "app_users")
@Getter
@Setter
@NoArgsConstructor
public class AppUser {

    @Id
    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 120, nullable = false)
    private String password;

    @Column(length = 120, nullable = false)
    private String fullname;

    @Column(nullable = false)
    private Boolean enabled;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private UserRole role;
}

