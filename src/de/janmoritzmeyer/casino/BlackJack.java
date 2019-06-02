package de.janmoritzmeyer.casino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BlackJack extends Casino implements ActionListener{

    private final LinkedList<Object> karten_crou;
    private GUI gui;
    private JButton bleiben;
    private JButton nehmen;
    private List<Component> karten;
    private int kartensumme;
    private int kartensumme_crou;
    private int asse;
    private int asse_crou;
    private int cardx;
    private int cardx_crou;

    public BlackJack(GUI gui) {
        this.gui = gui;
        karten = new ArrayList<>();
        kartensumme = 0;
        kartensumme_crou = 0;
        karten_crou = new LinkedList<>();
        asse = 0;
        asse_crou = 0;
        asse = 0;
        cardx = 0;
        cardx_crou = 0;
        startnewGame();
    }

    public void startnewGame(){
        karteziehen_crou();
    }

    public void actionPerformed (ActionEvent ae){
        if(ae.getSource() == this.bleiben){
            while (kartensumme_crou < 17){
                karteziehen_crou();
                gui.reloadBlackJack();
            }

        }
        else if(ae.getSource() == this.nehmen){
            karteziehen();
        }
    }

    private void aufdecken_crou(){

    }

    private void karteziehen_crou(){
        int cardnum = random( 1,13 );
        int cardval = getValueofCard(cardnum,1);
        karten_crou.add( cardnum );
        kartensumme_crou = kartensumme_crou + cardval;
        BlackJackCard card = new BlackJackCard( 14 );
        card.card.setLocation( cardx_crou,25 );
        karten.add( card.card );
        cardx_crou = cardx_crou + 100;
    }

    private void karteziehen(){
        int cardnum = random( 1,13 );
        kartensumme = kartensumme + getValueofCard(cardnum,0);
        BlackJackCard card = new BlackJackCard( cardnum );
        card.card.setLocation( cardx,150 );
        karten.add( card.card );
        gui.reloadBlackJack();
        cardx = cardx + 100;
        System.out.println( kartensumme );
        if (kartensumme > 21){
            System.out.println( "verloren" );
        }
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

    private int getValueofCard(int number, int owner){
        if (number < 10){
            return number + 1;
        }
        else if(number == 10) {
            if (owner == 0){
                asse++;
            }
            else {
                asse_crou++;
            }
            return 11;
        }
        else {
            return 10;
        }
    }

}