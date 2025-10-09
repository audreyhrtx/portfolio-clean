package com.porfolio.portfolio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;
    private String lastname;
    private String firstName;
    private String email;
    private String password;
    private String role;
    private String pseudo;
    // @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval
    // = true)
    // private List<Paint> likes;

    // public List<Paint> getLikes() {
    // return likes;
    // }

    // public void setLikes(List<Paint> likes) {
    // this.likes = likes;
    // }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    @Override
    public String toString() {
        return "utilisateur [lastname=" + lastname + ", firstName=" + firstName + ", email=" + email + ", password="
                + password + ", role=" + role + ", pseudo=" + pseudo + "]";
    }

}
