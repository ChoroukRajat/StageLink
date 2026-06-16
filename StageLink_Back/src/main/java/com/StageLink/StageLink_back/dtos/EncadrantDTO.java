package com.StageLink.StageLink_back.dtos;

import lombok.Data;

@Data
public class EncadrantDTO {
    private Long idEncadrant;
    private String profession;
    private String departement;
    private Long entrepriseId;
    private Long utilisateurId;
    private String nom;
    private String prenom;


    public Long getIdEncadrant() {
        return idEncadrant;
    }

    public void setIdEncadrant(Long idEncadrant) {
        this.idEncadrant = idEncadrant;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public Long getEntrepriseId() {
        return entrepriseId;
    }

    public void setEntrepriseId(Long entrepriseId) {
        this.entrepriseId = entrepriseId;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
