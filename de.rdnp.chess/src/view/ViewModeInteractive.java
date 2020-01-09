package view;

import model.ChessLocation;
import model.Figure;
import model.Pawn;
import model.Position;

/**@author Richard Pohl*/
public class ViewModeInteractive extends ViewMode {
	

	public ViewModeInteractive(ChessFrame view) {
		super(view);
		updatePreferencePanel();
	}

	@Override
	protected void updatePreferencePanel()
	{
		view.getMenuPanel().setPositionComponentsVisible(false);
		view.getMenuPanel().setInteractiveComponentsVisible(true);
	}

	@Override
	public void changePosition(Position position) {
		// desire to change the position, since only legal moves are allowed 
		// in this mode.
		view.getGame().getActivePlayer().desire(position);	
	}
	
	@Override
	public Position handlePawnMove(Position currentPosition, Figure figure,
			ChessLocation targetLocation) {
		// en passant move
		if ((targetLocation.x == currentPosition.getEnPassantLine() && 
				currentPosition.getFigureAt(targetLocation) == null && 
				currentPosition.getFigureLocation(figure).x 
														!= targetLocation.x))
			// remove pawn to be taken en passant manually, because user 
			// moving figure does not provide that
			return new Position(currentPosition, 
					currentPosition.getFigureAt(
						new ChessLocation(targetLocation.x, 
						currentPosition.getFigureLocation(figure).y)), null);
		// pawn transformation
		else if ((targetLocation.y == 0 || targetLocation.y == 7))
		{
			Position result = new Position(currentPosition, figure, null);
			Figure transformed = new TransformPawnDialog(
					view, 
					(Pawn)figure).open();
			System.out.println("attempting "+new Position(result, transformed, targetLocation));
			return new Position(result, transformed, targetLocation);
		}
		return new Position(currentPosition, figure, targetLocation);
	}
	
	@Override
	public Position handleKingMove(Position currentPosition, Figure figure,
			ChessLocation targetLocation)
	{
		// castle king side
		if (targetLocation.x - currentPosition.getFigureLocation(figure).x == 2)
			return new Position(
					currentPosition,
					currentPosition.getFigureAt(
							new ChessLocation(7, targetLocation.y)),
					new ChessLocation(targetLocation.x - 1, targetLocation.y) );
		// castle queen side
		else if (targetLocation.x 
				- currentPosition.getFigureLocation(figure).x == -2)
			return new Position(
					currentPosition,
					currentPosition.getFigureAt(
							new ChessLocation(0, targetLocation.y)),
					new ChessLocation(targetLocation.x + 1, targetLocation.y) );
		return currentPosition;
	}
}
