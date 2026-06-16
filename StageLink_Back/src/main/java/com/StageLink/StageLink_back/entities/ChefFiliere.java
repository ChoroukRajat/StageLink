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
    @Column(name="id_chef_filiere")
    private Long idChefFiliere;

    @Column(length = 200, nullable = false, name = "nom_filiere")
    private String nomFiliere;

    @Column(length = 200, name = "domaine_detude")
    private String domaineDetude;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @ManyToOne
    @JoinColumn(name = "id_ecole", nullable = false)
    private Ecole ecole;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;

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

    public Ecole getEcole() {
        return ecole;
    }

    public void setEcole(Ecole ecole) {
        this.ecole = ecole;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
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

