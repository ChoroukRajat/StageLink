package com.StageLink.StageLink_back.dtos;

import lombok.Data;

@Data
public class ChefFiliereDTO {
    private Long idChefFiliere;
    private String nomFiliere;
    private String domaineDetude;
    private Long ecoleId;
    private Long utilisateurId;
    private String nom;
    private String prenom;

    public Long getIdChefFiliere() {
        return idChefFiliere;
    }

    public void setIdChefFiliere(Long idChefFiliere) {
        this.idChefFiliere = idChefFiliere;
    }

    public String getNomFiliere() {
        return nomFiliere;
    }

    public void setNomFiliere(String nomFiliere) {
        this.nomFiliere = nomFiliere;
    }

    public String getDomaineDetude() {
        return domaineDetude;
    }

    public void setDomaineDetude(String domaineDetude) {
        this.domaineDetude = domaineDetude;
    }

    public Long getEcoleId() {
        return ecoleId;
    }

    public void setEcoleId(Long ecoleId) {
        this.ecoleId = ecoleId;
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

