package model;

public class TestPositionConstants {
	public static final String TEST_POSITION_1 = 
		"- figure locations\n" +
		"8  bR bN bB bQ bK bB bN bR \n" +
		"7  bP bP                   \n" +                   
		"6                          \n"+
		"5        bP bP bP bP wP bP \n" +
		"4        wP wP wP wP wP    \n"+
		"3                          \n"+
		"2  wP wP                   \n"+
		"1  wR wN wB wQ wK wB wN wR \n"+
		"--  A  B  C  D  E  F  G  H"	;
	
	public static final String TEST_POSITION_2 = 
		"- figure locations\n" +
		"8  bR bN bB bQ bK bB bN bR\n"+ 
		"7  bP bP bP bP    wQ      \n"+
		"6                         \n"+
		"5              bP    bP bP\n"+
		"4        wB    wP         \n"+
		"3                         \n"+
		"2  wP wP wP wP    wP wP wP\n"+
		"1  wR wN wB    wK    wN wR\n"+
		"--  A  B  C  D  E  F  G  H";
	
	/** Checkmate threat */
	public static final String TEST_POSITION_3 = 
		"- figure locations\n" +
		"8  bR bN bB bQ bK bB bN bR \n" +
		"7  bP bP bP bP bP bP wP bP \n" +                   
		"6                          \n"+
		"5           wB    wQ       \n" +
		"4                          \n"+
		"3                          \n"+
		"2  wP wP wP wP wP wP wP    \n"+
		"1  wR wN       wK wB wN wR \n"+
		"--  A  B  C  D  E  F  G  H"	;
	
	/** wQ can take, but should not. */
	public static final String TEST_POSITION_4 = 
		"- figure locations\n" +
		"8     bN bB    bK bB bN bR \n" +
		"7     bP bP bB bP    wP bP \n" +                   
		"6                          \n"+
		"5           bP    wP       \n" +
		"4  bR       wQ       bP wP \n"+
		"3                          \n"+
		"2  wP    wP    wP    wP    \n"+
		"1  wR wN       wK wB wN wR \n"+
		"--  A  B  C  D  E  F  G  H"	;
	
	/** bQ can take, but should not. Bug observed in game. */
	public static final String TEST_POSITION_5 = 
		"- figure locations\n" +
		"8  bR bN bB    bK bB bN bR \n" +
		"7     bP bP    bP    bP bP \n" +                   
		"6     bQ                   \n"+
		"5           bP    wP       \n" +
		"4     bP    wP       bP wP \n"+
		"3                          \n"+
		"2  wP    wP    wP    wB    \n"+
		"1  wR wN    wQ wK wB wN wR \n"+
		"--  A  B  C  D  E  F  G  H"	;
	
	/** Position after bug in TEST_POSITION_5 occured. */
	public static final String TEST_POSITION_6 = 
		"- figure locations\n" +
		"8  bR bN bB    bK bB bN bR \n" +
		"7     bP bP    bP    bP bP \n" +                   
		"6                          \n"+
		"5           bP    wP       \n" +
		"4     bP    bQ       bP wP \n"+
		"3                          \n"+
		"2  wP    wP    wP    wB    \n"+
		"1  wR wN    wQ wK wB wN wR \n"+
		"--  A  B  C  D  E  F  G  H"	;
	
	/** Position where a black Pawn can be transformed. */
	public static final String TEST_POSITION_7 = 
		"- figure locations\n" +
		"8  bR bN bB bQ bK bB bN bR \n" +
		"7  bP bP bP bP bP bP wP bP \n" +                   
		"6                          \n"+
		"5                          \n" +
		"4                          \n"+
		"3                          \n"+
		"2  wP wP wP wP wP wP wP bP \n"+
		"1  wR wN wB wQ wK wB wN    \n"+
		"--  A  B  C  D  E  F  G  H"	;
	
	/** Position where the black Pawn cannot be transformed. */
	public static final String TEST_POSITION_8 = 
		"- figure locations\n" +
		"8  bR bN bB bQ bK bB bN bR \n" +
		"7  bP bP bP bP bP bP wP    \n" +                   
		"6                          \n"+
		"5                          \n" +
		"4                          \n"+
		"3                       bP \n"+
		"2  wP wP wP wP wP wP wP    \n"+
		"1  wR wN wB wQ wK wB wN wR \n"+
		"--  A  B  C  D  E  F  G  H"	;
	
	public static final String TEST_POSITION_9 =
		"- figure locations\n" +
		"8  bR bN bB bQ bK bB       \n"+
		"7     bP             bP    \n"+
		"6              bP bN bR    \n"+
		"5  bP       bP wP bP    bP \n"+
		"4  wP    bP wP    wP wP wP \n"+
		"3        wN                \n"+
		"2     wP wP          wB    \n"+
		"1  wR    wB wQ wK    wN wR \n"+
		"--  A  B  C  D  E  F  G  H";
}
