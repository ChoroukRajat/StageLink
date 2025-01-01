package com.StageLink.StageLink_back.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Encadrant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEncadrant;

    @Column(nullable = false, length = 50)
    private String profession;

    @Column(nullable = false, length = 50)
    private String departement;

    @ManyToOne
    @JoinColumn(name = "id_entreprise", nullable = false)
    private Entreprise entreprise;

    @OneToOne
    @JoinColumn(name = "id_encadrant")
    private Utilisateur utilisateur;
}
