package de.janmoritzmeyer.casino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Roulette extends SuperCasino implements ActionListener {
    //JLabel für die welcome und ergebnis Strings deklarieren
    private JLabel welcomeString;
    private JLabel ergebnisString;
    //JButtons für die jeweiligen Ereignisse deklarieren
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
    private JButton ro_pain;
    private JButton ro_pain_0;
    //JButton für das Laden des HomeScreens deklarieren
    private JButton home;
    //JLabel für den Hintergrund deklarieren
    private JLabel background;
    //GUI deklarieren, damit unter anderem auf die changeMoney Funktion zugegriffen werden kann
    private GUI gui;

    //Konstruktor welcher die Verknüpfung für die gui herstellt
    public Roulette(GUI gui){
        this.gui = gui;
    }

    //Funktion die aufgerufen wird, sobald ein Button geklickt wird
    public void actionPerformed (ActionEvent ae){
        //Zahl ziehen
        int result = random( 0,36 );
        //Einsatz von Slider abrufen
        int einsatz = gui.slider1.getValue();
        //Wenn nicht nach Hause Button gedrückt wurde
        if (ae.getSource() != home){
            //Sound abspielen
            audioPlayer("roulette_spin.wav", false);
            //Warten bis der Sound zu Ende abgespielt wurde
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Wenn nach Hause BUtton gedrückt wurde, Home Screen laden
        if (ae.getSource() == home){
            gui.initHome();
        }
        //Überprüfung für die jeweiligen Ereignisse bei entsprechendem Knopfdruck
        else if (ae.getSource() == col1 && (result-1)%3 == 0){
            //Wenn richtig gesetzt wurde und das Ereignis auftritt win Funktion mit dem Gewinn aufrufen
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
        //Da die JButtons für die Zahlen 1-36 nicht gespeichert werden Zahl aus Button lesen und dann Ergebnis prüfen
        else if(ae.getSource() == ro_pain ){
            int number = Integer.parseInt( ae.getSource().toString() );
            if (number == result){
                win( einsatz*35 );
            }
        }
        //Wenn keine Bedingung erfüllt wurde hat der Spieler verloren
        else{
            //und die loose Funktion wird aufgeruft
            loose(einsatz);
        }
        //Für den ergebnisString wird außerdem noch die Farbe bestimmt
        String res_color = "";
        if (getColor( result ) == 0){
            res_color = "Rot";
        }
        else if(getColor( result ) == 1){
            res_color = "Schwarz";
        }
        //Außerdem wird noch bestimmt ob die Zahl gerade, ungerade oder 0  ist
        String res_gerade = "";
        if (result/ 2 == 0 && result != 0){
            res_gerade = "gerade";
        }
        else{
            res_gerade = "ungerade";
        }
        //Der ergebnisString wird dann gesetzt
        ergebnisString.setText("Gedrehte Zahl: "+result+" "+res_color+" "+res_gerade);
        //und der Slider wird upgedatet
        gui.updateSlider();
    }

    //Funktion welche aufgerufen wird wenn gewonnen wurde
    private void win(int winsum){
        //Sound abspielen
        audioPlayer("win.wav", false);
        //Warten bis der Sound abgespielt wurde
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Den welcome String in eine Gewinn Nachricht ändern
        welcomeString.setText( "Herzlichen Glückwunsch. Du hast "+winsum+"€ gewonnen");
        //Geld aktualisieren
        gui.changeMoney( winsum );
    }

    //Funktion welche aufgerufen wird wenn verloren wurde
    private void loose(int sum){
        //Sound abspielen
        audioPlayer("loose.wav", false);
        //Warten bis der Sound abgespielt wurde
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Den welcome String in eine Verlier Nachricht ändern
        welcomeString.setText( "Schade. Du hast "+sum+"€ verloren");
        //Geld aktualisieren
        gui.changeMoney( -sum );
    }

    public java.util.List<Component> getGUIElements(){
        //ArrayList für die gui Elemente initialisieren
        List<Component> guilist = new ArrayList<>();
        //ergebnisString initialisieren, sowie Größe und Position setzen und zur Liste hinzufügen
        ergebnisString = new JLabel(  );
        ergebnisString.setSize( 300,10 );
        ergebnisString.setLocation( 50,200 );
        guilist.add( ergebnisString );
        //Home Button initialisieren, Größe und Position setzen und zur Liste hinzufügen
        home = new JButton( "<-" );
        home.addActionListener( this );
        home.setSize( 25,25 );
        home.setLocation( 0,0 );
        guilist.add( home );
        //welcomeString initialiseren, etc
        welcomeString = new JLabel("Herzlich Willkommen beim Roulette. Bitte setze auf ein Feld");
        welcomeString.setLocation( 150,10 );
        welcomeString.setSize( 500,15 );
        guilist.add( welcomeString );
        //Button für die 0 initalisieren und Optionen setzen sowie zur Liste hinzufügen
        ro_pain_0 = new JButton( String.valueOf( 0 ) );
        ro_pain_0.setLocation(500,25);
        ro_pain_0.setSize( 225,25 );
        ro_pain_0.setBackground( Color.GREEN );
        ro_pain_0.setOpaque(true);
        ro_pain_0.addActionListener( this );
        //Für ansprechendes Design und Ähnlichkeit zu einem echten Roulette Feld den Border entfernen
        ro_pain_0.setBorder(BorderFactory.createBevelBorder(0));
        guilist.add( ro_pain_0 );
        //Für jede Zahl von 1-36 den Button erstellen und zur Liste hinzufügen. Durch die for Schleife werden die Buttons erstellt und durch eine Berechnung direkt richtig positioniert.
        for ( int zahl = 1; zahl <= 36; zahl ++ ) {
            ro_pain = new JButton( String.valueOf( zahl ) );
            ro_pain.setLocation( 500+((zahl-1)%3)*75, 50+(((int) Math.ceil(((zahl-1)/3)))*25) );
            ro_pain.setSize( 75,25 );
            ro_pain.setBorder(BorderFactory.createBevelBorder(0));
            //Außerdem wird die entsprechende Farbe gesetzt
            if (getColor( zahl ) == 1){
                ro_pain.setBackground( Color.RED );
            }
            else {
                ro_pain.setBackground( Color.BLACK );
                ro_pain.setForeground( Color.WHITE );
            }
            ro_pain.setOpaque(true);
            //Der ActionListener wird gesetzt
            ro_pain.addActionListener( this );
            //und der Button wird zur Liste hinzugefügt
            guilist.add( ro_pain );
        }
        //mit der angepassten Funktion für die JButtons die Buttons erstellen und zur Liste hinzufüge
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
        //JLabel mit den Gewinn Faktoren erstellen. Mit HTML für die Absätze und die Formatierung sorgen
        JLabel faktoren = new JLabel( "<html><body><h2>Auszahlungsquoten</h2>" +
                "einzelne Zahl: x35<br>" +
                "vertikale Reihe (ohne 0): x2<br>" +
                "12er Bereich: x2<br>" +
                "1-18 oder 19-36: x1<br>" +
                "gerade/ungerade: x1<br>" +
                "schwarz/rot: x1<br>");
        faktoren.setLocation( 10,25 );
        faktoren.setSize( 200,150 );
        //Die Faktoren zur Liste hinzufügen
        guilist.add( faktoren );
        //Hintergrund mithilfe der Funktion setzen
        background = greenBackground();
        guilist.add( background );

        //Liste zurückgeben
        return guilist;
    }

    //Funktion zum zurückgeben der Farbe für das jeweilige Feld. 2 steht für die 0 (grün), 1 für rot und 0 für schwarz
    public int getColor(int x){
        if (x <= 0 || x > 36){
            return 2;
        }
        //Für die jewiligen Zahlen die jewilige Farbe zurückgeben
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
