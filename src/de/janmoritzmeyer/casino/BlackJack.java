package de.janmoritzmeyer.casino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BlackJack extends Casino implements ActionListener{

    private LinkedList<Object> karten_crou;
    private LinkedList<Component> kartenobj_crou;
    private GUI gui;
    private JButton bleiben;
    private JButton nehmen;
    private JButton home;
    private List<Component> karten;
    private int kartensumme;
    private int kartensumme_crou;
    private int asse;
    private int asse_to1;
    private int asse_crou;
    private int asse_crou_to1;
    private int cardx;
    private int cardx_crou;
    private boolean firstgame;

    public BlackJack(GUI gui) {
        this.gui = gui;
        firstgame = true;
        startnewGame();
        firstgame = false;
    }

    public void startnewGame(){
        karten = new ArrayList<>();
        kartensumme = 0;
        kartensumme_crou = 0;
        karten_crou = new LinkedList<>();
        kartenobj_crou = new LinkedList<>();
        asse = 0;
        asse_to1 = 0;
        asse_crou = 0;
        asse_crou_to1 = 0;
        cardx = 0;
        cardx_crou = 0;
        if (!firstgame){
            karteziehen_crou();
            karteziehen();
        }
    }

    public void actionPerformed (ActionEvent ae){
        if(ae.getSource() == this.bleiben){
            int max = 17;
            while (true){
                while (kartensumme_crou < max){
                    karteziehen_crou();
                    gui.reloadBlackJack();
                }
                if (kartensumme_crou > 21 && asse_crou != 0){
                    max = max + 10;
                    asse_crou_to1++;
                }
                else if(asse_crou == 0){
                    break;
                }
                else if(asse_crou == asse_crou_to1){
                    break;
                }
            }
            aufdecken_crou();
            System.out.println( kartensumme_crou );
        }
        else if(ae.getSource() == this.nehmen){
            karteziehen();
        }
        else if(ae.getSource() == this.home){
            gui.initHome();
        }
    }

    private void aufdecken_crou(){
        cardx_crou = 0;
        kartenobj_crou.clear();
        for (Object cardnum: karten_crou){
            int cardnum_int = (int) cardnum;
            BlackJackCard card = new BlackJackCard( cardnum_int );
            card.card.setLocation( cardx_crou,25 );
            kartenobj_crou.add( card.card );
            cardx_crou = cardx_crou + 100;
        }
        gui.reloadBlackJack();
    }

    private void karteziehen_crou(){
        int cardnum = random( 1,13 );
        int cardval = getValueofCard(cardnum,1);
        karten_crou.add( cardnum );
        kartensumme_crou = kartensumme_crou + cardval;
        BlackJackCard card = new BlackJackCard( 14 );
        card.card.setLocation( cardx_crou,25 );
        kartenobj_crou.add( card.card );
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
        if (kartensumme < 21){

        }
        else if(kartensumme < (21 + asse*10)){
            asse_to1++;
        }
        else if (kartensumme > 21 && asse == 0){
            //Verloren
            aufdecken_crou();
        }
    }

    private void checkwin(){
        kartensumme_crou = kartensumme_crou - asse_crou_to1*10;
        kartensumme = kartensumme - asse_to1*10;
        System.out.println( kartensumme_crou );
        System.out.println( kartensumme );
    }

    public java.util.List<Component> getGUIElements(){
        List<Component> guilist = new ArrayList<>();

        for (Component guielement:karten) {
            guilist.add( guielement );
        }
        for (Component guielement:kartenobj_crou) {
            guilist.add( guielement );
        }

        home = new JButton("<-");
        home.setLocation( 0,0 );
        home.setSize( 25,25 );
        home.addActionListener( this );
        guilist.add( home );

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