package com.StageLink.StageLink_back.repositories;

import com.StageLink.StageLink_back.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

    // Custom SQL query for finding Etudiant by Utilisateur's ID
    @Query("SELECT e FROM Etudiant e WHERE e.utilisateur.id = :utilisateurId")
    Etudiant findByUtilisateurId(@Param("utilisateurId") Long utilisateurId);

    // Custom SQL query for finding Etudiant by ChefFiliere's ID and AnneePromotion
    @Query("SELECT e FROM Etudiant e WHERE e.chefFiliere.id = :idChefFiliere AND e.anneePromotion = :anneePromotion")
    List<Etudiant> findByChefFiliereIdAndAnneePromotion(@Param("idChefFiliere") Long idChefFiliere, @Param("anneePromotion") int anneePromotion);

    @Query("SELECT e.anneePromotion, COUNT(e) FROM Etudiant e WHERE e.chefFiliere.id = :chefFiliereId GROUP BY e.anneePromotion")
    List<Object[]> countStudentsByPromotion(@Param("chefFiliereId") Long chefFiliereId);
}


