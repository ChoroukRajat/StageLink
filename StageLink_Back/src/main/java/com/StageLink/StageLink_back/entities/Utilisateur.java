package com.StageLink.StageLink_back.entities;

import jakarta.persistence.*;
import lombok.*;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Utilisateur")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur")
    private Long idUtilisateur;

    @Column(length = 100,  unique = true)
    private String email;

    @Column(length = 255)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role",  columnDefinition = "NUMBER")
    private Role role;


    public Long getIdUtilisateur() {
        return idUtilisateur;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

