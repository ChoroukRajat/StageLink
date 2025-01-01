package com.StageLink.StageLink_back.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntreprise;

    @Column(nullable = false, length = 250)
    private String nomEntreprise;

    @Column(nullable = false, length = 50)
    private String formeJuridique;

    @Column(nullable = false, length = 250)
    private String adresseEntreprise;

    @Column(nullable = false, length = 50)
    private String telephoneEntreprise;

    @Column(nullable = false, length = 50)
    private String faxEntreprise;

    @Column(nullable = false, length = 50)
    private String emailEntreprise;

    @OneToOne
    @JoinColumn(name = "id_entreprise")
    private Utilisateur utilisateur;
}

