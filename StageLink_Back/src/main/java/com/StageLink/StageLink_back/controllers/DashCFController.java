package com.StageLink.StageLink_back.controllers;


import com.StageLink.StageLink_back.repositories.CandidatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard/cf")
public class DashCFController {

    @Autowired
    private CandidatureRepository candidatureRepository;

    @GetMapping("/{id}/validatedApplications")
    public ResponseEntity<Map<String, Long>> getValidatedApplications(@PathVariable Long id) {
        Map<String, Long> result = candidatureRepository.countValidatedApplicationsForChefFiliere(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/applicationsByOffre")
    public ResponseEntity<List<Map<String, Object>>> getApplicationsByOffre(@PathVariable Long id) {
        List<Map<String, Object>> result = candidatureRepository.countApplicationsByOffreForChefFiliere(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/applicationsByType")
    public ResponseEntity<List<Map<String, Object>>> getApplicationsByType(@PathVariable Long id) {
        List<Map<String, Object>> result = candidatureRepository.countApplicationsByTypeForChefFiliere(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/applicationsByEtudiant")
    public ResponseEntity<List<Map<String, Object>>> getApplicationsByEtudiant(@PathVariable Long id) {
        List<Map<String, Object>> result = candidatureRepository.countApplicationsByEtudiantForChefFiliere(id);
        return ResponseEntity.ok(result);
    }





}
