package game;

/**
 * Represents the {@link GameMode} in which a {@link Game} is an interaction 
 * between {@link Player} instances.
 * @author Richard Pohl*/
public class InteractiveMode extends GameMode {
	
	/**
	 * Constructs an {@link InteractiveMode} 
	 * (see {@link GameMode#GameMode(Game)}.
	 * */
	public InteractiveMode(Game game) {
		super(game);
	}
	
	/** Moves the player as long as he is playing in the game (i.e. as long
	 * as he did not lose or resign.) */
	@Override
	public void play() {
		canceled = false;
		while (/* TODO remove game.getActivePlayer().isPlaying() && */!canceled)
		{
			game.getActivePlayer().turn();
		}
	}
}
