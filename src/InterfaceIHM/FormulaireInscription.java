package InterfaceIHM;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Héloïse
 */


import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class FormulaireInscription extends JPanel {
    private JPanel panelPrincipal;
    private Font robotoFont;
    private final Color ORANGE_PRIMARY = new Color(253, 94, 9);
    private final Color BACKGROUND_LIGHT = new Color(250, 250, 250);
    private final Color TEXT_DARK = new Color(51, 51, 51);
    private final Color TEXT_LIGHT = new Color(102, 102, 102);
    
    private JTextField champEmail;
    private JPasswordField champMotDePasse;
    private JPasswordField champMotDePasseConfirmee;
    private JTextField champNom;
    private JTextField champPrenom;
    private JTextField champAdresse;
    JButton btnInscription;
    
    
    
    private Font chargerFontUnique() {
        Font policeParDefaut = new Font("Segoe UI", Font.PLAIN, 14);
        try {
            InputStream is = getClass().getResourceAsStream("./ressources/fonts/Roboto.ttf");
            if (is != null) {
                Font police = Font.createFont(Font.TRUETYPE_FONT, is);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(police);
                is.close();
                return police;
            }
        } catch (Exception e) {
            System.err.println("Impossible de charger la police Roboto: " + e.getMessage());
        }
        return policeParDefaut;
    }
    
    
    private JLabel creerLabel(String texte, float taille, int style) {
        JLabel label = new JLabel(texte);
        label.setFont(robotoFont.deriveFont(style, taille));
        label.setForeground(TEXT_DARK);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        return label;
    }
    

    private JPanel creerChampAvecIcone(String placeholder, String type, JTextField fieldReference) {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.X_AXIS));
        wrapper.setMaximumSize(new Dimension(380, 50));
        wrapper.setPreferredSize(new Dimension(380, 50));
        
        wrapper.setOpaque(false);
        wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel champContainer = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                g2.setColor(new Color(220, 220, 220));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                
                g2.dispose();
            }
        };
        champContainer.setOpaque(false);
        champContainer.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        
        JLabel icone = new JLabel();
        icone.setPreferredSize(new Dimension(20, 20));
        icone.setForeground(TEXT_LIGHT);
        
        switch (type.toLowerCase()) {
        case "telephone": icone.setText("📱"); break;
        case "password":  icone.setText("🔒"); break;
        case "email":     icone.setText("✉️"); break;
        case "nom":       
        case "prenom":    icone.setText("👤"); break;
        default:          icone.setText("✏️"); break;
    }
        
        JTextField textField = fieldReference;
        textField.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        textField.setFont(robotoFont.deriveFont(Font.PLAIN, 14f));
        textField.setForeground(TEXT_DARK);
        textField.setOpaque(false);
        
        // placeholder
        textField.setText(placeholder);
        textField.setForeground(TEXT_LIGHT);
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(TEXT_DARK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(TEXT_LIGHT);
                    textField.setText(placeholder);
                }
            }
        });
        
        champContainer.add(icone, BorderLayout.WEST);
        champContainer.add(textField, BorderLayout.CENTER);
        
        wrapper.add(champContainer);
        return wrapper;
    }
    
  
    private JPanel creerGroupe(String labelTexte, JPanel champInput) {
        JPanel groupe = new JPanel();
        groupe.setLayout(new BoxLayout(groupe, BoxLayout.Y_AXIS));
        groupe.setOpaque(false);
        groupe.setAlignmentX(Component.LEFT_ALIGNMENT);
        groupe.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        JLabel label = creerLabel(labelTexte, 13f, Font.BOLD);
        groupe.add(label);
        groupe.add(champInput);
        
        return groupe;
    }
    
   
    private JButton creerBoutonPrincipal(String texte) {
        JButton bouton = new JButton(texte) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Dégradé orange si hover, sinon couleur unie
                if (getModel().isRollover()) {
                    GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(255, 110, 30),
                        getWidth(), 0, ORANGE_PRIMARY
                    );
                    g2.setPaint(gradient);
                } else {
                    g2.setColor(ORANGE_PRIMARY);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                // Texte
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                
                g2.dispose();
            }
        };
        
        bouton.setFont(robotoFont.deriveFont(Font.BOLD, 15f));
        bouton.setForeground(Color.WHITE);
        bouton.setMaximumSize(new Dimension(380, 50));
        bouton.setPreferredSize(new Dimension(380, 50));
        bouton.setAlignmentX(Component.LEFT_ALIGNMENT);
        bouton.setContentAreaFilled(false);
        bouton.setBorderPainted(false);
        bouton.setFocusPainted(false);
        bouton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return bouton;
    }
    
   
    private JPanel creerPanneauGauche() {
        JPanel panelLeft = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(255, 235, 215), 
                    0, getHeight(), new Color(255, 220, 190)  
                );
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
                
                g2.dispose();
            }
        };
        
        panelLeft.setLayout(new BorderLayout());
        panelLeft.setOpaque(false);
        
        // Image au centre
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        
        JLabel illustration = new JLabel();
        try {
            java.net.URL imageUrl = getClass().getResource("./ressources/images/illustration.png");
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(imageUrl);
                Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                illustration.setIcon(new ImageIcon(img));
            } else {
                illustration.setText("🛍️");
                illustration.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 120));
                illustration.setForeground(new Color(253, 94, 9, 150));
            }
        } catch (Exception e) {
            // Placeholder emoji si erreur
            illustration.setText("🛍️");
            illustration.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 120));
            illustration.setForeground(new Color(253, 94, 9, 150));
        }
        illustration.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(illustration);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 50, 40));
        
        JLabel textePrincipal = new JLabel("<html><body style='width: 300px; text-align: center;'>" +
                                           "Bienvenue sur LuxStore</body></html>");
        textePrincipal.setFont(robotoFont.deriveFont(Font.BOLD, 32f));
        textePrincipal.setForeground(new Color(253, 94, 9));
        textePrincipal.setAlignmentX(Component.CENTER_ALIGNMENT);
        textePrincipal.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel texteSecondaire = new JLabel("<html><body style='width: 300px; text-align: center;'>" +
                                            "Vos achats simplifiés </body></html>");
        texteSecondaire.setFont(robotoFont.deriveFont(Font.PLAIN, 16f));
        texteSecondaire.setForeground(new Color(100, 100, 100));
        texteSecondaire.setAlignmentX(Component.CENTER_ALIGNMENT);
        texteSecondaire.setHorizontalAlignment(SwingConstants.CENTER);
        texteSecondaire.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        bottomPanel.add(textePrincipal);
        bottomPanel.add(texteSecondaire);
        
        panelLeft.add(centerPanel, BorderLayout.CENTER);
        panelLeft.add(bottomPanel, BorderLayout.SOUTH);
        
        return panelLeft;
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

    
   
    private JPanel creerPanneauDroit() {
        JPanel panelRight = new JPanel();
        panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
        panelRight.setOpaque(false);
        panelRight.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        header.setOpaque(false);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.setMaximumSize(new Dimension(500, 40));
        
        JLabel seConnecter = creerLabel("Se connecter", 14f, Font.BOLD);
        seConnecter.setForeground(TEXT_LIGHT);
        seConnecter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel creerCompte = creerLabel("Créer un compte", 14f, Font.PLAIN);
        creerCompte.setForeground(ORANGE_PRIMARY);
        creerCompte.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        seConnecter.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e){
                afficherFormulaireConnexion();

            }
        });
        
        header.add(seConnecter);
        header.add(creerCompte);
        
        JLabel titrePrincipal = creerLabel("Créer votre compte", 28f, Font.BOLD);
        titrePrincipal.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));

        
        champNom = new JTextField(20);
        JPanel champN = creerChampAvecIcone("Votre nom", "nom", champNom);
        JPanel groupeNom = creerGroupe("Nom ", champN);
        
        champPrenom = new JTextField(20);
        JPanel champP = creerChampAvecIcone("Votre prénom", "prenom", champPrenom);
        JPanel groupePrenom = creerGroupe("Prénom", champP);
        
        champMotDePasse = new JPasswordField(20);
        JPanel champMdp = creerChampAvecIcone("••••••••", "password", champMotDePasse);
        JPanel groupeMotDePasse = creerGroupe("Mot de passe", champMdp);
        
        champMotDePasseConfirmee = new JPasswordField(20);
        JPanel champMdpConf = creerChampAvecIcone("••••••••", "password", champMotDePasseConfirmee);
        JPanel groupeMotDePasseConfirmee = creerGroupe("Confirmer le mot de passe", champMdpConf);
        
        champEmail = new JTextField(20);
        JPanel champEm = creerChampAvecIcone("email@gmail.com", "email", champEmail);
        JPanel groupeEmail = creerGroupe("Adresse email", champEm);
        
        champAdresse = new JPasswordField(20);
        JPanel champAd = creerChampAvecIcone("Votre adresse", "password", champAdresse);
        JPanel groupeAdresse = creerGroupe("Adresse", champAd);
        
        btnInscription = creerBoutonPrincipal("S'inscrire");
        
        JPanel bottomText = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        bottomText.setOpaque(false);
        bottomText.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottomText.setMaximumSize(new Dimension(500, 30));
        bottomText.setBorder(BorderFactory.createEmptyBorder(13, 0, 0, 0));
        
        JLabel textNormal = new JLabel("Vous avez déja de compte ?");
        textNormal.setFont(robotoFont.deriveFont(Font.PLAIN, 12f));
        textNormal.setForeground(TEXT_LIGHT);
        
        JLabel textLink = new JLabel("Connectez-vous gratuitement");
        textLink.setFont(robotoFont.deriveFont(Font.BOLD, 12f));
        textLink.setForeground(ORANGE_PRIMARY);
        textLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        textLink.addMouseListener( new java.awt.event.MouseAdapter(){
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e){
            afficherFormulaireConnexion();
        }
        });
        
        // assemblage
        panelRight.add(header);
        panelRight.add(titrePrincipal);
        panelRight.add(groupeNom);
        panelRight.add(groupePrenom);
        panelRight.add(groupeEmail);
        panelRight.add(groupeAdresse);
        panelRight.add(groupeMotDePasse);
        panelRight.add(groupeMotDePasseConfirmee);
        panelRight.add(btnInscription);
        panelRight.add(bottomText);
        panelRight.add(Box.createVerticalGlue());
        
        return panelRight;
    }
    
   //constructeur
    public FormulaireInscription() {
        robotoFont = chargerFontUnique();
        
        panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(BACKGROUND_LIGHT);
        
        JPanel formContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fond blanc avec coins arrondis
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                g2.dispose();
            }
        };
        
        formContainer.setLayout(new GridLayout(1, 2, 0, 0));
        formContainer.setOpaque(false);
        formContainer.setPreferredSize(new Dimension(980, 600));
        
        // Ajout des deux panneaux
        formContainer.add(creerPanneauGauche());
        formContainer.add(creerPanneauDroit());
        
        panelPrincipal.add(formContainer);
    }
    
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
    
    
    public String getEmail() {
        String em = champEmail.getText();
        return em.equals("email@gmail.com") ? "" : em;
    }
    
   
    public String getNom(){
        String n = champNom.getText();
        return n.equals("Votre nom" )? "": n;
    }
    
    public String getPrenom(){
        String n = champPrenom.getText();
        return n.equals("Votre prénom" )? "": n;
    }
    
    public String getAdresse(){
        String n = champAdresse.getText();
        return n.equals("Votre adresse" )? "": n;
    }
    
    public String getMotDePasse() {
        String mdp = String.valueOf(champMotDePasse.getPassword());
        return mdp.equals("••••••••") ? "" : mdp;
    }
    
    public String getMotDePasseConfirmee(){
        String mdp = String.valueOf(champMotDePasseConfirmee.getPassword());
        return mdp.equals("••••••••")? "": mdp;
    }
}