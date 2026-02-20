package InterfaceIHM;


/**
 * @author Héloïse  
 */

import java.awt.*;
import javax.swing.*;
import java.io.InputStream;
import Controllers.*;
import Metier.Article;
import Metier.Categorie;
import java.awt.event.HierarchyEvent;
import java.util.List;

public class InterfaceClient extends javax.swing.JPanel {
    private ClientController clientCtrl;
    private PanierController panierCtrl;
    private AdminController adminCtrl;
    private String nbrArt;
    
    private JFrame mainFrame;

    
    private final Color CARD_BG = Color.WHITE;
    private final Color PRIMARY_ORANGE = new Color(255, 87, 34);
    private final Color PRINCIPAL_ORANGE = new Color(255, 87, 34);
    private final Color TEXT_DARK = new Color(33, 33, 33);
    private final Color TEXT_LIGHT = new Color(120, 120, 120);
    private final Color CATEGORY_SELECTED = new Color(240, 240, 240);
    
    private Font robotoFont;
    private Font bungeeFont;
    private int categorieSelectionneeId = -1;
    private List<Categorie> categories;
    private List<Article> articles;

    public InterfaceClient(ClientController clientCtrl, PanierController panierCtrl) {
        this.clientCtrl = clientCtrl;
        this.panierCtrl = panierCtrl;
        this.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
                if (isShowing()) {
                    mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                    System.out.println("mainFrame capturé: " + mainFrame.getTitle());
                }
            }
        });
        chargerPolices();
        initComponents();
        configurerLayout();
        chargerCategories();
        afficherSectionPromotion();
        afficherArticlesPopulaires();
    }
    
    private void chargerCategories() {
        categories = clientCtrl.getToutesCategories();
        afficherCategoriesModernes();
    }
    
    private void chargerArticlesParCategorie(int idCategorie) {
        if (idCategorie <= 0) {
            articles = clientCtrl.getTousArticles();
        } else {
            articles = clientCtrl.getArticlesParCategorie(idCategorie);
        }
        afficherArticlesPopulaires();
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
    
    private void configurerLayout() {
        panelPrincipal.setLayout(new BorderLayout(0, 0));
        
        panelPrincipal.add(creerHeader(), BorderLayout.NORTH);
        
        JPanel conteneur = new JPanel(new BorderLayout(20, 0));
        conteneur.setOpaque(false);
        conteneur.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        conteneur.add(panelCategorie, BorderLayout.WEST);
        
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
        
        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        searchIcon.setForeground(TEXT_LIGHT);
        
        searchText = new JTextField("Rechercher un article...");
        searchText.setFont(robotoFont.deriveFont(Font.PLAIN, 14f));
        searchText.setForeground(TEXT_LIGHT);
        searchText.setBorder(null);
        searchText.setOpaque(false);
        searchText.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchText.getText().equals("Rechercher un article...")) {
                    searchText.setText("");
                    searchText.setForeground(TEXT_DARK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchText.getText().isEmpty()) {
                    searchText.setText("Rechercher un article...");
                    searchText.setForeground(TEXT_LIGHT);
                }
            }
        });
        
        searchText.addActionListener(e -> rechercherArticles());
        
        searchBox.add(searchIcon, BorderLayout.WEST);
        searchBox.add(searchText, BorderLayout.CENTER);
        searchContainer.add(Box.createHorizontalGlue());
        searchContainer.add(searchBox);
        searchContainer.add(Box.createHorizontalGlue());
        
        return searchContainer;
    }
    
    private void rechercherArticles() {
        String recherche = searchText.getText();
        if (!recherche.isEmpty() && !recherche.equals("Rechercher un article...")) {
            articles = clientCtrl.rechercherArticlesParNom(recherche);
            afficherArticlesPopulaires();
        }
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

        if (clientCtrl.estConnecte()) {
            // Bouton admin (visible seulement pour les admins)
            if (clientCtrl.estAdmin()) {
                btnAdmin = creerBoutonIconeAvecBadge("./ressources/icons/store.png", "A");
                btnAdmin.addActionListener(evt -> {
                    try {
                        // Utiliser mainFrame s'il est disponible
                        if (mainFrame == null) {
                            mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                        }

                        if (mainFrame != null) {
                            // Utiliser la même fenêtre
                            AdminController adminCtrl = new AdminController(clientCtrl.getConnection());
                            JPanel panelAdmin = new AdminInterface(adminCtrl).getPanelPrincipal();

                            mainFrame.getContentPane().removeAll();
                            mainFrame.getContentPane().add(panelAdmin);
                            mainFrame.revalidate();
                            mainFrame.repaint();
                        } else {
                            // Fallback: nouvelle fenêtre
                            JFrame newFrame = new JFrame("Administration");
                            newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            newFrame.setSize(1200, 800);
                            newFrame.setLocationRelativeTo(null);

                            AdminController adminCtrl = new AdminController(clientCtrl.getConnection());
                            newFrame.add(new AdminInterface(adminCtrl).getPanelPrincipal());
                            newFrame.setVisible(true);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null,
                            "Erreur: " + e.getMessage(),
                            "Erreur", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                });
                panel.add(btnAdmin);
            } else {
                // Bouton panier (visible seulement pour les clients)
                nbrArt = String.valueOf(panierCtrl.getNombreArticlesDansPanier());
                btnPanier = creerBoutonIconeAvecBadge("./ressources/images/panierIcon.png", nbrArt);
                btnPanier.addActionListener(evt -> {
                    JPanel panelContenuPanier = panierCtrl.showContenuPanier();
                    clientCtrl.switchView(panelContenu, panelContenuPanier);
                });
                panel.add(btnPanier);
            }

            // Bouton utilisateur (visible pour tous les connectés)
            btnUser = creerBoutonAvatar("./ressources/images/userIcon.png");
            btnUser.addActionListener(evt -> {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(btnUser);
                clientCtrl.showProfilPopup(topFrame, btnUser);
            });
            panel.add(btnUser);

        } 
        else {
            // Non connecté - afficher les liens de connexion/inscription
            JLabel seConnecter = creerLabel("Se connecter", 14f, Font.BOLD);
            seConnecter.setForeground(TEXT_LIGHT);
            seConnecter.setCursor(new Cursor(Cursor.HAND_CURSOR));

            JLabel creerCompte = creerLabel("Créer un compte", 14f, Font.PLAIN);
            creerCompte.setForeground(TEXT_LIGHT);
            creerCompte.setCursor(new Cursor(Cursor.HAND_CURSOR));

            seConnecter.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e){
                    afficherFormulaireConnexion();
                }
            });

            creerCompte.addMouseListener( new java.awt.event.MouseAdapter(){
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e){
                    afficherFormulaireInscription();
                }
            });

            panel.add(creerCompte);
            panel.add(seConnecter);
        }

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
                    int x = (getWidth() - 28) / 2 -2;
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

    private void afficherCategoriesModernes() {
        panelCategorie.removeAll();
        panelCategorie.setLayout(new BoxLayout(panelCategorie, BoxLayout.Y_AXIS));
        panelCategorie.setOpaque(false);
        panelCategorie.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));
        panelCategorie.setPreferredSize(new Dimension(200, 0));
        
        JLabel titre = new JLabel("Categories");
        titre.setFont(robotoFont.deriveFont(Font.BOLD, 20f));
        titre.setForeground(TEXT_DARK);
        titre.setAlignmentX(Component.LEFT_ALIGNMENT);
        titre.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelCategorie.add(titre);
        
        // Bouton "Tous les articles"
        panelCategorie.add(creerBoutonCategorie("Tous les articles", -1));
        
        for (Categorie cat : categories) {
            panelCategorie.add(creerBoutonCategorie(cat.getNomCategorie(), cat.getIdCategorie()));
        }
        
        panelCategorie.revalidate();
        panelCategorie.repaint();
    }
    
    private JButton creerBoutonCategorie(String nom, int idCategorie) {
        JButton btn = new JButton(nom) {
            private boolean isHovered = false;
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (idCategorie == categorieSelectionneeId || isHovered) {
                    g2.setColor(CATEGORY_SELECTED);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                }
                
                g2.setColor(idCategorie == categorieSelectionneeId ? TEXT_DARK : TEXT_LIGHT);
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
                btn.repaint();
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.repaint();
            }
        });
        
        btn.addActionListener(e -> {
            categorieSelectionneeId = idCategorie;
            chargerArticlesParCategorie(idCategorie);
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
        
        JPanel bigSaleCard = creerCarteBigSale();
        bigSaleCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        
        promoSection.add(bigSaleCard);
        promoSection.add(Box.createHorizontalGlue());
        
        panelContenu.add(promoSection);
    }
    
    private JPanel creerCarteBigSale() {
        JPanel carte = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
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
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        
        JLabel bigSale = new JLabel("Promotion !");
        bigSale.setFont(bungeeFont.deriveFont(Font.BOLD, 32f));
        bigSale.setForeground(TEXT_DARK);
        
        JLabel subtitle = new JLabel("<html>Nouveaux articles<br>à découvrir</html>");
        subtitle.setFont(robotoFont.deriveFont(Font.PLAIN, 14f));
        subtitle.setForeground(TEXT_LIGHT);
        subtitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        
        JButton btnDecouvrir = creerBoutonOrange("Découvrir");
        btnDecouvrir.addActionListener(e -> {
            categorieSelectionneeId = -1;
            chargerArticlesParCategorie(-1);
        });
        
        textPanel.add(bigSale);
        textPanel.add(subtitle);
        textPanel.add(btnDecouvrir);
        
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
    
    private void afficherArticlesPopulaires() {
        if (articles == null) {
            articles = clientCtrl.getTousArticles();
        }
        
        panelContenu.removeAll();
        
        // Section des articles
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setOpaque(false);
        section.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        String titreTexte = categorieSelectionneeId == -1 ? "Nos articles" : 
                           categories.stream()
                           .filter(c -> c.getIdCategorie() == categorieSelectionneeId)
                           .findFirst()
                           .map(Categorie::getNomCategorie)
                           .orElse("Articles");
        
        JLabel titre = new JLabel(titreTexte);
        titre.setFont(robotoFont.deriveFont(Font.BOLD, 20f));
        titre.setForeground(TEXT_DARK);
        
        header.add(titre, BorderLayout.WEST);
        
        // Grille d'articles
        JPanel grid = new JPanel(new GridLayout(0, 2, 20, 20));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        for (Article article : articles) {
            grid.add(creerArticleCard(article));
        }
        
        if (articles.isEmpty()) {
            JLabel aucunArticle = new JLabel("Aucun article disponible");
            aucunArticle.setFont(robotoFont.deriveFont(Font.PLAIN, 16f));
            aucunArticle.setForeground(TEXT_LIGHT);
            aucunArticle.setAlignmentX(Component.CENTER_ALIGNMENT);
            grid.add(aucunArticle);
        }
        
        section.add(header);
        section.add(grid);
        
        panelContenu.add(section);
        panelContenu.revalidate();
        panelContenu.repaint();
    }
    
    // Classe interne dédiée pour le panneau d'image (réutilisable et claire)
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

// ────────────────────────────────────────────────────────────────
// Méthode principale
// ────────────────────────────────────────────────────────────────
    private JPanel creerArticleCard(Article article) {
        // Carte principale avec coins arrondis et bordure légère
        JPanel carte = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                try {
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.WHITE);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                    g2.setColor(new Color(230, 230, 230));
                    g2.setStroke(new BasicStroke(1f));
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                } finally {
                    g2.dispose();
                }
            }
        };

        carte.setLayout(new BorderLayout(12, 12));
        carte.setOpaque(false);
        carte.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // Image
        ArticleImagePanel imagePanel = new ArticleImagePanel();
        imagePanel.setArticleImage(article.getUrlPhoto());

        // Infos
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Nom
        JLabel nomLabel = new JLabel(article.getNom() != null ? article.getNom() : "Article sans nom");
        nomLabel.setFont(robotoFont.deriveFont(Font.BOLD, 16f));
        nomLabel.setForeground(TEXT_DARK);
        nomLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Description
        String desc = article.getDescription();
        if (desc == null || desc.trim().isEmpty()) {
            desc = "Aucune description disponible";
        }
        JLabel descLabel = new JLabel("<html><div style='width:180px;'>" + desc + "</div></html>");
        descLabel.setFont(robotoFont.deriveFont(Font.PLAIN, 12f));
        descLabel.setForeground(TEXT_LIGHT);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descLabel.setBorder(BorderFactory.createEmptyBorder(4, 0, 8, 0));

        // Prix
        JLabel prixLabel = new JLabel(String.format("%.0f FCFA", article.getPrix()));
        prixLabel.setFont(robotoFont.deriveFont(Font.BOLD, 18f));
        prixLabel.setForeground(PRINCIPAL_ORANGE);
        prixLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Stock
        int stock = article.getStock();
        JLabel stockLabel = new JLabel("Stock : " + stock);
        stockLabel.setFont(robotoFont.deriveFont(Font.PLAIN, 12f));
        stockLabel.setForeground(stock > 0 ? new Color(46, 125, 50) : new Color(211, 47, 47));
        stockLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Bouton
        JButton btnAjouter = creerBoutonNoir("Ajouter au panier");
        btnAjouter.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnAjouter.setMaximumSize(new Dimension(160, 38));
        btnAjouter.setPreferredSize(new Dimension(160, 38));

        btnAjouter.addActionListener(e -> {
            if (!clientCtrl.estConnecte()) {
                int choix = JOptionPane.showConfirmDialog(
                    this,
                    "Vous devez être connecté pour ajouter au panier.\nVoulez-vous vous connecter ?",
                    "Connexion requise",
                    JOptionPane.YES_NO_OPTION
                );
                if (choix == JOptionPane.YES_OPTION) {
                    afficherFormulaireConnexion();
                }
                return;
            }

            if (stock <= 0) {
                JOptionPane.showMessageDialog(this, "Produit en rupture de stock", "Indisponible", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String input = JOptionPane.showInputDialog(
                this,
                "Quantité (disponible : " + stock + ") :",
                "1"
            );

            if (input == null || input.trim().isEmpty()) return;

            try {
                int qte = Integer.parseInt(input.trim());
                if (qte <= 0) {
                    JOptionPane.showMessageDialog(this, "Quantité invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (qte > stock) {
                    JOptionPane.showMessageDialog(this, "Stock insuffisant", "Erreur", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (panierCtrl.ajouterArticle(article.getId_article(), qte)) {
                    String nouveauNbr = String.valueOf(panierCtrl.getNombreArticlesDansPanier());
                    setNbrArt(nouveauNbr);
                    JOptionPane.showMessageDialog(this, "Article ajouté !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Échec de l'ajout au panier", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Entrez un nombre valide", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Assemblage vertical des éléments infos
        infoPanel.add(nomLabel);
        infoPanel.add(descLabel);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(prixLabel);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(stockLabel);
        infoPanel.add(Box.createVerticalGlue());
        infoPanel.add(btnAjouter);

        // Disposition finale de la carte
        carte.add(imagePanel, BorderLayout.WEST);
        carte.add(infoPanel, BorderLayout.CENTER);

        return carte;
    }

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
        
        btn.setFont(robotoFont.deriveFont(Font.BOLD, 12f));
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(120, 35));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return btn;
    }
    private void afficherVueAccueil(){
        panelContenu.removeAll();
        afficherSectionPromotion();
        chargerCategories();
        panelContenu.revalidate();
        panelContenu.repaint();
    }
    
    private void afficherFormulaireConnexion() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
            "Connexion", true);
        FormulaireConnexion formPanel = new FormulaireConnexion(clientCtrl, dialog);  // ← Passez le dialogue
        dialog.getContentPane().add(formPanel.getPanelPrincipal());
        dialog.setResizable(false);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        // Rafraîchir l'interface après connexion
        if (clientCtrl.estConnecte()) {
            // Mettre à jour l'en-tête
            
            panelPrincipal.removeAll();
            configurerLayout();
            panelPrincipal.revalidate();
            panelPrincipal.repaint();
        }
    }
    /*
    System.out.println("Dans la condition: "+formPanel.getEstConnec());
    */
    
    private void afficherFormulaireInscription() {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog((Frame) parentWindow, "Créer un compte", true);
        FormulaireInscription formPanel = new FormulaireInscription(clientCtrl);

        dialog.getContentPane().add(formPanel.getPanelPrincipal());
        dialog.setResizable(false);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    

    private void initComponents() {
        panelPrincipal = new JPanel();
        panelCategorie = new JPanel();
        panelContenu = new JPanel();
        panelvue = new JPanel();
        labelLogo = new JLabel();
        searchText = new JTextField();
        btnPanier = new JButton();
        btnUser = new JButton();
        btnAdmin = new JButton();
    }
    
    private JPanel panelPrincipal;
    private JPanel panelCategorie;
    private JPanel panelContenu;
    private JPanel panelvue;
    private JPanel searchBar;
    private JLabel labelLogo;
    private JTextField searchText;
    private JButton btnPanier;
    private JButton btnUser;
    private JButton btnAdmin;

    public String getNbrArt() {
        return nbrArt;
    }

    public void setNbrArt(String nbrArt) {
        this.nbrArt = nbrArt;
    }
    
    
    public JPanel getPanelPrincipal(){
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }
    
}