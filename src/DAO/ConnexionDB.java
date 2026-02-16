/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author YACOUBOU
 */
public class ConnexionDB {
    private static Connection connection;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String  URL = "jdbc:mysql://localhost:3307/gestionvente";
    private static final String  USER = "root";
    private static final String  PASSWORD = "";
    
    
    public static Connection connect(){
        
        try{
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Ok! Bd Ok!!");
            return connection;
        }
        catch(ClassNotFoundException e){
            System.out.println(e.getException());
            
        } catch(SQLException sq){
            System.getLogger(ConnexionDB.class.getName()).log(System.Logger.Level.ALL, sq);
        }
        return connection;
    }
}
