package main;

import view.ChessFrame;

/** @author Richard Pohl*/
public final class ChessApplication {
	private static final ChessApplication instance = new ChessApplication();
	
	private int maxThreadNumber; 
	
	private ChessApplication() {}
	
	public static final ChessApplication getInstance() {
		return instance;
	}
	
	public int getMaxThreadNumber() {
		return maxThreadNumber;
	}
	
	private void start()
	{
		ChessFrame chessFrame = new ChessFrame();
		chessFrame.display();
	}
	
	public static void main(String [] args)
	{
		if (args.length > 0)
			instance.maxThreadNumber = Integer.parseInt(args[0]);
		else
			instance.maxThreadNumber = 2;
		instance.start();		
	}
}
