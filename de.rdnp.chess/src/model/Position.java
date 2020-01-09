package model;

import game.Player;

/**
 * Represents a chess position. 
 * <br>
 * See {@link Position#Position()} and 
 * {@link Position#Position(Position, Figure, ChessLocation)} on how
 * to construct the initial position or a position from a position and a 
 * ply.
 * <br>
 * Obtain figures and figure locations in a Position using the methods
 * {@link Position#getFigureAt(ChessLocation)} and 
 * {@link Position#getFigureLocation(Figure)}
 * <br>
 * Among providing different methods for position processing (e.g. obtaining 
 * figures in the position), this class defines the four castling constants:
 * {@link Position#WHITE_CASTLE_KING_SIDE},
 * {@link Position#WHITE_CASTLE_QUEEN_SIDE},
 * {@link Position#BLACK_CASTLE_KING_SIDE}, and
 * {@link Position#BLACK_CASTLE_QUEEN_SIDE}
 *  @author Richard Pohl*/
public class Position {
	
	/** A pre-computed hash code. */
	private int hash;
	
	/** 
	 * The figures in the position, held in an array. A figure's location is
	 * determined by the index (0..64) in the array, according to the following
	 * formula:<br>
	 * 		x-coordinate * 8 + y-coordinate,<br>
	 * whereby x-coordinate and y-coordinate are the figures coordinates on the 
	 * board. The range of each coordinate is (0..7). 
	 * <b>Note:</b> This is intentionally declared as a one-dimensional array,
	 * because this increases performance significantly (about 25 % compared to 
	 * using a two-dimensional array to keep figures).
	 */
	private final Figure [] figures;

	/* FOUR CONSTANTS FOR CASTLING */
	/** Constant to indicate a white castling to the King's side. It is used in
	 * and returned by methods of {@link Position} that deal with castling. 
	 * Can be used by figures to compare those method's return values. */
	public static final byte WHITE_CASTLE_KING_SIDE = 0x01;
	/** Constant to indicate a white castling to the Queen's side. It is used in
	 * and returned by methods of {@link Position} that deal with castling. 
	 * Can be used by figures to compare those method's return values. */
	public static final byte WHITE_CASTLE_QUEEN_SIDE = 0x02;
	/** Constant to indicate a black castling to the King's side. It is used in
	 * and returned by methods of {@link Position} that deal with castling. 
	 * Can be used by figures to compare those method's return values. */
	public static final byte BLACK_CASTLE_KING_SIDE = 0x04;
	/** Constant to indicate a black castling to the Queen's side. It is used in
	 * and returned by methods of {@link Position} that deal with castling. 
	 * Can be used by figures to compare those method's return values. */
	public static final byte BLACK_CASTLE_QUEEN_SIDE = 0x08;
	
	/** Holds the state of the castling for each player in a position.
	 * Independent from chess and threats from other figures to fields.
	 * This is only to hold the history part of the castling condition.
	 * By default, all castles are allowed. Call 
	 * {@link Position}{@link #castle(int)} to disallow a castling 
	 * ply. 
	 * */
	private int castlingHistory = 0x0F;
	
	/** The x-position, on which a pawn can be taken en passant; -1 if no 
	 * en passant ply is legal. */
	private int enPassantLine = -1;
	
	/** Create the initial position */
	public Position() {
		// build initial position
		figures = new Figure[64];
		for (int p = 0; p < 8; p += 7)
		{
			Player player = null;
			if (p == 0)			player = Player.WHITE;
			else if (p == 7) 	player = Player.BLACK;
			figures[0*8+p] = new Rook(player);
			figures[1*8+p] = new Knight(player);
			figures[2*8+p] = new Bishop(player);
			figures[3*8+p] = new Queen(player);
			figures[4*8+p] = new King(player);
			figures[5*8+p] = new Bishop(player);
			figures[6*8+p] = new Knight(player);
			figures[7*8+p] = new Rook(player);
		}
		for (int p = 1; p < 7; p += 5)
		{
			Player player = null;
			if (p == 1)			player = Player.WHITE;
			else if (p == 6) 	player = Player.BLACK;
			for (int x=0; x<8; x++)
				figures[x*8+p] = new Pawn(player);
		}
		computeZobristHash();
	}
	
	/** Create a position by moving figure to location in position.
	 * @param position - the {@link Position}
	 * @param figure - the {@link Figure}.
	 *  */
	public Position(Position position, Figure figure, 
			ChessLocation location) 
	{
		figures = new Figure[64];
		System.arraycopy(position.figures, 0, figures, 0, 64);
		ChessLocation oldFigureLocation = null;
		if (figure != null)
			oldFigureLocation = position.getFigureLocation(figure);
		// remove old figure
		if (figure != null && oldFigureLocation != null)
			figures[oldFigureLocation.x*8+oldFigureLocation.y] = null;
		// insert new figure at location
		if (location != null)
			figures[location.x*8+location.y] = figure;
		// update castling rights: if king or rook moved, cancel corresponding
		// rights
		if (figure != null )
		{
			updateCastlingHistory(position, figure);
			if (oldFigureLocation != null)
				updateEnPassantLine(position, figure);
		}
		computeZobristHash();
	}
	
	/** 
	 * @param figure - the {@link Figure}. 
	 * @return A {@link ChessLocation} representing the location of figure.*/
	public final ChessLocation getFigureLocation(Figure figure)
	{
		for (int i = 0; i < 64; i++)
			if (figure.equals(figures[i]))
				return new ChessLocation(i/8, i%8);
		return null;
	}
	
	/** 
	 * @param location - a {@link ChessLocation} representing the location to 
	 * look at.
	 * @return The {@link Figure} at location. 
	 * */
	public final Figure getFigureAt(ChessLocation location)
	{
		return figures[location.x * 8 + location.y];
	}
	
	/** @return The castling history in the position - one of the following:
	 	{@link Position#WHITE_CASTLE_KING_SIDE}, 
	 	{@link Position#WHITE_CASTLE_QUEEN_SIDE},
	 	{@link Position#BLACK_CASTLE_KING_SIDE}, 
	 	{@link Position#BLACK_CASTLE_QUEEN_SIDE},*/
	public final int getCastlingHistory() {
		return castlingHistory;
	}
	
	/** Sets the castling history in the position.
	 @param castlingHistory - one of the following:
	 	{@link Position#WHITE_CASTLE_KING_SIDE}, 
	 	{@link Position#WHITE_CASTLE_QUEEN_SIDE},
	 	{@link Position#BLACK_CASTLE_KING_SIDE}, 
	 	{@link Position#BLACK_CASTLE_QUEEN_SIDE},
	 */
	public final void setCastlingHistory(int castlingHistory) {
		this.castlingHistory = castlingHistory;
		computeZobristHash();
	}
	
	/**@return The x-position, on which a pawn can be taken en passant; -1 if no 
	 * en passant ply is legal.*/
	public final int getEnPassantLine() {
		return enPassantLine;
	}
	
	/**
	 * Sets the en passant line of the position.
	 * @param enPassantLine 
	 * The x-position, on which a pawn can be taken en passant; -1 if no 
	 * en passant ply is legal.*/
	public final void setEnPassantLine(int enPassantLine) {
		this.enPassantLine = enPassantLine;
		computeZobristHash();
	}
	
	@Override
	public final int hashCode() {
		return hash;
	}
	
	@Override
	public final boolean equals(Object obj) {
		if (obj instanceof Position)
		{
			Position anotherPosition = (Position)obj;
//			if (hash != anotherPosition.hash) return false;
			for (int i = 0; i < 64; i++)
			{
				if (figures[i]==null)
				{
					if (anotherPosition.figures[i]!=null)
						return false;
				}
				else if (anotherPosition.figures[i] == null)
					return false;
				else if (anotherPosition.figures[i].getClass()
						!= figures[i].getClass() ||
						anotherPosition.figures[i].getOwner()
						!= figures[i].getOwner())
					return false;
			}
			return enPassantLine == anotherPosition.enPassantLine
				&& castlingHistory == anotherPosition.castlingHistory;
		}
		return false;
	}
	
	@Override
	public final String toString() {
		String result = "Position:\n";
		for (int y = 7; y >= 0; y--)
		{
			result += Integer.toString(y + 1) + "  ";
			for (int x = 0; x < 8; x++)
				if (figures[x * 8 + y] != null)
					result += figures[x * 8 + y].toString()+" ";
				else result += "   ";
			result += "\n";
		}
		result += "--  A  B  C  D  E  F  G  H";
		return result;
	}
	
	/**
	 * Computes the Zobrist hash for this {@link Position} and stores the result
	 * in {@link Position#hash}.
	 * */
	private void computeZobristHash() {
		for (int i = 0; i < 64; i++)
		{
			if (figures[i] != null)
				hash ^= figures[i].getHashKey(i);
		}
		hash ^= castlingHistory;
		hash ^= enPassantLine;
	}
	
	/**
	 * @param player - the {@link Player} for which the {@link Position} should
	 * be evaluated.
	 * @return The advantage of player (in centipawns).
	 * */
	public final int evaluate(Player player)
	{
		int value = 0;
		for (int i = 0; i < 64; i++)
			if (figures[i] != null)
			{
				// sign determines wheter to add a positive advantage
				// or a negative advantage for the given figure.
				int sign = 0;
				if (figures[i].getOwner().equals(player))
					sign = 1;
				else 
					sign = -1;
				value += sign * figures[i].getValue();
			}
		return value;
	}
	
	/** @param castleType - one of the four castling constants defined in
	 * {@link Position}
	 * @return true, iff a castling according to castleType has already occurred
	 * in the current position. */
	public final boolean hasCastled(int castleType)
	{
		if ((castlingHistory & castleType) == 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Disallows a specific type of castling ply in position.
	 * @param castleType - one of the four castling constants defined in
	 * {@link Position}
	 * */
	private void castle(int castleType)
	{
		// clear the bit at the position where castleType is 1
		castlingHistory ^= castleType;
	}
	
	/** 
	 * Updates {@link Position#castlingHistory}, depending on the last 
	 * ply performed. The last ply is specified in terms of the last position 
	 * and the figure that changed its positoin. 
	 * This updates only the given player's 
	 * castling rights. Watch out, you have to provide the given player's 
	 * castling codes to this method! */
	private void updatePlayerCastlingHistory(Position position, Figure figure,
			int castleKingSideCode, int castleQueenSideCode)
	{
		castlingHistory = position.castlingHistory;
		if (castlingHistory > 0)
		{
			// king ply
			if (figure.getClass() == King.class)
			{
				castle(castleKingSideCode);
				castle(castleQueenSideCode);
			}
			// rook ply
			else if (figure.getClass() == Rook.class)
			{
				ChessLocation oldRookLocation 
					= position.getFigureLocation(figure);
				// king's rook ply
				if (oldRookLocation != null && oldRookLocation.x == 0)
					castle(castleKingSideCode);
				// queen's rook ply
				if (oldRookLocation != null && oldRookLocation.x == 7)
					castle(castleQueenSideCode);
			}
		}	
	}
	
	/** 
	 * Updates {@link Position#castlingHistory}, depending on the last ply per-
	 * formed. The last ply is specified in terms of the last position and
	 * the figure that changed its position. */
	private void updateCastlingHistory(Position position, Figure figure)
	{

		castlingHistory = position.castlingHistory;
		if (castlingHistory > 0)
		{
			if (figure.getOwner() == Player.WHITE)
				updatePlayerCastlingHistory(position, figure,
					WHITE_CASTLE_KING_SIDE, WHITE_CASTLE_QUEEN_SIDE);
			if (figure.getOwner() == Player.BLACK)
				updatePlayerCastlingHistory(position, figure,
					BLACK_CASTLE_KING_SIDE, BLACK_CASTLE_QUEEN_SIDE);
		}
	}
	
	/** 
	 * Updates {@link Position#enPassantLine}, depending on the last move per-
	 * formed. The last move is specified in terms of the last position and
	 * the figure that was moved. */
	private void updateEnPassantLine(Position position, Figure figure)
	{
		enPassantLine = -1;
		ChessLocation currentFigureLocation = getFigureLocation(figure);
		if (figure.getClass() == Pawn.class && currentFigureLocation != null
				&& Math.abs(position.getFigureLocation(figure).y 
						- currentFigureLocation.y) == 2)
		{
			// figures left and right of current pawn, used for checking if
			// en passant move is possible
			Figure left = null;
			if (currentFigureLocation.x > 0)
				left = figures[(currentFigureLocation.x - 1) * 8 
					           		+ currentFigureLocation.y];
			Figure right = null;
			if (currentFigureLocation.x < 7)
				right = figures[(currentFigureLocation.x + 1) * 8 
					           		+ currentFigureLocation.y];
			// only add the en passant line if it is possible to do an 
			// en passant move, this is also to avoid that the same position 
			// will produce different hash values
			if ((left != null && left.getClass() == Pawn.class 
										&& left.getOwner() != figure.getOwner()) 
					|| (right != null && right.getClass() == Pawn.class 
									   && right.getOwner() != figure.getOwner())
					)
				enPassantLine = currentFigureLocation.x;
		}
	}
	
}
