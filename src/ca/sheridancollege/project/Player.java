/**
 * @author Joren Rafeiro
 * @date November 15, 2025
 * @description Player Class (creates Player objects)
 */
package ca.sheridancollege.project;

import java.util.ArrayList;

public class Player {

    // Create instance variables
    private String name;
    private ArrayList<Card> hand = new ArrayList<>();

    // Player Constructor 
    public Player(String name) {
        this.name = name;
    }

    // Create getters
    public String getName() {
        return name;
    }
    
    public ArrayList<Card> getHand() {
        return hand;
    }
    
    public int getBestScore() {

        int total = 0;
        int aceCount = 0;

        // Loop through all cards in the hand
        for (Card c : hand) {

            // Safe cast to PlayingCard
            PlayingCard pc = (PlayingCard) c;

            // Add card's base value (Aces count as 1 here)
            int value = pc.getValue();
            total += value;

            // Track if this card is an Ace
            if (pc.isAce()) {
                aceCount++;
            }
        }

        // Start with total and try to convert Aces to 11 when possible
        int best = total;

        // Convert as many Aces as possible from 1 → 11 without busting
        while (aceCount > 0 && best + 10 <= 21) {
            best += 10;
            aceCount--;
        }

        // Return the highest valid score
        return best;
    }

    // Create setters
    public void setName(String name) {
        this.name = name;
    }

    // Method to add a card to the player's hand
    public void addCard(Card c) {
        hand.add(c);
    }

    // Method to clear all cards from the hand
    public void clearHand() {
        hand.clear();
    }

    // Method to check if Player's score exceeds 21
    public boolean isBusted() {
        return getBestScore() > 21;
    }

    // Method to check if Player Blackjack
    public boolean hasBlackjack() {
        if (hand.size() == 2 && getBestScore() == 21) {
            return true;
        }

        return false;
    }
}
