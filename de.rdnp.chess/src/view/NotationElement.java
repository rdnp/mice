package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This is the view corresponding to a ply in the {@link InfoPanel}.
 * @author Richard Pohl
 * */
public class NotationElement extends Panel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7864470761999012068L;
	public static final int NOTATION_ELEMENT_WIDTH = 64;
	public static final int NOTATION_ELEMENT_HEIGHT = 16;
	
	// flag to indicate whether the NotationElement is selected, i.e. whether
	// the position of this element should be displayed.
	private boolean selected;
	private InfoPanel owner;
	private int index;
	private String ply;
	
	public NotationElement(InfoPanel owner, String plyAsString,
			int index) {
		this.owner = owner;
		this.index = index;
		this.ply = plyAsString;
		addMouseListener(this);
		setPreferredSize(new Dimension(
				NOTATION_ELEMENT_WIDTH, 
				NOTATION_ELEMENT_HEIGHT));
		owner.getNotationPanel().add(this);
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		if (selected)
		{
			g.setFont(new Font(g.getFont().getName(), Font.BOLD, 
					g.getFont().getSize()));
			g.drawRect(2, 2, getWidth()-4, getHeight()-4);
		}
		g.drawString(ply, 2, 12);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		boolean oldSelected = selected;
		owner.deselectAllNotationElements();
		selected = !oldSelected;
		if (selected)
			owner.getChessFrame().getPositionPanel().setDisplayPosition(
				owner.getChessFrame().getGame().getGameHistory()
				.getPositionBefore(index));
		else
		{
			owner.getChessFrame().getPositionPanel().setDisplayPosition(null);
		}
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
