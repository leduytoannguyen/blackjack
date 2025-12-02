/**
 * @author Joren Rafeiro
 * @date November 15, 2025
 * @description Main Method
 */
package ca.sheridancollege.project;

public class BlackjackMain {

    public static void main(String[] args) {

        // Create a new BlackjackGame object with the name "Blackjack"
        BlackjackGame game = new BlackjackGame("Blackjack");

        // Start the game loop
        game.play();
    }
}
