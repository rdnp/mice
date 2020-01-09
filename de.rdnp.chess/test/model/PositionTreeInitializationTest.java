package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import game.Game;
import game.Player;

import org.junit.Test;


public class PositionTreeInitializationTest {	
	@Test
	public void buildTree0()
	{
		Position start = TestPositions.createStartPosition();
		new Game().setPosition(start);
		PositionTree subject = new PositionTree(Player.WHITE, start, 
				start.getFigureAt(new ChessLocation(3, 1)), 
				TestConstants.TEST_POSITION_TREE_DEPTH);
		assertTrue(subject.size() > 0);
		assertEquals(2, subject.getTargets(start).size());
	}
	
	@Test
	public void buildTree2()
	{
		Position testPosition = TestPositions.createPosition(
				TestPositionConstants.TEST_POSITION_2);
		new Game().setPosition(testPosition);
		PositionTree subject = new PositionTree(Player.BLACK, testPosition, 
				testPosition.getFigureAt(new ChessLocation(4, 3)), 
				TestConstants.TEST_POSITION_TREE_DEPTH);
		assertTrue(subject.size() > 0);
		assertEquals(0, subject.getTargets(testPosition).size());
	}
}
