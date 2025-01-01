package com.StageLink.StageLink_back.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChefFiliere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idChefFiliere;

    @Column(nullable = false, length = 200)
    private String nomFiliere;

    @Column(nullable = false, length = 200)
    private String domaineDetude;

    @ManyToOne
    @JoinColumn(name = "id_ecole", nullable = false)
    private Ecole ecole;

    @OneToOne
    @JoinColumn(name = "id_chef_filiere")
    private Utilisateur utilisateur;
}
