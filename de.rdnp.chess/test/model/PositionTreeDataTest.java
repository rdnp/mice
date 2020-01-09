/**
 * 
 */
package model;


import static org.junit.Assert.*;

import game.Game;
import game.Player;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Richard Pohl
 *
 */
public class PositionTreeDataTest {

	private PositionTree subject;
	private Position startPosition;
	private Position testPosition1;
	private Position testPosition2;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		new Game();
		startPosition = TestPositions.createStartPosition();
		testPosition1 = TestPositions.createPosition(
				TestPositionConstants.TEST_POSITION_1);
		testPosition2 = TestPositions.createPosition(
				TestPositionConstants.TEST_POSITION_2);
		subject = new PositionTree(Player.WHITE, startPosition, 
				startPosition.getFigureAt(new ChessLocation(3, 1)), 
				TestConstants.TEST_POSITION_TREE_DEPTH); 
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	
	@Test
	public void addPosition() {
		subject.addNode(testPosition1, Player.WHITE, 7);
		assertTrue(subject.getPlayer(testPosition1) == Player.WHITE);
		assertTrue(subject.getReachability(testPosition1) == 7);
	}
	
	@Test
	public void addSource() {
		subject.addNode(testPosition1, Player.WHITE, 7);
		subject.setSource(testPosition1, testPosition2);
		assertEquals(testPosition2, subject.getSource(testPosition1));
	} 
	
	@Test
	public void addTarget() {
		subject.addNode(testPosition1, Player.WHITE, 7);
		subject.addTarget(testPosition1, testPosition2);
		assertTrue(subject.getTargets(testPosition1).contains(testPosition2));
		assertTrue(subject.getTargets(testPosition1).size()==1);
	} 
	
	@Test
	public void addTargets() {
		subject.addNode(testPosition1, Player.WHITE, 7);
		subject.addTarget(testPosition1, testPosition2);
		subject.addTarget(testPosition1, startPosition);
		assertTrue(subject.getTargets(testPosition1).contains(testPosition2));
		assertTrue(subject.getTargets(testPosition1).contains(startPosition));
		assertTrue(subject.getTargets(testPosition1).size()==2);
	} 
}
