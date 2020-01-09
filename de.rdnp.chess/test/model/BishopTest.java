package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class BishopTest {

	@Test 
	public void locationComputationTest1() {
		Position position = TestPositions.createPosition(
					TestPositionConstants.TEST_POSITION_1);
		Bishop subject = (Bishop) position.getFigureAt(new ChessLocation(5, 7));
		ChessLocation[] onePlyLocations 
			= subject.computeBishopOnePlyLocations(position);
		int i;
		for (i = 0; onePlyLocations[i] != null; i++)
			System.out.println(onePlyLocations[i]);
		assertEquals(i, 4);
	}
	
	@Test 
	public void positionComputationTest1() {
		Position position = TestPositions.createPosition(
					TestPositionConstants.TEST_POSITION_1);
		Bishop subject = (Bishop) position.getFigureAt(new ChessLocation(5, 7));
		Position [] onePlies = subject.computeOnePly(position);
		int i;
		for (i = 0; onePlies[i] != null; i++)
			System.out.println(onePlies[i]);
		assertEquals(i, 4);
	}
	
}
