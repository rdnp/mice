package model;

import io.GameStateParser;

import java.io.StringBufferInputStream;

@SuppressWarnings("deprecation")
public class TestPositions {
	public static Position createPosition(String positionString)
	{
		return new GameStateParser(new StringBufferInputStream(positionString
		)).parse().getCurrentPosition();
	}
	
	public static Position createStartPosition()
	{
		return new Position();
	}
}
