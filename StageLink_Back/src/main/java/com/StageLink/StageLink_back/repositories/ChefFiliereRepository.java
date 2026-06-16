package com.StageLink.StageLink_back.repositories;

import com.StageLink.StageLink_back.entities.ChefFiliere;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefFiliereRepository extends JpaRepository<ChefFiliere, Long> {
    @Query("SELECT e FROM ChefFiliere e WHERE e.utilisateur.id = :utilisateurId")
    ChefFiliere findByUtilisateurId(@Param("utilisateurId") Long utilisateurId);
}
