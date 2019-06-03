package de.janmoritzmeyer.casino;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


// Klasse welche aus der JFrame Klasse erbt. Hier wird die de.janmoritzmeyer.casino.GUI erstellt

public class GUI extends SuperCasino implements ActionListener {
    //Generelle Komponenten
    private int money;
    private JLabel moneyLabel;
    //die label für die € Anzeige vom Slider
    protected JLabel label5;
    protected JLabel label6;
    protected JLabel label7;
    //Range Model für die Slider, damit die Maximal Zahl dynamisch angepasst werden kann
    protected DefaultBoundedRangeModel bRangeModel;
    //Slider
    protected JSlider slider1;

    //Home Seite (Buttons für die drei Spiele)
    private JButton jHomeButton1;
    private JButton jHomeButton2;
    private JButton jHomeButton3;

    //Hintergrund und String für die Willkommensnachricht
    private JLabel welcomeString;
    private JLabel welcomeImage;

    //Die drei Spiel Objekte
    private Wuerfeln wuerfelGame;
    private BlackJack blackjackGame;
    private Roulette rouletteGame;



    public GUI(int x, int y){
        //Spielt die Hintergrund Musik in einer Schleife ab
        audioPlayer("background.wav", true);
        //Erstellt die Spiele und übergibt das derzeitige Objekt, um unter anderen auf die changeMoney Methode zuzugreifen
        wuerfelGame = new Wuerfeln(this);
        blackjackGame = new BlackJack(this);
        rouletteGame = new Roulette(this);

        //50€ Startgeld
        money = 50;
        //Das Fenster initialisieren
        initWindow(x,y);
        //Die einzelnen Komponenten initialisieren
        initJComponents();
        //Das Home Menü aktivieren
        initHome();
    }

    private void initWindow(int x, int y){
        //Titel setzen
        this.setTitle("JaMos Casino");
        //Fenster sichtbar machen
        this.setVisible(true);
        //Programm beenden beim klicken auf das x
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Layout auf null setzen
        this.setLayout( null );
        //Größe festlegen
        this.setSize(x, y);
        //Position des Fensters auf dem Bildschirm festlegen. Das Fenster befindet sich dann in der Mitte vom Bildschirm
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }

    private void checkMoney(){
        //Prüfen ob der Geldstand unter oder gleich 0€ ist
        if (money <= 0){
            //Falls der Geldstand 0€ ist, den HomeScreen aufrufen
            initHome();
            //Eine Nachricht einblenden
            JOptionPane.showMessageDialog(this,
                    "Du hast dein ganzes Geld verloren. Du fängst wieder mit 50€ an");
            //Und das Geld wieder auf 50€ setzen
            setMoney( 50 );
        }
    }

    //Getter Funktion zum zurückgeben des Geldes
    public int getMoney(){
        return money;
    }

    //Setter Funktion zum setzen des Geldes
    public void setMoney(int money){
        //Setzen des Integers für das Geld
        this.money = money;
        //Setzen des Strings für das Geld
        this.moneyLabel.setText("Geld: "+money+"€");
        //Prüfen ob der Geldbestand 0€ oder weniger beträgt
        checkMoney();
    }

    //Funktion zum verändern des Geldbestandes
    public void changeMoney(int money){
        //Die Veränderung addieren
        this.money += money;
        //String aktualisieren
        this.moneyLabel.setText("Geld: "+this.money+"€");
        //Geldbestand prüfen
        checkMoney();
    }

    //Funktion die aufgerufen wird, sobald ein Button geklickt wird
    public void actionPerformed (ActionEvent ae){
        //Prüfen der Buttons und das jeweilige Spiel starten
        if(ae.getSource() == this.jHomeButton1){
            this.initGame("w");
        }
        else if(ae.getSource() == this.jHomeButton2){
            this.initGame("b");
        }
        else if(ae.getSource() == this.jHomeButton3){
            this.initGame( "r" );
        }
    }

    //initialisieren der Home Bildschirm Objekte
    private void initJComponents(){
        //Text setzen und JLabel erstellen
        moneyLabel = new JLabel("Geld: "+money+"€");
        //Setzen der Position
        moneyLabel.setLocation( 700, 10 );
        //Setzen der Größe
        moneyLabel.setSize( 100,10);
        //Vordergrund Farbe setzen
        moneyLabel.setForeground( Color.WHITE );
        //Hintergrund Farbe setzen
        moneyLabel.setBackground( Color.BLACK );

        //weitere JLabel erstellen
        welcomeString = new JLabel( "Herzlich Willkommen "+System.getProperty("user.name")+" in JaMos Casino. Viel Spaß beim spielen" );
        welcomeString.setLocation( 0 ,50);
        welcomeString.setSize( 800,20);
        welcomeString.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeString.setForeground( Color.WHITE );
        welcomeString.setBackground( Color.BLACK );

        welcomeImage = new JLabel( "" );
        welcomeImage.setLocation( 0 ,0);
        welcomeImage.setSize( 800,400);
        welcomeImage.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeImage.setIcon(scaleImage("start_background.jpg",800,400));

        //JBUtton erstellen
        jHomeButton1 = new JButton();
        jHomeButton1.setText("Würfeln");
        jHomeButton1.setLocation( 250,150 );
        jHomeButton1.setSize( 100,50 );
        //ActionListener auf this setzen, damit die Funktion actionPerformed aufgerufen wird
        jHomeButton1.addActionListener( this);

        jHomeButton2 = new JButton();
        jHomeButton2.setText("Black Jack");
        jHomeButton2.setLocation( 350,150 );
        jHomeButton2.setSize( 100,50 );
        jHomeButton2.addActionListener( this );

        jHomeButton3 = new JButton();
        jHomeButton3.setText("Roulette");
        jHomeButton3.setLocation( 450,150 );
        jHomeButton3.setSize( 100,50 );
        jHomeButton3.addActionListener( this );

        /* Einsatz Slider */
        label5 = new JLabel("1 €");
        label5.setSize( 50,50);

        label6 = new JLabel( ( getMoney() )+"€" );
        label6.setSize( 50,50);

        label7 = new JLabel( ( getMoney()/2 )+"€" );
        label7.setSize( 50,50);

        //Range Model definieren
        bRangeModel = new DefaultBoundedRangeModel(getMoney()/2, 1, 1, getMoney()+1);
        slider1 = new JSlider(bRangeModel);
        slider1.setSize( 310,50 );
        //Setzen der Optik für den Slider
        slider1.setPaintTicks(true);
        slider1.setPaintLabels(true);
        slider1.createStandardLabels(1);
        //ChangeListener setzen
        slider1.addChangeListener( changeListner);
    }

    //ChangeListener erstellen
    ChangeListener changeListner = new ChangeListener() {
        @Override
        //Funktion welche aufgerufen wenn sich der Slidervalue verändert
        public void stateChanged(ChangeEvent event) {
            //Wenn der Slider über dem Money Wert ist, Slider auf Money zurücksetzen
            if (slider1.getValue() > getMoney()){
                slider1.setValue(getMoney());

            }
            //Label7 setzen, welches den aktuellen Wert für das Setzen anzeigt
            label7.setText( String.valueOf( slider1.getValue()+"€" ) );
        }

    };

    //Setzt den Frame auf die Home Komponenten
    public void initHome() {
        //ruft die resetContentpane Funktion auf, damit alle Elemente entfernt werden
        resetContentpane();
        //money Text weiß machen, damit man ihn erkennen kann
        moneyLabel.setForeground( Color.WHITE );
        //Die jeweiligen Elemente für den HomeScreen hinzufügen
        getContentPane().add(welcomeString);
        getContentPane().add(jHomeButton1);
        getContentPane().add(jHomeButton2);
        getContentPane().add(jHomeButton3);
        getContentPane().add(welcomeImage);
    }

    //Funktion zum initialisieren der BlackJack Komponenten
    private void initBlackJackComponents(){
        //leeren des Fensters
        resetContentpane();
        //Elemente abfragen und darstellen
        for (Component guielement:blackjackGame.getGUIElements()) {
            getContentPane().add(guielement);
        }
    }

    //Neues darstellen der Karten, etc wenn z.b. eine neue Karte hinzugefügt wurde
    public void reloadBlackJack(){
        initBlackJackComponents();
    }

    //Funktion welche von den Buttons aufgerufen wird, mit dem String der den Spiel namen enthällt
    private void initGame(String game){
        //zurücksetzen des Fensters
        resetContentpane();
        //Wenn würfel spiel gestartet werden soll
        if (game == "w"){
            //Slider hinzufügen
            addSlider(0,0);
            //Komponenten hinzufügen
            for (Component guielement:wuerfelGame.getGUIElements()) {
                getContentPane().add(guielement);
            }
            //Wenn es das erste Spiel ist, Willkommens Nachricht mit Erklärung des Spiels
            if (wuerfelGame.firstgame){
                JOptionPane.showMessageDialog(this, "Herzlich Willkommen beim Würfeln. \nDu kannst unten die Augensumme auswählen und deinen Einsatz tätigen");
                wuerfelGame.firstgame = false;
            }
        }
        //Black Jack Spiel wird gestartet
        else if (game == "b") {
            //Black Jack Komponenten darstellen
            initBlackJackComponents();
            //neues Spiel starten
            blackjackGame.startnewGame();
        }
        //Roulette Spiel starten
        else if(game == "r"){
            //Slider hinzufügen, außerdem den Slider verschieben
            addSlider(-200,50);
            //Komponenten hinzufügen
            for (Component guielement:rouletteGame.getGUIElements()) {
                getContentPane().add(guielement);
            }
        }
        else {

        }
    }

    //Funktion zum zurücksetzen des Fensters
    private void resetContentpane(){
        //Aufrufen der Funktionen
        getContentPane().invalidate();
        getContentPane().validate();
        getContentPane().repaint();
        getContentPane().removeAll();
        //setzen der Farbe für das moneyLabel
        moneyLabel.setForeground( Color.BLACK );
        //moneyLabel Standardmäßig hinzufügen
        getContentPane().add(moneyLabel);
    }

    //Funktion mit welcher der Slider ausgelagert wurde
    private void addSlider(int x, int y){
        //Position verändern wenn die Slider verschieben
        slider1.setLocation( 225+x, 255+y );
        label7.setLocation( 370+x, 235+y );
        label6.setLocation( 540+x, 250+y );
        label5.setLocation( 210+x, 250+y );
        //hinzufügen der Slider Elemente zum ContentPane
        getContentPane().add(slider1);
        getContentPane().add(label5);
        getContentPane().add(label6);
        getContentPane().add(label7);
    }

    //Slider updaten
    public void updateSlider(){
        //Aktuellen Wert setzen (Hälfte des Geldes)
        bRangeModel.setValue( getMoney()/2 );
        //Minimum setzen
        bRangeModel.setMinimum( 1 );
        //Maximum setzen
        bRangeModel.setMaximum( getMoney()+1 );
        //label6 setzen, welches dafür zuständig ist den maximalbetrag anzuzeigen
        label6.setText( String.valueOf( getMoney() ) );
    }
}
