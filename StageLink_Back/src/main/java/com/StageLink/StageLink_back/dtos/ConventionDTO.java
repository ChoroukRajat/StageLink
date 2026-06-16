package com.StageLink.StageLink_back.dtos;

public class ConventionDTO {
    private String studentNom;
    private String studentPrenom;
    private String studentEmail;
    private String encadrantNom;
    private String encadrantPrenom;
    private String offreTitle;
    private String descriptionOffre;
    private String duree;
    private String objectifsProjet;
    private Integer remuneration;
    private String companyName;
    private boolean confirmationEtudiant;
    private boolean valide;

    public String getStudentNom() {
        return studentNom;
    }

    public void setStudentNom(String studentNom) {
        this.studentNom = studentNom;
    }

    public String getStudentPrenom() {
        return studentPrenom;
    }

    public void setStudentPrenom(String studentPrenom) {
        this.studentPrenom = studentPrenom;
    }

    public String getEncadrantNom() {
        return encadrantNom;
    }

    public void setEncadrantNom(String encadrantNom) {
        this.encadrantNom = encadrantNom;
    }

    public String getEncadrantPrenom() {
        return encadrantPrenom;
    }

    public void setEncadrantPrenom(String encadrantPrenom) {
        this.encadrantPrenom = encadrantPrenom;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getObjectifsProjet() {
        return objectifsProjet;
    }

    public void setObjectifsProjet(String objectifsProjet) {
        this.objectifsProjet = objectifsProjet;
    }

    public Integer getRemuneration() {
        return remuneration;
    }

    public void setRemuneration(Integer remuneration) {
        this.remuneration = remuneration;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }



    public String getOffreTitle() {
        return offreTitle;
    }

    public void setOffreTitle(String offreTitle) {
        this.offreTitle = offreTitle;
    }

    public String getDescriptionOffre() {
        return descriptionOffre;
    }

    public void setDescriptionOffre(String descriptionOffre) {
        this.descriptionOffre = descriptionOffre;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public boolean isConfirmationEtudiant() {
        return confirmationEtudiant;
    }

    public void setConfirmationEtudiant(boolean confirmationEtudiant) {
        this.confirmationEtudiant = confirmationEtudiant;
    }

    public boolean isValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }
}
