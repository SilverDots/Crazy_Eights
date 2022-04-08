import java.util.*;

/**
 * A Player describes some of the behavior for a card game player.
 */
public interface Player {
    public Card play(Card card);
    public void add(Card card);
    public List<Card> getHand();
    public boolean isOut();
    public boolean canPlayOn(Card card);
    public void clear();
    public String toString();
}
