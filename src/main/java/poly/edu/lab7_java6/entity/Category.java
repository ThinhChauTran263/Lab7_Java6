package poly.edu.lab7_java6.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {

    @Id
    @NotBlank(message = "Category id is required")
    @Column(length = 30, nullable = false)
    private String id;

    @NotBlank(message = "Category name is required")
    @Column(length = 120, nullable = false)
    private String name;
}

