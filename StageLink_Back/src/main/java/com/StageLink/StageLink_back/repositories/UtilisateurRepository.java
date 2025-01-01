package com.StageLink.StageLink_back.repositories;

import com.StageLink.StageLink_back.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    // Custom query to find a user by email
    Utilisateur findByEmail(String email);

    // Custom query to find all users with a specific role
    List<Utilisateur> findByRoleNomRole(String nomRole);

    // Custom query to find users by name (case-insensitive)
    List<Utilisateur> findByNomIgnoreCase(String nom);
}
