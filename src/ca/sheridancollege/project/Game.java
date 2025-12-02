/**
 * @author Joren Rafeiro
 * @date November 15, 2025
 * @description Game Class (to be used by Blackjack Game)
 */
package ca.sheridancollege.project;

import java.util.ArrayList;

public abstract class Game {

    // Create instance variables
    private final String name;
    private ArrayList<Player> players;

    // Game Constructor
    public Game(String name) {
        this.name = name;
        players = new ArrayList<>();
    }

    // Create getters
    public String getName() {
        return name;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    // Create setters
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    // Create abstract methods (for play and declareWinner)
    public abstract void play();
    
    public abstract void declareWinner();
}
