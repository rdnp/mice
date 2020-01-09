package view;

import model.ChessLocation;
import model.Figure;
import model.King;
import model.Pawn;
import model.Position;

/**
 * Mouse Handler for moving figures.
 * @author Richard Pohl*/
public class MoveFigureHandler extends PositionMouseHandler {

	/** The figure to move. */
	private Figure figure;
	
	public MoveFigureHandler(PositionPanel positionPanel) {
		super(positionPanel);
	}
	
	@Override
	protected void handleMousePressed(ChessLocation location) {
		Position currentPosition = positionPanel.getGame().getPosition();
		figure = currentPosition.getFigureAt(location);
	}
	
	@Override
	protected void handleMouseReleased(ChessLocation location) {
		// XXX maybe the handling of the release should depend on the state
		// of the view, because otherwise, e.g. castling moves and en passant
		// moves are possible in the position editor mode...
		Position currentPosition = positionPanel.getGame().getPosition();
		Position result = currentPosition;
		if (figure != null && figure.getClass() == Pawn.class)
		{
			result = 
				positionPanel.getChessFrame().getViewMode()
					.handlePawnMove(currentPosition, figure, location);
			if (result.getFigureAt(location) != figure)
				figure = result.getFigureAt(location);
		}
		else if (figure != null && figure.getClass() == King.class)
			result = 
				positionPanel.getChessFrame().getViewMode()
					.handleKingMove(currentPosition, figure, location);
		positionPanel.getChessFrame().getViewMode().changePosition( 
				new Position(result, figure, location));
	}
	
	
}
