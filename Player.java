public class Player {
	
	private String name;
	private char color;

	//constructor sets player nae
	public Player(String name) {
		this.name = name;
	}
	
	//getters and setters for names and colors
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public char getColor() {
		return color;
	}

	public void setColor(char color) {
		this.color = color;
	}
}