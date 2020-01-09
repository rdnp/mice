package model;


import game.Automaton;
import game.Player;

import java.util.Map;


import main.ChessApplication;


/** 
 * Represents a Thread that computes positions and builds the 
 * {@link PositionTree}, starting with a particular start position and a
 * particular figure.
 * @author Richard Pohl*/
public class PositionComputer extends Thread {

	private Automaton automaton;
	private Player player;
	private Map<Figure, PositionComputationResult> bestPlies;
	private Position start;
	private Figure figure;
	/** The limit of the computation. This defines, how many successive legal 
	 * future plies are considered from the start position.*/
	private int quality;
	
	/** The value of the best move found after considering 
	 * {@link PositionComputer#quality} moves. */
	private int maximumFigureAdvantageN = Integer.MIN_VALUE;

	/** Constructs a PositionComputer. 
	 * @param automaton - the {@link Automaton} to compute the best positions 
	 * for.
	 * @param bestPlies - a map where the computation results are registered by 
	 * {@link Figure} instances.
	 * @param start - the {@link Position} to start the compuation from
	 * @param figure - the {@link Figure} to initially move in the compuation. 
	 * In the first ply, a {@link PositionComputer} will only consider
	 * movements of figure.
	 * On all successive plies, the movements of all figures are considered.
	 * @param quality - see {@link PositionComputer#quality}.
	 * */
	public PositionComputer(Automaton automaton, 
			Map<Figure, PositionComputationResult> bestPlies, Position start, 
			Figure figure, int quality) 
	{
		this.automaton = automaton;
		player = automaton.getPlayer();
		this.bestPlies = bestPlies;
		this.start = start;
		this.figure = figure;
		this.quality = quality;
	} 
	
	/** 
	 * This does a search for each possible initial ply do a to find 
	 * the best outcome and some other parameters. The results are registered by 
	 * {@link Figure} keys in a Map.
	 * See {@link PositionComputer#PositionComputer(Automaton, Map, Position, 
	 * Figure, int)} and {@link PositionComputer} for further details.*/
	@Override
	public void run() {
		// acquire semaphore
		automaton.getPositionComputerSemaphore().acquireUninterruptibly();
		// initialize position vector for figure
		bestPlies.put(figure, new PositionComputationResult());
		// build position tree for figure
		PositionTree positionTree 
			= new PositionTree(player, start, figure, quality);
		System.out.println("Selecting from "
			+positionTree.getTargets(start).size()
			+" initial plies considering "
			+positionTree.size()+" total positions.");
		// get maximum advantage from initial plies
		for (Position initialPly: positionTree.getTargets(start))
		{
			System.out.println("Found ply "+initialPly.hashCode()
					+" with evaluation "+positionTree.getEvaluation(
							initialPly, player));
			if (positionTree.getEvaluation(initialPly, player) > 
				maximumFigureAdvantageN)
			{	
				maximumFigureAdvantageN = positionTree.getEvaluation(
					initialPly, player);
			}
		}
		// add maximum advantage to result structure
		bestPlies.get(figure).setMaximumAdvantageN(maximumFigureAdvantageN);
		// add all plies that have maximum advantage to result list
		for (Position initialPly: positionTree.getTargets(start))
		{
			if (positionTree.getEvaluation(initialPly, player) 
				== maximumFigureAdvantageN)
			bestPlies.get(figure).getPositions().add(initialPly);
		}
		// release semaphore
		automaton.getPositionComputerSemaphore().release();
		// send notification if all figures are computed and there are no 
		// more working threads (checks Semaphore's permits).
		boolean allFiguresComputed = true;
		for (Figure figure: player.getFigures(start))
			if (bestPlies.get(figure) == null)
				allFiguresComputed = false;
		synchronized (automaton.getPositionComputerSemaphore()) {
			if (automaton.getPositionComputerSemaphore().availablePermits() 
					== ChessApplication.getInstance().getMaxThreadNumber() && 
					allFiguresComputed)
					automaton.getPositionComputerSemaphore().notify();
		}
	}
	
	/** @return The value of the best move found after considering 
	 * {@link PositionComputer#quality} moves. */
	public int getMaximumFigureAdvantageN() {
		return maximumFigureAdvantageN;
	}
	
}
