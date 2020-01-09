package model;

import game.Player;

/** 
 * Represents a queen figure.
 * @author Richard Pohl*/
public class Queen extends Figure {
	
	public Queen(Player player) {
		super(player);
	}
	
	@Override
	protected final ChessLocation[] computeOnePlyLocations(Position position) 
	{
		// the queen's plies are computed by combining the rook plies and the
		// bishop plies.
		ChessLocation[] result = new ChessLocation[29];
		int resultIndex = 0;
		ChessLocation [] bishopOnePlyLocations 
			= computeBishopOnePlyLocations(position);
		for (int i = 0; bishopOnePlyLocations[i] != null; i++)
			result[resultIndex++] = bishopOnePlyLocations[i];
		ChessLocation [] rookOnePlyLocations 
			= computeRookOnePlyLocations(position);
		for (int i = 0; rookOnePlyLocations[i] != null; i++)
			result[resultIndex++] = rookOnePlyLocations[i];
		return result;
	}

	@Override
	public final double getValue() {
		return 950d;
	}
	
	@Override
	public final String toString() {
		return super.toString()+"Q";
	}

	@Override
	public final String toUnicode() {
		if (super.toString().equals("w"))
			return FigureSymbols.WHITE_QUEEN;
		else if (super.toString().equals("b"))
			return FigureSymbols.BLACK_QUEEN;
		else return "Q";
	}

	@Override
	public final int getHashKey(int location) {
		return getOwner().getHashKeyProvider().getHashKey(location, this);
	}
}
