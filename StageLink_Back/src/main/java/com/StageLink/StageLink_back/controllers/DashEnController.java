package com.StageLink.StageLink_back.controllers;


import com.StageLink.StageLink_back.repositories.OffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard/en")
public class DashEnController {

    @Autowired
    private OffreRepository offreRepository;

    // Get the number of students per offer
    @GetMapping("/students-per-offer/{encadrantId}")
    public List<Map<String, Object>> getStudentsPerOffer(@PathVariable Long encadrantId) {
        return offreRepository.getStudentsPerOffer(encadrantId);
    }

    // Get the number of validated applications per offer
    @GetMapping("/validated-applications/{encadrantId}")
    public List<Map<String, Object>> getValidatedApplicationsPerOffer(@PathVariable Long encadrantId) {
        return offreRepository.getValidatedApplicationsPerOffer(encadrantId);
    }

    // Get the number of evaluated applications per offer
    @GetMapping("/evaluated-applications/{encadrantId}")
    public List<Map<String, Object>> getEvaluatedApplicationsPerOffer(@PathVariable Long encadrantId) {
        return offreRepository.getEvaluatedApplicationsPerOffer(encadrantId);
    }

    // Get the distribution of students by school
    @GetMapping("/students-distribution/{encadrantId}")
    public List<Map<String, Object>> getStudentsDistributionBySchool(@PathVariable Long encadrantId) {
        return offreRepository.getStudentsDistributionBySchool(encadrantId);
    }
}
