/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InterfaceIHM;

/**
 *
 * @author Héloïse
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import Metier.Client;
import Controllers.ClientController;

public class ProfilInfosPanel extends JPanel {

    private final Color ORANGE       = new Color(255, 87, 34);
    private final Color LIGHT_GRAY   = new Color(245, 245, 245);
    private final Color BORDER_GRAY  = new Color(210, 210, 210);
    private final Color TEXT_DARK    = new Color(33, 33, 33);
    private final Color TEXT_LIGHT   = new Color(120, 120, 120);

    private JPanel      panelPrincipal;
    private RoundField  fieldPrenom;
    private RoundField  fieldNom;
    private RoundField  fieldEmail;
    private RoundField  fieldTelephone;
    private JButton     btnSauvegarder;
    private JButton     btnAnnuler;

    private final ClientController clientCtrl;
    private final Client           client;

    // ─── Constructeur ────────────────────────────────────────────
    public ProfilInfosPanel(ClientController clientCtrl) {
        this.clientCtrl = clientCtrl;
        this.client     = clientCtrl.getClientConnecte();

        panelPrincipal = new JPanel();
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setLayout(new GridBagLayout());
        panelPrincipal.setBorder(new EmptyBorder(40, 50, 40, 50));

        buildUI();
        remplirChamps();
    }

    // ─── Construction de l'UI ────────────────────────────────────
    private void buildUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx  = 0;
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets  = new Insets(0, 0, 0, 0);

        // ── Onglets Se connecter / Créer un compte ──
        JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        tabPanel.setOpaque(false);

        JLabel tabInfos   = onglet("Mes informations", true);
        tabPanel.add(tabInfos);

        gbc.gridy = 0;
        panelPrincipal.add(tabPanel, gbc);

        // ── Titre ──
        JLabel titre = new JLabel("Mes informations");
        titre.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titre.setForeground(TEXT_DARK);
        titre.setBorder(new EmptyBorder(24, 0, 4, 0));

        gbc.gridy = 1;
        panelPrincipal.add(titre, gbc);

        // ── Sous-titre ──
        JLabel sousTitre = new JLabel("Modifiez vos informations personnelles");
        sousTitre.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sousTitre.setForeground(TEXT_LIGHT);
        sousTitre.setBorder(new EmptyBorder(0, 0, 24, 0));

        gbc.gridy = 2;
        panelPrincipal.add(sousTitre, gbc);

        // ── Champ Prénom ──
        gbc.gridy = 3;
        panelPrincipal.add(label("Prénom"), gbc);
        fieldPrenom = new RoundField("👤");
        gbc.gridy = 4;
        gbc.insets = new Insets(6, 0, 16, 0);
        panelPrincipal.add(fieldPrenom, gbc);

        // ── Champ Nom ──
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 0, 0);
        panelPrincipal.add(label("Nom"), gbc);
        fieldNom = new RoundField("👤");
        gbc.gridy = 6;
        gbc.insets = new Insets(6, 0, 16, 0);
        panelPrincipal.add(fieldNom, gbc);

        // ── Champ Email ──
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 0, 0);
        panelPrincipal.add(label("Adresse Email"), gbc);
        fieldEmail = new RoundField("✉");
        gbc.gridy = 8;
        gbc.insets = new Insets(6, 0, 16, 0);
        panelPrincipal.add(fieldEmail, gbc);

        // ── Champ Téléphone ──
        gbc.gridy = 9;
        gbc.insets = new Insets(0, 0, 0, 0);
        panelPrincipal.add(label("Téléphone"), gbc);
        fieldTelephone = new RoundField("📞");
        gbc.gridy = 10;
        gbc.insets = new Insets(6, 0, 24, 0);
        panelPrincipal.add(fieldTelephone, gbc);

        // ── Bouton Sauvegarder ──
        btnSauvegarder = roundButton("Sauvegarder les modifications", ORANGE, Color.WHITE);
        btnSauvegarder.addActionListener(e -> sauvegarder());
        gbc.gridy  = 11;
        gbc.insets = new Insets(0, 0, 12, 0);
        panelPrincipal.add(btnSauvegarder, gbc);

        // ── Bouton Annuler ──
        btnAnnuler = roundButton("Annuler", LIGHT_GRAY, TEXT_DARK);
        gbc.gridy  = 12;
        gbc.insets = new Insets(0, 0, 0, 0);
        panelPrincipal.add(btnAnnuler, gbc);
    }

    // ─── Remplissage des champs depuis le client connecté ────────
    private void remplirChamps() {
        if (client == null) return;
        fieldPrenom   .setText(nvl(client.getPrenom()));
        fieldNom      .setText(nvl(client.getNom()));
        fieldEmail    .setText(nvl(client.getEmail()));
        fieldTelephone.setText(nvl(client.getTelephone()));
    }

    // ─── Sauvegarde ──────────────────────────────────────────────
    private void sauvegarder() {
        // TODO : appeler clientCtrl.modifierInfosClient(...)
        JOptionPane.showMessageDialog(panelPrincipal,
            "Informations mises à jour !",
            "Succès",
            JOptionPane.INFORMATION_MESSAGE);
    }

    // ─── Helpers visuels ─────────────────────────────────────────
    private JLabel label(String texte) {
        JLabel lbl = new JLabel(texte);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(TEXT_DARK);
        return lbl;
    }

    private JLabel onglet(String texte, boolean actif) {
        JLabel lbl = new JLabel(texte);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(actif ? ORANGE : TEXT_LIGHT);
        lbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (actif) {
            lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ORANGE));
        }
        return lbl;
    }

    private JButton roundButton(String texte, Color bg, Color fg) {
        JButton btn = new JButton(texte) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                g2.setColor(fg);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth()  - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(Integer.MAX_VALUE, 48));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private String nvl(String s) { return s != null ? s : ""; }

    // ─── Getters ─────────────────────────────────────────────────
    public JPanel  getPanelPrincipal() { return panelPrincipal; }
    public JButton getBtnAnnuler()     { return btnAnnuler;     }

    // ════════════════════════════════════════════════════════════
    // Classe interne : champ de saisie arrondi avec icône
    // ════════════════════════════════════════════════════════════
    private class RoundField extends JPanel {

        private final JTextField field;

        RoundField(String icone) {
            setLayout(new BorderLayout(8, 0));
            setOpaque(false);
            setPreferredSize(new Dimension(0, 52));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));

            JLabel ico = new JLabel(icone);
            ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
            ico.setForeground(TEXT_LIGHT);
            ico.setBorder(new EmptyBorder(0, 14, 0, 0));

            field = new JTextField();
            field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            field.setForeground(TEXT_DARK);
            field.setBorder(new EmptyBorder(0, 6, 0, 14));
            field.setOpaque(false);

            add(ico,   BorderLayout.WEST);
            add(field, BorderLayout.CENTER);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(LIGHT_GRAY);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
            g2.setColor(BORDER_GRAY);
            g2.setStroke(new BasicStroke(1f));
            g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f,
                    getWidth() - 1, getHeight() - 1, 12, 12));
            g2.dispose();
            super.paintComponent(g);
        }

        public String getText()           { return field.getText(); }
        public void   setText(String txt) { field.setText(txt);    }
    }
}