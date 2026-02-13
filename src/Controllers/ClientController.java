/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;
import InterfaceIHM.*;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Héloïse
 */
public class ClientController {
    private ProfilPopup menu;
    
    
    
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
