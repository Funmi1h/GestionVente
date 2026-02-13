/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metier;

/**
 *
 * @author Héloïse
 */
public class Article {
    private int id_article;
    private String nom;
    private float prix;
    private int stock;
    private String urlPhoto;
    private String createdAt;
    private String updatedAt;

    public Article() {
    }

    
    public Article(String nom, float prix, Integer stock) {
        this.nom = nom;
        this.prix = prix;
        this.stock = stock;
    }

    public int getId_article() {
        return id_article;
    }

    public String getNom() {
        return nom;
    }

    public float getPrix() {
        return prix;
    }

    public int getStock() {
        return stock;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
    //Les Setters
    public void setId_article(int id_article) {
        this.id_article = id_article;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean estDisponible(int quantite) {
        return quantite<=this.stock;
    }
     
}
