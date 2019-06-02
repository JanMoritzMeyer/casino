package de.janmoritzmeyer.casino;

import javax.swing.*;
import java.awt.*;

public class Casino extends JFrame {
    public static void main(String[] args){
        GUI frame=new GUI(800,400);
    }

    public ImageIcon scaleImage(String name, int x, int y){
        ImageIcon imageIcon = new ImageIcon("Images/"+name); // Bild laden
        Image image = imageIcon.getImage(); // Icon zu Bild umwandeln
        Image newimg = image.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // Bild skalieren
        imageIcon = new ImageIcon(newimg); //Bild zu Icon umwandeln
        return imageIcon; //Icon zurückgeben
    }

}
