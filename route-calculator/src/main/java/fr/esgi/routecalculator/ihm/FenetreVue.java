package fr.esgi.routecalculator.ihm;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.border.*;

//import controller.FenetreController;


import fr.esgi.routecalculator.gtfscalculator.Graph;
import fr.esgi.routecalculator.ihm.FenetreController;
import net.sourceforge.jdatepicker.JDateComponentFactory;

public class FenetreVue extends JFrame {	
	/**
	 * 
	 */
	
	/*
	 * import java.util.Date
	 * Date now = new Date()
	 * 
	 * static DateFormat = new SimpleDateFormat("yyyy-MM-dd)
	 * */
	private static final long serialVersionUID = 1L;
	private Graph        model; 
	private FenetreController    controller;
//	private Menu menu;
	
	//declaration des variables
	public JMenuBar menuBar;
	public JMenu mnFichier,mnEdition,mnPropos ;
	public JMenuItem mntmNouvelleRecherche, mntmDveloppeurs;
	public JMenuItem mntmQuitter, mntmCopier, mntmColler, mntmSupprimer, mntmVersion,mntmToutSlctionner ;


	private JLabel Depart,Arrive;
	public JTextField depart,arrive;
	public JPanel panel1, panel2,panel3,partie,choixdate;
	public JComboBox<String> choix1, choix2, choix3;
	public CheckboxGroup groupe1, groupe;
	public JButton recherche ;
	private Border border1,border2,border3;
	public JTable resultat;
	public JScrollPane scroller;
	
	
	/*
	 * Constructeur de Fenetre Vue
	 */
	public FenetreVue(Graph model){
		
			this.model = model;
			
			controller = new FenetreController(this,model);
	
		
			this.setTitle("ITINERAIRES IDF");
		   	this.setBackground(Color.GRAY);
		   	this.setSize(500,500);
		   	this.setLocationRelativeTo(null);
		   	this.setResizable(false);
		   	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	
	
		   	
		   	/*
		   	 * /set the Menu bar
		   	 */
		   	initMenu();
		   	
		   	
		   	
			/*
			 * /creation de la grille
			 */
		   	GridBagLayout repartiteur = new GridBagLayout();
			Container interieur = getContentPane();
			interieur.setLayout(repartiteur);
			
			
			
			/*
			 * /label depart
			 */
			Depart = new JLabel("Depart");
			GridBagConstraints g_Depart = new GridBagConstraints();
			g_Depart.anchor = GridBagConstraints.WEST;
			g_Depart.insets = new Insets(0, 0, 5, 5);
			g_Depart.gridx = 3;
			g_Depart.gridy = 1;
			this.getContentPane().add(Depart, g_Depart);
			
			
			
			/*
			 * /label arrivee
			 */
			Arrive = new JLabel("Arriv\u00E9e");
			GridBagConstraints g_Arrive = new GridBagConstraints();
			g_Arrive.anchor = GridBagConstraints.WEST;
			g_Arrive.insets = new Insets(0, 0, 5, 5);
			g_Arrive.gridx = 6;
			g_Arrive.gridy = 1;
			this.getContentPane().add(Arrive, g_Arrive);
	
			
			
			/*
			 * /texte depart
			 */
			depart = new JTextField();
			GridBagConstraints g_depart = new GridBagConstraints();
			g_depart.insets = new Insets(0, 0, 0, 5);
			g_depart.anchor = GridBagConstraints.WEST;
			g_depart.fill = GridBagConstraints.HORIZONTAL;
			g_depart.gridx = 6;
			g_depart.gridy = 2;
			this.getContentPane().add(depart, g_depart);
			depart.setColumns(10);
			
			
			/*
			 * / texte arrivee
			 */
			arrive = new JTextField();
			GridBagConstraints g_arrive = new GridBagConstraints();
			g_arrive.insets = new Insets(0, 0, 0, 5);
			g_arrive.anchor = GridBagConstraints.WEST;
			g_arrive.fill = GridBagConstraints.HORIZONTAL;
			g_arrive.gridx = 3;
			g_arrive.gridy = 2;
			this.getContentPane().add(arrive, g_arrive);
			arrive.setColumns(10);
			
			/*
			 * /implémentation heure
			 */
			JPanel panelx = new JPanel();
			JPanel panelx1 = new JPanel();
			panel1 = new JPanel(new GridLayout(2,1));
			border1 = BorderFactory.createTitledBorder("Heure");
			panel1.setBorder(border1);
	
			choix1 = new JComboBox<String>(new String[]
					{
						"Départ à","Arrivée à"
					});
			choix1.setPreferredSize(new Dimension(125, 30));
			
			choix2 = new JComboBox<String>(new String[]
					{
						"00h","01h","02h","03h","04h","05h","06h","07h","08h","09h","10h","11h","12h","13h","14h","15h","16h","17h","18h","19h","20h","21h","22h","23h"
					});
			choix2.setPreferredSize(new Dimension(100, 30));
		   
			choix3 = new JComboBox<String>(new String[]
		    		{
		    			"00","05","10","15","20","25","30","35","40","45","50","55"
		    		});
			choix3.setPreferredSize(new Dimension(100, 30));
		    
			panelx.add(choix1);
			panelx1.add(choix2);
			panelx1.add(choix3);
			panel1.add(panelx);
			panel1.add(panelx1);
			
			GridBagConstraints g_panel1 = new GridBagConstraints();
			g_panel1.insets = new Insets(0,0,0,5);
			g_panel1.fill = GridBagConstraints.HORIZONTAL;
			g_panel1.gridx = 3;
			g_panel1.gridy = 3;
			this.getContentPane().add(panel1,g_panel1);
	
			
			
			/*
			 * /implementation date
			 */
			choixdate = (JPanel) JDateComponentFactory.createJDatePicker() ;
			
			GridBagConstraints g_choixdate = new GridBagConstraints();
			g_choixdate.anchor = GridBagConstraints.WEST;
			g_choixdate.insets = new Insets(0, 0, 5, 5);
			g_choixdate.gridx = 6;
			g_choixdate.gridy = 3;
			this.getContentPane().add(choixdate, g_choixdate);
			
			
			/*
			 * /les modes
			 */
			panel2 = new JPanel(new GridLayout(0,1));
			border2 = BorderFactory.createTitledBorder("Mode");
			panel2.setBorder(border2);
			
			groupe = new CheckboxGroup();
			Checkbox box1 = new Checkbox("Tous",groupe,true);
			Checkbox box2 = new Checkbox("Metro, RER",groupe,false);
			Checkbox box3 = new Checkbox("Tramway, Bus",groupe,false);
			panel2.add(box1);
			panel2.add(box2);
			panel2.add(box3);
			
			GridBagConstraints g_panel2 = new GridBagConstraints();
			g_panel2.insets = new Insets(0,0,0,5);
			g_panel2.fill = GridBagConstraints.HORIZONTAL;
			g_panel2.gridx = 3;
			g_panel2.gridy = 4;
			this.getContentPane().add(panel2,g_panel2);
			
			
			/*
			 * /les criteres
			 */
			panel3 = new JPanel(new GridLayout(0,1));
			border3 = BorderFactory.createTitledBorder("Critères");
			panel3.setBorder(border3);
	
			groupe1 = new CheckboxGroup();
			Checkbox box_1 = new Checkbox("Plus rapide",groupe1,true);
			Checkbox box_2 = new Checkbox("Moins de correspondance",groupe1,false);
			panel3.add(box_1);
			panel3.add(box_2);
			
			GridBagConstraints g_panel3 = new GridBagConstraints();
			g_panel3.insets = new Insets(0,0,0,5);
			g_panel3.fill = GridBagConstraints.HORIZONTAL;
			g_panel3.gridx = 6;
			g_panel3.gridy = 4;
			g_panel3.insets = new Insets(5,0,0,0);
			this.getContentPane().add(panel3,g_panel3);
			
			/*
			 *  bouton recherche
			 */
			recherche = new JButton("Rechercher");
			recherche.setForeground(Color.BLUE);
			recherche.addActionListener(controller);
			GridBagConstraints g_recherche = new GridBagConstraints();
			g_recherche.anchor = GridBagConstraints.CENTER;
			g_recherche.insets = new Insets(0,20,0,0);
			g_recherche.gridx = 6;
			g_recherche.gridy = 5;
			this.getContentPane().add(recherche, g_recherche);
			
			/*tableau d'affichage des resultats
			resultat = new JTable();
			resultat.setShowGrid(true);
			resultat.setShowHorizontalLines(true);
			scroller = new JScrollPane(resultat);
			GridBagConstraints g_scroller = new GridBagConstraints();
			g_scroller.anchor = GridBagConstraints.CENTER;
			g_scroller.insets = new Insets(0,20,0,0);
			g_scroller.gridx = 3;
			g_scroller.gridy = 7;
			g_scroller.gridwidth = 5;
			this.getContentPane().add(scroller, g_scroller);
			scroller.setVisible(false);
			*/
	
			
		}

	
	
   	/*
   	 * Conception du menu
   	 */
	public void initMenu(){
		menuBar = new JMenuBar();
		
		mnFichier = new JMenu("Fichier");
		mnFichier.setMnemonic('F');
		menuBar.add(mnFichier);
		
		mntmNouvelleRecherche = new JMenuItem("Nouvelle recherche");
		/*
		 * lancer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_MASK));
		 *	animation.add(lancer);
		 */
		mntmNouvelleRecherche.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
		mntmNouvelleRecherche.addActionListener(controller);
		mnFichier.add(mntmNouvelleRecherche);
		
		mntmQuitter = new JMenuItem("Quitter");
		mntmQuitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));
		mntmQuitter.addActionListener(controller);
		mnFichier.add(mntmQuitter);
		
		mnPropos = new JMenu("\u00C0 Propos");
		mnPropos.setMnemonic('P');
		menuBar.add(mnPropos);
		
		mntmVersion = new JMenuItem("Version");
		mntmVersion.addActionListener(controller);
		mntmVersion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
		mnPropos.add(mntmVersion);
		
		mntmDveloppeurs = new JMenuItem("D\u00E9veloppeurs");
		mntmDveloppeurs.addActionListener(controller);
		mntmDveloppeurs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_MASK));
		mnPropos.add(mntmDveloppeurs);
	
		this.setJMenuBar(menuBar);	
	}
	
	
	/*
	 * Tester la validité des entrées
	 */
	public boolean entreeValide(){
		return rootPaneCheckingEnabled;
	}
	
	
	/*
	 * Affichage erreur sur la validité des entrees
	 */
	public void erreur(String a){
		JOptionPane.showMessageDialog(this,a,"Alerte !",JOptionPane.ERROR_MESSAGE);
	}
}
