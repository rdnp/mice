package game;

import model.Position;

/**
 * An abstract class that represents the state of a {@link Player}.
 * The behavior of a Player in a {@link Game} is dependent on its state.
 * Essentially there are two states for a Player: one state, in which the
 * player moves automatically and one state in which the Player is moved by the
 * user.
 * @author Richard Pohl*/
public abstract class PlayerState {
	
	/** The Player this state refers to.*/
	protected Player player;
	
	/** @param desire - the {@link Position} the {@link Player} should desire to 
	 * achieve with his next move. */
	protected Position desire;
	
	/** 
	 * Constructs a {@link PlayerState}. 
	 * @param player - the player that behaves according to the 
	 * {@link PlayerState}. This constructor sets the {@link PlayerState} of
	 * player to the newly constructed object.  
	 * */
	public PlayerState(Player player) {
		this.player = player;
		player.setPlayerState(this);
	}
	
	/** @return {@link Automaton#player} */
	public Player getPlayer() {
		return player;
	}
	
	/** @param desire - the {@link Position} the {@link Player} should desire to 
	 * achieve with his next move. */
	public void desire(Position desire) {
		this.desire = desire;
	}
	
	/** Describes the way how a {@link Player} performs a ply in a 
	 * {@link Game} when he is in a specific {@link PlayerState}.*/
	public abstract void turn();
}
