package de.janmoritzmeyer.casino;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Casino extends JFrame {
    public static void main(String[] args){
        GUI frame = new GUI(800,400);
    }

    public ImageIcon scaleImage(String name, int x, int y){
        ImageIcon imageIcon = new ImageIcon("Images/"+name); // Bild laden
        Image image = imageIcon.getImage(); // Icon zu Bild umwandeln
        Image newimg = image.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // Bild skalieren
        imageIcon = new ImageIcon(newimg); //Bild zu Icon umwandeln
        return imageIcon; //Icon zurückgeben
    }

    public int random(int min, int max){
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    protected JButton robutton(String text, int xpos, int ypos, int width, int height){
        JButton button = new JButton( text );
        button.setSize( width, height );
        button.setLocation( xpos, ypos );
        button.setBackground( Color.GREEN );
        button.setOpaque(true);
        return button;
    }

}
