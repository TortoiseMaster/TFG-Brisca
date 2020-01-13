package jordibarea.tfg.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import jordibarea.tfg.Card;
import jordibarea.tfg.Deck;
import jordibarea.tfg.Player;
import jordibarea.tfg.R;

public class GameActivity extends Activity {
    private Deck deck = new Deck();
    private int cardsLeft = 48;
    private Player player = new Player();
    private Player rival  = new Player();
    private Card pal;
    private int turn;
    private int playerPoints;
    private int lastHands = 0;

    private int playerCardUsed, rivalCardUsed;
    private boolean firstTurn, easy;

    ImageButton palCard;
    ImageView rivalThrownImg, playerThrownImg;
    TextView textCardsRemaining;
    MediaPlayer soundCard, loseSound, winSound;

    SharedPreferences sharedPref;
    boolean soundOn;

    // Manage the rotation of the screen, recharging all the layout elements
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.game);

        rivalThrownImg = (ImageView) findViewById(R.id.imgRivalThrown);
        playerThrownImg = (ImageView) findViewById(R.id.imgPlayerThrown);
        palCard = (ImageButton) findViewById(R.id.imgPal);
        textCardsRemaining = (TextView) findViewById(R.id.remainingCards);

        ImageButton next = (ImageButton) findViewById(R.id.buttonCard1);

        next.setImageResource(this.player.useCard(0).getImgID());
        next.announceForAccessibility(getString(R.string.player_draws)+this.player.useCard(0).toString());
        next.setContentDescription("" + this.player.useCard(0).toString());
        next = (ImageButton) findViewById(R.id.buttonCard2);
        next.setImageResource(this.player.useCard(1).getImgID());
        next.announceForAccessibility(getString(R.string.player_draws)+this.player.useCard(1).toString());
        next.setContentDescription("" + this.player.useCard(1).toString());
        next = (ImageButton) findViewById(R.id.buttonCard3);
        next.setImageResource(this.player.useCard(2).getImgID());
        next.announceForAccessibility(getString(R.string.player_draws)+this.player.useCard(2).toString());
        next.setContentDescription("" + this.player.useCard(2).toString());

        textCardsRemaining.setContentDescription(getString(R.string.remaining_cards)+this.cardsLeft);
        textCardsRemaining.setText(" " + this.cardsLeft + " ");

        palCard.setImageResource(this.deck.getCard(0).getImgID());
        palCard.announceForAccessibility(getString(R.string.brisca)+this.deck.getCard(0).toString());
        palCard.setContentDescription(getString(R.string.brisca) + this.deck.getCard(0).toString());

        TextView nextText = (TextView) findViewById(R.id.textYourPoints);
        nextText.setText(getString(R.string.current_points) + playerPoints);
        if (turn == 1){
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rivalInteraction();
                }
            }, 2000);
        }
        else {
            initButtons();
        }
    }

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        sharedPref = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
        soundOn = sharedPref.getBoolean("Sound",true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        firstTurn = getIntent().getBooleanExtra("firstTurn", true);
        easy = getIntent().getBooleanExtra("easy", true);

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

    // Initialize all the necessary thing to be able to do a match
    private void initGame(){
        this.cardsLeft -= 1;
        textCardsRemaining.setContentDescription(getString(R.string.remaining_cards)+this.cardsLeft);
        textCardsRemaining.setText(" " + this.cardsLeft + " ");
        this.player.drawCard(0,this.deck.getCard(this.cardsLeft));
        ImageButton next = (ImageButton) findViewById(R.id.buttonCard1);
        // next.setText(this.deck.getCard(this.cardsLeft).toString());
        next.setImageResource(this.deck.getCard(this.cardsLeft).getImgID());
        next.announceForAccessibility(getString(R.string.player_draws)+this.deck.getCard(this.cardsLeft).toString());
        next.setContentDescription("" + this.deck.getCard(this.cardsLeft).toString());

        this.cardsLeft -=1;
        textCardsRemaining.setContentDescription(getString(R.string.remaining_cards)+this.cardsLeft);
        textCardsRemaining.setText(" " + this.cardsLeft + " ");
        this.rival.drawCard(0,this.deck.getCard(this.cardsLeft));
        textCardsRemaining.setContentDescription(getString(R.string.remaining_cards)+this.cardsLeft);
        textCardsRemaining.setText(" " + this.cardsLeft + " ");
        this.cardsLeft -=1;
        textCardsRemaining.setContentDescription(getString(R.string.remaining_cards)+this.cardsLeft);
        textCardsRemaining.setText(" " + this.cardsLeft + " ");
        this.player.drawCard(1,this.deck.getCard(this.cardsLeft));
        next = (ImageButton) findViewById(R.id.buttonCard2);
        // next.setText(this.deck.getCard(this.cardsLeft).toString());
        next.setImageResource(this.deck.getCard(this.cardsLeft).getImgID());
        next.announceForAccessibility(getString(R.string.player_draws)+this.deck.getCard(this.cardsLeft).toString());
        next.setContentDescription("" + this.deck.getCard(this.cardsLeft).toString());

        this.cardsLeft -=1;
        textCardsRemaining.setContentDescription(getString(R.string.remaining_cards)+this.cardsLeft);
        textCardsRemaining.setText(" " + this.cardsLeft + " ");
        this.rival.drawCard(1,this.deck.getCard(this.cardsLeft));

        this.cardsLeft -=1;
        textCardsRemaining.setContentDescription(getString(R.string.remaining_cards)+this.cardsLeft);
        textCardsRemaining.setText(" " + this.cardsLeft + " ");
        this.player.drawCard(2,this.deck.getCard(this.cardsLeft));
        next = (ImageButton) findViewById(R.id.buttonCard3);
        // next.setText(this.deck.getCard(this.cardsLeft).toString());
        next.setImageResource(this.deck.getCard(this.cardsLeft).getImgID());
        next.announceForAccessibility(getString(R.string.player_draws)+this.deck.getCard(this.cardsLeft).toString());
        next.setContentDescription("" + this.deck.getCard(this.cardsLeft).toString());

        this.cardsLeft -=1;
        textCardsRemaining.setContentDescription(getString(R.string.remaining_cards)+this.cardsLeft);
        textCardsRemaining.setText(" " + this.cardsLeft + " ");
        this.rival.drawCard(2,this.deck.getCard(this.cardsLeft));

        this.pal = this.deck.getCard(0);
        // palCard.setText(this.deck.getCard(0).toString());
        palCard.setImageResource(this.deck.getCard(0).getImgID());
        palCard.announceForAccessibility(getString(R.string.brisca)+this.deck.getCard(0).toString());
        palCard.setContentDescription(getString(R.string.brisca) + this.deck.getCard(0).toString());

        TextView nextText = (TextView) findViewById(R.id.textYourPoints);
        nextText.setText(getString(R.string.current_points)+ playerPoints);
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

    // What the rival does when is his turn
    private void rivalInteraction(){
        if (cardsLeft != 0) { // Still cards in the deck
            if (turn == 1){ // Rival has the first turn
                rivalCardUsed = IA(); // Check which card to use
                rivalThrownImg.setImageResource(rival.useCard(rivalCardUsed).getImgID());
                if (soundOn){
                    soundCard.start();
                }
                rivalThrownImg.announceForAccessibility(getString(R.string.rival_use) + rival.useCard(rivalCardUsed).toString());
                rivalThrownImg.setContentDescription(getString(R.string.rival_use) + rival.useCard(rivalCardUsed).toString());
            }
            else { // Rival has the second turn
                rivalCardUsed = IA();
                rivalThrownImg.setImageResource(rival.useCard(rivalCardUsed).getImgID());
                if (soundOn){
                    soundCard.start();
                }
                rivalThrownImg.announceForAccessibility(getString(R.string.rival_use) + rival.useCard(rivalCardUsed).toString());
                rivalThrownImg.setContentDescription(getString(R.string.rival_use) + rival.useCard(rivalCardUsed).toString());
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
        } else { // Only the cards in the hand remain
            if (turn == 1){
                rivalCardUsed = IA();
                rivalThrownImg.setImageResource(rival.useCard(rivalCardUsed).getImgID());
                if (soundOn){
                    soundCard.start();
                }
                rivalThrownImg.announceForAccessibility(getString(R.string.rival_use) + rival.useCard(rivalCardUsed).toString());
                rivalThrownImg.setContentDescription(getString(R.string.rival_use) + rival.useCard(rivalCardUsed).toString());
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
                if (soundOn){
                    soundCard.start();
                }
                rivalThrownImg.announceForAccessibility(getString(R.string.rival_use) + rival.useCard(rivalCardUsed).toString());
                rivalThrownImg.setContentDescription(getString(R.string.rival_use) + rival.useCard(rivalCardUsed).toString());
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() { // Wait before drawing cards in order to allow the user
                    // to understand what happens.
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

    // IT will decide which is the better decision in every moment, explained in the Memory
    private int IA() {
        double effectiveValue;
        double bestValue;
        int bestCard = 0;
        if (!easy){
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
        } else {
            if (turn == 0){
                bestValue = -5000;
                for (int i = 0; i < 3; i++) {
                    if (rival.useCard(i) != null){
                        effectiveValue = getCardReward(rival.useCard(i), player.useCard(playerCardUsed));
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
        }
        return bestCard;
    }

    // Controls who has the turn and makes the palayer draw cards.
    public void gameLogic() {
        ImageButton pal = (ImageButton) findViewById(R.id.imgPal);


        turn = decideWinner(player.useCard(playerCardUsed), rival.useCard(rivalCardUsed), turn);
        if (cardsLeft == 0) {
            if (lastHands != 2) {
                finalCards();
                lastHands++;
            } else {
                if (turn == 0) {
                    addPoints(player.useCard(playerCardUsed));
                    addPoints(rival.useCard(rivalCardUsed));
                }
                Intent myIntent = new Intent(this.getBaseContext(), WinActivity.class);
                myIntent.putExtra("points",playerPoints);
                startActivityForResult(myIntent, 0);
            }
        }
        else {
            if (turn == 0){
                if (soundOn){
                    winSound.start();
                }
                playerThrownImg.announceForAccessibility(getString(R.string.hand_win));
                addPoints(player.useCard(playerCardUsed));
                addPoints(rival.useCard(rivalCardUsed));

                this.cardsLeft -= 1;
                textCardsRemaining.setContentDescription(getString(R.string.remaining_cards)+this.cardsLeft);
                textCardsRemaining.setText(" " + this.cardsLeft + " ");
                this.player.drawCard(playerCardUsed,this.deck.getCard(this.cardsLeft));
                if(this.cardsLeft == 0){
                    ImageView deckImage = (ImageView) findViewById(R.id.deck);
                    rivalThrownImg.announceForAccessibility(getString(R.string.last_hands));
                    deckImage.setVisibility(View.INVISIBLE);
                    palCard.setEnabled(false);
                    palCard.setAlpha(0.5f);
                }
                if(playerCardUsed == 0){
                    ImageButton next = (ImageButton) findViewById(R.id.buttonCard1);
                    next.setImageResource(this.deck.getCard(this.cardsLeft).getImgID());
                    next.announceForAccessibility(getString(R.string.player_draws) + this.deck.getCard(this.cardsLeft).toString());
                    next.setContentDescription("" + this.deck.getCard(this.cardsLeft).toString());
                }else if(playerCardUsed == 1){
                    ImageButton next = (ImageButton) findViewById(R.id.buttonCard2);
                    next.setImageResource(this.deck.getCard(this.cardsLeft).getImgID());
                    next.announceForAccessibility(getString(R.string.player_draws) + this.deck.getCard(this.cardsLeft).toString());
                    next.setContentDescription("" + this.deck.getCard(this.cardsLeft).toString());
                }else{
                    ImageButton next = (ImageButton) findViewById(R.id.buttonCard3);
                    next.setImageResource(this.deck.getCard(this.cardsLeft).getImgID());
                    next.announceForAccessibility(getString(R.string.player_draws) + this.deck.getCard(this.cardsLeft).toString());
                    next.setContentDescription("" + this.deck.getCard(this.cardsLeft).toString());
                }
                this.cardsLeft -= 1;
                textCardsRemaining.setContentDescription(getString(R.string.remaining_cards)+this.cardsLeft);
                textCardsRemaining.setText(" " + this.cardsLeft + " ");
                this.rival.drawCard(rivalCardUsed,this.deck.getCard(this.cardsLeft));
                if(this.cardsLeft == 0){
                    ImageView deckImage = (ImageView) findViewById(R.id.deck);
                    rivalThrownImg.announceForAccessibility(getString(R.string.last_hands));
                    deckImage.setVisibility(View.INVISIBLE);
                    palCard.setEnabled(false);
                    palCard.setAlpha(0.5f);
                }
                initButtons();
            } else {
                if (soundOn){
                    loseSound.start();
                }

                playerThrownImg.announceForAccessibility(getString(R.string.hand_lost));
                this.cardsLeft -= 1;
                textCardsRemaining.setContentDescription(getString(R.string.remaining_cards)+this.cardsLeft);
                textCardsRemaining.setText(" " + this.cardsLeft + " ");
                this.rival.drawCard(rivalCardUsed,this.deck.getCard(this.cardsLeft));
                if(this.cardsLeft == 0){
                    ImageView deckImage = (ImageView) findViewById(R.id.deck);
                    rivalThrownImg.announceForAccessibility(getString(R.string.last_hands));
                    deckImage.setVisibility(View.INVISIBLE);
                    palCard.setEnabled(false);
                    palCard.setAlpha(0.5f);
                }
                this.cardsLeft -= 1;
                textCardsRemaining.setContentDescription(getString(R.string.remaining_cards)+this.cardsLeft);
                textCardsRemaining.setText(" " + this.cardsLeft + " ");
                this.player.drawCard(playerCardUsed,this.deck.getCard(this.cardsLeft));
                if(this.cardsLeft == 0){
                    ImageView deckImage = (ImageView) findViewById(R.id.deck);
                    rivalThrownImg.announceForAccessibility(getString(R.string.last_hands));
                    deckImage.setVisibility(View.INVISIBLE);
                    palCard.setEnabled(false);
                    palCard.setAlpha(0.5f);
                }
                if(playerCardUsed == 0){
                    ImageButton next = (ImageButton) findViewById(R.id.buttonCard1);
                    // next.setText(this.deck.getCard(this.cardsLeft).toString());
                    next.setImageResource(this.deck.getCard(this.cardsLeft).getImgID());
                    next.announceForAccessibility(getString(R.string.player_draws) + this.deck.getCard(this.cardsLeft).toString());
                    next.setContentDescription("" + this.deck.getCard(this.cardsLeft).toString());
                }else if(playerCardUsed == 1){
                    ImageButton next = (ImageButton) findViewById(R.id.buttonCard2);
                    // next.setText(this.deck.getCard(this.cardsLeft).toString());
                    next.setImageResource(this.deck.getCard(this.cardsLeft).getImgID());
                    next.announceForAccessibility(getString(R.string.player_draws)+ this.deck.getCard(this.cardsLeft).toString());
                    next.setContentDescription("" + this.deck.getCard(this.cardsLeft).toString());
                }else{
                    ImageButton next = (ImageButton) findViewById(R.id.buttonCard3);
                    // next.setText(this.deck.getCard(this.cardsLeft).toString());
                    next.setImageResource(this.deck.getCard(this.cardsLeft).getImgID());
                    next.announceForAccessibility(getString(R.string.player_draws) + this.deck.getCard(this.cardsLeft).toString());
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

    // Last hands of the match that don't cards to be drawn
    private void finalCards() {
        turn = decideWinner(player.useCard(playerCardUsed), rival.useCard(rivalCardUsed), turn);

        if (turn == 0){
            if (soundOn){
                winSound.start();
            }
            playerThrownImg.announceForAccessibility(getString(R.string.hand_win));
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
            if (soundOn){
                loseSound.start();
            }
            playerThrownImg.announceForAccessibility(getString(R.string.hand_lost));
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

    // Given two cards decide which one wins the hand
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

    // Given two values of cards that should have the same suit tells which one wins the hand
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


    // Add the amount of points to the user score
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
        nextText.setText(getString(R.string.current_points) + playerPoints);
    }

    // When the result is shown and the cards are drawn we need to desactivate
    // the buttons bs it doesnt make sence to use the cards then. This method gives the funcionality of the buttons back
    private void initButtons() {
        ImageButton next = (ImageButton) findViewById(R.id.buttonCard1);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (soundOn){
                    soundCard.start();
                }
                disableButtons();
                ImageButton actual = (ImageButton) findViewById(R.id.buttonCard1);
                playerCardUsed = 0;
                if (turn == 0) {
                    playerThrownImg.setImageResource(player.useCard(playerCardUsed).getImgID());
                    playerThrownImg.announceForAccessibility(getString(R.string.player_use) +player.useCard(playerCardUsed).toString());
                    playerThrownImg.setContentDescription(getString(R.string.player_use) + player.useCard(playerCardUsed).toString());
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
                    playerThrownImg.announceForAccessibility(getString(R.string.player_use) +player.useCard(playerCardUsed).toString());
                    playerThrownImg.setContentDescription(getString(R.string.player_use) + player.useCard(playerCardUsed).toString());
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
                if (soundOn){
                    soundCard.start();
                }
                disableButtons();
                ImageButton actual = (ImageButton) findViewById(R.id.buttonCard2);
                playerCardUsed = 1;
                if (turn == 0) {
                    playerThrownImg.setImageResource(player.useCard(playerCardUsed).getImgID());
                    playerThrownImg.announceForAccessibility(getString(R.string.player_use) + player.useCard(playerCardUsed).toString());
                    playerThrownImg.setContentDescription(getString(R.string.player_use) + player.useCard(playerCardUsed).toString());
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
                    playerThrownImg.announceForAccessibility(getString(R.string.player_use) +player.useCard(playerCardUsed).toString());
                    playerThrownImg.setContentDescription(getString(R.string.player_use) + player.useCard(playerCardUsed).toString());
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
                if (soundOn){
                    soundCard.start();
                }
                disableButtons();
                ImageButton actual = (ImageButton) findViewById(R.id.buttonCard3);
                playerCardUsed = 2;
                if (turn == 0) {
                    playerThrownImg.setImageResource(player.useCard(playerCardUsed).getImgID());
                    playerThrownImg.announceForAccessibility(getString(R.string.player_use) +player.useCard(playerCardUsed).toString());
                    playerThrownImg.setContentDescription(getString(R.string.player_use) + player.useCard(playerCardUsed).toString());
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
                    playerThrownImg.announceForAccessibility(getString(R.string.player_use)+player.useCard(playerCardUsed).toString());
                    playerThrownImg.setContentDescription(getString(R.string.player_use) + player.useCard(playerCardUsed).toString());
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
                        if (soundOn){
                            soundCard.start();
                        }
                        if (soundOn){
                            soundCard.start();
                        }
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
                        actual.setContentDescription(getString(R.string.brisca)+ deck.getCard(0).toString());
                        actual.announceForAccessibility(getString(R.string.change_brisca) + player.useCard(i).toString() + getString(R.string.change_brisca2) + deck.getCard(0).toString());
                    }
                }
            }

        });
    }

    // When the result is shown and the cards are drawn we need to desactivate
    // the buttons bs it doesnt make sence to use the cards then. This method takes off the funcionality of the buttons
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

    // Used by the IA to get the amount of points given by every card
    private int getCardReward(Card cardToUse, Card cardUsed) {
        int points = 0;
        if (cardToUse.getValue() == 1) {
            points += 11;
        }else if (cardToUse.getValue() == 3){
            points += 10;
        }else if (cardToUse.getValue() == 12){
            points += 5;
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
            points += 5;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // Bc we don't save the state of the match we need a confirmation when quitting the game.
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getString(R.string.alert_title))
                .setMessage(getString(R.string.alert_message))
                .setPositiveButton(getString(R.string.alert_confirm), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton(getString(R.string.alert_deny), null)
                .show();
    }
}