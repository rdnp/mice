package view;


import game.Game;
import game.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Panel;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Observable;
import java.util.Observer;

import model.ChessLocation;
import model.Figure;
import model.Position;


/**
 * Chess panel to be used in the chess GUI to display positions. 
 * @author Richard Pohl*/
public class PositionPanel extends Panel implements ComponentListener, Observer 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1394715937528864051L;
	
	private static final int TOP_MARGIN_HEIGHT = 32;
	private static final int STATUS_BAR_HEIGHT = 32;
	
	// Colors for the field backgrounds:
	private static final Color COLOR_DEFAULT = new Color(0,0,0);
	private static final Color COLOR_BLACK_FIELD = new Color(196,232,224);
	private static final Color COLOR_WHITE_FIELD = new Color(255,255,255);
	
	private Position displayPosition;
	private int fieldSize;
	private static String chessFontName;
	
	private ChessFrame container;
	private PositionMouseHandler mouseHandler;
	
	public PositionPanel(ChessFrame container) {
		// initialize model-related fields, add to game as an observer
		this.container = container;
		// initialize components
		initializeChessFont();
		// add mouse listener, component listener
		addComponentListener(this);
		// add to container
		container.add(this);
	}
	
	public static String getChessFontName() {
		return chessFontName;
	}
	
	public int getFieldSize() {
		return fieldSize;
	}
	
	public ChessFrame getChessFrame() {
		return container;
	}
	
	public Game getGame() {
		return container.getGame();
	}
	
	public Position getDisplayPosition() {
		if (displayPosition != null)
			return displayPosition;
		return container.getGame().getPosition();
	}
	
	public void setDisplayPosition(Position displayPosition) {
		this.displayPosition = displayPosition;
		repaint();
	}
	
	@Override
	public void componentHidden(ComponentEvent e) {}
	
	@Override
	public void componentMoved(ComponentEvent e) {}
	
	@Override
	public void componentResized(ComponentEvent e) {
		setPreferredSize(
			new Dimension(
				container.getWidth() - ChessFrame.BORDER_WEST 
					- ChessFrame.BORDER_EAST, 
				container.getHeight() - TOP_MARGIN_HEIGHT - STATUS_BAR_HEIGHT));
		repaint();
	}
	
	@Override
	public void componentShown(ComponentEvent e) {}
	
	@Override
	public void paint(Graphics g) {
		// general initialization
		super.paint(g);
		Position p = getDisplayPosition();
		g.clearRect(0, 0, 8 * fieldSize + 85, 8 * fieldSize);
		fieldSize = Math.min(getWidth(), getHeight() - TOP_MARGIN_HEIGHT 
				- STATUS_BAR_HEIGHT) * 10 / 89;
		// initialize fonts
		Font f = g.getFont();
		Font fLarge = new Font(chessFontName, f.getStyle(), f.getSize() 
				* fieldSize / 14);
		Font fSmall = new Font(f.getName(), f.getStyle(), f.getSize() * 1);
		// if no chess font found, make chess labels smaller, because two 
		// letters will be displayed
		if (chessFontName == null)
			fLarge = new Font(chessFontName, f.getStyle(), 
					f.getSize() * fieldSize / 20);
		g.setFont(fLarge); 
		// flag to indicate whether to draw a black or a white field.
		boolean black = true;
		// draw the position
		for (int x = 0; x < 8; x++)
		{
			for (int y = 0; y < 8; y++)
			{
				// draw field background 
				g.setColor(black?COLOR_BLACK_FIELD:COLOR_WHITE_FIELD);
				g.fillRect(fieldSize * x, TOP_MARGIN_HEIGHT 
						+ fieldSize * (8 - y), 
						fieldSize, fieldSize);
				g.setColor(COLOR_DEFAULT);
				// switch background color after y-increase
				black = !black;
				// get and draw figures
				Figure figure = p.getFigureAt(new ChessLocation(x,y));
				// the following command will draw chess symbol if a chess font 
				// is installed, a String abbreviation of the figure otherwise
				String fieldLabel = (figure != null?
						(chessFontName != null?
									figure.toUnicode(): figure.toString()
						): 
						"");
				g.drawString(fieldLabel, 
						fieldSize * x + fLarge.getSize() / 6, 
						TOP_MARGIN_HEIGHT + fieldSize * (8 - y) 
							+ fLarge.getSize());
			}
			// switch background color after x-increase
			black = !black;
		}
		// draw position evaluation
		g.setFont(fSmall);
		g.drawString("Position value: "
				+Integer.toString(p.evaluate(Player.WHITE)), 8, 
				getHeight() - STATUS_BAR_HEIGHT + 12);
		g.drawString("MICE = Minimalistic Intelligent Chess Engine", 
				8, 24);
		g.drawString("© Richard D. N. Pohl, 2011. All rights reserved. " +
				"This is an alpha version.", 
				8, 40);
		g.drawString("Redistribution (including modified versions) only with " +
				"permission from the author. ",
				8, 56);
		g.drawString(
				"Apply for permissions at and report violations and bugs to " +
				"richardpohl@gmx.net.", 
				8, 72);
		g.dispose();
	}

	@Override
	public void update(Observable o, Object arg) {
		this.repaint();			
	}

	/** Will cause this PositionPanel to react on mouse events in changing
	 * the figure on the mouse location into the figure in newFigure. Will
	 * remove the figure at the mouse location, if newFigure is null.*/
	public void changeFigureOnMouse(Figure newFigure)
	{
		if (mouseHandler != null) this.removeMouseListener(mouseHandler);
		mouseHandler = new ChangeFigureHandler(this, newFigure);
		this.addMouseListener(mouseHandler);
	}
	
	/** Will cause this PositionPanel to react on mouse events in moving
	 * the figure on the mouse location to a new location via drag and drop.*/
	public void moveFigureOnMouse()
	{
		if (mouseHandler != null) this.removeMouseListener(mouseHandler);
		mouseHandler = new MoveFigureHandler(this);
		this.addMouseListener(mouseHandler);
	}
	
	/** Will initialize the chess font, if one is installed. */
	private static void initializeChessFont() {
		// check for chess fonts
		Font [] allFonts = 
			GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		// try to use Segoe UI Symbol to make the application look the same
		// on all systems with this font installed.
		for (Font f: allFonts)
			if (f.getName().equalsIgnoreCase("Segoe UI Symbol"))
				chessFontName = f.getName();
		// if this fails, try finding any other font supporting chess symbols.
		if (chessFontName == null)
			for (Font f: allFonts)
				if (f.canDisplay('\u2659')) // the white pawn symbol
				{
					chessFontName = f.getName();
					break;
				}
	}
	
}

