package com.StageLink.StageLink_back.repositories;

import com.StageLink.StageLink_back.entities.Encadrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface EncadrantRepository extends JpaRepository<Encadrant, Long> {

    @Query("SELECT e FROM Encadrant e WHERE e.utilisateur.id = :utilisateurId")
    Encadrant  findByUtilisateurId(@Param("utilisateurId") Long utilisateurId);




}
