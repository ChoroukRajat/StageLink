package com.StageLink.StageLink_back.repositories;



import com.StageLink.StageLink_back.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {


    @Query("SELECT m FROM Message m " +
            "JOIN m.candidature c " +
            "JOIN c.etudiant e JOIN e.utilisateur u " +
            "WHERE u.idUtilisateur = :idUtilisateur AND m.read = false")
    List<Message> findUnreadMessagesByUtilisateurId(@Param("idUtilisateur") Long idUtilisateur);



}

