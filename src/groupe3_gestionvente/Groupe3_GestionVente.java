/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package groupe3_gestionvente;

import InterfaceIHM.MainFrame;
import InterfaceIHM.InterfaceClient;
import InterfaceIHM.FormInscription;
import InterfaceIHM.FormulaireConnexion;
import InterfaceIHM.AdminForm;
import javax.swing.*;

/**
 *
 * @author Héloïse
 */
public class Groupe3_GestionVente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MainFrame app = new MainFrame();
        JPanel container = app.getContainer();
        
        
        InterfaceClient intClient = new InterfaceClient();
        FormInscription formInscription = new FormInscription();
        FormulaireConnexion formConnexion = new FormulaireConnexion();
        AdminForm formAdmin = new AdminForm();
        
        container.add(formAdmin.getPanelPrincipal());
        
        //container.add(formConnexion.getPanelPrincipal());
        //container.add(formInscription.getPanelPrincipal());
        //container.add(intClient);
        app.setVisible(true);
        
    }
    
}
