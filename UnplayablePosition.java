public class UnplayablePosition extends Position {
	
	public static final char UNPLAYABLE = '*';
	
	//constructor sets piece to unplayable
	public UnplayablePosition() {
		setPiece(UNPLAYABLE);
	}
		
	//returns false for canPlay
	@Override
	public boolean canPlay() {
		return false;
	}

	//getter for piece
	public static char getUnplayable() {
		return UNPLAYABLE;
	}	
}
