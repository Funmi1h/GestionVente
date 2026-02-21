/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

/**
 * Contrôleur pour la gestion du panier d'achat
 * Fait le lien entre l'interface utilisateur et le modèle de données
 * 
 * @author YACOUBOU
 */

import Modeles.PanierModel;
import Modeles.CommandeModel;
import Modeles.ArticleModel;
import InterfaceIHM.PanierContent;
import Metier.*;
import java.sql.Connection;
import java.util.List;
import javax.swing.*;

public class PanierController {
    private PanierModel panierModel;
    private CommandeModel commandeModel;
    private ArticleModel articleModel;
    private ClientController clientCtrl;
    private int idClientConnecte;
    private PanierContent panierContent;
    
    public PanierController(Connection connection, ClientController clientCtrl) {
        this.panierModel = new PanierModel(connection);
        this.commandeModel = new CommandeModel(connection);
        this.articleModel = new ArticleModel(connection);
        this.clientCtrl = clientCtrl;
        this.panierContent=  new PanierContent(this, clientCtrl);
    }
    
    private boolean isClientConnecte() {
        if (!clientCtrl.estConnecte()) {
            return false;
        }
        // Si c'est un admin, retourner false car pas de panier
        if (clientCtrl.estAdmin()) {
            return false;
        }
        idClientConnecte = clientCtrl.getClientConnecte().getIdClient();
        return idClientConnecte > 0;
    }
    
    public boolean ajouterArticle(int idArticle, int quantite) {
        if (!isClientConnecte()) return false;
        return panierModel.ajouterArticleAuPanier(idClientConnecte, idArticle, quantite);
    }
    
    public boolean supprimerArticle(int idArticle) {
        if (!isClientConnecte()) return false;
        return panierModel.supprimerArticleDuPanier(idClientConnecte, idArticle);
    }
    
    public boolean modifierQuantite(int idArticle, int nouvelleQuantite) {
        if (!isClientConnecte()) return false;
        return panierModel.modifierQuantitePanier(idClientConnecte, idArticle, nouvelleQuantite);
    }
    
    public boolean viderPanier() {
        if (!isClientConnecte()) return false;
        return panierModel.viderPanier(idClientConnecte);
    }
    
    public List<LigneCommande> getContenuPanier() {
        if (!isClientConnecte()) return List.of();
        return panierModel.getContenuPanier(idClientConnecte);
    }
    
    public double calculerTotalPanier() {
        if (!isClientConnecte()) return 0;
        return panierModel.calculerTotalPanier(idClientConnecte);
    }
    
    public int getNombreArticlesDansPanier() {
        if (!isClientConnecte()) return 0;
        return panierModel.getNombreArticlesDansPanier(idClientConnecte);
    }
    
    public Article getArticle(int idArticle) {
        return articleModel.trouverArticleParId(idArticle);
    }
    
    public boolean validerPanier(String modePaiement) {
        if (!isClientConnecte()) return false;
        return commandeModel.validerPanier(idClientConnecte, modePaiement);
    }
    
    public JPanel showContenuPanier() {
  

        return panierContent.getPanelPrincipal();
    }
    public JButton getBtnRetour(){
        JButton btnRetour = panierContent.getBtnRetour();
        return btnRetour;
    }
   
}