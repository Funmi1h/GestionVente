/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InterfaceIHM;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Héloïse
 */
public class PanierContent extends JPanel {
    JPanel panelPrincipal;
    JButton btnRetour;
    
    public PanierContent(){
        panelPrincipal = new JPanel();
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPrincipal.setOpaque(true); 
        panelPrincipal.setBackground(new Color(254, 245, 240));
        panelPrincipal.setLayout(new BorderLayout());
        JLabel labelTitre = new JLabel("Votre panier");
        panelPrincipal.add(labelTitre);
        
        
            // le btn de retour
        btnRetour = new RoundButton("← "); 

        btnRetour.setBackground(Color.WHITE);
        btnRetour.setForeground(new Color(80, 80, 80));
        btnRetour.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnRetour.setFocusPainted(false);
        btnRetour.setContentAreaFilled(false); // Rend le fond transparent
        btnRetour.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1)); // Bordure fine     
        btnRetour.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setOpaque(false);
        header.add(btnRetour);
        header.add(labelTitre);
        panelPrincipal.add(header, BorderLayout.NORTH);
    }
    public JPanel getPanelPrincipal(){
        return panelPrincipal;
    }
    public JButton getBtnRetour(){
        return btnRetour;
    }
    
}
