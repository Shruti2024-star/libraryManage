package com.libraryManagement.project1.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "users")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // LOCAL / GOOGLE login
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    // One user can borrow many books
    @OneToMany(mappedBy = "user")
    private List<IssueRecord> issues;

    // One user can reserve many books
    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;
}