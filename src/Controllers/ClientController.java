/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;
import Modeles.*;
import Metier.*;
import DAO.*;
import InterfaceIHM.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;

/**
 *
 * @author Héloïse
 */
public class ClientController {
    private ProfilPopup menu;
    private Connection conn = ConnexionDB.connect();
    private ClientDAO clientDAO = new ClientDAO(conn);
    private Client clientConnectee;
    private final ClientModel clientModel = new ClientModel(conn);
    
    
    public void connexionController(FormulaireConnexion form){
        JButton btnValider = form.getBtnConnexion();
        btnValider.addActionListener(new java.awt.event.ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                Client client = rechercherClient(form);
                if (client != null){
                    javax.swing.SwingUtilities.getWindowAncestor(form).dispose();
                    InterfaceClient intClient = new InterfaceClient(client);
            }
        } });
        
    }
    
    private Client rechercherClient (FormulaireConnexion form){
        // récupération des valeurs 
        String email = form.getEmail();
        String password = form.getMotDePasse();
        
        if(email.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(form, "Veuillez remplir tous les champs !");
            return null;
        }
        
        Client client = clientDAO.rechercheClientParMail(email);
        if (client != null){
            // vérifier l'égalité des mots de passe 
            if(client.getMotDePasse().equals(password) && client.getMotDePasse() != null){
                clientConnectee = client;
                return client;
            }else{
                JOptionPane.showMessageDialog(form, "Mot de passe invalide");
            }
            
        }else{
            JOptionPane.showMessageDialog(form, "Utilisateur introuvable");
        }
        
        return null;
    }
    
    public void inscriptionController(FormulaireInscription form){
        JButton btnValider = form.getBtnInscription();
        btnValider.addActionListener(new java.awt.event.ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Client client = inscrireClient(form);
                clientConnectee = client;
                if(client != null){
                    javax.swing.SwingUtilities.getWindowAncestor(form).dispose();
                    InterfaceClient intClient = new InterfaceClient(client);
                    
                }
                
                
            }
            
        });
      
    }
    
    private Client inscrireClient(FormulaireInscription form){
        String nom = form.getNom();
        String prenom = form.getPrenom();
        String email = form.getEmail();
        String adresse = form.getAdresse();
        String mdp = form.getMotDePasse();
        String mdpConfirmee = form.getMotDePasseConfirmee();
        if(nom.isEmpty() || email.isEmpty()|| mdp.isEmpty() || mdpConfirmee.isEmpty() || prenom.isEmpty()){
            JOptionPane.showMessageDialog(form, "Veuillez remplir tous les champs ");
        }
        if( !mdpConfirmee.equals(mdp)){
            JOptionPane.showMessageDialog(form, "Les deux mots de passe ne sont pas identiques ");
        }
        Client client = new Client(nom, prenom, email, adresse, mdp);
        
        if (clientModel.ajouterClient(client) == false){
            JOptionPane.showMessageDialog(form, "Inscription échouée ! \n Veuillez réessayer");
            return null;
        }else{
            return client;
        }
        
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
}
