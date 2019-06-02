package de.janmoritzmeyer.casino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Wuerfeln extends Casino implements ActionListener{

    JButton button1 = new JButton();
    JButton button2 = new JButton();
    JButton button3 = new JButton();
    JButton button4 = new JButton();
    JButton button5 = new JButton();
    JButton button6 = new JButton();
    JLabel label1 = new JLabel();
    JLabel label2 = new JLabel();
    JLabel label3 = new JLabel();
    JLabel label4 = new JLabel();

    public Wuerfeln(){

    }

    public void actionPerformed (ActionEvent ae){
        if(ae.getSource() == this.button1){

        }
        else if(ae.getSource() == this.button1){
        }
    }

    public List<Component> getGUIElements(){
        List<Component> guilist = new ArrayList<Component>();

        label1 = new JLabel("Herzlich Willkommen beim Würfeln. Zum spielen wähle eine Augenzahl");
        label1.setLocation( 100, 50 );
        label1.setSize( 600,10);
        label1.setHorizontalAlignment(JLabel.CENTER);
        guilist.add( label1 );

        label2 = new JLabel("1");
        label2.setIcon(scaleImage("dice_1.png",100,10));
        label2.setLocation( 300, 100 );
        label2.setSize( 100,10);
        guilist.add( label2 );

        label3 = new JLabel("2");
        label3.setLocation( 400, 100 );
        label3.setSize( 100,10);
        guilist.add( label3 );

        label4 = new JLabel("3");
        label4.setLocation( 500, 100 );
        label4.setSize( 100,10);
        guilist.add( label4 );

        button1.setText("3-6 (x 9)");
        button1.setLocation( 100,200 );
        button1.setSize( 100,50 );
        button1.addActionListener( this );
        guilist.add( button1 );

        button2.setText("7-10 (x 2)");
        button2.setLocation( 250,200 );
        button2.setSize( 100,50 );
        button2.addActionListener( this );
        guilist.add( button2 );

        button3.setText("12-15 (x 3)");
        button3.setLocation( 400,200 );
        button3.setSize( 100,50 );
        button3.addActionListener( this );
        guilist.add( button3 );

        button4.setText("15-18 (x 20)");
        button4.setLocation( 550,200 );
        button4.setSize( 100,50 );
        button4.addActionListener( this );
        guilist.add( button4 );

        return guilist;
    }
}
