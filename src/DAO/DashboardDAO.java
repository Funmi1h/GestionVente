/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author YACOUBOU
 */
import java.sql.*;
import java.util.*;
import Metier.*;


public class DashboardDAO {

    private Connection connection;

    public DashboardDAO(Connection connection) {
        this.connection = connection;
    }

    // ===============================
    // 🔹 1. Nombre total de clients
    // ===============================
    public int getTotalClients() {
        String sql = "SELECT COUNT(*) FROM clients";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ===============================
    // 🔹 2. Nombre total de commandes
    // ===============================
    public int getTotalCommandes() {
        String sql = "SELECT COUNT(*) FROM commandes";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ===============================
    // 🔹 3. Chiffre d'affaires PAYEE
    // ===============================
    public double getChiffreAffaires() {

        String sql = """
                SELECT SUM(p.montant)
                FROM paiements p
                JOIN commandes c ON p.id_commande = c.id_commande
                WHERE c.statut = ?
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, Commande.STATUT_PAYEE);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    // ===============================
    // 🔹 4. Commandes en cours
    // ===============================
    public int getCommandesEnCours() {

        String sql = "SELECT COUNT(*) FROM commandes WHERE statut = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, Commande.STATUT_EN_COURS);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    // ===============================
    // 🔹 5. Top 5 Articles vendus
    // ===============================
    public List<Article> getTopArticles() {

        List<Article> liste = new ArrayList<>();

        String sql = """
                SELECT a.*, SUM(lc.quantite) as total_vendu
                FROM ligne_commande lc
                JOIN articles a ON lc.id_article = a.id_article
                JOIN commandes c ON lc.id_commande = c.id_commande
                WHERE c.statut = ?
                GROUP BY a.id_article
                ORDER BY total_vendu DESC
                LIMIT 5
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, Commande.STATUT_PAYEE);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Article article = new Article();
                article.setId_article(rs.getInt("id_article"));
                article.setNom(rs.getString("nom_article"));
                article.setPrix(rs.getFloat("prix"));
                article.setStock(rs.getInt("stock"));
                article.setDescription(rs.getString("description"));
                article.setUrlPhoto(rs.getString("url_photo"));

                liste.add(article);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liste;
    }

    // ===============================
    // 🔁 6. Commandes d’un client
    // ===============================
    public List<Commande> getCommandesByClient(int idClient) {

        List<Commande> liste = new ArrayList<>();

        String sql = "SELECT * FROM commandes WHERE id_client = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idClient);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Commande cmd = new Commande();
                cmd.setIdCommande(rs.getInt("id_commande"));
                cmd.setDateCommande(rs.getString("date_commande"));
                cmd.setStatut(rs.getString("statut"));
                cmd.setIdClient(rs.getInt("id_client"));

                liste.add(cmd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liste;
    }

    // ===============================
    // 🔁 7. Client d’une commande
    // ===============================
    public Client getClientByCommande(int idCommande) {

        String sql = """
                SELECT c.*
                FROM clients c
                JOIN commandes cmd ON c.id_client = cmd.id_client
                WHERE cmd.id_commande = ?
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idCommande);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Client client = new Client();
                client.setIdClient(rs.getInt("id_client"));
                client.setNom(rs.getString("nom"));
                client.setPrenom(rs.getString("prenom"));
                client.setEmail(rs.getString("email"));
                client.setTelephone(rs.getString("telephone"));
                client.setAdresse(rs.getString("adresse"));

                return client;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // ===============================
    // 🔁 8. Nouveau getClientById (remplace ancien)
    // ===============================
    public Client getClientById(int idClient) {

        String sql = "SELECT * FROM clients WHERE id_client = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idClient);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Client client = new Client();
                client.setIdClient(rs.getInt("id_client"));
                client.setNom(rs.getString("nom"));
                client.setPrenom(rs.getString("prenom"));
                client.setEmail(rs.getString("email"));
                client.setTelephone(rs.getString("telephone"));
                client.setAdresse(rs.getString("adresse"));

                return client;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public Commande getCommandeByPaiementId(int idPaiement) {
    Commande commande = null;
    String sql = "SELECT c.* FROM commandes c " +
                 "JOIN paiements p ON c.id_commande = p.id_paiement " +
                 "WHERE p.id_paiement = ?";

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, idPaiement);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            commande = new Commande();
            commande.setIdCommande(rs.getInt("id_commande"));
            commande.setIdClient(rs.getInt("id_client"));
            commande.setDateCommande(rs.getString("date_commande"));
            commande.setStatut(rs.getString("statut"));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return commande;
}
}