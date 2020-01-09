package view;

import model.ChessLocation;
import model.Figure;
import model.Position;

/**
 * Mouse handler for changing the figure at a location on the board.
 * @author Richard Pohl*/
public class ChangeFigureHandler extends PositionMouseHandler {
	
	private Figure figure;
	
	public ChangeFigureHandler(PositionPanel positionPanel, Figure figure) {
		super(positionPanel);
		this.figure = figure;
	}
	
	@Override
	protected void handleMousePressed(ChessLocation location) {
		
	}

	@Override
	protected void handleMouseReleased(ChessLocation location) {
		Position currentPosition = positionPanel.getGame().getPosition();
		positionPanel.getChessFrame().getGame().setPosition(
				new Position(currentPosition, figure, location)
		);
	}
}
