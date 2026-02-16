/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package groupe3_gestionvente;


import DAO.*;
import Metier.*;
import Modeles.*;
import java.sql.Connection;
import java.util.*;

import InterfaceIHM.MainFrame;
import InterfaceIHM.InterfaceClient;

import InterfaceIHM.FormulaireInscription;
import InterfaceIHM.FormulaireConnexion;
import InterfaceIHM.AdminForm;
import javax.swing.*;
import Controllers.ClientController;

//import InterfaceIHM.FormAddFournisseur;


/**
 *
 * @author Héloïse
 */
public class Groupe3_GestionVente {
    private static Connection connection=ConnexionDB.connect();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
/*
        //ConnexionDB.connect();
        
        System.out.println("=== Zone de test ===\n");
        
        
        Client c = new Client("LAMINE", "Yamal", "yamal@gmail.com", "1233444", "Rue 10");
        Client ch = new Client("CHRITIANO", "Ronaldo", "Ronaldo@gmail.com", "1233444", "Rue 10");
        Client c3 = new Client("YACOUBOU", "Faoussane", "faoussane@gmail.com", "1233444", "Rue 10");
        ClientDAO c1 = new ClientDAO(connection);
        //c1.ajouter(c);
        //c1.ajouter(c3);
        System.out.println("La liste avant suppression");
        
        System.out.println("_____________PRODUITS______________");
        Article a = new Article("Orange", 300, 30);
        /*Article a1 = new Article("Ananas", 1000, 8);
        Article a2 = new Article("Ananas", 500, 20);
        
        aD.ajouter(a);
        aD.ajouter(a1);
        aD.ajouter(a2);*/
/*
        ArticleDAO aD = new ArticleDAO(connection);
        
        System.out.println("La liste avant suppression");
        for (Article arg : aD.lists()) {
            System.out.println(arg.getNom());
        }
        //aD.modifier(a, 6);
        //aD.supprime(5);
        System.out.println("La liste apres suppression");
        for (Article arg : aD.lists()) {
            System.out.println(arg.getNom());
        }
        System.out.println("_____________Recherche______________");
        System.out.println("Résultat : " + aD.rechercheArticle(6).getNom());
        
        
        // Création des modèles
        AuthentificationModel authModel = new AuthentificationModel(connection);
        ClientModel clientModel = new ClientModel(connection);
        PanierModel panierModel = new PanierModel(connection);
        CommandeModel commandeModel = new CommandeModel(connection);
        
        
        
           
        /*System.out.println("TEST INSCRIPTION CLIENT \n");
        
        // 1. INSCRIPTION
        System.out.println("1. Inscription d'un nouveau client...");
        Client nouveauClient = new Client();
        nouveauClient.setNom("Dupont");
        nouveauClient.setPrenom("Jean");
        nouveauClient.setEmail("dupont@email.com");
        nouveauClient.setTelephone("0123456789");
        nouveauClient.setAdresse("123 Rue de Paris");
        
        boolean inscriptionReussie = authModel.inscrireClient(nouveauClient, "password123");
        
        if (inscriptionReussie) {
            System.out.println("✓ Client inscrit avec succès");
            
            // 2. VÉRIFICATION DE LA SESSION
            System.out.println("\n2. Vérification de la session...");
            
            // Méthode sécurisée
            try {
                Client clientConnecte = authModel.getClientConnecteSecurise();
                System.out.println("✓ Client connecté: " + clientConnecte.getNom() + " " + clientConnecte.getPrenom());
                System.out.println("  ID Client: " + clientConnecte.getIdClient());
                System.out.println("  Email: " + clientConnecte.getEmail());
            } catch (IllegalStateException e) {
                System.err.println("✗ Erreur: " + e.getMessage());
                
                // Tentative de rechargement
                System.out.println("Tentative de rechargement du client...");
                if (authModel.rechargerClient()) {
                    Client client = authModel.getClientConnecte();
                    if (client != null) {
                        System.out.println("✓ Client rechargé: " + client.getNom());
                    }
                }
            }
            
            // 3. VÉRIFICATION DES MÉTHODES DE SESSION
            System.out.println("\n3. Vérification des méthodes de session...");
            System.out.println("Est connecté: " + authModel.estConnecte());
            System.out.println("Est client: " + authModel.estClient());
            System.out.println("Est admin: " + authModel.estAdministrateur());
            System.out.println("ID Client session: " + authModel.getIdClientConnecte());
            
            // 4. DÉCONNEXION
            System.out.println("\n4. Déconnexion...");
            authModel.deconnecter();
            System.out.println("Est connecté après déconnexion: " + authModel.estConnecte());
            
        } else {
            System.err.println("✗ Échec de l'inscription");
        }
        
        System.out.println("\n=== TEST TERMINÉ ===");
        
        
        
        System.out.println("\n2. Ajout d'articles au panier...");
        int idClient = 15;//authModel.getIdClientConnecte();
        
        panierModel.ajouterArticleAuPanier(idClient, 1, 1); 
        panierModel.ajouterArticleAuPanier(idClient, 2, 2); 
        
        List<LigneCommande> panier = panierModel.getContenuPanier(idClient);
        System.out.println(" Panier créé avec " + panier.size() + " articles");
        
        System.out.println("\n3. Validation de la commande...");
        boolean commandeValidee = commandeModel.validerPanier(idClient, "CARTE");
        
        if (commandeValidee) {
            System.out.println(" Commande validée avec succès");
            
            // Récupérer la commande créée
            List<Commande> commandesClient = commandeModel.getCommandesParClient(idClient);
            if (!commandesClient.isEmpty()) {
                Commande derniereCommande = commandesClient.get(commandesClient.size() - 1);
                System.out.println("  Numéro commande: " + derniereCommande.getIdCommande());
                System.out.println("  Statut: " + derniereCommande.getStatut());
                System.out.println("  Date: " + derniereCommande.getDateCommande());
            }
        }
        
        System.out.println("\n4. Consultation de l'historique...");
        List<Commande> historique = commandeModel.getCommandesParClient(idClient);
        System.out.println(historique.size() + " commande(s) dans l'historique");
        
        //System.out.println("\n5. Déconnexion...");
        //authModel.deconnecter();
        System.out.println(" Utilisateur déconnecté");
        
        System.out.println("\n=== FLUX TERMINÉ ===");
        System.out.println("\n=== Administrateur ===");
        AdminModel adminModel = new AdminModel(connection);
        Map<String, Object> stats = adminModel.getStatistiquesGlobales();
        
        System.out.println("=== TABLEAU DE BORD ADMIN ===");
        System.out.println("Clients: " + stats.get("nombreClients"));
        System.out.println("Articles en stock: " + stats.get("nombreArticles"));
        System.out.println("Valeur du stock: " + stats.get("valeurStock") + " FCFA");
        System.out.println("Chiffre d'affaires: " + stats.get("chiffreAffaires") + " FCFA");

*/
        MainFrame app = new MainFrame();
        JPanel container = app.getContainer();        
        InterfaceClient intClient = new InterfaceClient(null);
        container.add(intClient.getPanelPrincipal());
        app.setVisible(true);

        
    }
    
}
