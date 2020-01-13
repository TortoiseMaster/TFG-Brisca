package jordibarea.tfg;

public class Player {
    final int NUMBER_OF_CARDS_HAND = 3;

    private Card[] hand;

    public Player (){
        this.hand = new Card[NUMBER_OF_CARDS_HAND];
    }

    // Gets a card of the hand of the player
    public Card useCard(int i){
        return this.hand[i];
    }
    // puts a card in a position of the hand
    public void drawCard(int i, Card card){
        this.hand[i] = card;
    }
}
