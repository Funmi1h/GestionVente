/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InterfaceIHM;
import java.awt.*;
import java.awt.CardLayout;
import javax.swing.*;
import java.awt.geom.Point2D;

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
        this.setSize(1000, 700);
        setLocationRelativeTo(null);
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);
        container.setBackground(Color.WHITE); 
        container.setOpaque(true); 
        container.setBorder(null);
        container.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
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
    
    public void interfaceClient(){
        JPanel intClient = new InterfaceClient();
        afficher(intClient);
        
    }

}