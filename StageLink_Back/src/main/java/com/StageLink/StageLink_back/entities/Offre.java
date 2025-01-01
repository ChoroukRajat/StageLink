package com.StageLink.StageLink_back.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Offre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOffre;

    @Column(nullable = false, length = 50)
    private String titreProjet;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false, length = 50)
    private String duree;

    @Column(nullable = false, length = 50)
    private String objectifsProjet;

    @Column(nullable = false, length = 200)
    private String competence;

    @Column(nullable = false)
    private Boolean remuneration;

    @ManyToOne
    @JoinColumn(name = "id_encadrant", nullable = false)
    private Encadrant encadrant;

    @Enumerated(EnumType.STRING)  // Store the enum as a string in the database
    @Column(nullable = false)
    private Offre.Type type;

    @ManyToOne
    @JoinColumn(name = "id_ville", nullable = false)
    private Ville ville;

    public enum Type {
        PFA1, PFA2, PFE;
    }
}

