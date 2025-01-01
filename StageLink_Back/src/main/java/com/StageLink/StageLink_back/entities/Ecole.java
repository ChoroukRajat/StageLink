package com.StageLink.StageLink_back.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ecole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEcole;

    @Column(nullable = false, length = 50)
    private String nomEcole;

    @Column(nullable = false, length = 250)
    private String adresseEcole;

    @Column(nullable = false, length = 50)
    private String telephoneEcole;

    @Column(nullable = false, length = 50)
    private String emailEcole;

    @OneToOne
    @JoinColumn(name = "id_ecole")
    private Utilisateur utilisateur;
}

