package model;

import java.util.Collection;
import java.util.ArrayList;

/** 
 * Represents the result of a position computation performed by a 
 * {@link PositionComputer}.
 * @author Richard Pohl */
public class PositionComputationResult {

	private Collection<Position> positions;
	private int maximumAdvantageN;
	
	/**
	 * Constructs a {@link PositionComputationResult}.
	 * */
	public PositionComputationResult() {
		positions = new ArrayList<Position>();
	}
	
	/**
	 * @return The maximum advantage after N legal plies were executed from
	 * the start position (N is the computation limit of the 
	 * {@link PositionComputer}, {@link PositionComputer#quality}).
	 * */
	public int getMaximumAdvantageN() {
		return maximumAdvantageN;
	}
	
	/**
	 * Sets the maximum advantage after N legal plies were executed from
	 * the start position. (N is the computation limit of the 
	 * {@link PositionComputer}, {@link PositionComputer#quality}).
	 * Called by {@link PositionComputer} instances.
	 * @param maximumAdvantageN - the new advantage.
	 * */
	void setMaximumAdvantageN(int maximumAdvantageN) {
		this.maximumAdvantageN = maximumAdvantageN;
	}
	
	/**
	 * @return A {@link Collection} with {@link Position} instances found in a 
	 * position computation executed by a {@link PositionComputer} instance.
	 * */
	public Collection<Position> getPositions() {
		return positions;
	}
	
	/**
	 * Sets the {@link Collection} with {@link Position} instances found in a 
	 * position computation executed by a {@link PositionComputer} instance.
	 * Called by {@link PositionComputer} instances.
	 * @param positions - the new Colletion of Position instances.
	 * */
	void setPositions(Collection<Position> positions) {
		this.positions = positions;
	}
}
