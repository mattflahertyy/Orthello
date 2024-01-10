public abstract class Position {
	
	private char piece;
	public static final char EMPTY = ' ';
	public static final char BLACK = 'B';
	public static final char WHITE = 'W';	
	
	//getters and setters for all fields
	
    public static char getBlack() {
		return BLACK;
	}

	public static char getWhite() {
		return WHITE;
	}

	public static char getEmpty() {
		return EMPTY;
	}

	public char getPiece() {
		return piece;
	}

	public void setPiece(char piece) {
		this.piece = piece;
	}
	
	//abstract method
	public abstract boolean canPlay();	
}
