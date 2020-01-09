package model;


import game.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;



/** 
 * This is an n-ary tree implementation to store chess positions in an ordered
 * way. Supports operations to determine a best ply for a player and to get
 * information about stored positions.
 * @author Richard Pohl*/
public class PositionTree {

	/** The root position of the position tree. */
	private Position root;
	private int depth;	
	
	/** The nodes of the position tree. */
	private Map<Position, PositionNode> nodes;
	
	/**
	 * Constructs the position tree, assuming the activePlayer is active in
	 * the given root position. 
	 * @param activePlayer - the player at move.
	 * @param root - the current position in the game.
	 * @param figure - the figure to compute the PositionTree for.
	 * @param depth - a measure for the depth, i.e. quality of the 
	 * PositionTree. Higher values require more time and memory, but enable
	 * a more precise estimation of the advantages.
	 * */
	public PositionTree(Player activePlayer, Position root, Figure figure, 
			int depth) 
	{
		nodes = new HashMap<Position, PositionNode>();
		this.root = root;
		this.depth = depth;
		initialize(activePlayer, root, figure);
		evaluate();
	}
	
	private PositionNode getPositionNode(Position position)
	{
		return nodes.get(position);
	}

	/** 
	 * @param position - the {@link Position} for which the {@link Player} at 
	 * move should be determined.
	 * @return The {@link Player} at move in position.
	 *  */
	public Player getPlayer(Position position)
	{
		PositionNode positionNode = getPositionNode(position);
		return positionNode != null? positionNode.getPlayer(): null;
	}
	
	/** Sets the evaluation of a {@link Position}, 
	 * which is held as a pre-computed
	 * value in a {@link PositionNode} for performance reasons.
	 * @param position - the {@link Position} to set the evaluation for.
	 * @param evaluation - the advantage of the {@link Player} at move in the
	 * {@link Position} of this {@link Position}. Can be obtained by calling
	 * {@link Position#evaluate(Player)}.
	 * */
	public void setEvaluation(Position position, int evaluation)
	{
		PositionNode positionNode = getPositionNode(position);
		positionNode.setEvaluation(evaluation);
	}

	/**
	 * @param player - the {@link Player} for which the {@link Position} 
	 * shall be evaluated.
	 * @param position - the {@link Position} to get the evaluation for.
	 * @return The evaluation of the position for player. This is a pre-computed
	 * value that is stored in a {@link PositionNode} by a 
	 * {@link PositionComputer}. 
	 * */
	public int getEvaluation(Position position, Player player)
	{
		PositionNode positionNode = getPositionNode(position);
		return positionNode != null?
			positionNode.getEvaluation(player): Integer.MIN_VALUE;
	}
	
	/** 
	 * @param position - the position to get the reachability for.
	 * @return The number of legal plies in which the {@link Position} 
	 * can be reached starting from the root Position 
	 * of the {@link PositionTree}.
	 * */
	public int getReachability(Position position)
	{
		PositionNode positionNode = getPositionNode(position);
		return positionNode != null? positionNode.getReachability(): -1;
	}
	
	/** 
	 * @param position - the position to get the sources for.
	 * @return A {@link Position} instance from which the 
	 * {@link Position} can be reached with a legal ply. */
	public Position getSource(Position position)
	{
		PositionNode positionNode = getPositionNode(position);
		return positionNode != null? positionNode.getSource(): null;
	}
	
	/**
	 * @param position - the {@link Position} for which the source shall be 
	 * set.
	 * @param source - a {@link Position} instance from which the 
	 * position can be reached with a 
	 * legal ply that should be captured in this PositionTree.
	 * */
	public void setSource(Position position, Position source)
	{
		getPositionNode(position).setSource(source);
	}

	/** 
	 * @param position - the position to get the targets for.
	 * @return A {@link List} of {@link Position} instances that can be reached 
	 * with a legal ply in this {@link PositionTree}.
	 */
	public List<Position> getTargets(Position position)
	{
		PositionNode positionNode = getPositionNode(position);
		return positionNode != null? positionNode.getTargets(): null;
	}

	/**
	 * Adds a node to this {@link PositionTree}, if there is not already a node
	 * for the {@link Position} given or if the node in the PositionTree has
	 * a reachability that is greater than the new node to be added.
	 * @param position - the {@link Position} to add a {@link PositionNode} for.
	 * @param player - the {@link Player} at move in the {@link Position}.
	 * @param reachability - the distance of the new {@link PositionNode} to the
	 * root of this PositionTree.
	 * @return true, if a {@link PositionNode} was added, false otherwise.
	 * */
	public boolean addNode(Position position, Player player, int reachability)
	{
		// do not allow cycles, since this is a tree:
		if (nodes.get(position) == null 
//				|| (nodes.get(position).getReachability() > reachability)
				)
		{
			PositionNode positionNode = new PositionNode(position, player, 
				reachability);
			nodes.put(position, positionNode);
			return true;
		}
		return false;
	}

	/**
	 * @param position - the {@link Position} for which the target shall be 
	 * added.
	 * @param target - a {@link Position} instance that can be reached 
	 * with a legal ply from the position
	 * that should be captured in this PositionTree.
	 * */
	public void addTarget(Position position, Position target)
	{
		getPositionNode(position).addTarget(target);
	}
	
	public int size()
	{
		return nodes.size();
	}
	
	/** Builds the tree of the best positions for figure. 
	 * @param activePlayer - the {@link Player} at move in the root or start 
	 * {@link Position}.
	 * @param root - the root or start position of the {@link PositionTree}.
	 * @param figure - the {@link Figure} to compute the {@link PositionTree} 
	 * for. This means that in the first ply only movements of figure are 
	 * considered, in all subsequent plies, all figures are moved. 
	 * */	
	private void initialize(Player activePlayer, Position root, Figure figure)
	{
		// breadth-first-search through possible plies
		Queue<Position> q = new LinkedList<Position>(); 
		// level counter to track the distance to root, 
		int level = 0; 
		// player at move
		Player movePlayer = activePlayer;
		// insert root position into position tree
		addNode(root, movePlayer, level);
		q.add(root); 
		while (!q.isEmpty()) {
			Position currentPosition = q.poll();
			// get player and level of the ply
			level = getReachability(currentPosition);
			if (level == 1)
				System.out.println("Thinking about what would happen, if I did "
						+ "this (ply #"+currentPosition.hashCode()
						+"):\n"+currentPosition.toString());
			movePlayer = getPlayer(currentPosition);
			// the positions for the next iteration of the dfs
			Position[] nextPositions;
			// on the first level compute plies for given figure
			if (level == 0)
			{
				nextPositions = figure.computeOnePly(currentPosition);
				// figure computation doesn't consider side effects on other figures,
				// such as chess .. so it needs to be checked if the move is at all
				// legal.
				for (int i = 0; nextPositions[i] != null; i++) {
					if (movePlayer.chess(nextPositions[i])) 
						// this move is illegal
						nextPositions[i] = null;
				}
				// now filter out nulled out moves
				int newIndex = 0;
				for (int i = 0; i < nextPositions.length; i++) {
					if (nextPositions[i] != null) {
						nextPositions[newIndex++] = nextPositions[i];
					}
				}
				if (nextPositions[0] == null)
					System.out.println("No ply found in "+currentPosition
						+" for "+figure.getClass().getName()+" figure" 
						+" at (x="+currentPosition.getFigureLocation(figure).x
						+", y="+currentPosition.getFigureLocation(figure).y
						+")");
				
			}
			// on all other levels compute all possible plies 
			else 
			{
				nextPositions = movePlayer.computePlies(currentPosition, false);
			}
			// if we did not cross the thinkThreshold, continue with search
			//   fill stack, add an inner node & switch player
			if (level < depth) 
			{
				for (int i = 0; nextPositions[i] != null; i++)
				{
					Position target = nextPositions[i];
					boolean proceedWithTarget =
						addNode(target, movePlayer.getOpponent(), 
							level + 1);
					// only proceed if not already processed (avoid running into
					// cycles)
					if (proceedWithTarget)
					{
						addTarget(currentPosition, target);
						setSource(target, currentPosition);
						q.add(target);
					}
				}
			}
			// else we reached the thinkThreshold, which is the point we stop
			// computation. The plies at this point in the search need to be
			// evaluated
			else
				getPositionNode(currentPosition).evaluate();
		} 
		System.out.println("I built the position tree.");
	}
	
	/** Propagates the evaluations of the target positions to their sources.*/
	private void evaluate() {
		// post-order depth-first-search to find targets:
		// s used for an initial pre-order dfs that builds p
		// p is used to save the (reverse) order of the dfs to process the 
		// visited elements after the search in post-order
		Stack<Position> s = new Stack<Position>(), 
					    p = new Stack<Position>();
		// build p with a normal pre-order dfs
		s.push(root); 
		while (!s.isEmpty())
		{
			Position position = s.pop();
			p.push(position);
			// dfs-add
			List<Position> targets = getTargets(position);
			for (int i = 0; i < targets.size(); i++)
				s.push(targets.get(i));
		}
		// process elements in p 
		// (the processing order is the order of a post-order dfs)
		while (!p.isEmpty())
		{
			Position position = p.pop();
			// propagate position's evaluation to its sources
			Position source = getSource(position);
			if (source != null)
			{
				// player at move in source
				Player sourcePlayer = getPlayer(source);
				// values of source and position for this player
				int positionValue = getEvaluation(position, 
					sourcePlayer);
				int sourceValue = getEvaluation(source, 
					sourcePlayer);
				// this player can move at source => propagate best ply
				// of position level to source level
				int bestEvaluation = Math.max(sourceValue, positionValue);
				setEvaluation(source, bestEvaluation);

				// debug output:
//				Position source1 = source;
//				while (getReachability(source1) > 1)
//					source1 = getSources(source1).get(0);
//				if (source1.hashCode() == -1856444744 && (
//						getReachability(position) < 4 
//						|| position.getFigureLocation(
//								root.getFigureAt(new ChessLocation(1, 5))) 
//								== null)
//								)
//					System.out.println("Propagating an evaluation of "
//						+getEvaluation(position, sourcePlayer)+" from level "+
//						getReachability(position)+"("
//						+position+", "
//						+getTargets(position).size()
//						+" targets, hashCode="+position.hashCode()+") to level "
//						+getReachability(source)
//						+"("+source.hashCode()+") resulting in "+
//						getEvaluation(source, sourcePlayer)+"("
//						+getPlayer(source)+" at move)");
			}
		}	
		// output message for debugging:
		System.out.println("I evaluated the position tree.");
	}
}
