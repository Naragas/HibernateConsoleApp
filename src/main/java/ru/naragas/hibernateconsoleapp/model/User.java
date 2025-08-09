package ru.naragas.hibernateconsoleapp.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @version 1.0
 * @author Naragas
 * @created 7/31/2025
 */

@Entity
@Table(name = "users")
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private int id;

    @NonNull
    @Column(name = "name", nullable = false)
    @Getter @Setter
    private String name;

    @NonNull
    @Column(name = "email", unique = true, nullable = false, length = 50)
    @Getter @Setter
    private String email;

    @NonNull
    @Column(name = "age")
    @Getter @Setter
    private int age;

    @Column(name = "created_at", updatable = false, insertable = false)
    @Getter @Setter
    private LocalDateTime createdAt;

}
