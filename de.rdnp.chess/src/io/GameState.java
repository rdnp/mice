package io;

import game.Game;
import game.Player;
import model.Position;

/**
 * Contains the persistent attributes of a {@link Game}.
 * @author Richard Pohl
 * */
public class GameState {
	private Position currentPosition;
	private Player activePlayer;
	
	/**
	 * Constructs a {@link GameState}.
	 * @param currentPosition - the position to include in the GameState.
	 * @param activePlayer - the player at move to include in the GameState.
	 * */
	public GameState(Position currentPosition, Player activePlayer) {
		this.currentPosition = currentPosition;
		this.activePlayer = activePlayer;
	}
	
	public Position getCurrentPosition() {
		return currentPosition;
	}
	
	public Player getActivePlayer() {
		return activePlayer;
	}
	
}

