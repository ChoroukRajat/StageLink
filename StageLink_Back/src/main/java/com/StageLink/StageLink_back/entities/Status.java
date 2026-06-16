package com.StageLink.StageLink_back.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status")
    private Long idStatus;

    @Column(nullable = false, unique = true, name = "nom_status")
    private String nomStatus;
}
