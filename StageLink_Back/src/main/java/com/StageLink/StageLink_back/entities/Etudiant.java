package com.StageLink.StageLink_back.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ETUDIANT")
    private Long idEtudiant;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "telephone")
    private String telephone;

    @Column(length = 250, name = "adresse_etudiant")
    private String adresseEtudiant;


    private int anneePromotion;

    @Column(length = 50)
    private String mentionEtudiant;

    private boolean assuranceEtudiant;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name="id_status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "id_chef_filiere", nullable = false)
    private ChefFiliere chefFiliere;

    public Long getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(Long idEtudiant) {
        this.idEtudiant = idEtudiant;
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

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public ChefFiliere getChefFiliere() {
        return chefFiliere;
    }

    public void setChefFiliere(ChefFiliere chefFiliere) {
        this.chefFiliere = chefFiliere;
    }
}

