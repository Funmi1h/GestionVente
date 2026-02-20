package InterfaceIHM;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author YACOUBOU
 */

import Controllers.AdminController;
import Controllers.ClientController;
import DAO.ConnexionDB;
import Metier.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AdminInterface extends JPanel {
    private AdminController adminCtrl;
    
    // Couleurs
    private final Color PRIMARY_BLUE = new Color(33, 150, 243);
    private final Color PRIMARY_COLOR = new Color(255, 87, 34);
    private final Color PRIMARY_DARK = new Color(25, 118, 210);
    private final Color SUCCESS_GREEN = new Color(76, 175, 80);
    private final Color WARNING_ORANGE = new Color(255, 152, 0);
    private final Color DANGER_RED = new Color(244, 67, 54);
    private final Color BACKGROUND_LIGHT = new Color(245, 245, 250);
    private final Color CARD_BG = Color.WHITE;
    private final Color TEXT_DARK = new Color(33, 33, 33);
    private final Color TEXT_LIGHT = new Color(120, 120, 120);
    private final Color BORDER_COLOR = new Color(230, 230, 230);
    
    // Polices
    private Font poppinsFont;
    private Font robotoFont;
    
    // Composants principaux
    private JPanel panelPrincipal;
    private JPanel panelHeader;
    private JPanel panelSidebar;
    private JPanel panelContent;
    private JScrollPane panelG;
    private CardLayout cardLayout;
    
    // Composants sidebar
    private SidebarButton btnDashboard;
    private SidebarButton btnArticles;
    private SidebarButton btnCategories;
    private SidebarButton btnClients;
    private SidebarButton btnCommandes;
    private SidebarButton btnPaiements;
    private SidebarButton btnStatistiques;
    private SidebarButton btnDeconnexion;
    
    // Panels de contenu
    private JPanel panelDashboard;
    private JPanel panelArticles;
    private JPanel panelCategories;
    private JPanel panelClients;
    private JPanel panelCommandes;
    private JPanel panelPaiements;
    private JPanel panelStatistiques;
    
    //
    private JList<Client> clientList;
    private JList<Article> articleList;
    private JTable categoriesTable;
    private DefaultTableModel categoriesTableModel;
    private JLabel lblTotalClients;
    private JLabel lblTotalArticles;
    private JLabel lblTotalCommandes;
    private JLabel lblChiffreAffaires;
    
    // État
    private String currentView = "dashboard";
    
    public AdminInterface(AdminController adminCtrl) {
        this.adminCtrl = adminCtrl;
        chargerPolices();
        initUI();
        chargerDonneesInitiales();
        switchView("dashboard");
    }
    
    private void chargerPolices() {
        try {
            InputStream is = getClass().getResourceAsStream("./ressources/fonts/Poppins-Regular.ttf");
            if (is != null) {
                poppinsFont = Font.createFont(Font.TRUETYPE_FONT, is);
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(poppinsFont);
                is.close();
            }
        } catch (Exception e) {
            poppinsFont = new Font("Segoe UI", Font.PLAIN, 14);
        }
        
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
    }
    
    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_LIGHT);
        
        // Panel principal avec BorderLayout
        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(BACKGROUND_LIGHT);
        
        // Création du header
        panelHeader = createHeader();
        
        // Création de la sidebar
        panelSidebar = createSidebar();
        
        // Création du contenu principal avec CardLayout
        cardLayout = new CardLayout();
        panelContent = new JPanel(cardLayout){
            @Override
            public Dimension getMaximumSize() {
                Dimension d = super.getMaximumSize();
                d.width = Integer.MAX_VALUE;
                return d; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            }
            
        };
        panelContent.setBackground(BACKGROUND_LIGHT);
        panelContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelG = new JScrollPane(panelContent);
        panelG.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panelG.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelG.getVerticalScrollBar().setUnitIncrement(16);
        
        // Création des différents panels
        createDashboardPanel();
        createArticlesPanel();
        createCategoriesPanel();
        createClientsPanel();
        createCommandesPanel();
        createPaiementsPanel();
        createStatistiquesPanel();
        
        // Assemblage
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(BACKGROUND_LIGHT);
        centerPanel.add(panelSidebar, BorderLayout.WEST);
        centerPanel.add(panelContent, BorderLayout.CENTER);
        
        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        panelPrincipal.add(centerPanel, BorderLayout.CENTER);
        add(panelPrincipal, BorderLayout.CENTER);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(CARD_BG);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR),
            BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));
        
        // Logo et titre
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftPanel.setOpaque(false);
        
        JLabel logoLabel = new JLabel("📊");
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        logoLabel.setForeground(PRIMARY_COLOR);
        
        JLabel titleLabel = new JLabel("Administration");
        titleLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 24f));
        titleLabel.setForeground(TEXT_DARK);
        
        leftPanel.add(logoLabel);
        leftPanel.add(titleLabel);
        
        // Info admin et date
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        rightPanel.setOpaque(false);
        
        // Date actuelle
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEEE dd MMMM yyyy");
        String dateStr = sdf.format(new java.util.Date());
        JLabel dateLabel = new JLabel(dateStr);
        dateLabel.setFont(robotoFont.deriveFont(Font.PLAIN, 14f));
        dateLabel.setForeground(TEXT_LIGHT);
        
        // Avatar admin
        JPanel adminPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        adminPanel.setOpaque(false);
        
        JLabel adminIcon = new JLabel("👤");
        adminIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        adminIcon.setForeground(PRIMARY_COLOR);
        
        JLabel adminName = new JLabel(adminCtrl.getAdminName());
        adminName.setFont(robotoFont.deriveFont(Font.BOLD, 14f));
        adminName.setForeground(TEXT_DARK);
        
        adminPanel.add(adminIcon);
        adminPanel.add(adminName);
        
        rightPanel.add(dateLabel);
        rightPanel.add(adminPanel);
        
        header.add(leftPanel, BorderLayout.WEST);
        header.add(rightPanel, BorderLayout.EAST);
        
        return header;
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(CARD_BG);
        sidebar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 0, 1, BORDER_COLOR),
            BorderFactory.createEmptyBorder(20, 10, 20, 10)
        ));
        sidebar.setPreferredSize(new Dimension(280, 0));
        
        // Menu principal
        JLabel menuLabel = new JLabel("MENU PRINCIPAL");
        menuLabel.setFont(robotoFont.deriveFont(Font.BOLD, 12f));
        menuLabel.setForeground(TEXT_LIGHT);
        menuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 0));
        sidebar.add(menuLabel);
        
        // Boutons du menu
        btnDashboard = createSidebarButton("./ressources/icons/dashboard.png", "Tableau de bord");
        btnArticles = createSidebarButton("./ressources/icons/label.png", "Gestion des articles");
        btnCategories = createSidebarButton("./ressources/icons/bag_icon.png", "Catégories");
        btnClients = createSidebarButton("./ressources/icons/users.png", "Clients");
        btnCommandes = createSidebarButton("./ressources/icons/comPanier.png", "Commandes");
        btnPaiements = createSidebarButton("./ressources/icons/payment.png", "Paiements");
        btnStatistiques = createSidebarButton("./ressources/icons/statistic.png", "Statistiques");
        
        sidebar.add(btnDashboard);
        sidebar.add(btnArticles);
        sidebar.add(btnCategories);
        sidebar.add(btnClients);
        sidebar.add(btnCommandes);
        sidebar.add(btnPaiements);
        sidebar.add(btnStatistiques);
        
        sidebar.add(Box.createVerticalGlue());
        
        // Séparateur
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(250, 1));
        separator.setForeground(BORDER_COLOR);
        sidebar.add(separator);
        sidebar.add(Box.createVerticalStrut(10));
        
        // Bouton déconnexion
        btnDeconnexion = createSidebarButton("./ressources/icons/del.png", "Déconnexion");
        btnDeconnexion.addActionListener(e -> deconnecter());
        sidebar.add(btnDeconnexion);
        
        return sidebar;
    }
    
    private SidebarButton createSidebarButton(String iconPath, String text) {

        java.net.URL imgUrl = getClass().getResource(iconPath);
        ImageIcon icon = null;

        if (imgUrl != null) {
            Image img = new ImageIcon(imgUrl).getImage()
                    .getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
        }

        SidebarButton btn = new SidebarButton(text, icon);

        btn.addActionListener(e -> {
            resetSidebarButtons();
            btn.setActive(true);

            if (btn == btnDashboard) switchView("dashboard");
            else if (btn == btnArticles) switchView("articles");
            else if (btn == btnCategories) switchView("categories");
            else if (btn == btnClients) switchView("clients");
            else if (btn == btnCommandes) switchView("commandes");
            else if (btn == btnPaiements) switchView("paiements");
            else if (btn == btnStatistiques) switchView("statistiques");
        });

        return btn;
    }

    
    private void setActiveStyle(JButton btn) {
        btn.setOpaque(true);
        btn.setBackground(new Color(255, 87, 34, 10)); // léger fond orange
        btn.setForeground(PRIMARY_COLOR);

        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, PRIMARY_COLOR),
                BorderFactory.createEmptyBorder(12, 16, 12, 15)
        ));
    }

    private void setInactiveStyle(JButton btn) {
        btn.setOpaque(true);
        btn.setBackground(CARD_BG);
        btn.setForeground(TEXT_DARK);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 15));
    }



    
    private void resetSidebarButtons() {
        SidebarButton[] buttons = {
            btnDashboard, btnArticles, btnCategories,
            btnClients, btnCommandes, btnPaiements,
            btnStatistiques
        };

        for (SidebarButton btn : buttons) {
            btn.setActive(false);
        }
    }

    private void switchView(String view) {
        currentView = view;
        cardLayout.show(panelContent, view);
    }
    
    // ==================== PANEL DASHBOARD ====================
    
    private void createDashboardPanel() {

        panelDashboard = new JPanel(new BorderLayout());
        panelDashboard.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        panelDashboard.setOpaque(false);

        JPanel statsGrid = new JPanel(new GridLayout(1, 4, 25, 25));
        statsGrid.setOpaque(false);

        lblTotalClients = new JLabel("0");
        lblTotalArticles = new JLabel("0");
        lblTotalCommandes = new JLabel("0");
        lblChiffreAffaires = new JLabel("0 FCFA");

        statsGrid.add(createDynamicStatCard("Clients", lblTotalClients, PRIMARY_BLUE));
        statsGrid.add(createDynamicStatCard("Articles", lblTotalArticles, SUCCESS_GREEN));
        statsGrid.add(createDynamicStatCard("Commandes", lblTotalCommandes, WARNING_ORANGE));
        statsGrid.add(createDynamicStatCard("Chiffre d'affaires", lblChiffreAffaires, new Color(156, 39, 176)));

        JPanel chartsPanel = new JPanel(new GridLayout(1, 2, 25, 25));
        chartsPanel.setOpaque(false);

        chartsPanel.add(createModernChartCard("Ventes mensuelles"));
        chartsPanel.add(createModernChartCard("Répartition des ventes"));

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 25, 25));
        bottomPanel.setOpaque(false);
        bottomPanel.add(createTopProductsCard());
        bottomPanel.add(createRecentOrdersCard());
        //bottomPanel.add(createModernTableCard("Top Produits", createTopProductsCardContent()));
        //bottomPanel.add(createModernTableCard("Dernières commandes", createRecentOrdersCardContent()));

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);
        container.add(statsGrid);
        container.add(Box.createVerticalStrut(35));
        container.add(chartsPanel);
        container.add(Box.createVerticalStrut(35));
        container.add(bottomPanel);

        panelDashboard.add(container, BorderLayout.NORTH);

        panelContent.add(panelDashboard, "dashboard");
        chargerDashboardData();
    }
    private JPanel createDynamicStatCard(String title, JLabel valueLabel, Color color) {

        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        textPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(robotoFont.deriveFont(Font.PLAIN, 14f));
        titleLabel.setForeground(TEXT_LIGHT);

        valueLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 24f));
        valueLabel.setForeground(TEXT_DARK);

        textPanel.add(titleLabel);
        textPanel.add(valueLabel);

        card.add(textPanel, BorderLayout.CENTER);

        return card;
    }
    private void chargerDashboardData() {

        lblTotalClients.setText(String.valueOf(adminCtrl.getTotalClientsDashboard()));
        lblTotalCommandes.setText(String.valueOf(adminCtrl.getTotalCommandesDashboard()));
        lblChiffreAffaires.setText(
            String.format("%.0f FCFA", adminCtrl.getChiffreAffairesDashboard())
        );

        lblTotalArticles.setText(
            String.valueOf(adminCtrl.getAllArticles().size())
        );
    }

    private JPanel createStatCard(String title, String value, String icon, Color color) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        textPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(robotoFont.deriveFont(Font.PLAIN, 14f));
        titleLabel.setForeground(TEXT_LIGHT);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 24f));
        valueLabel.setForeground(TEXT_DARK);
        
        textPanel.add(titleLabel);
        textPanel.add(valueLabel);
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setForeground(color);
        iconLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        card.add(textPanel, BorderLayout.CENTER);
        card.add(iconLabel, BorderLayout.EAST);
        
        return card;
    }
    
    private JPanel createChartCard(String title) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setPreferredSize(new Dimension(400, 250));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(robotoFont.deriveFont(Font.BOLD, 16f));
        titleLabel.setForeground(TEXT_DARK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Simuler un graphique
                int[] values = {120, 150, 180, 220, 250, 280, 310, 290, 270, 300, 330, 350};
                int w = getWidth() - 40;
                int h = getHeight() - 40;
                
                g2.setColor(new Color(33, 150, 243, 50));
                g2.setStroke(new BasicStroke(2f));
                
                int x = 20;
                int prevY = 0;
                
                for (int i = 0; i < values.length; i++) {
                    int barHeight = (int) ((values[i] / 400.0) * h);
                    int y = getHeight() - 20 - barHeight;
                    
                    if (i > 0) {
                        g2.drawLine(x - 20, prevY, x, y);
                    }
                    
                    prevY = y;
                    x += 30;
                }
            }
        };
        chartPanel.setOpaque(false);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(chartPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createTopProductsCard() {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel("Articles les plus vendus");
        titleLabel.setFont(robotoFont.deriveFont(Font.BOLD, 16f));

        String[] columns = {"Article", "Ventes", "Revenu"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        List<Article> topArticles = adminCtrl.getTopArticlesDashboard();

        for (Article a : topArticles) {
            model.addRow(new Object[]{
                a.getNom(),
                "—",  // si tu veux quantité exacte on peut l'ajouter dans DAO
                String.format("%,.0f FCFA", a.getPrix())
            });
        }

        JTable table = new JTable(model);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(scrollPane, BorderLayout.CENTER);

        return card;
    } 

    private JPanel createRecentOrdersCard() {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);

        JLabel titleLabel = new JLabel("Dernières commandes");
        titleLabel.setFont(robotoFont.deriveFont(Font.BOLD, 16f));

        String[] columns = {"Client", "Montant", "Statut"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        List<Commande> commandes = adminCtrl.getAllCommandes();

        int count = 0;

        for (Commande c : commandes) {

            if (count == 5) break;

            Client client = adminCtrl.getClientByIdDashboard(c.getIdClient());
            double montant = adminCtrl.getMontantCommande(c.getIdCommande());

            model.addRow(new Object[]{
                client != null ? client.getPrenom() + " " + client.getNom() : "Inconnu",
                String.format("%.0f FCFA", montant),
                c.getStatut()
            });

            count++;
        }

        JTable table = new JTable(model);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(scrollPane, BorderLayout.CENTER);

        return card;
    }


    // ==================== PANEL ARTICLES ====================
    
    private void createArticlesPanel() {

        panelArticles = new JPanel(new BorderLayout(0, 20));
        panelArticles.setOpaque(false);

        // ================= TOOLBAR MODERNE =================

        JPanel toolbar = new JPanel(new BorderLayout(15, 0));
        toolbar.setOpaque(false);

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actionsPanel.setOpaque(false);

        JButton btnAjouter = new JButton("➕ Ajouter");
        JButton btnModifier = new JButton("✏ Modifier");
        JButton btnSupprimer = new JButton("🗑 Supprimer");

        btnAjouter.addActionListener(e -> showArticleDialog(null));
        btnModifier.addActionListener(e -> modifierArticleSelectionne());
        btnSupprimer.addActionListener(e -> supprimerArticleSelectionne());

        JTextField searchField = new JTextField();
        searchField.putClientProperty("JTextField.placeholderText", "Rechercher un article...");
        searchField.setPreferredSize(new Dimension(250, 38));

        actionsPanel.add(btnAjouter);
        actionsPanel.add(btnModifier);
        actionsPanel.add(btnSupprimer);

        toolbar.add(actionsPanel, BorderLayout.WEST);
        toolbar.add(searchField, BorderLayout.EAST);

        // ================= LIST VIEW =================

        DefaultListModel<Article> listModel = new DefaultListModel<>();
        articleList = new JList<>(listModel);
        articleList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = articleList.locationToIndex(e.getPoint());
                if (index < 0) return;

                Article article = articleList.getModel().getElementAt(index);
                Rectangle cellBounds = articleList.getCellBounds(index, index);
                if (cellBounds == null) return;

                // Calculer la position relative dans la cellule
                Point clickPoint = e.getPoint();
                int relativeX = clickPoint.x - cellBounds.x;
                int relativeY = clickPoint.y - cellBounds.y;

                // Déterminer si on a cliqué sur les boutons
                // On suppose que le panneau droit (contenant les boutons) occupe environ 150px de large
                int rightPanelWidth = 150; // à ajuster selon votre layout
                if (relativeX > cellBounds.width - rightPanelWidth) {
                    // Clic dans la zone droite
                    // On peut distinguer modifier (en haut) et supprimer (en bas)
                    // La hauteur des boutons est d'environ 30px chacun, avec espacements
                    int buttonHeight = 30;
                    int spacing = 5;
                    int yInRight = relativeY;
                    int firstButtonY = cellBounds.height - 80; // approximatif, à calculer précisément
                    if (yInRight > firstButtonY && yInRight < firstButtonY + buttonHeight) {
                        // Modifier
                        System.err.println("Modifier");
                        showArticleDialog(article);
                    } else if (yInRight > firstButtonY + buttonHeight + spacing && yInRight < firstButtonY + 2*buttonHeight + spacing) {
                        // Supprimer
                        //supprimerArticle(article);
                    }
                }
            }
        });

        articleList.setCellRenderer(new ArticleCardRenderer());
        articleList.setFixedCellHeight(110);
        articleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        articleList.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(articleList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setOpaque(false);

        chargerArticles(listModel);

        panelArticles.add(toolbar, BorderLayout.NORTH);
        panelArticles.add(scrollPane, BorderLayout.CENTER);

        panelContent.add(panelArticles, "articles");
    }
    private void modifierArticleSelectionne() {
        Article selected = articleList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un article à modifier.", 
                                          "Aucune sélection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        showArticleDialog(selected);
    }

    private void supprimerArticleSelectionne() {
        Article selected = articleList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un article à supprimer.", 
                                          "Aucune sélection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Voulez-vous vraiment supprimer l'article \"" + selected.getNom() + "\" ?",
            "Confirmation de suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = adminCtrl.supprimerArticle(selected.getId_article());
            if (success) {
                JOptionPane.showMessageDialog(this, "Article supprimé avec succès.", 
                                              "Succès", JOptionPane.INFORMATION_MESSAGE);
                // Recharger la liste
                chargerArticles((DefaultListModel<Article>) articleList.getModel());
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression de l'article.", 
                                              "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void chargerArticles(DefaultListModel<Article> model) {
        model.clear();
        List<Article> articles = adminCtrl.getAllArticles();
        for (Article article : articles) {
            model.addElement(article);
        }
    }
    class ArticleCardRenderer extends JPanel implements ListCellRenderer<Article> {
        private JLabel lblNom = new JLabel();
        private JLabel lblDesc = new JLabel();
        private JLabel lblPrix = new JLabel();
        private JLabel lblStock = new JLabel();
        private JLabel lblImage = new JLabel();
        private JLabel lblCatg = new JLabel();
        private JLabel btnModifier = new JLabel("✏️ Modifier");
        private JLabel btnSupprimer = new JLabel("🗑️ Supprimer");

        public ArticleCardRenderer() {
            setLayout(new BorderLayout(15, 5));
            setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

            // Zone image à gauche
            lblImage.setPreferredSize(new Dimension(60, 60));
            lblImage.setHorizontalAlignment(SwingConstants.CENTER);
            lblImage.setVerticalAlignment(SwingConstants.CENTER);
            lblImage.setOpaque(true);
            lblImage.setBackground(new Color(240, 240, 240));
            lblImage.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));

            // Panneau central (nom + description)
            lblNom.setFont(robotoFont.deriveFont(Font.BOLD, 15f));
            lblDesc.setFont(robotoFont.deriveFont(Font.PLAIN, 13f));
            lblDesc.setForeground(new Color(120,120,120));

            JPanel center = new JPanel();
            center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
            center.setOpaque(false);
            center.add(lblNom);
            center.add(Box.createVerticalStrut(5));
            center.add(lblDesc);

            // Panneau droite (prix + stock + boutons)
            lblPrix.setFont(robotoFont.deriveFont(Font.BOLD, 14f));
            lblPrix.setForeground(new Color(34, 197, 94));
            lblStock.setFont(robotoFont.deriveFont(Font.PLAIN, 13f));

            // Style des boutons
            btnModifier.setFont(robotoFont.deriveFont(Font.BOLD, 12f));
            btnModifier.setForeground(new Color(255, 152, 0));
            btnModifier.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 152, 0)),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            btnModifier.setOpaque(true);
            btnModifier.setBackground(Color.WHITE);
            btnModifier.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            lblCatg.setFont(robotoFont.deriveFont(Font.BOLD, 12f));
            lblCatg.setForeground(new Color(255, 152, 0));
            lblCatg.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 152, 0)),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            lblCatg.setOpaque(true);
            lblCatg.setBackground(Color.WHITE);
            lblCatg.setCursor(new Cursor(Cursor.HAND_CURSOR));


            JPanel right = new JPanel();
            right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
            right.setOpaque(false);
            right.add(lblPrix);
            right.add(Box.createVerticalStrut(5));
            right.add(lblStock);
            right.add(Box.createVerticalStrut(10));
            right.add(lblCatg);

            add(lblImage, BorderLayout.WEST);
            add(center, BorderLayout.CENTER);
            add(right, BorderLayout.EAST);
        }

        @Override
        public Component getListCellRendererComponent(
                JList<? extends Article> list,
                Article article,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {

            lblNom.setText(article.getNom());
            lblDesc.setText(article.getDescription() != null ? article.getDescription() : "-");
            lblPrix.setText(String.format("%.0f FCFA", article.getPrix()));
            lblStock.setText("Stock : " + article.getStock());
            lblCatg.setText("Catégorie : "+adminCtrl.getCategorieArticle(article.getId_article()));

            // Charger l'image si disponible
            String photoPath = article.getUrlPhoto();
            if (photoPath != null && !photoPath.isEmpty()) {
                try {
                    String fullPath = System.getProperty("user.dir") + "/" + photoPath;
                    ImageIcon icon = new ImageIcon(fullPath);
                    Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    lblImage.setIcon(new ImageIcon(img));
                    lblImage.setText(null);
                } catch (Exception e) {
                    lblImage.setIcon(null);
                    lblImage.setText("📦");
                    lblImage.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
                }
            } else {
                lblImage.setIcon(null);
                lblImage.setText("📦");
                lblImage.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
            }

            // Couleur de fond selon sélection
            if (isSelected) {
                setBackground(new Color(33,150,243,30));
            } else {
                setBackground(Color.WHITE);
            }

            setOpaque(true);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0,0,1,0,new Color(230,230,230)),
                    BorderFactory.createEmptyBorder(15,20,15,20)
            ));

            return this;
        }
    }
    
    // ==================== PANEL CATÉGORIES ====================
    private JTable createModernTable(DefaultTableModel model) {

        JTable table = new JTable(model);

        table.setFont(robotoFont.deriveFont(Font.PLAIN, 14f));
        table.setRowHeight(42);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);

        table.setSelectionBackground(new Color(33, 150, 243, 40));
        table.setSelectionForeground(Color.BLACK);

        JTableHeader header = table.getTableHeader();
        header.setFont(robotoFont.deriveFont(Font.BOLD, 13f));
        header.setReorderingAllowed(false);

        return table;
    }
    private JScrollPane createModernScrollPane(JTable table) {

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        return scroll;
    }
    
    private JPanel createToolbar(JComponent... components) {

        JPanel toolbar = new JPanel();
        toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.X_AXIS));
        toolbar.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        toolbar.setOpaque(false);

        for (JComponent comp : components) {
            toolbar.add(comp);
            toolbar.add(Box.createHorizontalStrut(12));
        }

        toolbar.add(Box.createHorizontalGlue());

        return toolbar;
    }
    private JPanel createModernStatCard(String title, String value, String icon, Color color) {

        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        card = wrapWithShadow(card);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(robotoFont.deriveFont(Font.PLAIN, 13f));
        titleLabel.setForeground(new Color(120, 120, 120));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(poppinsFont.deriveFont(Font.BOLD, 26f));
        valueLabel.setForeground(TEXT_DARK);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(6));
        textPanel.add(valueLabel);

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));
        iconLabel.setForeground(color);

        card.add(textPanel, BorderLayout.CENTER);
        card.add(iconLabel, BorderLayout.EAST);

        return card;
    }
    private JPanel wrapWithShadow(JPanel content) {

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230,230,230),1,true),
                BorderFactory.createEmptyBorder(15,15,15,15)
        ));

        wrapper.add(content);
        return wrapper;
    }
    private JPanel createModernChartCard(String title) {

        JPanel card = wrapWithShadow(new JPanel(new BorderLayout(0,15)));
        card.setPreferredSize(new Dimension(400, 280));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(robotoFont.deriveFont(Font.BOLD, 15f));
        titleLabel.setForeground(TEXT_DARK);

        JPanel chartPanel = new SmoothLineChart();

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(chartPanel, BorderLayout.CENTER);

        return card;
    }
    class SmoothLineChart extends JPanel {

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int[] values = {120,150,180,220,250,280,310,290,270,300,330,350};

            int w = getWidth() - 60;
            int h = getHeight() - 60;

            g2.setStroke(new BasicStroke(3f));
            g2.setColor(new Color(33,150,243));

            int xStep = w / (values.length - 1);
            int prevX = 30;
            int prevY = getHeight() - 30 - (values[0] * h / 400);

            for (int i = 1; i < values.length; i++) {
                int x = 30 + i * xStep;
                int y = getHeight() - 30 - (values[i] * h / 400);
                g2.drawLine(prevX, prevY, x, y);
                prevX = x;
                prevY = y;
            }
        }
    }
    private JPanel createModernTableCard(String title, JScrollPane tableContent) {

        JPanel card = wrapWithShadow(new JPanel(new BorderLayout(0,15)));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(robotoFont.deriveFont(Font.BOLD, 15f));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(tableContent, BorderLayout.CENTER);

        return card;
    }
    private JPanel createTopProductsCardContent() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        List<Article> topProduits = adminCtrl.getAllArticles();

        if (topProduits == null || topProduits.isEmpty()) {

            JLabel empty = new JLabel("Aucune donnée disponible");
            empty.setFont(robotoFont.deriveFont(Font.PLAIN, 14f));
            empty.setForeground(new Color(150,150,150));
            panel.add(empty);

            return panel;
        }

    int max = topProduits.get(0).getStock();

    for (Article p : topProduits) {

        panel.add(createProductBar(p.getNom(), p.getStock(), max));
        panel.add(Box.createVerticalStrut(18));
    }

    return panel;
}
    private JPanel createProductBar(String nom, int valeur, int max) {

    JPanel container = new JPanel(new BorderLayout(10, 8));
    container.setOpaque(false);

    JLabel lblNom = new JLabel(nom);
    lblNom.setFont(robotoFont.deriveFont(Font.BOLD, 14f));

    JLabel lblValeur = new JLabel(valeur + " ventes");
    lblValeur.setFont(robotoFont.deriveFont(Font.PLAIN, 13f));
    lblValeur.setForeground(new Color(120,120,120));

    JPanel header = new JPanel(new BorderLayout());
    header.setOpaque(false);
    header.add(lblNom, BorderLayout.WEST);
    header.add(lblValeur, BorderLayout.EAST);

    // ===== Barre custom moderne =====

    JProgressBar bar = new JProgressBar(0, max);
    bar.setValue(valeur);
    bar.setStringPainted(false);
    bar.setBorderPainted(false);
    bar.setPreferredSize(new Dimension(100, 8));
    bar.setForeground(new Color(33,150,243)); // bleu SaaS
    bar.setBackground(new Color(235,235,235));

    container.add(header, BorderLayout.NORTH);
    container.add(bar, BorderLayout.CENTER);

    return container;
}
 
    private void createCategoriesPanel() {
        panelCategories = new JPanel(new BorderLayout(0, 15));
        panelCategories.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelCategories.setOpaque(false);

        // Boutons de la barre d'outils
        JButton btnAjouter = createActionButton("+ Nouvelle catégorie", SUCCESS_GREEN);
        btnAjouter.addActionListener(e -> showCategorieDialog(null));

        JButton btnModifier = createActionButton("Modifier", WARNING_ORANGE);
        JButton btnSupprimer = createActionButton("-Supprimer", DANGER_RED);

        JTextField searchField = new JTextField(22);
        searchField.putClientProperty("JTextField.placeholderText", "🔍 Rechercher une catégorie...");

        // Assemblage de la toolbar (gauche : boutons, droite : champ de recherche)
        JPanel leftToolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftToolbar.setOpaque(false);
        leftToolbar.add(btnAjouter);
        leftToolbar.add(btnModifier);
        leftToolbar.add(btnSupprimer);

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);
        toolbar.add(leftToolbar, BorderLayout.WEST);
        toolbar.add(searchField, BorderLayout.EAST);

        // Table des catégories (sans colonne Actions)
        String[] columns = {"ID", "Nom", "Description", "Nombre Articles"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // plus de colonne éditable
            }
        };

        JTable table = createModernTable(model);
        chargerCategories(model);
        categoriesTable = table;
        categoriesTableModel = model;

        // Masquer la colonne ID (index 0)
        table.removeColumn(table.getColumnModel().getColumn(0));

        JScrollPane scrollPane = createModernScrollPane(table);

        // Actions des boutons Modifier et Supprimer
        btnModifier.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner une catégorie.",
                    "Aucune sélection",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Récupérer l'ID depuis la ligne sélectionnée (attention : la colonne ID est masquée mais existe dans le modèle)
            int id = (int) table.getValueAt(selectedRow, 0);
            Categorie categorie = adminCtrl.getCategorieById(id);
            if (categorie != null) {
                showCategorieDialog(categorie);
            }
        });

        btnSupprimer.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner une catégorie.",
                    "Aucune sélection",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = (int) table.getValueAt(selectedRow, 0);
            String nom = (String) table.getValueAt(selectedRow, 1);
            int confirm = JOptionPane.showConfirmDialog(this,
                "Voulez-vous vraiment supprimer la catégorie \"" + nom + "\" ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = adminCtrl.supprimerCategorie(id);
                if (success) {
                    JOptionPane.showMessageDialog(this,
                        "Catégorie supprimée.",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
                    // Recharger le tableau
                    chargerCategories(model);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Impossible de supprimer : la catégorie contient des articles.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnModifier.addActionListener(e -> modifierCategorieSelectionnee());
        btnSupprimer.addActionListener(e -> supprimerCategorieSelectionnee());
        // Recherche en temps réel (optionnel)
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrer(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrer(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrer(); }
            private void filtrer() {
                String texte = searchField.getText().toLowerCase();
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
                table.setRowSorter(sorter);
                if (texte.trim().isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texte));
                }
            }
        });

        panelCategories.add(toolbar, BorderLayout.NORTH);
        panelCategories.add(scrollPane, BorderLayout.CENTER);

        panelContent.add(panelCategories, "categories");
    }

    private void chargerCategories(DefaultTableModel model) {
        model.setRowCount(0);
        List<Categorie> categories = adminCtrl.getAllCategories();
        for (Categorie categorie : categories) {
            int nbArticles = adminCtrl.getNombreArticlesParCategorie(categorie.getIdCategorie());
            model.addRow(new Object[]{
                categorie.getIdCategorie(),
                categorie.getNomCategorie(),
                categorie.getDescription() != null ? categorie.getDescription() : "-",
                nbArticles
            });
        }
    }

    /**
    * Modifie la catégorie sélectionnée dans le tableau.
    */
   private void modifierCategorieSelectionnee() {
    int selectedRow = categoriesTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this,
            "Veuillez sélectionner une catégorie.",
            "Aucune sélection",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Convertir l'index de vue en index de modèle (important si tri/filtre)
    int modelRow = categoriesTable.convertRowIndexToModel(selectedRow);
    
    // Récupérer l'ID depuis le modèle (colonne 0)
    Object idObj = categoriesTable.getModel().getValueAt(modelRow, 0);
    int id;
    if (idObj instanceof Integer) {
        id = (Integer) idObj;
    } else if (idObj instanceof String) {
        id = Integer.parseInt((String) idObj);
    } else {
        JOptionPane.showMessageDialog(this,
            "Format d'ID invalide.",
            "Erreur",
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    Categorie categorie = adminCtrl.getCategorieById(id);
    
    if (categorie != null) {
        showCategorieDialog(categorie);
    } else {
        JOptionPane.showMessageDialog(this,
            "Catégorie introuvable.",
            "Erreur",
            JOptionPane.ERROR_MESSAGE);
    }
}

private void supprimerCategorieSelectionnee() {
    int selectedRow = categoriesTable.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this,
            "Veuillez sélectionner une catégorie.",
            "Aucune sélection",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    int modelRow = categoriesTable.convertRowIndexToModel(selectedRow);
    
    Object idObj = categoriesTable.getModel().getValueAt(modelRow, 0);
    int id;
    if (idObj instanceof Integer) {
        id = (Integer) idObj;
    } else if (idObj instanceof String) {
        id = Integer.parseInt((String) idObj);
    } else {
        JOptionPane.showMessageDialog(this,
            "Format d'ID invalide.",
            "Erreur",
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    String nom = (String) categoriesTable.getModel().getValueAt(modelRow, 1);
    
    int confirm = JOptionPane.showConfirmDialog(this,
        "Voulez-vous vraiment supprimer la catégorie \"" + nom + "\" ?",
        "Confirmation",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE);
    
    if (confirm == JOptionPane.YES_OPTION) {
        boolean success = adminCtrl.supprimerCategorie(id);
        if (success) {
            JOptionPane.showMessageDialog(this,
                "Catégorie supprimée avec succès.",
                "Succès",
                JOptionPane.INFORMATION_MESSAGE);
            // Recharger le tableau
            chargerCategories(categoriesTableModel);
        } else {
            JOptionPane.showMessageDialog(this,
                "Impossible de supprimer la catégorie.\n" +
                "Vérifiez qu'elle ne contient pas d'articles.",
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}

    // ==================== PANEL CLIENTS ====================

    private void createClientsPanel() {

        panelClients = new JPanel(new BorderLayout(0, 20));
        panelClients.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelClients.setOpaque(false);

        // ===== Toolbar moderne =====

        JTextField searchField = new JTextField(25);
        searchField.putClientProperty("JTextField.placeholderText", "🔍 Rechercher un client...");
        searchField.setPreferredSize(new Dimension(260, 38));

        JPanel toolbar = new JPanel();
        toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.X_AXIS));
        toolbar.setOpaque(false);
        toolbar.add(searchField);
        toolbar.add(Box.createHorizontalGlue());

        // ===== LIST VIEW =====

        DefaultListModel<Client> model = new DefaultListModel<>();
        clientList = new JList<>(model);

        clientList.setCellRenderer(new ClientCardRenderer());
        clientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        clientList.setFixedCellHeight(95);
        clientList.setOpaque(false);

        chargerClientsList(model);

        JScrollPane scrollPane = new JScrollPane(clientList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setOpaque(false);

        panelClients.add(toolbar, BorderLayout.NORTH);
        panelClients.add(scrollPane, BorderLayout.CENTER);

        panelContent.add(panelClients, "clients");
    }
    private void chargerClientsList(DefaultListModel<Client> model) {

        model.clear();
        List<Client> clients = adminCtrl.getAllClients();

        for (Client client : clients) {
            model.addElement(client);
        }
    }
    
    private void chargerClients(DefaultTableModel model) {
        model.setRowCount(0);
        List<Client> clients = adminCtrl.getAllClients();
        for (Client client : clients) {
            model.addRow(new Object[]{
                client.getIdClient(),
                client.getNom(),
                client.getPrenom(),
                client.getEmail(),
                client.getTelephone(),
                client.getAdresse(),
            });
        }
    }
    
    // ==================== PANEL COMMANDES ====================

    private void createCommandesPanel() {

        panelCommandes = new JPanel(new BorderLayout(0, 15));
        panelCommandes.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelCommandes.setOpaque(false);

        

        JTextField searchField = new JTextField(20);
        searchField.putClientProperty("JTextField.placeholderText", "🔍 Rechercher une commande...");

        

        String[] columns = {"ID", "Client", "Date", "Montant", "Statut", "Paiement"};

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) {
                return false; // plus aucune colonne modifiable
            }
        };

        JTable table = createModernTable(model);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        searchField.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                filter();
            }

            public void removeUpdate(DocumentEvent e) {
                filter();
            }

            public void changedUpdate(DocumentEvent e) {
                filter();
            }

            private void filter() {
                String text = searchField.getText();
                if (text.trim().isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
        JComboBox<String> statutFilter = new JComboBox<>(
            new String[]{"Tous", "En attente", "Payée", "Livrée", "Annulée"}
        );
        JPanel toolbar = createToolbar(statutFilter, searchField);
        statutFilter.addActionListener(e -> {

            String selected = statutFilter.getSelectedItem().toString();

            if (selected.equals("Tous")) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter(selected, 4));
                // 4 = colonne Statut
            }
        });

        chargerCommandes(model);

        table.getColumnModel().getColumn(4).setCellRenderer(new StatutRenderer());
        table.getColumnModel().getColumn(4).setCellRenderer(new StatutRenderer());

        table.removeColumn(table.getColumnModel().getColumn(0));

        JScrollPane scrollPane = createModernScrollPane(table);

        panelCommandes.add(toolbar, BorderLayout.NORTH);
        panelCommandes.add(scrollPane, BorderLayout.CENTER);

        panelContent.add(panelCommandes, "commandes");
    }
    
    private void chargerCommandes(DefaultTableModel model) {
        model.setRowCount(0);
        List<Commande> commandes = adminCtrl.getAllCommandes();

        for (Commande commande : commandes) {
            Client client = adminCtrl.getClientById(commande.getIdClient());
            double montant = adminCtrl.getMontantCommande(commande.getIdCommande());

            // Traduire le statut en français
            String statut = commande.getStatut();
            String statutAffichage = statut;

            if (statut != null) {
                switch (statut.toUpperCase()) {
                    case "EN_COURS": statutAffichage = " En cours"; break;
                    case "PAYEE": statutAffichage = " Payée"; break;
                    case "LIVREE": statutAffichage = " Livrée"; break;
                    case "ANNULEE": statutAffichage = " Annulée"; break;
                }
            }

            // Déduire le statut de paiement
            String statutPaiement = " En attente";
            if (statut != null && (statut.equals("PAYEE") || statut.equals("LIVREE"))) {
                statutPaiement = " Payé";
            }

            String nomComplet = "Inconnu";

            if (client != null) {
                nomComplet = client.getPrenom() + " " + client.getNom().toUpperCase();
            }

            model.addRow(new Object[]{
                commande.getIdCommande(),
                nomComplet,
                commande.getDateCommande(),
                String.format("%.0f FCFA", montant),
                statutAffichage,
                statutPaiement
            });
        }
    }
    /**
     * Déduit le statut de paiement à partir du statut de la commande
     */
    private String getStatutPaiementParDefaut(Commande commande) {
        if (commande.getStatut() == null) return "Non défini";

        switch (commande.getStatut().toUpperCase()) {
            case Commande.STATUT_PAYEE:
                return "✓ Payé";
            case Commande.STATUT_EN_COURS:
                return "⏳ En attente";
            case Commande.STATUT_LIVREE:
                return "✓ Payé";
            case Commande.STATUT_ANNULEE:
                return "✗ Remboursé";
            default:
                return "Non défini";
        }
    }

        // ==================== PANEL PAIEMENTS ====================

 
    private void createPaiementsPanel() {

        panelPaiements = new JPanel(new BorderLayout(0, 15));
        panelPaiements.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPaiements.setOpaque(false);

        JComboBox<String> modeFilter = new JComboBox<>(
            new String[]{"Tous", "Carte", "PayPal", "Virement", "Espèces"}
        );

        JPanel toolbar = createToolbar(modeFilter);

        String[] columns = {"ID", "Commande", "Client", "Date", "Montant", "Mode", "Statut"};

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // plus de colonne éditable
            }
        };
        JTable table = createModernTable(model);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        modeFilter.addActionListener(e -> {

            String selected = modeFilter.getSelectedItem().toString();

            if (selected.equals("Tous")) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter(selected, 4));
                // 4 = colonne Statut
            }
        });

        chargerPaiements(model);

        table.getColumnModel().getColumn(4).setCellRenderer(new StatutRenderer());

        table.removeColumn(table.getColumnModel().getColumn(0));

        JScrollPane scrollPane = createModernScrollPane(table);

        panelPaiements.add(toolbar, BorderLayout.NORTH);
        panelPaiements.add(scrollPane, BorderLayout.CENTER);

        panelContent.add(panelPaiements, "paiements");
    }
    private void chargerPaiements(DefaultTableModel model) {
        model.setRowCount(0);
        List<Paiement> paiements = adminCtrl.getAllPaiements();
        for (Paiement paiement : paiements) {
            Commande commande = adminCtrl.getCommandeById(paiement.getIdCommande());
            Client client = commande != null ? adminCtrl.getClientById(commande.getIdClient()) : null;
            
            
            String nomComplet = "Inconnu";

            if (client != null) {
                nomComplet = client.getPrenom() + " " + client.getNom().toUpperCase();
            }

            model.addRow(new Object[]{
                paiement.getIdPaiement(),
                "CMD-" + paiement.getIdCommande(),
                nomComplet,
                paiement.getDatePaiement(),
                String.format("%.0f FCFA", paiement.getMontant()),
                paiement.getModePaiement(),
                "Validé",
                ""
            });
        }
    }
    
    // ==================== PANEL STATISTIQUES ====================
    
    private void createStatistiquesPanel() {

        panelStatistiques = new JPanel(new BorderLayout(0, 30));
        panelStatistiques.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        panelStatistiques.setOpaque(false);

        Map<String, Object> stats = adminCtrl.getStatistiquesGlobales();

        // ===== SECTION KPI =====

        JPanel statsGrid = new JPanel(new GridLayout(2, 4, 20, 20));
        statsGrid.setOpaque(false);

        statsGrid.add(createModernStatCard("Clients", String.valueOf(stats.get("nombreClients")), "👥", PRIMARY_BLUE));
        statsGrid.add(createModernStatCard("Articles", String.valueOf(stats.get("nombreArticles")), "📦", SUCCESS_GREEN));
        statsGrid.add(createModernStatCard("Valeur stock", stats.get("valeurStock") + " FCFA", "💰", WARNING_ORANGE));
        statsGrid.add(createModernStatCard("Chiffre d'affaires", stats.get("chiffreAffaires") + " FCFA", "📈", new Color(156, 39, 176)));
        statsGrid.add(createModernStatCard("Commandes", String.valueOf(stats.get("nombreCommandes")), "🛒", PRIMARY_BLUE));
        statsGrid.add(createModernStatCard("Paiements", stats.get("totalPaiements") + " FCFA", "💳", SUCCESS_GREEN));
        statsGrid.add(createModernStatCard("Moyenne panier", "75,000 FCFA", "🛍️", WARNING_ORANGE));
        statsGrid.add(createModernStatCard("Taux conversion", "68%", "📊", new Color(233, 30, 99)));

        // ===== SECTION CHARTS =====

        JPanel chartsPanel = new JPanel(new GridLayout(2, 2, 25, 25));
        chartsPanel.setOpaque(false);

        chartsPanel.add(createModernChartCard("Ventes mensuelles"));
        chartsPanel.add(createModernChartCard("Répartition par catégorie"));
        chartsPanel.add(createModernChartCard("Top 5 clients"));
        chartsPanel.add(createModernChartCard("Paiements par mode"));

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);
        container.add(statsGrid);
        container.add(Box.createVerticalStrut(35));
        container.add(chartsPanel);

        panelStatistiques.add(container, BorderLayout.NORTH);

        panelContent.add(panelStatistiques, "statistiques");
        panelContent.setAutoscrolls(true);
    }
    
    // ==================== CLASSES UTILITAIRES ====================
    
    private JButton createActionButton(String iconPath, String text, Color bgColor) {
        java.net.URL imgUrl = getClass().getResource(iconPath);
        ImageIcon icon = null;
        if (imgUrl != null) {
            Image img = new ImageIcon(imgUrl).getImage()
                          .getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
        }

        JButton btn = new JButton(text, icon) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground().darker());
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
                g2.dispose();
            }
            @Override
            public boolean isContentAreaFilled() { return false; }
        };

        btn.setFont(robotoFont.deriveFont(Font.BOLD, 12f));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton createActionButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(robotoFont.deriveFont(Font.BOLD, 12f));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    
    // Renderer pour les actions
    class ActionsRenderer extends JPanel implements TableCellRenderer {
        public ActionsRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            setOpaque(false);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }
    
    // Editor pour les actions
    // Remplacer la classe ActionsEditor par celle-ci
    class ActionsEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;
        private JTable table;
        private int row;

        public ActionsEditor(JTable table) {
            this.table = table;
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            panel.setOpaque(false);
        }

        private JButton createActionButton(String action, Color bgColor) {
            JButton btn = new JButton(action);
            btn.setFont(robotoFont.deriveFont(Font.PLAIN, 11f));
            btn.setMargin(new Insets(2, 5, 2, 5));
            btn.setBackground(bgColor);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            return btn;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            this.row = row;
            panel.removeAll();

            String actions = (String) table.getValueAt(row, table.getColumnCount() - 1);
            String[] actionList = actions.split("\\|");

            for (String action : actionList) {
                Color bgColor = TEXT_LIGHT;
                switch (action) {
                    case "✏️": bgColor = WARNING_ORANGE; break;
                    case "🗑️": bgColor = DANGER_RED; break;
                    case "👁️": bgColor = PRIMARY_BLUE; break;
                    case "📄": bgColor = SUCCESS_GREEN; break;
                }

                JButton btn = createActionButton(action, bgColor);
                btn.addActionListener(e -> handleAction(action, row));
                panel.add(btn);
            }

            return panel;
        }

        private void handleAction(String action, int row) {
            int id = 0;
            try {
                id = (int) table.getValueAt(row, 0);
            } catch (Exception e) {
                fireEditingStopped();
                return;
            }

            switch (action) {
                case "✏️":
                    if (currentView.equals("articles")) {
                        Article article = adminCtrl.getArticleById(id);
                        if (article != null) showArticleDialog(article);
                    } else if (currentView.equals("categories")) {
                        Categorie categorie = adminCtrl.getCategorieById(id);
                        if (categorie != null) showCategorieDialog(categorie);
                    }
                    break;

                case "🗑️":
                    int confirm = JOptionPane.showConfirmDialog(panel, 
                        "Voulez-vous vraiment supprimer cet élément ?", 
                        "Confirmation", 
                        JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean success = false;
                        if (currentView.equals("articles")) {
                            success = adminCtrl.supprimerArticle(id);
                            if (success) refreshView("articles");
                        } else if (currentView.equals("categories")) {
                            success = adminCtrl.supprimerCategorie(id);
                            if (success) refreshView("categories");
                        }

                        if (success) {
                            JOptionPane.showMessageDialog(panel, "Suppression réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    break;

                case "👁️":
                    showDetailsDialog(id);
                    break;
            }

            fireEditingStopped();
        }

        @Override
        public Object getCellEditorValue() {
            return table.getValueAt(row, table.getColumnCount() - 1);
        }
    }


    // Renderer pour le statut
    
    class StatutRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            String statut = (String) value;
            setHorizontalAlignment(SwingConstants.CENTER);

            if (statut != null) {
                if (statut.contains("Livrée") || statut.contains("Payée")) {
                    setBackground(new Color(76, 175, 80));  // Vert
                    setForeground(Color.WHITE);
                } else if (statut.contains("En cours")) {
                    setBackground(new Color(33, 150, 243)); // Bleu
                    setForeground(Color.WHITE);
                } else if (statut.contains("Annulée")) {
                    setBackground(new Color(244, 67, 54));  // Rouge
                    setForeground(Color.WHITE);
                } else {
                    setBackground(new Color(255, 152, 0));  // Orange
                    setForeground(Color.WHITE);
                }
            }

            return this;
        }
    }
    
    class SidebarButton extends JButton {

        private boolean active = false;
        private float hoverAlpha = 0f;

        public SidebarButton(String text, Icon icon) {
            super(text, icon);

            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setHorizontalAlignment(SwingConstants.LEFT);
            setIconTextGap(12);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setFont(robotoFont.deriveFont(Font.PLAIN, 14f));

            setPreferredSize(new Dimension(240, 44));

        }
        

        public void setActive(boolean value) {
            active = value;
            repaint();
        }

        private void animateHover(float target) {
            Timer timer = new Timer(5, null);
            timer.addActionListener(e -> {
                if (Math.abs(hoverAlpha - target) < 0.01f) {
                    hoverAlpha = target;
                    timer.stop();
                } else {
                    hoverAlpha += (target - hoverAlpha) * 0.2f;
                }
                repaint();
            });
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int arc = 16;

            // Hover effect
            if (hoverAlpha > 0f) {
                g2.setComposite(AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, hoverAlpha));
                g2.setColor(new Color(255, 87, 34));
                g2.fillRoundRect(4, 4, getWidth() - 8, getHeight() - 8, arc, arc);
            }

            // Active indicator
            if (active) {
                g2.setColor(new Color(255, 87, 34));
                g2.fillRoundRect(4, 4, getWidth() - 8, getHeight() - 8, arc, arc);
                setForeground(Color.WHITE);
            } else {
                setForeground(TEXT_DARK);
            }

            g2.dispose();
            super.paintComponent(g);
        }
    }
    class ClientCardRenderer extends JPanel implements ListCellRenderer<Client> {

        private JLabel lblNom = new JLabel();
        private JLabel lblEmail = new JLabel();
        private JLabel lblTel = new JLabel();
        private JLabel avatar = new JLabel();

        public ClientCardRenderer() {

            setLayout(new BorderLayout(15, 10));
            setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

            // Avatar rond simulé
            avatar.setPreferredSize(new Dimension(50, 50));
            avatar.setHorizontalAlignment(SwingConstants.CENTER);
            avatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));

            lblNom.setFont(robotoFont.deriveFont(Font.BOLD, 15f));
            lblEmail.setFont(robotoFont.deriveFont(Font.PLAIN, 13f));
            lblTel.setFont(robotoFont.deriveFont(Font.PLAIN, 13f));

            lblEmail.setForeground(new Color(120, 120, 120));
            lblTel.setForeground(new Color(120, 120, 120));

            JPanel textPanel = new JPanel();
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
            textPanel.setOpaque(false);

            textPanel.add(lblNom);
            textPanel.add(Box.createVerticalStrut(4));
            textPanel.add(lblEmail);
            textPanel.add(lblTel);

            add(avatar, BorderLayout.WEST);
            add(textPanel, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(
                JList<? extends Client> list,
                Client client,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {

            lblNom.setText(client.getPrenom() + " " + client.getNom());
            lblEmail.setText("✉ " + client.getEmail());
            lblTel.setText("📞 " + client.getTelephone());

            // Avatar avec initiales
            String initials = "";
            if (client.getPrenom() != null && !client.getPrenom().isEmpty())
                initials += client.getPrenom().charAt(0);
            if (client.getNom() != null && !client.getNom().isEmpty())
                initials += client.getNom().charAt(0);

            avatar.setText(initials.toUpperCase());

            if (isSelected) {
                setBackground(new Color(33, 150, 243, 30));
            } else {
                setBackground(Color.WHITE);
            }

            setOpaque(true);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(235, 235, 235)),
                    BorderFactory.createEmptyBorder(15, 20, 15, 20)
            ));

            return this;
        }
    }
    // ==================== Fonction Utilitaires =================
    
    // ==================== DIALOGUES ====================
    
    private void showArticleDialog(Article article) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
            article == null ? "Ajouter un article" : "Modifier un article", true);
        dialog.setSize(800, 800);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        FormulaireArticle formulaire = new FormulaireArticle(adminCtrl, 
            new FormulaireArticle.ArticleCreatedCallback() {
                @Override
                public void onArticleCreated(Article article) {
                    refreshView("articles");
                }

                @Override
                public void onArticleUpdated(Article article) {
                    refreshView("articles");
                }
            }, 
            article
        );

        formulaire.setParentDialog(dialog);
        dialog.add(formulaire.getPanelPrincipal());
        dialog.setVisible(true);
    }
    
    private void showCategorieDialog(Categorie categorie) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
            categorie == null ? "Ajouter une catégorie" : "Modifier une catégorie", true);
        dialog.setSize(500, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel lblNom = new JLabel("Nom de la catégorie *");
        lblNom.setFont(robotoFont.deriveFont(Font.BOLD, 13f));

        JTextField txtNom = new JTextField(15);
        txtNom.setFont(robotoFont.deriveFont(Font.PLAIN, 14f));
        txtNom.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        JLabel lblDesc = new JLabel("Description");
        lblDesc.setFont(robotoFont.deriveFont(Font.BOLD, 13f));

        JTextArea txtDescription = new JTextArea(3, 15);
        txtDescription.setFont(robotoFont.deriveFont(Font.PLAIN, 14f));
        txtDescription.setLineWrap(true);
        txtDescription.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        if (categorie != null) {
            txtNom.setText(categorie.getNomCategorie());
            txtDescription.setText(categorie.getDescription());
        }

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(lblNom, gbc);
        gbc.gridx = 1;
        panel.add(txtNom, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(lblDesc, gbc);
        gbc.gridx = 1;
        panel.add(txtDescription, gbc);

        y++;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.setOpaque(false);

        JButton btnValider = new JButton(categorie == null ? "Ajouter" : "Modifier");
        btnValider.setFont(poppinsFont.deriveFont(Font.BOLD, 14f));
        btnValider.setForeground(Color.WHITE);
        btnValider.setBackground(SUCCESS_GREEN);
        btnValider.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnValider.setFocusPainted(false);
        btnValider.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnAnnuler = new JButton("Annuler");
        btnAnnuler.setFont(poppinsFont.deriveFont(Font.BOLD, 14f));
        btnAnnuler.setForeground(Color.WHITE);
        btnAnnuler.setBackground(DANGER_RED);
        btnAnnuler.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnAnnuler.setFocusPainted(false);
        btnAnnuler.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnValider.addActionListener(e -> {
            if (txtNom.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Le nom est obligatoire", "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                Categorie c = categorie != null ? categorie : new Categorie();
                c.setNomCategorie(txtNom.getText().trim());
                c.setDescription(txtDescription.getText().trim());

                boolean success = (categorie == null) 
                    ? adminCtrl.ajouterCategorie(c)
                    : adminCtrl.modifierCategorie(c);

                if (success) {
                    JOptionPane.showMessageDialog(dialog, 
                        categorie == null ? "Catégorie ajoutée !" : "Catégorie modifiée !", 
                        "Succès", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    refreshView("categories");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Erreur: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAnnuler.addActionListener(e -> dialog.dispose());

        btnPanel.add(btnValider);
        btnPanel.add(btnAnnuler);

        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        panel.add(btnPanel, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showDetailsDialog(int id) {
        // Implémenter l'affichage des détails selon la vue
        JOptionPane.showMessageDialog(this, "Détails - Fonctionnalité à implémenter");
    }
    
    private void refreshView(String view) {
        if (view.equals("articles")) {
            panelContent.remove(panelArticles);
            createArticlesPanel();
            cardLayout.show(panelContent, "articles");
        } else if (view.equals("categories")) {
            panelContent.remove(panelCategories);
            createCategoriesPanel();
            cardLayout.show(panelContent, "categories");
        } else if (view.equals("clients")) {
            panelContent.remove(panelClients);
            createClientsPanel();
            cardLayout.show(panelContent, "clients");
        } else if (view.equals("commandes")) {
            panelContent.remove(panelCommandes);
            createCommandesPanel();
            cardLayout.show(panelContent, "commandes");
        } else if (view.equals("paiements")) {
            panelContent.remove(panelPaiements);
            createPaiementsPanel();
            cardLayout.show(panelContent, "paiements");
        }
        panelContent.revalidate();
        panelContent.repaint();
    }
    
    private void chargerDonneesInitiales() {
        // Mettre à jour les statistiques du dashboard
        updateDashboardStats();
    }
    
    private void updateDashboardStats() {
        Map<String, Object> stats = adminCtrl.getStatistiquesGlobales();
        
        // Mettre à jour les cartes du dashboard
        panelDashboard.removeAll();
        createDashboardPanel();
        panelDashboard.revalidate();
        panelDashboard.repaint();
    }
    
    private void deconnecter() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Voulez-vous vraiment vous déconnecter ?", 
            "Déconnexion", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            adminCtrl.deconnecter();
            
            // Retour à l'interface client
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.getContentPane().removeAll();
            
            ClientController clientCtrl = new ClientController();
            InterfaceClient interfaceClient = new InterfaceClient(clientCtrl, clientCtrl.getPanierController());
            frame.getContentPane().add(interfaceClient.getPanelPrincipal());
            frame.revalidate();
            frame.repaint();
        }
    }
    
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

}