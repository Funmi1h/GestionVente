/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metier;

/**
 *
 * @author Héloïse
 */
public class Client {
    private int idClient;
    private String nom, prenom, email, telephone, adresse, dateCreate, dateUpdate, motDePasse;
    // Deux constructeurs
    public Client() {
    }
    
    public Client(String nom, String prenom, String email, String adresse , String motDePasse) {
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.telephone=telephone;
        this.adresse=adresse;
        this.motDePasse = motDePasse;
    }

    
    // Les Getters
    public int getIdClient() {
        return idClient;
    }
    
    
    public String getMotDePasse(){
        return motDePasse;
    }
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    

    public String getAdresse() {
        return adresse;
    }
    
    public String getDateCreate() {
        return dateCreate;
    }

    public String getDateUpdate() {
        return dateUpdate;
    }
    
    
    // Les Setters

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
    
    

    
    
}
