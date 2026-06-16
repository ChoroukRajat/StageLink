package com.StageLink.StageLink_back.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Candidature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCandidature;

    @ManyToOne
    @JoinColumn(name = "id_offre")
    private Offre offre;

    @ManyToOne
    @JoinColumn(name = "id_etudiant" , columnDefinition = "NUMBER")
    private Etudiant etudiant;

    @Column(name="valide")
    private boolean valide;



    @Column(name="confirmation_Etudiant")
    private boolean confirmationEtudiant;

    @Column(length = 500)
    private String evaluation;

    @Column(name="cvUrl", length = 250)
    private String cvUrl;

    @Column(name="motivationUrl", length = 250)
    private String motivationUrl;

    @Column(name="rapportUrl", length = 250)
    private String rapportUrl;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = new Date(); // Automatically set the created date
    }



    public Long getIdCandidature() {
        return idCandidature;
    }

    public void setIdCandidature(Long idCandidature) {
        this.idCandidature = idCandidature;
    }

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public boolean isValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }



    public boolean isConfirmationEtudiant() {
        return confirmationEtudiant;
    }

    public void setConfirmationEtudiant(boolean confirmationEtudiant) {
        this.confirmationEtudiant = confirmationEtudiant;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public String getMotivationUrl() {
        return motivationUrl;
    }

    public void setMotivationUrl(String motivationUrl) {
        this.motivationUrl = motivationUrl;
    }

    public String getRapportUrl() {
        return rapportUrl;
    }

    public void setRapportUrl(String rapportUrl) {
        this.rapportUrl = rapportUrl;
    }
}

