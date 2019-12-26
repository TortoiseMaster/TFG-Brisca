package jordibarea.tfg.Activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import jordibarea.tfg.Card;
import jordibarea.tfg.Deck;
import jordibarea.tfg.MusicPlayer;
import jordibarea.tfg.Player;
import jordibarea.tfg.R;

public class GameActivity extends Activity {
    private int cardsLeft = 48;
    private Deck deck = new Deck();

    private Random r = new Random();
    private Player player = new Player();
    private Player rival  = new Player();
    private Card pal;
    private boolean changeBrisca, firstTurn;
    private int turn;
    private int playerPoints;
    private int playerCardUsed, rivalCardUsed, lastHands = 0;

    ImageButton palCard;
    ImageView rivalThrownImg, playerThrownImg;
    TextView textCardsRemaining;
    MediaPlayer soundCard, loseSound, winSound;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        firstTurn = getIntent().getBooleanExtra("firstTurn", true);

        rivalThrownImg = (ImageView) findViewById(R.id.imgRivalThrown);
        playerThrownImg = (ImageView) findViewById(R.id.imgPlayerThrown);
        palCard = (ImageButton) findViewById(R.id.imgPal);
        textCardsRemaining = (TextView) findViewById(R.id.remainingCards);
        playerPoints = 0;

        soundCard = MediaPlayer.create(getApplicationContext(), R.raw.card_move);
        winSound = MediaPlayer.create(getApplicationContext(), R.raw.hand_win);
        loseSound = MediaPlayer.create(getApplicationContext(), R.raw.lose_hand);

        if(firstTurn) {
            turn = 0;
        }
        else {
            turn = 1;
        }
        initGame();
        initButtons();
    }
    private void initGame(){
        this.cardsLeft -= 1;
        textCardsRemaining.setContentDescription("Cartas en la baraja "+this.cardsLeft);
        textCardsRemaining.setText("" + this.cardsLeft);
        this.player.drawCard(0,this.deck.getCard(this.cardsLeft));
        ImageButton next = (ImageButton) findViewById(R.id.buttonCard1);
        // next.setText(this.deck.getCard(this.cardsLeft).toString());
        next.setImageResource(this.deck.getCard(this.cardsLeft).getImgID());
        next.announceForAccessibility("Jugador roba "+this.deck.getCard(this.cardsLeft).toString());
        next.setContentDescription("" + this.deck.getCard(this.cardsLeft).toString());

        this.cardsLeft -=1;
        textCardsRemaining.setContentDescription("Cartas en la baraja "+this.cardsLeft);
        textCardsRemaining.setText("" + this.cardsLeft);
        this.rival.drawCard(0,this.deck.getCard(this.cardsLeft));
        textCardsRemaining.setContentDescription("Cartas en la baraja "+this.cardsLeft);
        textCardsRemaining.setText("" + this.cardsLeft);
        this.cardsLeft -=1;
        textCardsRemaining.setContentDescription("Cartas en la baraja "+this.cardsLeft);
        textCardsRemaining.setText("" + this.cardsLeft);
        this.player.drawCard(1,this.deck.getCard(this.cardsLeft));
        next = (ImageButton) findViewById(R.id.buttonCard2);
        // next.setText(this.deck.getCard(this.cardsLeft).toString());
        next.setImageResource(this.deck.getCard(this.cardsLeft).getImgID());
        next.announceForAccessibility("Jugador roba "+this.deck.getCard(this.cardsLeft).toString());
        next.setContentDescription("" + this.deck.getCard(this.cardsLeft).toString());

        this.cardsLeft -=1;
        textCardsRemaining.setContentDescription("Cartas en la baraja "+this.cardsLeft);
        textCardsRemaining.setText("" + this.cardsLeft);
        this.rival.drawCard(1,this.deck.getCard(this.cardsLeft));

        this.cardsLeft -=1;
        textCardsRemaining.setContentDescription("Cartas en la baraja "+this.cardsLeft);
        textCardsRemaining.setText("" + this.cardsLeft);
        this.player.drawCard(2,this.deck.getCard(this.cardsLeft));
        next = (ImageButton) findViewById(R.id.buttonCard3);
        // next.setText(this.deck.getCard(this.cardsLeft).toString());
        next.setImageResource(this.deck.getCard(this.cardsLeft).getImgID());
        next.announceForAccessibility("Jugador roba "+this.deck.getCard(this.cardsLeft).toString());
        next.setContentDescription("" + this.deck.getCard(this.cardsLeft).toString());

        this.cardsLeft -=1;
        textCardsRemaining.setContentDescription("Cartas en la baraja "+this.cardsLeft);
        textCardsRemaining.setText("" + this.cardsLeft);
        this.rival.drawCard(2,this.deck.getCard(this.cardsLeft));

        this.pal = this.deck.getCard(0);
        // palCard.setText(this.deck.getCard(0).toString());
        palCard.setImageResource(this.deck.getCard(0).getImgID());
        palCard.announceForAccessibility("La brisca es "+this.deck.getCard(0).toString());
        palCard.setContentDescription("La brisca es  " + this.deck.getCard(0).toString());

        TextView nextText = (TextView) findViewById(R.id.textYourPoints);
        nextText.setText("Tus puntos:" + playerPoints);
        if (turn == 1){
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rivalInteraction();
                }
            }, 2000);
        }
    }


    private void rivalInteraction(){
        if (cardsLeft != 0) { // Still cards in the deck
            if (turn == 1){
                rivalCardUsed = IA();
                rivalThrownImg.setImageResource(rival.useCard(rivalCardUsed).getImgID());
                soundCard.start();
                rivalThrownImg.announceForAccessibility("Rival usa " + rival.useCard(rivalCardUsed).toString());
                rivalThrownImg.setContentDescription("Carta usada por el rival " + rival.useCard(rivalCardUsed).toString());
            }
            else {
                rivalCardUsed = IA();
                rivalThrownImg.setImageResource(rival.useCard(rivalCardUsed).getImgID());
                soundCard.start();
                rivalThrownImg.announceForAccessibility("Rival usa " + rival.useCard(rivalCardUsed).toString());
                rivalThrownImg.setContentDescription("Carta usada por el rival " + rival.useCard(rivalCardUsed).toString());
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    gameLogic();
                    playerThrownImg.setImageResource(android.R.color.transparent);
                    playerThrownImg.setContentDescription(null);
                    rivalThrownImg.setImageResource(android.R.color.transparent);
                    rivalThrownImg.setContentDescription(null);

                    }
                }, 3000);
            }
        } else {  // Only the cards in the hand remain
            if (turn == 1){
                rivalCardUsed = IA();
                rivalThrownImg.setImageResource(rival.useCard(rivalCardUsed).getImgID());
                soundCard.start();
                rivalThrownImg.announceForAccessibility("Rival usa " + rival.useCard(rivalCardUsed).toString());
                rivalThrownImg.setContentDescription("Carta usada por el rival " + rival.useCard(rivalCardUsed).toString());
                if (rivalCardUsed == 0) {
                    ImageView rivalCardImg = (ImageView) findViewById(R.id.rivalCard1);
                    rivalCardImg.setImageResource(android.R.color.transparent);
                }
                else if (rivalCardUsed == 1) {
                    ImageView rivalCardImg = (ImageView) findViewById(R.id.rivalCard2);
                    rivalCardImg.setImageResource(android.R.color.transparent);
                }
                else {
                    ImageView rivalCardImg = (ImageView) findViewById(R.id.rivalCard3);
                    rivalCardImg.setImageResource(android.R.color.transparent);
                }
            }
            else {
                rivalCardUsed = IA();
                rivalThrownImg.setImageResource(rival.useCard(rivalCardUsed).getImgID());
                soundCard.start();
                rivalThrownImg.announceForAccessibility("Rival usa " + rival.useCard(rivalCardUsed).toString());
                rivalThrownImg.setContentDescription("Carta usada por el rival " + rival.useCard(rivalCardUsed).toString());
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gameLogic();
                        playerThrownImg.setImageResource(android.R.color.transparent);
                        playerThrownImg.setContentDescription(null);
                        rivalThrownImg.setImageResource(android.R.color.transparent);
                        rivalThrownImg.setContentDescription(null);
                        rival.drawCard(rivalCardUsed,null);
                    }
                }, 3000);
                if (rivalCardUsed == 0) {
                    ImageView rivalCardImg = (ImageView) findViewById(R.id.rivalCard1);
                    rivalCardImg.setImageResource(android.R.color.transparent);
                }
                else if (rivalCardUsed == 1) {
                    ImageView rivalCardImg = (ImageView) findViewById(R.id.rivalCard2);
                    rivalCardImg.setImageResource(android.R.color.transparent);
                }
                else {
                    ImageView rivalCardImg = (ImageView) findViewById(R.id.rivalCard3);
                    rivalCardImg.setImageResource(android.R.color.transparent);
                }
            }
        }
    }

    private int IA() {
        double effectiveValue;
        double bestValue;
        int bestCard = 0;
        if (turn == 0){
            bestValue = -5000;
            for (int i = 0; i < 3; i++) {
                if (rival.useCard(i) != null){
                    effectiveValue = 9.5 * getCardReward(rival.useCard(i), player.useCard(playerCardUsed)) - 2 * getCardCost(rival.useCard(i));
                    if (effectiveValue > bestValue) {
                        bestCard = i;
                        bestValue = effectiveValue;
                    }
                }
            }
        }
        else {
            bestValue = 5000;
            for (int i = 0; i < 3; i++){
                if (rival.useCard(i) != null){
                    effectiveValue = getCardCost(rival.useCard(i));
                    if (effectiveValue < bestValue){
                        bestCard = i;
                        bestValue = effectiveValue;
                    }
                }
            }
        }
        return bestCard;
    }

    public void gameLogic() {
        ImageButton pal = (ImageButton) findViewById(R.id.imgPal);


        turn = decideWinner(player.useCard(playerCardUsed), rival.useCard(rivalCardUsed), turn);
        if (cardsLeft == 0) {

            if (lastHands != 2) {
                finalCards();
                lastHands++;
            } else {
                Intent myIntent = new Intent(this.getBaseContext(), WinActivity.class);
                myIntent.putExtra("points",playerPoints);
                startActivityForResult(myIntent, 0);
            }
        }
        else {
            if (turn == 0){
                winSound.start();
                playerThrownImg.announceForAccessibility("Gana jugador");
                addPoints(player.useCard(playerCardUsed));
                addPoints(rival.useCard(rivalCardUsed));

                this.cardsLeft -= 1;
                textCardsRemaining.setContentDescription("Cartas en la baraja "+this.cardsLeft);
                textCardsRemaining.setText("" + this.cardsLeft);
                this.player.drawCard(playerCardUsed,this.deck.getCard(this.cardsLeft));
                if(this.cardsLeft == 0){
                    ImageView deckImage = (ImageView) findViewById(R.id.deck);
                    rivalThrownImg.announceForAccessibility("Últimas manos");
                    deckImage.setVisibility(View.INVISIBLE);
                    palCard.setEnabled(false);
                    palCard.setAlpha(0.5f);
                }
                if(playerCardUsed == 0){
                    ImageButton next = (ImageButton) findViewById(R.id.buttonCard1);
                    next.setImageResource(this.deck.getCard(this.cardsLeft).getImgID());
                    next.announceForAccessibility("Jugador roba " + this.deck.getCard(this.cardsLeft).toString());
                    next.setContentDescription("" + this.deck.getCard(this.cardsLeft).toString());
                }else if(playerCardUsed == 1){
                    ImageButton next = (ImageButton) findViewById(R.id.buttonCard2);
                    next.setImageResource(this.deck.getCard(this.cardsLeft).getImgID());
                    next.announceForAccessibility("Jugador roba " + this.deck.getCard(this.cardsLeft).toString());
                    next.setContentDescription("" + this.deck.getCard(this.cardsLeft).toString());
                }else{
                    ImageButton next = (ImageButton) findViewById(R.id.buttonCard3);
                    next.setImageResource(this.deck.getCard(this.cardsLeft).getImgID());
                    next.announceForAccessibility("Jugador roba " + this.deck.getCard(this.cardsLeft).toString());
                    next.setContentDescription("" + this.deck.getCard(this.cardsLeft).toString());
                }
                this.cardsLeft -= 1;
                textCardsRemaining.setContentDescription("Cartas en la baraja "+this.cardsLeft);
                textCardsRemaining.setText("" + this.cardsLeft);
                this.rival.drawCard(rivalCardUsed,this.deck.getCard(this.cardsLeft));
                if(this.cardsLeft == 0){
                    ImageView deckImage = (ImageView) findViewById(R.id.deck);
                    rivalThrownImg.announceForAccessibility("Últimas manos");
                    deckImage.setVisibility(View.INVISIBLE);
                    palCard.setEnabled(false);
                    palCard.setAlpha(0.5f);
                }
                initButtons();
            } else {
                loseSound.start();
                playerThrownImg.announceForAccessibility("Gana rival");
                this.cardsLeft -= 1;
                textCardsRemaining.setContentDescription("Cartas en la baraja "+this.cardsLeft);
                textCardsRemaining.setText("" + this.cardsLeft);
                this.rival.drawCard(rivalCardUsed,this.deck.getCard(this.cardsLeft));
                if(this.cardsLeft == 0){
                    ImageView deckImage = (ImageView) findViewById(R.id.deck);
                    rivalThrownImg.announceForAccessibility("Últimas manos");
                    deckImage.setVisibility(View.INVISIBLE);
                    palCard.setEnabled(false);
                    palCard.setAlpha(0.5f);
                }
                this.cardsLeft -= 1;
                textCardsRemaining.setContentDescription("Cartas en la baraja "+this.cardsLeft);
                textCardsRemaining.setText("" + this.cardsLeft);
                this.player.drawCard(playerCardUsed,this.deck.getCard(this.cardsLeft));
                if(this.cardsLeft == 0){
                    ImageView deckImage = (ImageView) findViewById(R.id.deck);
                    rivalThrownImg.announceForAccessibility("Últimas manos");
                    deckImage.setVisibility(View.INVISIBLE);
                    palCard.setEnabled(false);
                    palCard.setAlpha(0.5f);
                }
                if(playerCardUsed == 0){
                    ImageButton next = (ImageButton) findViewById(R.id.buttonCard1);
                    // next.setText(this.deck.getCard(this.cardsLeft).toString());
                    next.setImageResource(this.deck.getCard(this.cardsLeft).getImgID());
                    next.announceForAccessibility("Jugador roba " + this.deck.getCard(this.cardsLeft).toString());
                    next.setContentDescription("" + this.deck.getCard(this.cardsLeft).toString());
                }else if(playerCardUsed == 1){
                    ImageButton next = (ImageButton) findViewById(R.id.buttonCard2);
                    // next.setText(this.deck.getCard(this.cardsLeft).toString());
                    next.setImageResource(this.deck.getCard(this.cardsLeft).getImgID());
                    next.announceForAccessibility("Jugador roba " + this.deck.getCard(this.cardsLeft).toString());
                    next.setContentDescription("" + this.deck.getCard(this.cardsLeft).toString());
                }else{
                    ImageButton next = (ImageButton) findViewById(R.id.buttonCard3);
                    // next.setText(this.deck.getCard(this.cardsLeft).toString());
                    next.setImageResource(this.deck.getCard(this.cardsLeft).getImgID());
                    next.announceForAccessibility("Jugador roba " + this.deck.getCard(this.cardsLeft).toString());
                    next.setContentDescription("" + this.deck.getCard(this.cardsLeft).toString());
                }
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rivalInteraction();
                        initButtons();
                    }
                }, 2000);
            }
        }
    }

    private void finalCards() {
        turn = decideWinner(player.useCard(playerCardUsed), rival.useCard(rivalCardUsed), turn);

        if (turn == 0){
            winSound.start();
            playerThrownImg.announceForAccessibility("Gana jugador");
            addPoints(player.useCard(playerCardUsed));
            addPoints(rival.useCard(rivalCardUsed));
            rival.drawCard(rivalCardUsed,null);
            if(playerCardUsed == 0){
                ImageButton next = (ImageButton) findViewById(R.id.buttonCard1);
                next.setVisibility(View.INVISIBLE);
            }else if(playerCardUsed == 1){
                ImageButton next = (ImageButton) findViewById(R.id.buttonCard2);
                next.setVisibility(View.INVISIBLE);
            }else{
                ImageButton next = (ImageButton) findViewById(R.id.buttonCard3);
                next.setVisibility(View.INVISIBLE);
            }
            initButtons();
        } else {
            loseSound.start();
            playerThrownImg.announceForAccessibility("Gana rival");
            rival.drawCard(rivalCardUsed,null);
            if(playerCardUsed == 0){
                ImageButton next = (ImageButton) findViewById(R.id.buttonCard1);
                next.setVisibility(View.INVISIBLE);
            }else if(playerCardUsed == 1){
                ImageButton next = (ImageButton) findViewById(R.id.buttonCard2);
                next.setVisibility(View.INVISIBLE);
            }else{
                ImageButton next = (ImageButton) findViewById(R.id.buttonCard3);
                next.setVisibility(View.INVISIBLE);
            }
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rivalInteraction();
                    initButtons();
                }
            }, 2000);
        }
    }


    private int decideWinner(Card playerCard, Card rivalCard, int turn) {
        if (playerCard.getType() == this.pal.getType()){
            if(rivalCard.getType() == this.pal.getType()){
                return decideWinnerSameType(playerCard.getValue(),rivalCard.getValue());
            } else {
                return 0;
            }
        } else if (rivalCard.getType() == this.pal.getType()){
            return 1;
        } else {
            if (playerCard.getType() == rivalCard.getType()) {
                return decideWinnerSameType(playerCard.getValue(),rivalCard.getValue());
            } else {
                return turn;
            }
        }
    }
    private int decideWinnerSameType(int valuePlayer,int valueRival){

        if(valuePlayer == 1){
            valuePlayer = 14;
        }else if (valuePlayer == 3){
            valuePlayer = 13;
        }

        if(valueRival == 1){
            valueRival = 14;
        }else if (valueRival == 3){
            valueRival = 13;
        }

        if (valuePlayer > valueRival){
            return 0;
        }else{
            return 1;
        }
    }

    private void addPoints(Card card){
        if (card.getValue() == 1) {
            playerPoints += 11;
        }else if (card.getValue() == 3){
            playerPoints += 10;
        }else if (card.getValue() == 12){
            playerPoints += 4;
        }else if (card.getValue() == 11){
            playerPoints += 3;
        }else if (card.getValue() == 10){
            playerPoints += 2;
        }else{
            playerPoints += 0;
        }
        TextView nextText = (TextView) findViewById(R.id.textYourPoints);
        nextText.setText("Tus puntos:" + playerPoints);
    }

    private void initButtons() {
        ImageButton next = (ImageButton) findViewById(R.id.buttonCard1);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                soundCard.start();
                disableButtons();
                ImageButton actual = (ImageButton) findViewById(R.id.buttonCard1);
                playerCardUsed = 0;
                if (turn == 0) {
                    playerThrownImg.setImageResource(player.useCard(playerCardUsed).getImgID());
                    playerThrownImg.announceForAccessibility("Jugador usa " +player.useCard(playerCardUsed).toString());
                    playerThrownImg.setContentDescription("Carta usada por el jugador " + player.useCard(playerCardUsed).toString());
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rivalInteraction();
                        }
                    }, 2000);
                }
                else {
                    playerThrownImg.setImageResource(player.useCard(playerCardUsed).getImgID());
                    playerThrownImg.announceForAccessibility("Jugador usa " +player.useCard(playerCardUsed).toString());
                    playerThrownImg.setContentDescription("Carta usada por el jugador " + player.useCard(playerCardUsed).toString());
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            gameLogic();
                            playerThrownImg.setImageResource(android.R.color.transparent);
                            playerThrownImg.setContentDescription(null);
                            rivalThrownImg.setImageResource(android.R.color.transparent);
                            rivalThrownImg.setContentDescription(null);
                        }
                    }, 5000);
                }
            }

        });
        next = (ImageButton) findViewById(R.id.buttonCard2);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                soundCard.start();
                disableButtons();
                ImageButton actual = (ImageButton) findViewById(R.id.buttonCard2);
                playerCardUsed = 1;
                if (turn == 0) {
                    playerThrownImg.setImageResource(player.useCard(playerCardUsed).getImgID());
                    playerThrownImg.announceForAccessibility("Jugador usa " + player.useCard(playerCardUsed).toString());
                    playerThrownImg.setContentDescription("Carta usada por el jugador " + player.useCard(playerCardUsed).toString());
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rivalInteraction();
                        }
                    }, 2000);
                }
                else {
                    playerThrownImg.setImageResource(player.useCard(playerCardUsed).getImgID());
                    playerThrownImg.announceForAccessibility("Jugador usa " +player.useCard(playerCardUsed).toString());
                    playerThrownImg.setContentDescription("Carta usada por el jugador " + player.useCard(playerCardUsed).toString());
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            gameLogic();
                            playerThrownImg.setImageResource(android.R.color.transparent);
                            playerThrownImg.setContentDescription(null);
                            rivalThrownImg.setImageResource(android.R.color.transparent);
                            rivalThrownImg.setContentDescription(null);
                        }
                    }, 5000);
                }
            }

        });
        next = (ImageButton) findViewById(R.id.buttonCard3);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                soundCard.start();
                disableButtons();
                ImageButton actual = (ImageButton) findViewById(R.id.buttonCard3);
                playerCardUsed = 2;
                if (turn == 0) {
                    playerThrownImg.setImageResource(player.useCard(playerCardUsed).getImgID());
                    playerThrownImg.announceForAccessibility("Jugador usa " +player.useCard(playerCardUsed).toString());
                    playerThrownImg.setContentDescription("Carta usada por el jugador " + player.useCard(playerCardUsed).toString());
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rivalInteraction();
                        }
                    }, 2000);
                }
                else {
                    playerThrownImg.setImageResource(player.useCard(playerCardUsed).getImgID());
                    playerThrownImg.announceForAccessibility("Jugador usa " +player.useCard(playerCardUsed).toString());
                    playerThrownImg.setContentDescription("Carta usada por el jugador " + player.useCard(playerCardUsed).toString());
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            gameLogic();
                            playerThrownImg.setImageResource(android.R.color.transparent);
                            playerThrownImg.setContentDescription(null);
                            rivalThrownImg.setImageResource(android.R.color.transparent);
                            rivalThrownImg.setContentDescription(null);
                        }
                    }, 5000);
                }
            }

        });

        next = (ImageButton) findViewById(R.id.imgPal); // change Brisca
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Card extra;
                extra = deck.getCard(0);
                for (int i = 0 ; i < 3 ; i++ ){
                    if (player.useCard(i).getType() == extra.getType() && player.useCard(i).getValue() == 7) {
                        soundCard.start();
                        soundCard.start();
                        deck.swapCards(0,player.useCard(i));
                        player.drawCard(i, extra);
                        ImageButton actual;
                        if (i == 0){
                            actual = (ImageButton) findViewById(R.id.buttonCard1);
                        }
                        else if (i == 1){
                            actual = (ImageButton) findViewById(R.id.buttonCard2);
                        }
                        else {
                            actual = (ImageButton) findViewById(R.id.buttonCard3);
                        }
                        actual.setImageResource(player.useCard(i).getImgID());
                        actual.setContentDescription("" + player.useCard(i).toString());
                        actual = (ImageButton) findViewById(R.id.imgPal);
                        actual.setImageResource(deck.getCard(0).getImgID());
                        actual.setContentDescription("La brisca es  " + deck.getCard(0).toString());
                        actual.announceForAccessibility("Jugador cambia brisca" + player.useCard(i).toString() + " por " + deck.getCard(0).toString());
                    }
                }
            }

        });
    }
    private void disableButtons() {
        ImageButton next = (ImageButton) findViewById(R.id.buttonCard1);
        next.setOnClickListener(null);
        next = (ImageButton) findViewById(R.id.buttonCard2);
        next.setOnClickListener(null);
        next = (ImageButton) findViewById(R.id.buttonCard3);
        next.setOnClickListener(null);
        next = (ImageButton) findViewById(R.id.imgPal);
        next.setOnClickListener(null);
    }
    private int getCardCost(Card card) {
        int value;

        if (card.getValue() == 1){
            value = 12;
        }
        else if (card.getValue() == 3) {
            value = 11;
        }
        else if (card.getValue() == 12) {
            value = 5;
        }
        else if (card.getValue() == 11) {
            value = 1;
        }
        else if (card.getValue() == 10) {
            value = 1;
        } else{ value = 1; }


        if (card.getType() == this.deck.getCard(0).getType()) {
            return value*10;
        }
        return value;
    }
    private int getCardReward(Card cardToUse, Card cardUsed) {
        int points = 0;
        if (cardToUse.getValue() == 1) {
            points += 11;
        }else if (cardToUse.getValue() == 3){
            points += 10;
        }else if (cardToUse.getValue() == 12){
            points += 4;
        }else if (cardToUse.getValue() == 11){
            points += 3;
        }else if (cardToUse.getValue() == 10){
            points += 2;
        }else{
            points += 0;
        }

        if (cardUsed.getValue() == 1) {
            points += 11;
        }else if (cardUsed.getValue() == 3){
            points += 10;
        }else if (cardUsed.getValue() == 12){
            points += 4;
        }else if (cardUsed.getValue() == 11){
            points += 3;
        }else if (cardUsed.getValue() == 10){
            points += 2;
        }else{
            points += 0;
        }

        if (decideWinner(cardUsed,cardToUse,0) == 0){
            return -1*points;
        }
        else {
            return 1*points;
        }

    }

    protected void onDestroy() {
        //stop service and stop music
        stopService(new Intent(getApplicationContext(), MusicPlayer.class));
        super.onDestroy();
    }
}