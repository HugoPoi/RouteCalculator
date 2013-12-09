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
import java.util.List;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import org.onebusaway.gtfs.model.StopTime;

/**
 *
 * @author mike
 */
public class VueResultat extends JPanel {
    
    public VueResultat (PathGtfsImpl selectedRoute) {
        String departureTime = this.toTime(selectedRoute.getDepartureTime());
        String totalTime = this.toTime(selectedRoute.getDepartureTime() + selectedRoute.getTotalTime());
        String dureeTime = this.toTime(selectedRoute.getTotalTime());
        Box contener = Box.createVerticalBox();
        contener.add(new JLabel("Le plus rapide : "+dureeTime));
        JPanel panelInfo = new JPanel(new GridLayout(1, 4));
        panelInfo.setPreferredSize(new Dimension(600, 100));
        panelInfo.add(new JLabel("Départ : "+departureTime));
        panelInfo.add(new JLabel("Durée total : "+dureeTime));
        panelInfo.add(new JLabel("Zones : 1-2"));
        panelInfo.add(new JLabel("Arrivée : "+totalTime));
        contener.add(panelInfo);
        Image image = new Image();
        Box cheminGraphique = Box.createHorizontalBox();
        
        cheminGraphique.add(new JLabel("départ"));
        cheminGraphique.add(new Panneau(70, 20,8));
        List<StopTime> stopTimes = selectedRoute.getTrips();
        for (int i = 0 ; i < stopTimes.size() ; i++) {
            int routeType = stopTimes.get(i).getTrip().getRoute().getType();
            switch (routeType) {
                case 1:
                    cheminGraphique.add(new JLabel("metro"));
                    break;
            }
            cheminGraphique.add(new JLabel(stopTimes.get(i).getTrip().getRoute().getShortName()));
            if (i+1 < stopTimes.size()) {
                cheminGraphique.add(new Panneau(70, 20));
            }
        }
        cheminGraphique.add(new Panneau(70, 20,8));
        cheminGraphique.add(new JLabel("arrivé"));
        contener.add(cheminGraphique);
        
        String[] ligne2 = {"depuis " + selectedRoute.getDepartStation(), "direction ", "jusqu’à " + stopTimes.get(0).getStop().getName()};
        
        Object[][] donnees = new Object[stopTimes.size()*2][4];
        donnees[0][0] = null;
        donnees[0][1] = new PanelDeTexte("aller jusqu’à " + selectedRoute.getDepartStation());
        donnees[0][2] = departureTime;
        donnees[0][3] = "0 min";
        
        donnees[1][0] = "metro"+stopTimes.get(0).getTrip().getRoute().getShortName();
        donnees[1][1] = new PanelDeTexte(ligne2);
        donnees[1][2] = this.toTime(stopTimes.get(0).getArrivalTime());
        donnees[1][3] = this.toTime(stopTimes.get(0).getArrivalTime() - selectedRoute.getDepartureTime());
        
        for (int i = 1 ; i < stopTimes.size() ; i++) {
            donnees[i*2][0] = null;
            donnees[i*2][1] = new PanelDeTexte("correspondance");
            donnees[i*2][2] = null;
            donnees[i*2][3] = "0 min";
            
            donnees[(i*2)+1][0] = "metro"+stopTimes.get(i).getTrip().getRoute().getShortName();
            donnees[(i*2)+1][1] = new PanelDeTexte(ligne2);
            donnees[(i*2)+1][2] = this.toTime(stopTimes.get(i).getArrivalTime());
            donnees[(i*2)+1][3] = this.toTime(stopTimes.get(i).getArrivalTime() - selectedRoute.getDepartureTime());
        }
        String[] entetes = {"", "", "Horraire", "Durée"};
 
        
        MyTableModel dtm = new MyTableModel(donnees, entetes);
        JTable chemin = new JTable(dtm);
        chemin.setRowHeight(60);
        chemin.getColumnModel().getColumn(0).setPreferredWidth(70);
        chemin.getColumnModel().getColumn(1).setPreferredWidth(300);
        chemin.getColumnModel().getColumn(2).setPreferredWidth(100);
        chemin.getColumnModel().getColumn(3).setPreferredWidth(70);
        chemin.setDefaultRenderer(PanelImage.class, new PolicyTableComponent());
        chemin.setDefaultRenderer(PanelDeTexte.class, new PolicyTableComponent());
        JScrollPane scrollPane = new JScrollPane(chemin);
        JPanel panelResultat = new JPanel();
        panelResultat.setPreferredSize(new Dimension(700, 350));
        panelResultat.add(scrollPane);
        contener.add(panelResultat);
        this.add(contener);
    }
    
    private String toTime (int time) {
        String stringTime;
        int min = time /60;
        if (min >= 60) {
            stringTime = (min/60) +"h " + (min %60) +" min";
        } else {
            stringTime = min +" min";
        }
        return stringTime;
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
                    return Object.class;
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
            return donnees.length;
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
