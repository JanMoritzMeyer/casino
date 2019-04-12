package de.janmoritzmeyer.casino;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// Klasse welche aus der JFrame Klasse erbt. Hier wird die de.janmoritzmeyer.casino.GUI erstellt

public class GUI extends JFrame implements ActionListener {
    //Buttons deklarieren
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;

    //JLabel deklarieren
    private JLabel moneyLabel;

    //Geld
    private int money;

    public GUI(int x, int y){
        //50€ Startgeld
        money = 50;
        initWindow(x,y);
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

    public void actionPerformed (ActionEvent ae){
        if(ae.getSource() == this.jButton1){
            this.initWuerfeln();
        }
        else if(ae.getSource() == this.jButton2){
            setMoney( 40 );
        }
        else if(ae.getSource() == this.jButton3){
            setMoney( 60 );
        }
    }

    private void initHome() {
        getContentPane().invalidate();
        getContentPane().validate();
        getContentPane().repaint();
        getContentPane().removeAll();

        moneyLabel = new JLabel("Geld: "+money+"€");
        moneyLabel.setLocation( 700, 10 );
        moneyLabel.setSize( 100,10);
        getContentPane().add(moneyLabel);

        JLabel welcomeString = new JLabel( "Herzlich Willkommen "+System.getProperty("user.name")+" in JaMos Casino. Viel Spaß beim spielen" );
        welcomeString.setLocation( 0 ,50);
        welcomeString.setSize( 800,20);
        welcomeString.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(welcomeString);

        jButton1 = new JButton();
        jButton1.setText("Würfeln");
        jButton1.setLocation( 250,150 );
        jButton1.setSize( 100,50 );
        jButton1.addActionListener( this);
        getContentPane().add(jButton1);

        jButton2 = new JButton();
        jButton2.setText("Black Jack");
        jButton2.setLocation( 350,150 );
        jButton2.setSize( 100,50 );
        jButton2.addActionListener( this );
        getContentPane().add(jButton2);

        jButton3 = new JButton();
        jButton3.setText("Roulette");
        jButton3.setLocation( 450,150 );
        jButton3.setSize( 100,50 );
        jButton3.addActionListener( this );
        getContentPane().add(jButton3);
    }

    private void initWuerfeln(){
        getContentPane().invalidate();
        getContentPane().validate();
        getContentPane().repaint();
        getContentPane().removeAll();

        JButton jButton4 = new JButton();
        jButton4.setText("Black Jack");
        jButton4.setLocation( 350,150 );
        jButton4.setSize( 100,50 );
        jButton4.addActionListener( this );
        getContentPane().add(jButton4);

        JButton jButton5 = new JButton();
        jButton5.setText("Roulette");
        jButton5.setLocation( 450,350 );
        jButton5.setSize( 100,50 );
        jButton5.addActionListener( this );
        getContentPane().add(jButton5);
    }
}
