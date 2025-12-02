/**
 * @author Joren Rafeiro
 * @date November 15, 2025
 * @description Game Controller
 */
package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.Scanner;

public class BlackjackGame extends Game {

    // The deck used for gameplay
    private GroupOfCards deck;

    // Counters to track total wins for player and dealer
    private int playerWins = 0;
    private int dealerWins = 0;

    // Enum to hold values
    private enum values {
        A("A"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"),
        EIGHT("8"), NINE("9"), TEN("10"), J("J"), Q("Q"), K("K");

        private final String label;

        values(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    // BlackjackGame Constructor
    public BlackjackGame(String name) {
        super(name);
    }

    // Method to build a shuffled 52 card deck
    private void buildDeck() {

        // Create a new GroupOfCards with capacity 52
        deck = new GroupOfCards(52);

        // Loop through every suit and rank to build each card
        for (PlayingCard.Suit s : PlayingCard.Suit.values()) {
            for (values v : values.values()) {

                // Create each PlayingCard and add it to the deck
                deck.addCard(new PlayingCard(v.toString(), s));
            }
        }

        // Shuffle the deck once it is fully built
        deck.shuffle();
    }

    // Method to loop game until the user quits
    @Override
    public void play() {

        // Create a Scanner for user input
        Scanner input = new Scanner(System.in);

        // Ask user for a display name
        System.out.print("Enter your name: ");
        String name = input.nextLine().trim();

        // Default to "Player" if no name given
        if (name.isEmpty()) {
            name = "Player";
        }

        // Create Player objects for the user and dealer
        Player player = new Player(name);
        Player dealer = new Player("Dealer");

        // Store them inside the Game class's player list
        ArrayList<Player> list = new ArrayList<>();
        list.add(player);
        list.add(dealer);
        setPlayers(list);

        // Loop condition so the player can play multiple rounds
        boolean again = true;

        while (again) {

            // Start a new round: build a new deck and clear both hands
            buildDeck();
            player.clearHand();
            dealer.clearHand();

            // Deal two cards to each player
            player.addCard(deck.dealCard());
            dealer.addCard(deck.dealCard());
            player.addCard(deck.dealCard());
            dealer.addCard(deck.dealCard());

            // Display player hand and dealer's first card
            System.out.println("\n" + player.getName() + "'s hand: " + show(player)
                    + " (score " + player.getBestScore() + ")");
            System.out.println("Dealer shows: " + dealer.getHand().get(0));

            // Check for immediate blackjack
            if (player.hasBlackjack() || dealer.hasBlackjack()) {

                // Show dealer full hand and resolve the round immediately
                declareWinner(player, dealer);

            } else {

                // Player’s turn: ask to hit or stand until bust or stand
                while (!player.isBusted()) {

                    // Ask player for decision
                    System.out.print("Hit or stand? (h/s): ");
                    String in = input.nextLine().trim().toLowerCase();

                    // Check if player hits
                    if (in.startsWith("h")) {

                        // Deal a card
                        Card c = deck.dealCard();
                        player.addCard(c);

                        // Display new state
                        System.out.println("You drew " + c);
                        System.out.println("Your hand: " + show(player)
                                + " (score " + player.getBestScore() + ")");

                        // If player hits 21 (non-natural 21), they must stop
                        if (player.getBestScore() == 21) {
                            break;
                        }

                    } else if (in.startsWith("s")) {

                        // Break out of loop if player stands
                        break;

                    } else {
                        System.out.println("Sorry, that was not a valid entry. Please enter h or s.");
                    }

                }

                // Dealer’s turn
                if (!player.isBusted()) {

                    // Show dealer’s full hand
                    System.out.println("\nDealer's hand: " + show(dealer)
                            + " (score " + dealer.getBestScore() + ")");

                    // Dealer hits until reaching at least 17
                    while (dealer.getBestScore() < 17) {
                        Card c = deck.dealCard();
                        dealer.addCard(c);
                        System.out.println("Dealer draws " + c);
                    }
                }

                // Determine and announce the winner of round
                declareWinner(player, dealer);
            }

            // Ask if the user wants to play another round
            System.out.print("\nPlay again? (y/n): ");
            again = input.nextLine().trim().toLowerCase().startsWith("y");
            // Print a line for readability
            System.out.println("\n------------------------------------");
        }

        // Display final score after the player chooses to stop playing
        System.out.println("\nFinal Score:");
        System.out.println(player.getName() + ": " + playerWins);
        System.out.println("Dealer: " + dealerWins);
        System.out.println("\nThanks for playing!");
        System.out.println("\nCreated by:");
        System.out.println("Joren Rafeiro");
        System.out.println("Gian Victor Pujante");
        System.out.println("Ibrahim Adam");
        System.out.println("Le Duy Toan Nguyen Nguyen");
        // Print a line for readability
        System.out.println("\n------------------------------------");

    }

    // Method to print a player's hand as a comma-separated line (StringBuilder removed)
    private String show(Player p) {
        String result = "";

        // Loop through each card in the player's hand
        for (int i = 0; i < p.getHand().size(); i++) {

            // Add card as text
            result += p.getHand().get(i).toString();

            // Add comma spacing except after the last card
            if (i < p.getHand().size() - 1) {
                result += ", ";
            }
        }
        return result;
    }

    // Method to determine which player wins a round of Blackjack
    private void declareWinner(Player player, Player dealer) {

        System.out.println("\nFinal Hands:");
        System.out.println(player.getName() + ": " + show(player)
                + " (" + player.getBestScore() + ")");
        System.out.println("Dealer: " + show(dealer)
                + " (" + dealer.getBestScore() + ")");

        // Check bust conditions first
        if (player.isBusted()) {
            System.out.println("Dealer wins — player busted.");

            // Add dealer win to counter
            dealerWins++;

        } else if (dealer.isBusted()) {
            System.out.println(player.getName() + " wins — dealer busted!");

            // Add player win to counter
            playerWins++;

        } // Check Blackjack conditions (real two-card blackjack)
        else if (player.hasBlackjack() && !dealer.hasBlackjack()) {
            System.out.println(player.getName() + " wins with Blackjack!");

            // Add player win to counter
            playerWins++;

        } else if (dealer.hasBlackjack() && !player.hasBlackjack()) {
            System.out.println("Dealer wins with Blackjack.");

            // Add dealer win to counter
            dealerWins++;

        } // Compare scores normally
        else if (player.getBestScore() > dealer.getBestScore()) {
            System.out.println(player.getName() + " wins!");

            // Add player win to counter
            playerWins++;

        } else if (dealer.getBestScore() > player.getBestScore()) {
            System.out.println("Dealer wins.");

            // Add dealer win to counter
            dealerWins++;

        } else {
            System.out.println("Push (tie). No points awarded.");
        }

        // Display the running score after each game
        System.out.println("\nCurrent Score:");
        System.out.println(player.getName() + ": " + playerWins);
        System.out.println("Dealer: " + dealerWins);
    }

    // Method to print out the winner
    @Override
    public void declareWinner() {
        // Required by abstract class but unused
        System.out.println("Winner declared.");
    }
}
