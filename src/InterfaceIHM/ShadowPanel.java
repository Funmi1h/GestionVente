import javax.swing.*;
import java.awt.*;

public class ShadowPanel extends JPanel {
    private int shadowSize = 5;
    private int shadowOpacity = 50; // 0-255

    public ShadowPanel() {
        setOpaque(false); // Nécessaire pour voir l'ombre en dessous
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dessiner l'ombre
        g2d.setColor(new Color(0, 0, 0, shadowOpacity));
        for (int i = 0; i < shadowSize; i++) {
            g2d.drawRoundRect(i, i, getWidth() - i * 2 - 1, getHeight() - i * 2 - 1, 10, 10);
        }

        // Dessiner le fond du composant (votre contenu)
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(0, 0, getWidth() - shadowSize, getHeight() - shadowSize, 10, 10);
        
        g2d.dispose();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Exemple Ombre Swing");
        ShadowPanel panel = new ShadowPanel();
        panel.setPreferredSize(new Dimension(200, 100));
        panel.setBackground(Color.LIGHT_GRAY);
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
