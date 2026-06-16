package com.StageLink.StageLink_back.controllers;


import com.StageLink.StageLink_back.entities.Candidature;
import com.StageLink.StageLink_back.repositories.CandidatureRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/docs")
public class EnDocsController {


    private final CandidatureRepository candidatureRepository;

    public EnDocsController(CandidatureRepository c){
        this.candidatureRepository = c;
    }


    @GetMapping("/{idc}")
    public ResponseEntity<?>getDocs(@PathVariable Long idc){
        Candidature candidature = candidatureRepository.findByIdCandidature(idc).get();
        return ResponseEntity.ok(candidature);
    }
}
