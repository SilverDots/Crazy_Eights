import java.util.*;

/**
 * A CrazyPlayer prompts keeps track of a player's hand during a game
 * of Crazy Eights.
 */
public class CrazyPlayer extends AbstractCrazyEightsPlayer {
    /**
     * The Scanner linked to console which receives player input
     */
    private Scanner console;

    /**
     * String representations of the valid suits
     */
    public static final String[] validSuits = new String[]{"CLUBS", "DIAMONDS", "HEARTS", "SPADES"};

    /**
     * Constructs a new CrazyPlayer with the given name.
     * 
     * @param name      the name to be assigned to the player
     * @param console   a Scanner linked to console
     */
    public CrazyPlayer(String name, Scanner console) {
        super(name);
        this.console = console;
    }

    public Card play(Card.Suit suit, Card.Rank rank) {
        printHand();
        System.out.println("Enter the number of the card you wish");
        System.out.print("to play or enter \"D\" to draw: ");
        String choice = console.nextLine();
        while (!validChoice(choice, suit, rank)) {
            choice = console.nextLine();
        }
        System.out.println();
        if (choice.equalsIgnoreCase("D")) {
            return null;
        } else {
            return getHand().remove(Integer.parseInt(choice) - 1);
        }
    }

    public boolean canPlayOn(Card.Suit suit, Card.Rank rank) {
        for (Card inHand : getHand()) {
            if (inHand.suit() == suit || inHand.rank() == rank || inHand.rank() == Card.Rank.EIGHT) {
                return true;
            }
        }
        return false;
    }

    /**
     * Prints all of the cards in the player's hand to console.
     */
    private void printHand() {
        System.out.println("Current hand:");
        for (int i = 0; i < getHand().size(); i++) {
            System.out.println((i + 1) + ". " + getHand().get(i));
        }
        System.out.println();
    }

    /**
     * Checks if the choice entered by the user reflects a valid option.
     * 
     * @param choice    the String to be checked for a valid card selection
     * @param suit      the suit to be played on
     * @param rank      the card to be played on
     * @return          true if the choice is valid; false otherwise
     */
    private boolean validChoice(String choice, Card.Suit suit, Card.Rank rank) {
        if (!choice.equalsIgnoreCase("D")) {
            try {
                Integer.parseInt(choice);
            } catch (NumberFormatException nfe) {
                System.out.print("Please enter a valid option: ");
                return false;
            }
            if (Integer.parseInt(choice) - 1 < 0 || Integer.parseInt(choice) - 1 >= getHand().size()) {
                System.out.print("Please enter a valid number: ");
                return false;
            }
            if (!canPlayOn(suit, rank)) {
                System.out.println("Sorry, you cannot play on the last card played.");
                System.out.print("You must draw a card: ");
                return false;
            }
            Card card = getHand().get(Integer.parseInt(choice) - 1);
            boolean suitsEightCheck = card.rank() == Card.Rank.EIGHT || card.suit() == suit;
            if (!suitsEightCheck && (rank == null || (rank != null && card.rank() != rank))) {
                System.out.print("This card cannot be played. Please select another or draw: ");
            }
            if (rank != null) {
                return suitsEightCheck || card.rank() == rank;
            }
            return suitsEightCheck;
        }
        return true;
    }

    public Card.Suit chooseNewSuit() {
        printHand();
        System.out.print("What would you like to be the new suit? ");
        String response = console.nextLine();
        while (!Arrays.asList(validSuits).contains(response.toUpperCase())) {
            System.out.print("Please enter a valid option (Clubs, Diamonds, Hearts, or Spades): ");
            response = console.nextLine();
        }
        System.out.println();
        return Card.Suit.valueOf(response.toUpperCase());
    }
}