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

    // BlackjackGame Constructor
    public BlackjackGame(String name) {
        super(name);
    }

    // Method to build a shuffled 52 card deck
    private void buildDeck() {

        // Create a new GroupOfCards with capacity 52
        deck = new GroupOfCards(52);

        // All possible card values
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        // Loop through every suit and rank to build each card
        for (PlayingCard.Suit s : PlayingCard.Suit.values()) {
            for (String v : values) {

                // Create each PlayingCard and add it to the deck
                deck.addCard(new PlayingCard(v, s));
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

            // Display player hand and dealer's first (face-up) card
            System.out.println("\n" + player.getName() + "'s hand: " + show(player)
                    + " (score " + player.getBestScore() + ")");
            System.out.println("Dealer shows: " + dealer.getHand().get(0));

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
                } // Check if player stands
                else if (in.startsWith("s")) {
                    // Break out of loop
                    break;

                } // Check for invalid input
                else {
                    System.out.println("Enter h or s.");
                }

            }

            // Dealer’s turn
            // Check if player did not bust
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

            // Ask if the user wants to play another round
            System.out.print("\nPlay again? (y/n): ");
            again = input.nextLine().trim().toLowerCase().startsWith("y");
        }
    }

    // Method to print a player's hand as a comma-separated line
    private String show(Player p) {
        StringBuilder sb = new StringBuilder();

        // Loop through each card in the player's hand
        for (int i = 0; i < p.getHand().size(); i++) {
            sb.append(p.getHand().get(i).toString());

            // Add comma spacing except after final card
            if (i < p.getHand().size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
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

        } else if (dealer.isBusted()) {
            System.out.println(player.getName() + " wins — dealer busted!");
        } // Check Blackjack conditions
        else if (player.hasBlackjack() && !dealer.hasBlackjack()) {
            System.out.println(player.getName() + " wins with Blackjack!");

        } else if (dealer.hasBlackjack() && !player.hasBlackjack()) {
            System.out.println("Dealer wins with Blackjack.");

        } // Compare scores
        else if (player.getBestScore() > dealer.getBestScore()) {
            System.out.println(player.getName() + " wins!");

        } else if (dealer.getBestScore() > player.getBestScore()) {
            System.out.println("Dealer wins.");

        } else {
            System.out.println("Push (tie).");
        }
    }

    // Method to print out the winner
    @Override
    public void declareWinner() {
        // Required by abstract class but unused
        System.out.println("Winner declared.");
    }
}
