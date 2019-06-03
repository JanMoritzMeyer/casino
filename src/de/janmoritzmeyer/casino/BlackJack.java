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

    //Funktion zum aufdecken der Karten vom Croupier
    private void aufdecken_crou(){
        //X-Position der Karte im Fenster wieder auf 0 setzen
        cardx_crou = 0;
        //Karten Objekte entfernen
        kartenobj_crou.clear();
        //Für Jedes KartenObject, das richtige JLabel mit der entsprechenden Grafik erstellen
        for (Object cardnum: karten_crou){
            //Kartennummer setzen
            int cardnum_int = (int) cardnum;
            //Kartengrafik für die dazugehörige Nummer
            BlackJackCard card = new BlackJackCard( cardnum_int );
            //Position der Karte setzen
            card.card.setLocation( cardx_crou,50 );
            //Hinzufügen der Karte in die Liste
            kartenobj_crou.add( card.card );
            //X-Position der Karte auf 100 erhöhen, damit diese nebeneinander angezeigt werden
            cardx_crou = cardx_crou + 100;
        }
        //gui neuladen
        gui.reloadBlackJack();
        //Funktion aufrufen in der der Gewinner ermittelt wird
        checkwin();
    }

    //Funktion zum ziehen einer Karte des Croupiers
    private void karteziehen_crou(){
        //Karte random ziehen
        int cardnum = random( 1,13 );
        //Karten Wert erhalten
        int cardval = getValueofCard(cardnum,1);
        //Karte zur Liste hinzufügen, damit diese am Ende aufgedeckt werden kann
        karten_crou.add( cardnum );
        //Kartensumme aktualisieren
        kartensumme_crou = kartensumme_crou + cardval;
        //BlackJack Card JLabel erhalten, welches die Rückseite einer Karte zeigt
        BlackJackCard card = new BlackJackCard( 14 );
        //Position setzen
        card.card.setLocation( cardx_crou,50 );
        //Karte zur Liste hinzufügen, damit diese angezeigt wird
        kartenobj_crou.add( card.card );
        //Karten X-Position um 100 erhöhen
        cardx_crou = cardx_crou + 100;
    }

    //Funktion zum ziehen einer Karte durch den Nutzer
    private void karteziehen(){
        //Sound abspielen
        audioPlayer("card.wav", false);
        //Warten bis der Sound abgespielt wurde
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Karte ziehen
        int cardnum = random( 1,13 );
        //Kartensumme aktualisieren
        kartensumme = kartensumme + getValueofCard(cardnum,0);
        //JLabel für die Karte mit dem jeweiligen Bild beziehen
        BlackJackCard card = new BlackJackCard( cardnum );
        //Position für die Karte setzen
        card.card.setLocation( cardx,175 );
        //Karte zur Liste hinzufügen, damit sie angezeigt werden kann
        karten.add( card.card );
        //GUI neu laden, damit die neue Karte angezeigt wird
        gui.reloadBlackJack();
        //X-Position der Karte um 100 erhöhen
        cardx = cardx + 100;
        //Wenn Kartensumme unter 21 ist nichts machen (weiterspielen) | Pro verwandeltes Ass wird das Limit um 10 erhöht, da die Karte dann 10 weniger zählt
        if (kartensumme < (21 + asse_to1*10)){

        }
        //Wenn Kartensumme mit verwandelten Assen unter 21 ist
        else if(kartensumme <= (21 + asse*10)){
            //Ein Ass runterstufen
            asse_to1++;
        }
        //Wenn die Kartensumme über 21 ist und kein Ass gezogen wurde
        else if (kartensumme > 21 && asse == 0){
            //Karten aufdecken
            aufdecken_crou();
        }
    }

    //Funktion zum überprüfen wer gewonnen hat
    private void checkwin(){
        //Kartensummen ermitteln | Für jedes runtergestufte ass wird 10 abgezogen
        kartensumme_crou = kartensumme_crou - asse_crou_to1*10;
        kartensumme = kartensumme - asse_to1*10;
        //Wenn der Spieler 21 hat, hat er gewonnen
        if(kartensumme == 21){
            //Gewinn Funktion aufrufen
            won();
        }
        //Wenn beide die gleiche Kartensumme haben ist es unentschieden
        else if(kartensumme_crou == kartensumme){
            //Untentschieden Funktion aufrufen
            draw();
        }
        //Wenn man näher an der 21 ist als der Croupier hat an gewonnen
        else if(kartensumme_crou - 21 > kartensumme - 21){
            won();
        }
        //Sonst verloren
        else {
            loose();
        }
    }

    //Funktion welche die Aktionen für das gewinnen ausführt
    private void won(){
        //Sound abspielen
        audioPlayer("win.wav", false);
        //Warten bis der Sound abgespielt wurde
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Info Fenster für den Gewinn anzeigen
        JOptionPane.showMessageDialog(this, "Herzlich Glückwunsch. Du hast "+einsatz+" € gewonnen");
        //Gewinn hinzufügen
        gui.changeMoney( einsatz );
        //Wieder den HomeScreen aufrufen
        gui.initHome();
    }

    //Funktion welche die Aktionen für ein unentschieden ausführt
    private void draw(){
        //Sound abspielen
        audioPlayer("loose.wav", false);
        //Warten bis der Sound abgespielt wurde
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Info Fenster anzeigen
        JOptionPane.showMessageDialog(this, "Unentschieden. Du bekommst deinen Einsatz zurück");
        //HomeScreen aufrufen
        gui.initHome();
    }

    //Funktionen welche die Aktionen für den Verlust ausführt
    private void loose(){
        //Sound abspielen
        audioPlayer("loose.wav", false);
        //Warten bis der Sound abgespielt wurde
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Wenn das Geld nach dem Verlust nicht unter 1€ ist, Info anzeigen und den HomeScreen laden. Unter 1 wird von GUI ein Fenster angezeigt
        if(!(gui.getMoney()-einsatz <= 0)){
            JOptionPane.showMessageDialog(this, "Du hast verloren. Viel Glück beim nächsten mal");
            gui.initHome();
        }
        //Geld welches eingesetzt wurde abziehen
        gui.changeMoney( -einsatz );
    }

    //Funktion welche die Komponenten für das Spiel zurückgibt
    public java.util.List<Component> getGUIElements(){
        //Lokale Liste erstellen, damit diese unter anderem immer wieder leer ist und neu befüllt wird
        List<Component> guilist = new ArrayList<>();

        //Karten vom Nutzer der Liste hinzufügen
        for (Component guielement:karten) {
            guilist.add( guielement );
        }
        //Karten vom Croupier hinzufügen
        for (Component guielement:kartenobj_crou) {
            guilist.add( guielement );
        }

        //Label für das Design initialisieren und der Liste hinzufügen
        croupier = new JLabel("Croupier Karten");
        croupier.setLocation( 0,25 );
        croupier.setSize( 100,25 );
        guilist.add( croupier );

        self = new JLabel("Deine Karten");
        self.setLocation( 0,150 );
        self.setSize( 100,25 );
        guilist.add( self );

        //Buttons für die Aktionen initialisieren und der Liste hinzufügen
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

        //Hintergrund aus der Funktion nehmen
        background = greenBackground();
        guilist.add( background );

        //Liste zurückgeben
        return guilist;
    }

    //Funktion welche den Wert der Black Jack Karte zurückgibt. Je nach Owner wird außerdem die Anzhal der Asse erhöht
    private int getValueofCard(int number, int owner){
        //Nummern 1-9 stehen für die Karten 2-10
        if (number < 10){
            return number + 1;
        }
        //Nummer 10 für das Ass
        else if(number == 10) {
            if (owner == 0){
                asse++;
            }
            else {
                asse_crou++;
            }
            return 11;
        }
        //Die anderen Nummern für die Bild Karten
        else {
            return 10;
        }
    }

}