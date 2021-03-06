package de.janmoritzmeyer.casino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


public class Wuerfeln extends SuperCasino implements ActionListener{

    //Buttons deklarieren welche für das setzen auf die Zahlenbereiche zuständig sind. Außerdem gibt es einen Button der auf den HomeScreen weiterleitet
    JButton button1 = new JButton();
    JButton button2 = new JButton();
    JButton button3 = new JButton();
    JButton button4 = new JButton();
    JButton button5 = new JButton();
    //Label für das Design. label1 ist der WillkommenString der unter anderem für die Anzeige des Gewinns oder Verlustes zuständig ist. label1, label2 und label3 sind für die Darstellung der Würfel zuständig
    JLabel label1 = new JLabel();
    JLabel label2 = new JLabel();
    JLabel label3 = new JLabel();
    JLabel label4 = new JLabel();
    //GUI Objekt, damit auf das Geld Element und den Slider zugegriffen werden kann
    private GUI gui;
    //boolean welcher sagt ob es das erste Spiel ist. Beim ersten Spiel wird ein Info Fenster angezeigt. Der Boolean ist protected damit GUI auf diesen zugreifen kann, da dort die Prüfung stattfindet
    protected boolean firstgame;

    //Konstruktor
    public Wuerfeln(GUI gui){
        //firstgame auf true setzen, damit die Info angezeigt wird
        firstgame = true;
        //Verknüpfung auf GUI setzen
        this.gui = gui;
    }

    //Funktion die aufgerufen wird, sobald ein Button geklickt wird
    public void actionPerformed (ActionEvent ae){
        //Einsatz feststellen
        int einsatz = gui.slider1.getValue();
        //Für die Buttons jeweils die Funktionen aufrufen
        if(ae.getSource() == this.button1){
            //Würfel Funktion mit dem Augenzahl Bereich, dem Faktor und dem Einsatz aufrufen
            wuerfeln(3,6, 9, einsatz);
        }
        else if(ae.getSource() == this.button2){
            wuerfeln(7,10, 2, einsatz);
        }
        else if(ae.getSource() == this.button3){
            wuerfeln(11,15, 3, einsatz);
        }
        else if(ae.getSource() == this.button4){
            wuerfeln(16,18, 20, einsatz);
        }
        else if(ae.getSource() == this.button5){
            //HomeScreen aufrufen
            gui.initHome();
        }
    }

    //Funktion welche die Komponenten für das Spiel zurückgibt
    public List<Component> getGUIElements(){
        //leere Liste erstellen
        List<Component> guilist = new ArrayList<>();

        //JLabel mit Erklärungstext erstellen. Größe und Position setzen sowie Text zentrieren
        label1 = new JLabel("Herzlich Willkommen beim Würfeln. Zum spielen wähle eine Augenzahl");
        label1.setLocation( 100, 50 );
        label1.setSize( 600,10);
        label1.setHorizontalAlignment(JLabel.CENTER);
        guilist.add( label1 );

        //JLabel mit Würfelgrafik erstellen. Für den Anfang mit den Augenzahlen 1-3
        label2 = new JLabel("");
        label2.setIcon(scaleImage("dice_1.png",50,50));
        label2.setLocation( 250, 100 );
        label2.setSize( 50,50);
        guilist.add( label2 );

        label3 = new JLabel("");
        label3.setIcon(scaleImage("dice_2.png",50,50));
        label3.setLocation( 350, 100 );
        label3.setSize( 50,50);
        guilist.add( label3 );

        label4 = new JLabel("");
        label4.setIcon(scaleImage("dice_3.png",50,50));
        label4.setLocation( 450, 100 );
        label4.setSize( 50,50);
        guilist.add( label4 );

        //Buttons für die Augenzahlbereiche setzen. Text, Position, Größe und actionListener hinzufügen
        button1.setText("3-6 (x 9)");
        button1.setLocation( 100,200 );
        button1.setSize( 100,50 );
        button1.addActionListener( this );
        guilist.add( button1 );

        button2.setText("7-10 (x 2)");
        button2.setLocation( 250,200 );
        button2.setSize( 100,50 );
        button2.addActionListener( this );
        guilist.add( button2 );

        button3.setText("11-15 (x 3)");
        button3.setLocation( 400,200 );
        button3.setSize( 100,50 );
        button3.addActionListener( this );
        guilist.add( button3 );

        button4.setText("16-18 (x 20)");
        button4.setLocation( 550,200 );
        button4.setSize( 100,50 );
        button4.addActionListener( this );
        guilist.add( button4 );

        //Home Button
        button5.setText("<-");
        button5.setLocation( 10,10 );
        button5.setSize( 25,25 );
        button5.addActionListener( this );
        guilist.add( button5 );

        //Liste zurückgeben
        return guilist;
    }


    //Funktion welche das jeweilige Bild für die Würfelzahl zurückgibt. Rest ist selbsterklären
    private ImageIcon getImage(int x){
        switch (x){
            case 1:
                return scaleImage("dice_1.png",50,50);
            case 2:
                return scaleImage("dice_2.png",50,50);
            case 3:
                return scaleImage("dice_3.png",50,50);
            case 4:
                return scaleImage("dice_4.png",50,50);
            case 5:
                return scaleImage("dice_5.png",50,50);
            case 6:
                return scaleImage("dice_6.png",50,50);
            default:
                return scaleImage("",50,50);
        }
    }

    //Funktion fürs Würfeln
    private void wuerfeln(int min, int max, int factor, int einsatz) {
        //Spielt Würfel Sound ab
        audioPlayer("dice.wav", false);
        //und wartet
        try {
            Thread.sleep(1300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //es wird 3 mal gewürfelt
        int wuerfel1 = random(1,6);
        int wuerfel2 = random(1,6);
        int wuerfel3 = random(1,6);
        //und die Summe ermittelt
        int summe = wuerfel1 + wuerfel2 + wuerfel3;
        //Dann werden die JLabels mit den entsprechenden Bildern versehen
        label2.setIcon( getImage(wuerfel1) );
        label3.setIcon( getImage(wuerfel2) );
        label4.setIcon( getImage(wuerfel3) );
        //Gewinn Prüfung
        if (summe <= max && summe >= min){
            //Gewinn Sound abspielen
            audioPlayer("win.wav", false);
            //Geld verändern
            gui.changeMoney( factor*einsatz );
            //Text setzen
            label1.setText( "Herzlichen Glückwunsch. Du hast "+factor*einsatz+"€ gewonnen" );
        }
        else {
            //Verlier Sound abspielen
            audioPlayer("loose.wav", false);
            //Geld verändern
            gui.changeMoney( -einsatz );
            //Text setzen
            label1.setText( "Schade, vielleicht klappt es nächstes mal" );
        }
        //Slider updaten
        gui.updateSlider();
    }
}
