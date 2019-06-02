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

        welcomeString = new JLabel("Herzlich Willkommen beim Roulette. Bitte setze auf ein Feld");
        welcomeString.setLocation( 150,10 );
        welcomeString.setSize( 500,15 );
        guilist.add( welcomeString );
        JButton ro_pain_0 = new JButton( String.valueOf( 0 ) );
        ro_pain_0.setLocation(500,25);
        ro_pain_0.setSize( 225,25 );
        ro_pain_0.setBackground( Color.GREEN );
        ro_pain_0.setOpaque(true);
        guilist.add( ro_pain_0 );
        for ( int zahl = 1; zahl <= 36; zahl ++ ) {
            JButton ro_pain = new JButton( String.valueOf( zahl )+" (x 40)" );
            ro_pain.setLocation( 500+((zahl-1)%3)*75, 50+(((int) Math.ceil(((zahl-1)/3)))*25) );
            ro_pain.setSize( 75,25 );
            if (getColor( zahl ) == 1){
                ro_pain.setBackground( Color.RED );
            }
            else {
                ro_pain.setBackground( Color.GREEN );
            }
            ro_pain.setOpaque(true);
            guilist.add( ro_pain );
        }
        guilist.add( col1=robutton("1. Kol", 500, 350, 75, 25) );
        guilist.add( col2=robutton("2. Kol", 575, 350, 75, 25) );
        guilist.add( col3=robutton("3. Kol", 650, 350, 75, 25) );
        guilist.add( doz1=robutton("1-12", 450, 50, 50, 100) );
        guilist.add( doz2=robutton("13-24", 450, 150, 50, 100) );
        guilist.add( doz3=robutton("25-36", 450, 250, 50, 100) );
        guilist.add( u19=robutton("1-18", 375, 50, 75, 50) );
        guilist.add( even=robutton("gerade", 375, 100, 75, 50) );
        guilist.add( black=robutton("schwarz", 375, 150, 75, 50) );
        guilist.add( red=robutton("rot", 375, 200, 75, 50) );
        guilist.add( uneven=robutton("ungerade", 375, 250, 75, 50) );
        guilist.add( ue18=robutton("19-36", 375, 300, 75, 50) );
        return guilist;
    }

    public int getColor(int x){
        if (x <= 0 || x > 36){
            return 2;
        }
        switch (x){
            case 1:
            case 3:
            case 5:
            case 7:
            case 9:
            case 12:
            case 14:
            case 16:
            case 18:
            case 19:
            case 21:
            case 23:
            case 25:
            case 27:
            case 30:
            case 32:
            case 34:
            case 36:
                return 1;
        }
        return 0;
    }

}
