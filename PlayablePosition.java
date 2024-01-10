public class PlayablePosition extends Position {
		
	//base constructor for new board that sets pieces empty
	public PlayablePosition() {
		setPiece(getEmpty());
	}
	
	//sets pieces totheir respective colors
	public PlayablePosition(char piece) {
		if (piece == getBlack()) {
			setPiece(getBlack());
		}
		else if (piece == getWhite()){
			setPiece(getWhite());
		}
		else if (piece == UnplayablePosition.getUnplayable()){
			setPiece(UnplayablePosition.getUnplayable());
		}
		else {
			setPiece(getEmpty());
		}
	}
	
	//returns true for can play
	@Override
	public boolean canPlay() {
		return true;
	}
}
