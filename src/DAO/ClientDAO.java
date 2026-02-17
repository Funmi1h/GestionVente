/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Metier.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author YACOUBOU
 */
public class ClientDAO {
    private Connection connection;
    
    public ClientDAO(Connection connection) {
        this.connection = connection;
    }
    public boolean ajouter(Client c){
        String codeSQL="INSERT INTO clients "
                       +"(nom, prenom, email, telephone, adresse) "
                       +"VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement reqPreparer = connection.prepareStatement(codeSQL)){
            reqPreparer.setString(1, c.getNom());
            reqPreparer.setString(2, c.getPrenom());
            reqPreparer.setString(3, c.getEmail());
            reqPreparer.setString(4, c.getTelephone());
            reqPreparer.setString(5, c.getAdresse());
            reqPreparer.execute();
            return true;
        } catch (SQLException ex) {
            System.getLogger(ClientDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return false;
    }
    
    public boolean modifier(Client c, int id_Client){
        String codeSQL="UPDATE clients SET nom=?, prenom=?, email=?, telephone=?, adresse=? WHERE id_client=?";
        try(PreparedStatement reqPreparer = connection.prepareStatement(codeSQL)){
            reqPreparer.setString(1, c.getNom());
            reqPreparer.setString(2, c.getPrenom());
            reqPreparer.setString(3, c.getEmail());
            reqPreparer.setString(4, c.getTelephone());
            reqPreparer.setString(5, c.getAdresse());
            reqPreparer.setInt(6, id_Client);
            reqPreparer.execute();
            return true;
        } catch (SQLException ex) {
            System.getLogger(ClientDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return false;
    }
    
    public boolean supprime(int id_Client){
        String codeSQL="DELETE FROM clients WHERE id_client=?";
        try(PreparedStatement reqPreparer = connection.prepareStatement(codeSQL)){
            reqPreparer.setInt(1, id_Client);
            reqPreparer.execute();
            return true;
        } catch (SQLException ex) {
            System.getLogger(ClientDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return false;
    }
    
    public List<Client>lists(){
        String codeSQL="SELECT * FROM clients ORDER BY nom ";
        try(Statement req = connection.createStatement()){
            ResultSet resReq = req.executeQuery(codeSQL);
            List<Client> clients = new ArrayList();
            while(resReq.next()){
                Client client = new Client();
                client.setIdClient(resReq.getInt(1));
                client.setNom(resReq.getString(2));
                client.setPrenom(resReq.getString(3));
                client.setEmail(resReq.getString(4));
                client.setTelephone(resReq.getString(5));
                client.setAdresse(resReq.getString(6));
                client.setDateCreate(resReq.getString(7));
                client.setDateUpdate(resReq.getString(8));
                
                /*System.out.println(resReq.getInt(1));
                System.out.println(resReq.getString(2));
                System.out.println(resReq.getString(3));
                System.out.println(resReq.getString(4));
                System.out.println(resReq.getString(5));
                System.out.println(resReq.getString(6));
                System.out.println(resReq.getString(7));
                System.out.println(resReq.getString(8));*/
                clients.add(client);
            }
            return clients;
        } catch (SQLException ex) {
            System.getLogger(ClientDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return null;
    }
    
    public Client rechercheClient(int id_client){
        String codeSQL="SELECT * FROM clients WHERE id_client=?";
        try(PreparedStatement reqPreparer = connection.prepareStatement(codeSQL)){
            reqPreparer.setInt(1, id_client);
            ResultSet resReq = reqPreparer.executeQuery();
            if(resReq.next()){
                Client client = new Client();
                client.setIdClient(resReq.getInt(1));
                client.setNom(resReq.getString(2));
                client.setPrenom(resReq.getString(3));
                client.setEmail(resReq.getString(4));
                client.setTelephone(resReq.getString(5));
                client.setAdresse(resReq.getString(6));
                client.setDateCreate(resReq.getString(7));
                client.setDateUpdate(resReq.getString(8));
                System.out.println("Le nom du client : "+client.getNom());
                return client;
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
        System.out.println("Le client avec id : "+id_client+" n'existe pas");
        return null;
    }
    public Client rechercheClientParMail(String email_client){
        String codeSQL="SELECT * FROM clients WHERE email=?";
        try(PreparedStatement reqPreparer = connection.prepareStatement(codeSQL)){
            reqPreparer.setString(1, email_client);
            ResultSet resReq = reqPreparer.executeQuery();
            if(resReq.next()){
                Client client = new Client();
                client.setIdClient(resReq.getInt(1));
                client.setNom(resReq.getString(2));
                client.setPrenom(resReq.getString(3));
                client.setEmail(resReq.getString(4));
                client.setTelephone(resReq.getString(5));
                client.setAdresse(resReq.getString(6));
                client.setDateCreate(resReq.getString(7));
                client.setDateUpdate(resReq.getString(8));
                System.out.println("Le nom du client : "+client.getNom());
                return client;
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
        System.out.println("Le client avec email : "+email_client+" n'existe pas");
        return null;
    }
}
