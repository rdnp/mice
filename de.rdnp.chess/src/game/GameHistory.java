package game;

import java.util.ArrayList;
import java.util.List;

import model.Position;

/**
 * Records the history of a {@link Game}, which is a sequence of {@link Ply}
 * instances.
 * 
 * @author Richard Pohl
 * */

public class GameHistory {

	private List<Ply> plies = new ArrayList<Ply>();
	
	/**
	 * Constructs a {@link GameHistory}.
	 * */
	public GameHistory() {
	}
	
	/**
	 * Adds a {@link Ply} as the most recent Ply in the {@link GameHistory}.
	 * @param ply - the {@link Ply} to add.
	 * */
	public void addPly(Ply ply)
	{
		plies.add(ply);
	}
	
	/**
	 * @return The number of plies played since this {@link GameHistory} was
	 * constructed.
	 * */
	public int getSize()
	{
		return plies.size();
	}
	
	/**
	 * @param index - an index of a {@link Ply} in the {@link GameHistory}.
	 * @return The position before the {@link Ply} with the given index was
	 * performed.
	 * */
	public Position getPositionBefore(int index) {
		return plies.get(index).getLast();
	}
	
	/**
	 * @param i - the index of the {@link Ply} to return.
	 * @return The i-th {@link Ply} in the {@link GameHistory} as a string.
	 * */
	public String plyAsString(int i)
	{
		return plies.get(i).toString();
	}

	
}
