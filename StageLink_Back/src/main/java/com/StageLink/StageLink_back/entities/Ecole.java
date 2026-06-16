package com.StageLink.StageLink_back.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="Ecole")
public class Ecole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ecole")
    private Long idEcole;

    @Column(length = 100, nullable = false)
    private String nomEcole;

    @Column(length = 250)
    private String adresseEcole;

    @Column(length = 50)
    private String telephoneEcole;

    @Column(length = 100, nullable = true)
    private String emailEcole;

    @OneToOne
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    public Long getIdEcole() {
        return idEcole;
    }

    public void setIdEcole(Long idEcole) {
        this.idEcole = idEcole;
    }

    public String getNomEcole() {
        return nomEcole;
    }

    public void setNomEcole(String nomEcole) {
        this.nomEcole = nomEcole;
    }

    public String getAdresseEcole() {
        return adresseEcole;
    }

    public void setAdresseEcole(String adresseEcole) {
        this.adresseEcole = adresseEcole;
    }

    public String getTelephoneEcole() {
        return telephoneEcole;
    }

    public void setTelephoneEcole(String telephoneEcole) {
        this.telephoneEcole = telephoneEcole;
    }

    public String getEmailEcole() {
        return emailEcole;
    }

    public void setEmailEcole(String emailEcole) {
        this.emailEcole = emailEcole;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}

