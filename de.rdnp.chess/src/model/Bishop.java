package model;

import game.Player;

/** 
 * Represents a bishop figure.
 * @author Richard Pohl*/
public class Bishop extends Figure {

	public Bishop(Player player) {
		super(player);
	}
	
	@Override
	protected final ChessLocation[] computeOnePlyLocations(
			Position position) {
		return computeBishopOnePlyLocations(position);
	}

	@Override
	public final double getValue() {
		return 300d;
	}
	
	@Override
	public final String toString() {
		return super.toString()+"B";
	}

	@Override
	public final String toUnicode() {
		if (super.toString().equals("w"))
			return FigureSymbols.WHITE_BISHOP;
		else if (super.toString().equals("b"))
			return FigureSymbols.BLACK_BISHOP;
		else return "B";
	}

	@Override
	public final int getHashKey(int location) {
		return getOwner().getHashKeyProvider().getHashKey(location, this);
	}

}
