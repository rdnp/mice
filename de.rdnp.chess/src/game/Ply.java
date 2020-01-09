package game;

import model.ChessLocation;
import model.Figure;
import model.Pawn;
import model.Position;
import model.PositionTree;

/**
 * Represents a ply in a chess game. A ply in a chess game is a single movement
 * of a figure on the board.
 * 
 * Remark: This class is not used for ply computations; the class for ply
 * computaitons is {@link PositionTree}. {@link Ply} is used as a record in the 
 * {@link GameHistory} of a {@link Game}.
 * @author Richard Pohl
 * */
public class Ply {
	
	private class IllegalPlyException extends RuntimeException {
		private static final long serialVersionUID = -337690518865878857L;
		public IllegalPlyException(String message) {
			super(message);
		}
	};
	
	private Position last;
	private Position next;
	
	/**
	 * Constructs a {@link Ply}.
	 * @param last - the position before the ply.
	 * @param next - the position after the ply.
	 * */
	public Ply(Position last, Position next)
	{
		this.last = last;
		this.next = next;
	}
	
	/**
	 * @return The Position before the {@link Ply} was performed.
	 * */
	public Position getLast() {
		return last;
	}
	
	/**
	 * Converts a {@link Ply} to a {@link String} representation in chess 
	 * notation.
	 * */
	@Override
	public String toString() {
		// TODO chess, en passant, castling, pawn transformation
		Figure figure = computeChangedFigure();
		String figureSymbol = figure.toString().substring(1,2), takeSymbol = "",
			sourceLocation = "", targetLocation = "";
		if (figureSymbol.equals("P"))
			figureSymbol = "";
		if (last.getFigureLocation(figure) != null 
									&& next.getFigureLocation(figure) != null)
		{
			takeSymbol = last.getFigureAt(
				next.getFigureLocation(figure)) != null? "x": "";
			sourceLocation = last.getFigureLocation(figure).toString();
			targetLocation = next.getFigureLocation(figure).toString();
		}
		return figureSymbol + sourceLocation + takeSymbol + targetLocation;
	}
	
	/**
	 * @return - the {@link Figure} that has changed its location by performing
	 * this {@link Ply}.
	 * */
	private Figure computeChangedFigure()
	{
		// check all locations to get the figure that changed from source to 
		// target
		for (int x = 0; x < 8; x++)
			for (int y = 0; y < 8; y++)
			{
				ChessLocation location = new ChessLocation(x, y);
				if (last.getFigureAt(location) != null)
				{
					
					Figure figure = last.getFigureAt(location);
					if (// figure was removed from old location 
						// => this is probably figure we are searching for	
						next.getFigureAt(location) == null
							// second condition avoids including pawns taken
							// en passant
							// or rooks that were used for castling
							&& next.getFigureLocation(figure) != null)
						return figure;
				}		
				// this is a pawn transformation, because a figure is 
				// on the board that was not there in the last move
				else if (next.getFigureAt(location) != null && 
						last.getFigureLocation(next.getFigureAt(location))
						== null)
					return new Pawn(next.getFigureAt(location).getOwner());
			}
		// no figure was changed => this ply is illegal
		throw new IllegalPlyException("Last and next position of a ply cannot" +
				" be equal!");
	}
}
