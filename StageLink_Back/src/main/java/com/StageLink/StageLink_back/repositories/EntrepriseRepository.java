package com.StageLink.StageLink_back.repositories;

import com.StageLink.StageLink_back.entities.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {
}
