    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metier;

import DAO.ClientDAO;

/**
 *
 * @author YACOUBOU
 */

public class SessionUtilisateur {
    private static SessionUtilisateur instance;
    private Utilisateur utilisateurConnecte;
    private Client clientAssocie;
    
    private SessionUtilisateur() {
        // Constructeur privé pour Singleton
    }
    
    public static SessionUtilisateur getInstance() {
        if (instance == null) {
            instance = new SessionUtilisateur();
        }
        return instance;
    }
    
    // Getters/Setters
    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }
    
    public void setUtilisateurConnecte(Utilisateur utilisateur) {
        this.utilisateurConnecte = utilisateur;
    }
    
    public Client getClientAssocie() {
        return clientAssocie;
    }
    
    public void setClientAssocie(Client client) {
        this.clientAssocie = client;
    }
    
    // Méthodes de vérification
    public boolean estConnecte() {
        return utilisateurConnecte != null;
    }
    
    public boolean estAdmin() {
        return estConnecte() && utilisateurConnecte.estAdmin();
    }
    
    public boolean estClient() {
        return estConnecte() && utilisateurConnecte.estClient();
    }
    
    public int getIdClientSession() {
        if (estClient() && utilisateurConnecte.getIdClient() != 0) {
            return utilisateurConnecte.getIdClient();
        }
        return 0;
    }
    
    // Déconnexion
    public void deconnecter() {
        this.utilisateurConnecte = null;
        this.clientAssocie = null;
    }
    public boolean chargerClientAssocie(ClientDAO clientDAO) {
        if (estClient() && utilisateurConnecte.getIdClient() != 0) {
            Client client = clientDAO.rechercheClient(utilisateurConnecte.getIdClient());
            if (client != null) {
                this.clientAssocie = client;
                return true;
            }
        }
        return false;
    }
}