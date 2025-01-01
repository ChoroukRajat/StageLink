package com.StageLink.StageLink_back.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEtudiant;

    @Column(nullable = false, length = 50)
    private String adresseEtudiant;

    @Column(nullable = false)
    private Integer anneePromotion;

    @Column(nullable = false, length = 50)
    private String mentionEtudiant;

    @Column(nullable = false)
    private Boolean assuranceEtudiant;

    @Column(nullable = false, length = 50)
    private String cv;

    @Enumerated(EnumType.STRING)  // Store the enum as a string in the database
    @Column(nullable = false)
    private Etudiant.Status status;

    @OneToOne
    @JoinColumn(name = "id_etudiant")
    private Utilisateur utilisateur;

    public enum Status {
        EN_RECHERCHE, EN_STAGE, NONE;
    }


}
