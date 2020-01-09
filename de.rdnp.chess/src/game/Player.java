package game;

import java.util.List;
import java.util.ArrayList;

import model.ChessLocation;
import model.Figure;
import model.HashKeyProvider;
import model.King;
import model.Pawn;
import model.Position;


/** @author Richard Pohl */
public class Player {

	/** The white {@link Player}. */
	public static final Player WHITE = new Player(new HashKeyProvider(1987));

	/** The black {@link Player}. */
	public static final Player BLACK = new Player(new HashKeyProvider(2502));
	
	/** The {@link Game} in which the {@link Player} plays.*/
	private Game game;
	
	/** The {@link PlayerState} of the {@link Player} (a {@link Human} 
	 * or an {@link Automaton}). 
	 * Initialized with a new game. */
	private PlayerState playerState;

	/** Provides keys for hashing in {@link Position}.*/
	private HashKeyProvider hashKeyProvider;	
	
	/** Constructs a player.
	 * @param hashKeyProvider - a {@link HashKeyProvider} that provides keys 
	 * for the hashing of positions depending on the state of fields on the
	 * chess board - the keys should depend on a player. 
	 *  */
	private Player(HashKeyProvider hashKeyProvider) {
		this.hashKeyProvider = hashKeyProvider;
	}
	
	/**@return The game in which the {@link Player} plays. */
	public Game getGame() {
		return game;
	}

	/**Sets the game in which the {@link Player} plays. Called by
	 * methods within {@link Game} to parametrize the static player constants
	 * {@link Player#WHITE} and {@link Player#BLACK}. 
	 * @param game - the Game in which the player will play.*/
	void setGame(Game game) {
		this.game = game;
		// this depends on the game so it cannot be initialized before the 
		// game is set.
		playerState = new Human(this);
	}
	
	/**Sets the {@link PlayerState} in which the {@link Player} plays. 
	 * Should be called by constructor of PlayerState only, in order to maintain
	 * consistency of the inverse reference (the reference on the Player from
	 * its PlayerState).
	 * {@link Player#WHITE} and {@link Player#BLACK}. 
	 * @param playerState - the PlayerState in which the player will play.*/
	void setPlayerState(PlayerState playerState) {
		this.playerState = playerState;
	}
	
	/** @return The {@link HashKeyProvider} of this {@link Player}.*/
	public HashKeyProvider getHashKeyProvider() {
		return hashKeyProvider;
	}
		
	/** @return The opponent {@link Player} of the Player. */
	public Player getOpponent() {
		if (this.equals(WHITE))
			return BLACK;
		else if (this.equals(BLACK))
			return WHITE;
		return null;
	}

	/** Gets all the {@link Figure} instances of the {@link Player} from a 
	 * position.
	 * @param position - the position from which the Figures should be 
	 * 	extracted.
	 * @return A list of Figure instances in the following order: 
	 * First, all Figure instances that are neither {@link Pawn} nor 
	 * {@link King}, then all Pawn instances, then the King instance. 
	 * May cause an unexpected result
	 * if the Player has more than one King instance in position 
	 * (will probably only include an arbitrary one of the King instances in 
	 * that case).  */
	public List<Figure> getFigures(Position position) {
		List<Figure> result = new ArrayList<Figure>();
		Figure figure = null, king = null;
		for (int x = 0; x < 8; x++)
			for (int y = 0; y < 8; y++)
				if ((figure = position.getFigureAt(new ChessLocation(x, y)))
						!= null
						&& this.equals(figure.getOwner()))
				{
					if (figure.getClass() != Pawn.class 
							&& figure.getClass() != King.class)
						// not a pawn and not a king => beginning
						result.add(0, figure);
					else if (figure.getClass() != King.class)
						// pawns to the end
						result.add(figure);
					else 
						// do not add king, until all other elemens added
						king = figure;
				}
		// the king should be the last element
		if (king != null)
			result.add(king);
		return result;
	}
	
	/** 
	 * @return true, iff the Player is still participating in the {@link Game},
	 * i.e., iff he has (at least) one {@link King} instance on the board.*/
	public boolean isPlaying()
	{
		for (Figure figure: getFigures(game.getPosition()))
		{
			if (figure.getClass() == King.class)
				return true;
		}
		return false;
	}
	
	/** 
	 * @return The {@link Player}'s {@link King}, null in the absurd situation
	 * that the Player has no King.*/
	private King getKing()
	{
		for (Figure figure: getFigures(game.getPosition()))
		{
			if (figure.getClass() == King.class)
				return (King) figure;
		}
		return null;
	}

	@Override
	public String toString() {
		if (this.equals(BLACK))
			return "BLACK";
		if (this.equals(WHITE))
			return "WHITE";
		else
			return "(invalid player)";
	}

	/* Methods for setting the Player's state. */
	/** Sets the player state to {@link Automaton}. 
	 * @param quality - The quality of the computation of plies. Higher quality
	 * requires longer computation times.*/
	public void playAutomaton(int quality)
	{
		new Automaton(this, quality);
	}
	
	/** Sets the player state to {@link Human}. */
	public void playHuman()
	{
		new Human(this);
	}

	/* Methods for computation of plies and turn. */

	/** 
	 * @param position - the {@link Position} to check.
	 * @return true, iff the Player is in chess in position.*/
	public boolean chess(Position position)
	{
		// chess means that there exists a ply with which the opponent could
		// take the player's king, if he was at move...
		// compute all legal plies of the opponent, not considering chess
		// (which is to avoid an infinite recursion)
		Position[] opponentPlies = getOpponent().computePlies(position, false);
		for (int i = 0; opponentPlies[i] != null; i++)
		{
			// check, if the king still exists, assuming the worst, until he
			// was actually found:
			List<Figure> figuresAfterOpponentPly = getFigures(opponentPlies[i]);
			// king is last in the list:
			boolean kingExists = figuresAfterOpponentPly
					.get(figuresAfterOpponentPly.size() - 1).equals(getKing());
			// chess situation is given, if there exists a ply with which the
			// opponent could take this players king => return true, if we
			// have found a ply that leads to a situation where no kingExists.
			if (!kingExists)
				return true;
		}
		return false;
	}
	
	/** 
	 * @param position - the {@link Position} to check.
	 * @param location - the {@link ChessLcation}s to check. 
	 * @return true, iff the Player was in chess, if his king was
	 * at a location within the given {@link ChessLocation} array.*/
	public final boolean chess(Position position, ChessLocation[] locations)
	{
		for (int i = 0; i < locations.length; i++)
			if (chess(new Position(position, getKing(), locations[i])))
				return true;
		return false;
	}
	
	/**
	 * Computes at least all legal positions reachable with one ply for the 
	 * player from position.
	 * @param position - the {@link Position} to compute the resulting positions 
	 * from.
	 * @param considerChess - whether to consider chess for the computation of
	 * legal moves. This parameter is internally used to break the infinite 
	 * recursion of chess computation and ply computation that depend on each 
	 * other, but it can also be used to increase performance in the case that
	 * the sender does not care of receiving a result containing some 
	 * illegal moves.
	 * @return All positions reachable from this position with one ply.
	 * The positions are returned in an array with a fixed length. 
	 * The last elements of the array will be null.
	 */
	public Position[] computePlies(Position position, boolean considerChess)
	{
		// initialize: 149 is the theoretical overall maximum number of legal 
		// plies
		// TODO check if this threshold holds when multiple transformed queens
		// are on the board!!!
		Position[] result = new Position[150];
		int resultIndex = 0;
		List<Figure> figures = getFigures(position);
		// add only a restricted number of available plies per figure
		for (int iF = 0; iF < figures.size(); iF++)
		{
			Position[] figurePositions 
				= figures.get(iF).computeOnePly(position);
			for (int ip = 0; figurePositions[ip] != null; ip++)
			{
				if (!(considerChess && chess(figurePositions[ip])))
					result[resultIndex++] = figurePositions[ip];
			}
		}
		return result;
	}
	
	/**
	 * Computes at least all legal positions reachable with one ply for the 
	 * player from position.
	 * @param position - the {@link Position} to compute the resulting positions 
	 * from.
	 * @return All positions reachable from this position with one ply.
	 * The positions are returned in an array with a fixed length. 
	 * The last elements of the array will be null.
	 */
	public Position[] computePlies(Position position)
	{
		return computePlies(position, true);
	}

	/** 
	 * Causes the player to desire to achieve position with his next ply.
	 * @param position - the {@link Position} to desire.
	 * */
	public void desire(Position desire)
	{
		playerState.desire(desire);
	}

	/** Causes this {@link Player} to lose the {@link Game} he plays in. */
	public void resign()
	{
		game.win(getOpponent());
	}
	
	/** 
	 * Causes the player to turn in his {@link Game}, i.e. to perform a ply.
	 * If this is impossible, the player will end the Game.
	 * */
	public void turn()
	{
		// check, if there are legal moves, else lose or draw the game
		Position[] onePlyPositions = computePlies(game.getPosition());
		int plyCount = 0; 
		for (; onePlyPositions[plyCount] != null; plyCount++)
			; // this is a no-op counting loop to count the legal plies
		if (plyCount == 0)
		{
			if (chess(game.getPosition()))
				// make the opponent win, since this player is checkmate
				game.win(getOpponent());
			else
				// no more moves and no chess => draw
				game.draw();
		}
		else
			playerState.turn();
	}

}
