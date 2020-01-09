package game;

import io.GameState;
import io.GameStateParser;
import io.GameStateWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Observable;

import model.Position;



/** 
 * Represents a chess game between two {@link Player} instances: 
 * {@link Player#WHITE} and {@link Player#BLACK}.
 * @author Richard Pohl*/
public class Game extends Observable {
	
	/** The current {@link GameMode} of the {@link Game}. Specifies its 
	 * user-observable behavior. */
	private GameMode gameMode;
		
	/**
	 * The {@link GameHistory} of the {@link Game}.
	 * */
	private GameHistory gameHistory;
	
	/** The current {@link Position} in the {@link Game}. */
	private Position position;
		
	/** The {@link Player} at move in the {@link Game}, null if the Game has 
	 * ended. */
	private Player activePlayer;
	
	/** Winner of the {@link Game}, null if the Game has not finished yet. */
	private Player winner;
	
	/** A {@link Thread} that does the move operation asynchronously to the GUI. 
	 */
	private Thread moveThread;
	
	/** Constructs a new game, setting it to the initial {@link Position}. */
	public Game() {
		this.position = new Position();		
		this.activePlayer = Player.WHITE;
		Player.WHITE.setGame(this);
		Player.BLACK.setGame(this);
		gameHistory = new GameHistory();
		winner = null;
	}
	
	/** Constructs a new game, loading the initial position from a file.
	 * @param fileName  */
	public Game(String fileName) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = 
				new FileInputStream(fileName);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		if (fileInputStream != null)
		{
			GameState parsedState 
				= new GameStateParser(fileInputStream).parse();
			position = parsedState.getCurrentPosition();
			activePlayer = parsedState.getActivePlayer();
		}
		Player.WHITE.setGame(this);
		Player.BLACK.setGame(this);
		gameHistory = new GameHistory();
		winner = null;
	}
	
	/** @return The current {@link Position} in the {@link Game}. */
	public Position getPosition() {
		return position;
	}

	/** Sets the current {@link Position} in the {@link Game}. 
	 * @param position - the new current Position that should be set in the 
	 * Game. */
	public void setPosition(Position position) {
		this.position = position;
		setChanged();
		notifyObservers();
	}

	/** @return The current {@link Player} at move in the {@link Game}.
	 * null, if the game has finished. */
	public Player getActivePlayer() {
		return activePlayer;
	}
	
	/** Sets the current {@link Player} at move in the {@link Game}. If the
	 * Player is playing automatically, this will change nothing on the players
	 * behavior.
	 * If the Player is playing as a {@link Human}, he will try to turn in the
	 * {@link Game} to achieve desire. If this fails (e.g., because desire
	 * cannot be achieved with a legal move, this method will do nothing.
	 * @param activePlayer - the Player at move that should be set in the Game. 
	 * */
	public void setActivePlayer(Player activePlayer) {
		this.activePlayer = activePlayer;
	}
	
	/**
	 * @return The winner of a {@link Game}. null, if the Game is not finished,
	 * or if there is no winner (draw).
	 * */
	public Player getWinner() {
		return winner;
	}
	
	/**
	 * @return The {@link GameHistory} of the {@link Game}.
	 * */
	public GameHistory getGameHistory() {
		return gameHistory;
	}
	
	/** Changes the {@link Player} at move to his opponent and records the last
	 * turn made.
	 * @param next - the position after the turn. */
	public void turn(Position next)
	{
		gameHistory.addPly(new Ply(position, next));
		position = next;
		activePlayer = activePlayer.getOpponent();
		setChanged();
		notifyObservers();
	}

	/** Draws the {@link Game} (terminates it without a winner). */
	public void draw()
	{
		// draw Game
		gameMode.stopPlaying();
		setActivePlayer(null);
		// notify Observers
		setChanged();
		notifyObservers();
	}
	
	/** Causes player to win the {@link Game} (terminates it with a winner). 
	 * @param winner
	 * */
	public void win(Player winner)
	{
		// make winner win
		this.winner = winner;
		gameMode.stopPlaying();
		setActivePlayer(null);
		// notify Observers
		setChanged();
		notifyObservers();
	}
	
	/** Causes this {@link Game} to enter {@link InteractiveMode} and 
	 * immediately starts the game play. */
	public void startInteraction() {
		gameMode = new InteractiveMode(this);
		play();
	}
	
	/** Causes this {@link Game} to enter {@link PositionMode}. Currently does 
	 * not react immediately => TODO. */
	public void stopInteraction() {
		// stop a possibly running old move thread
		gameMode.stopPlaying();
		gameMode = new PositionMode(this);
	}
	
	/** Starts the game play. */
	private void play() {
		// only one player may be active at one time.
		if (moveThread != null)
			gameMode.stopPlaying();
		// start a new Thread in which the move is performed.
		moveThread = new Thread() {
			public void run()
			{
				System.out.println("Starting a new move thread...");
				gameMode.play();
				System.out.println("Leaving a move thread...");
			}
		};
		moveThread.start();
	}
	
	public void save(String fileName) {
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = 
				new FileOutputStream(fileName);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		if (fileOutputStream != null)
			new GameStateWriter(fileOutputStream).write(
					new GameState(position, activePlayer));
	}
	
	
	
}
