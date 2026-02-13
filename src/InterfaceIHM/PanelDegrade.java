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

public class PanelDegrade extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Activer l'anti-aliasing pour un rendu lisse
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Tes couleurs
        Color couleurOrange = new Color(253, 94, 9);
        Color orangeClair = new Color(0xFFCD90); 

        // Dégradé vertical (0,0 vers 0,h)
        // Pour un dégradé diagonal, utilise (0,0, w, h)
        GradientPaint gp = new GradientPaint(0, 10, couleurOrange, w, h, orangeClair);

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}