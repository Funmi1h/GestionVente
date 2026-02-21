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
import java.awt.*;
import java.awt.event.*;
import Metier.Client;
import Controllers.ClientController;

public class ProfilPopup extends JWindow {
    private ClientController clientCtrl;
    private RoundButton logOut;
    private JLabel labelInfos;
    private JLabel labelCommandes;
    private JLabel labelNom;
    private JLabel labelEmail;
    private CustumizedRoundedPanel panelPrincipal;
    private AWTEventListener awtListener;

    public ProfilPopup(Window owner, ClientController clientCtrl) {
        super(owner);
        this.clientCtrl = clientCtrl;
        this.setBackground(new Color(0, 0, 0, 0));

        // Fermer le popup si clic en dehors
        awtListener = event -> {
            if (event instanceof MouseEvent) {
                MouseEvent me = (MouseEvent) event;
                if (me.getID() == MouseEvent.MOUSE_PRESSED) {
                    if (isVisible() && !getBounds().contains(me.getLocationOnScreen())) {
                        dispose();
                    }
                }
            }
        };
        Toolkit.getDefaultToolkit().addAWTEventListener(awtListener, AWTEvent.MOUSE_EVENT_MASK);

        panelPrincipal = new CustumizedRoundedPanel(30, 30, 30, 30, Color.WHITE);
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        labelNom = new JLabel();
        labelNom.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelNom.setAlignmentX(Component.CENTER_ALIGNMENT);

        labelEmail = new JLabel();
        labelEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        labelEmail.setForeground(new Color(100, 100, 100));
        labelEmail.setAlignmentX(Component.CENTER_ALIGNMENT);

        labelInfos = new JLabel("Mes informations");
        labelInfos.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelInfos.setForeground(new Color(255, 87, 34));
        labelInfos.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelInfos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addHoverEffect(labelInfos);
        labelInfos.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        dispose(); // fermer le popup
        // Afficher le panel dans la fenêtre principale
        Window parent = getOwner();
        if (parent instanceof JFrame) {
            JFrame frame = (JFrame) parent;
            ProfilInfosPanel profilPanel = new ProfilInfosPanel(clientCtrl);
            JButton btnAnnuler = profilPanel.getBtnAnnuler();

            frame.getContentPane().removeAll();
            frame.getContentPane().add(profilPanel.getPanelPrincipal());
            frame.revalidate();
            frame.repaint();

            // Retour à l'accueil au clic Annuler
            btnAnnuler.addActionListener(ev -> {
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new InterfaceClient(clientCtrl,
                clientCtrl.getPanierController()).getPanelPrincipal());
                frame.revalidate();
                frame.repaint();
            });
        }
    }
});

        labelCommandes = new JLabel("Mes commandes");
        labelCommandes.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelCommandes.setForeground(new Color(255, 87, 34));
        labelCommandes.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelCommandes.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addHoverEffect(labelCommandes);
        labelCommandes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO : tes instructions ici
            }
        });

        logOut = new RoundButton("Se déconnecter");
        logOut.setBackground(new Color(255, 87, 34));
        logOut.setForeground(Color.WHITE);
        logOut.setAlignmentX(Component.CENTER_ALIGNMENT);
        logOut.addActionListener(e -> {
            ClientController clientCtrl = new ClientController();
            clientCtrl.deconnexion();
            dispose();

            Window parent = getOwner();
            if (parent instanceof JFrame) {
                ((JFrame) parent).getContentPane().removeAll();
                ((JFrame) parent).getContentPane().add(new InterfaceClient(clientCtrl, clientCtrl.getPanierController()).getPanelPrincipal());
                ((JFrame) parent).revalidate();
                ((JFrame) parent).repaint();
            }
        });

        panelPrincipal.add(labelNom);
        panelPrincipal.add(Box.createVerticalStrut(5));
        panelPrincipal.add(labelEmail);
        panelPrincipal.add(Box.createVerticalStrut(15));
        panelPrincipal.add(labelInfos);
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(labelCommandes);
        panelPrincipal.add(Box.createVerticalStrut(20));
        panelPrincipal.add(logOut);

        this.add(panelPrincipal);
        this.pack();
    }

    private void addHoverEffect(JLabel label) {
        label.setOpaque(true);
        label.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setBackground(new Color(240, 240, 240));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setBackground(Color.WHITE);
            }
        });
    }

    @Override
    public void dispose() {
        Toolkit.getDefaultToolkit().removeAWTEventListener(awtListener);
        super.dispose();
    }

    public void setClientInfo(Client client) {
        labelNom.setText(client.getPrenom() + " " + client.getNom());
        labelEmail.setText(client.getEmail());
    }
}