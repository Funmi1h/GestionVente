package InterfaceIHM;

import java.awt.*;
import javax.swing.*;
import java.io.InputStream;
import Controllers.*;

/**
 * @author Héloïse  
 */
public class InterfaceClient extends javax.swing.JPanel {
    private ClientController clientCtrl;
    
    private final Color CARD_BG = Color.WHITE;
    private final Color PRIMARY_ORANGE = new Color(255, 87, 34);
    private final Color TEXT_DARK = new Color(33, 33, 33);
    private final Color TEXT_LIGHT = new Color(120, 120, 120);
    private final Color CATEGORY_SELECTED = new Color(240, 240, 240);
    
    private Font robotoFont;
    private Font bungeeFont;
    private String categorieSelectionnee = null;

    public InterfaceClient(ClientController clientCrl) {
        this.clientCtrl = clientCtrl;
        chargerPolices();
        initComponents();
        configurerLayout();
        afficherCategoriesModernes();
        afficherSectionPromotion();
        afficherCategoriesPopulaires();
    }
    
    
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
    
    //layoutPrincipal
    private void configurerLayout() {
        panelPrincipal.setLayout(new BorderLayout(0, 0));
        
        
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
        panelPrincipal.setBackground(CARD_BG);
        
        JScrollPane scrollPane = new JScrollPane(panelContenu);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        conteneur.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(conteneur, BorderLayout.CENTER);
    }

    private JPanel creerHeader() {
        JPanel header = new JPanel(new BorderLayout(20, 0));
        header.setOpaque(false);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));
        
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        logoPanel.setOpaque(false);
        
        try {
            java.net.URL logoUrl = getClass().getResource("./ressources/images/logo.png");
            if (logoUrl != null) {
                ImageIcon icon = new ImageIcon(logoUrl);
                Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                labelLogo = new JLabel(new ImageIcon(img));
            } else {
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
        
        JPanel rightPanel = creerPanelDroit();
        rightPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
       
        
        header.add(logoPanel, BorderLayout.WEST);
        header.add(searchBar, BorderLayout.CENTER);
        header.add(rightPanel, BorderLayout.EAST);
        
        return header;
    }
    
   
    private JPanel creerBarreRecherche() {
        JPanel searchContainer = new JPanel();
        //searchContainer.setLayout(new BoxLayout(searchContainer, BoxLayout.X_AXIS));
        searchContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        searchContainer.setOpaque(false);
        searchContainer.setMaximumSize(new Dimension(400, 45));
        searchContainer.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100));
        
        JPanel searchBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(new Color(245, 245, 245));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                
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
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchText.getText().equals("Search")) {
                    searchText.setText("");
                    searchText.setForeground(TEXT_DARK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchText.getText().isEmpty()) {
                    searchText.setText("Search");
                    searchText.setForeground(TEXT_LIGHT);
                }
            }
        });
        
        searchBox.add(searchIcon, BorderLayout.EAST);
        searchBox.add(searchText, BorderLayout.CENTER);
        searchBox.setPreferredSize(new Dimension(400, 40));
        searchContainer.add(Box.createHorizontalGlue());
        searchContainer.add(searchBox);
        searchContainer.add(Box.createHorizontalGlue());
        
        return searchContainer;
    }
    
   
    
    private JLabel creerLabel(String texte, float taille, int style){
        JLabel label = new JLabel(texte);
        label.setFont(robotoFont.deriveFont(style, taille));
        label.setForeground(TEXT_DARK);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 10, 8,0));
        return label;       
    }
   
    private JPanel creerPanelDroit() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panel.setOpaque(false);
        
        // Bouton panier avec badge
        btnPanier = creerBoutonIconeAvecBadge("./ressources/images/panierIcon.png", "4"); // le 4 represente le nombre de choses dans le panier controlleur a créer 
        btnPanier.addActionListener(evt -> {
            if (clientCtrl != null) {
                JPanel panelContenuPanier = clientCtrl.showContenuPanier();
                clientCtrl.switchView(panelContenu, panelContenuPanier);
            } else {
                JOptionPane.showMessageDialog(this, "Panier - Fonctionnalité à connecter", 
                                            "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        
        btnUser = creerBoutonAvatar("./ressources/images/userIcon.png");
        btnUser.addActionListener(evt -> {
            if (clientCtrl != null) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(btnUser);
                clientCtrl.showProfilPopup(topFrame, btnUser);
            } else {
                JOptionPane.showMessageDialog(this, "Profil utilisateur - Fonctionnalité à connecter", 
                                            "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        //labels de connection et inscription et espace admin
        JLabel seConnecter = creerLabel("Se connecter", 14f, Font.BOLD);
        seConnecter.setForeground(TEXT_LIGHT);
        seConnecter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel creerCompte = creerLabel("Créer un compte", 14f, Font.PLAIN);
        creerCompte.setForeground(TEXT_LIGHT);
        creerCompte.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel espaceAdmin = creerLabel("Espace admin", 14f, Font.PLAIN);
        espaceAdmin.setForeground(PRIMARY_ORANGE);
        espaceAdmin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        espaceAdmin.setForeground(PRIMARY_ORANGE);
        
        // listerners pour afficher le form de connexion et d'inscription
        seConnecter.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e){
                afficherFormulaireConnexion();

            }
        });
        
        espaceAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e){
                afficherFormulaireAdmin();

            }
        });
        
        creerCompte.addMouseListener( new java.awt.event.MouseAdapter(){
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e){
            afficherFormulaireInscription();
        }
        });
        
        if(clientCtrl != null){
            panel.add(btnPanier);
            panel.add(btnUser);
        }else{
            panel.add(creerCompte);
            panel.add(seConnecter);
            panel.add(espaceAdmin);
            
        }
        
        
            
         
        
        espaceAdmin.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseListener e){
                afficherFormulaireAdmin();
            }
            
        });
        
        return panel;
    }
    


    private JButton creerBoutonIconeAvecBadge(String path, String badgeText) {
        java.net.URL url = getClass().getResource(path);
        final Image imgBrute = (url != null) ? new ImageIcon(url).getImage() : null;

        if (imgBrute == null) {
            System.err.println("Icône panier introuvable -> " + path);
        }

        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();           
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                if (imgBrute != null) {
                    int x = (getWidth() - 25) / 2 - 3;
                    int y = (getHeight() - 25) / 2;
                    g2.drawImage(imgBrute, x, y, 25, 25, this); 
                } else {
                    g2.drawString("🛒", 10, 25);
                }

                g2.setColor(new Color(255, 69, 0)); // Un orange plus vif
                int badgeSize = 18;
                int badgeX = getWidth() - badgeSize - 2;
                int badgeY = 2;
                g2.fillOval(badgeX, badgeY, badgeSize, badgeSize);

                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 10));
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(badgeText);
                int textX = badgeX + (badgeSize - textWidth) / 2;
                int textY = badgeY + ((badgeSize - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(badgeText, textX, textY);
                g2.dispose();
            }
        };

        btn.setPreferredSize(new Dimension(50, 45));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }        
// avatar utilisateur 
    private JButton creerBoutonAvatar(String path) {
        java.net.URL imgUrl = getClass().getResource(path); 
        final Image bruteImage = (imgUrl!= null) ? new ImageIcon(imgUrl).getImage(): null; 

        try {
            java.net.URL imgURL = getClass().getResource(path);
            if (bruteImage != null) {
                ImageIcon icon = new ImageIcon(imgURL);
            }
        } catch (Exception e) {
            System.err.println("Erreur chargement avatar: " + e.getMessage());
        }

        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(255, 200, 180));
                //g2.fillOval(0, 0, getWidth(), getHeight());

                if (bruteImage != null) {
                    int x = (getWidth() - 28) / 2 -2;
                    //int y = (getHeight() - 28) / 2;
                    //int x = 4;
                    int y = 2;
                    g2.drawImage(bruteImage, x, y, this);
                }
                g2.dispose();
            }
        };

        btn.setPreferredSize(new Dimension(30, 30));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);

        return btn;
    }

    //le nom des categories dans le panel de gauche 
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
        
        // Liste des catégories on appellera le controleur qui renvoie la liste des catégories 
        String[] categories = {
            "Electronics", "Computers", "Clothes", "Arts & Crafts",
            "Toys & Games", "Jewelry", "Beauty & Care", "Mother & Kids",
            "Home Design", "Sports", "Pet Supplies"
        };
        
        for (String cat : categories) {
            panelCategorie.add(creerBoutonCategorie(cat));
        }
    }
    
    // le nom d'un catégorie sous forme de boutton 
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
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setForeground(TEXT_DARK);
                btn.repaint();
            }
            @Override
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
    
    //la grande bannierer de promotion 
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
        
        JLabel bigSale = new JLabel("Promotion !");
        bigSale.setFont(bungeeFont.deriveFont(Font.BOLD, 32f));
        bigSale.setForeground(TEXT_DARK);
        
        JLabel subtitle = new JLabel("<html>Casques sans fils</html>");
        subtitle.setFont(robotoFont.deriveFont(Font.PLAIN, 13f));
        subtitle.setForeground(TEXT_LIGHT);
        subtitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        
        JButton btnHeadphones = creerBoutonOrange("Casques");
        
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
    
    //les catégories populaires
    private void afficherCategoriesPopulaires() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setOpaque(false);
        section.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        // En-tête avec titre et "See all"
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JLabel titre = new JLabel("Nos articles ");
        titre.setFont(robotoFont.deriveFont(Font.BOLD, 20f));
        titre.setForeground(TEXT_DARK);
        
        JLabel seeAll = new JLabel("Voir tout ->");
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
    
    //creer Crte categorie
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
    
    // retourne un emoji mais je vais modifier ca pour que cela prenne l'url d'image et retourne les image 
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
    
    //btn noir 
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
    
    private void afficherFormulaireConnexion() {
        // ova afficher le formualaire dans une fenetre de dialogue
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog((Frame) parentWindow, "Connexion", true);
        FormulaireConnexion formPanel = new FormulaireConnexion();



    // Vous pouvez créer une interface de rappel (callback) ou simplement vérifier une condition
    /*
    formPanel.getBtnValider().addActionListener(e -> {
        // Logique de validation effectuée dans le panel...
        // Si la connexion est réussie, on ferme :
        // dialog.dispose();
    }); */

    // 4. Configuration finale du dialogue
    dialog.getContentPane().add(formPanel.getPanelPrincipal());
    dialog.setResizable(false);
    dialog.pack(); // Ajuste la taille automatiquement selon le JPanel
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true); 
    }
    private void afficherFormulaireAdmin() {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog((Frame) parentWindow, "Accéder à l'espace admin", true);
        AdminForm formPanel = new AdminForm();



    /*
    formPanel.getBtnValider().addActionListener(e -> {
        // Logique de validation effectuée dans le panel...
        // Si la connexion est réussie, on ferme :
        // dialog.dispose();
    }); */

    // 4. Configuration finale du dialogue
    dialog.getContentPane().add(formPanel.getPanelPrincipal());
    dialog.setResizable(false);
    dialog.pack(); // Ajuste la taille automatiquement selon le JPanel
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true); 
    }
    
    private void afficherFormulaireInscription() {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog((Frame) parentWindow, "Créer un compte ", true);
        FormulaireInscription formPanel = new FormulaireInscription();



        /*
        formPanel.getBtnValider().addActionListener(e -> {
            // Logique de validation effectuée dans le panel...
            // Si la connexion est réussie, on ferme :
            // dialog.dispose();
        }); */

        // 4. Configuration finale du dialogue
        dialog.getContentPane().add(formPanel.getPanelPrincipal());
        dialog.setResizable(false);
        dialog.pack(); // Ajuste la taille automatiquement selon le JPanel
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true); 
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
    
    private JPanel panelPrincipal;
    private JPanel panelCategorie;
    private JPanel panelContenu;
    private JPanel searchBar;
    private JLabel labelLogo;
    private JTextField searchText;
    private JButton btnPanier;
    private JButton btnUser;
    
    
    public JPanel getPanelPrincipal(){
        return panelPrincipal;
    }
    
}