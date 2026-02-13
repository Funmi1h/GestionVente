/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InterfaceIHM;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Font;
/**
 *
 * @author Héloïse
 */
public class FormInscription extends JPanel {
    private JPanel panelPrincipal;
    
    public FormInscription(){
        


        // panel pour le formulaire 
        CustumizedRoundedPanel formPanel = new CustumizedRoundedPanel(12, 12, 12, 12,Color.WHITE);
        formPanel.setLayout(new GridLayout(1, 2, 20, 0));
        
        
           JPanel wrapperCenter = new JPanel(new GridBagLayout()); 
        wrapperCenter.setOpaque(false); // ce panel va conteni le formPanel pour permettre de le centrer 
       
       formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        Dimension tailleFixe = new Dimension(400, 50);
        panelPrincipal = new JPanel();
        panelPrincipal.setOpaque(false);
        panelPrincipal.setLayout(new BorderLayout());
        JLabel labelTitre = new JLabel("Inscrivez-vous !");
        labelTitre.setHorizontalAlignment(JLabel.CENTER);
       
        JPanel groupNom = new JPanel();
        groupNom.setLayout(new BoxLayout(groupNom, BoxLayout.Y_AXIS ));
        JLabel labelNom = new JLabel("Nom");
        labelNom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        labelNom.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        RoundTextField inputNom = new RoundTextField(50);
        inputNom.setText("Votre nom ... ");
        inputNom.setBorderColor(new Color (253, 94, 9));
        
        inputNom.setMaximumSize(tailleFixe);
        groupNom.add(labelNom);
        groupNom.add(inputNom);
        groupNom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      
        
        JPanel groupPrenom = new JPanel();
        groupPrenom.setLayout(new BoxLayout(groupPrenom, BoxLayout.Y_AXIS));
        JLabel labelPrenom = new JLabel("Prénom");
        RoundTextField inputPrenom = new RoundTextField(50);
        inputPrenom.setText("Votre Prénom");
        inputPrenom.setBorderColor(new Color (253, 94, 9));
        inputPrenom.setMaximumSize(new Dimension(tailleFixe));
        groupPrenom.add(labelPrenom);
        groupPrenom.add(inputPrenom);
        groupPrenom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        
        JPanel groupContact = new JPanel();
        groupContact.setLayout(new BoxLayout(groupContact, BoxLayout.Y_AXIS));
        JLabel labelContact = new JLabel("Contact");
        RoundTextField inputContact = new RoundTextField(50);
        inputContact.setText("Votre contact");
        inputContact.setBorderColor(new Color (253, 94, 9));
        inputContact.setMaximumSize(new Dimension(tailleFixe));
        groupContact.add(labelContact);
        groupContact.add(inputContact);
        groupContact.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel groupAdresse = new JPanel();
        groupAdresse.setLayout(new BoxLayout(groupAdresse, BoxLayout.Y_AXIS));
        JLabel labelAdresse = new JLabel("Adresse");
        RoundTextField inputAdresse = new RoundTextField(50);
        inputAdresse.setText("Votre Adresse");
        inputAdresse.setBorderColor(new Color (253, 94, 9));
        inputAdresse.setMaximumSize(new Dimension(tailleFixe));
        groupAdresse.add(labelAdresse);
        groupAdresse.add(inputAdresse);
        groupAdresse.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        
        JPanel groupSubmit = new JPanel();
        groupSubmit.setLayout(new BoxLayout(groupSubmit, BoxLayout.Y_AXIS));
        RoundTextField btnSubmit = new RoundTextField(50);
        btnSubmit.setText("S'inscrire !");
        btnSubmit.setEditable(false);
        btnSubmit.setHorizontalAlignment(JTextField.CENTER);
        btnSubmit.setBackground(new Color(253, 94, 9));
        btnSubmit.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setMaximumSize(new Dimension(tailleFixe));
        btnSubmit.setAlignmentX(Component.LEFT_ALIGNMENT);
        groupSubmit.add(btnSubmit);
        groupSubmit.setMaximumSize(tailleFixe);
        
        
        
        
        groupNom.setAlignmentX(Component.RIGHT_ALIGNMENT);
        groupPrenom.setAlignmentX(Component.RIGHT_ALIGNMENT);
        groupContact.setAlignmentX(Component.RIGHT_ALIGNMENT);
        groupAdresse.setAlignmentX(Component.RIGHT_ALIGNMENT);
        groupSubmit.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        labelNom.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputNom.setAlignmentX(Component.LEFT_ALIGNMENT);

        labelPrenom.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputPrenom.setAlignmentX(Component.LEFT_ALIGNMENT);

        labelContact.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputContact.setAlignmentX(Component.LEFT_ALIGNMENT);

        labelAdresse.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputAdresse.setAlignmentX(Component.LEFT_ALIGNMENT);


        
        groupNom.setOpaque(false);
        groupPrenom.setOpaque(false);
        groupContact.setOpaque(false);
        groupAdresse.setOpaque(false);
        groupSubmit.setOpaque(false);
        
        
        formPanel.setPreferredSize(new Dimension(900, 400));
        formPanel.setMaximumSize(new Dimension(900, 600));
        
        
        //panel de gsuche avec logo 
        JPanel panelLeft = new JPanel( );
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
        java.net.URL logoUrl = getClass().getResource("./ressources/images/logo.png");
        ImageIcon logoIcon = new ImageIcon (logoUrl);
        Image imgLogo = logoIcon.getImage().getScaledInstance(170, 170, java.awt.Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(imgLogo);
        JLabel labelLogo = new JLabel();
        labelLogo.setIcon(logoIcon);
        
        JLabel textAccroche = new JLabel("Dénichez vos coup de coeur a meilleur prix");
        
        panelLeft.add(labelTitre);
        panelLeft.add(labelLogo);
        panelLeft.add(textAccroche);
        panelLeft.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelLeft.setOpaque(false);
        
        
        formPanel.add(panelLeft); 
        JPanel ensembleInput = new JPanel();
        ensembleInput.setLayout(new BoxLayout(ensembleInput, BoxLayout.Y_AXIS));
        ensembleInput.setOpaque(false);
        ensembleInput.add(groupNom);
        ensembleInput.add(groupPrenom);
        ensembleInput.add(groupContact);
        ensembleInput.add(groupAdresse);
        ensembleInput.add(groupSubmit);
        formPanel.add(ensembleInput);
        
       wrapperCenter.add(formPanel);
       
       panelPrincipal.add(wrapperCenter, BorderLayout.CENTER);

    }
    public JPanel getPanelPrincipal(){
        return panelPrincipal;
    }
    
    
}
