/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

/**
 *
 * @author YACOUBOU
 */
import DAO.ConnexionDB;
import InterfaceIHM.ProfilPopup;
import Modeles.*;
import Metier.*;
import java.awt.Point;
import java.sql.Connection;
import java.util.List;
import javax.swing.*;

public class ClientController {
    private Connection connection;
    private AuthentificationModel authModel;
    private ClientModel clientModel;
    private ArticleModel articleModel;
    private CategorieModel categorieModel;
    private PanierController panierController;
    private PanierModel panierModel;
    
    // Variables d'état
    private boolean estConnecte = false;
    private boolean estAdmin = false;
    private int idClientConnecte = -1;
    
    public ClientController() {
        // Connexion à la base de données
        this.connection = ConnexionDB.connect();
        this.authModel = new AuthentificationModel(connection);
        this.clientModel = new ClientModel(connection);
        this.articleModel = new ArticleModel(connection);
        this.categorieModel = new CategorieModel(connection);
        this.panierModel = new PanierModel(connection);
        this.panierController = new PanierController(connection, this);
    }
    
    /**
     * Ajoute un article au panier du client connecté
     * @param idArticle L'identifiant de l'article à ajouter
     * @param quantite La quantité à ajouter
     * @return true si l'ajout a réussi, false sinon
     */
    public boolean ajouterAuPanier(int idArticle, int quantite) {
        if (!estConnecte) {
            JOptionPane.showMessageDialog(null,
                "Veuillez vous connecter pour ajouter des articles au panier",
                "Connexion requise",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (quantite <= 0) {
            JOptionPane.showMessageDialog(null,
                "La quantité doit être supérieure à 0",
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Récupérer l'article pour vérifier le stock
        Article article = getArticleById(idArticle);
        if (article == null) {
            JOptionPane.showMessageDialog(null,
                "Article introuvable",
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Vérifier le stock
        if (article.getStock() < quantite) {
            JOptionPane.showMessageDialog(null,
                "Stock insuffisant. Stock disponible: " + article.getStock(),
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Ajouter au panier via le modèle
        boolean success = panierModel.ajouterArticleAuPanier(idClientConnecte, idArticle, quantite);

        if (success) {
            JOptionPane.showMessageDialog(null,
                "✓ " + quantite + " x " + article.getNom() + " ajouté(s) au panier",
                "Succès",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                "❌ Erreur lors de l'ajout au panier",
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
        }

        return success;
    }

    /**
     * Version simplifiée qui ajoute une quantité par défaut de 1
     * @param idArticle L'identifiant de l'article à ajouter
     * @return true si l'ajout a réussi, false sinon
     */
    public boolean ajouterAuPanier(int idArticle) {
        return ajouterAuPanier(idArticle, 1);
    }

    public boolean connexion(String email, String motDePasse) {
        boolean success = authModel.authentifier(email, motDePasse);
        if (success) {
            this.estConnecte = true;
            this.estAdmin = authModel.estAdministrateur();
            Client client = authModel.getClientConnecte();
            if (client != null && !estAdmin) {
                this.idClientConnecte = client.getIdClient();
            }
        }
        return success;
    }
    
    public void deconnexion() {
        authModel.deconnecter();
        this.estConnecte = false;
        this.estAdmin = false;
        this.idClientConnecte = -1;
    }
    
    public boolean estConnecte() {
        return estConnecte;
    }
    
    public boolean estAdmin() {
        return estAdmin;
    }
    
    public int getIdClientConnecte() {
        return idClientConnecte;
    }
    
    public boolean inscription(Client client, String motDePasse) {
        return authModel.inscrireClient(client, motDePasse);
    }
    
    public boolean clientExist(String email) {
        return clientModel.emailExiste(email);
    }
    
    public Client getClientConnecte() {
        return authModel.getClientConnecte();
    }
    
    public Article getArticleById(int idArticle) {
        return articleModel.trouverArticleParId(idArticle);
    }
    
    public List<Article> getTousArticles() {
        return articleModel.listerTousArticles();
    }
    
    public List<Categorie> getToutesCategories() {
        return categorieModel.listerToutesCategories();
    }
    
    public List<Article> getArticlesParCategorie(int idCategorie) {
        return articleModel.listerTousArticles().stream()
            .filter(a -> {
                List<Categorie> cats = categorieModel.getCategoriesByArticle(a.getId_article());
                return cats.stream().anyMatch(c -> c.getIdCategorie() == idCategorie);
            })
            .toList();
    }
    
    public List<Article> rechercherArticlesParNom(String nom) {
        return articleModel.rechercherArticlesParNom(nom);
    }
    
    public void switchView(JPanel from, JPanel to) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(from);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(to);
        frame.revalidate();
        frame.repaint();
    }
    
    public void showProfilPopup(JFrame parent, JButton anchor) {
        if (!estConnecte) {
            JOptionPane.showMessageDialog(parent, 
                "Vous devez être connecté pour voir votre profil",
                "Non connecté",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        ProfilPopup popup = new ProfilPopup(parent, this);
        
        // Ajouter les informations du client
        Client client = getClientConnecte();
        if (client != null) {
            popup.setClientInfo(client);
        }
        
        // Positionner le popup
        Point location = anchor.getLocationOnScreen();
        popup.setLocation(location.x + anchor.getWidth() / 2 - popup.getWidth() / 2, 
                         location.y + anchor.getHeight());
        popup.setVisible(true);
    }
    
    public PanierController getPanierController() {
        return panierController;
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public void fermerConnexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}