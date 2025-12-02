/**
 * @author Joren Rafeiro
 * @date November 15, 2025
 * @description Group of Cards Class (to create groups of card objects)
 */
package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.Collections;

public class GroupOfCards {

    // Create instance variables
    private ArrayList<Card> cards;
    private int size;

    // GroupOfCards Constructor
    public GroupOfCards(int size) {
        this.size = size;

        // Initialize the internal list of cards
        this.cards = new ArrayList<>();
    }

    // Create getters
    public ArrayList<Card> getCards() {
        return cards;
    }
    
    public int getSize() {
        return size;
    }
    
    // Create setters
    public void setSize(int size) {
        this.size = size;
    }

    // Method to shuffle cards
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // Method to add a card to the group
    public void addCard(Card c) {
        cards.add(c);
    }

    // Method to deal one card from the top of the deck
    public Card dealCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }

}
