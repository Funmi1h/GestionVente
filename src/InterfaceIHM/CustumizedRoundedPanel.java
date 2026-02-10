/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InterfaceIHM;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.Path2D;


/**
 *
 * @author Héloïse
 */
public class CustumizedRoundedPanel extends JPanel {
    private int tl , tr, bl, br;
    private Color bgColor;
    public CustumizedRoundedPanel(int tl, int tr, int bl, int br, Color bgColor){
        this.tl = tl;
        this.tr = tr;
        this.bl = bl;
        this.br = br;
        this.bgColor = bgColor;
        this.setOpaque(false);       
    }
    
   @Override
   protected void paintComponent(Graphics g){
       super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(bgColor);

        int w = getWidth();
        int h = getHeight();

        // On crée un chemin personnalisé
        Path2D.Float path = new Path2D.Float();
        path.moveTo(tl, 0); // Point de départ après l'arrondi en haut à gauche
        
        // Ligne du haut et coin haut-droit
        path.lineTo(w - tr, 0);
        path.quadTo(w, 0, w, tr);
        
        // Ligne de droite et coin bas-droit
        path.lineTo(w, h - br);
        path.quadTo(w, h, w - br, h);
        
        // Ligne du bas et coin bas-gauche
        path.lineTo(bl, h);
        path.quadTo(0, h, 0, h - bl);
        
        // Ligne de gauche et coin haut-gauche
        path.lineTo(0, tl);
        path.quadTo(0, 0, tl, 0);
        
        path.closePath();
        g2d.fill(path);
       
   }
    
}
