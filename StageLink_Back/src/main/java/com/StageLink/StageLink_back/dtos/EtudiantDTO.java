package com.StageLink.StageLink_back.dtos;

import lombok.Data;

@Data
public class EtudiantDTO {
    private Long idEtudiant;
    private String adresseEtudiant;
    private int anneePromotion;
    private String mentionEtudiant;
    private boolean assuranceEtudiant;
    private Long utilisateurId;
    private Long chefFiliereId;
    private String nom;
    private String prenom;
    private String telephone;


    public Long getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(Long idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public String getAdresseEtudiant() {
        return adresseEtudiant;
    }

    public void setAdresseEtudiant(String adresseEtudiant) {
        this.adresseEtudiant = adresseEtudiant;
    }

    public int getAnneePromotion() {
        return anneePromotion;
    }

    public void setAnneePromotion(int anneePromotion) {
        this.anneePromotion = anneePromotion;
    }

    public String getMentionEtudiant() {
        return mentionEtudiant;
    }

    public void setMentionEtudiant(String mentionEtudiant) {
        this.mentionEtudiant = mentionEtudiant;
    }

    public boolean isAssuranceEtudiant() {
        return assuranceEtudiant;
    }

    public void setAssuranceEtudiant(boolean assuranceEtudiant) {
        this.assuranceEtudiant = assuranceEtudiant;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public Long getChefFiliereId() {
        return chefFiliereId;
    }

    public void setChefFiliereId(Long chefFiliereId) {
        this.chefFiliereId = chefFiliereId;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
