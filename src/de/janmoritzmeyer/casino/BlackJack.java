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
    private List<Component> karten;
    private int kartensumme;
    private int kartensumme_crou;
    private int asse;
    private int asse_to1;
    private int asse_crou;
    private int asse_crou_to1;
    private int cardx;
    private int cardx_crou;
    private boolean firstload;
    private boolean firstgame;
    private int einsatz;
    private JLabel croupier;
    private JLabel self;

    public BlackJack(GUI gui) {
        this.gui = gui;
        firstload = true;
        startnewGame();
        firstload = false;
        firstgame = true;
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
        einsatz = 0;
        if (firstgame){
            JOptionPane.showMessageDialog(this, "Herzlich Willkommen bei Black Jack. Das Ziel ist es einen Kartenwert von 21 zu erzielen. Bilder zählen 10, Die Zahlen den Zahlenwert und Asse 1 oder 11. Dein Kartenwert darf 21 nicht übersteigen");
            firstgame = false;
        }
        if (!firstload){
            Object antwort = JOptionPane.showInputDialog(null, "Wieviel € möchtest du setzen? Min. 1€ Max. "+this.gui.getMoney()+"€ (Nur die Zahl eingeben)");
            try {
                einsatz = Integer.parseInt((String) antwort);
                if (einsatz > gui.getMoney() || einsatz < 0){
                    einsatz = 0;
                }
            } catch (Exception e) {
                einsatz = 0;
            }
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
                else if(kartensumme_crou >= 17){
                    break;
                }
            }
            aufdecken_crou();
        }
        else if(ae.getSource() == this.nehmen){
            karteziehen();
        }
    }

    private void aufdecken_crou(){
        cardx_crou = 0;
        kartenobj_crou.clear();
        for (Object cardnum: karten_crou){
            int cardnum_int = (int) cardnum;
            BlackJackCard card = new BlackJackCard( cardnum_int );
            card.card.setLocation( cardx_crou,50 );
            kartenobj_crou.add( card.card );
            cardx_crou = cardx_crou + 100;
        }
        gui.reloadBlackJack();
        checkwin();
    }

    private void karteziehen_crou(){
        int cardnum = random( 1,13 );
        int cardval = getValueofCard(cardnum,1);
        karten_crou.add( cardnum );
        kartensumme_crou = kartensumme_crou + cardval;
        BlackJackCard card = new BlackJackCard( 14 );
        card.card.setLocation( cardx_crou,50 );
        kartenobj_crou.add( card.card );
        cardx_crou = cardx_crou + 100;
    }

    private void karteziehen(){
        int cardnum = random( 1,13 );
        kartensumme = kartensumme + getValueofCard(cardnum,0);
        BlackJackCard card = new BlackJackCard( cardnum );
        card.card.setLocation( cardx,175 );
        karten.add( card.card );
        gui.reloadBlackJack();
        cardx = cardx + 100;
        if (kartensumme < 21){

        }
        else if(kartensumme <= (21 + asse*10)){
            asse_to1++;
        }
        else if (kartensumme > 21 && asse == 0){
            aufdecken_crou();
            loose();
        }
    }

    private void checkwin(){
        kartensumme_crou = kartensumme_crou - asse_crou_to1*10;
        kartensumme = kartensumme - asse_to1*10;
        if(kartensumme == 21){
            won();
        }
        else if(kartensumme_crou - 21 == kartensumme - 21){
            draw();
        }
        else if(kartensumme_crou - 21 > kartensumme - 21){
            won();
        }
        else {
            loose();
        }
    }

    private void won(){
        JOptionPane.showMessageDialog(this, "Herzlich Glückwunsch. Du hast "+einsatz+" € gewonnen");
        gui.changeMoney( einsatz );
        gui.initHome();
    }

    private void draw(){
        JOptionPane.showMessageDialog(this, "Unentschieden. Du bekommst deinen Einsatz zurück");
        gui.initHome();
    }

    private void loose(){
        if(!(gui.getMoney()-einsatz <= 0)){
            JOptionPane.showMessageDialog(this, "Du hast verloren. Viel Glück beim nächsten mal");
            gui.initHome();
        }
        gui.changeMoney( -einsatz );
    }

    public java.util.List<Component> getGUIElements(){
        List<Component> guilist = new ArrayList<>();

        for (Component guielement:karten) {
            guilist.add( guielement );
        }
        for (Component guielement:kartenobj_crou) {
            guilist.add( guielement );
        }

        croupier = new JLabel("Croupier Karten");
        croupier.setLocation( 0,25 );
        croupier.setSize( 100,25 );
        guilist.add( croupier );

        self = new JLabel("Deine Karten");
        self.setLocation( 0,150 );
        self.setSize( 100,25 );
        guilist.add( self );

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