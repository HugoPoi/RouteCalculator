package fr.esgi.routecalculator.ihm;

import java.awt.Checkbox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;

import fr.esgi.routecalculator.gtfscalculator.Graph;
import fr.esgi.routecalculator.ihm.FenetreVue;
import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDateComponent;

public class FenetreController implements ActionListener {

	
	private FenetreVue vue;
	private Graph model;
	
	public FenetreController(FenetreVue vue, Graph model){
		this.vue = vue;
		this.model = model;
	}

	public void actionPerformed(ActionEvent e){
		//TODO
		if(e.getSource().equals(vue.recherche)){
			/*
			 * Les variables à  utiliser dans les recherches
			 */
			Calendar selectedCalendar = (Calendar) ((JDateComponent) vue.choixdate).getModel().getValue();
			Date date=(selectedCalendar==null?null:selectedCalendar.getTime());
			String arrive = vue.arrive.getText();
			String depart = vue.depart.getText();
			String etat = (String) vue.choix1.getSelectedItem();
			String heure = (String) vue.choix2.getSelectedItem();
			String minute = (String) vue.choix3.getSelectedItem();
			String mode = vue.groupe.getSelectedCheckbox().getLabel();
			String criteres = vue.groupe1.getSelectedCheckbox().getLabel();
			Date now = new Date();
			
			String table = "gtfs_agencies",name ="Name"; 
			// String requete = "select id FROM gtfs_agencies WHERE name=arrive"
			//TODO
			//entreeValide(String val, String colonne, String table, Connection c)
			
			/*
			 * Name est la colonne comportant les noms des stations si je ne me trompe
			 * TABLE est le nom de la table, ici gtfs_agencies si je ne me trompe
			 * CN la connexion à la bdd
			 * TODO
			 */
			if(entreeValide(depart,name,table,CN)){
				if(entreeValide(arrive,name,table,CN)){
					if(date.before(now) || date.equals(now)){
						//TODO
						//implémenté la methode de recherche
					}
					else{
						vue.erreur("La date est erronnée");
					}
				}
				else{
					vue.erreur("L'arrivée est erronnée");
				}
			}
			else{
				vue.erreur("Le depart est erronnée");
			}
			
			
			if(date.before(new Date()) || date.equals(new Date())){
				vue.erreur("Le choix de la date est erronnée");
			} else{
				/*
				 * Recuperation des info entrées dans l'IHM
				 */


			
			/*
			 * Faire la recherche du chemin
			 */
			//model.findRoute(depart, arrive);
			
			
			/*
			 * TODO
			 * Recherche en prenant en compte la date et l'heure
			 */
			
			/*
			 * requete de verification de la station
			 */
				
			/*
			 * import java.util.Date
			 * Date now = new Date()
			 * 
			 * static DateFormat = new SimpleDateFormat("yyyy-MM-dd)
			 * */
			
			//System.out.println(now+" "+date+" "+ mode + " "+etat+" "+heure+" "+minute);
			
			}
			//Date date = vue.choixdate ;
			//String date = vue.choixdate.getModel;

			//requete SQL
			/* public void lancerRequete()
			  try
			  {
			    Class.forName("com.mysql.jdbc.Driver" );                                               //Initialisation du pilote de base de données
			    Connection con = DriverManager.getConnection("jdbc:mysql://database", "user", "pwd" ); //Connexion à ta base de données
			    Statement instruction = con.createStatement();                                         //Création du statement
			 
			    String maRequete = "select designation_produit, type_produit FROM produit WHERE ref_produit 5";                                                                 // Entre ta requete ici
			    ResultSet resultat = instruction.executeQuery(maRequete);                              //Exécution de ta requete avec retour de resultat
			    
			    //on peut mettre toutes ces variables comme des String
			    String premier ="";
			    String suivant ="";
			    int dureeTrajet;
			    Date heures; 
			    
			    while (resultat.next()){
			    	premier+=resultat.getString("on met ici ce qu'on recupere du tableau")+" ";
			    	suivant+=resutlat.getString("on met....") + " ";
			    	dureeTrajet += resultat.getInt("....") + " ";
			    	heure += resultat.getDate("") + " ";
			    }
			    //On affiche dans un option pane
				JOptionPane.SwowMessageDialog(null, +premier+ " "+suivant+" "+dureeTrajet+" "+heure);
			  }
			  catch(ClassNotFoundException ex){
			  	J
			  }
			 */
			
			//je lance la fonction
			

		}
		else if (e.getSource().equals(vue.mntmNouvelleRecherche)){	
			JOptionPane jop = new JOptionPane();
			int option = jop.showConfirmDialog(null, "Voulez-vous effectuer une nouvelle recherche ?","Nouvelle recherche",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(option == JOptionPane.OK_OPTION){
//				vue.arrive.set
//				System.exit(0);
				FenetreVue fenetre = new FenetreVue(null);
				fenetre.setVisible(true);
			}
		}
		
		else if (e.getSource().equals(vue.mntmQuitter)){
			JOptionPane jop = new JOptionPane();
			int option = jop.showConfirmDialog(null, "Voulez-vous vraiment fermer l'application ?","Fermeture de l'application",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(option == JOptionPane.OK_OPTION){
				System.exit(0);
			}
		}
		
		else if (e.getSource().equals(vue.mntmVersion)){
			JOptionPane jop1 = new JOptionPane();
			jop1.showMessageDialog(null, "Version 1.0", "Information", JOptionPane.INFORMATION_MESSAGE);
		}

		else if (e.getSource().equals(vue.mntmDveloppeurs)){
		JOptionPane dialogue;
		Object[] dev = {"Alexandre DUBOIS","Hugo POISSONNET","Idriss SANI","Mike FIARI"};
		dialogue = new JOptionPane(dev,JOptionPane.OK_OPTION );
		}
		
	}

	
}
