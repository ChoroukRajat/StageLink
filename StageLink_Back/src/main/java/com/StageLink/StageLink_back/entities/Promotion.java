package com.StageLink.StageLink_back.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPromotion;

    @Column(nullable = false)
    private Integer nbInscrits;

    @Column(nullable = false)
    private Integer nbRecus;

    @ManyToOne
    @JoinColumn(name = "id_chef_filiere", nullable = false)
    private ChefFiliere chefFiliere;
}
