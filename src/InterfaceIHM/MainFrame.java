/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InterfaceIHM;

/**
 *
 * @author Héloïse
 */

import java.awt.*;
import javax.swing.*;
import Controllers.ClientController;
import com.formdev.flatlaf.FlatLightLaf;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel container;
    private ClientController clientCtrl;
    
    public MainFrame(){
        super("LuxStore - Application de vente");
        
        // Initialisation du contrôleur
        clientCtrl = new ClientController();
        
        this.getContentPane().setBackground(Color.WHITE);
        this.setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);
        container.setBackground(Color.WHITE); 
        container.setOpaque(true); 
        container.setBorder(null);
        container.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Afficher l'interface client
        InterfaceClient interfaceClient = new InterfaceClient(clientCtrl, clientCtrl.getPanierController());
        container.add(interfaceClient.getPanelPrincipal(), "client");
        
        this.add(container);
    }
    
    public JPanel getContainer(){
        return container;
    }
    
    public void afficher(JPanel j){
        container.add(j);
    }
    
    public void enlever(JPanel j){
        container.remove(j);
        container.revalidate();
        container.repaint();
    }
 }