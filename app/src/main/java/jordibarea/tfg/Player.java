package jordibarea.tfg;

public class Player {
    private Card[] hand;
    private Card[] win;
    private int totalWins;
    private int points;

    public Player (){
        this.hand = new Card[3];
        this.win = new Card[48];
        this.totalWins = 0;
    }

    public Card useCard(int i){
        return this.hand[i];
    }
    public void drawCard(int i, Card card){
        this.hand[i] = card;
    }

    public void winCards(Card card1,Card card2){
        win[totalWins] = card1;
        win[totalWins+1] = card2;
        totalWins += 2;
    }

    public int getScore(){
        this.points = 0;
        for (int i = 0; i < this.totalWins; i++){
            points +=0;
        }
        return points;
    }

}
