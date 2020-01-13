package jordibarea.tfg;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;



public class Deck {
    // We need this array in order to be able to give an image ti every card
    final int[] imgIndex =
            {R.drawable.image1espades,
                    R.drawable.image2espades,
                    R.drawable.image3espades,
                    R.drawable.image4espades,
                    R.drawable.image5espades,
                    R.drawable.image6espades,
                    R.drawable.image7espades,
                    R.drawable.image8espades,
                    R.drawable.image9espades,
                    R.drawable.image10espades,
                    R.drawable.image11espades,
                    R.drawable.image12espades,

                    R.drawable.image1cups,
                    R.drawable.image2cups,
                    R.drawable.image3cups,
                    R.drawable.image4cups,
                    R.drawable.image5cups,
                    R.drawable.image6cups,
                    R.drawable.image7cups,
                    R.drawable.image8cups,
                    R.drawable.image9cups,
                    R.drawable.image10cups,
                    R.drawable.image11cups,
                    R.drawable.image12cups,

                    R.drawable.image1clubs,
                    R.drawable.image2clubs,
                    R.drawable.image3clubs,
                    R.drawable.image4clubs,
                    R.drawable.image5clubs,
                    R.drawable.image6clubs,
                    R.drawable.image7clubs,
                    R.drawable.image8clubs,
                    R.drawable.image9clubs,
                    R.drawable.image10clubs,
                    R.drawable.image11clubs,
                    R.drawable.image12clubs,

                    R.drawable.image1gold,
                    R.drawable.image2gold,
                    R.drawable.image3gold,
                    R.drawable.image4gold,
                    R.drawable.image5gold,
                    R.drawable.image6gold,
                    R.drawable.image7gold,
                    R.drawable.image8gold,
                    R.drawable.image9gold,
                    R.drawable.image10gold,
                    R.drawable.image11gold,
                    R.drawable.image12gold,

            };
    final int NUMBER_OF_CARDS_DECK = 48;
    final int NUMBER_OF_SUITS = 4;
    final int NUMBER_OF_VALUES = 12;

    private Card[] deck;

    // Creats a deck and shuffle it, bc we don't want a not shuffled deck for anything
    public Deck (){
        this.deck = new Card[NUMBER_OF_CARDS_DECK];

        for (int j = 0; j < NUMBER_OF_SUITS; j++){
            for (int i = 1; i <= NUMBER_OF_VALUES; i++){
                this.deck[(i-1)+j*NUMBER_OF_VALUES] = new Card(i,j,imgIndex[(i-1)+j*NUMBER_OF_VALUES]);
            }
        }
        Collections.shuffle(Arrays.asList(this.deck));
    }

    // Method that allows to have a seven as the first card in order to test the funcionality of changing the brisca
    private void force7() {
        for (int i = 0; i < NUMBER_OF_CARDS_DECK; i++) {
            if(this.deck[i].getType() == this.deck[0].getType() && this.deck[i].getValue() == 7){
                Card aux = this.deck[i];
                this.deck[i] = this.deck[NUMBER_OF_CARDS_DECK - 1];
                this.deck[NUMBER_OF_CARDS_DECK -1] = aux;
            }
        }
        for (int i = 0; i < NUMBER_OF_CARDS_DECK; i++) {
            if(this.deck[i].getType() == this.deck[0].getType() && this.deck[i].getValue() == 1){
                Card aux = this.deck[i];
                this.deck[i] = this.deck[0];
                this.deck[0] = aux;
            }
        }
        for (int i = 0; i < NUMBER_OF_CARDS_DECK; i++) {
            System.out.println(i + ": " +this.deck[i]);
        }
    }

    // returns a card of the deck
    public Card getCard(int i){
        return this.deck[i];
    }

    // Change the card given with the card in a position and returns the card in that position. In order to change the brisca
    public Card swapCards(int i, Card card){
        Card copy = this.deck[i];
        this.deck[i] = card;
        return copy;
    }
}
