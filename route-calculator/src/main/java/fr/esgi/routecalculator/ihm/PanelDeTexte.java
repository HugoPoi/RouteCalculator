/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.esgi.routecalculator.ihm;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author mike
 */
public class PanelDeTexte extends JPanel {
    
    public PanelDeTexte(String texte) {
        super();
        this.add(new JLabel(texte));
    }
    
    public PanelDeTexte(String[] textes) {
        super(new GridLayout(textes.length, 1));
        for (int i = 0 ; i < textes.length ; i++) {
            this.add(new JLabel(textes[i]));
        }
    }
}
