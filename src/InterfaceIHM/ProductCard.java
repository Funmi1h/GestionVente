package InterfaceIHM;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DecimalFormat;

public class ProductCard extends JPanel {
    private String nom;
    private double prix;
    private int stock;
    private String description;
    private String imagePath;
    private double note;
    
    private static final Color STAR_COLOR = new Color(255, 200, 50);
    private static final Color GLASS_COLOR = new Color(255, 255, 255, 200); // Semi-transparent white
    
    public ProductCard(String nom, double prix, int stock, String description) {
        this(nom, prix, stock, description, null, 5.0);
    }
    
    public ProductCard(String nom, double prix, int stock, String description, 
                      String imagePath, double note) {
        this.nom = nom;
        this.prix = prix;
        this.stock = stock;
        this.description = description;
        this.imagePath = imagePath;
        this.note = note;
        
        initUI();
    }
    
    private void initUI() {
        setLayout(new BorderLayout(20, 0));
        setOpaque(false);
        setPreferredSize(new Dimension(500, 200));
        setBorder(new EmptyBorder(0, 0, 0, 0));
        
        // Panel principal avec effet de verre
        JPanel glassPanel = createGlassPanel();
        add(glassPanel, BorderLayout.CENTER);
    }
    
    private JPanel createGlassPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Effet de verre avec fond semi-transparent
                g2.setColor(GLASS_COLOR);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                
                // Bordure subtile
                g2.setColor(new Color(255, 255, 255, 100));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 25, 25);
            }
        };
        
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout(20, 0));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Panel gauche pour l'image
        JPanel imagePanel = createImagePanel();
        panel.add(imagePanel, BorderLayout.WEST);
        
        // Panel droit pour les informations
        JPanel infoPanel = createInfoPanel();
        panel.add(infoPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createImagePanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fond arrondi beige
                g2.setColor(new Color(235, 210, 180));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                // Cercle décoratif
                g2.setColor(new Color(215, 180, 140));
                int circleSize = 120;
                int circleX = (getWidth() - circleSize) / 2;
                int circleY = 10;
                g2.fillOval(circleX, circleY, circleSize, circleSize);
                
                // Image du produit
                if (imagePath != null) {
                    try {
                        ImageIcon icon = new ImageIcon(imagePath);
                        Image img = icon.getImage().getScaledInstance(100, 130, Image.SCALE_SMOOTH);
                        g2.drawImage(img, (getWidth() - 100) / 2, 25, null);
                    } catch (Exception e) {
                        drawDefaultProduct(g2);
                    }
                } else {
                    drawDefaultProduct(g2);
                }
            }
            
            private void drawDefaultProduct(Graphics2D g2) {
                // Simulation d'une bouteille
                g2.setColor(new Color(100, 70, 40, 180));
                int bottleWidth = 45;
                int bottleHeight = 110;
                int bottleX = (getWidth() - bottleWidth) / 2;
                int bottleY = 40;
                g2.fillRoundRect(bottleX, bottleY, bottleWidth, bottleHeight, 12, 12);
                
                // Bouchon
                g2.setColor(new Color(40, 40, 40));
                g2.fillRoundRect(bottleX + 10, bottleY - 12, 25, 15, 6, 6);
                
                // Éléments décoratifs (noix)
                g2.setColor(new Color(200, 160, 120));
                g2.fillOval(10, 140, 20, 16);
                g2.fillOval(130, 145, 18, 14);
                
                // Feuilles
                g2.setColor(new Color(100, 140, 90));
                g2.fillOval(125, 140, 12, 6);
            }
        };
        
        panel.setPreferredSize(new Dimension(160, 160));
        panel.setOpaque(false);
        
        return panel;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        
        // Catégorie
        JLabel categoryLabel = new JLabel(description);
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        categoryLabel.setForeground(new Color(100, 100, 100));
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(categoryLabel);
        panel.add(Box.createVerticalStrut(8));
        
        // Nom du produit
        JLabel nameLabel = new JLabel(nom);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        nameLabel.setForeground(new Color(40, 40, 40));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(15));
        
        // Panel pour note et stock
        JPanel detailsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        detailsPanel.setOpaque(false);
        detailsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Note avec étoile
        JPanel ratingPanel = createRatingPanel();
        detailsPanel.add(ratingPanel);
        
        // Stock
        JLabel stockLabel = new JLabel("Stock: " + stock);
        stockLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        stockLabel.setForeground(new Color(120, 120, 120));
        detailsPanel.add(stockLabel);
        
        panel.add(detailsPanel);
        panel.add(Box.createVerticalStrut(15));
        
        // Prix
        DecimalFormat df = new DecimalFormat("$#,##0.00");
        JLabel priceLabel = new JLabel(df.format(prix));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 28));
        priceLabel.setForeground(new Color(230, 140, 80));
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(priceLabel);
        
        return panel;
    }
    
    private JPanel createRatingPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panel.setOpaque(false);
        
        // Étoile
        JLabel starLabel = new JLabel("★");
        starLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        starLabel.setForeground(STAR_COLOR);
        panel.add(starLabel);
        
        // Note
        JLabel noteLabel = new JLabel(String.format("%.1f", note));
        noteLabel.setFont(new Font("Arial", Font.BOLD, 16));
        noteLabel.setForeground(new Color(40, 40, 40));
        panel.add(noteLabel);
        
        return panel;
    }
    
    // Getters et Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { 
        this.nom = nom; 
        repaint();
    }
    
    public double getPrix() { return prix; }
    public void setPrix(double prix) { 
        this.prix = prix;
        repaint();
    }
    
    public int getStock() { return stock; }
    public void setStock(int stock) { 
        this.stock = stock;
        repaint();
    }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { 
        this.description = description;
        repaint();
    }
    
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        repaint();
    }
    
    public void setNote(double note) {
        this.note = note;
        repaint();
    }
    
 }