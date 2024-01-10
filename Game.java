import java.util.Scanner;
import java.io.*;

public class Game {
	
	private Player first, second, current;
	public Scanner scanner = new Scanner(System.in);
	private Board board = new Board(); 

	public Game () {
		first = null;
		second = null;
		current = null;
	}
	
	//for new games
	public Game(Player p1, Player p2) {
		first = p1;
		second = p2;
		current = p1;
	}
	
	//for loaded games
	public Game(Player p1, Player p2, Player cur) {
		first = p1;
		second = p2; 
		current = cur;
	}

	//asks user to choose starting posiiton, creates new board then calls play method
	public void start() {
		
		int start_position;
		
		System.out.println("Awesome! Now choose the starting positions: \n\n Standard: \n\t1. 4-4 middle \n Non-Standard: \n\t2. Upper Left \n\t"
				+ "3. Upper Right \n\t4. Lower Left  \n\t5. Lower Right");
		
		do 
		{		
			while (!scanner.hasNextInt()) {
				System.out.println("Error: Must enter a valid integer.");
				scanner.nextLine();
			}
			
			
			start_position = Integer.parseInt(scanner.nextLine());
			System.out.println("");
			
			
			
			if (start_position > 5 || start_position < 1)
				System.out.println("Error: Please enter a number between 1-5!");
			
		} while (start_position > 5 || start_position < 1);
		
			
			board.createNewBoard(start_position);
			
			play();
	}
	
	//loops until game is over calling take turn or save method, removes markers too	
	public void play() {
		boolean isGameOver = false;
		
		while (!isGameOver)
		{
			if (board.hasForfeited)
				break;
			
			board.takeTurn(current);
			
			if (board.hasSaved) {
				if (current.getName().equals(first.getName()))
					current = first;
				else
					current = second;
				save();
				break;
			}
			
			if (current.getName().equals(first.getName()))
				current = second;
			else
				current = first;
			
			
			
			isGameOver = board.checkIfGameOver();
			board.removeMarkers();
		}
		
		if (isGameOver) {
			board.drawBoard();
			System.out.println(current.getName() + " wins with " + board.countPieces(current) + " pieces!");
			System.out.println("");
		}
					
	}
	
	//opens file and updates board and creates new players, returns board
	public static Board load() {
		String filename, tempp1, tempp2, currentp, positions;
		Game loadedOrthello;
		boolean doesFileExist = false;
		Scanner scanner1 = new Scanner(System.in);
		
		System.out.println("Please enter the name of which game you would like to load: ");
		filename = scanner1.nextLine();
		//check if it is a legit string
	
		try {
			File file = new File(filename);
			
			if (!file.exists()) {
				doesFileExist = false;
				while(!doesFileExist) {
					System.out.println("File does not exist. Please enter a filename that exists: ");
					filename = scanner1.nextLine();
					file = new File(filename);
						if (file.exists())
							doesFileExist = true;
					}
				
			}
			if (file.exists() || doesFileExist) {
				Scanner scan2 = new Scanner(new File(filename));
				if (scan2.hasNextLine()) {
					
					tempp1 = scan2.nextLine();
					tempp2 = scan2.nextLine();
					currentp = scan2.nextLine();
					positions = scan2.nextLine();
					
					Player p1 = new Player(tempp1);
					p1.setColor(Position.getBlack());
					
					Player p2 = new Player(tempp2);
					p2.setColor(Position.getWhite());
					
					Player cur = new Player(currentp);
					if (cur.getName().equals(p1.getName()))
						cur.setColor(p1.getColor());
					else
						cur.setColor(p2.getColor());
					
					loadedOrthello = new Game(p1, p2, cur);
					
					loadedOrthello.board = new Board(positions);	
					loadedOrthello.play();
					
					return loadedOrthello.board;
				}
			
			}
		}
		catch (IOException e)
		{
			System.out.println("An error occurred.");
		     e.printStackTrace();
		}
		catch (IllegalStateException e) {
			System.out.println("Problem trying to read file");
		}
		
		
		scanner1.close();
		
		///filler for above block
		Board filler = new Board();
		return filler;
	}	
	
	//saves the game by saving names and positions into a file
	private void save() {
		String filename, thePositions = "";
		
		System.out.println("To save the current game, enter a filename below:");
		filename = scanner.nextLine();
		
		try {
			File file = new File(filename);
			
			if(file.exists() && !file.isDirectory()) 
			    System.out.println("File already exists.");
			else {
				file.createNewFile();
				System.out.println("");
				System.out.println("File created.");

				PrintWriter pw = new PrintWriter(filename);

				pw.write(first.getName() + "\n" + second.getName() + "\n" + current.getName() + "\n");
				
				thePositions = board.getPositions();	
				
				pw.write(thePositions);
				
				pw.close();			
			}
		}
		catch (IOException e)
		{
			System.out.println("An error occurred.");
		     e.printStackTrace();
		}
		catch (IllegalStateException e) {
			System.out.println("Problem trying to read file");
		}
		
		System.out.println("");
	}
}
