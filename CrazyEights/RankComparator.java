import java.util.*;

public class RankComparator implements Comparator<Card> {
    public int compare(Card first, Card second) {
        return second.rank().ordinal() - first.rank().ordinal();
    }
}
