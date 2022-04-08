import java.util.*;

/**
 * A Deck specifies the operations that a deck
 * should implement. 
 */
public interface Deck {
    public boolean isEmpty();
    public Card draw();
    public void discard(Card card);
    public void reshuffle();
    public void reset();
    public void addPlayerHand(List<Card> hand);
    public Card topDiscard();
}
