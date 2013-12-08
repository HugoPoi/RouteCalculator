/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.esgi.routecalculator.ihm.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author mike
 */
public class Image {
    
    public final static String defaut = "defaut/";
    public final static String metro = "metro/";
    public final static String bus = "bus/";
    
    public URL getUrlImage (String nom, String type) {
        URL url = getClass().getResource(type+nom+".png");
        System.out.println(type+nom);
        System.out.println(url);
        if (url == null) {
            url = getClass().getResource(type+nom+".jpg");
            if (url == null) {
                url = getClass().getResource(type+nom+".gif");
            }
        }
        if (url == null && !defaut.equals(type)) {
            return this.getUrlImage(nom, defaut);
        }
        return url;
    }
    
    public URL getUrlImage (String nom) {
        return this.getUrlImage(nom, defaut);
    }
    
    public boolean aImage (String nom) {
        return this.getUrlImage(nom) != null;
    }
    
    public boolean aImage (String nom, String type) {
        return this.getUrlImage(nom, type) != null;
    }
    
    public BufferedImage getImage (String nom) {
        return this.getImage(this.getUrlImage(nom));
    }
    
    public BufferedImage getImage (String nom, String type) {
        return this.getImage(this.getUrlImage(nom, type));
    }
    
    protected BufferedImage getImage (URL url) {
        BufferedImage img = null;
        File fichier;
        fichier = new File(url.getFile());
        try {
            img = ImageIO.read(fichier);
        } catch (IOException ex) {
            Logger.getLogger(Image.class.getName()).log(Level.SEVERE, null, ex);
        }
        return img;
    }
}
