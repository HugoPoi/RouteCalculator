/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.esgi.routecalculator;

import fr.esgi.routecalculator.controller.RechercheController;
import fr.esgi.routecalculator.ihm.Fenetre;
import fr.esgi.routecalculator.ihm.VueRecherche;

/**
 *
 * @author mike
 */
public class Main {

    public static void main(String[] arg) {
        Fenetre fenetre = new Fenetre("RATP");
        RechercheController controller = new RechercheController(fenetre);
        fenetre.ajouterPanel(new VueRecherche(controller));
        controller.init();
    }
}
