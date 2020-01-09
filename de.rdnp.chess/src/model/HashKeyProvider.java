package model;

import game.Player;

import java.util.Random;

/**
 * Provides random keys used for hashing. 
 * (see {@link Position#computeZobristHash}). Is usually owned by a 
 * {@link Player} and provides hash keys that depend on the state of a location
 * and the Player.
 * 
 * Remark: This is actually a GOF visitor.
 * @author Richard Pohl
 * */
public class HashKeyProvider {
	
	// constants for the factors that modify the access location in the hash
	// key array
	private static final int KING_OFFSET 	= 320;
	private static final int QUEEN_OFFSET 	= 256;
	private static final int ROOK_OFFSET 	= 192;
	private static final int KNIGHT_OFFSET 	= 128;
	private static final int BISHOP_OFFSET 	= 64;
	private static final int PAWN_OFFSET	= 0;
	
	// the hash keys are pre-computed for fast access
	private final int[] hashKeys;
	
	public HashKeyProvider(int seed) {
		hashKeys = new int[384];
		Random random = new Random(seed);
		for (int i = 0; i < hashKeys.length; i++)
			hashKeys[i] = random.nextInt();
	}
	
	/**
	 * @return A hash key for the constellation defined by the parameters.
	 * */
	public final int getHashKey(int location, King king)
	{
		int hashIndex = location + KING_OFFSET;
		return hashKeys[hashIndex]; 
	}

	/**
	 * @return A hash key for the constellation defined by the parameters.
	 * */
	public final int getHashKey(int location, Queen queen)
	{
		int hashIndex = location + QUEEN_OFFSET;
		return hashKeys[hashIndex]; 
	}
	
	/**
	 * @return A hash key for the constellation defined by the parameters.
	 * */
	public final int getHashKey(int location, Rook rook)
	{
		int hashIndex = location + ROOK_OFFSET;
		return hashKeys[hashIndex]; 
	}

	/**
	 * @return A hash key for the constellation defined by the parameters.
	 * */
	public final int getHashKey(int location, Knight knight)
	{
		int hashIndex = location + KNIGHT_OFFSET;
		return hashKeys[hashIndex]; 
	}

	/**
	 * @return A hash key for the constellation defined by the parameters.
	 * */
	public final int getHashKey(int location, Bishop bishop)
	{
		int hashIndex = location + BISHOP_OFFSET;
		return hashKeys[hashIndex]; 
	}

	/**
	 * @return A hash key for the constellation defined by the parameters.
	 * */
	public final int getHashKey(int location, Pawn pawn)
	{
		int hashIndex = location + PAWN_OFFSET;
		return hashKeys[hashIndex]; 
	}
}
