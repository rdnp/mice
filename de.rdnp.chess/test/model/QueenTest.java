package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueenTest {

	@Test 
	public void locationComputationTest1() {
		Position position = TestPositions.createPosition(
					TestPositionConstants.TEST_POSITION_1);
		Queen subject = (Queen) position.getFigureAt(new ChessLocation(3, 7));
		ChessLocation[] onePlyLocations 
			= subject.computeOnePlyLocations(position);
		int i;
		for (i = 0; onePlyLocations[i] != null; i++)
			System.out.println(onePlyLocations[i]);
		assertEquals(8, i);
	}
	
	@Test 
	public void positionComputationTest1() {
		Position position = TestPositions.createPosition(
					TestPositionConstants.TEST_POSITION_1);
		Queen subject = (Queen) position.getFigureAt(new ChessLocation(3, 7));
		Position [] onePlies = subject.computeOnePly(position);
		int i;
		for (i = 0; onePlies[i] != null; i++)
			System.out.println(onePlies[i]);
		assertEquals(8, i);
	}

//	@Test 
//	public void collisionTest5()
//	{
//		Position position = TestPositions.createPosition(
//					TestPositionConstants.TEST_POSITION_6);
//		System.out.println(" Collision test in position "+position);
//		Queen subject = (Queen) position.getFigureAt(new ChessLocation(3, 0));
//
//		System.out.println(subject.getOwner()+" collides with "
//				+position.getFigureAt(new ChessLocation(2, 0)).getOwner());
//		assertTrue(subject.isCollidingWithOwn(position, 2,0));
//	}
	
	@Test 
	public void locationComputationTest5() {
		Position position = TestPositions.createPosition(
					TestPositionConstants.TEST_POSITION_6);
		Queen subject = (Queen) position.getFigureAt(new ChessLocation(3, 0));
		ChessLocation[] onePlyLocations 
			= subject.computeOnePlyLocations(position);
		int i;
		for (i = 0; onePlyLocations[i] != null; i++)
			System.out.println(onePlyLocations[i]);
		assertEquals(4, i);
	}
	
	@Test 
	public void positionComputationTest5() {
		Position position = TestPositions.createPosition(
					TestPositionConstants.TEST_POSITION_6);
		Queen subject = (Queen) position.getFigureAt(new ChessLocation(3, 0));
		Position [] onePlies = subject.computeOnePly(position);
		int i;
		for (i = 0; onePlies[i] != null; i++)
			System.out.println(onePlies[i]);
		assertEquals(4, i);
	}
}
