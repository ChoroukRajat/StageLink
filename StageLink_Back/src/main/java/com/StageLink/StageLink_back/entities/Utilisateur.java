package com.StageLink.StageLink_back.entities;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtilisateur;

    @Column(nullable = false, length = 50)
    private String nom;

    @Column(nullable = false, length = 50)
    private String prenom;

    @Column(nullable = false)
    private LocalDate dateNaissance;

    @Column(nullable = false)
    private Boolean sexe; // Represented as true/false instead of 0/1

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, unique = true, length = 50)
    private String telephone;

    @Column(nullable = false) // **Added field**
    private String password;

    @Enumerated(EnumType.STRING)  // Store the enum as a string in the database
    @Column(nullable = false)
    private Role role;



    public enum Role {
        ADMIN, ETUDIANT, ECOLE, ENTREPRISE, CF, ENCADRANT, RESPO; // Add as many roles as you need
    }
}
