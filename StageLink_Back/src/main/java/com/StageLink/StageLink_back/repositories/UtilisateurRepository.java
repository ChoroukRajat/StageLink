package com.StageLink.StageLink_back.repositories;

import com.StageLink.StageLink_back.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);

    boolean existsByEmail(String email);

    Utilisateur getUtilisaturByIdUtilisateur(Long id);
}
