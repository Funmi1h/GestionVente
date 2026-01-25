/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package groupe3_gestionvente;

import InterfaceIHM.MainFrame;
import InterfaceIHM.InterfaceClient;

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
        InterfaceClient intClient = new InterfaceClient();
        app.add(intClient);
        app.setVisible(true);
        
    }
    
}
