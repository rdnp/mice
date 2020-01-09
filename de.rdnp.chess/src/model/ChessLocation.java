package model;

/**
 * Represents a location on the chess board with an x- and a y-coordinate.
 * The coordinates should be in [0,...,7].
 *  @author Richard Pohl*/
public class ChessLocation {
	/** The x position of the {@link ChessLocation}. 
	 * This is potentially accessed very often and
	 * therefore declared public to save method calls. */
	public int x;
	/** The y position of the {@link ChessLocation}. 
	 * This is potentially accessed very often and
	 * therefore declared public to save method calls. */
	public int y;
	
	/** Creates a {@link ChessLocation} with the given position. */
	public ChessLocation(int x, int y) {
		this.x = x; this.y = y;
	}
	
	/** Two {@link ChessLocation} objects are equal, iff their coordinates are
	 * equal. */
	@Override
	public final boolean equals(Object obj) {
		if (obj instanceof ChessLocation)
		{
			ChessLocation anotherCheckerboardPoint = (ChessLocation)obj;
			if (anotherCheckerboardPoint.x == x 
					&& anotherCheckerboardPoint.y == y)
				return true;
		}
		return false;
	}
	
	/**
	 * @return The {@link ChessLocation} as point on the chess board in chess
	 * notation.
	 * */
	@Override
	public final String toString() {
		return Character.toString((char)(x + 'a'))+Integer.toString(y + 1);
	}
}
