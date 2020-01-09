package model;

import game.Player;

/** @author Richard Pohl*/
public class Rook extends Figure {

	public Rook(Player player) {
		super(player);
	}

	@Override
	public final ChessLocation[] computeOnePlyLocations(
			Position position) {
		return computeRookOnePlyLocations(position);
	}
	
	@Override
	public final double getValue() {
		return 450d;
	}
	
	@Override
	public final String toString() {
		return super.toString()+"R";
	}

	@Override
	public final String toUnicode() {
		if (super.toString().equals("w"))
			return FigureSymbols.WHITE_ROOK;
		else if (super.toString().equals("b"))
			return FigureSymbols.BLACK_ROOK;
		else return "R";
	}

	@Override
	public final int getHashKey(int location) {
		return getOwner().getHashKeyProvider().getHashKey(location, this);
	}
}
