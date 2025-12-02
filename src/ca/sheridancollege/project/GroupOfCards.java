/**
 * @author Joren Rafeiro
 * @date November 15, 2025
 * @description Group of Cards Class (to create groups of card objects)
 */
package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.Collections;

public class GroupOfCards {

    // Holds all cards in this group
    private ArrayList<Card> cards;

    // The maximum allowed size (not enforced strictly here)
    private int size;

    // GroupOfCards Constructor
    public GroupOfCards(int size) {
        this.size = size;

        // Initialize the internal list of cards
        this.cards = new ArrayList<>();
    }

    // Return all cards as an ArrayList
    public ArrayList<Card> getCards() {
        return cards;
    }

    // Shuffle the cards randomly
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // Add a single card to the group
    public void addCard(Card c) {
        cards.add(c);
    }

    // Deal one card from the top of the deck
    public Card dealCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }

    // Return the maximum size of the group
    public int getSize() {
        return size;
    }

    // Set a new maximum size
    public void setSize(int size) {
        this.size = size;
    }

}
