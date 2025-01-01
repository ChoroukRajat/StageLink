package com.StageLink.StageLink_back.repositories;

import com.StageLink.StageLink_back.entities.ChefFiliere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefFiliereRepository extends JpaRepository<ChefFiliere, Long> {
}
