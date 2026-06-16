package com.StageLink.StageLink_back.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Offre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_offre")
    private Long idOffre;

    @Column(length = 100, nullable = false, name = "titre_projet")
    private String titreProjet;

    @Column(length = 1000)
    private String description;

    @Column(length = 50)
    private String duree;

    @Column(length = 500 ,name = "objectifs_projet")
    private String objectifsProjet;

    @Column(length = 500)
    private String competence;

    private Integer remuneration;

    @ManyToOne
    @JoinColumn(name = "id_encadrant", nullable = false)
    private Encadrant encadrant;

    @ManyToOne
    @JoinColumn(name = "id_type", nullable = false)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "id_ville", nullable = false)
    private Ville ville;

    @Temporal(TemporalType.TIMESTAMP)
    @Column( updatable = false)
    private Date createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = new Date(); // Automatically set the created date
    }

    public Long getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(Long idOffre) {
        this.idOffre = idOffre;
    }

    public String getTitreProjet() {
        return titreProjet;
    }

    public void setTitreProjet(String titreProjet) {
        this.titreProjet = titreProjet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public Integer isRemuneration() {
        return remuneration;
    }

    public void setRemuneration(Integer remuneration) {
        this.remuneration = remuneration;
    }

    public Encadrant getEncadrant() {
        return encadrant;
    }

    public void setEncadrant(Encadrant encadrant) {
        this.encadrant = encadrant;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}


