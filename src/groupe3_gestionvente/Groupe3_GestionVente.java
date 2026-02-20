/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package groupe3_gestionvente;

import InterfaceIHM.MainFrame;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.UIManager;



/**
 *
 * @author Héloïse
 */
public class Groupe3_GestionVente {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("flatlaf.useNativeLibrary", "false");
        FlatLightLaf.setup();
        UIManager.put("List.selectionBackground", new Color(33,150,243,30));
        UIManager.put("ScrollBar.showButtons", false);
        UIManager.put("Component.arc", 15);
        UIManager.put("Button.arc", 20);
        UIManager.put("TextComponent.arc", 12);
        UIManager.put("Table.showHorizontalLines", false);
        UIManager.put("Table.showVerticalLines", false);
        UIManager.put("Table.intercellSpacing", new Dimension(0, 0));
        UIManager.put("Table.rowHeight", 42);
        UIManager.put("TableHeader.height", 38);
        UIManager.put("TextComponent.arc", 10);
        UIManager.put("List.selectionBackground", new Color(33,150,243,30));
        MainFrame app = new MainFrame();
        app.setVisible(true);
    }

    
}
