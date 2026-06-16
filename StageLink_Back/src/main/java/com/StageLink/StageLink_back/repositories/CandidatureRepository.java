package com.StageLink.StageLink_back.repositories;


import com.StageLink.StageLink_back.entities.Candidature;
import com.StageLink.StageLink_back.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CandidatureRepository extends JpaRepository<Candidature, Long> {
    List<Candidature> findByEtudiant(Etudiant etudiant);

    Optional<Candidature> findByIdCandidature(Long idCandidature);

    //hnaya fhadi li t7t you ll know why you need to keep it as it is
    //n9d comme solution n3ti id chef filiere comme parametre

    @Query("SELECT c FROM Candidature c " +
            "JOIN c.offre o " +
            "JOIN o.encadrant e " +
            "JOIN e.utilisateur u " +
            "JOIN c.etudiant et " +
            "JOIN et.chefFiliere cf " +
            "JOIN cf.ecole ec " +
            "WHERE u.idUtilisateur = :idEncadrant " +
            "AND (:idEcole IS NULL OR ec.id = :idEcole) " +
            "AND (:isValide IS NULL OR c.valide = :isValide) " +
            "AND (:idTypeOffre IS NULL OR o.type.id = :idTypeOffre)")
    List<Candidature> findCandidaturesByEncadrant(
            @Param("idEncadrant") Long idEncadrant,
            @Param("idEcole") Long idEcole,
            @Param("isValide") Boolean isValide,
            @Param("idTypeOffre") Long idTypeOffre);


    Candidature getCandidatureByIdCandidature(Long idCandidature);


    //hna dyal dashboard:
    @Query("SELECT " +
            "SUM(CASE WHEN c.valide = true THEN 1 ELSE 0 END) AS validated, " +
            "SUM(CASE WHEN c.valide = false THEN 1 ELSE 0 END) AS notValidated " +
            "FROM Candidature c, Etudiant e, ChefFiliere cf " +
            "WHERE c.etudiant = e " +
            "AND e.chefFiliere = cf " +
            "AND cf.idChefFiliere = :id")
    Map<String, Long> countValidatedApplicationsForChefFiliere(@Param("id") Long id);

    @Query("SELECT o.titreProjet AS titreProjet, COUNT(c.idCandidature) AS totalApplications " +
            "FROM Candidature c, Offre o, Etudiant e, ChefFiliere cf " +
            "WHERE c.offre = o " +
            "AND c.etudiant = e " +
            "AND e.chefFiliere = cf " +
            "AND cf.idChefFiliere = :id " +
            "GROUP BY o.titreProjet")
    List<Map<String, Object>> countApplicationsByOffreForChefFiliere(@Param("id") Long id);

    @Query("SELECT e.nom AS etudiantNom, e.prenom AS etudiantPrenom, COUNT(c.idCandidature) AS totalApplications " +
            "FROM Candidature c, Etudiant e, ChefFiliere cf " +
            "WHERE c.etudiant = e " +
            "AND e.chefFiliere = cf " +
            "AND cf.idChefFiliere = :id " +
            "GROUP BY e.nom, e.prenom")
    List<Map<String, Object>> countApplicationsByEtudiantForChefFiliere(@Param("id") Long id);

    @Query("SELECT t.nomType AS typeName, COUNT(c.idCandidature) AS totalApplications " +
            "FROM Candidature c " +
            "JOIN c.offre o " +
            "JOIN o.type t " +
            "JOIN c.etudiant e " +
            "JOIN e.chefFiliere cf " +
            "WHERE cf.idChefFiliere = :id " +
            "GROUP BY t.nomType")
    List<Map<String, Object>> countApplicationsByTypeForChefFiliere(@Param("id") Long id);




}

