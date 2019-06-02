package de.janmoritzmeyer.casino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Roulette extends Casino implements ActionListener {
    private JLabel welcomeString;
    private JButton col1;
    private JButton col2;
    private JButton col3;
    private JButton doz1;
    private JButton doz2;
    private JButton doz3;
    private JButton even;
    private JButton uneven;
    private JButton black;
    private JButton red;
    private JButton u19;
    private JButton ue18;

    public Roulette(GUI gui){

    }

    public void actionPerformed (ActionEvent ae){

    }

    public java.util.List<Component> getGUIElements(){
        List<Component> guilist = new ArrayList<>();

        welcomeString = new JLabel("Herzlich Willkommen beim Roulette. Bitte setze auf die entsprechenden Felder");
        welcomeString.setLocation( 150,10 );
        welcomeString.setSize( 500,25 );
        guilist.add( welcomeString );
        JButton ro_pain_0 = new JButton( String.valueOf( 0 ) );
        ro_pain_0.setLocation(500,25);
        ro_pain_0.setSize( 230,30 );
        ro_pain_0.setBackground( Color.GREEN );
        ro_pain_0.setOpaque(true);
        guilist.add( ro_pain_0 );
        for ( int zahl = 1; zahl <= 36; zahl ++ ) {
            JButton ro_pain = new JButton( String.valueOf( zahl )+" (x 40)" );
            ro_pain.setLocation( 500+((zahl-1)%3)*75, 50+(((int) Math.ceil(((zahl-1)/3)))*25) );
            ro_pain.setSize( 85,25 );
            guilist.add( ro_pain );
        }
        guilist.add( col1=robutton("1. Kol", 500, 350, 85, 25) );
        guilist.add( col2=robutton("2. Kol", 575, 350, 85, 25) );
        guilist.add( col3=robutton("3. Kol", 650, 350, 85, 25) );
        guilist.add( doz1=robutton("1-12", 455, 50, 50, 100) );
        guilist.add( doz2=robutton("13-24", 455, 150, 50, 100) );
        guilist.add( doz3=robutton("25-36", 455, 250, 50, 100) );
        guilist.add( u19=robutton("1-18", 405, 50, 50, 50) );
        guilist.add( even=robutton("gerade", 405, 100, 50, 50) );
        guilist.add( black=robutton("schwarz", 405, 150, 50, 50) );
        guilist.add( red=robutton("rot", 405, 200, 50, 50) );
        guilist.add( uneven=robutton("ungerade", 405, 250, 50, 50) );
        guilist.add( ue18=robutton("19-36", 405, 300, 50, 50) );
        return guilist;
    }

}
