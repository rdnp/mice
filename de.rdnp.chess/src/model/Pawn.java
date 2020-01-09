package model;

import game.Player;

/**
 *  Represents a pawn.
 *  @author Richard Pohl*/
public class Pawn extends Figure {

	private final int direction;
	private int resultIndex;
	private boolean transform;
	private boolean enPassant;
	
	private Bishop transformedBishop = new Bishop(owner);
	private Knight transformedKnight = new Knight(owner);
	private Rook transformedRook = new Rook(owner);
	private Queen transformedQueen = new Queen(owner);
	
	public Pawn(Player player) {
		super(player);
		// pawn plies are directed, depending on the player, so we give
		// black a negative sign to invert the direction
		if (owner == Player.WHITE)		direction = 1;
		else if (owner == Player.BLACK)	direction = -1;
		else direction = 0;
	}
	
	@Override
	public final double getValue() {
		return 100d;
	}

	@Override
	public final String toString() {
		return super.toString()+"P";
	}

	@Override
	public final String toUnicode() {
		if (super.toString().equals("w"))
			return FigureSymbols.WHITE_PAWN;
		else if (super.toString().equals("b"))
			return FigureSymbols.BLACK_PAWN;
		else return "P";
	}
	
	public Bishop toBishop() {
		return transformedBishop;
	}
	
	public Knight toKnight() {
		return transformedKnight;
	}
	
	public Queen toQueen() {
		return transformedQueen;
	}
	
	public Rook toRook() {
		return transformedRook;
	}
	
	private void addNormalOnePlyLocations(ChessLocation[] result, 
			Position position)
	{
		// normal movement by one or two fields
		int x = position.getFigureLocation(this).x, 
			y = position.getFigureLocation(this).y + direction;
		ChessLocation plyLocation = null;
		// flag to capture if a long ply (over 2 fields) is allowed
		boolean longPly = true;
		while (y >= 0 && y < 8 
				&& (Math.abs(y - position.getFigureLocation(this).y) < 3)
				&& position.getFigureAt(
				plyLocation = new ChessLocation(x, y)) == null
				&& longPly
			)
		{
			result[resultIndex++] = plyLocation;
			y += direction;
			// disallow long move on opponent's side:
			if (direction == 1 && y > 3 || direction == -1 && y < 4)
				longPly = false;
		}
	}
	
	private boolean addTakingOnePlylocations(ChessLocation[] result,
			Position position, int takingDirection /*L=-1, R=1*/)
	{
		// this is needed for calculating the en passant ply
		// the x-position of the en passant ply:
		int enPassantLine = position.getEnPassantLine();
		// the y-position of the en passant ply:
		int enPassantRow = (direction == 1? 5: 2);
		// taking another figure (diagonally), including en passant
		ChessLocation plyLocation = new ChessLocation(
				position.getFigureLocation(this).x + takingDirection, 
								position.getFigureLocation(this).y + direction);
		boolean takeLocationExists 
			= plyLocation.x >= 0 && plyLocation.x < 8 
				&& plyLocation.y >= 0 && plyLocation.y < 8;
		boolean takeLegal
			= takeLocationExists &&
				(position.getFigureAt(plyLocation) != null && 
					!position.getFigureAt(plyLocation).getOwner().equals(
							getOwner()));
		boolean _enPassant = takeLocationExists && 
			(plyLocation.x == enPassantLine && plyLocation.y == enPassantRow);
		if (takeLegal || _enPassant)
			result[resultIndex++] = plyLocation;
		return _enPassant;
	}
	
	@Override
	protected final ChessLocation[] computeOnePlyLocations(Position position)
	{
		ChessLocation[] result = new ChessLocation[13];
		resultIndex = 0;
		// flag for en passant, will be used to remove opponent's pawn later
		enPassant = false;
		// flag for pawn transformation
		transform = false;
		if (direction == 1 && position.getFigureLocation(this).y == 6 ||
			direction == -1 && position.getFigureLocation(this).y == 1)
			transform = true;
		addNormalOnePlyLocations(result, position);
		enPassant = 
			addTakingOnePlylocations(result, position,  1) ||
			addTakingOnePlylocations(result, position, -1);
		return result;
	}

	// needs to be synchronized because it consists of multiple method calls
	// that perform an operation that needs to be done without interleaving
	// (at least on the same pawn)
	@Override
	public synchronized final Position[] computeOnePly(Position position) {
		Position[] result = super.computeOnePly(position);
		// add pawn transformation
		if (transform)
		{
			// number of possible pawn plies
			int movementCount = resultIndex;
			for (resultIndex = 0; resultIndex < movementCount; resultIndex++)
			{
				// the position in which we transform a pawn
				Position basePosition = result[resultIndex];
				if (result[resultIndex] == null)
					System.out.println("NULL position at "+resultIndex+
							" with movementCount = "+movementCount
							+"\nCritical "+position.toString()
							+"\nCritical Pawn at "+position.getFigureLocation(this)
					);
				
				
				// replace pawn by the four possible figures
				result[resultIndex] = new Position(basePosition, 
					transformedBishop, basePosition.getFigureLocation(this));
				result[movementCount + resultIndex] = new Position(basePosition, 
					transformedKnight, basePosition.getFigureLocation(this));
				result[2 * movementCount + resultIndex] 
				    = new Position(basePosition, 
				    transformedQueen, basePosition.getFigureLocation(this));
				result[3 * movementCount + resultIndex] 
				    = new Position(basePosition, 
				    transformedRook, basePosition.getFigureLocation(this));
			}
		} 
		// remove the opponent's pawn from en passant move
		else if (enPassant)
		{
			for (int i = 0; result[i] != null; i++)
			{
				if (result[i].getFigureLocation(this).x == 
					position.getEnPassantLine())
					result[i] = new Position(
							result[i], null, new ChessLocation(
									position.getEnPassantLine(), 
									position.getFigureLocation(this).y)
					);
			}
		}
		return result;
	}
	
	@Override
	public final int getHashKey(int location) {
		return getOwner().getHashKeyProvider().getHashKey(location, this);
	}
}
