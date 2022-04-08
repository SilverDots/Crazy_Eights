import java.util.*;

/**
 * A CrazyEightsAdmin manages the administrative tasks, such as dealing, tracking
 * play, and scorekeeping in games of traditional Crazy Eights.
 */
public class CrazyEightsAdmin {
    /**
     * The list of all players.
     */
    private List<AbstractCrazyEightsPlayer> players;
    
    /**
     * A standard 52-card deck.
     */
    private Deck deck;

    /**
     * Tracks whether or not the game has concluded.
     */
    private boolean gameOver;

    /**
     * The dealer for the current game.
     */
    private AbstractCrazyEightsPlayer dealer;
    
    /**
     * The number of turns that have elapsed since the start of play.
     */
    private int turn;

    /**
     * Constructs a CrazyEightsAdmin which represents the given number of players
     * and records the player's name.
     * 
     * @param name          the name of the sentient player
     * @param numPlayers    the number of players who will be playing games
     * @param console       a Scanner which takes user input
     */
    public CrazyEightsAdmin(String name, int numPlayers, Scanner console) {
        players = new LinkedList<>();
        deck = new Deck52();
        players.add(new CrazyPlayer(name, console));
        for (int i = 1; i < numPlayers; i++) {
            players.add(new CrazyBot(i));
        }
        players.get(1).add(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));
    }

    /**
     * Adds 5 cards from the draw pile to each player's hand.
     */
    public void deal() {
        for (int i = 0; i < 5; i++) {
            for(AbstractCrazyEightsPlayer p : players) {
                p.add(deck.draw());
            }
        }
    }

    /**
     * Begins a round of Crazy Eights and concludes when a winner has been found.
     */
    public void play() {
        gameOver = false;
        turn = 0;
        dealer = players.get(0);
        Card.Suit suit = null;
        System.out.println(dealer + " is the dealer.");
        deck.discard(deck.draw());
        //Note: while it may appear redudant, it is possible for the player to draw when
        //the suit was changed, which would force the next player/bot to instead play on
        //the suit; therefore, some kind of check is necessary
        boolean eightFlag = deck.topDiscard().rank() == Card.Rank.EIGHT ? true : false;
        System.out.println("A " + deck.topDiscard() + " was flipped to begin.");
        while (!gameOver) {
            Collections.rotate(players, -1);
            if (eightFlag) {
                suit = players.get(players.size() - 1).chooseNewSuit();
                System.out.println("The new suit is: " + suit);
            }
            eightFlag = getCard(deck.topDiscard(), eightFlag, suit);
            if (players.get(0).isOut()) {
                gameOver = true;
            }
            turn++;
        }
        System.out.println();
        System.out.println("Game over: the winner is " + players.get(0) + "!");
        System.out.println("The game lasted " + turn + " turns. ");
    }

    /**
     * Retrieves a card from the player and adds it to the current discard pile or
     * draws a card from the draw pile to add to the player's hand. If a player
     * chooses to draw but the draw pile is empty, and if the discard is also empty
     * barring the top discard, then the player is forcecd to pass. 
     * 
     * @param lastPlayed    the Card which was last played
     * @param eightFlag     a boolean which is true if the last card played was an eight
     * @param suit          the suit to be played on if the last card was an eight
     * @return              true if the last card played was an eight; false otherwise
     */
    private boolean getCard(Card lastPlayed, boolean eightFlag, Card.Suit suit) {
        if (players.get(0) instanceof CrazyPlayer) {
            System.out.println();
            if (eightFlag) {
                System.out.println("The suit to play on is: " + suit);
            } else {
                System.out.println("The top card is: " + lastPlayed);                
            }
        }
        if (deck.isEmpty()) {
            deck.reshuffle();       
        }
        Card choice = lastPlayed.rank() == Card.Rank.EIGHT ? players.get(0).play(suit) :
                players.get(0).play(lastPlayed);
        if (choice != null) {
            System.out.println(players.get(0) + " played: " + choice);
            deck.discard(choice);
            if (choice.rank() == Card.Rank.EIGHT) {
                return true;
            }
        } else if (deck.isEmpty()) {
            System.out.println(players.get(0) + " was forced to pass.");
        } else {
            System.out.println(players.get(0) + " drew a card.");
            boolean canPlay = lastPlayed.rank() == Card.Rank.EIGHT ? players.get(0).canPlayOn(suit) :
                    players.get(0).canPlayOn(lastPlayed);
            players.get(0).add(deck.draw());
            if (!canPlay) {
                return getCard(lastPlayed, eightFlag, suit);
            }
        }
        return false;
    }

    /**
     * Takes all remaining cards in the players' hands and inserts them
     * back into the deck and awards points to the player who won the last round.
     */
    public void reset() {
        int score = 0;
        for (AbstractCrazyEightsPlayer p : players) {
            score += p.getHandValue();
            deck.addPlayerHand(p.getHand());
        }
        players.get(0).awardPoints(score);
        while (!players.get(players.size() - 1).equals(dealer)) {
            Collections.rotate(players, -1);
        }
        deck.reset();
    }

    /**
     * Prints all sum of each player's points from order of most points to least,
     * separated by line.
     * 
     * @param text      the String representing the current stage of game during
     *                  which points are to be printed
     */
    public void printStats(String text) {
        Collections.sort(players);
        System.out.println(text);
        for (int i = 0; i < players.size(); i++) {
            System.out.println((i + 1) + ". " + players.get(i) + " (" + players.get(i).getScore() + " points)");
        }
    }
}
