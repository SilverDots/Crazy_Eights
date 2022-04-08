/**
 * A Card holds data for the suit and rank
 * of a playing card.
 */
public class Card {
    /**
     * The suit of this Card.
     */
    private Suit suit;

    /**
     * The rank, or face value, of this Card.
     */
    private Rank rank;
    
    /**
     * The set of all possible suits.
     */
    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES;
    }

    /**
     * The set of all possible ranks.
     */
    public enum Rank {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT,
        NINE, TEN, JACK, QUEEN, KING, ACE;
    }

    /**
     * Constructs a Card with a given card and rank.
     * 
     * @param suit      the suit of the card to be created
     * @param rank      the rank of the card to be created
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Reports the suit of this Card.
     * 
     * @return      the suit of this Card
     */
    public Suit suit() {
        return suit;
    }

    /**
     * Reports the rank of this Card.
     * 
     * @return      the rank of this Card
     */
    public Rank rank() {
        return rank;
    }

    /**
     * Returns a String containing the rank and suit
     * of this Card.
     * 
     * @return String       a String representation of this
     *                      Card object's rank and suit
     */
    public String toString() {
        return rank + " of " + suit;
    }
}
