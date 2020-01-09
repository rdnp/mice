package view;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Figure;
import model.Pawn;

/** A frame in which the user can choose a figure to transform a pawn. 
 * @author Richard Pohl*/
public class TransformPawnDialog extends Dialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1524860083736884900L;

	/** ActionListener for {@link FigureButton}. */
	private class TransformPawnActionListener implements ActionListener {
		private Figure figure;
		
		public TransformPawnActionListener(Figure figure) {
			this.figure = figure;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			newFigure = figure;
			dispose();
		}
	}
	
	/** Button to select a figure. */
	private class FigureButton extends Button {
		/**
		 * 
		 */
		private static final long serialVersionUID = 148138134110286517L;
		private Figure figure;
		
		public FigureButton(Figure figure) {
			this.figure = figure;
			addActionListener(new TransformPawnActionListener(figure));
			setPreferredSize(new Dimension(48,48));
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			if (PositionPanel.getChessFontName() != null)
			{
				g.setFont(new Font(PositionPanel.getChessFontName(), 
						Font.PLAIN, 40));
				g.drawString(figure.toUnicode(), getWidth()/2-20, 
						getHeight()/2+18);
			}
			else
				g.drawString(figure.toString(), getWidth()/2-5, 
						getHeight()/2+5);
		}
	}
	
	private FigureButton bishopButton, knightButton, rookButton, queenButton;
	/** The figure to transform the pawn into. */
	private Figure newFigure;
	
	/** Creates and initializes a {@link TransformPawnDialog}*/
	public TransformPawnDialog(Frame owner, Pawn figure) {
		super(owner, true);
		bishopButton = new FigureButton(figure.toBishop());
		knightButton = new FigureButton(figure.toKnight());
		rookButton = new FigureButton(figure.toRook());
		queenButton = new FigureButton(figure.toQueen());
		add(bishopButton);
		add(knightButton);
		add(rookButton);
		add(queenButton);
		setLayout(new FlowLayout());
		pack();
		setLocation(owner.getX()+owner.getWidth()/2-getWidth()/2, 
				owner.getY()+owner.getHeight()/2-getHeight()/2);
	}
	
	/** Opens the {@link TransformPawnDialog}.
	 * @return The figure selected by the user. */
	public Figure open() {
		setVisible(true);
		// simply wait for the user to press one of the buttons here
		while (newFigure == null)
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		return newFigure;
	}
}
