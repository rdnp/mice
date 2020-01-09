package view;

import model.ChessLocation;
import model.Figure;
import model.Position;

/**
 * Represents abstract state of the GUI.
 * @author Richard Pohl*/
public abstract class ViewMode {
	
	protected ChessFrame view;
	
	public ViewMode(ChessFrame view) {
		this.view = view;
		updatePositionPanel();
	}

	protected void updatePositionPanel() {
		view.getMenuPanel().resetMouseModeSelector();
	}
	
	protected abstract void updatePreferencePanel();

	/** Handles position changes from the user, depending on the 
	 * {@link ViewMode}.*/
	public abstract void changePosition(Position position);
	
	/** Enables to handle pawn location changes from the user, depending on 
	 * the {@link ViewMode} (e.g. to disallow en passant in 
	 * {@link ViewModePosition}).*/
	public Position handlePawnMove(Position currentPosition, Figure figure,
			ChessLocation location) {
		return currentPosition;
	}
	
	/** Enables to handle king location changes from the user, depending on 
	 * the {@link ViewMode} (e.g. to disallow castling in 
	 * {@link ViewModePosition}.*/
	public Position handleKingMove(Position currentPosition, Figure figure,
			ChessLocation location)
	{
		return currentPosition;
	}
}
