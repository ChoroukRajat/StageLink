package com.StageLink.StageLink_back.repositories;

import com.StageLink.StageLink_back.entities.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface OffreRepository extends JpaRepository<Offre, Long> {
    @Query("SELECT o FROM Offre o " +
            "JOIN OffreSelectionnee os ON os.offre.idOffre = o.idOffre " +
            "JOIN ChefFiliere cf ON os.chefFiliere.idChefFiliere = cf.idChefFiliere " +
            "JOIN Etudiant e ON e.chefFiliere.idChefFiliere = cf.idChefFiliere " +
            "WHERE e.idEtudiant = :etudiantId")
    List<Offre> findOffresByEtudiantId(@Param("etudiantId") Long etudiantId);

    @Query("SELECT o FROM Offre o " +
            "WHERE (:idVille IS NULL OR o.ville.id = :idVille) " +
            "ORDER BY o.createdDate ASC")
    List<Offre> findAllOffresFilterByVilleSortedByDateAsc(@Param("idVille") Long idVille);

    @Query("SELECT o FROM Offre o " +
            "WHERE (:idVille IS NULL OR o.ville.id = :idVille) " +
            "ORDER BY o.createdDate DESC")
    List<Offre> findAllOffresFilterByVilleSortedByDateDesc(@Param("idVille") Long idVille);



    //dashboard

    @Query("SELECT o.idOffre AS offerId, o.titreProjet AS offerTitle, COUNT(c.idCandidature) AS studentCount " +
            "FROM Candidature c JOIN c.offre o JOIN o.encadrant e " +
            "WHERE e.idEncadrant = :encadrantId " +
            "GROUP BY o.idOffre, o.titreProjet")
    List<Map<String, Object>> getStudentsPerOffer(@Param("encadrantId") Long encadrantId);

    @Query("SELECT o.idOffre AS offerId, o.titreProjet AS offerTitle, COUNT(c.idCandidature) AS validatedCount " +
            "FROM Candidature c JOIN c.offre o JOIN o.encadrant e " +
            "WHERE e.idEncadrant = :encadrantId AND c.valide = true " +
            "GROUP BY o.idOffre, o.titreProjet")
    List<Map<String, Object>> getValidatedApplicationsPerOffer(@Param("encadrantId") Long encadrantId);


    @Query("SELECT o.idOffre AS offerId, o.titreProjet AS offerTitle, COUNT(c.idCandidature) AS evaluatedCount " +
            "FROM Candidature c JOIN c.offre o JOIN o.encadrant e " +
            "WHERE e.idEncadrant = :encadrantId AND c.evaluation IS NOT NULL " +
            "GROUP BY o.idOffre, o.titreProjet")
    List<Map<String, Object>> getEvaluatedApplicationsPerOffer(@Param("encadrantId") Long encadrantId);


    @Query("SELECT ec.nomEcole AS schoolName, COUNT(c.idCandidature) AS studentCount " +
            "FROM Candidature c " +
            "JOIN c.etudiant e " +
            "JOIN e.chefFiliere cf " +
            "JOIN cf.ecole ec "+
            "JOIN c.offre o " +
            "JOIN o.encadrant enc " +
            "WHERE enc.idEncadrant = :encadrantId " +
            "GROUP BY ec.nomEcole")
    List<Map<String, Object>> getStudentsDistributionBySchool(@Param("encadrantId") Long encadrantId);


}

