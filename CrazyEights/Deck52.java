import java.util.*;

/**
 * A Deck52 represents a standard 52-card deck.
 * And yes, they are Bicycle playing cards.
 */
public class Deck52 implements Deck {
    /**
     * A pile holding all the cards which can be drawn.
     */
    private List<Card> drawPile;

    /**
     * A pile holding cards which have previously been played.
     */
    private List<Card> discardPile;

    /**
     * Constructs a new Deck52 with a draw pile and discard pile.
     */
    public Deck52() {
        drawPile = new LinkedList<>();
        discardPile = new ArrayList<>();
        for (Card.Suit s : Card.Suit.values()) {
            for (Card.Rank r : Card.Rank.values()) {
                drawPile.add(new Card(s, r));
            }
        }
        Collections.shuffle(drawPile);
    }

    /**
     * Checks if the draw pile is empty.
     * 
     * @return      true if the draw pile is empty; false otherwise
     */
    public boolean isEmpty() {
        return drawPile.isEmpty();
    }

    /**
     * Removes a card from the draw pile.
     * 
     * @return      the card removed from the top of the draw pile
     */
    public Card draw() {
        return drawPile.remove(0);
    }

    /**
     * Adds a card that was recently discarded to the top of the discard pile.
     */
    public void discard(Card card) {
        discardPile.add(0, card);
    }

    /**
     * Reports the last card played.
     * 
     * @return      the card that was last played
     */
    public Card topDiscard() {
        return discardPile.get(0);
    }

    /**
     * Adds all of the cards from a player's hand to the draw pile.
     * 
     * @param hand      the List of Card containing all cards in a given
     *                  player's hand
     */
    public void addPlayerHand(List<Card> hand) {
        drawPile.addAll(hand);
        hand.clear();
    }

    /**
     * Takes all cards from the discard pile excluding the most recently discarded
     * card and adds them to the draw pile, shuffling all cards.
     */
    public void reshuffle() {
        Card card = discardPile.remove(0);
        reset();
        discardPile.add(card);
    }

    /**
     * Takes all cards from the discard pile and shuffles them back into the draw
     * pile.
     */
    public void reset() {
        drawPile.addAll(discardPile);
        discardPile.clear();
        Collections.shuffle(drawPile);
    }

    public String toString() {
        return "Cards left in draw pile: " + drawPile.size();
    }
}
