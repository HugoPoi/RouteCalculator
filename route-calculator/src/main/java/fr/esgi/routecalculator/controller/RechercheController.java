/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.esgi.routecalculator.controller;

import fr.esgi.routecalculator.gtfscalculator.Graph;
import fr.esgi.routecalculator.gtfscalculator.PathGtfsImpl;
import fr.esgi.routecalculator.ihm.Fenetre;
import fr.esgi.routecalculator.ihm.VueResultat;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author mike
 */
public class RechercheController {
    
    private Fenetre fenetre;
    private String depart;
    private String arrive;
    private int choixMode;
    private PropertyChangeSupport pcsControlleurVue = new PropertyChangeSupport(this);
    
    public RechercheController (Fenetre fenetre) {
        this.fenetre = fenetre;
    }
    
    public void init () {
        this.pcsControlleurVue.firePropertyChange("init", null, null);
        do {
            this.pcsControlleurVue.firePropertyChange("lancerRecherche", null, null);
            while (!this.routeValide()) {
                this.pcsControlleurVue.firePropertyChange("routeInvalide", null, null);
            }
            this.pcsControlleurVue.firePropertyChange("calculeEnCours", null, null);
            this.findRoute();
        } while (true);
    }
    
    public void setRoute (String depart, String arrive, int choixMode) {
        this.depart = depart;
        this.arrive = arrive;
        this.choixMode = choixMode;
    }
    
    private boolean routeValide () {
        return true;
    }
    
    private void findRoute () {
        Graph premierGraph = new Graph();
        System.out.println("depart : " + this.depart);
        System.out.println("arrive : " + this.arrive);
        System.out.println("mode : " + this.choixMode);
	PathGtfsImpl selectedRoute = premierGraph.findRoute(this.depart, this.arrive);
        this.pcsControlleurVue.firePropertyChange("calculeTerminer", null, null);
        this.fenetre.ajouterPanel(new VueResultat(selectedRoute));
    }
    
    public void ajouterEcouteur(PropertyChangeListener ecouteur) {
        this.pcsControlleurVue.addPropertyChangeListener(ecouteur);
    }
    
}
