package groupe3_gestionvente;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.io.InputStream;
import java.awt.font.TextAttribute;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import Controllers.*;

/**
 * Interface client moderne - E-commerce
 * @author Héloïse (Version améliorée)
 */
public class InterfaceClient extends javax.swing.JPanel {
    ClientController clientCtrl;
    
    // Couleurs du thème
    private final Color BACKGROUND = new Color(245, 247, 250);
    private final Color CARD_BG = Color.WHITE;
    private final Color PRIMARY_ORANGE = new Color(255, 87, 34);
    private final Color TEXT_DARK = new Color(33, 33, 33);
    private final Color TEXT_LIGHT = new Color(120, 120, 120);
    private final Color CATEGORY_SELECTED = new Color(240, 240, 240);
    
    private Font robotoFont;
    private Font bungeeFont;
    private String categorieSelectionnee = null;

    public InterfaceClient() {
        try {
            clientCtrl = new ClientController();
        } catch (Exception e) {
            System.out.println("ClientController non disponible - Mode démo");
            clientCtrl = null;
        }
        
        chargerPolices();
        initComponents();
        configurerLayout();
        afficherCategoriesModernes();
        afficherSectionPromotion();
        afficherCategoriesPopulaires();
    }
    
    /**
     * Charge les polices personnalisées
     */
    private void chargerPolices() {
        try {
            InputStream is = getClass().getResourceAsStream("./ressources/fonts/Roboto.ttf");
            if (is != null) {
                robotoFont = Font.createFont(Font.TRUETYPE_FONT, is);
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(robotoFont);
                is.close();
            }
        } catch (Exception e) {
            robotoFont = new Font("Segoe UI", Font.PLAIN, 14);
        }
        
        try {
            InputStream is = getClass().getResourceAsStream("./ressources/fonts/Bungee-Regular.ttf");
            if (is != null) {
                bungeeFont = Font.createFont(Font.TRUETYPE_FONT, is);
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(bungeeFont);
                is.close();
            }
        } catch (Exception e) {
            bungeeFont = new Font("SansSerif", Font.BOLD, 18);
        }
    }
    
    /**
     * Configure le layout principal
     */
    private void configurerLayout() {
        panelPrincipal.setLayout(new BorderLayout(0, 0));
        panelPrincipal.setBackground(BACKGROUND);
        
        // Header
        panelPrincipal.add(creerHeader(), BorderLayout.NORTH);
        
        // Contenu avec catégories à gauche
        JPanel conteneur = new JPanel(new BorderLayout(20, 0));
        conteneur.setOpaque(false);
        conteneur.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Panel des catégories à gauche
        conteneur.add(panelCategorie, BorderLayout.WEST);
        
        // Panel du contenu principal
        panelContenu.setLayout(new BoxLayout(panelContenu, BoxLayout.Y_AXIS));
        panelContenu.setOpaque(false);
        
        JScrollPane scrollPane = new JScrollPane(panelContenu);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        conteneur.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(conteneur, BorderLayout.CENTER);
    }
    
    /**
     * Crée le header moderne
     */
    private JPanel creerHeader() {
        JPanel header = new JPanel(new BorderLayout(20, 0));
        header.setBackground(CARD_BG);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));
        
        // Logo à gauche
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        logoPanel.setOpaque(false);
        
        try {
            java.net.URL logoUrl = getClass().getResource("./ressources/images/logo.png");
            if (logoUrl != null) {
                ImageIcon icon = new ImageIcon(logoUrl);
                Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                labelLogo = new JLabel(new ImageIcon(img));
            } else {
                // Logo par défaut si image absente
                labelLogo = new JLabel("🛍️");
                labelLogo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 35));
                labelLogo.setForeground(PRIMARY_ORANGE);
            }
        } catch (Exception e) {
            labelLogo = new JLabel("Shop");
            labelLogo.setFont(bungeeFont.deriveFont(Font.BOLD, 24f));
            labelLogo.setForeground(PRIMARY_ORANGE);
        }
        logoPanel.add(labelLogo);
        
        // Barre de recherche au centre
        searchBar = creerBarreRecherche();
        
        // Icônes à droite (filtre, panier, favoris, user)
        JPanel rightPanel = creerPanelDroit();
        
        header.add(logoPanel, BorderLayout.WEST);
        header.add(searchBar, BorderLayout.CENTER);
        header.add(rightPanel, BorderLayout.EAST);
        
        return header;
    }
    
    /**
     * Crée la barre de recherche moderne
     */
    private JPanel creerBarreRecherche() {
        JPanel searchContainer = new JPanel();
        searchContainer.setLayout(new BoxLayout(searchContainer, BoxLayout.X_AXIS));
        searchContainer.setOpaque(false);
        searchContainer.setMaximumSize(new Dimension(500, 45));
        searchContainer.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100));
        
        JPanel searchBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(new Color(245, 245, 245));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                
                g2.dispose();
            }
        };
        searchBox.setLayout(new BorderLayout(10, 0));
        searchBox.setOpaque(false);
        searchBox.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        searchBox.setPreferredSize(new Dimension(400, 45));
        
        // Icône de recherche
        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        searchIcon.setForeground(TEXT_LIGHT);
        
        searchText = new JTextField("Search");
        searchText.setFont(robotoFont.deriveFont(Font.PLAIN, 14f));
        searchText.setForeground(TEXT_LIGHT);
        searchText.setBorder(null);
        searchText.setOpaque(false);
        searchText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchText.getText().equals("Search")) {
                    searchText.setText("");
                    searchText.setForeground(TEXT_DARK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchText.getText().isEmpty()) {
                    searchText.setText("Search");
                    searchText.setForeground(TEXT_LIGHT);
                }
            }
        });
        
        searchBox.add(searchIcon, BorderLayout.WEST);
        searchBox.add(searchText, BorderLayout.CENTER);
        
        searchContainer.add(Box.createHorizontalGlue());
        searchContainer.add(searchBox);
        searchContainer.add(Box.createHorizontalGlue());
        
        return searchContainer;
    }
    
    /**
     * Crée le panel droit avec les icônes
     */
    private JPanel creerPanelDroit() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panel.setOpaque(false);
        
        // Bouton filtre
        JButton btnFiltre = creerBoutonIcone("☰", false);
        
        // Bouton panier avec badge
        btnPanier = creerBoutonIconeAvecBadge("🛒", "4");
        btnPanier.addActionListener(evt -> {
            if (clientCtrl != null) {
                JPanel panelContenuPanier = clientCtrl.showContenuPanier();
                clientCtrl.switchView(panelContenu, panelContenuPanier);
            } else {
                JOptionPane.showMessageDialog(this, "Panier - Fonctionnalité à connecter", 
                                            "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // Bouton favoris
        JButton btnFavoris = creerBoutonIcone("🤍", false);
        
        // Bouton user avec avatar
        btnUser = creerBoutonAvatar();
        btnUser.addActionListener(evt -> {
            if (clientCtrl != null) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(btnUser);
                clientCtrl.showProfilPopup(topFrame, btnUser);
            } else {
                JOptionPane.showMessageDialog(this, "Profil utilisateur - Fonctionnalité à connecter", 
                                            "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        panel.add(btnFiltre);
        panel.add(btnPanier);
        panel.add(btnFavoris);
        panel.add(btnUser);
        
        return panel;
    }
    
    /**
     * Crée un bouton icône simple
     */
    private JButton creerBoutonIcone(String emoji, boolean isToggle) {
        JButton btn = new JButton(emoji);
        btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        btn.setPreferredSize(new Dimension(45, 45));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    /**
     * Crée un bouton avec badge de notification
     */
    private JButton creerBoutonIconeAvecBadge(String emoji, String badgeText) {
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Badge orange
                g2.setColor(PRIMARY_ORANGE);
                g2.fillOval(getWidth() - 18, 2, 16, 16);
                
                // Nombre
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 10));
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(badgeText);
                g2.drawString(badgeText, getWidth() - 10 - textWidth/2, 13);
                
                g2.dispose();
            }
        };
        
        btn.setText(emoji);
        btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        btn.setPreferredSize(new Dimension(45, 45));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return btn;
    }
    
    /**
     * Crée le bouton avatar utilisateur
     */
    private JButton creerBoutonAvatar() {
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Cercle de fond
                g2.setColor(new Color(255, 200, 180));
                g2.fillOval(2, 2, getWidth() - 4, getHeight() - 4);
                
                // Emoji utilisateur
                g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
                FontMetrics fm = g2.getFontMetrics();
                String emoji = "👤";
                int x = (getWidth() - fm.stringWidth(emoji)) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(emoji, x, y);
                
                g2.dispose();
            }
        };
        
        btn.setPreferredSize(new Dimension(40, 40));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return btn;
    }
    
    /**
     * Affiche les catégories dans le panel gauche
     */
    private void afficherCategoriesModernes() {
        panelCategorie.setLayout(new BoxLayout(panelCategorie, BoxLayout.Y_AXIS));
        panelCategorie.setOpaque(false);
        panelCategorie.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));
        panelCategorie.setPreferredSize(new Dimension(200, 0));
        
        // Titre "Categories"
        JLabel titre = new JLabel("Categories");
        titre.setFont(robotoFont.deriveFont(Font.BOLD, 20f));
        titre.setForeground(TEXT_DARK);
        titre.setAlignmentX(Component.LEFT_ALIGNMENT);
        titre.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelCategorie.add(titre);
        
        // Liste des catégories
        String[] categories = {
            "Electronics", "Computers", "Clothes", "Arts & Crafts",
            "Toys & Games", "Jewelry", "Beauty & Care", "Mother & Kids",
            "Home Design", "Sports", "Pet Supplies"
        };
        
        for (String cat : categories) {
            panelCategorie.add(creerBoutonCategorie(cat));
        }
    }
    
    /**
     * Crée un bouton de catégorie
     */
    private JButton creerBoutonCategorie(String nom) {
        JButton btn = new JButton(nom) {
            private boolean isHovered = false;
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fond si sélectionné ou hover
                if (nom.equals(categorieSelectionnee) || isHovered) {
                    g2.setColor(CATEGORY_SELECTED);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                }
                
                // Texte
                g2.setColor(nom.equals(categorieSelectionnee) ? TEXT_DARK : TEXT_LIGHT);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(), 10, (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                
                g2.dispose();
            }
        };
        
        btn.setFont(robotoFont.deriveFont(Font.PLAIN, 14f));
        btn.setForeground(TEXT_LIGHT);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 40));
        btn.setPreferredSize(new Dimension(200, 40));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setForeground(TEXT_DARK);
                btn.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!nom.equals(categorieSelectionnee)) {
                    btn.setForeground(TEXT_LIGHT);
                }
                btn.repaint();
            }
        });
        
        btn.addActionListener(e -> {
            categorieSelectionnee = nom;
            panelCategorie.repaint();
        });
        
        return btn;
    }
    
    /**
     * Affiche la section promotionnelle (BIG SALE uniquement)
     */
    private void afficherSectionPromotion() {
        JPanel promoSection = new JPanel();
        promoSection.setLayout(new BoxLayout(promoSection, BoxLayout.X_AXIS));
        promoSection.setOpaque(false);
        promoSection.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        promoSection.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Carte "BIG SALE" avec casque
        JPanel bigSaleCard = creerCarteBigSale();
        bigSaleCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        
        promoSection.add(bigSaleCard);
        promoSection.add(Box.createHorizontalGlue());
        
        panelContenu.add(promoSection);
    }
    
    /**
     * Crée la carte BIG SALE
     */
    private JPanel creerCarteBigSale() {
        JPanel carte = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Dégradé de fond (bleu clair à blanc)
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(220, 235, 255),
                    getWidth(), getHeight(), new Color(240, 245, 255)
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                g2.dispose();
            }
        };
        
        carte.setLayout(new BorderLayout(20, 20));
        carte.setOpaque(false);
        carte.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Texte à gauche
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        
        JLabel bigSale = new JLabel("BIG SALE!");
        bigSale.setFont(bungeeFont.deriveFont(Font.BOLD, 32f));
        bigSale.setForeground(TEXT_DARK);
        
        JLabel subtitle = new JLabel("<html>Wireless headphones<br>with noise canceling</html>");
        subtitle.setFont(robotoFont.deriveFont(Font.PLAIN, 13f));
        subtitle.setForeground(TEXT_LIGHT);
        subtitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        
        JButton btnHeadphones = creerBoutonOrange("Headphones");
        
        textPanel.add(bigSale);
        textPanel.add(subtitle);
        textPanel.add(btnHeadphones);
        
        // Image du casque
        JLabel imageLabel = new JLabel();
        try {
            java.net.URL url = getClass().getResource("./ressources/images/casque.png");
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(img));
            } else {
                imageLabel.setText("🎧");
                imageLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 100));
            }
        } catch (Exception e) {
            imageLabel.setText("🎧");
            imageLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 100));
        }
        
        carte.add(textPanel, BorderLayout.WEST);
        carte.add(imageLabel, BorderLayout.EAST);
        
        return carte;
    }
    
    /**
     * Affiche les catégories populaires
     */
    private void afficherCategoriesPopulaires() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setOpaque(false);
        section.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        // En-tête avec titre et "See all"
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JLabel titre = new JLabel("Explore popular categories");
        titre.setFont(robotoFont.deriveFont(Font.BOLD, 20f));
        titre.setForeground(TEXT_DARK);
        
        JLabel seeAll = new JLabel("See all →");
        seeAll.setFont(robotoFont.deriveFont(Font.PLAIN, 14f));
        seeAll.setForeground(TEXT_LIGHT);
        seeAll.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        header.add(titre, BorderLayout.WEST);
        header.add(seeAll, BorderLayout.EAST);
        
        // Grille de cartes
        JPanel grid = new JPanel(new GridLayout(1, 4, 20, 0));
        grid.setOpaque(false);
        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        grid.add(creerCategorieCard("Household goods", "./ressources/images/kettle.png", 
                                    new Color(215, 230, 240)));
        grid.add(creerCategorieCard("Game controllers", "./ressources/images/controller.png", 
                                    new Color(245, 240, 235)));
        grid.add(creerCategorieCard("Accessories", "./ressources/images/bag.png", 
                                    new Color(230, 235, 245)));
        grid.add(creerCategorieCard("Furniture", null, 
                                    new Color(255, 235, 220)));
        
        section.add(header);
        section.add(grid);
        
        panelContenu.add(section);
    }
    
    /**
     * Crée une carte de catégorie
     */
    private JPanel creerCategorieCard(String nom, String imagePath, Color bgColor) {
        JPanel carte = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(bgColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                g2.dispose();
            }
        };
        
        carte.setLayout(new BorderLayout());
        carte.setOpaque(false);
        carte.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        carte.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Image centrée
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        
        if (imagePath != null) {
            try {
                java.net.URL url = getClass().getResource(imagePath);
                if (url != null) {
                    ImageIcon icon = new ImageIcon(url);
                    Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(img));
                }
            } catch (Exception e) {
                imageLabel.setText(getEmojiForCategory(nom));
                imageLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
            }
        } else {
            imageLabel.setText(getEmojiForCategory(nom));
            imageLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        }
        
        // Nom en bas
        JLabel nomLabel = new JLabel(nom);
        nomLabel.setFont(robotoFont.deriveFont(Font.BOLD, 13f));
        nomLabel.setForeground(TEXT_DARK);
        nomLabel.setHorizontalAlignment(JLabel.CENTER);
        nomLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        carte.add(imageLabel, BorderLayout.CENTER);
        carte.add(nomLabel, BorderLayout.SOUTH);
        
        return carte;
    }
    
    /**
     * Retourne un emoji pour une catégorie
     */
    private String getEmojiForCategory(String nom) {
        if (nom.contains("Furniture")) return "🪑";
        if (nom.contains("controller")) return "🎮";
        if (nom.contains("Household")) return "🫖";
        if (nom.contains("Accessories")) return "👜";
        return "📦";
    }
    
    /**
     * Crée un bouton orange
     */
    private JButton creerBoutonOrange(String texte) {
        JButton btn = new JButton(texte) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(PRIMARY_ORANGE);
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
        
        btn.setFont(robotoFont.deriveFont(Font.BOLD, 13f));
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(140, 40));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return btn;
    }
    
    /**
     * Crée un bouton noir
     */
    private JButton creerBoutonNoir(String texte) {
        JButton btn = new JButton(texte) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(TEXT_DARK);
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
        
        btn.setFont(robotoFont.deriveFont(Font.BOLD, 13f));
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return btn;
    }
    
    private void initComponents() {
        panelPrincipal = new JPanel();
        panelCategorie = new JPanel();
        panelContenu = new JPanel();
        labelLogo = new JLabel();
        searchText = new JTextField();
        btnPanier = new JButton();
        btnUser = new JButton();
    }
    
    // Variables
    private JPanel panelPrincipal;
    private JPanel panelCategorie;
    private JPanel panelContenu;
    private JPanel searchBar;
    private JLabel labelLogo;
    private JTextField searchText;
    private JButton btnPanier;
    private JButton btnUser;
    
    /**
     * Méthode main pour tester l'interface
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("E-commerce Client");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1400, 900);
            frame.setLocationRelativeTo(null);
            
            InterfaceClient interfaceClient = new InterfaceClient();
            frame.add(interfaceClient.panelPrincipal);
            
            frame.setVisible(true);
        });
    }
}