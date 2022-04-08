import java.util.*;

public class CrazyEightsMain {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        rules();
        scoring();
        String name = getName(console);
        int players = getPlayers(console);
        console.nextLine();
        CrazyEightsAdmin admin = new CrazyEightsAdmin(name, players, console);
        do {
            admin.deal();
            admin.play();
            admin.reset();
            //admin.printStats("Current scores:");
            //admin.println();
        } while (menu(console).equalsIgnoreCase("Y"));
        System.out.println();
        admin.printStats("Final leaderboard:");
    }

    public static void rules() {
        System.out.println("Welcome to Crazy Eights, a program which");
        System.out.println("simulates the titular card game.");
        System.out.println();
        System.out.println("RULES:");
        System.out.println("-----------------------------------------------------");
        System.out.println("After the first card is flipped, players must either");
        System.out.println("follow suit or rank, or draw. Players who are unable");
        System.out.println("to follow suit must draw a card, and if all cards in");
        System.out.println("the draw pile are exhausted, then the player will be");
        System.out.println("forced to pass. Eights are wild, meaning that players");
        System.out.println("may select a new suit of their choice.");
        System.out.println();
        System.out.println("A winner is determined by the first player to \"go out\";");
        System.out.println("that is, they are the first to play all of their cards.");
        System.out.println();
    }

    public static void scoring() {
        System.out.println("SCORING:");
        System.out.println("-----------------------------------------------------");
        System.out.println("When a game ends, the winning player receives the sum");
        System.out.println("of all cards in the other players' hands. Apart from");
        System.out.println("face cards, which are worth 10 points, all cards are");
        System.out.println("worth their face value. Remaining eights are worth 50");
        System.out.println("points.");
        System.out.println();
    }

    public static String getName(Scanner console) {
        System.out.print("Enter a player name: ");
        String name = console.nextLine();
        if (name.isEmpty()) {
            System.out.println("Names must be at least one character long.");
            System.out.print("Enter a player name: ");
            name = console.nextLine();
        }
        System.out.println();
        return name;
    }

    public static int getPlayers(Scanner console) {
        System.out.print("How many players (min 2, max 7)? ");
        while (!console.hasNextInt()) {
            System.out.print("Please enter a number: ");
            console.nextLine();
        }
        int players = console.nextInt();
        if (players < 2 || players > 7) {
            System.out.println("Invalid number.");
            return getPlayers(console);
        }
        return players;
    }

    public static String menu(Scanner console) {
        System.out.println("Do you want to play again?");
        System.out.print("(\"Y\" for yes, \"N\" for no): ");
        String choice = console.nextLine();
        while (!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N")) {
            System.out.print("Please enter either \"Y\" or \"N\": ");
            choice = console.nextLine();
        }
        return choice;
    }
}
