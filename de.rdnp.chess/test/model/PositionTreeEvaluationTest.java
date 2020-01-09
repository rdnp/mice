package model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import game.Player;


public class PositionTreeEvaluationTest {

	/**
	 * Tests, if a simple chess situation can be avoided by taking the best 
	 * countermeasure.
	 * */
	@Test
	public void evaluation3()
	{
		Position testPosition = TestPositions.createPosition(
				TestPositionConstants.TEST_POSITION_3);
		PositionTree subject = new PositionTree(Player.BLACK, testPosition, 
				testPosition.getFigureAt(new ChessLocation(4, 6)), 
				TestConstants.TEST_POSITION_TREE_DEPTH);
		int maximumAdvantage = Integer.MIN_VALUE;
		Position bestResult = null;
		for (Position result: subject.getTargets(testPosition))
		{
			if (subject.getEvaluation(result, Player.BLACK) > maximumAdvantage)
			{
				maximumAdvantage = subject.getEvaluation(result, Player.BLACK);
				bestResult = result;
			}
		}
		assertTrue(
				bestResult.getFigureLocation(testPosition.getFigureAt(
						new ChessLocation(4, 6))).equals(
								new ChessLocation(4, 5))
						);
	}
	
	/**
	 * Tests, if the evaluation of several moves with different qualities leads
	 * to the correct decision (the queen in Position 4 should not attempt to
	 * take the rook).
	 * */
	@Test
	public void evaluation4()
	{
		Position testPosition = TestPositions.createPosition(
				TestPositionConstants.TEST_POSITION_4);
		PositionTree subject = new PositionTree(Player.WHITE, testPosition, 
				testPosition.getFigureAt(new ChessLocation(3, 3)), 
				TestConstants.TEST_POSITION_TREE_DEPTH);
		int maximumAdvantage = Integer.MIN_VALUE;
		Position bestResult = null;
		for (Position result: subject.getTargets(testPosition))
		{
			System.out.println(result.hashCode()+" evaluated to "
								+subject.getEvaluation(result, Player.WHITE));
			if (subject.getEvaluation(result, Player.WHITE) > maximumAdvantage)
			{
				maximumAdvantage = subject.getEvaluation(result, Player.WHITE);
				bestResult = result;
			}
		}
		assertTrue(
				!bestResult.getFigureLocation(testPosition.getFigureAt(
						new ChessLocation(3, 3))).equals(
								new ChessLocation(0, 3))
						);
	}
	
	/**
	 * Tests, if the evaluation of several moves with different qualities leads
	 * to the correct decision (the queen in Position 4 should not attempt to
	 * take the rook).
	 * */
	@Test
	public void evaluation5()
	{
		Position testPosition = TestPositions.createPosition(
				TestPositionConstants.TEST_POSITION_5);
		PositionTree subject = new PositionTree(Player.BLACK, testPosition, 
				testPosition.getFigureAt(new ChessLocation(1, 5)), 
				TestConstants.TEST_POSITION_TREE_DEPTH);
		int maximumAdvantage = Integer.MIN_VALUE;
		Position bestResult = null;
		for (Position result: subject.getTargets(testPosition))
		{
			System.out.println(result.hashCode()+" evaluated to "
								+subject.getEvaluation(result, Player.BLACK));
			if (subject.getEvaluation(result, Player.BLACK) > maximumAdvantage)
			{
				maximumAdvantage = subject.getEvaluation(result, Player.BLACK);
				bestResult = result;
			}
		}
		assertTrue(
				!bestResult.getFigureLocation(testPosition.getFigureAt(
						new ChessLocation(1, 5))).equals(
								new ChessLocation(3, 3))
						);
	}
}
