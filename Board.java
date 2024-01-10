import java.util.Scanner;

public class Board {
	/////
	
	private final int BOARD_SIDE = 8;
	private final char MARKER = 'x';
	private final char UNPLAYABLE = '*';
	public boolean hasForfeited, hasSaved;
	
	protected Position[][] board = new Position[BOARD_SIDE][BOARD_SIDE];
	
	Scanner scanner = new Scanner(System.in);
	
	//filler for board in game class if load fails
	public Board() {
		
	}
	
	//constructor for setting positions
	public Board(String positions) {
		int counter = 0;
		
		for (int i = 0; i < BOARD_SIDE; i++)
			for (int j = 0; j < BOARD_SIDE; j++) {
				board[i][j] = new PlayablePosition(positions.charAt(counter));
				counter++;
			}
	}
	
	//creates new empty board with starting positions and unplayable positions
	public void createNewBoard(int start_position) {
		
		for (int i = 0; i < BOARD_SIDE; i++)
			for (int j = 0; j < BOARD_SIDE; j++)
				board[i][j] = new PlayablePosition();
		
		board[0][3] = new UnplayablePosition();
		board[0][4] = new UnplayablePosition();
		
		if (start_position == 1) {
			for (int i = 2; i < 4; i++)
				for (int j = 2; j < 4; j++)
					board[i][j] = new PlayablePosition('W');
			for (int i = 4; i < 6; i++)
				for (int j = 4; j < 6; j++)
					board[i][j] = new PlayablePosition('W');
			for (int i = 2; i < 4; i++)
				for (int j = 4; j < 6; j++)
					board[i][j] = new PlayablePosition('B');
			for (int i = 4; i < 6; i++)
				for (int j = 2; j < 4; j++)
					board[i][j] = new PlayablePosition('B');
		}
		else if (start_position == 2) {
			board[2][2] = new PlayablePosition('W');
			board[2][3] = new PlayablePosition('B');
			board[3][2] = new PlayablePosition('B');
			board[3][3] = new PlayablePosition('W');
		}
		else if (start_position == 3) {
			board[2][4] = new PlayablePosition('W');
			board[2][5] = new PlayablePosition('B');
			board[3][4] = new PlayablePosition('B');
			board[3][5] = new PlayablePosition('W');
		}
		else if (start_position == 4) {
			board[4][2] = new PlayablePosition('W');
			board[4][3] = new PlayablePosition('B');
			board[5][2] = new PlayablePosition('B');
			board[5][3] = new PlayablePosition('W');
		}
		else if (start_position == 5) {
			board[4][4] = new PlayablePosition('W');
			board[4][5] = new PlayablePosition('B');
			board[5][4] = new PlayablePosition('B');
			board[5][5] = new PlayablePosition('W');
		}
	}
	
	//allows player to take turn, calls method to print markers for possible position choices
	//and draws the board. displays menu for user
	public void takeTurn(Player current) {
	
		int player_choice;
		
		placeMarkers(current);
		drawBoard();
		
		System.out.println(current.getName() + " (" + current.getColor() + "), please select an option.");
		System.out.println("1. Make move");
		System.out.println("2. Forfeit");
		System.out.println("3. Save Game");
		
	
		do {
			while (!scanner.hasNextInt()) {
				System.out.println("Error: Please enter a valid integer.");
				scanner.nextLine();
			}
			
			player_choice = Integer.parseInt(scanner.nextLine());
			
			if (player_choice > 3 || player_choice < 1)
				System.out.println("Error: Please enter a number between 1-3!");
			
			
		} while (player_choice < 1 || player_choice > 3);
		
			
		if (player_choice == 1)
			makeMove(current);
		else if (player_choice == 2) {
			hasForfeited = true;
			System.out.println(current.getName() + " has forfeited the match. Game over.");
		}
		else if (player_choice == 3) {
			hasSaved = true;
			System.out.println("Saving the game...");
		}
	}
	
	//makes the move, player inputs row and column then updates position on board
	public void makeMove(Player current) {
		int pieces;
		boolean validPosition = false;
		int row, column;
		String rowStr, colStr;
		
		while (!validPosition) {
			System.out.println(current.getName() + ", to select the row and column you would like to pick,");
			System.out.println("Please enter a letter from A-H. (must select a position with 'x')");
			rowStr = scanner.nextLine();
			rowStr = rowStr.toUpperCase();
			
			while (!(rowStr.equals("A") || rowStr.equals("B") || rowStr.equals("C") || rowStr.equals("D") || rowStr.equals("E") || 
					rowStr.equals("F") || rowStr.equals("G") || rowStr.equals("H"))) {
				System.out.println("Error: Please retry and enter a letter from A to H!");
				rowStr = scanner.nextLine();
				rowStr = rowStr.toUpperCase();
			}
			
			System.out.println("Please enter a number from 1 to 8.");
			
			while(!scanner.hasNextInt()) {
				System.out.println("Error: Please enter a valid integer!");
			    scanner.nextLine();
			    
			}
			
			colStr = scanner.nextLine();
			
			while (Integer.parseInt(colStr) < 1 || Integer.parseInt(colStr) > 8) {
				System.out.println("Error: Please enter a number between 1 and 8!");
				colStr = scanner.nextLine();
			}
			
			
			if (rowStr.equals("A"))
				row = 0;
			else if (rowStr.equals("B"))
				row = 1;
			else if (rowStr.equals("C"))
				row = 2;
			else if (rowStr.equals("D"))
				row = 3;
			else if (rowStr.equals("E"))
				row = 4;
			else if (rowStr.equals("F"))
				row = 5;
			else if (rowStr.equals("G"))
				row = 6;
			else if (rowStr.equals("H"))
				row = 7;
			else
				row = 0;
			
			column = Integer.parseInt(colStr) - 1;
					
			validPosition = isValidPosition(row, column);

			if (validPosition) {
				board[row][column] = new PlayablePosition(current.getColor());
				pieces = convertPieces(current, row, column);
				System.out.println(pieces + " have been flipped!");
			}
			else
				System.out.println("The position you selected was invalid. Please choose a position on the board with an 'x'!");	
		}
	}
	
	//cnverts pieces using check methods
	public int convertPieces(Player current, int row, int col) {

		int numOfPieces = 0;
		int starterRow, starterCol;
		starterRow = row;
		starterCol = col;
		
		if (checkUp(current, row, col)) {
			while (board[row-1][col].getPiece() == findOpposingPiece(current)) {
				board[row-1][col].setPiece(current.getColor());
				numOfPieces++;
				row--;
			}
			row = starterRow;
			col = starterCol;
		}
		
		if (checkDown(current, row, col)) {
			while (board[row+1][col].getPiece() == findOpposingPiece(current)) {
				board[row+1][col].setPiece(current.getColor());
				numOfPieces++;
				row++;
			}		
			row = starterRow;
			col = starterCol;
		}
		
		if (checkLeft(current, row, col)) {
			while (board[row][col-1].getPiece() == findOpposingPiece(current)) {
				board[row][col-1].setPiece(current.getColor());
				numOfPieces++;
				col--;
			}
			row = starterRow;
			col = starterCol;
		}
		
		
		if (checkRight(current, row, col)) {
			while (board[row][col+1].getPiece() == findOpposingPiece(current)) {
				board[row][col+1].setPiece(current.getColor());
				numOfPieces++;
				col++;
			}
			row = starterRow;
			col = starterCol;
		}
		
		if (checkDiagUpLeft(current, row, col)) {
			while (board[row-1][col-1].getPiece() == findOpposingPiece(current)) {
				board[row-1][col-1].setPiece(current.getColor());
				numOfPieces++;
				row--;
				col--;
			}
			row = starterRow;
			col = starterCol;
		}
		
		if (checkDiagDownLeft(current, row, col)) {
			while (board[row+1][col-1].getPiece() == findOpposingPiece(current)) {
				board[row+1][col-1].setPiece(current.getColor());
				numOfPieces++;
				row++;
				col--;
			}
			row = starterRow;
			col = starterCol;
		}
		
		if (checkDiagUpRight(current, row, col)) {
			while (board[row-1][col+1].getPiece() == findOpposingPiece(current)) {
				board[row-1][col+1].setPiece(current.getColor());
				numOfPieces++;
				row--;
				col++;
			}
			row = starterRow;
			col = starterCol;
		}
		
		if (checkDiagDownRight(current, row, col)) {
			while (board[row+1][col+1].getPiece() == findOpposingPiece(current)) {
				board[row+1][col+1].setPiece(current.getColor());
				numOfPieces++;
				row++;
				col++;
			}
		}	
		
		return numOfPieces;
	}
	
	//places markers on board for user to choose their position
	public void placeMarkers(Player current) {
		
		for (int i = 0; i < BOARD_SIDE; i++) {
			for (int j = 0; j < BOARD_SIDE; j++) {
				if(board[i][j].canPlay() == true) {
					if (board[i][j].getPiece() == Position.getEmpty()) {
						if (checkUp(current, i, j)) {
							board[i][j].setPiece(MARKER);
						}
						if (checkDown(current, i, j)) {
							board[i][j].setPiece(MARKER);
						}
						if (checkLeft(current, i, j)) {
							board[i][j].setPiece(MARKER);
						}
						if (checkRight(current, i, j)) {
							board[i][j].setPiece(MARKER);
						}
						if (checkDiagUpLeft(current, i, j)) {
							board[i][j].setPiece(MARKER);
						}
						if (checkDiagDownLeft(current, i, j)) {
							board[i][j].setPiece(MARKER);
						}
						if (checkDiagUpRight(current, i, j)) {
							board[i][j].setPiece(MARKER);
						}
						if (checkDiagDownRight(current, i, j)) {
							board[i][j].setPiece(MARKER);
						}
					}
				}
			}	
		}
	}
	
	//the following check methods check every square on the board for possible legal pieces to be placed
	
	public boolean checkUp(Player current, int i, int j) {
		
		int minCounter = 0;
		
		if (i != 0 && board[i-1][j].getPiece() == findOpposingPiece(current)) {
			
			while (board[i-1][j].getPiece() != current.getColor() && i != 0)
			{
				if (board[i-1][j].getPiece() == Position.getEmpty() || board[i-1][j].getPiece() == UNPLAYABLE
						|| board[i-1][j].getPiece() == MARKER)
					return false; 
				
				i--;
				minCounter++;
				
				if (i == 0)
					return false;
				
				if (board[i-1][j].getPiece() == current.getColor())
				{
					if (minCounter >= 1)
						return true;	
				}
			}
		}
		
		return false;
	}
	
	public boolean checkDown(Player current, int i, int j) {
		int minCounter = 0;
		
		if (i != 7 && board[i+1][j].getPiece() == findOpposingPiece(current)) {
			
			while (board[i+1][j].getPiece() != current.getColor() && i != 7)
			{
				if (board[i+1][j].getPiece() == Position.getEmpty() || board[i+1][j].getPiece() == UNPLAYABLE
						|| board[i+1][j].getPiece() == MARKER)
					return false; 
				
				i++;
				minCounter++;
				
				if (i == 7)
					return false;
				
				if (board[i+1][j].getPiece() == current.getColor())
				{
					if (minCounter >= 1)
						return true;	
				}
			}
		}
		return false;
	}
	
	public boolean checkLeft(Player current, int i, int j) {
		int minCounter = 0;
		
		if (j != 0 && board[i][j-1].getPiece() == findOpposingPiece(current)) {
			
		
			
			while (board[i][j-1].getPiece() != current.getColor() && j != 0)
			{
				if (board[i][j-1].getPiece() == Position.getEmpty() || board[i][j-1].getPiece() == UNPLAYABLE
						|| board[i][j-1].getPiece() == MARKER)
					return false; 
				
				j--;
				minCounter++;
				
				if (j == 0)
					return false;
				
				if (board[i][j-1].getPiece() == current.getColor())
				{
					if (minCounter >= 1)
						return true;	
				}
			}
		}
		return false;
	}

	public boolean checkRight(Player current, int i, int j) /*make it return boolean instead of void*/ {
		int minCounter = 0;
		
		if (j != 7 && board[i][j+1].getPiece() == findOpposingPiece(current)) {
			
			while (board[i][j+1].getPiece() != current.getColor() && j != 7)
			{
				if (board[i][j+1].getPiece() == Position.getEmpty() || board[i][j+1].getPiece() == UNPLAYABLE
						|| board[i][j+1].getPiece() == MARKER)
					return false; 
				
				j++;
				minCounter++;
				
				if (j == 7)
					return false;
				
				if (board[i][j+1].getPiece() == current.getColor())
				{
					if (minCounter >= 1)
						return true;	
				}
			}
		}
		return false;
	}
	
	public boolean checkDiagUpLeft(Player current, int i, int j) {
		
		int minCounter = 0;
		
		if (i != 0 && j != 0 && board[i-1][j-1].getPiece() == findOpposingPiece(current)) {
			
			while (board[i-1][j-1].getPiece() != current.getColor() && i != 0 && j != 0)
			{
				if (board[i-1][j-1].getPiece() == Position.getEmpty() || board[i-1][j-1].getPiece() == UNPLAYABLE
						|| board[i-1][j-1].getPiece() == MARKER)
					return false; 
				
				i--;
				j--;
				minCounter++;
				
				if (i == 0 || j == 0)
					return false;
				
				if (board[i-1][j-1].getPiece() == current.getColor())
				{
					if (minCounter >= 1)
						return true;	
				}
			}
		}
		return false;
	}
	
	public boolean checkDiagDownLeft(Player current, int i, int j) {
		int minCounter = 0;
		
		if (i != 7 && j != 0 && board[i+1][j-1].getPiece() == findOpposingPiece(current)) {
			
			while (board[i+1][j-1].getPiece() != current.getColor() && i != 7 && j != 0)
			{
				if (board[i+1][j-1].getPiece() == Position.getEmpty() || board[i+1][j-1].getPiece() == UNPLAYABLE
						|| board[i+1][j-1].getPiece() == MARKER)
					return false; 
				
				i++;
				j--;
				minCounter++;
				
				if (i == 7 || j == 0)
					return false;
				
				if (board[i+1][j-1].getPiece() == current.getColor())
				{
					if (minCounter >= 1)
						return true;	
				}
			}
		}
		return false;
	}
	
	public boolean checkDiagUpRight(Player current, int i, int j) { 
		int minCounter = 0;
		
		if (i != 0 && j != 7 && board[i-1][j+1].getPiece() == findOpposingPiece(current)) {
			
			while (board[i-1][j+1].getPiece() != current.getColor() && i != 0 && j != 7) 
			{
				if (board[i-1][j+1].getPiece() == Position.getEmpty() || board[i-1][j+1].getPiece() == UNPLAYABLE
						|| board[i-1][j+1].getPiece() == MARKER)
					return false; 
				
				i--;
				j++;
				minCounter++;
				
				if (i == 0 || j == 7)
					return false;
				
				if (board[i-1][j+1].getPiece() == current.getColor())
				{
					if (minCounter >= 1)
						return true;	
				}
			}
		}	
		return false;	
	}
	
	public boolean checkDiagDownRight(Player current, int i, int j) {
		int minCounter = 0;
		
		if (i != 7 && j != 7 && board[i+1][j+1].getPiece() == findOpposingPiece(current)) {
			
			while (board[i+1][j+1].getPiece() != current.getColor())
			{
				if (board[i+1][j+1].getPiece() == Position.getEmpty() || board[i+1][j+1].getPiece() == UNPLAYABLE
						|| board[i+1][j+1].getPiece() == MARKER)
					return false; 
				
				i++;
				j++;
				minCounter++;
				
				if (i == 7 || j == 7)
					return false;
				
				if (board[i+1][j+1].getPiece() == current.getColor())
				{
					if (minCounter >= 1)
						return true;	
				}
			}
		}
		return false;
	}
	
	//draws the board
	public void drawBoard() {
		char[] chararr = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
		
		System.out.println("     1   2   3   4   5   6   7   8");
		for (int i = 0; i < 8; i++) {
			System.out.println("   ┏---+---+---+---+---+---+---+---┓");
			System.out.print(chararr[i]);
			System.out.print("");
			System.out.print("  | ");
            for (int j = 0; j < 8; j++) {
            	
            	System.out.print(board[i][j].getPiece() + " | ");
           
            }
            System.out.println("");
		}
		System.out.println("   ┗---+---+---+---+---+---+---+---┛");
	}
	
	//checks if the game is over
	public boolean checkIfGameOver() {
		int counter = 0;
		
		for (int i = 0; i < BOARD_SIDE; i++)
			for (int j = 0; j < BOARD_SIDE; j++)
				if (board[i][j].getPiece() == MARKER) 
					counter++;

		if (counter > 0)
			return false;
		else 
			return true;
	}

	//counts winning players pieces
	public int countPieces(Player winner) {
	
		int pieces = 0;
		
		for (int i = 0; i < BOARD_SIDE; i++)
			for (int j = 0; j < BOARD_SIDE; j++)
				if (board[i][j].getPiece() == winner.getColor())
					pieces++;
		
		return pieces;
	}

	//checks if it is a valid position on the board
	public boolean isValidPosition(int row, int col) {
		if (board[row][col].getPiece() == MARKER)
			return true;
		else
			return false;
	}
	
	//gets other players piece
	public char findOpposingPiece(Player current) {
		if (current.getColor() == 'B')
			return 'W';
		else
			return 'B';
	}
	
	//removes markers on the board in between each turn
	public void removeMarkers() {
		for (int i = 0; i < BOARD_SIDE; i++)
			for (int j = 0; j < BOARD_SIDE; j++)
				if (board[i][j].getPiece() == MARKER)
					board[i][j].setPiece(Position.getEmpty());
	}
	
	//gets positions in a string
	public String getPositions() {
		String p = "";
		
		for (int i = 0; i < BOARD_SIDE; i++)
			for (int j = 0; j < BOARD_SIDE; j++)
				p += board[i][j].getPiece();
		
		return p;
	}

	
	
	//gets board side
	public int getBOARD_SIDE() {
		return BOARD_SIDE;
	}
}
