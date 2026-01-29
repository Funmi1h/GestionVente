/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InterfaceIHM;
import java.awt.*;
import java.awt.CardLayout;
import javax.swing.*;

/**
 *
 * @author Héloïse
 */
public class MainFrame extends JFrame{
    private CardLayout cardLayout;
    private JPanel container;
    public MainFrame(){
        super("Notre Frame");
        this.getContentPane().setBackground(Color.WHITE);
        this.setSize(800, 600);
        setLocationRelativeTo(null);
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);
        this.add(container);
        
        
        
        
    }
    public void afficher(JPanel j){
        
        
        
    }
    

}