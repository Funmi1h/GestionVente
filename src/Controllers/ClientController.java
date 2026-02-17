/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import DAO.ConnexionDB;
import InterfaceIHM.PanierContent;
import InterfaceIHM.ProfilPopup;
import Modeles.AuthentificationModel;
import Modeles.ClientModel;
import Metier.Client;
import java.awt.Point;
import java.sql.Connection;
import javax.swing.*;

/**
 *
 * @author YACOUBOU
 */
public class ClientController {
    private ProfilPopup menu;
    private Connection connection = ConnexionDB.connect();
    private ClientModel client;
    private AuthentificationModel authModel= new AuthentificationModel(connection);

    public ClientController() {
        client = new ClientModel(connection);
    }
    

    public void showProfilPopup(JFrame parentFrame, JButton btnUser ){
        
        if (menu == null) {
            menu = new ProfilPopup(parentFrame); 
        }
        //JPanel menuPanel = menu.getPanelPrincipal();
        if(menu.isVisible()){
            menu.setVisible(false);
        }else{
            Point locationOnScreen = btnUser.getLocationOnScreen();
            //menu juste en dessous (ajuster X pour l'alignement)
            int x = locationOnScreen.x - (menu.getWidth() - btnUser.getWidth());
            int y = locationOnScreen.y + btnUser.getHeight() + 5;
            menu.setLocation(x, y);
            menu.setVisible(true);
            menu.requestFocus();
            
        }
        
    }
    
    public JPanel showContenuPanier(){
        PanierContent contenuPanier = new PanierContent();
        return contenuPanier.getPanelPrincipal();
        
        
    }
    public void switchView(JPanel container, JPanel newView){
        container.removeAll();
        container.add(newView);
        container.revalidate();
        container.repaint();
    }
    
    public void configurerRetourCatalogue(PanierContent panier, JPanel conteneurMain, JPanel catalogueView) {
        panier.getBtnRetour().addActionListener(e -> {
            conteneurMain.removeAll();
            conteneurMain.add(catalogueView);
            conteneurMain.revalidate();
            conteneurMain.repaint();
       });
    }
    
    public boolean inscription(Client nouveauClient, String password){
        return authModel.inscrireClient(nouveauClient, password);  
    }
    
    public boolean connexion(String email, String password){
        return authModel.authentifier(email, password);
    }
    public boolean clientExist(String email){
        return client.emailExiste(email);
    }
}
