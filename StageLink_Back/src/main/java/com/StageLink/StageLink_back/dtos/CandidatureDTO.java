package com.StageLink.StageLink_back.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class CandidatureDTO {
    private Long idCandidature;
    private Long offreSelectionneeId;
    private Long etudiantId;
    private boolean valide;
    private boolean confirmationEtudiant;
    private String evaluation;
    private Date createDate;
    private String nomEtudiant;
    private String prenomEtudiant;
    private String titreProjet;
    private String cvUrl;
    private String motivationUrl;
    private String rapportUrl;

    public String getNomEtudiant() {
        return nomEtudiant;
    }

    public void setNomEtudiant(String nomEtudiant) {
        this.nomEtudiant = nomEtudiant;
    }

    public String getPrenomEtudiant() {
        return prenomEtudiant;
    }

    public void setPrenomEtudiant(String prenomEtudiant) {
        this.prenomEtudiant = prenomEtudiant;
    }

    public String getTitreProjet() {
        return titreProjet;
    }

    public void setTitreProjet(String titreProjet) {
        this.titreProjet = titreProjet;
    }

    public Long getIdCandidature() {
        return idCandidature;
    }

    public void setIdCandidature(Long idCandidature) {
        this.idCandidature = idCandidature;
    }

    public Long getOffreSelectionneeId() {
        return offreSelectionneeId;
    }

    public void setOffreSelectionneeId(Long offreSelectionneeId) {
        this.offreSelectionneeId = offreSelectionneeId;
    }

    public Long getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(Long etudiantId) {
        this.etudiantId = etudiantId;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

