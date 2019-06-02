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
    private GUI gui;
    private JButton ro_pain;
    private JButton ro_pain_0;

    public Roulette(GUI gui){
        this.gui = gui;
    }

    public void actionPerformed (ActionEvent ae){
        int result = random( 0,36 );
        int einsatz = gui.slider1.getValue();
        if (ae.getSource() == col1 && (result-1)%3 == 0){
            win(einsatz*2);
        }
        else if (ae.getSource() == col2 && (result-2)%3 == 0){
            win(einsatz*2);
        }
        else if (ae.getSource() == col3 && result%3 == 0){
            win(einsatz*2);
        }
        else if (ae.getSource() == doz1 && result>= 1 && result <= 12){
            win(einsatz*2);
        }
        else if (ae.getSource() == doz2 && result>= 13 && result <= 24){
            win(einsatz*2);
        }
        else if (ae.getSource() == doz3 && result>= 25 && result <= 36){
            win(einsatz*2);
        }
        else if (ae.getSource() == even && result/2 == 0 && result != 0){
            win(einsatz);
        }
        else if (ae.getSource() == uneven && result/2 != 0 && result != 0){
            win(einsatz);
        }
        else if (ae.getSource() == black && getColor(result) == 0){
            win(einsatz);
        }
        else if (ae.getSource() == red && getColor(result) == 1){
            win(einsatz);
        }
        else if (ae.getSource() == u19 && result < 19 && result != 0){
            win(einsatz);
        }
        else if (ae.getSource() == ue18 && result >= 19){
            win(einsatz);
        }
        else if(ae.getSource() == ro_pain_0 && result == 0){
            win( einsatz*35 );
        }
        else if(ae.getSource() == ro_pain ){
            int number = Integer.parseInt( ae.getSource().toString() );
            if (number == result){
                win( einsatz*35 );
            }
        }
        else{
            loose(einsatz);
        }
    }

    private void win(int winsum){
        welcomeString.setText( "Herzlichen Glückwunsch. Du hast "+winsum+"€ gewonnen");
        gui.changeMoney( winsum );
    }

    private void loose(int sum){
        welcomeString.setText( "Schade. Du hast "+sum+"€ verloren");
        gui.changeMoney( -sum );
    }

    public java.util.List<Component> getGUIElements(){
        List<Component> guilist = new ArrayList<>();

        welcomeString = new JLabel("Herzlich Willkommen beim Roulette. Bitte setze auf ein Feld");
        welcomeString.setLocation( 150,10 );
        welcomeString.setSize( 500,15 );
        guilist.add( welcomeString );
        ro_pain_0 = new JButton( String.valueOf( 0 ) );
        ro_pain_0.setLocation(500,25);
        ro_pain_0.setSize( 225,25 );
        ro_pain_0.setBackground( Color.GREEN );
        ro_pain_0.setOpaque(true);
        ro_pain_0.addActionListener( this );
        guilist.add( ro_pain_0 );
        for ( int zahl = 1; zahl <= 36; zahl ++ ) {
            ro_pain = new JButton( String.valueOf( zahl ) );
            ro_pain.setLocation( 500+((zahl-1)%3)*75, 50+(((int) Math.ceil(((zahl-1)/3)))*25) );
            ro_pain.setSize( 75,25 );
            if (getColor( zahl ) == 1){
                ro_pain.setBackground( Color.RED );
            }
            else {
                ro_pain.setBackground( Color.GREEN );
            }
            ro_pain.setOpaque(true);
            ro_pain.addActionListener( this );
            guilist.add( ro_pain );
        }
        guilist.add( col1=robutton("1. Reihe", 500, 350, 75, 25,this) );
        guilist.add( col2=robutton("2. Reihe", 575, 350, 75, 25,this) );
        guilist.add( col3=robutton("3. Reihe", 650, 350, 75, 25,this) );
        guilist.add( doz1=robutton("1-12", 450, 50, 50, 100,this) );
        guilist.add( doz2=robutton("13-24", 450, 150, 50, 100,this) );
        guilist.add( doz3=robutton("25-36", 450, 250, 50, 100,this) );
        guilist.add( u19=robutton("1-18", 375, 50, 75, 50,this) );
        guilist.add( even=robutton("gerade", 375, 100, 75, 50,this) );
        guilist.add( black=robutton("schwarz", 375, 150, 75, 50,this) );
        guilist.add( red=robutton("rot", 375, 200, 75, 50,this) );
        guilist.add( uneven=robutton("ungerade", 375, 250, 75, 50,this) );
        guilist.add( ue18=robutton("19-36", 375, 300, 75, 50,this) );
        JLabel faktoren = new JLabel( "<html><body><h2>Auszahlungsquoten</h2>" +
                "einzelne Zahl: x35<br>" +
                "vertikale Reihe (ohne 0): x2<br>" +
                "12er Bereich: x2<br>" +
                "1-18 oder 19-36: x1<br>" +
                "gerade/ungerade: x1<br>" +
                "schwarz/rot: x1<br>");
        faktoren.setLocation( 10,10 );
        faktoren.setSize( 200,150 );
        guilist.add( faktoren );
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
