
package view;


import game.Game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;



/** Chess GUI. 
 * @author Richard Pohl*/
public class ChessFrame extends JFrame implements WindowListener {

	public static final int DEFAULT_WIDTH  = 800;
	public static final int DEFAULT_HEIGHT = 600;
	
	public static final int BORDER_WEST = 64;
	public static final int BORDER_EAST = 200;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1858497463528415833L;
	
	private Game game;
	
	private ViewMode viewMode;
	
	private BorderLayout layout = new BorderLayout();
	private MenuPanel menuPanel;
	private PositionPanel positionPanel;
	private InfoPanel infoPanel;
	
	/** Constructs a {@link ChessFrame}. Thereafter, call 
	 * {@link ChessFrame#display()} to initialize and display it. */
	public ChessFrame() {
		// initialize data
		game = new Game();
		addWindowListener(this);
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setGame(Game game) {
		deleteGameObservers();
		this.game = game;
		addGameObservers();
	}
	
	public ViewMode getViewMode() {
		return viewMode;
	}
	
	public MenuPanel getMenuPanel() {
		return menuPanel;
	}
	
	public PositionPanel getPositionPanel() {
		return positionPanel;
	}
	
	/** Puts this {@link ChessFrame} into the Position Editor Mode */
	public void positionMode() {
		viewMode = new ViewModePosition(this);
		
	}
	
	/** Puts this {@link ChessFrame} into the Interactive Game Mode */
	public void interactiveMode() {
		viewMode = new ViewModeInteractive(this);
	}
	
	/** Initializes and displays the {@link ChessFrame}.*/
	public void display()
	{
		// initialize components (order is important because of dependencies)
		initializePositionPanel();
		initializeMenuPanel();
		initializeInfoPanel();
		// add components as observers to game
		addGameObservers();
		// initialize frame
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		setLayout(layout);
		setTitle("MICE");
		pack();
		setVisible(true);
	}
	
	/** Removes the views that are Observer instances from game. */
	private void deleteGameObservers() {
		if (game != null)
		{
			game.deleteObserver(positionPanel);
			game.deleteObserver(infoPanel);
		}
	}
	
	/** Adds the views that are Observer instances to game.*/
	private void addGameObservers() {
		game.addObserver(positionPanel);
		positionPanel.update(null, null);
		game.addObserver(infoPanel);
		infoPanel.update(null, null);
	}
	
	private void initializeMenuPanel() {
		// create preference panel
		menuPanel = new MenuPanel(this); 
		menuPanel.initialize();
		// TODO check, if memory has any leaks here due to repeated allocation
		// of system resources by selector images
		menuPanel.setPreferredSize(new Dimension(BORDER_WEST, 
				DEFAULT_HEIGHT));
		layout.addLayoutComponent(menuPanel, BorderLayout.WEST);
		layout.layoutContainer(this);
	}
	
	private void initializePositionPanel() {
		// create position panel
		positionPanel = new PositionPanel(this);
		positionPanel.setPreferredSize(new Dimension(
				DEFAULT_WIDTH - BORDER_WEST - BORDER_EAST, DEFAULT_HEIGHT));
		layout.addLayoutComponent(positionPanel, BorderLayout.CENTER);
	}
	
	private void initializeInfoPanel() {
		// create notation panel
		infoPanel = new InfoPanel(this);
		infoPanel.setPreferredSize(
				new Dimension(BORDER_EAST, DEFAULT_HEIGHT));
		layout.addLayoutComponent(infoPanel, BorderLayout.EAST);
	}
	
	@Override
	public void windowOpened(WindowEvent e) {}
		
	@Override
	public void windowIconified(WindowEvent e) {}
	
	@Override
	public void windowDeiconified(WindowEvent e) {}
		
	@Override
	public void windowDeactivated(WindowEvent e) {}
		
	@Override
	public void windowClosing(WindowEvent e) {	System.exit(0);}
		
	@Override
	public void windowClosed(WindowEvent e) {}
		
	@Override
	public void windowActivated(WindowEvent e) {}
}
