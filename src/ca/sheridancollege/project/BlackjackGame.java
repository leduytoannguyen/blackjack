/**
 * @author Joren Rafeiro
 * @date November 15, 2025
 * @description Game Controller
 */
package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.Scanner;

public class BlackjackGame extends Game {

    // Create the deck used for gameplay
    private GroupOfCards deck;

    // Create counters to track total wins for player and dealer
    private int playerWins = 0;
    private int dealerWins = 0;

    // Create enum to hold values
    private enum values {
        A("A"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"),
        EIGHT("8"), NINE("9"), TEN("10"), J("J"), Q("Q"), K("K");

        // Create label variable to properly display the card
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

        // Loop through every suit and value to build each card
        for (PlayingCard.Suit s : PlayingCard.Suit.values()) {
            for (values v : values.values()) {

                // Create each PlayingCard and add it to the deck
                deck.addCard(new PlayingCard(v.toString(), s));
            }
        }

        // Shuffle the deck
        deck.shuffle();
    }

    // Method to loop game until the user quits
    @Override
    public void play() {

        // Create a Scanner for user input
        Scanner input = new Scanner(System.in);

        // Ask user for their name
        System.out.print("Enter your name: ");
        String name = input.nextLine().trim();

        // Make their name "Player" if nothing was entered
        if (name.isEmpty()) {
            name = "Player";
        }

        // Print a line for readability
        System.out.println("\n------------------------------------");

        // Create Player objects for the user and dealer
        Player player = new Player(name);
        Player dealer = new Player("Dealer");

        // Store them inside the Game class's player list
        ArrayList<Player> list = new ArrayList<>();
        list.add(player);
        list.add(dealer);
        setPlayers(list);

        // Loop condition so the user can play multiple rounds
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
            System.out.println("Dealer shows: " + dealer.getHand().get(0) + " ?");

            // Check if the player has Blackjack
            if (player.hasBlackjack()) {

                // Show dealer full hand end the round
                declareWinner(player, dealer);

            } else {

                // User's turn (only loop if they did not bust)
                while (!player.isBusted()) {

                    // Ask user to hit or stand
                    System.out.print("\nHit or stand? (h/s): ");
                    String in = input.nextLine().trim().toLowerCase();

                    
                    // Check if user hits
                    if (in.startsWith("h")) {

                        // Deal a card
                        Card c = deck.dealCard();
                        player.addCard(c);

                        // Display new state
                        System.out.println("You drew " + c);
                        System.out.println("Your hand: " + show(player)
                                + " (score " + player.getBestScore() + ")");

                        // Check if user has score of 21
                        if (player.getBestScore() == 21) {
                            break;
                        }

                    } // Check if user stands
                    else if (in.startsWith("s")) {
                        break;

                    } // Invalid input
                    else {
                        System.out.println("Sorry, that was not a valid entry. Please try again.");
                    }

                }

                // Dealer’s turn
                if (!player.isBusted()) {

                    // Check if dealer has Blackjack
                    if (dealer.hasBlackjack()) {
                        // Declare winner immediately
                        declareWinner(player, dealer);
                    }

                    // Show dealer’s full hand
                    System.out.println("\nDealer's hand: " + show(dealer)
                            + " (score " + dealer.getBestScore() + ")");

                    // Make dealer hit until win or tie
                    while (dealer.getBestScore() < player.getBestScore()) {
                        Card c = deck.dealCard();
                        dealer.addCard(c);
                        System.out.println("Dealer draws " + c);
                    }
                }

                // Determine and announce the winner of round
                declareWinner(player, dealer);
            }

            // Create a variable to detect valid input
            boolean validInput = false;
            // Loop until input is valid
            while (!validInput) {
                // Ask the user if they want to play again
                System.out.print("\nPlay again? (y/n): ");
                String response = input.nextLine().trim().toLowerCase();

                // Check if the input was yes
                if (response.equals("y") || response.equals("yes")) {
                    again = true;
                    validInput = true;
                } // Check if the input was no
                else if (response.equals("n") || response.equals("no")) {
                    again = false;
                    validInput = true;
                } // Invalid inout
                else {
                    System.out.println("Sorry, that was not a valid entry. Please try again.");
                }
            }
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
        System.out.println("Le Duy Toan Nguyen");
        // Print a line for readability
        System.out.println("\n------------------------------------");

    }

    // Method to print a Player's hand
    private String show(Player p) {
        // Create empty String to hold the printed hand
        String result = "";

        // Loop through each card in the Players's hand
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

    // Method to determine which Player wins a round of Blackjack
    private void declareWinner(Player player, Player dealer) {

        // Display final hands
        System.out.println("\nFinal Hands:");
        System.out.println(player.getName() + ": " + show(player)
                + " (" + player.getBestScore() + ")");
        System.out.println("Dealer: " + show(dealer)
                + " (" + dealer.getBestScore() + ")");

        // Check bust conditions
        if (player.isBusted()) {
            System.out.println("Dealer wins — player busted.");

            // Add dealer win to counter
            dealerWins++;

        } else if (dealer.isBusted()) {
            System.out.println(player.getName() + " wins — dealer busted!");

            // Add player win to counter
            playerWins++;

        } // Check Blackjack conditions
        else if (player.hasBlackjack() && !dealer.hasBlackjack()) {
            System.out.println(player.getName() + " wins with Blackjack!");

            // Add player win to counter
            playerWins++;

        } else if (dealer.hasBlackjack() && !player.hasBlackjack()) {
            System.out.println("Dealer wins with Blackjack.");

            // Add dealer win to counter
            dealerWins++;

        } // User wins
        else if (player.getBestScore() > dealer.getBestScore()) {
            System.out.println(player.getName() + " wins!");

            // Add player win to counter
            playerWins++;

        } // Dealer wins
        else if (dealer.getBestScore() > player.getBestScore()) {
            System.out.println("Dealer wins.");

            // Add dealer win to counter
            dealerWins++;

        } // Tie
        else {
            System.out.println("Push. No points awarded.");
        }

        // Display the running score after each game
        System.out.println("\nCurrent Score:");
        System.out.println(player.getName() + ": " + playerWins);
        System.out.println("Dealer: " + dealerWins);
    }

    // Method declareWinner (required by abstract class)
    @Override
    public void declareWinner() {
        // Do nothing
    }
}
