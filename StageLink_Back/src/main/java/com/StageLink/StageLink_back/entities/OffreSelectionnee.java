package com.StageLink.StageLink_back.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OffreSelectionnee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOffreSelectionnee;

    @ManyToOne
    @JoinColumn(name = "id_offre", nullable = false)
    private Offre offre;

    @ManyToOne
    @JoinColumn(name = "id_chef_filiere", nullable = false)
    private ChefFiliere chefFiliere;


    public Long getIdOffreSelectionnee() {
        return idOffreSelectionnee;
    }

    public void setIdOffreSelectionnee(Long idOffreSelectionnee) {
        this.idOffreSelectionnee = idOffreSelectionnee;
    }

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public ChefFiliere getChefFiliere() {
        return chefFiliere;
    }

    public void setChefFiliere(ChefFiliere chefFiliere) {
        this.chefFiliere = chefFiliere;
    }
}

