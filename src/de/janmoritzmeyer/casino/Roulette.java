package de.janmoritzmeyer.casino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Roulette extends Casino implements ActionListener {
    private JLabel welcomeString;

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

        return guilist;
    }

}
