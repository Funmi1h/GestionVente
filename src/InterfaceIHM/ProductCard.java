/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InterfaceIHM;

/**
 *
 * @author YACOUBOU
 */

import Controllers.ClientController;
import Metier.Article;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ProductCard extends JPanel {
    private Article article;
    private String categorie;
    private ClientController clientController;
    
    private final Color PRIMARY_ORANGE = new Color(253, 94, 9);
    private final Color TEXT_DARK = new Color(51, 51, 51);
    private final Color TEXT_LIGHT = new Color(102, 102, 102);
    private final Color CARD_BG = Color.WHITE;
    private final Color STOCK_GREEN = new Color(76, 175, 80);
    private final Color STOCK_RED = new Color(244, 67, 54);
    
    private JLabel nomLabel;
    private JLabel prixLabel;
    private JLabel stockLabel;
    private JLabel categorieLabel;
    private JButton btnAjouter;
    private JSpinner quantiteSpinner;
    
    public ProductCard(Article article, String categorie, ClientController clientController) {
        this.article = article;
        this.categorie = categorie;
        this.clientController = clientController;
        
        initUI();
    }
    
    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(CARD_BG);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        setPreferredSize(new Dimension(350, 180));
        
        // Panel gauche avec image (placeholder)
        JPanel imagePanel = createImagePanel();
        
        // Panel central avec informations
        JPanel infoPanel = createInfoPanel();
        
        // Panel droit avec actions
        JPanel actionPanel = createActionPanel();
        
        add(imagePanel, BorderLayout.WEST);
        add(infoPanel, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.EAST);
    }
    
    private JPanel createImagePanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fond gris clair
                g2.setColor(new Color(245, 245, 245));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Icône produit (📦 par défaut)
                g2.setColor(TEXT_LIGHT);
                g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
                FontMetrics fm = g2.getFontMetrics();
                String emoji = getEmojiForCategory();
                int x = (getWidth() - fm.stringWidth(emoji)) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(emoji, x, y);
                
                g2.dispose();
            }
        };
        
        panel.setPreferredSize(new Dimension(100, 100));
        panel.setOpaque(false);
        
        return panel;
    }
    
    private String getEmojiForCategory() {
        if (categorie == null) return "📦";
        
        String cat = categorie.toLowerCase();
        if (cat.contains("electro") || cat.contains("ordinateur") || cat.contains("telephone")) {
            return "💻";
        } else if (cat.contains("vetement") || cat.contains("clothes") || cat.contains("mode")) {
            return "👕";
        } else if (cat.contains("maison") || cat.contains("meuble") || cat.contains("deco")) {
            return "🏠";
        } else if (cat.contains("sport") || cat.contains("loisir")) {
            return "⚽";
        } else if (cat.contains("beaute") || cat.contains("care") || cat.contains("soin")) {
            return "💄";
        } else if (cat.contains("livre") || cat.contains("book")) {
            return "📚";
        } else if (cat.contains("jouet") || cat.contains("toy")) {
            return "🎮";
        } else if (cat.contains("aliment") || cat.contains("food")) {
            return "🍎";
        } else {
            return "📦";
        }
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        
        // Catégorie
        categorieLabel = new JLabel(categorie);
        categorieLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        categorieLabel.setForeground(TEXT_LIGHT);
        categorieLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Nom
        nomLabel = new JLabel(article.getNom());
        nomLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nomLabel.setForeground(TEXT_DARK);
        nomLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Description (si disponible)
        String description = article.getDescription();
        if (description != null && !description.isEmpty()) {
            JLabel descLabel = new JLabel("<html><body style='width:200px'>" + description + "</body></html>");
            descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            descLabel.setForeground(TEXT_LIGHT);
            descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            descLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            panel.add(descLabel);
        }
        
        // Prix
        prixLabel = new JLabel(String.format("%,.0f FCFA", article.getPrix()));
        prixLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        prixLabel.setForeground(PRIMARY_ORANGE);
        prixLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        prixLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        
        // Stock
        String stockText = article.getStock() > 0 ? 
            "En stock: " + article.getStock() + " unités" : 
            "Rupture de stock";
        stockLabel = new JLabel(stockText);
        stockLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        stockLabel.setForeground(article.getStock() > 0 ? STOCK_GREEN : STOCK_RED);
        stockLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(categorieLabel);
        panel.add(nomLabel);
        panel.add(prixLabel);
        panel.add(stockLabel);
        
        return panel;
    }
    
    private JPanel createActionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // Spinner pour la quantité
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, article.getStock(), 1);
        quantiteSpinner = new JSpinner(spinnerModel);
        quantiteSpinner.setPreferredSize(new Dimension(80, 30));
        quantiteSpinner.setMaximumSize(new Dimension(80, 30));
        quantiteSpinner.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Activer/désactiver le spinner selon le stock
        quantiteSpinner.setEnabled(article.getStock() > 0);
        
        // Bouton Ajouter au panier
        btnAjouter = new JButton("Ajouter au panier") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (isEnabled()) {
                    if (getModel().isPressed()) {
                        g2.setColor(PRIMARY_ORANGE.darker());
                    } else if (getModel().isRollover()) {
                        g2.setColor(PRIMARY_ORANGE.brighter());
                    } else {
                        g2.setColor(PRIMARY_ORANGE);
                    }
                } else {
                    g2.setColor(new Color(200, 200, 200));
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                
                g2.dispose();
            }
        };
        
        btnAjouter.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAjouter.setForeground(Color.WHITE);
        btnAjouter.setPreferredSize(new Dimension(150, 35));
        btnAjouter.setMaximumSize(new Dimension(150, 35));
        btnAjouter.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAjouter.setContentAreaFilled(false);
        btnAjouter.setBorderPainted(false);
        btnAjouter.setFocusPainted(false);
        btnAjouter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAjouter.setEnabled(article.getStock() > 0);
        
        // Action du bouton
        btnAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int quantite = (int) quantiteSpinner.getValue();
                
                // Appel au contrôleur
                boolean success = clientController.ajouterAuPanier(article.getId_article(), quantite);
                
                if (success) {
                    // Désactiver temporairement le bouton pour éviter les doubles clics
                    btnAjouter.setEnabled(false);
                    Timer timer = new Timer(2000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            btnAjouter.setEnabled(article.getStock() > 0);
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
            }
        });
        
        panel.add(Box.createVerticalGlue());
        panel.add(quantiteSpinner);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnAjouter);
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    /**
     * Met à jour l'affichage de la carte (après modification du stock)
     */
    public void refresh() {
        stockLabel.setText(article.getStock() > 0 ? 
            "En stock: " + article.getStock() + " unités" : 
            "Rupture de stock");
        stockLabel.setForeground(article.getStock() > 0 ? STOCK_GREEN : STOCK_RED);
        
        SpinnerNumberModel model = (SpinnerNumberModel) quantiteSpinner.getModel();
        model.setMaximum(article.getStock());
        quantiteSpinner.setEnabled(article.getStock() > 0);
        btnAjouter.setEnabled(article.getStock() > 0);
    }
}