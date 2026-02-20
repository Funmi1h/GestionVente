/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InterfaceIHM;

/**
 *
 * @author YACOUBOU
 */

import javax.swing.*;
import java.awt.*;
import Metier.LigneCommande;
import Metier.Article;
import Controllers.PanierController;
import Controllers.ClientController;
import java.util.List;

public class PanierContent extends JPanel {
    private JPanel panelPrincipal;
    private JButton btnRetour;
    private JButton btnValider;
    private JLabel labelTotal;
    private PanierController panierCtrl;
    private ClientController clientCtrl;
    private JPanel contenuPanel;

    public PanierContent(PanierController panierCtrl, ClientController clientCtrl) {
        this.panierCtrl = panierCtrl;
        this.clientCtrl = clientCtrl;
        
        panelPrincipal = new JPanel();
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelPrincipal.setOpaque(true); 
        panelPrincipal.setBackground(new Color(254, 245, 240));
        panelPrincipal.setLayout(new BorderLayout());
        
        initUI();
        chargerContenuPanier();
    }
    
    private void initUI() {
        // Header avec bouton retour
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnRetour = new RoundButton("← Retour"); 
        btnRetour.setBackground(Color.WHITE);
        btnRetour.setForeground(new Color(80, 80, 80));
        btnRetour.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRetour.setFocusPainted(false);
        btnRetour.setContentAreaFilled(false);
        btnRetour.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        btnRetour.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel labelTitre = new JLabel("Votre panier");
        labelTitre.setFont(new Font("Segoe UI", Font.BOLD, 24));
        labelTitre.setForeground(new Color(51, 51, 51));
        
        header.add(btnRetour, BorderLayout.WEST);
        header.add(labelTitre, BorderLayout.CENTER);
        
        panelPrincipal.add(header, BorderLayout.NORTH);
        
        // Panel de contenu
        contenuPanel = new JPanel();
        contenuPanel.setLayout(new BoxLayout(contenuPanel, BoxLayout.Y_AXIS));
        contenuPanel.setOpaque(false);
        contenuPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JScrollPane scrollPane = new JScrollPane(contenuPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        
        // Footer avec total et validation
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        labelTotal = new JLabel("Total: 0 FCFA");
        labelTotal.setFont(new Font("Segoe UI", Font.BOLD, 20));
        labelTotal.setForeground(new Color(255, 87, 34));
        
        btnValider = new RoundButton("Valider la commande");
        btnValider.setBackground(new Color(255, 87, 34));
        btnValider.setForeground(Color.WHITE);
        btnValider.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnValider.setFocusPainted(false);
        btnValider.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnValider.addActionListener(e -> validerCommande());
        
        footer.add(labelTotal, BorderLayout.WEST);
        footer.add(btnValider, BorderLayout.EAST);
        
        panelPrincipal.add(footer, BorderLayout.SOUTH);
    }
    
    private void chargerContenuPanier() {
        contenuPanel.removeAll();
        
        List<LigneCommande> lignes = panierCtrl.getContenuPanier();
        
        if (lignes.isEmpty()) {
            JLabel emptyLabel = new JLabel("Votre panier est vide");
            emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            emptyLabel.setForeground(new Color(150, 150, 150));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contenuPanel.add(emptyLabel);
            btnValider.setEnabled(false);
            labelTotal.setText("Total: 0 FCFA");
        } else {
            for (LigneCommande ligne : lignes) {
                contenuPanel.add(creerLignePanier(ligne));
                contenuPanel.add(Box.createVerticalStrut(10));
            }
            btnValider.setEnabled(true);
            labelTotal.setText(String.format("Total: %.2f FCFA", panierCtrl.calculerTotalPanier()));
        }
        
        contenuPanel.revalidate();
        contenuPanel.repaint();
    }
    
    private JPanel creerLignePanier(LigneCommande ligne) {
        Article article = panierCtrl.getArticle(ligne.getIdArticle());
        
        JPanel lignePanel = new JPanel(new BorderLayout(20, 0));
        lignePanel.setBackground(Color.WHITE);
        lignePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        lignePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        
        // Image placeholder
        JLabel imageLabel = new JLabel("📦");
        imageLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        imageLabel.setPreferredSize(new Dimension(80, 80));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        
        ArticleImagePanel imagePanel = new ArticleImagePanel();
        imagePanel.setArticleImage(article.getUrlPhoto());
        
        // Infos article
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        
        JLabel nomLabel = new JLabel(article.getNom());
        nomLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        JLabel prixLabel = new JLabel(String.format("%.2f FCFA", article.getPrix()));
        prixLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        prixLabel.setForeground(new Color(100, 100, 100));
        
        infoPanel.add(nomLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(prixLabel);
        
        // Panel quantité
        JPanel quantitePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        quantitePanel.setOpaque(false);
        
        JButton btnMoins = new JButton("-");
        btnMoins.setPreferredSize(new Dimension(30, 30));
        btnMoins.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnMoins.setFocusPainted(false);
        btnMoins.addActionListener(e -> {
            if (ligne.getQuantite() > 1) {
                panierCtrl.modifierQuantite(article.getId_article(), ligne.getQuantite() - 1);
                chargerContenuPanier();
            }
        });
        
        JLabel quantiteLabel = new JLabel(String.valueOf(ligne.getQuantite()));
        quantiteLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        quantiteLabel.setPreferredSize(new Dimension(30, 30));
        quantiteLabel.setHorizontalAlignment(JLabel.CENTER);
        
        JButton btnPlus = new JButton("+");
        btnPlus.setPreferredSize(new Dimension(30, 30));
        btnPlus.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnPlus.setFocusPainted(false);
        btnPlus.addActionListener(e -> {
            if (article.getStock() > ligne.getQuantite()) {
                panierCtrl.modifierQuantite(article.getId_article(), ligne.getQuantite() + 1);
                chargerContenuPanier();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Stock insuffisant",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton btnSupprimer = creerBoutonAvatar("./ressources/icons/del.png");

        btnSupprimer.addActionListener(e -> {
            panierCtrl.supprimerArticle(article.getId_article());
            chargerContenuPanier();
        });
        
        double sousTotal = article.getPrix() * ligne.getQuantite();
        JLabel sousTotalLabel = new JLabel(String.format("%.2f FCFA", sousTotal));
        sousTotalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sousTotalLabel.setForeground(new Color(255, 87, 34));
        
        quantitePanel.add(btnMoins);
        quantitePanel.add(quantiteLabel);
        quantitePanel.add(btnPlus);
        quantitePanel.add(btnSupprimer);
        
        lignePanel.add(imagePanel, BorderLayout.WEST);
        lignePanel.add(infoPanel, BorderLayout.CENTER);
        lignePanel.add(quantitePanel, BorderLayout.EAST);
        lignePanel.add(sousTotalLabel, BorderLayout.SOUTH);
        
        return lignePanel;
    }
    private static class ArticleImagePanel extends JPanel {

        private Image scaledImage = null;
        private boolean hasValidImage = false;

        public ArticleImagePanel() {
            setPreferredSize(new Dimension(110, 110));
            setMinimumSize(new Dimension(90, 90));
            setOpaque(false);
        }

        public void setArticleImage(String photoPath) {
            hasValidImage = false;
            scaledImage = null;

            if (photoPath == null || photoPath.trim().isEmpty()) {
                repaint();
                return;
            }

            try {
                String fullPath = System.getProperty("user.dir") + "/" + photoPath;
                ImageIcon icon = new ImageIcon(fullPath);

                // Vérification robuste que l'image est bien chargée
                if (icon.getIconWidth() > 0 && icon.getIconHeight() > 0) {
                    scaledImage = icon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                    hasValidImage = true;
                }
            } catch (Exception ignored) {
                // → on affiche l'emoji en cas d'erreur ou image invalide
            }

            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fond gris clair arrondi
                g2.setColor(new Color(245, 245, 245));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                if (hasValidImage && scaledImage != null) {
                    int x = (getWidth() - scaledImage.getWidth(null)) / 2;
                    int y = (getHeight() - scaledImage.getHeight(null)) / 2;
                    g2.drawImage(scaledImage, x, y, this);
                } else {
                    // Emoji de remplacement
                    g2.setColor(new Color(140, 140, 140));
                    g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 52));

                    FontMetrics fm = g2.getFontMetrics();
                    String emoji = "📦";
                    int x = (getWidth() - fm.stringWidth(emoji)) / 2;
                    int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;

                    g2.drawString(emoji, x, y);
                }
            } finally {
                g2.dispose();
            }
        }
    }

    private JButton creerBoutonAvatar(String path) { 
        java.net.URL imgUrl = getClass().getResource(path); 
        final Image bruteImage = (imgUrl != null) ? new ImageIcon(imgUrl).getImage(): null; 

        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (bruteImage != null) {
                    g2.drawImage(bruteImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Si image non trouvée, on peut mettre un carré rouge pour debug
                    g2.setColor(Color.RED);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }

                g2.dispose();
            }
        };

        btn.setPreferredSize(new Dimension(30, 30));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);

        return btn;
    }

    private void validerCommande() {
        String[] modes = {"Carte bancaire", "PayPal", "Virement", "À la livraison"};
        String mode = (String) JOptionPane.showInputDialog(this,
            "Choisissez le mode de paiement:",
            "Paiement",
            JOptionPane.QUESTION_MESSAGE,
            null,
            modes,
            modes[0]);
            
        if (mode != null) {
            if (panierCtrl.validerPanier(mode)) {
                JOptionPane.showMessageDialog(this,
                    "Commande validée avec succès !",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
                chargerContenuPanier();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Erreur lors de la validation de la commande",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public JPanel getPanelPrincipal(){
        return panelPrincipal;
    }
    
    public JButton getBtnRetour(){
        return btnRetour;
    }
}