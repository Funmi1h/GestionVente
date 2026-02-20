/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metier;

/**
 *
 * @author YACOUBOU
 */
public class TopProduit {

    private String nomArticle;
    private int totalVendu;
    private double chiffreArticle;

    public TopProduit(String nomArticle, int totalVendu, double chiffreArticle) {
        this.nomArticle = nomArticle;
        this.totalVendu = totalVendu;
        this.chiffreArticle = chiffreArticle;
    }

    public String getNomArticle() { return nomArticle; }
    public int getTotalVendu() { return totalVendu; }
    public double getChiffreArticle() { return chiffreArticle; }
}