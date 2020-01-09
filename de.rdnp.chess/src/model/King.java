package model;

import game.Player;

/** 
 * Represents a king figure.
 * @author Richard Pohl*/
public class King extends Figure {
	
	public King(Player player) {
		super(player);
	}
	
	@Override
	public final double getValue() {
		return 100000d;
	}
	
	@Override
	public final String toString() {
		return super.toString()+"K";
	}
	
	@Override
	public final String toUnicode() {
		if (super.toString().equals("w"))
			return FigureSymbols.WHITE_KING;
		else if (super.toString().equals("b"))
			return FigureSymbols.BLACK_KING;
		else return "K";
	}
	
	/** See {@link Figure#computeOnePlyLocations(Position)} for details.
	 * This computation does not include castling plies, since they are added 
	 * later during the call of {@link King#computeOnePly(Position)}.*/
	@Override
	protected final ChessLocation[] computeOnePlyLocations(
			Position position) {
		ChessLocation[] result = new ChessLocation[9];
		int resultIndex = 0;
		// four diagonal plies
		int x = position.getFigureLocation(this).x - 1, 
			y = position.getFigureLocation(this).y - 1;  
		if (x >= 0 && y >= 0 && !isCollidingWithOwn(position, x, y)) 
			result[resultIndex++] = new ChessLocation(x, y);
		x = position.getFigureLocation(this).x - 1; 
		y = position.getFigureLocation(this).y + 1;
		if (x >= 0 && y < 8 && !isCollidingWithOwn(position, x, y)) 
			result[resultIndex++] = new ChessLocation(x, y);
		x = position.getFigureLocation(this).x + 1; 
		y = position.getFigureLocation(this).y - 1;
		if (x < 8 && y >= 0 && !isCollidingWithOwn(position, x, y)) 
			result[resultIndex++] = new ChessLocation(x, y);
		x = position.getFigureLocation(this).x + 1; 
		y = position.getFigureLocation(this).y + 1;
		if (x < 8 && y < 8 && !isCollidingWithOwn(position, x, y)) 
			result[resultIndex++] = new ChessLocation(x, y);
		// four plies up/down/left/right
		// up
		x = position.getFigureLocation(this).x; 
		y = position.getFigureLocation(this).y + 1;
		if (y < 8 && !isCollidingWithOwn(position, x, y)) 
			result[resultIndex++] = new ChessLocation(x, y);
		// down
		x = position.getFigureLocation(this).x; 
		y = position.getFigureLocation(this).y - 1;
		if (y >= 0 && !isCollidingWithOwn(position, x, y)) 
			result[resultIndex++] = new ChessLocation(x, y);
		// left
		x = position.getFigureLocation(this).x + 1; 
		y = position.getFigureLocation(this).y;
		if (x < 8 && !isCollidingWithOwn(position, x, y)) 
			result[resultIndex++] = new ChessLocation(x, y);
		// right
		x = position.getFigureLocation(this).x - 1;
		y = position.getFigureLocation(this).y;
		if (x >= 0 && !isCollidingWithOwn(position, x, y)) 
			result[resultIndex++] = new ChessLocation(x, y);
		// Castle plies need to be treated as total positions because they
		// involve two figures. Therefore, they are treated in 
		// computePositions() 
		return result;
	}

	/** See {@link Figure#computeOnePly(Position)} for details.
	 * Needs to add castling plies for the king. This cannot be done by
	 * simply returning new positions to the overridden method because
	 * castle moves require to move the {@link Rook}.*/
	@Override
	public Position[] computeOnePly(Position position) {
		Position[] result;
		ChessLocation [] plyLocations = computeOnePlyLocations(position);
		result = new Position[plyLocations.length+2];
		int resultIndex;
		for (resultIndex = 0; plyLocations[resultIndex] != null; resultIndex++)
			result[resultIndex] 
			       = new Position(position, this, plyLocations[resultIndex]);
		// add castling moves
		int castleKingSideCode = 0x0, castleQueenSideCode = 0x0;
		// add the white castling moves for white player
		if (getOwner() == Player.WHITE)
		{
			 castleKingSideCode = Position.WHITE_CASTLE_KING_SIDE;
			 castleQueenSideCode = Position.WHITE_CASTLE_QUEEN_SIDE;
		}
		// add the black castling plies for black player
		if (getOwner() == Player.BLACK)
		{
			 castleKingSideCode = Position.BLACK_CASTLE_KING_SIDE;
			 castleQueenSideCode = Position.BLACK_CASTLE_QUEEN_SIDE;
		}
		Position castleKingSide = null, castleQueenSide = null;
		if (!position.hasCastled(castleKingSideCode))
		{
			castleKingSide = computeCastlePly(position, castleKingSideCode);
			if (castleKingSide != null)
				result[resultIndex++] = castleKingSide;
		}
		if (!position.hasCastled(castleQueenSideCode))
		{
			castleQueenSide = computeCastlePly(position, castleQueenSideCode);
			if (castleQueenSide != null)
				result[resultIndex++] = castleQueenSide;
		}
		return result;
	};

//	/** @return true, if at least one of the locations in location can be 
//	 * reached by a figure of the opponent of the player that owns this King.*/
//	private boolean chessAt(Position position, ChessLocation [] locations)
//	{
//		// needs to eliminate castling rights from position before computing
//		// chess, because the chess computation will call King.computeOnePly()
//		// which would call chessAt, if castling was allowed in the position
//		// passed to the calculation of opponent plies. This would cause a
//		// call stack overflow through an infinite recursion.
//		Position noOpponentCastlePosition = new Position(position, this, 
//				position.getFigureLocation(this));
//		noOpponentCastlePosition.setCastlingHistory(0x00);
//		Position[] opponentPlies 
//			= getOwner().getOpponent().computePlies(noOpponentCastlePosition);
//		for (int im=0; im < opponentPlies.length 
//											&& opponentPlies[im] != null; im++)
//		{
//			Position opponentMove = opponentPlies[im];
//			for (int il = 0; il < locations.length
//								&& locations[il] != null; il++)
//				if (opponentMove.getFigureAt(locations[il]) != null 
//						&& opponentMove.getFigureAt(locations[il]).getOwner() 
//								!= getOwner())
//					return true;
//		}
//		return false;
//	}
	
	/** Computes the castle ply for this king defined by position and 
	 * castleType. castleType has to be one of the castleType constants defined
	 * in {@link Position}
	 * Adds all found castle plies that are legal with regard to restrictions
	 * on chess and threatened fields to the Collection result 
	 * (first parameter).*/
	private Position computeCastlePly(Position position, int castleType)
	{
		// the rook to castle with
		Figure rook = null; 
		// location of king before castle ply
		ChessLocation kingLocation = position.getFigureLocation(this);
		// locations of king and rook after castle ply
		ChessLocation castleKingLocation = null;
		ChessLocation castleRookLocation = null;
		// location right of queen's rook, needed for castle on queen's side
		ChessLocation rightOfQueensRook = null;
		// determine locations after castle move depending on castle type
		if (castleType == Position.WHITE_CASTLE_KING_SIDE 
				|| castleType == Position.BLACK_CASTLE_KING_SIDE)
		{
			rook = position.getFigureAt(new ChessLocation(7, 
					position.getFigureLocation(this).y));
			castleKingLocation = new ChessLocation(
					position.getFigureLocation(this).x+2, 
					position.getFigureLocation(this).y);
			castleRookLocation = new ChessLocation(
					position.getFigureLocation(this).x+1, 
					position.getFigureLocation(this).y);
		}
		if (castleType == Position.WHITE_CASTLE_QUEEN_SIDE 
				|| castleType == Position.BLACK_CASTLE_QUEEN_SIDE)
		{
			rook = position.getFigureAt(new ChessLocation(0, 
					position.getFigureLocation(this).y));
			rightOfQueensRook = new ChessLocation(1, 
					position.getFigureLocation(this).y);
			castleKingLocation = new ChessLocation(
					position.getFigureLocation(this).x-2, 
					position.getFigureLocation(this).y);
			castleRookLocation = new ChessLocation(
					position.getFigureLocation(this).x-1, 
					position.getFigureLocation(this).y);
		};
		// return if no rook was found to save time
		if (rook == null) 
			return null;
		// check, if all fields are free
		boolean castleLegal =
			(rightOfQueensRook == null // null here actually means we are doing
										// a castle on the king's side.
					|| rightOfQueensRook.x >= 0 && rightOfQueensRook.x < 8 && 
						position.getFigureAt(rightOfQueensRook) == null) && 
			castleRookLocation.x >= 0 && castleRookLocation.x < 8 &&
			position.getFigureAt(castleRookLocation) == null &&
			position.getFigureAt(castleKingLocation) == null;
		// to increase performance return if castle fails here, because chess
		// check is costly
		if (!castleLegal) 	return null; 
		castleLegal &=
		// check for chess and threats to fields the king moves across:
			!(getOwner().chess(position, new ChessLocation [] {kingLocation,
					castleKingLocation,
					castleRookLocation}));
		if (castleLegal)
		{
			// now build the position after castle in two steps:
			// first step: move king
			Position halfCastle 
				= new Position(position, this, castleKingLocation);
			// second step: move rook
			return new Position(halfCastle, rook, castleRookLocation);
		}
		return null;
	}
	
	@Override
	public final int getHashKey(int location) {
		return getOwner().getHashKeyProvider().getHashKey(location, this);
	}
}
