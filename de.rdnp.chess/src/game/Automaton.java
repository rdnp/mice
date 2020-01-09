package game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import main.ChessApplication;
import model.Figure;
import model.King;
import model.Pawn;
import model.Position;
import model.PositionComputationResult;
import model.PositionComputer;

/**
 * An {@link Automaton} is the {@link PlayerState} of a {@link Player}, in which
 * his behaviour is controlled automatically. Moves are selected automatically.
 * @author Richard Pohl*/
public class Automaton extends PlayerState {

	// default value for computation quality
	private static final int DEFAULT_QUALITY = 4;
	
	/** The quality of the ply computation - higher quality means better plies
	 * can be computed at the price of higher computation times. */
	private int quality = DEFAULT_QUALITY;
	
	/** A {@link Semaphore} to control the number of {@link PositionComputer}
	 * instances that are active at a time. This is introduced to optimize the
	 * performance in environments with less than 16 CPUs. */
	private Semaphore positionComputerSemaphore;
	
	/** 
	 * Constructs an {@link Automaton} for a {@link Player}
	 * (see also {@link PlayerState#PlayerState(Player)}).
	 * @param player -
	 * the {@link Player} for which plies should be computed automatically by  
	 * the constructed Automaton.
	 * @param quality - 
	 * the quality of the ply computation - higher quality means better plies
	 * can be computed at the price of higher computation times. 
	 * */
	public Automaton(Player player, int quality) {
		super(player);
		this.quality = quality;
	}
	
	/** The Player this {@link Automaton} computes plies for. */
	public Player getPlayer() {
		return player;
	}
	
	/** @return {@link Automaton#positionComputerSemaphore} */
	public Semaphore getPositionComputerSemaphore() {
		return positionComputerSemaphore;
	}
		
	/** Performs a ply for the player this {@link Automaton} computes plies for 
	 * (see {@link PlayerState#player}) in his {@link Game}. */
	@Override
	public void turn() {
		// 1st step: compute best plies
		Collection<Position> bestPlies = 
			computeBestPlies(player.getGame().getPosition());
		// 2nd step: perform ply in the Game, if any feasible ply was found
		if (!bestPlies.isEmpty())
			player.getGame().turn(bestPlies.iterator().next());
		else {
			// lose the Game if chess (this is checkmate)
			// if (chess TODO!)
			// 		player.getGame().win(player.getOpponent());
			// draw the Game if no chess
			// else
			//		player.getGame().draw();
		}
	}
	
	/**
	 * Computes the best possible plies for the player this  {@link Automaton} 
	 * refers to  ({@link PlayerState#player}), i.e. returned plies are the 
	 * plies with the highest advantage after {@link Automaton#quality} plies 
	 * were considered, starting from the given position and using the base 
	 * model that each player at move will take the ply that produces the 
	 * highest advantage for him after {@link Automaton#quality} plies. 
	 * 
	 * @param position
	 *            - the Position to start from (should be the current position).
	 * @return The positions after the best possible initial plies
	 *  		  (starting with position) were executed.
	 * */
	private List<Position> computeBestPlies(Position position)
	{
		// inititalize data
		List<Position> result = new ArrayList<Position>();
		// getFigures result is used multiple times in the computation
		List<Figure> figures = player.getFigures(position);
		Map<Figure, PositionComputationResult> bestPlies 
			= new Hashtable<Figure, PositionComputationResult>();
		int maximumTotalAdvantageN = Integer.MIN_VALUE;
		// The multi-threaded computation
		int maxThreadNumber 
			= ChessApplication.getInstance().getMaxThreadNumber();
		positionComputerSemaphore = new Semaphore(maxThreadNumber, true);
		// List of Threads to start for computation, needed to get results after
		// computation from the PositionComputer Objects
		ArrayList<PositionComputer> computers 
			= new ArrayList<PositionComputer>();
		// for each figure create and start one PositionComputer
		for (int i = 0; i < figures.size(); i++) {
			Figure figure = figures.get(i);
			PositionComputer computer = new PositionComputer(
					this, bestPlies, position, figure, quality);
			computer.start();
			computers.add(computer);
		}
		// wait for finished notification from last running PositionComputer
		synchronized (positionComputerSemaphore)
		{
			try {
				positionComputerSemaphore.wait();
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// process results from the computers to find the maximum total 
		// advantage (over all figures)
		for (PositionComputer computer : computers) {
			int maximumFigureAdvantageN = computer
				.getMaximumFigureAdvantageN();
			if (maximumFigureAdvantageN > maximumTotalAdvantageN)
				maximumTotalAdvantageN = maximumFigureAdvantageN;
		}
		// debug output (to check correct thread synchronization)
		System.out.println("I finished all ply computations.");
		// build result list from the results from each of the figures
		for (int i = 0; i < figures.size(); i++) {
			Figure figure = figures.get(i);
			PositionComputationResult figureResult = bestPlies.get(figure);
			if (figureResult == null)
				throw new RuntimeException("Figure result is null for figure "
						+figure+"@"+position.getFigureLocation(figure)
						+" (index "+i+")");
			// add non-empty figure result to total result
			// only if maximum advantage is reached
			if (!figureResult.getPositions().isEmpty()
				&& figureResult.getMaximumAdvantageN() 
						== maximumTotalAdvantageN) 
			{
				// The result list should be sorted in the following way:
				// First:  Castling
				// Second: Pawn with distance 2
				// Third:  Figures
				// Fourth: Pawn with distance 1
				// Fifth:  King 
				// The following insertion in combination with the order of the 
				// figure list returned by getFigures() will do the sorting:
				if (figure.getClass() == King.class)
				{
					for (Position kingPly: 
							bestPlies.get(figure).getPositions())
					{
						// insert castling at the beginning
						if (Math.abs(kingPly.getFigureLocation(figure).x - 
								position.getFigureLocation(figure).x) == 2)
							result.add(0, kingPly);
						// insert plies that move the king at the end
						else
							result.add(kingPly);
					}
				}
				else if (figure.getClass() == Pawn.class)
				{
					for (Position pawnPly: 
						bestPlies.get(figure).getPositions())
					{
						// insert plies of length 2 at the beginning
						if (pawnPly.getFigureLocation(figure) != null && 
 							((Math.abs(pawnPly.getFigureLocation(figure).y - 
							position.getFigureLocation(figure).y) == 2)))
							result.add(0, pawnPly);
						// insert plies of length 1 at the end
						else
							result.add(pawnPly);
					}
				}
				else 
				{
					// insert all other plies at the end, the order of the
					// figure list will do the rest
					result.addAll(
						figureResult.getPositions());
				}
			}
		}
		// resign, if no reasonable ply can be found
		if (maximumTotalAdvantageN < -60000 && result.size() > 0) {
			player.resign();
			result.clear();
		}
		// check consistency by checking that the player does not
		// cause chess (for debugging)
		for (Position p: result)
		{
			Figure king = null;
			for (Figure f : figures)
				if (f.getClass().equals(King.class))
					king = f;
			Position[] opponentPlies = player.getOpponent().computePlies(p);
			for (int i = 0; opponentPlies[i] != null; i++)
				if (king != null && opponentPlies[i].getFigureLocation(king) 
						== null)
					throw new RuntimeException("Automaton for Player "+player
						+" is considering to perform an illegal ply " +
								"\n\t -- FROM -- " +
							position+"\n\t -- TO -- "+p);
		}
		// output for debugging
		System.out.println("I (" + toString() + ") am selecting ply "
				+ result.iterator().next().hashCode()
				+ "\n\tand a maximum advantage of " + maximumTotalAdvantageN
				+ " centipawns after all plies I have thought about "
				+ "\n\t\t(which are at most " + quality + " ply levels).");
		return result;
	}
}
