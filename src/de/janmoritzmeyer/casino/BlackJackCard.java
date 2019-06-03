package de.janmoritzmeyer.casino;

import javax.swing.*;

public class BlackJackCard extends SuperCasino {
    public JLabel card;

    public BlackJackCard(int number){
        this.card = new JLabel();
        this.card.setSize( 100,100);
        switch (number){
            case 1:
                this.card.setIcon( scaleImage( "card_2.png",100,100 ) );
                break;
            case 2:
                this.card.setIcon( scaleImage( "card_3.png",100,100 ) );
                break;
            case 3:
                this.card.setIcon( scaleImage( "card_4.png",100,100 ) );
                break;
            case 4:
                this.card.setIcon( scaleImage( "card_5.png",100,100 ) );
                break;
            case 5:
                this.card.setIcon( scaleImage( "card_6.png",100,100 ) );
                break;
            case 6:
                this.card.setIcon( scaleImage( "card_7.png",100,100 ) );
                break;
            case 7:
                this.card.setIcon( scaleImage( "card_8.png",100,100 ) );
                break;
            case 8:
                this.card.setIcon( scaleImage( "card_9.png",100,100 ) );
                break;
            case 9:
                this.card.setIcon( scaleImage( "card_10.png",100,100 ) );
                break;
            case 10:
                this.card.setIcon( scaleImage( "card_a.png",100,100 ) );
                break;
            case 11:
                this.card.setIcon( scaleImage( "card_k.png",100,100 ) );
                break;
            case 12:
                this.card.setIcon( scaleImage( "card_j.png",100,100 ) );
                break;
            case 13:
                this.card.setIcon( scaleImage( "card_q.png",100,100 ) );
                break;
            case 14:
                this.card.setIcon( scaleImage( "card_turned.png",100,100 ) );
                break;
        }
    }
}