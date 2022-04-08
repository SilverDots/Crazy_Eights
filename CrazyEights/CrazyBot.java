import java.util.*;

/**
 * A CrazyBot is a simple bot that automatically plays during
 * a game of CrazyEights. CrazyBot will always play when possible and will
 * only draw when necessary or when it is forced to pass.
 */
public class CrazyBot extends AbstractCrazyEightsPlayer {
    /**
     * Organizes this CrazyBot's cards by suit.
     */
    private Map<Card.Suit, Queue<Card>> suits;

    /**
     * Organizes this CrazyBot's cards by face value.
     */
    private Map<Card.Rank, List<Card>> ranks;

    /**
     * A Random object for creating wacky, wild numbers.
     */
    private Random rand;

    /**
     * Constructs a CrazyBot with the given number.
     * 
     * @param n     a number specifying which number bot this is
     */
    public CrazyBot(int n) {
        super("Bot " + n);
        suits = new HashMap<>();
        ranks = new HashMap<>();
        rand = new Random();
        for (Card.Suit suit : Card.Suit.values()) {
            suits.put(suit, new PriorityQueue<>(new RankComparator()));
        }
        for (Card.Rank rank : Card.Rank.values()) {
            ranks.put(rank, new ArrayList<>());
        }
    }

    public void add(Card card) {
        super.add(card);
        if (card.rank() != Card.Rank.EIGHT) {
            suits.get(card.suit()).add(card);            
        }
        ranks.get(card.rank()).add(card);
    }

    public Card play(Card.Suit suit, Card.Rank rank) {
        Card removed = null;
        if (canPlayOn(suit, rank)) {
            if (ranks.get(Card.Rank.EIGHT).size() > 0 && rand.nextInt(7) == 5 || (suits.get(suit).size() == 0 && (rank == null || 
                        (rank != null && ranks.get(rank).size() == 0)))) {
                removed = ranks.get(Card.Rank.EIGHT).remove(0);
            } else if (suits.get(suit).size() > 0) {
                removed = suits.get(suit).remove();
                ranks.get(removed.rank()).remove(removed);
            } else {
                removed = ranks.get(rank).remove(0);
                suits.get(removed.suit()).remove(removed);
            }
            getHand().remove(removed);    
        }
        return removed;
    }

    public boolean canPlayOn(Card.Suit suit, Card.Rank rank) {
        boolean suitsEightCheck = suits.get(suit).size() > 0 || ranks.get(Card.Rank.EIGHT).size() > 0;
        if (rank != null) {
            return suitsEightCheck || ranks.get(rank).size() > 0;
        }
        return suitsEightCheck;
    }

    public Card.Suit chooseNewSuit() {
        int most = 0;
        Card.Suit mostSuit = null;
        for (Card.Suit suit : suits.keySet()) {
            if (suits.get(suit).size() > most) {
                most = suits.get(suit).size();
                mostSuit = suit;
            }
        }
        return mostSuit;
    }

    public void clear() {
        super.clear();
        for (Card.Suit suit : suits.keySet()) {
            suits.get(suit).clear();
        }
        ranks.get(Card.Rank.EIGHT).clear();
    }
}
