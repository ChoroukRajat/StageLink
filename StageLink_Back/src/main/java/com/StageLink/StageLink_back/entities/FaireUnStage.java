package com.StageLink.StageLink_back.entities;

import com.StageLink.StageLink_back.entities.embedded.FaireUnStageId;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaireUnStage {
    @EmbeddedId
    private FaireUnStageId id;

    @ManyToOne
    @MapsId("idOffre")
    @JoinColumn(name = "id_offre")
    private Offre offre;

    @ManyToOne
    @MapsId("idChefFiliere")
    @JoinColumn(name = "id_chef_filiere")
    private ChefFiliere chefFiliere;

    @ManyToOne
    @MapsId("idEtudiant")
    @JoinColumn(name = "id_etudiant")
    private Etudiant etudiant;

    @Column(nullable = false, length = 200)
    private String appreciation;

    @Column(nullable = false)
    private Float noteStage;

    @Column(nullable = false, length = 200)
    private String rapportStage;

    @Column(nullable = false)
    private Integer anneeStage;

    @Column(nullable = false)
    private java.time.LocalDate dateDebut;

    @Column(nullable = false)
    private java.time.LocalDate dateFin;
}

