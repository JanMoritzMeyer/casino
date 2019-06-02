package de.janmoritzmeyer.casino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BlackJack extends Casino implements ActionListener{

    private GUI gui;
    private JButton bleiben;
    private JButton nehmen;
    private List<Component> karten;
    private int kartensumme;
    private int asse;
    private int cardx;

    public BlackJack(GUI gui) {
        this.gui = gui;
        karten = new ArrayList<>();
        cardx = 0;
    }

    public void actionPerformed (ActionEvent ae){
        if(ae.getSource() == this.bleiben){

        }
        else if(ae.getSource() == this.nehmen){
            karteziehen();
        }
    }

    private void karteziehen(){
        BlackJackCard card = new BlackJackCard( random( 1,13 ) );
        card.card.setLocation( cardx,150 );
        karten.add( card.card );
        gui.reloadBlackJack();
        cardx = cardx + 100;
    }

    public java.util.List<Component> getGUIElements(){
        List<Component> guilist = new ArrayList<>();

        for (Component guielement:karten) {
            guilist.add( guielement );
        }

        bleiben = new JButton("bleiben");
        bleiben.setLocation( 50,325 );
        bleiben.setSize( 100,50 );
        bleiben.addActionListener( this );
        guilist.add( bleiben );

        nehmen = new JButton( "nehmen" );
        nehmen.setLocation( 150,325 );
        nehmen.setSize( 100,50 );
        nehmen.addActionListener( this );
        guilist.add( nehmen );

        return guilist;
    }

    private void getValueofCard(int number){

    }

}