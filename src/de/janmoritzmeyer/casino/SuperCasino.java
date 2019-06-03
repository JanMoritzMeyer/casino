package de.janmoritzmeyer.casino;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import static javax.sound.sampled.AudioSystem.getClip;

public abstract class SuperCasino extends Casino {

    /* Class for providing Elements and functions for the Casino */

    //Funktion zum ausgeben der Bilder als Icon mit der richtigen Größe für die Komponenten
    public ImageIcon scaleImage(String name, int x, int y){
        ImageIcon imageIcon = new ImageIcon("Images/"+name); // Bild laden
        Image image = imageIcon.getImage(); // Icon zu Bild umwandeln
        Image newimg = image.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // Bild skalieren
        imageIcon = new ImageIcon(newimg); //Bild zu Icon umwandeln
        return imageIcon; //Icon zurückgeben
    }

    //Vereinfachte Funktion um eine zufällige Zahl zu erhalten
    public int random(int min, int max){
        Random r = new Random();
        //Grenzen für die Zufallszahl festlegen
        return r.nextInt((max - min) + 1) + min;
    }

    //Funktion, welche die Buttons vereinfacht erstellt, welche für das Roulette gebraucht werden
    //Das Setzen erklärt sich von selber
    protected JButton robutton(String text, int xpos, int ypos, int width, int height, ActionListener actionListener){
        JButton button = new JButton( text );
        button.setSize( width, height );
        button.setLocation( xpos, ypos );
        button.setBackground( Color.GREEN );
        button.setForeground( Color.BLACK );
        button.setOpaque(true);
        button.setBorder(BorderFactory.createBevelBorder(0));
        button.addActionListener( actionListener );
        return button;
    }

    //vereinfachte Funktion zum abspielen von audio Dateien
    public void audioPlayer(String file, boolean loop){
        //Lokale Variablen deklarieren
        AudioInputStream audioInputStream;
        Clip clip;
        try {
            //initialisieren der AudioFiles
            audioInputStream = AudioSystem.getAudioInputStream(new File("Sounds/"+file).getAbsoluteFile());
            clip = getClip();
            //Clip öffnen
            clip.open(audioInputStream);
            //Wenn Sound geloopt wird, Loop einstellen, sonst Sound starten
            if(loop){
                clip.loop( Clip.LOOP_CONTINUOUSLY);
            }
            else {
                clip.start();
            }
        }
        //catch Funktionen falls Fehler auftreten
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
