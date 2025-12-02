/**
 * @author Joren Rafeiro
 * @date November 15, 2025
 * @description Playing Card Class (creates PlayingCard objects extending the card class)
 */
package ca.sheridancollege.project;

public class PlayingCard extends Card {

    // Enum of the four standard suits
    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    // The card's value (A, 2-10, J, Q, K)
    private final String value;

    // The suit of the card
    private final Suit suit;

    // PlayingCard Constructor
    public PlayingCard(String value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    // Method to check if the card is an Ace
    public boolean isAce() {
        return value.equals("A");
    }

    // Method to return the numeric Blackjack value of this card
    public int getValue() {

        // Check if the card is an Ace, return value 1
        if (value.equals("A")) {
            return 1;
        }

        // Check if value is J, Q, or K, value is 10
        if (value.equals("J") || value.equals("Q") || value.equals("K")) {
            return 10;
        }

        // Parse the number from the value string (when the if statements are false)
        return Integer.parseInt(value);
    }

    // Method to convert the card into a printable string rank + suitSymbol
    @Override
    public String toString() {

        String symbol;

        // Determine which Unicode symbol to use based on suit
        if (suit == Suit.HEARTS) {
            symbol = "♥";
        } else if (suit == Suit.DIAMONDS) {
            symbol = "♦";
        } else if (suit == Suit.CLUBS) {
            symbol = "♣";
        } else {
            symbol = "♠";
        }

        // Example: "K♦" or "10♠"
        return value + symbol;
    }
}
