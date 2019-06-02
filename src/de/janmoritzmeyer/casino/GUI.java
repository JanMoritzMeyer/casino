package de.janmoritzmeyer.casino;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.applet.*;


// Klasse welche aus der JFrame Klasse erbt. Hier wird die de.janmoritzmeyer.casino.GUI erstellt

public class GUI extends Casino implements ActionListener {
    //Generelle Komponenten
    private int money;
    private JLabel moneyLabel;
    private AudioClip helloClip;

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



    public GUI(int x, int y){
        wuerfelGame = new Wuerfeln();
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

    public void setMoney(int money){
        this.money = money;
        this.moneyLabel.setText("Geld: "+money+"€");
    }

    public void changeMoney(int money){
        this.money += money;
        this.moneyLabel.setText("Geld: "+this.money+"€");
    }

    public void actionPerformed (ActionEvent ae){
        if(ae.getSource() == this.jHomeButton1){
            this.initWuerfeln();
        }
        else if(ae.getSource() == this.jHomeButton2){
            setMoney( 40 );
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

        welcomeString = new JLabel( "Herzlich Willkommen "+System.getProperty("user.name")+" in JaMos Casino. Viel Spaß beim spielen" );
        welcomeString.setLocation( 0 ,50);
        welcomeString.setSize( 800,20);
        welcomeString.setHorizontalAlignment(SwingConstants.CENTER);

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
    }

    private void initHome() {
        resetContentpane();

        getContentPane().add(welcomeString);
        getContentPane().add(jHomeButton1);
        getContentPane().add(jHomeButton2);
        getContentPane().add(jHomeButton3);
        getContentPane().add(welcomeImage);
    }

    private void initWuerfeln(){
        resetContentpane();
        for (Component guielement:wuerfelGame.getGUIElements()) {
            getContentPane().add(guielement);
        }
        JOptionPane.showMessageDialog(this,
                "Herzlich Willkommen beim Würfeln. Du kannst unten die Augensumme auswählen und deinen Einsatz tätigen");
    }

    private void resetContentpane(){
        getContentPane().invalidate();
        getContentPane().validate();
        getContentPane().repaint();
        getContentPane().removeAll();
        getContentPane().add(moneyLabel);
    }


}
