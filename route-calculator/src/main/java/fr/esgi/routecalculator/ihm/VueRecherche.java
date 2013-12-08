/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.esgi.routecalculator.ihm;

import fr.esgi.routecalculator.controller.RechercheController;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import net.sourceforge.jdatepicker.JDateComponentFactory;

/**
 *
 * @author mike
 */
public class VueRecherche extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private RechercheController controller;
    //declaration des variables
    public JMenuItem mntmNouvelleRecherche, mntmDveloppeurs;
    public JMenuItem mntmQuitter, mntmCopier, mntmColler, mntmSupprimer, mntmVersion, mntmToutSlctionner;
    private JLabel Depart, Arrive;
    public JTextField depart, arrive;
    public JPanel panel1, panel2, panel3, partie, choixdate;
    public JComboBox<String> choix1, choix2, choix3;
    public CheckboxGroup groupe1, groupe;
    public JButton recherche;
    private Border border1, border2, border3;
    public JTable resultat;
    public JScrollPane scroller;
    
    public synchronized void attendre () {
        try {
            this.wait(0);
        } catch (InterruptedException ex) {
            Logger.getLogger(VueRecherche.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public synchronized void reprendre () {
        this.notify();
    }

    public VueRecherche(RechercheController controller) {
        super(new GridBagLayout());
        
        this.controller = controller;
        this.controller.ajouterEcouteur(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                switch (evt.getPropertyName()) {
                    case "init":
                        init();
                        break;
                    case "debutJeu":
                        break;
                }
                attendre();
            }
        });

    }
    
    private void init () {
        //label depart
        Depart = new JLabel("Départ");
        GridBagConstraints g_Depart = new GridBagConstraints();
        g_Depart.anchor = GridBagConstraints.WEST;
        g_Depart.insets = new Insets(0, 0, 5, 5);
        g_Depart.gridx = 3;
        g_Depart.gridy = 1;
        this.add(Depart, g_Depart);

        //label arrivee
        Arrive = new JLabel("Arriv\u00E9e");
        GridBagConstraints g_Arrive = new GridBagConstraints();
        g_Arrive.anchor = GridBagConstraints.WEST;
        g_Arrive.insets = new Insets(0, 0, 5, 5);
        g_Arrive.gridx = 6;
        g_Arrive.gridy = 1;
        this.add(Arrive, g_Arrive);

        // texte arrivee
        depart = new JTextField();
        GridBagConstraints g_arrive = new GridBagConstraints();
        g_arrive.insets = new Insets(0, 0, 0, 5);
        g_arrive.anchor = GridBagConstraints.WEST;
        g_arrive.fill = GridBagConstraints.HORIZONTAL;
        g_arrive.gridx = 3;
        g_arrive.gridy = 2;
        this.add(depart, g_arrive);
        depart.setColumns(10);

        //texte depart
        arrive = new JTextField();
        GridBagConstraints g_depart = new GridBagConstraints();
        g_depart.insets = new Insets(0, 0, 0, 5);
        g_depart.anchor = GridBagConstraints.WEST;
        g_depart.fill = GridBagConstraints.HORIZONTAL;
        g_depart.gridx = 6;
        g_depart.gridy = 2;
        this.add(arrive, g_depart);
        arrive.setColumns(10);

        //implentation heure
        //panel1 = new JPanel(new GridLayout(0,1));
        JPanel panelx = new JPanel();
        JPanel panelx1 = new JPanel();
        panel1 = new JPanel(new GridLayout(2, 1));
        border1 = BorderFactory.createTitledBorder("Heure");
        panel1.setBorder(border1);

        choix1 = new JComboBox<>(new String[]{
            "Départ à", "Arrivée à"
        });
        choix1.setPreferredSize(new Dimension(100, 30));

        choix2 = new JComboBox<>(new String[]{
            "00h", "01h", "02h", "03h", "04h", "05h", "06h", "07h", "08h", "09h", "10h", "11h", "12h", "13h", "14h", "15h", "16h", "17h", "18h", "19h", "20h", "21h", "22h", "23h"
        });
        choix2.setPreferredSize(new Dimension(100, 30));

        choix3 = new JComboBox<>(new String[]{
            "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"
        });
        choix3.setPreferredSize(new Dimension(100, 30));

        panelx.add(choix1);
        panelx1.add(choix2);
        panelx1.add(choix3);
        panel1.add(panelx);
        panel1.add(panelx1);

        GridBagConstraints g_panel1 = new GridBagConstraints();
        g_panel1.insets = new Insets(0, 0, 0, 5);
        g_panel1.fill = GridBagConstraints.HORIZONTAL;
        g_panel1.gridx = 3;
        g_panel1.gridy = 3;
        this.add(panel1, g_panel1);

        //implementation date
        choixdate = (JPanel) JDateComponentFactory.createJDatePicker();
        //choixdate = new JDateChooser();
        GridBagConstraints g_choixdate = new GridBagConstraints();
        g_choixdate.anchor = GridBagConstraints.WEST;
        g_choixdate.insets = new Insets(0, 0, 5, 5);
        g_choixdate.gridx = 6;
        g_choixdate.gridy = 3;
//		g_choixdate.gridwidth = 1;
        this.add(choixdate, g_choixdate);

        //les modes
        panel2 = new JPanel(new GridLayout(0, 1));
        border2 = BorderFactory.createTitledBorder("Mode");
        panel2.setBorder(border2);

        groupe = new CheckboxGroup();
        Checkbox box1 = new Checkbox("Tous", groupe, true);
        Checkbox box2 = new Checkbox("Metro, RER", groupe, false);
        Checkbox box3 = new Checkbox("Tramway, Bus", groupe, false);
        panel2.add(box1);
        panel2.add(box2);
        panel2.add(box3);

        GridBagConstraints g_panel2 = new GridBagConstraints();
        g_panel2.insets = new Insets(0, 0, 0, 5);
        g_panel2.fill = GridBagConstraints.HORIZONTAL;
        g_panel2.gridx = 3;
        g_panel2.gridy = 4;
        this.add(panel2, g_panel2);


        //les criteres
        panel3 = new JPanel(new GridLayout(0, 1));
        border3 = BorderFactory.createTitledBorder("Critères");
        panel3.setBorder(border3);

        groupe1 = new CheckboxGroup();
        Checkbox box_1 = new Checkbox("Plus rapide", groupe1, true);
        Checkbox box_2 = new Checkbox("Moins de correspondance", groupe1, false);
        panel3.add(box_1);
        panel3.add(box_2);

        GridBagConstraints g_panel3 = new GridBagConstraints();
        g_panel3.insets = new Insets(0, 0, 0, 5);
        g_panel3.fill = GridBagConstraints.HORIZONTAL;
        g_panel3.gridx = 6;
        g_panel3.gridy = 4;
        g_panel3.insets = new Insets(5, 0, 0, 0);
        this.add(panel3, g_panel3);

        // bouton recherche
        recherche = new JButton("Rechercher");
        recherche.setForeground(Color.BLUE);
        recherche.addActionListener(new BoutonRecherche());
        //	recherche.addMouseListener(controller);
        GridBagConstraints g_recherche = new GridBagConstraints();
        g_recherche.anchor = GridBagConstraints.CENTER;
        g_recherche.insets = new Insets(0, 20, 0, 0);
        g_recherche.gridx = 6;
        g_recherche.gridy = 5;
        this.add(recherche, g_recherche);

        //######################################################
        //########### Disposition ##############################
        //######################################################
        this.repaint();
        this.validate();
    }
    
    private class BoutonRecherche implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent event) {
            int choixTrajet = 1;
            String name = groupe.getSelectedCheckbox().getLabel();
            switch (name) {
                case "Tous":
                    choixTrajet = 1;
                    break;
                case "Metro, RER":
                    choixTrajet = 2;
                    break;
                case "Tramway, Bus":
                    choixTrajet = 3;
                    break;
            }
            controller.setRoute(depart.getText(), arrive.getText(), choixTrajet);
            reprendre();
        }
    }
}