/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modeles;

/**
 *
 * @author YACOUBOU
 */
import Metier.Client;
import DAO.ClientDAO;
import java.sql.Connection;
import java.util.List;

public class ClientModel {
    private ClientDAO clientDAO;
    
    public ClientModel(Connection connection) {
        this.clientDAO = new ClientDAO(connection);
    }
    
    /**
     * Ajouter un nouveau client
     */
    public boolean ajouterClient(Client client) {
        // Validation des données
        if (client.getNom() == null || client.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom est obligatoire");
        }
        if (client.getPrenom() == null || client.getPrenom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le prénom est obligatoire");
        }
        if (client.getEmail() != null && !client.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email invalide");
        }
        
        return clientDAO.ajouter(client);
    }
    
    /**
     * Modifier un client existant
     */
    public boolean modifierClient(Client client) {
        if (client.getIdClient() <= 0) {
            throw new IllegalArgumentException("ID client invalide");
        }
        
        // Validation des données
        if (client.getNom() == null || client.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom est obligatoire");
        }
        
        return clientDAO.modifier(client, client.getIdClient());
    }
    
    /**
     * Supprimer un client
     */
    public boolean supprimerClient(int idClient) {
        if (idClient <= 0) {
            throw new IllegalArgumentException("ID client invalide");
        }
        
        // Vérifier si le client a des commandes avant suppression
        // Cette logique peut être ajoutée ici
        
        return clientDAO.supprime(idClient);
    }
    
    /**
     * Récupérer tous les clients
     */
    public List<Client> listerTousClients() {
        return clientDAO.lists();
    }
    
    /**
     * Rechercher un client par ID
     */
    public Client trouverClientParId(int idClient) {
        if (idClient <= 0) {
            throw new IllegalArgumentException("ID client invalide");
        }
        
        return clientDAO.rechercheClient(idClient);
    }
    
        /**
     * Rechercher un client par ID
     */
    public Client trouverClientParEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("ID client invalide");
        }
        
        return clientDAO.rechercheClientParMail(email);
    }
    
    /**
     * Rechercher des clients par nom
     */
    public List<Client> rechercherClientsParNom(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de recherche est obligatoire");
        }
        
        List<Client> tousClients = clientDAO.lists();
        return tousClients.stream()
                .filter(c -> c.getNom().toLowerCase().contains(nom.toLowerCase()))
                .toList();
    }
    
    /**
     * Vérifier si un email existe déjà
     */
    public boolean emailExiste(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        List<Client> clients = clientDAO.lists();
        return clients.stream()
                .anyMatch(c -> email.equalsIgnoreCase(c.getEmail()));
    }
    
    /**
     * Obtenir le nombre total de clients
     */
    public int getNombreTotalClients() {
        return clientDAO.lists().size();
    }
}