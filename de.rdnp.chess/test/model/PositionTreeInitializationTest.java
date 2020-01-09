package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import game.Player;

import org.junit.Test;


public class PositionTreeInitializationTest {	
	@Test
	public void buildTree0()
	{
		Position start = TestPositions.createStartPosition();
		PositionTree subject = new PositionTree(Player.WHITE, start, 
				start.getFigureAt(new ChessLocation(3, 1)), 
				TestConstants.TEST_POSITION_TREE_DEPTH);
		assertTrue(subject.size() > 0);
		assertEquals(subject.getTargets(start).size(), 2);
	}
	
	@Test
	public void buildTree2()
	{
		Position testPosition = TestPositions.createPosition(
				TestPositionConstants.TEST_POSITION_2);
		PositionTree subject = new PositionTree(Player.BLACK, testPosition, 
				testPosition.getFigureAt(new ChessLocation(4, 3)), 
				TestConstants.TEST_POSITION_TREE_DEPTH);
		assertTrue(subject.size() > 0);
		assertEquals(subject.getTargets(testPosition).size(), 0);
	}
}
