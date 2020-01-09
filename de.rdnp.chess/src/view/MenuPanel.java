package view;

import game.Game;
import game.Player;

import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import model.Bishop;
import model.King;
import model.Knight;
import model.Pawn;
import model.Queen;
import model.Rook;


/** 
 * A panel where the user can select different preferences for the game.
 * @author Richard Pohl
 * */
public class MenuPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8822821014097092742L;
	
	private ChessFrame container;
	
	private List<Component> positionComponents, interactiveComponents;
	private Selector mouseModeSelector, positionPlayerSelector;
	
	/** Constructs a {@link MenuPanel}. After construction, the caller
	 * needs to call {@link MenuPanel#initialize()}. */
	public MenuPanel(ChessFrame container) {
		this.container = container;
	}
	
	public ChessFrame getChessFrame() {
		return container;
	}
	
	public void setInteractiveComponentsVisible(boolean visible) {
		for (Component c : interactiveComponents) 	c.setVisible(visible);
		doLayout();
	}
	
	public void setPositionComponentsVisible(boolean visible) {
		for (Component c : positionComponents) 		c.setVisible(visible);
		doLayout();
	}
	
	/** Initializes and displays a {@link MenuPanel}. */
	public void initialize() {
		addInteractiveComponents();
		addPositionComponents();
		// add base components last, they refer to other components in their
		// initialization process
		addBaseComponents();
		// set layout 
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		layout.setHgap(8);
		layout.setAlignOnBaseline(true);
		setLayout(layout);
		// add to container
		container.add(this);
	}
	
	private void addBaseComponents()
	{
		List<SelectorOption> gameModeOptions 
			= new LinkedList<SelectorOption>();
		gameModeOptions.add(new SelectorOption(
				SelectorOptionImages.INTERACTIVE_MODE) {	
			@Override
			public void choose() {
				container.getGame().startInteraction();
				MenuPanel.this.container.interactiveMode();
			}
		});
		gameModeOptions.add(new SelectorOption(
				SelectorOptionImages.POSITION_MODE) {	
			@Override
			public void choose() {
				container.getGame().stopInteraction();
				MenuPanel.this.container.positionMode();
			}
		});
		new Selector(this, gameModeOptions);
	}
	
	/** Adds the components for an interactive game. */
	public void addInteractiveComponents()
	{
		interactiveComponents = new LinkedList<Component>();
		// remember other components to remove them later
		LinkedList<Component> otherComponents = new LinkedList<Component>(); 
		otherComponents.addAll(Arrays.asList(getComponents()));
		// add interactive components to container
		createSelector(Player.WHITE);
		createSelector(Player.BLACK);
		// add interactive components to list
		interactiveComponents.addAll(Arrays.asList(getComponents()));
		interactiveComponents.removeAll(otherComponents);
	}
	
	/** Adds the components for editing positions. */
	public void addPositionComponents()
	{
		positionComponents = new LinkedList<Component>();
		// remember other components to remove them later
		LinkedList<Component> otherComponents = new LinkedList<Component>(); 
		otherComponents.addAll(Arrays.asList(getComponents()));
		// add position components to container
		// buttons for new/open/save
		Button btNewPosition = new Button("New");
		btNewPosition.setPreferredSize(new Dimension(48,48));
		btNewPosition.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				container.setGame(new Game());
			}
		});
		add(btNewPosition);
		Button btOpenPosition = new Button("Open");
		btOpenPosition.setPreferredSize(new Dimension(48,48));
		btOpenPosition.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileDialog fileDialog = new FileDialog(getChessFrame(), 
						"Open Chess Position", FileDialog.LOAD);
				fileDialog.setFilenameFilter(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						if (name.endsWith(".pos"))
							return true;
						return false;
					}
				});
				fileDialog.setVisible(true);
				String fileName = fileDialog.getDirectory() 
										+ File.separator + fileDialog.getFile();
				if (fileName != null)
				{
					container.setGame(new Game(fileName));
				}
			}
		});
		add(btOpenPosition);
		Button btSavePosition = new Button("Save");
		btSavePosition.setPreferredSize(new Dimension(48,48));
		btSavePosition.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileDialog fileDialog = new FileDialog(getChessFrame(), 
						"Save Chess Position", FileDialog.SAVE);
				fileDialog.setFilenameFilter(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						if (name.endsWith(".pos"))
							return true;
						return false;
					}
				});
				fileDialog.setVisible(true);
				String fileName = fileDialog.getDirectory() 
										+ File.separator + fileDialog.getFile();
				if (fileName != null)
				{
					container.getGame().save(fileName);
				}
			}
		});
		add(btSavePosition);
		// selector to select different operations for the mouse
		List<SelectorOption> positionMouseModeOptions 
			= new LinkedList<SelectorOption>();
		positionMouseModeOptions.add(new SelectorOption(
				SelectorOptionImages.MOVE_FIGURE) {	
			@Override
			public void choose() {
				getChessFrame().getPositionPanel().moveFigureOnMouse();
			}
		});
		positionMouseModeOptions.add(new SelectorOption(
				SelectorOptionImages.REMOVE_FIGURE) {	
			@Override
			public void choose() {
				getChessFrame().getPositionPanel().changeFigureOnMouse(null);
			}
		});
		positionMouseModeOptions.add(new SelectorOption(
				SelectorOptionImages.ADD_BLACK_PAWN) {	
			@Override
			public void choose() {
				getChessFrame().getPositionPanel().changeFigureOnMouse(
						new Pawn(Player.BLACK));
			}
		});
		positionMouseModeOptions.add(new SelectorOption(
				SelectorOptionImages.ADD_BLACK_BISHOP) {	
			@Override
			public void choose() {
				getChessFrame().getPositionPanel().changeFigureOnMouse(
						new Bishop(Player.BLACK));
			}
		});
		positionMouseModeOptions.add(new SelectorOption(
				SelectorOptionImages.ADD_BLACK_KNIGHT) {	
			@Override
			public void choose() {
				getChessFrame().getPositionPanel().changeFigureOnMouse(
						new Knight(Player.BLACK));
			}
		});
		positionMouseModeOptions.add(new SelectorOption(
				SelectorOptionImages.ADD_BLACK_ROOK) {	
			@Override
			public void choose() {
				getChessFrame().getPositionPanel().changeFigureOnMouse(
						new Rook(Player.BLACK));
			}
		});
		positionMouseModeOptions.add(new SelectorOption(
				SelectorOptionImages.ADD_BLACK_QUEEN) {	
			@Override
			public void choose() {
				getChessFrame().getPositionPanel().changeFigureOnMouse(
						new Queen(Player.BLACK));
			}
		});
		positionMouseModeOptions.add(new SelectorOption(
				SelectorOptionImages.ADD_BLACK_KING) {	
			@Override
			public void choose() {
				getChessFrame().getPositionPanel().changeFigureOnMouse(
						new King(Player.BLACK));
			}
		});
		positionMouseModeOptions.add(new SelectorOption(
				SelectorOptionImages.ADD_WHITE_PAWN) {	
			@Override
			public void choose() {
				getChessFrame().getPositionPanel().changeFigureOnMouse(
						new Pawn(Player.WHITE));
			}
		});
		positionMouseModeOptions.add(new SelectorOption(
				SelectorOptionImages.ADD_WHITE_BISHOP) {	
			@Override
			public void choose() {
				getChessFrame().getPositionPanel().changeFigureOnMouse(
						new Bishop(Player.WHITE));
			}
		});
		positionMouseModeOptions.add(new SelectorOption(
				SelectorOptionImages.ADD_WHITE_KNIGHT) {	
			@Override
			public void choose() {
				getChessFrame().getPositionPanel().changeFigureOnMouse(
						new Knight(Player.WHITE));
			}
		});
		positionMouseModeOptions.add(new SelectorOption(
				SelectorOptionImages.ADD_WHITE_ROOK) {	
			@Override
			public void choose() {
				getChessFrame().getPositionPanel().changeFigureOnMouse(
						new Rook(Player.WHITE));
			}
		});
		positionMouseModeOptions.add(new SelectorOption(
				SelectorOptionImages.ADD_WHITE_QUEEN) {	
			@Override
			public void choose() {
				getChessFrame().getPositionPanel().changeFigureOnMouse(
						new Queen(Player.WHITE));
			}
		});
		positionMouseModeOptions.add(new SelectorOption(
				SelectorOptionImages.ADD_WHITE_KING) {	
			@Override
			public void choose() {
				getChessFrame().getPositionPanel().changeFigureOnMouse(
						new King(Player.WHITE));
			}
		});
		mouseModeSelector = new Selector(this, positionMouseModeOptions);
		// selector to select player at move
		List<SelectorOption> positionPlayerOptions 
			= new LinkedList<SelectorOption>(); 
		positionPlayerOptions.add(new SelectorOption(
				SelectorOptionImages.WHITE_AT_MOVE) {
			@Override
			public void choose() {
				container.getGame().setActivePlayer(Player.WHITE);
			}
		});
		positionPlayerOptions.add(new SelectorOption(
				SelectorOptionImages.BLACK_AT_MOVE) {
			@Override
			public void choose() {
				container.getGame().setActivePlayer(Player.BLACK);
			}
		});
		positionPlayerSelector = new Selector(this, positionPlayerOptions);
		// add position components to list
		positionComponents.addAll(Arrays.asList(getComponents()));
		positionComponents.removeAll(otherComponents);
	}
	
	public void resetMouseModeSelector()
	{
		mouseModeSelector.reset();
	}
	
	public void resetPositionPlayerSelector()
	{
		// change default option if black at move
		if (container.getGame().getActivePlayer() == Player.BLACK)
			Collections.reverse(positionPlayerSelector.getOptions());
		// reset selector to default option
		positionPlayerSelector.reset();
	}
	
	/** Creates and initializes a {@link Selector} for the player mode for a 
	 * {@link Player}.*/
	private Button createSelector(final Player player) {
		Button result;
		List<SelectorOption> selectorOptions 
			= new LinkedList<SelectorOption>();
		SelectorOption human = new SelectorOption(
				player == Player.BLACK? 
						SelectorOptionImages.BLACK_HUMAN:
						SelectorOptionImages.WHITE_HUMAN) {
			@Override
			public void choose() {
				player.playHuman();
			}
		};
		selectorOptions.add(human);
		SelectorOption automatonEasiest = new SelectorOption(
				player == Player.BLACK? 
						SelectorOptionImages.BLACK_AUTOMATON_EASIEST:
						SelectorOptionImages.WHITE_AUTOMATON_EASIEST) {
			@Override
			public void choose() {
				player.playAutomaton(3);
			}
		};
		selectorOptions.add(automatonEasiest);
		SelectorOption automatonEasy = new SelectorOption(
				player == Player.BLACK? 
						SelectorOptionImages.BLACK_AUTOMATON_EASY:
						SelectorOptionImages.WHITE_AUTOMATON_EASY) {
			@Override
			public void choose() {
				player.playAutomaton(4);
			}
		};
		selectorOptions.add(automatonEasy);
		SelectorOption automatonMedium = new SelectorOption(
				player == Player.BLACK? 
						SelectorOptionImages.BLACK_AUTOMATON_MEDIUM:
						SelectorOptionImages.WHITE_AUTOMATON_MEDIUM) {
			@Override
			public void choose() {
				player.playAutomaton(5);
			}
		};
		selectorOptions.add(automatonMedium);
		SelectorOption automatonHard = new SelectorOption(
				player == Player.BLACK? 
						SelectorOptionImages.BLACK_AUTOMATON_HARD:
						SelectorOptionImages.WHITE_AUTOMATON_HARD) {
			@Override
			public void choose() {
				player.playAutomaton(6);
			}
		};
		selectorOptions.add(automatonHard);
		SelectorOption automatonHardest = new SelectorOption(
				player == Player.BLACK? 
						SelectorOptionImages.BLACK_AUTOMATON_HARDEST:
						SelectorOptionImages.WHITE_AUTOMATON_HARDEST) {
			@Override
			public void choose() {
				player.playAutomaton(7);
			}
		};
		selectorOptions.add(automatonHardest);
		result = new Selector(this, selectorOptions);
		return result;
	}
}
