package de.janmoritzmeyer.casino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Wuerfeln implements ActionListener{


    public Wuerfeln(){

    }

    public void actionPerformed (ActionEvent ae){
        /*if(ae.getSource() == this.jHomeButton1){
            this.initWuerfeln();
        }
        else if(ae.getSource() == this.jHomeButton2){
            setMoney( 40 );
        }
        else if(ae.getSource() == this.jHomeButton3){
            changeMoney( 10 );
        }*/
    }

    public List<Component> getGUIElements(){
        List<Component> guilist = new ArrayList<Component>();

        JButton jWuerfelButton1 = new JButton();
        jWuerfelButton1.setText("Black Jack");
        jWuerfelButton1.setLocation( 350,150 );
        jWuerfelButton1.setSize( 100,50 );
        jWuerfelButton1.addActionListener( this );
        guilist.add( jWuerfelButton1 );

        JButton jWuerfelButton2 = new JButton();
        jWuerfelButton2.setText("Black Jack");
        jWuerfelButton2.setLocation( 550,150 );
        jWuerfelButton2.setSize( 100,50 );
        jWuerfelButton2.addActionListener( this );
        guilist.add( jWuerfelButton2 );


        return guilist;
    }
}
