package view;

import model.Position;

/**
 * Represents the mode of the GUI in which the user can edit the position.
 * @author Richard Pohl*/
public class ViewModePosition extends ViewMode {

	public ViewModePosition(ChessFrame view) {
		super(view);
		updatePreferencePanel();
	}
	
	@Override
	protected void updatePreferencePanel()
	{
		view.getMenuPanel().setPositionComponentsVisible(true);
		view.getMenuPanel().setInteractiveComponentsVisible(false);

		view.getMenuPanel().resetPositionPlayerSelector();
	}

	@Override
	public void changePosition(Position position) {
		// set the position since we are in edit mode.
		view.getGame().setPosition(position);
	}
}
