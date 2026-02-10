
package InterfaceIHM;


import javax.swing.*;
import java.awt.*;

public class RoundTextField extends JTextField {
    private Color borderColor = Color.GRAY; // Couleur par défaut
    private int cornerRadius = 30;

    public RoundTextField(int columns) {
        super(columns);
        setOpaque(false); 
        setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(borderColor);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        
        g2.dispose();
    }
}