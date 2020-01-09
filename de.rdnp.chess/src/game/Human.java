package game;

import model.Position;

/**
 * {@link Human} is the {@link PlayerState} of a {@link Player}, in which
 * his behaviour is controlled by the user. In this state, there is no automatic
 * selection of plies.
 * @author Richard Pohl*/
public class Human extends PlayerState  {
	
	/**
	 * Constructs a {@link Human} (see {@link PlayerState#PlayerState(Player)}).
	 * */
	public Human(Player player) {
		super(player);
	}
	
	/** Performs a ply for the player this {@link Human} is associated to
	 * (see {@link PlayerState#player}) in his {@link Game}. Called indirectly
	 * by the UI via the {@link GameMode}, when the user enters a legal ply. */	
	@Override
	public void turn() {
		// compute the legal plies for the player 
		Position[] legalPlies 
			= player.computePlies(player.getGame().getPosition());
		for (int i = 0; legalPlies[i] != null; i++)
		{
			// if the desire of the player is legal, do it!
			if (legalPlies[i].equals(desire))
				player.getGame().turn(desire);
		}
	}
}
