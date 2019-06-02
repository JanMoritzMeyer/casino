package de.janmoritzmeyer.casino;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.applet.*;


// Klasse welche aus der JFrame Klasse erbt. Hier wird die de.janmoritzmeyer.casino.GUI erstellt

public class GUI extends Casino implements ActionListener {
    //Generelle Komponenten
    private int money;
    private JLabel moneyLabel;

    //Home Seite
    private JButton jHomeButton1;
    private JButton jHomeButton2;
    private JButton jHomeButton3;

    private JLabel welcomeString;
    private JLabel welcomeImage;

    //Würfel Seite
    private JButton jWuerfelButton1;
    private JButton jWuerfelButton2;

    private Wuerfeln wuerfelGame;
    private BlackJack blackjackGame;
    protected JLabel label5;
    protected JLabel label6;
    protected JLabel label7;
    protected DefaultBoundedRangeModel bRangeModel;
    protected JSlider slider1;


    public GUI(int x, int y){
        wuerfelGame = new Wuerfeln(this);
        blackjackGame = new BlackJack(this);
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
        this.setTitle("JaMos Casino");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout( null );
        this.setSize(x, y);
    }

    private void checkMoney(){
        if (money <= 0){
            initHome();
            JOptionPane.showMessageDialog(this,
                    "Du hast dein ganzes Geld verloren. Du fängst wieder mit 50€ an");
            setMoney( 50 );
        }
    }

    public int getMoney(){
        return money;
    }

    public void setMoney(int money){
        this.money = money;
        this.moneyLabel.setText("Geld: "+money+"€");
        checkMoney();
    }

    public void changeMoney(int money){
        this.money += money;
        this.moneyLabel.setText("Geld: "+this.money+"€");
        checkMoney();
    }

    public void actionPerformed (ActionEvent ae){
        if(ae.getSource() == this.jHomeButton1){
            this.initGame("w");
        }
        else if(ae.getSource() == this.jHomeButton2){
            this.initGame("b");
        }
        else if(ae.getSource() == this.jHomeButton3){
            changeMoney( 10 );
        }
    }

    //initialisieren der Home Bildschirm Objekte
    private void initJComponents(){
        moneyLabel = new JLabel("Geld: "+money+"€");
        moneyLabel.setLocation( 700, 10 );
        moneyLabel.setSize( 100,10);
        moneyLabel.setForeground( Color.WHITE );
        moneyLabel.setBackground( Color.BLACK );

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

        jHomeButton1 = new JButton();
        jHomeButton1.setText("Würfeln");
        jHomeButton1.setLocation( 250,150 );
        jHomeButton1.setSize( 100,50 );
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
        label5 = new JLabel("1");
        label5.setLocation( 200, 250 );
        label5.setSize( 50,50);

        label6 = new JLabel( String.valueOf( getMoney() ) );
        label6.setLocation( 550, 250 );
        label6.setSize( 50,50);

        label7 = new JLabel( String.valueOf( getMoney()/2 ) );
        label7.setLocation( 370, 235 );
        label7.setSize( 50,50);

        bRangeModel = new DefaultBoundedRangeModel(getMoney()/2, 1, 1, getMoney()+1);
        slider1 = new JSlider(bRangeModel);
        slider1.setLocation( 225, 255 );
        slider1.setSize( 310,50 );
        slider1.setPaintTicks(true);
        slider1.setPaintLabels(true);
        slider1.createStandardLabels(1);
        slider1.addChangeListener( changeListner);
    }

    ChangeListener changeListner = new ChangeListener() {

        @Override

        public void stateChanged(ChangeEvent event) {
            if (slider1.getValue() > getMoney()){
                slider1.setValue(getMoney());

            }
            label7.setText( String.valueOf( slider1.getValue() ) );
        }

    };

    public void initHome() {
        resetContentpane();
        moneyLabel.setForeground( Color.WHITE );
        getContentPane().add(welcomeString);
        getContentPane().add(jHomeButton1);
        getContentPane().add(jHomeButton2);
        getContentPane().add(jHomeButton3);
        getContentPane().add(welcomeImage);
    }

    private void initBlackJackComponents(){
        resetContentpane();
        for (Component guielement:blackjackGame.getGUIElements()) {
            getContentPane().add(guielement);
        }
    }

    public void reloadBlackJack(){
        initBlackJackComponents();
    }

    private void initGame(String game){
        resetContentpane();
        if (game == "w"){
            addSlider();
            for (Component guielement:wuerfelGame.getGUIElements()) {
                getContentPane().add(guielement);
            }
            if (wuerfelGame.firstgame){
                JOptionPane.showMessageDialog(this, "Herzlich Willkommen beim Würfeln. Du kannst unten die Augensumme auswählen und deinen Einsatz tätigen");
                wuerfelGame.firstgame = false;
            }
        } else if (game == "b") {
            initBlackJackComponents();
            blackjackGame.startnewGame();
        }
        else {

        }
    }

    private void resetContentpane(){
        getContentPane().invalidate();
        getContentPane().validate();
        getContentPane().repaint();
        getContentPane().removeAll();
        moneyLabel.setForeground( Color.BLACK );
        getContentPane().add(moneyLabel);
    }

    private void addSlider(){
        getContentPane().add(slider1);
        getContentPane().add(label5);
        getContentPane().add(label6);
        getContentPane().add(label7);
    }


}
