package model;


import game.Player;

import java.util.List;
import java.util.ArrayList;



/** 
 * Represents a node in a {@link PositionTree}. Holds a {@link Position} and
 * some of its associated game states, like the {@link Player} at move in it,
 * its successors and predecessors in the tree.
 * @author Richard Pohl*/
public class PositionNode {
	private Position element;
	/** the player at move. */
	private Player player;
	/** how many plies are necessary to reach the position. */
	private int reachability;
	/** an evaluation of the position. */
	private int evaluation;
	/** sources of the position. */
	private Position source;
	/** targets of the position. */
	private List<Position> targets;	
	
	/**
	 * Constructs a {@link PositionNode}.
	 * @param element - the {@link Position} the node should encapsulate.
	 * @param player - the {@link Player} at move in the position.
	 * @param reachability - a reachability value to determine how many
	 * plies are necessary to reach element from the root position of the 
	 * {@link PositionTree}.
	 * */
	public PositionNode(Position element, Player player, int reachability) {
		this.element = element;
		this.player = player;
		this.reachability = reachability;
		// assume ply is very bad until it is evaluated to prevent the player
		// from selecting wrong plies
		evaluation = Integer.MIN_VALUE; 
//		this.sources = new ArrayList<Position>();
		this.targets = new ArrayList<Position>();
	}
	
	/** @return The {@link Position} of the {@link PositionNode}. */
	public Position getElement() {
		return element;
	}

	/** @return The {@link Player} at move of the {@link PositionNode}. */
	public Player getPlayer() {
		return player;
	}
	
	/** Sets the evaluation of the {@link Position} stored in this node, 
	 * which is held as a pre-computed value for performance reasons.
	 * @param evaluation - the advantage of the {@link Player} at move in the
	 * {@link Position} of this {@link PositionNode}. Can be obtained by calling
	 * {@link Position#evaluate(Player)}.
	 * */
	public void setEvaluation(int evaluation) {
		this.evaluation = evaluation;
	}
	
	/**
	 * @param player - the {@link Player} for which the {@link Position} of this 
	 * {@link PositionNode} shall be evaluated.
	 * @return The evaluation of the position for player. This is a pre-computed
	 * value that is stored in a PositionNode by a {@link PositionComputer}. 
	 * */
	public int getEvaluation(Player player) {
		if (this.player.equals(player))
			return evaluation;
		else
			return evaluation * -1;
	}
	
	/** @return The number of legal plies in which the {@link Position} of
	 * this {@link PositionNode} can be reached starting from the root Position 
	 * of a {@link PositionTree} */
	public int getReachability() {
		return reachability;
	}
	
	/** @return A {@link Position} instance from which the 
	 * {@link Position} in this {@link PositionNode} can be reached with a 
	 * legal ply. */
	public Position getSource() {
		return source;
	}
	
	/**
	 * @param source - a {@link Position} instance from which the 
	 * {@link Position} in this {@link PositionNode} can be reached with a 
	 * legal ply that should be captured in this PositionNode.
	 * */
	public void setSource(Position source) {
		this.source = source;
	}
	
	/** @return A {@link List} of {@link Position} instances that can be reached 
	 * with a legal ply from the {@link Position} in this {@link PositionNode}.
	 */
	public List<Position> getTargets() {
		return targets;
	}
	
	
	/**
	 * @param target - a {@link Position} instance that can be reached 
	 * with a legal ply from the {@link Position} in this {@link PositionNode}
	 * that should be captured in this PositionNode.
	 * */
	public void addTarget(Position target) {
		targets.add(target);
	}
	
	/**
	 * Refreshes the pre-computed evaluation value stored in this 
	 * {@link PositionNode} by calling {@link Position#evaluate(Player)}
	 * on {@link Position} stored in the PositionNode with the player at move
	 * sored in the PositionNode.
	 * */
	public void evaluate() {
		this.evaluation = element.evaluate(player);
	}
}