/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InterfaceIHM;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Héloïse
 */
public class FormAddFournisseur extends JPanel {
    private JPanel panelPrincipal;
    
    public FormAddFournisseur(){
        Dimension tailleFixe = new Dimension(800, 50);
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        JLabel labelTitre = new JLabel("Ajouter un fournisseur");
       
        JPanel groupNom = new JPanel();
        groupNom.setLayout(new BoxLayout(groupNom, BoxLayout.Y_AXIS ));
        JLabel labelNom = new JLabel("Nom");
        RoundTextField inputNom = new RoundTextField(50);
        inputNom.setText("Nom du fournisseur");
        inputNom.setBorderColor(new Color (253, 94, 9));
        inputNom.setMaximumSize(tailleFixe);
        groupNom.add(labelNom);
        groupNom.add(inputNom);
        groupNom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        groupNom.setMaximumSize(new Dimension(tailleFixe));
        groupNom.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        JPanel groupPrenom = new JPanel();
        groupPrenom.setLayout(new BoxLayout(groupPrenom, BoxLayout.Y_AXIS));
        JLabel labelPrenom = new JLabel("Prénom");
        RoundTextField inputPrenom = new RoundTextField(50);
        inputPrenom.setText("Prénom du fournisseur");
        inputPrenom.setBorderColor(new Color (253, 94, 9));
        inputPrenom.setMaximumSize(new Dimension(tailleFixe));
        groupPrenom.add(labelPrenom);
        groupPrenom.add(inputPrenom);
        groupPrenom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        groupPrenom.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        
        JPanel groupContact = new JPanel();
        groupContact.setLayout(new BoxLayout(groupContact, BoxLayout.Y_AXIS));
        JLabel labelContact = new JLabel("Contact");
        RoundTextField inputContact = new RoundTextField(50);
        inputContact.setText("Contact du fournisseur");
        inputContact.setBorderColor(new Color (253, 94, 9));
        inputContact.setMaximumSize(new Dimension(tailleFixe));
        groupContact.add(labelContact);
        groupContact.add(inputContact);
        groupContact.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        groupContact.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        JPanel groupAdresse = new JPanel();
        groupAdresse.setLayout(new BoxLayout(groupAdresse, BoxLayout.Y_AXIS));
        JLabel labelAdresse = new JLabel("Adresse");
        RoundTextField inputAdresse = new RoundTextField(50);
        inputAdresse.setText("Adresse du fournisseur");
        inputAdresse.setBorderColor(new Color (253, 94, 9));
        inputAdresse.setMaximumSize(new Dimension(tailleFixe));
        groupAdresse.add(labelAdresse);
        groupAdresse.add(inputAdresse);
        groupAdresse.setMaximumSize(new Dimension(tailleFixe));
        groupAdresse.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        groupAdresse.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        
        JPanel groupSubmit = new JPanel();
        RoundedButton btnSubmit = new RoundedButton(30);
        btnSubmit.setText("Ajouter un fournisseur");
        btnSubmit.setBackground(new Color(253, 94, 9));
        btnSubmit.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setMaximumSize(new Dimension(tailleFixe));
        btnSubmit.setAlignmentX(Component.RIGHT_ALIGNMENT);
        groupSubmit.add(btnSubmit);
        groupSubmit.setMaximumSize(tailleFixe);
       
        
        panelPrincipal.add(labelTitre);
        panelPrincipal.add(groupNom);
        panelPrincipal.add(groupPrenom);
        panelPrincipal.add(groupContact);
        panelPrincipal.add(groupAdresse);
        panelPrincipal.add(groupSubmit);
        
       
    }
    public JPanel getPanelPrincipal(){
        return panelPrincipal;
    }
    
    
}
