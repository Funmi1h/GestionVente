/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modeles;

/**
 *
 * @author YACOUBOU
 */
// com.gestionvente.service.AuthenticationService.java



import Metier.Utilisateur;
import Metier.Client;
import Metier.SessionUtilisateur;
import DAO.UtilisateurDAO;
import DAO.ClientDAO;
import java.sql.Connection;

public class AuthentificationModel {
    private UtilisateurDAO utilisateurDAO;
    private ClientDAO clientDAO;
    private SessionUtilisateur session;
    
    public AuthentificationModel(Connection connection) {
        this.utilisateurDAO = new UtilisateurDAO(connection);
        this.clientDAO = new ClientDAO(connection);
    }
    
    /**
     * Authentifier un utilisateur
     */
    public boolean authentifier(String email, String motDePasse) {
        Utilisateur utilisateur = utilisateurDAO.authentifierSecurise(email, motDePasse);
        
        if (utilisateur != null) {
            System.out.println("ça fonctionne : "+utilisateur.getEmail());
            // Initialiser la session
            SessionUtilisateur session = SessionUtilisateur.getInstance();
            session.setUtilisateurConnecte(utilisateur);
            System.out.println("ça fonctionne avec session: "+session.getUtilisateurConnecte().getEmail());
            System.out.println("ça fonctionne avec session: "+session.getUtilisateurConnecte().getRole().getLibelle());
            // Si c'est un client, charger ses informations
            System.out.println(utilisateur.estClient() && utilisateur.getIdClient() != 0);
            System.out.println("Id du client : "+utilisateur.getIdClient());
            if (utilisateur.estClient() && utilisateur.getIdClient() != 0) {
                System.out.println("ça fonctionne avec session + condition: "+session.getUtilisateurConnecte().getEmail());
                Client client = clientDAO.rechercheClient(utilisateur.getIdClient());
                System.out.println("ça fonctionne avec session + condition: "+session.getUtilisateurConnecte().getEmail());
                session.setClientAssocie(client);
                
            }else{
                System.out.println("ça fonctionne avec session + condition else: "+session.getUtilisateurConnecte().getEmail());
            }
            
            return true;
        }
        System.out.println("ça ne fonctionne pas: null");
        return false;
    }
    
    /**
     * Inscription d'un nouveau client
     */
    public boolean inscrireClient(Client client, String motDePasse) {
        try {
            // 1. Vérifier si l'email n'existe pas déjà
            if (utilisateurDAO.findByEmail(client.getEmail()) != null) {
                throw new Exception("Cet email est déjà utilisé");
            }
            
            // 2. Créer le client
            if (!clientDAO.ajouter(client)) {
                throw new Exception("Erreur lors de la création du client");
            }
            
            // 3. Créer l'utilisateur associé
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setEmail(client.getEmail());
            utilisateur.setMotDePasse(motDePasse);
            utilisateur.setRole(Utilisateur.RoleUtilisateur.CLIENT);
            utilisateur.setIdClient(client.getIdClient());
            
            return utilisateurDAO.creerUtilisateur(utilisateur);
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean inscrireAdmin(Client client, String motDePasse) {
        try {
            // 1. Vérifier si l'email n'existe pas déjà
            if (utilisateurDAO.findByEmail(client.getEmail()) != null) {
                throw new Exception("Cet email est déjà utilisé");
            }
            
            // 2. Créer le client
            if (!clientDAO.ajouter(client)) {
                throw new Exception("Erreur lors de la création du client");
            }
            
            // 3. Créer l'utilisateur associé
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setEmail(client.getEmail());
            utilisateur.setMotDePasse(motDePasse);
            utilisateur.setRole(Utilisateur.RoleUtilisateur.ADMIN);
            utilisateur.setIdClient(client.getIdClient());
            
            return utilisateurDAO.creerUtilisateur(utilisateur);
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deconnecter() {
        SessionUtilisateur.getInstance().deconnecter();
    }

    public boolean estConnecte() {
        return SessionUtilisateur.getInstance().estConnecte();
    }

    public boolean estAdministrateur() {
        return SessionUtilisateur.getInstance().estAdmin();
    }
    public boolean estClient() {
        return session.estClient();
    }
    public int getIdClientConnecte() {
        return SessionUtilisateur.getInstance().getIdClientSession();
    }
    
    /**
     * Récupérer le client connecté
     */
    public Client getClientConnecte() {
        return SessionUtilisateur.getInstance().getClientAssocie();
    }
    
    /**
     * Changer le mot de passe
     */
    public boolean changerMotDePasse(int idUtilisateur, String ancienMdp, String nouveauMdp) {
        // Vérifier l'ancien mot de passe
        Utilisateur utilisateur = utilisateurDAO.findByEmail(
            SessionUtilisateur.getInstance().getUtilisateurConnecte().getEmail()
        );
        
        if (utilisateur == null) {
            return false;
        }
        
        // Vérification simplifiée (en production: utiliser BCrypt.verify)
        if (!utilisateur.getMotDePasse().equals("hashed_" + ancienMdp)) {
            return false;
        }
        
        // Changer le mot de passe
        return utilisateurDAO.changerMotDePasse(idUtilisateur, nouveauMdp);
    }
    
    
    private void initialiserSession(Utilisateur utilisateur) {
        session.setUtilisateurConnecte(utilisateur);
        
        // Si c'est un client, charger ses informations
        if (utilisateur.estClient() && utilisateur.getIdClient() != 0) {
            chargerClientDansSession(utilisateur.getIdClient());
        }
    }
    
    /**
     * Charger le client dans la session
     */
    private void chargerClientDansSession(int idClient) {
        Client client = clientDAO.rechercheClient(idClient);
        if (client != null) {
            session.setClientAssocie(client);
            System.out.println("DEBUG: Client chargé dans session - ID: " + client.getIdClient() + 
                             ", Nom: " + client.getNom());
        } else {
            System.err.println("DEBUG: Client introuvable pour ID: " + idClient);
        }
    }
    
    public Client getClientConnecteSecurise() {
        Client client = getClientConnecte();
        if (client == null) {
            throw new IllegalStateException("Aucun client connecté ou client introuvable");
        }
        return client;
    }
    public boolean rechargerClient() {
        if (session.estClient() && session.getIdClientSession() != 0) {
            return session.chargerClientAssocie(clientDAO);
        }
        return false;
    }
}