/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InterfaceIHM;

import javax.swing.JWindow;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Héloïse
 */
public class ProfilPopup extends JWindow {
    private RoundButton logOut;
    private JLabel labelInfos;
    private JLabel labelCommandes;
    CustumizedRoundedPanel panelPrincipal;
     public ProfilPopup(Window owner){
         super(owner);
        this.setBackground(new Color(0, 0, 0, 0));
        panelPrincipal = new CustumizedRoundedPanel(30, 30, 30, 30, Color.WHITE);
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
         
        logOut = new RoundButton("Se déconnecter");
        labelInfos = new JLabel("Infos personnels");
        labelCommandes = new JLabel("Vos commandes");
         
        labelInfos.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelCommandes.setAlignmentX(Component.CENTER_ALIGNMENT);
        logOut.setAlignmentX(Component.CENTER_ALIGNMENT);
         
        panelPrincipal.add(labelInfos);
        panelPrincipal.add(Box.createVerticalStrut(15));
        panelPrincipal.add(labelCommandes);
        panelPrincipal.add(Box.createVerticalStrut(20));
        panelPrincipal.add(logOut);
        this.add(panelPrincipal);
        this.pack();
     }
     

    
}
