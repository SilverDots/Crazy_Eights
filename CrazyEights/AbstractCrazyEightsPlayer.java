import java.util.*;

/**
 * An AbstractCrazyEightsPlayer describes some of the basic behavior
 * of a player in a game of Crazy Eights, while leaving other behavior
 * up to implementation.
 */
public abstract class AbstractCrazyEightsPlayer implements Player, Comparable<AbstractCrazyEightsPlayer> {
    /**
     * The name of the player.
     */
    private String name;

    /**
     * All of the cards currently in the player's hand.
     */
    private List<Card> hand;

    /**
     * The player's total points.
     */
    private int score;

    /**
     * Constructs an AbstractCrazyEightsPlayer with the give name.
     * 
     * @param name      the name of this AbstractCrazyEightsPlayer
     */
    public AbstractCrazyEightsPlayer(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    /**
     * Asks this AbstractCrazyEightsPlayer to play on the given suit,
     * returning either a card from the player's hand or null if they
     * chose to draw.
     * 
     * @param suit      the suit to be played on
     * @return          a valid Card or null if the player is drawing
     */
    public Card play(Card.Suit suit) {
        return play(suit, null);
    }

    /**
     * Asks this AbstractCrazyEightsPlayer to play on the given Card,
     * returning either a card from the player's hand or null if they
     * chose to draw.
     * 
     * @param topCard   the last card played
     * @return          a valid Card or null if the player is drawing
     */
    public Card play(Card topCard) {
        return play(topCard.suit(), topCard.rank());
    }
    
    /**
     * Adds the given card to the player's hand.
     * 
     * @param card      the card to be added to the player's hand
     */
    public void add(Card card) {
        hand.add(card);
    }

    /**
     * Retrieves all of the cards in the player's hand, returned as a list.
     * 
     * @return      a List of Card containing all of the cards in the player's hand
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Checks if the player's hand has no cards left.
     * 
     * @return      true if the player's hand is empty; false otherwise
     */
    public boolean isOut() {
        return hand.isEmpty();
    }

    /**
     * Checks if the player can play on the given suit.
     * 
     * @param suit      the suit to be checked for playability
     * @return          true if the player can play on the given suit; false otherwise
     */
    public boolean canPlayOn(Card.Suit suit) {
        return canPlayOn(suit, null);
    }

    /**
     * Checks if the player can play on the given Card.
     * 
     * @param topCard   the Card to be checked for playability
     * @return          true if the player can play on the given Card; false otherwise
     */
    public boolean canPlayOn(Card topCard) {
        return canPlayOn(topCard.suit(), topCard.rank());
    }
    
    /**
     * Clears the player's hand.
     */
    public void clear() {
        hand.clear();
    }

    /**
     * Retrieves the value of this player's current hand.
     * 
     * @return      the value of the player's hand according to the card
     *              values in Crazy Eights scoring
     */
    public int getHandValue() {
        int sum = 0;
        for (Card card : hand) {
            if (card.rank() == Card.Rank.ACE) {
                sum += 1;
            } else if (card.rank() == Card.Rank.EIGHT) {
                sum += 50;
            } else if (card.rank().ordinal() > 8) { 
                sum += 10;
            } else {
                sum += card.rank().ordinal() + 2;
            }
        }
        return sum;
    }

    /**
     * Adds the given number of points to this AbstractCrazyEightsPlayer's point total.
     * 
     * @param points    the number of points to be added
     */
    public void awardPoints(int points) {
        score += points;
    }

    /**
     * Compares this AbstractCrazyEightsPlayer to the given AbstractCrazyEightsPlayer.
     */
    public int compareTo(AbstractCrazyEightsPlayer other) {
        return other.score - score;
    }

    public int getScore() {
        return score;
    }

    /**
     * Gives this AbstractCrazyEightsPlayer's name.
     * 
     * @return      the player's name
     */
    public String toString() {
        return name;
    }

    /**
     * Prompts the player to choose a suit.
     * 
     * @return      a valid suit as chosen by the player
     */
    public abstract Card.Suit chooseNewSuit();

    /**
     * Asks this AbstractCrazyEightsPlayer to play on the given suit or rank,
     * returning either a card from the player's hand or null if they
     * chose to draw.
     * 
     * @param suit      the suit to be played on
     * @param rank      the rank to be played on
     * @return          a valid Card or null if the player is drawing
     */
    public abstract Card play(Card.Suit suit, Card.Rank rank);

    /**
     * Checks if the player can play on the given suit or rank.
     * 
     * @param suit      the suit to be checked for playability
     * @param rank      the rank to be checked for playability
     * @return          true if the player can play on the given suit; false otherwise
     */
    public abstract boolean canPlayOn(Card.Suit suit, Card.Rank rank);
}
