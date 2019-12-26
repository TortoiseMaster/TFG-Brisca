package jordibarea.tfg;

import java.util.Random;



public class Deck {

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
    private Card[] deck;
    public Deck (){
        this.deck = new Card[48];

        for (int j = 0; j < 4; j++){
            for (int i = 1; i <= 12; i++){
                this.deck[(i-1)+j*12] = new Card(i,j,imgIndex[(i-1)+j*12]);
            }
        }
        this.shuffle();
    }

    public void shuffle() {
        Random r = new Random();
        Card copy;
        int random;
        for (int i = 0; i < this.deck.length; i++){
            random = r.nextInt(this.deck.length);
            copy = this.deck[i];
            this.deck[i] = this.deck[random];
            this.deck[random] = copy;
        }
        // force7();
    }

    private void force7() {
        for (int i = 0; i < 48; i++) {
            if(this.deck[i].getType() == this.deck[0].getType() && this.deck[i].getValue() == 7){
                Card aux = this.deck[i];
                this.deck[i] = this.deck[47];
                this.deck[47] = aux;
            }
        }
        for (int i = 0; i < 48; i++) {
            if(this.deck[i].getType() == this.deck[0].getType() && this.deck[i].getValue() == 1){
                Card aux = this.deck[i];
                this.deck[i] = this.deck[0];
                this.deck[0] = aux;
            }
        }
        for (int i = 0; i < 48; i++) {
            System.out.println(i + ": " +this.deck[i]);
        }
    }

    public Card getCard(int i){
        return this.deck[i];
    }

    public Card swapCards(int i, Card card){
        Card copy = this.deck[i];
        this.deck[i] = card;
        return copy;
    }
}
