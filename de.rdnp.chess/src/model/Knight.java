package model;

import game.Player;

/** 
 * Represents a knight figure.
 * @author Richard Pohl*/
public class Knight extends Figure {

	public Knight(Player player) {
		super(player);
	}
	
	@Override
	protected final ChessLocation[] computeOnePlyLocations(
			Position position) {
		ChessLocation[] result = new ChessLocation[9];
		int resultIndex = 0;
		// four plies of the knight (x +/- 2, y +/- 1)
		int x = position.getFigureLocation(this).x - 2, 
			y = position.getFigureLocation(this).y - 1;  
		if (x >= 0 && y >= 0 && !isCollidingWithOwn(position, x, y))
			result[resultIndex++] = new ChessLocation(x, y);
		x = position.getFigureLocation(this).x - 2; 
		y = position.getFigureLocation(this).y + 1;
		if (x >= 0 && y < 8 && !isCollidingWithOwn(position, x, y)) 
			result[resultIndex++] = new ChessLocation(x, y);
		x = position.getFigureLocation(this).x + 2; 
		y = position.getFigureLocation(this).y - 1;
		if (x < 8 && y >= 0 && !isCollidingWithOwn(position, x, y)) 
			result[resultIndex++] = new ChessLocation(x, y);
		x = position.getFigureLocation(this).x + 2; 
		y = position.getFigureLocation(this).y + 1;
		if (x < 8 && y < 8 && !isCollidingWithOwn(position, x, y)) 
			result[resultIndex++] = new ChessLocation(x, y);
		// four plies of the knight (x +/- 1, y +/- 2)
		x = position.getFigureLocation(this).x - 1; 
		y = position.getFigureLocation(this).y - 2;  
		if (x >= 0 && y >= 0 && !isCollidingWithOwn(position, x, y)) 
			result[resultIndex++] = new ChessLocation(x, y);
		x = position.getFigureLocation(this).x - 1; 
		y = position.getFigureLocation(this).y + 2;
		if (x >= 0 && y < 8 && !isCollidingWithOwn(position, x, y)) 
			result[resultIndex++] = new ChessLocation(x, y);
		x = position.getFigureLocation(this).x + 1; 
		y = position.getFigureLocation(this).y - 2;
		if (x < 8 && y >= 0 && !isCollidingWithOwn(position, x, y)) 
			result[resultIndex++] = new ChessLocation(x, y);
		x = position.getFigureLocation(this).x + 1; 
		y = position.getFigureLocation(this).y + 2;
		if (x < 8 && y < 8 && !isCollidingWithOwn(position, x, y)) 
			result[resultIndex++] = new ChessLocation(x, y);
		return result;
	}
	
	@Override
	public final double getValue() {
		return 320d;
	}
	
	@Override
	public final String toString() {
		return super.toString()+"N";
	}

	@Override
	public final String toUnicode() {
		if (super.toString().equals("w"))
			return FigureSymbols.WHITE_KNIGHT;
		else if (super.toString().equals("b"))
			return FigureSymbols.BLACK_KNIGHT;
		else return "N";
	}

	@Override
	public final int getHashKey(int location) {
		return getOwner().getHashKeyProvider().getHashKey(location, this);
	}
}
