/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Metier.Article;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author YACOUBOU
 */
public class ArticleDAO {
    
    private Connection connection;
    
    public ArticleDAO(Connection connection) {
        this.connection = connection;
    }
    public boolean ajouter(Article a){
        String codeSQL="INSERT INTO articles "
                       +"(nom_article, prix, stock) "
                       +"VALUES (?, ?, ?)";
        try(PreparedStatement reqPreparer = connection.prepareStatement(codeSQL)){
            reqPreparer.setString(1, a.getNom());
            reqPreparer.setFloat(2, a.getPrix());
            reqPreparer.setInt(3, a.getStock());
            reqPreparer.execute();
            return true;
        } catch (SQLException ex) {
            System.getLogger(ClientDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return false;
    }
    
    public boolean modifier(Article a, int id_article){
        String codeSQL="UPDATE articles SET nom_article=?, prix=?, stock=? WHERE id_article=?";
        try(PreparedStatement reqPreparer = connection.prepareStatement(codeSQL)){
            reqPreparer.setString(1, a.getNom());
            reqPreparer.setFloat(2, a.getPrix());
            reqPreparer.setInt(3, a.getStock());
            reqPreparer.setInt(4, id_article);
            reqPreparer.execute();
            return true;
        } catch (SQLException ex) {
            System.getLogger(ClientDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return false;
    }
    
    public boolean supprime(int id_article){
        String codeSQL="DELETE FROM articles WHERE id_article=?";
        try(PreparedStatement reqPreparer = connection.prepareStatement(codeSQL)){
            reqPreparer.setInt(1, id_article);
            reqPreparer.execute();
            return true;
        } catch (SQLException ex) {
            System.getLogger(ClientDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return false;
    }
    
    public List<Article>lists(){
        String codeSQL="SELECT * FROM articles ORDER BY nom_article ";
        List<Article> articles = new ArrayList();
        try(Statement req = connection.createStatement()){
            ResultSet resReq = req.executeQuery(codeSQL);
            
            while(resReq.next()){
                Article article = new Article();
                article.setId_article(resReq.getInt(1));
                article.setNom(resReq.getString(2));
                article.setPrix(resReq.getFloat(3));
                article.setStock(resReq.getInt(4));
                article.setCreatedAt(resReq.getString(5));
                article.setUpdatedAt(resReq.getString(6));
                
                /*System.out.println(resReq.getInt(1));
                System.out.println(resReq.getString(2));
                System.out.println(resReq.getString(3));
                System.out.println(resReq.getString(4));
                System.out.println(resReq.getString(5));
                System.out.println(resReq.getString(6));
                System.out.println(resReq.getString(7));
                System.out.println(resReq.getString(8));*/
                articles.add(article);
            }
            return articles;
        } catch (SQLException ex) {
            System.getLogger(ClientDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return articles;
    }
    
    public Article rechercheArticle(int id_article){
        String codeSQL="SELECT * FROM articles WHERE id_article=?";
        try(PreparedStatement reqPreparer = connection.prepareStatement(codeSQL)){
            reqPreparer.setInt(1, id_article);
            ResultSet resReq = reqPreparer.executeQuery();
            if(resReq.next()){
                Article article = new Article();
                article.setId_article(resReq.getInt(1));
                article.setNom(resReq.getString(2));
                article.setPrix(resReq.getFloat(3));
                article.setStock(resReq.getInt(4));
                article.setCreatedAt(resReq.getString(5));
                article.setUpdatedAt(resReq.getString(6));
                return article;
                /*System.out.println(resReq.getInt(1));
                System.out.println(resReq.getString(2));
                System.out.println(resReq.getString(3));
                System.out.println(resReq.getString(4));
                System.out.println(resReq.getString(5));
                System.out.println(resReq.getString(6));
                System.out.println(resReq.getString(7));
                System.out.println(resReq.getString(8));*/
            }
            
        } catch (SQLException ex) {
            System.getLogger(ClientDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return null;
    }
    public boolean mettreAJourStock(int idArticle, int quantite) {
        String codeSQL = "UPDATE articles SET stock = stock - ? WHERE id_article = ? AND stock >= ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(codeSQL)) {
            stmt.setInt(1, quantite);
            stmt.setInt(2, idArticle);
            stmt.setInt(3, quantite);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean diminuerStock(int idArticle, int quantite) throws SQLException {
        String sql = "UPDATE articles SET stock = stock - ? WHERE id_article = ? AND stock >= ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quantite);
            ps.setInt(2, idArticle);
            ps.setInt(3, quantite);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean augmenterStock(int idArticle, int quantite) throws SQLException {
        String sql = "UPDATE articles SET stock = stock + ? WHERE id_article = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quantite);
            ps.setInt(2, idArticle);
            return ps.executeUpdate() > 0;
        }
    }
}




    