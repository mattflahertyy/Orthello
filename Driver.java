import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		
		int menu_selection;
		
		//menu loops asking to quit, load or start new game, if new game asks for players name
		//after exiting continues looping until user quits
		do {
			do 
			{
				System.out.println("Please select an option: ");
				System.out.println("1. Quit");
				System.out.println("2. Load a Game");
				System.out.println("3. Start a New Game");
				
				while (!scan.hasNextInt()) {
					System.out.println("Error: Must enter a valid integer.");
					scan.nextLine();
				}
				
				menu_selection = scan.nextInt();
				scan.nextLine();
				
				if (menu_selection > 3 || menu_selection < 1)
					System.out.println("Error: Please enter the number 1, 2 or 3!");
				
			} while ((menu_selection > 3 || menu_selection < 1));
			
			System.out.println("");		
			
			if (menu_selection == 1) {			
				System.out.println("User quit, game has ended.");
				System.exit(0);
			}
			if (menu_selection == 2) {
				Board loadedBoard = Game.load();			
			}
			if (menu_selection == 3) {
				
				System.out.println("Please enter a name for Player 1:");
				Player player1 = new Player(scan.nextLine());
				player1.setColor('B');
				
				System.out.println("Please enter a name for Player 2:");
				Player player2 = new Player(scan.nextLine());
				player2.setColor('W');
				
				Game orthello = new Game(player1, player2);
				System.out.println("");
				
				orthello.start();
			}
			
		} while(menu_selection != 1);
		scan.close();
	}

}
