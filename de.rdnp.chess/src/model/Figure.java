package model;

import game.Player;

/** 
 * Abstract class that represents a chess figure. 
 * @author Richard Pohl*/
public abstract class Figure {
	
	/** The {@link Player} of the {@link Figure}. */
	protected Player owner;
	
	
	/** Constructs a {@link Figure}.
	 * @param owner - the {@link Player} that owns the {@link Figure}.  
	 * */
	public Figure(Player player) {
		this.owner = player;
	}
	
	
	public Player getOwner() {
		return owner;
	}
	
	/** @return The value of the figure (in centipawns). */
	public abstract double getValue();
	
	/** @return true iff the figure collides with a figure of the own player. */
	protected final boolean isCollidingWithOwn(Position position, int x, int y)
	{
		return (position.getFigureAt(new ChessLocation(x,y)) != null 
				&& position.getFigureAt(new ChessLocation(x,y))
				.getOwner() == this.getOwner());
	}
	
	@Override
	public String toString() {
		if (Player.WHITE.equals(owner)) return "w";
		else if (Player.BLACK.equals(owner)) return "b";
		else return "";
	}
	
	/** Computes all {@link ChessLocation} instances that represent locations 
	 * that are reachable with one legal ply assuming this is a 
	 * {@link Bishop} figure. 
	 * @return An array with the resulting ChessLocation instances.
	 * The array is of fixed size. 
	 * The last elements of the result array are null. */
	protected final ChessLocation[]	
	                             computeBishopOnePlyLocations(Position position)
	{
		ChessLocation[] result = new ChessLocation[15];
		int resultIndex = 0;
		// the assumed position of the Bishop-compatible figure
		int x = position.getFigureLocation(this).x - 1, 
			y = position.getFigureLocation(this).y - 1;
		// flag to indicate that we found a ply where we take another figure.
		boolean take = false;
		// compute diagonal down / left
		while (x >= 0 && y >= 0 && !isCollidingWithOwn(position, x, y) 
				&& !take)
		{
			ChessLocation plyLocation = new ChessLocation(x, y);
			result[resultIndex++] = plyLocation; x--; y--;
			if (position.getFigureAt(plyLocation) != null 
					&& position.getFigureAt(plyLocation).getOwner() 
							!= getOwner())
				take = true;
		}
		// compute diagonal down / right
		x = position.getFigureLocation(this).x + 1;
		y = position.getFigureLocation(this).y - 1; 
		take = false;
		while (x < 8 && y >= 0 && !isCollidingWithOwn(position, x, y)
				&& !take)
		{
			ChessLocation plyLocation = new ChessLocation(x, y);
			result[resultIndex++] = plyLocation; x++; y--;
			if (position.getFigureAt(plyLocation) != null 
					&& position.getFigureAt(plyLocation).getOwner() 
						!= getOwner())
				take = true;
		}
		// compute diagonal up / left
		x = position.getFigureLocation(this).x - 1; 
		y = position.getFigureLocation(this).y + 1; 
		take = false;
		while (x >= 0 && y < 8 && !isCollidingWithOwn(position, x, y)
				&& !take)
		{
			ChessLocation plyLocation = new ChessLocation(x, y);
			result[resultIndex++] = plyLocation; x--; y++;
			if (position.getFigureAt(plyLocation) != null 
					&& position.getFigureAt(plyLocation).getOwner() 
						!= getOwner())
				take = true;
		}
		// compute diagonal up / right
		x = position.getFigureLocation(this).x + 1; 
		y = position.getFigureLocation(this).y + 1; 
		take = false;
		while (x < 8 && y < 8 && !isCollidingWithOwn(position, x, y)
				&& !take)
		{	
			ChessLocation plyLocation = new ChessLocation(x, y);
			result[resultIndex++] = plyLocation; x++; y++;
			if (position.getFigureAt(plyLocation) != null 
					&& position.getFigureAt(plyLocation).getOwner() 
						!= getOwner())
				take = true;
		}
		return result;
	}
	
	/** Computes all {@link ChessLocation} instances that represent locations 
	 * that are reachable with one legal ply assuming this is a 
	 * {@link Rook} figure. 
	 * @return An array with the resulting ChessLocation instances.
	 * The array is of fixed size. 
	 * The last elements of the result array are null. */
	protected final ChessLocation[] computeRookOnePlyLocations(
			Position position)
	{
		ChessLocation[] result = new ChessLocation[15];
		int resultIndex = 0;
		// the assumed position of the Rook-compatible figure
		int x = position.getFigureLocation(this).x - 1, 
			y = position.getFigureLocation(this).y;
		// flag to indicate that we found a move where we take another figure.
		boolean take = false;
		// compute line to the left
		while (x >= 0 && !isCollidingWithOwn(position, x, y) && !take)
		{
			ChessLocation plyLocation = new ChessLocation(x, y);
			result[resultIndex++] = plyLocation; x--;
			if (position.getFigureAt(plyLocation) != null 
					&& position.getFigureAt(plyLocation).getOwner() 
							!= getOwner())
				take = true;
		}
		// compute line to the right
		x = position.getFigureLocation(this).x + 1;
		y = position.getFigureLocation(this).y;
		take = false;
		while (x < 8 && !isCollidingWithOwn(position, x, y) && !take)
		{
			ChessLocation plyLocation = new ChessLocation(x, y);
			result[resultIndex++] = plyLocation; x++;
			if (position.getFigureAt(plyLocation) != null 
					&& position.getFigureAt(plyLocation).getOwner() 
						!= getOwner())
				take = true;
		}
		// compute line down
		x = position.getFigureLocation(this).x; 
		y = position.getFigureLocation(this).y - 1;
		take = false;
		while (y >= 0 && !isCollidingWithOwn(position, x, y) && !take)
		{	
			ChessLocation plyLocation = new ChessLocation(x, y);
			result[resultIndex++] = plyLocation; y--;
			if (position.getFigureAt(plyLocation) != null 
					&& position.getFigureAt(plyLocation).getOwner() 
						!= getOwner())
				take = true;
		}
		// compute line up
		x = position.getFigureLocation(this).x; 
		y = position.getFigureLocation(this).y + 1;
		take = false;
		while (y < 8 && !isCollidingWithOwn(position, x, y) && !take)
		{
			ChessLocation plyLocation = new ChessLocation(x, y);
			result[resultIndex++] = plyLocation; y++;
			if (position.getFigureAt(plyLocation) != null 
					&& position.getFigureAt(plyLocation).getOwner() 
						!= getOwner())
				take = true;
		}
		return result;
	}
	
	/** Computes the positions of this figure after one legal ply, starting 
	 *  from position. 
	 *  The array is of fixed size. 
	 *  The last elements of the result array are null.*/
	protected abstract ChessLocation [] computeOnePlyLocations(
			Position position);
	
	/** Computes all positions of the figure that are reachable with one legal
	 * ply moving this figure and starting with the given position.
	 * @return An array with all positions reachable from position by one legal
	 * ply that moves this figure. 
	 * The array is of fixed size. 
	 * The last elements of the array are null.*/
	public Position[] computeOnePly(Position currentPosition)
	{
		Position[] result;
		ChessLocation [] plyLocations = computeOnePlyLocations(currentPosition);
		result = new Position[plyLocations.length];
		for (int i = 0; plyLocations[i] != null; 
				i++)
			result[i] = new Position(currentPosition, this, plyLocations[i]);
		return result;
	}
	
	/** @return The unicode symbol of a {@link Figure}. */
	public abstract String toUnicode();
	
	/** 
	 * @param location - the index of the field on which the {@link Figure}
	 * is located, used for retrieving the hash key.
	 * @return One of 64 ids depending on the position, the {@link Figure} owner
	 * and the figure type (used for hashing, see 
	 * {@link Position#computeZobristHash}). */
	public abstract int getHashKey(int location);
	
}
