package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class PawnTest {

	private Position testTransform;
	private Position testNoTransform;
	private Position testCriticalTransform;
	
	@Before
	public void setUp() {
		testTransform = TestPositions.createPosition(
				TestPositionConstants.TEST_POSITION_7);
		testNoTransform = TestPositions.createPosition(
				TestPositionConstants.TEST_POSITION_8);
		testCriticalTransform = TestPositions.createPosition(
				TestPositionConstants.TEST_POSITION_9);
	}
	
	@Test
	public void transform7(){
		Pawn subject = (Pawn) testTransform.getFigureAt(new ChessLocation(7,1));
		Position[] onePlies = subject.computeOnePly(testTransform);
		int i;
		for (i = 0; onePlies[i] != null; i++)
			System.out.println(onePlies[i]);
		assertEquals(8, i);
	}
	
	@Test
	public void transform8(){
		Pawn subject = (Pawn) testNoTransform
			.getFigureAt(new ChessLocation(7,2));
		Position[] onePlies = subject.computeOnePly(testNoTransform);
		int i;
		for (i = 0; onePlies[i] != null; i++)
			System.out.println(onePlies[i]);
		assertEquals(2, i);
		
	}
	
	@Test
	public void transform9(){
		Pawn subject = (Pawn) testCriticalTransform
			.getFigureAt(new ChessLocation(4,4));
		Position[] onePlies = subject.computeOnePly(testCriticalTransform);
		int i;
		for (i = 0; onePlies[i] != null; i++)
			System.out.println(onePlies[i]);
		assertEquals(0, i);
		
	}
	
}
