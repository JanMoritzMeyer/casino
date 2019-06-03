package de.janmoritzmeyer.casino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BlackJack extends SuperCasino implements ActionListener{

    //Listen für die Karten der Croupier
    private LinkedList<Object> karten_crou;
    private LinkedList<Component> kartenobj_crou;
    //GUI Verknüpfung
    private GUI gui;
    //JButtons für ziehen und aufdecken
    private JButton bleiben;
    private JButton nehmen;
    //Liste für die Karten
    private List<Component> karten;
    //Integer für die Kartensummen, die Anzahl der Asse und die "verwandelten" Asse
    private int kartensumme;
    private int kartensumme_crou;
    private int asse;
    private int asse_to1;
    private int asse_crou;
    private int asse_crou_to1;
    private int cardx;
    private int cardx_crou;
    //Booleans welche für das laden und darstellen der Info Fenster verantwortlich sind
    private boolean firstload;
    private boolean firstgame;
    //integer für den Einsatz
    private int einsatz;
    //JLabel für das Design des Fensters
    private JLabel croupier;
    private JLabel self;
    private JLabel background;

    //Konstruktor
    public BlackJack(GUI gui) {
        //Verknüpfen der gui
        this.gui = gui;
        //Setzen des Booleans, damit das Spiel noch nicht gestartet wird
        firstload = true;
        //Funktion aufrufen, welche die Variablen initialisiert
        startnewGame();
        //Boolean fürs erste laden auf false setzen
        firstload = false;
        //Boolean fürs erste game auf true setzen, damit das Info Fenster angezeigt wird
        firstgame = true;
    }

    //Funktion zum starten eines neuen Spiels
    public void startnewGame(){
        //initialisieren der Variablen. Sorgt unter anderem davor, dass das vorherige Spiel gelöscht wird
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
            //Erklärung zeigen, falls es das erste Spiel ist
            JOptionPane.showMessageDialog(this, "Herzlich Willkommen bei Black Jack. \nDas Ziel ist es einen Kartenwert von 21 zu erzielen. \nBilder zählen 10, Die Zahlen den Zahlenwert und Asse 1 oder 11. \nDein Kartenwert darf 21 nicht übersteigen");
            firstgame = false;
        }
        //Wenn es nicht das erste Laden ist, Einsatz abfragen, und die ersten Karten ziehen
        if (!firstload){
            Object antwort = JOptionPane.showInputDialog(null, "Wieviel € möchtest du setzen? Min. 1€ Max. "+this.gui.getMoney()+"€ (Nur die Zahl eingeben)");
            //Wenn der Einsatz ungültig ist, Einsatz auf 0€ setzen
            try {
                einsatz = Integer.parseInt((String) antwort);
                if (einsatz > gui.getMoney() || einsatz < 0){
                    einsatz = 0;
                }
            } catch (Exception e) {
                einsatz = 0;
            }
            //Croupier Karte ziehen lassen
            karteziehen_crou();
            //Erste Karte ziehen
            karteziehen();
        }
    }

    //Funktion die aufgerufen wird, sobald ein Button geklickt wird
    public void actionPerformed (ActionEvent ae){
        //Logik für den Coupier, damit dieser nach den Regeln zieht und dann seine Karten aufdeckt
        if(ae.getSource() == this.bleiben){
            //Zieht bis seine Karten den Wert von 17 haben
            int max = 17;
            //Karten ziehen bis aus der Schleife ausgebrochen wird
            while (true){
                while (kartensumme_crou < max){
                    karteziehen_crou();
                    gui.reloadBlackJack();
                }
                //Wenn der Wert über 21 ist, prüfen ob er Asse hat, damit diese dann mit 1 gerechnet werden
                if (kartensumme_crou > 21 && asse_crou != 0){
                    max = max + 10;
                    asse_crou_to1++;
                }
                //Wenn keine Asse verfügbar sind aus Schleife gehen
                else if(asse_crou == 0){
                    break;
                }
                //Wenn alle Asse "verwandelt" wurden aus der Schleife gehen
                else if(asse_crou == asse_crou_to1){
                    break;
                }
                //Wenn der Croupier 17 oder mehr an Wert hat, aus der Schleife gehen
                else if(kartensumme_crou >= 17){
                    break;
                }
            }
            //Karten vom Croupier aufdecken
            aufdecken_crou();
        }
        //Wenn Button fürs Karten ziehen gedrückt wird, Funktion fürs Karten ziehen aufrufen
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
        audioPlayer("card.wav", false);
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        audioPlayer("win.wav", false);
        JOptionPane.showMessageDialog(this, "Herzlich Glückwunsch. Du hast "+einsatz+" € gewonnen");
        gui.changeMoney( einsatz );
        gui.initHome();
    }

    private void draw(){
        audioPlayer("loose.wav", false);
        JOptionPane.showMessageDialog(this, "Unentschieden. Du bekommst deinen Einsatz zurück");
        gui.initHome();
    }

    private void loose(){
        audioPlayer("loose.wav", false);
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

        bleiben = new JButton("aufdecken");
        bleiben.setLocation( 50,325 );
        bleiben.setSize( 100,50 );
        bleiben.addActionListener( this );
        guilist.add( bleiben );

        nehmen = new JButton( "Karte ziehen" );
        nehmen.setLocation( 150,325 );
        nehmen.setSize( 100,50 );
        nehmen.addActionListener( this );
        guilist.add( nehmen );

        background = new JLabel("");
        background.setIcon( scaleImage( "blackjack_background.jpg" , 800, 400 ));
        background.setLocation( 0,0 );
        background.setSize( 800,400 );
        background.setBackground( Color.GREEN );
        guilist.add( background );

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