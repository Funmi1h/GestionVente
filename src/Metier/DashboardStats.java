/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metier;

/**
 *
 * @author YACOUBOU
 */
public class DashboardStats {

    private int totalClients;
    private int totalCommandes;
    private double chiffreAffaires;
    private int commandesEnCours;

    public DashboardStats() {}

    public int getTotalClients() { return totalClients; }
    public void setTotalClients(int totalClients) { this.totalClients = totalClients; }

    public int getTotalCommandes() { return totalCommandes; }
    public void setTotalCommandes(int totalCommandes) { this.totalCommandes = totalCommandes; }

    public double getChiffreAffaires() { return chiffreAffaires; }
    public void setChiffreAffaires(double chiffreAffaires) { this.chiffreAffaires = chiffreAffaires; }

    public int getCommandesEnCours() { return commandesEnCours; }
    public void setCommandesEnCours(int commandesEnCours) { this.commandesEnCours = commandesEnCours; }
}