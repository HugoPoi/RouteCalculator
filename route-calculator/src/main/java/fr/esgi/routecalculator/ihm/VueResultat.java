/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.esgi.routecalculator.ihm;

import fr.esgi.routecalculator.gtfscalculator.PathGtfsImpl;
import fr.esgi.routecalculator.ihm.image.Image;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author mike
 */
public class VueResultat extends JPanel {
    
    public VueResultat (PathGtfsImpl selectedRoute) {
        Box contener = Box.createVerticalBox();
        contener.add(new JLabel("Le plus rapide : 45 min"));
        JPanel panelInfo = new JPanel(new GridLayout(1, 4));
        panelInfo.setPreferredSize(new Dimension(600, 100));
        panelInfo.add(new JLabel("Départ : 9h03"));
        panelInfo.add(new JLabel("Durée total : 45min"));
        panelInfo.add(new JLabel("Zones : 1-2"));
        panelInfo.add(new JLabel("Arrivée : 9h48"));
        contener.add(panelInfo);
        Image image = new Image();
        Box cheminGraphique = Box.createHorizontalBox();
        //cheminGraphique.add(new PanelImage(image.getImage("ico_flag_depart")));
        //cheminGraphique.add(new PanelImage(image.getImage("pieton")));
        cheminGraphique.add(new Panneau(70, 20,8));
        //cheminGraphique.add(new PanelImage(image.getImage("metro")));
        //cheminGraphique.add(new PanelImage(image.getImage("5")));
        cheminGraphique.add(new Panneau(70, 20));
        //cheminGraphique.add(new PanelImage(image.getImage("metro")));
        //cheminGraphique.add(new PanelImage(image.getImage("2")));
        cheminGraphique.add(new Panneau(70, 20));
        //cheminGraphique.add(new PanelImage(image.getImage("metro")));
        //cheminGraphique.add(new PanelImage(image.getImage("12")));
        cheminGraphique.add(new Panneau(70, 20));
        //cheminGraphique.add(new PanelImage(image.getImage("pieton-cercle")));
        cheminGraphique.add(new Panneau(70, 20,8));
        //cheminGraphique.add(new PanelImage(image.getImage("ico_flag_arrivee")));
        contener.add(cheminGraphique);
        
        String[] ligne1 = {};
        String[] ligne2 = {"depuis Bobigny-Pablo-Picasso", "direction Place d'Italie", "jusqu’à Stalingrad"};
        
        /*Object[][] donnees = {
                {new PanelImage(image.getImage("ico_flag_depart")), new PanelDeTexte("aller jusqu’à Bobigny-Pablo-Picasso – Métro"), "9h03", "3 min"},
                {new PanelImage(image.getImage("5")), new PanelDeTexte(ligne2), "9h20", "14 min"},
                {null, new PanelDeTexte("correspondance"), null, "2 min"},
                {new PanelImage(image.getImage("2")), null, "9h30", "5 min"},
                {null, new PanelDeTexte("correspondance"), null, "2 min"},
                {new PanelImage(image.getImage("12")), null, "9h45", "12 min"},
                {new PanelImage(image.getImage("ico_flag_arrivee")), new PanelDeTexte(" aller jusqu’à Rue Du Bac (METRO), Paris"), null, "3 min"},
        };*/
        Object[][] donnees = {
                {null, new PanelDeTexte("aller jusqu’à Bobigny-Pablo-Picasso – Métro"), "9h03", "3 min"},
                {null, new PanelDeTexte(ligne2), "9h20", "14 min"},
                {null, new PanelDeTexte("correspondance"), null, "2 min"},
                {null, null, "9h30", "5 min"},
                {null, new PanelDeTexte("correspondance"), null, "2 min"},
                {null, null, "9h45", "12 min"},
                {null, new PanelDeTexte(" aller jusqu’à Rue Du Bac (METRO), Paris"), null, "3 min"},
        };
        String[] entetes = {"", "", "Horraire", "Durée"};
 
        
        MyTableModel dtm = new MyTableModel(donnees, entetes);
        JTable chemin = new JTable(dtm);
        chemin.setRowHeight(60);
        chemin.getColumnModel().getColumn(0).setPreferredWidth(70);
        chemin.getColumnModel().getColumn(1).setPreferredWidth(300);
        chemin.getColumnModel().getColumn(2).setPreferredWidth(70);
        chemin.getColumnModel().getColumn(3).setPreferredWidth(70);
        chemin.setDefaultRenderer(PanelImage.class, new PolicyTableComponent());
        chemin.setDefaultRenderer(PanelDeTexte.class, new PolicyTableComponent());
        JScrollPane scrollPane = new JScrollPane(chemin);
        JPanel panelResultat = new JPanel();
        panelResultat.setPreferredSize(new Dimension(900, 350));
        panelResultat.add(scrollPane);
        contener.add(panelResultat);
        this.add(contener);
    }
    
    public class MyTableModel extends AbstractTableModel {
        
        private Object[][] donnees;
        private String[] entetes;
        
        public MyTableModel (Object[][] donnees, String[] entetes) {
            this.donnees = donnees;
            this.entetes = entetes;
        }
    
        @Override
        public Class getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0 :
                    return PanelImage.class;
                case 1 :
                    return PanelDeTexte.class;
                case 2 :
                    return String.class;
                case 3 :
                    return String.class;
                default :
                    return Object.class;
            }
        }
        
        @Override
        public int getColumnCount() {
            return 4;
        }
        
        @Override
        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 2 :
                    return "Horraire";
                case 3 :
                    return "Durée";
                default :
                    return "";
            }
        }
        
        @Override
        public int getRowCount() { 
            return 8;
        }
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return this.donnees[rowIndex][columnIndex];
        }
        
        @Override
        public boolean isCellEditable(int columnIndex, int rowIndex) {
            return false;
        }
    }
    
    public class PolicyTableComponent implements TableCellRenderer {
 
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof PanelImage) {
                return (PanelImage)value;
            } else if (value instanceof PanelDeTexte) {
                return (PanelDeTexte)value;
            }
            return null;
        }
    }
    
    public class Panneau extends JPanel {
        private int width;
        private int height;
        private int nbPointille;
        
        public Panneau (int width, int height) {
            this.width = width;
            this.height = height;
            this.nbPointille = 1;
        }
        
        public Panneau (int width, int height, int nbPointille) {
            this.width = width;
            this.height = height;
            this.nbPointille = nbPointille;
        }
        
        @Override
        public void paintComponent(Graphics g){
            //x1, y1, width, height
            int espace = 0;
            for (int i = 0 ; i < this.nbPointille ; i++) {
                g.fillRect(i*(this.width/this.nbPointille)+espace, this.height/4, this.width/this.nbPointille, this.height/2);
                espace++;
            }
            this.setPreferredSize(new Dimension(this.width, this.height));
        }               
    }
}
