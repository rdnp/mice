package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.ChessLocation;


/**
 * An abstract mouse handler for the position panel. Converts the coordinates
 * on the display into {@link ChessLocation}s.
 * @author Richard Pohl*/
public abstract class PositionMouseHandler implements MouseListener {
	
	protected PositionPanel positionPanel;
	
	public PositionMouseHandler(PositionPanel positionPanel) {
		this.positionPanel = positionPanel;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (registerMouse(e) != null)
			handleMousePressed(registerMouse(e));
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if (registerMouse(e) != null)
			handleMouseReleased(registerMouse(e));
	}
	
	/** Registers a mouse movement. */
	private ChessLocation registerMouse(MouseEvent e) {
		int boardX = (e.getX())/positionPanel.getFieldSize();
		int boardY = 8 - (e.getY() - 40)/positionPanel.getFieldSize();
		if (boardX >= 0 && boardX < 8 && boardY >= 0 && boardY < 8)
			return new ChessLocation(boardX, boardY);
		else return null;
	}
	
	/** Handles a mouse press on the chess board. Parameter is a
	 *  ({@link ChessLocation}) representing the location of the mouse.*/
	protected abstract void handleMousePressed(ChessLocation location);

	/** Handles a mouse release on the chess board. Parameter is a
	 *  ({@link ChessLocation}) representing the location of the mouse.*/
	protected abstract void handleMouseReleased(ChessLocation location);
	
}
