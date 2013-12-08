/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.esgi.routecalculator.ihm;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

/**
 *
 * @author mike
 */
public class Fenetre extends JFrame {

    //declaration des variables
    private JMenuBar menuBar;
    private JMenu mnFichier, mnEdition, mnPropos;
    private JMenuItem mntmNouvelleRecherche, mntmDveloppeurs;
    private JMenuItem mntmQuitter, mntmCopier, mntmColler, mntmSupprimer, mntmVersion, mntmToutSlctionner;

    public Fenetre() {
        this("fenetre");
    }

    public Fenetre(String title) {
        this.setTitle(title);
        this.setSize(1000, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.initMenu();
    }

    public void ajouterPanel(Container panel) {
        this.setContentPane(panel);
        this.validate();
    }

    private void initMenu() {
        menuBar = new JMenuBar();

        mnFichier = new JMenu("Fichier");
        mnFichier.setMnemonic('F');
        menuBar.add(mnFichier);

        mntmNouvelleRecherche = new JMenuItem("Nouvelle recherche");
        mntmNouvelleRecherche.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
        mntmNouvelleRecherche.addActionListener(new NouvelleRechercheController());
        mnFichier.add(mntmNouvelleRecherche);

        mntmQuitter = new JMenuItem("Quitter");
        mntmQuitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));
        mntmQuitter.addActionListener(new QuitterController());
        mnFichier.add(mntmQuitter);

        mnPropos = new JMenu("\u00C0 Propos");
        mnPropos.setMnemonic('P');
        menuBar.add(mnPropos);

        mntmVersion = new JMenuItem("Version");
        mntmVersion.addActionListener(new VersionController());
        mntmVersion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
        mnPropos.add(mntmVersion);

        mntmDveloppeurs = new JMenuItem("D\u00E9veloppeurs");
        mntmDveloppeurs.addActionListener(new DevelopperController());
        mntmDveloppeurs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_MASK));
        mnPropos.add(mntmDveloppeurs);

        this.setJMenuBar(menuBar);
    }

    private class NouvelleRechercheController implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int option = JOptionPane.showConfirmDialog(null, "Voulez-vous effectuer une nouvelle recherche ?", "Nouvelle recherche", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                /*RechercheController controller = new RechercheController(Fenetre.this);
                Fenetre.this.ajouterPanel(new VueRecherche(controller));
                controller.init();*/
            }
        }
    }

    private class QuitterController implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int option = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment fermer l'application ?", "Fermeture de l'application", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        }
    }

    private class VersionController implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Version 1.0", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class DevelopperController implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextArea sortie = new JTextArea(5, 20);
            String texte = "Architecture : Alexandre Dubois & Hugo POISSONNET\nIHM          : Idriss SANI\nInterfaçage : Mike Fiari\t";
            sortie.setText(texte);
            JOptionPane.showMessageDialog(null, sortie, "Liste des Développeurs", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
