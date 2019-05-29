public class Player {

	private String name;
	private String symbol;
	private int numGames;
	private int numWins;
	private int numLosses;
	private int numDraws;
	
	public Player() {
		symbol = " ? ";
		name = "Player Zero";
		numGames = 0;
		numWins = 0;
		numLosses = 0;
		numDraws = 0;
	}
	
	public Player(String symbol) {
		this();
		this.symbol = symbol;
	}
	
	public Player(String name, String symbol){
		this();//default constructor of same class
		this.name = name;
		this.symbol = symbol;
	}
	
	protected void addWin(){
		numGames++;
		numWins++;
	}
	protected void addLoss(){
		numGames++;
		numLosses++;
	}
	protected void addDraw(){
		numGames++;
		numDraws++;
	}
	public String getName() {
		return name;
	}
	public String getSymbol() {
		return symbol;
	}
	public int getNumGames() {
		return numGames;
	}
	public int getNumWins() {
		return numWins;
	}
	public int getNumLosses() {
		return numLosses;
	}
	public int getNumDraws() {
		return numDraws;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Player) {
			Player otherPlayer = (Player)obj;
			if(this.name.equalsIgnoreCase(otherPlayer.name)) {
				if(this.symbol == otherPlayer.symbol) {
					if(this.numDraws == otherPlayer.numDraws) {
						if(this.numGames == otherPlayer.numGames){
							if(this.numLosses == otherPlayer.numLosses) {
								if(this.numWins == otherPlayer.numWins) {
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	//toString method
	@Override
	public String toString() {
		String s = String.format("Name: %20s | Symbol:   | Number of wins:  | Number of losses:  | Number of draws:  | Number of games: "
				, name, symbol, numWins, numLosses, numDraws, numGames);
		return s;
	}
}