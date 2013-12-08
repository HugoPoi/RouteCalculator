/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.esgi.routecalculator.ihm;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author mike
 */
public class PanelImage extends JPanel {

    private BufferedImage image;

    public PanelImage() {
        this.image = null;
    }

    public PanelImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        if (this.image != null) {
            g.drawImage(this.image, ((this.getWidth() / 2) - (this.image.getWidth() / 2)), 0, this);
            this.setSize(new Dimension(this.image.getWidth(), this.image.getHeight()));
            this.setPreferredSize(new Dimension(this.image.getWidth(), this.image.getHeight()));
            this.revalidate();
        }
    }
}