/**
 * @author Joren Rafeiro
 * @date November 15, 2025
 * @description Game Class (to be used by Blackjack Game)
 */
package ca.sheridancollege.project;

import java.util.ArrayList;

public abstract class Game {

    // The name/title of the game
    private final String name;

    // List of players who are participating
    private ArrayList<Player> players;

    // Game Constructor
    public Game(String name) {
        this.name = name;
        players = new ArrayList<>();
    }

    // Return the name of the game
    public String getName() {
        return name;
    }

    // Get the list of players
    public ArrayList<Player> getPlayers() {
        return players;
    }

    // Set a new list of players
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    // All games must implement a play() method
    public abstract void play();

    // All games must implement a declareWinner() method
    public abstract void declareWinner();
}
