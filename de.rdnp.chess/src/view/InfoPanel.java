package view;

import java.awt.Dimension;
import java.awt.Label;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/** A panel to displa additional informations to the user. 
 * @author Richard Pohl
 * */
public class InfoPanel extends Panel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5689127362589549790L;

	private static final int NOTATION_HEIGHT = 250;
	
	private ChessFrame chessFrame;
	private ScrollPane container;
	private Panel notationPanel;
	private Label caption;
	private List<NotationElement> notationElements;
	
	/**
	 * Constructs a {@link InfoPanel}.
	 * @param chessFrame - the {@link ChessFrame} in which the 
	 * {@link InfoPanel} should be displayed.
	 * */
	public InfoPanel(ChessFrame chessFrame) {
		this.chessFrame = chessFrame;
		notationElements = new ArrayList<NotationElement>();
		container = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
		notationPanel = new Panel();
		notationPanel.setPreferredSize(
				new Dimension(ChessFrame.BORDER_EAST - 36, 0));
		caption = new Label();
		add(caption);
		notationPanel.setLayout(new NotationLayout());
		container.setSize(
				new Dimension(ChessFrame.BORDER_EAST - 16, NOTATION_HEIGHT));
		container.add(notationPanel);
		add(container);
		chessFrame.add(this);
	}
	
	public ChessFrame getChessFrame() {
		return chessFrame;
	}
	
	public Panel getNotationPanel() {
		return notationPanel;
	}
		
	@Override
	public void update(Observable o, Object arg) {
		// add new notation element for last ply
		int index = chessFrame.getGame().getGameHistory().getSize() - 1;
		if (index > -1)
		{
			NotationElement element = new NotationElement(this,	
					(index % 2 == 0? Integer.toString(index / 2 + 1) + ".": "")
					 + chessFrame.getGame().getGameHistory().plyAsString(index),
					index);
			notationElements.add(element);
		}
		notationPanel.setPreferredSize(
			new Dimension(ChessFrame.BORDER_EAST - 36,
				(index / 2 + 1) 
					* (NotationElement.NOTATION_ELEMENT_HEIGHT + 2)));
		String playerString = "";
		if (chessFrame.getGame().getActivePlayer() != null)
			playerString = chessFrame.getGame().getActivePlayer().toString() 
				+ " at move.";
		else if (chessFrame.getGame().getWinner() != null)
			playerString = chessFrame.getGame().getWinner().toString() 
				+ " won!";
		else
			playerString = "draw.";
		caption.setText("Moves ("+playerString+")");
		notationPanel.doLayout();
		container.validate();
	}

	public void deselectAllNotationElements() {
		for (NotationElement element: notationElements)
			element.setSelected(false);
	}
	
	
}
