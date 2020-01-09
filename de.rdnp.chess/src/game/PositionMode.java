package game;

import model.Position;

/**
 * Represents the {@link GameMode} in which a {@link Game} has no interaction
 * and the user can edit the {@link Position}.
 * @author Richard Pohl*/
public class PositionMode extends GameMode {
	
	/**
	 * Constructs an {@link PositionMode} 
	 * (see {@link GameMode#GameMode(Game)}.
	 * */
	public PositionMode(Game game) {
		super(game);
	}
	
	/**
	 * Causes the active {@link Player} in the current {@link Game} to turn,
	 * i.e. to perform exactly one ply. This method calls {@link Player#turn()}.
	 * */
	@Override
	public void play() {
		game.getActivePlayer().turn();
	}
}
