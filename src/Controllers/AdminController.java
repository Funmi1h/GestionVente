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
import DAO.DashboardDAO;
import Modeles.*;
import Metier.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class AdminController {
    private Connection connection;
    private AuthentificationModel authModel;
    private AdminModel adminModel;
    private ArticleModel articleModel;
    private CategorieModel categorieModel;
    private ClientModel clientModel;
    private CommandeModel commandeModel;
    private PaiementModel paiementModel;
    private DashboardDAO dashboardDAO;
    
    private Client adminConnecte;
    
    public AdminController(Connection connection) {
        this.connection = connection;
        this.authModel = new AuthentificationModel(connection);
        this.adminModel = new AdminModel(connection);
        this.articleModel = new ArticleModel(connection);
        this.categorieModel = new CategorieModel(connection);
        this.clientModel = new ClientModel(connection);
        this.commandeModel = new CommandeModel(connection);
        this.paiementModel = new PaiementModel(connection);
        this.dashboardDAO = new DashboardDAO(connection);
    }
    
    public String getAdminName() {
        // Récupérer depuis la session via authModel
        if (authModel != null && authModel.estConnecte()) {
            Client client = authModel.getClientConnecte();
            if (client != null) {
                return client.getPrenom() + " " + client.getNom();
            }
        }
        return "Administrateur";
    }
    
    public Client getAdminConnecte() {
        return adminConnecte;
    }

    
    public List<Article> getAllArticles() {
        return articleModel.listerTousArticles();
    }
    
    public Article getArticleById(int id) {
        return articleModel.trouverArticleParId(id);
    }
    
    public List<Categorie> getAllCategories() {
        return categorieModel.listerToutesCategories();
    }
    
    public Categorie getCategorieById(int id) {
        return categorieModel.trouverCategorieParId(id);
    }
    
    public String getCategorieArticle(int idArticle) {
        List<Categorie> cats = categorieModel.getCategoriesByArticle(idArticle);
        return cats.isEmpty() ? "Non catégorisé" : cats.get(0).getNomCategorie();
    }
    
    public Categorie getCategorieArticleObj(int idArticle) {
        List<Categorie> cats = categorieModel.getCategoriesByArticle(idArticle);
        return cats.isEmpty() ? null : cats.get(0);
    }
    
    public int getNombreArticlesParCategorie(int idCategorie) {
        return categorieModel.getNombreArticlesParCategorie(idCategorie);
    }
    
    public List<Client> getAllClients() {
        return clientModel.listerTousClients();
    }
    
    public Client getClientById(int id) {
        return clientModel.trouverClientParId(id);
    }
    
    public List<Commande> getAllCommandes() {
        return commandeModel.listerToutesCommandes();
    }
    // 🔹 Top 5 articles
    public List<Article> getTopArticlesDashboard() {
        return dashboardDAO.getTopArticles();
    }
    // 🔹 Toutes les commandes d’un client
    public List<Commande> getCommandesClientDashboard(int idClient) {
        return dashboardDAO.getCommandesByClient(idClient);
    }

    // 🔹 Trouver le client d’une commande
    public Client getClientByCommandeDashboard(int idCommande) {
        return dashboardDAO.getClientByCommande(idCommande);
    }

    // 🔹 Trouver un client par son ID (version DAO)
    public Client getClientByIdDashboard(int idClient) {
        return dashboardDAO.getClientById(idClient);
    }
    // 🔹 Total clients
    public int getTotalClientsDashboard() {
        return dashboardDAO.getTotalClients();
    }

    // 🔹 Total commandes
    public int getTotalCommandesDashboard() {
        return dashboardDAO.getTotalCommandes();
    }

    // 🔹 Chiffre d'affaires total
    public double getChiffreAffairesDashboard() {
        return dashboardDAO.getChiffreAffaires();
    }

    // 🔹 Commandes en cours
    public int getCommandesEnCoursDashboard() {
        return dashboardDAO.getCommandesEnCours();
    }
    
    public Commande getCommandeById(int id) {
        // À implémenter si nécessaire
        return dashboardDAO.getCommandeByPaiementId(id);
    }
    
    public double getMontantCommande(int idCommande) {
        return commandeModel.calculerTotalCommande(idCommande);
    }
    
    public List<Paiement> getAllPaiements() {
        return paiementModel.listerTousPaiements();
    }
    
    public Map<String, Object> getStatistiquesGlobales() {
        Map<String, Object> stats = adminModel.getStatistiquesGlobales();
        
        // Ajouter d'autres statistiques si nécessaire
        stats.put("nombreCommandes", getAllCommandes().size());
        
        return stats;
    }
    
    public boolean ajouterArticle(Article article, int idCategorie) {
        boolean success = articleModel.ajouterArticle(article);
        if (success && idCategorie > 0) {
            int id_article = articleModel.trouverArticleParNom(article.getNom()).getId_article();
            categorieModel.ajouterArticleACategorie(id_article, idCategorie);
        }
        return success;
    }
    public boolean estDejaCreer(String nom){
        return articleModel.estDejaDansCreer(nom);
    }
    public boolean modifierArticle(Article article, int idCategorie) {
        return articleModel.modifierArticle(article);
    }
    
    public boolean supprimerArticle(int id) {
        return articleModel.supprimerArticle(id);
    }
    
    public boolean ajouterCategorie(Categorie categorie) {
        return categorieModel.ajouterCategorie(categorie);
    }
    
    public boolean modifierCategorie(Categorie categorie) {
        return categorieModel.modifierCategorie(categorie);
    }
    
    public boolean supprimerCategorie(int id) {
        return categorieModel.supprimerCategorie(id);
    }
    
    public void deconnecter() {
        authModel.deconnecter();
    }
}