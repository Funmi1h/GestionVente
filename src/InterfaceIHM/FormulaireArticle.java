/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InterfaceIHM;

/**
 *
 * @author YACOUBOU
 */

import Metier.Article;
import Metier.Categorie;
import Controllers.AdminController;
import DAO.ConnexionDB;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FormulaireArticle extends JPanel {
    private JPanel panelPrincipal;
    
    private final Color PRIMARY_COLOR = new Color(255, 87, 34);
    private final Color SUCCESS_GREEN = new Color(76, 175, 80);
    private final Color DANGER_RED = new Color(244, 67, 54);
    private final Color BACKGROUND_LIGHT = new Color(250, 250, 250);
    private final Color TEXT_DARK = new Color(51, 51, 51);
    
    private Font poppinsFont;
    private Font robotoFont;
    
    private JTextField txtNom;
    private JTextArea txtDescription;
    private JTextField txtPrix;
    private JTextField txtStock;
    private JComboBox<Categorie> comboCategories;
    private JLabel labelImagePreview;
    private JLabel labelImagePath;
    private String cheminImageRelatif = "";
    private File imageSelectionnee;
    
    private AdminController adminCtrl;
    private ArticleCreatedCallback callback;
    private JDialog parentDialog;
    private Article articleEnEdition;
    private boolean modeEdition = false;
    
    public interface ArticleCreatedCallback {
        void onArticleCreated(Article article);
        void onArticleUpdated(Article article);
    }
    
    public FormulaireArticle(AdminController adminCtrl, ArticleCreatedCallback callback, Article article) {
        this.adminCtrl = adminCtrl;
        this.callback = callback;
        this.articleEnEdition = article;
        this.modeEdition = (article != null);
        
        chargerPolices();
        initUI();
        
        if (modeEdition) {
            preRemplirFormulaire();
        }
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
        panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(BACKGROUND_LIGHT);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel formPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        formPanel.setLayout(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        formPanel.setPreferredSize(new Dimension(700, 700));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        
        int y = 0;
        
        // Titre
        JLabel titreFormulaire = new JLabel(modeEdition ? "✏️ Modifier l'article" : "➕ Nouvel article");
        titreFormulaire.setFont(poppinsFont.deriveFont(Font.BOLD, 24f));
        titreFormulaire.setForeground(TEXT_DARK);
        titreFormulaire.setHorizontalAlignment(SwingConstants.CENTER);
        
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 8, 20, 8);
        formPanel.add(titreFormulaire, gbc);
        
        y++;
        
        // Champ Nom
        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 8, 8, 8);
        
        JLabel lblNom = creerLabel("Nom de l'article *");
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(lblNom, gbc);
        
        txtNom = new ModernTextField();
        txtNom.setFont(robotoFont.deriveFont(Font.PLAIN, 14f));
        txtNom.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1;
        formPanel.add(txtNom, gbc);
        
        y++;
        
        // Champ Description
        JLabel lblDescription = creerLabel("Description");
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(lblDescription, gbc);
        
        txtDescription = new JTextArea(5, 20);
        txtDescription.setFont(robotoFont.deriveFont(Font.PLAIN, 14f));
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        JScrollPane scrollDesc = new JScrollPane(txtDescription);
        scrollDesc.setBorder(null);
        gbc.gridx = 1;
        formPanel.add(scrollDesc, gbc);
        
        y++;
        
        // Champ Prix
        JLabel lblPrix = creerLabel("Prix (FCFA) *");
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(lblPrix, gbc);
        
        txtPrix = new ModernTextField();
        txtPrix.setFont(robotoFont.deriveFont(Font.PLAIN, 14f));
        txtPrix.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1;
        formPanel.add(txtPrix, gbc);
        
        y++;
        
        // Champ Stock
        JLabel lblStock = creerLabel("Stock *");
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(lblStock, gbc);
        
        txtStock = new ModernTextField();
        txtStock.setFont(robotoFont.deriveFont(Font.PLAIN, 14f));
        txtStock.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridx = 1;
        formPanel.add(txtStock, gbc);
        
        y++;
        
        // Champ Catégorie
        JLabel lblCategorie = creerLabel("Catégorie *");
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(lblCategorie, gbc);
        
        comboCategories = new JComboBox<>();
        comboCategories.setFont(robotoFont.deriveFont(Font.PLAIN, 14f));
        comboCategories.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        chargerCategories();
        gbc.gridx = 1;
        formPanel.add(comboCategories, gbc);
        
        y++;
        
        // Image
        JPanel imageSection = new JPanel();
        imageSection.setLayout(new BoxLayout(imageSection, BoxLayout.Y_AXIS));
        imageSection.setOpaque(false);
        imageSection.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            "Image de l'article",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            robotoFont.deriveFont(Font.BOLD, 12f),
            TEXT_DARK
        ));
        
        JPanel previewPanel = new JPanel(new BorderLayout());
        previewPanel.setOpaque(false);
        previewPanel.setPreferredSize(new Dimension(150, 150));
        
        labelImagePreview = new JLabel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(248, 250, 252));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                g2.setColor(new Color(226, 232, 240));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        labelImagePreview.setHorizontalAlignment(JLabel.CENTER);
        labelImagePreview.setVerticalAlignment(JLabel.CENTER);
        labelImagePreview.setPreferredSize(new Dimension(150, 150));
        labelImagePreview.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        labelImagePreview.setOpaque(true);
        labelImagePreview.setBackground(new Color(245, 245, 245));
        labelImagePreview.setText("🖼️");
        labelImagePreview.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        labelImagePreview.setForeground(TEXT_DARK);
        
        previewPanel.add(labelImagePreview, BorderLayout.CENTER);
        
        JPanel imageButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        imageButtonsPanel.setOpaque(false);
        
        JButton btnChoisirImage = creerBoutonAction("📁 Choisir une image", PRIMARY_COLOR);
        JButton btnEffacerImage = creerBoutonAction("🗑️ Effacer", DANGER_RED);
        
        btnChoisirImage.addActionListener(e -> choisirEtCopierImage());
        btnEffacerImage.addActionListener(e -> effacerImage());
        
        imageButtonsPanel.add(btnChoisirImage);
        imageButtonsPanel.add(btnEffacerImage);
        imageButtonsPanel.setPreferredSize(new Dimension(400, 50));
        
        labelImagePath = new JLabel("Aucune image sélectionnée");
        labelImagePath.setFont(robotoFont.deriveFont(Font.ITALIC, 11f));
        labelImagePath.setForeground(TEXT_DARK);
        labelImagePath.setHorizontalAlignment(JLabel.CENTER);
        
        imageSection.add(previewPanel);
        imageSection.add(Box.createVerticalStrut(10));
        imageSection.add(imageButtonsPanel);
        imageSection.add(Box.createVerticalGlue());
        imageSection.add(labelImagePath);
        //iPanel.add(imageSection);
        //imageSection.add(previewPanel, BorderLayout.CENTER);
        //imageSection.add(imageButtonsPanel, BorderLayout.SOUTH);
        
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(imageSection, gbc);
        
        y++;
        
        // Boutons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        actionPanel.setOpaque(false);
        
        ModernButton btnValider = new ModernButton(
                modeEdition ? "Mettre à jour" : "Ajouter l'article",
                SUCCESS_GREEN
        );
        ModernButton btnAnnuler = new ModernButton("Annuler", DANGER_RED);

        btnValider.addActionListener(e -> validerFormulaire());
        btnAnnuler.addActionListener(e -> fermerDialog());
        
        actionPanel.add(btnValider);
        actionPanel.add(btnAnnuler);
        
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 8, 8, 8);
        formPanel.add(actionPanel, gbc);
        
        panelPrincipal.add(formPanel, new GridBagConstraints());
    }
    
    private void choisirEtCopierImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choisir une image pour l'article");
        
        String userHome = System.getProperty("user.home");
        File picturesDir = new File(userHome + "/Pictures");
        if (picturesDir.exists()) {
            fileChooser.setCurrentDirectory(picturesDir);
        }
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Images (*.png, *.jpg, *.jpeg, *.bmp)", 
            "png", "jpg", "jpeg", "bmp"
        );
        fileChooser.setFileFilter(filter);
        
        int result = fileChooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) return;
        
        File fichierSource = fileChooser.getSelectedFile();
        
        ImageIcon imageIcon = new ImageIcon(fichierSource.getAbsolutePath());
        if (imageIcon.getIconWidth() <= 0 || imageIcon.getIconHeight() <= 0) {
            JOptionPane.showMessageDialog(this, "Image invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String dossierApp = System.getProperty("user.dir");
        String dossierImages = dossierApp + "/data/images/articles/";
        
        File dossier = new File(dossierImages);
        if (!dossier.exists()) dossier.mkdirs();
        
        String extension = "";
        String nomFichier = fichierSource.getName();
        int dernierPoint = nomFichier.lastIndexOf('.');
        if (dernierPoint > 0) extension = nomFichier.substring(dernierPoint + 1).toLowerCase();
        else extension = "jpg";
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        int random = new Random().nextInt(10000);
        
        String nomFichierUnique = String.format("article_%s_%d.%s", timestamp, random, extension);
        String cheminComplet = dossierImages + nomFichierUnique;
        
        try {
            Path sourcePath = fichierSource.toPath();
            Path destPath = Paths.get(cheminComplet);
            Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
            
            cheminImageRelatif = "data/images/articles/" + nomFichierUnique;
            imageSelectionnee = new File(cheminComplet);
            
            afficherPreviewImage(cheminComplet);
            labelImagePath.setText("✓ Image: " + nomFichierUnique);
            labelImagePath.setForeground(SUCCESS_GREEN);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void afficherPreviewImage(String cheminImage) {
        try {
            ImageIcon icon = new ImageIcon(cheminImage);
            Image img = icon.getImage();
            
            int maxSize = 140;
            int width = img.getWidth(null);
            int height = img.getHeight(null);
            
            if (width > maxSize || height > maxSize) {
                if (width > height) {
                    height = (height * maxSize) / width;
                    width = maxSize;
                } else {
                    width = (width * maxSize) / height;
                    height = maxSize;
                }
            }
            
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            labelImagePreview.setIcon(new ImageIcon(scaledImg));
            labelImagePreview.setText(null);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void effacerImage() {
        cheminImageRelatif = "";
        imageSelectionnee = null;
        labelImagePreview.setIcon(null);
        labelImagePreview.setText("🖼️");
        labelImagePreview.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        labelImagePath.setText("Aucune image sélectionnée");
        labelImagePath.setForeground(TEXT_DARK);
    }
    
    private void chargerCategories() {
        comboCategories.removeAllItems();
        List<Categorie> categories = adminCtrl.getAllCategories();
        for (Categorie cat : categories) {
            comboCategories.addItem(cat);
        }
    }
    
    private void preRemplirFormulaire() {
        if (articleEnEdition != null) {
            txtNom.setText(articleEnEdition.getNom());
            txtDescription.setText(articleEnEdition.getDescription());
            txtPrix.setText(String.valueOf(articleEnEdition.getPrix()));
            txtStock.setText(String.valueOf(articleEnEdition.getStock()));
            
            Categorie catArticle = adminCtrl.getCategorieArticleObj(articleEnEdition.getId_article());
            if (catArticle != null) comboCategories.setSelectedItem(catArticle);
            
            String photo = articleEnEdition.getUrlPhoto();
            if (photo != null && !photo.isEmpty()) {
                cheminImageRelatif = photo;
                String cheminAbsolu = System.getProperty("user.dir") + "/" + photo;
                File imgFile = new File(cheminAbsolu);
                if (imgFile.exists()) {
                    afficherPreviewImage(cheminAbsolu);
                    labelImagePath.setText("✓ Image: " + new File(photo).getName());
                    labelImagePath.setForeground(SUCCESS_GREEN);
                }
            }
        }
    }
    
    private void validerFormulaire() {
        if (txtNom.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le nom est obligatoire", "Erreur", JOptionPane.WARNING_MESSAGE);
            txtNom.requestFocus();
            return;
        }
        
        float prix = 0;
        try {
            prix = Float.parseFloat(txtPrix.getText().trim());
            if (prix <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Prix invalide", "Erreur", JOptionPane.WARNING_MESSAGE);
            txtPrix.requestFocus();
            return;
        }
        
        int stock = 0;
        try {
            stock = Integer.parseInt(txtStock.getText().trim());
            if (stock < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stock invalide", "Erreur", JOptionPane.WARNING_MESSAGE);
            txtStock.requestFocus();
            return;
        }
        
        Categorie categorie = (Categorie) comboCategories.getSelectedItem();
        if (categorie == null) {
            JOptionPane.showMessageDialog(this, "Sélectionnez une catégorie", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Article article;
        if (modeEdition) {
            article = articleEnEdition;
            article.setNom(txtNom.getText().trim());
            article.setDescription(txtDescription.getText().trim());
            article.setPrix(prix);
            article.setStock(stock);
            article.setUrlPhoto(cheminImageRelatif);
        } else {
            article = new Article(
                txtNom.getText().trim(),
                txtDescription.getText().trim(),
                prix,
                stock,
                cheminImageRelatif
            );
        }
        
        boolean success = modeEdition 
            ? adminCtrl.modifierArticle(article, categorie.getIdCategorie())
            : adminCtrl.ajouterArticle(article, categorie.getIdCategorie());
        
        if (success) {
            JOptionPane.showMessageDialog(this, 
                modeEdition ? "Article modifié !" : "Article ajouté !", 
                "Succès", JOptionPane.INFORMATION_MESSAGE);
            
            if (modeEdition && callback != null) callback.onArticleUpdated(article);
            else if (callback != null) callback.onArticleCreated(article);
            
            fermerDialog();
        } else {
            if(adminCtrl.estDejaCreer(article.getNom())){
                JOptionPane.showMessageDialog(this, article.getNom()+
                "a été déjà créer", 
                "Erreur", 
                JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void fermerDialog() {
        if (parentDialog != null) parentDialog.dispose();
        else {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window instanceof JDialog) ((JDialog) window).dispose();
        }
    }
    
    public void setParentDialog(JDialog dialog) { this.parentDialog = dialog; }
    public JPanel getPanelPrincipal() { return panelPrincipal; }
    
   
    
    /**
     * Crée un label stylisé
     */
    private JLabel creerLabel(String texte) {
        JLabel label = new JLabel(texte);
        label.setFont(robotoFont.deriveFont(Font.BOLD, 13f));
        label.setForeground(TEXT_DARK);
        return label;
    }
    
    /**
     * Crée un bouton principal
     */
    private JButton creerBoutonPrincipal(String texte) {
        JButton btn = new JButton(texte);
        btn.setFont(poppinsFont.deriveFont(Font.BOLD, 14f));
        btn.setForeground(Color.WHITE);
        btn.setBackground(SUCCESS_GREEN);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(SUCCESS_GREEN.darker());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(SUCCESS_GREEN);
            }
        });
        
        return btn;
    }
    
    /**
     * Crée un bouton secondaire
     */
    private JButton creerBoutonSecondaire(String texte) {
        JButton btn = new JButton(texte);
        btn.setFont(poppinsFont.deriveFont(Font.BOLD, 14f));
        btn.setForeground(TEXT_DARK);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(9, 24, 9, 24)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(250, 250, 250));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.WHITE);
            }
        });
        
        return btn;
    }
    
    /**
     * Crée un bouton d'action
     */
    private JButton creerBoutonAction(String texte, Color bgColor) {

    JButton btn = new JButton(texte) {

        private boolean hover = false;

        {
            setForeground(Color.WHITE);
            setFont(robotoFont.deriveFont(Font.BOLD, 12f));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    hover = true;
                    repaint();
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    hover = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            Color base = hover ? bgColor.darker() : bgColor;

            g2.setColor(base);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);

            g2.dispose();
            super.paintComponent(g);
        }
    };

    btn.setPreferredSize(new Dimension(160, 36));
    return btn;
}

    /*
    Classes utilitaires
    */
    class ModernTextField extends JTextField {

        private boolean focused = false;

        public ModernTextField() {
            setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
            setBackground(new Color(248, 250, 252));
            setFont(robotoFont.deriveFont(14f));
            setOpaque(false);

            addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    focused = true;
                    repaint();
                }
                public void focusLost(java.awt.event.FocusEvent evt) {
                    focused = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);

            if (focused) {
                g2.setColor(PRIMARY_COLOR);
                g2.setStroke(new BasicStroke(2));
            } else {
                g2.setColor(new Color(226, 232, 240));
                g2.setStroke(new BasicStroke(1));
            }

            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 14, 14);

            g2.dispose();
            super.paintComponent(g);
        }
    }
    
    class ModernButton extends JButton {

        private boolean hover = false;

        public ModernButton(String text, Color color) {
            super(text);
            setForeground(Color.WHITE);
            setFont(poppinsFont.deriveFont(Font.BOLD, 14f));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    hover = true;
                    repaint();
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    hover = false;
                    repaint();
                }
            });

            putClientProperty("bg", color);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            Color base = (Color) getClientProperty("bg");
            if (hover) base = base.darker();

            g2.setColor(base);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);

            g2.dispose();
            super.paintComponent(g);
        }
    }


    public static void main(String[] args) {
        Article article = null;
        JFrame app = new JFrame();
        AdminController adminCtrl = new AdminController(ConnexionDB.connect());
        FormulaireArticle formulaire = new FormulaireArticle(adminCtrl, 
            new FormulaireArticle.ArticleCreatedCallback() {
                @Override
                public void onArticleCreated(Article article) {
                }

                @Override
                public void onArticleUpdated(Article article) {
                }
            }, 
            article
        );
        app.add(formulaire.getPanelPrincipal());
        app.pack();
        app.setLocationRelativeTo(null);
        app.setVisible(true);
    }
}