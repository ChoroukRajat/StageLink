package com.StageLink.StageLink_back.dtos;

import java.time.LocalDate;

public class UtilisateurDTO {

    private Long idUtilisateur;
    private String email;
    private String password;
    private String role;

    public UtilisateurDTO(){

    }
    // Constructor
    public UtilisateurDTO(Long idUtilisateur,  String email, String password,
                           String role) {
        this.idUtilisateur = idUtilisateur;

        this.email = email;
        this.password = password;

        this.role = role;
    }

    // Getters and Setters
    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
