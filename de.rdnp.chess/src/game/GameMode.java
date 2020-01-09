package game;

/**
 * Describes the mode of the {@link Game}. The game can basically be in two 
 * modes:
 * 	- {@link PositionMode}: a mode where the user can enter and edit positions.
 *  - {@link InteractiveMode}: a mode where the user plays an interactive game.
 * @author Richard Pohl*/
public abstract class GameMode {
	/** A flag indicating that the current computation should be canceled.
	 * Will become true when the {@link Game} is stoped by calling
	 * {@link GameMode#stopPlaying()}. */
	protected boolean canceled = false;
	
	/** The game of this {@link GameMode}. */
	protected Game game;
	
	/**
	 * Constructs a {@link GameMode}. 
	 * @param game - the {@link Game} to contruct the GameMode for.
	 * */
	public GameMode(Game game) {
		this.game = game;
	}
	
	/** Describes what to do when playing a {@link Game} in a {@link GameMode}. 
	 */
	protected abstract void play();
	
	/** Stops playing a {@link Game} in a {@link GameMode}. 
	 */
	public void stopPlaying() {
		canceled = true;
	}
}
