import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ConnectFOURGUI extends JFrame{
	
	private JButton [] topButtons;
	private JPanel jpMain;
	int [] rowCounter = {7,7,7,7,7,7,7};
	Player player1;
	Player player2;
	Player currPlayer;
	ConnectFourBoard cfBoard;
	ScoreBoard scoreBoard; //score board
	
	public ConnectFOURGUI() {
		String playerX = JOptionPane.showInputDialog("Enter Player 1 name"); //Option to use names as texts
		String playerY = JOptionPane.showInputDialog("Enter Player 2 name"); 
		
		JOptionPane.showMessageDialog(null, "Press the CLICK button to drop your piece in the column of your choice."
				+ "\n The first player to have a line of four consecutive matches of their symbol wins."
				+ " \n Good luck and have fun!", "Game Rules", JOptionPane.PLAIN_MESSAGE);
		
		jpMain = new JPanel();
		jpMain.setLayout(new BorderLayout());
		
		player1 = new Player("" + playerX, "X");
		player2 = new Player("" + playerY, "O");
		currPlayer = player1;
		
		scoreBoard = new ScoreBoard();
		cfBoard = new ConnectFourBoard(); //game played here
		
		jpMain.add(scoreBoard, BorderLayout.NORTH);
		jpMain.add(cfBoard, BorderLayout.CENTER);
		add(jpMain);
		setSize(500,500);
		cfBoard.setBackground(Color.MAGENTA);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	private class ScoreBoard extends JPanel{
		private JLabel lblChamp, lblLatestWinner, 
		lblcurrPlayer, lblPlaceHolder, lblcurrPlayerTurn,
		lblPlayerNames, lblPlayerNumWins, lblChampPlayer,
		lblPlayer1Name,lblPlayer2Name, lblLastWinner,
		lblPlayer1NumWins,lblPlayer2NumWins;
		JPanel jpGenScoreInfo, jpPlayerScoreInfo, jpCurrPlayerInfo;
		
		public ScoreBoard() {
			setLayout(new BorderLayout());
			jpGenScoreInfo = new JPanel();
			jpGenScoreInfo.setLayout(new GridLayout(2,2));
			jpGenScoreInfo.setBackground(Color.GREEN);
			lblChamp = new JLabel("Champion");//00
			lblChampPlayer = new JLabel("----------");//01
			jpGenScoreInfo.add(lblChamp);
			jpGenScoreInfo.add(lblChampPlayer);
			lblLastWinner = new JLabel("Latest Winner");//10
			lblLatestWinner = new JLabel("----------");//11
			jpGenScoreInfo.add(lblLastWinner);
			jpGenScoreInfo.add(lblLatestWinner);
			
			jpPlayerScoreInfo = new JPanel();
			jpPlayerScoreInfo.setLayout(new GridLayout(3,3));
			jpPlayerScoreInfo.setBackground(Color.YELLOW);
			lblPlaceHolder = new JLabel("");
			lblPlayerNames = new JLabel("Name");
			lblPlayerNumWins = new JLabel("Score");
			lblPlayer1Name = new JLabel(player1.getName());
			lblPlayer2Name = new JLabel(player2.getName());
			lblPlayer1NumWins = new JLabel("" + player1.getNumWins());
			lblPlayer2NumWins = new JLabel("" + player2.getNumWins());
			
			jpPlayerScoreInfo.add(lblPlaceHolder);//00
			jpPlayerScoreInfo.add(new JLabel("Player 1"));//01
			jpPlayerScoreInfo.add(new JLabel("Player 2"));//02
			jpPlayerScoreInfo.add(lblPlayerNames);//10
			jpPlayerScoreInfo.add(lblPlayer1Name);//11
			jpPlayerScoreInfo.add(lblPlayer2Name);//12
			jpPlayerScoreInfo.add(lblPlayerNumWins);//20
			jpPlayerScoreInfo.add(lblPlayer1NumWins);//21
			jpPlayerScoreInfo.add(lblPlayer2NumWins);//22
					
			
			jpCurrPlayerInfo = new JPanel();
			jpCurrPlayerInfo.setLayout(new GridLayout(1,2));
			jpCurrPlayerInfo.setBackground(Color.cyan);
			lblcurrPlayer = new JLabel("Current Player");
			lblcurrPlayerTurn = new JLabel(currPlayer.getName());
			jpCurrPlayerInfo.add(lblcurrPlayer);
			jpCurrPlayerInfo.add(lblcurrPlayerTurn);
			
			add(jpGenScoreInfo, BorderLayout.NORTH); //Display the Score Board
			add(jpPlayerScoreInfo, BorderLayout.CENTER); //Display the Score Info
			add(jpCurrPlayerInfo, BorderLayout.SOUTH); //Display the Current player info
		}
	}//end of the ScoreBoard

	private class ConnectFourBoard extends JPanel implements GameBoardInterface, ActionListener {
		private JButton [][] board;
		private static final int NUM_ROWS = 6;
		private static final int NUM_COLMS = 7;
		
		public ConnectFourBoard(){
			setLayout(new GridLayout(7,8));
			topButtons = new JButton[7];
			for(int i = 0; i < 7; i++) {
				topButtons[i] = new JButton(("CLICK"));
				topButtons[i].addActionListener(this);
				add(topButtons[i]);
			}
			displayBoard();
		}
		
		@Override 
		public void clearBoard() {
			for(int row = 0; row < board.length; row++){
				for(int col = 0; col< board[row].length; col++){
					board[row][col].setText("");
					board[row][col].setEnabled(true);
				}
			}
		}

		@Override 
		public void displayBoard() {
			board = new JButton[6][7];
			for(int row = 0; row < board.length; row++){
				for(int col = 0; col < board[row].length; col++){
					board[row][col] = new JButton();
					Font bigF = new Font(Font.SANS_SERIF, Font.BOLD, 30);
					board[row][col].setFont(bigF);
					board[row][col].addActionListener(this);//listen for clicks
					add(board[row][col]);
				}
			}
		}

		@Override
		public void takeTurn() {
			if(currPlayer.equals(player1)){
				currPlayer = player2;
			}
			else{
				currPlayer = player1;
			}
		}
		
		@Override
		public void createFile() {
			String fileName = "Connect4-Results.txt";
			try {
				PrintWriter outputStream = new PrintWriter(fileName);
				if(player1.getNumWins() > player2.getNumWins()) {
					outputStream.println("Current Champion: " + player1.getName());
				}
				else {
					outputStream.println("Current Champion: " + player2.getName());
				}
				outputStream.println("Player 1: " + player1.getName());
				outputStream.println("Number of Wins: " + player1.getNumWins());			
				outputStream.println("Player 2: " + player2.getName());
				outputStream.println("Number of Wins: " + player2.getNumWins());				
				outputStream.close();
			
			}catch(FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		@Override //Stays
		public void displayWinner() {
			JOptionPane.showMessageDialog(null, "WINNER! " + currPlayer.getName());
			currPlayer.addWin();
			scoreBoard.lblLatestWinner.setText(currPlayer.getName());
			if(currPlayer.equals(player1)){
				scoreBoard.lblPlayer1NumWins.setText("" + player1.getNumWins());
			}
			else{
				scoreBoard.lblPlayer2NumWins.setText("" + player2.getNumWins());
			}
		}
		
		@Override
		public void displayCurrPlayer() {
			scoreBoard.lblcurrPlayerTurn.setText(currPlayer.getName());
			if(currPlayer.equals(player2)) {
				scoreBoard.lblcurrPlayerTurn.setText("" + player1.getName());
			}
			else {
				scoreBoard.lblcurrPlayerTurn.setText("" + player2.getName());
			}
		}
		
		@Override
		public void displayChampion() { //display champion if champion has most wins
			scoreBoard.lblChampPlayer.setText(currPlayer.getName());
			if(player1.getNumWins() > player2.getNumWins()) {
				scoreBoard.lblChampPlayer.setText("" + player1.getName());
			}
			else {
				scoreBoard.lblChampPlayer.setText("" + player2.getName());
			}
		}

		@Override 
		public boolean isFull() {
			for(int row = 0; row < board.length; row++){
				for(int col = 0; col < board[row].length; col++){
					if(board[row][col].getText().equals("")){
						return false; //if any are empty return false
					}
				}
			}
			return true;
		}

		@Override
		public boolean isWinner(String currPlayerSymbol) {
			if(isWinnerInRow() || isWinnerInCol()){
				return true;
			}
			if(isWinnerInMainDiag() || isWinnerInSecondDiag()) {
				return true;
			}
			return false;
		}
		
		public boolean isWinnerInRow(){ // "-"
			for(int row = 0; row < board.length; row++){
				for(int col = 0; col < board[row].length - 3; col++){
					if(board[row][col].getText().equals(currPlayer.getSymbol())
							&& board[row][col + 1].getText().equals(currPlayer.getSymbol())
							&& board[row][col + 2].getText().equals(currPlayer.getSymbol())
							&& board[row][col + 3].getText().equals(currPlayer.getSymbol())
							){
						return true;
						}
					}
				}
			return false;
			}
		
		public boolean isWinnerInCol(){ // "|"
			for(int col = 0; col < board.length; col++){
				for(int row = 0; row < board.length - 3; row++){
					if(board[row][col].getText().equals(currPlayer.getSymbol())
							&& board[row + 1][col].getText().equals(currPlayer.getSymbol())
							&& board[row + 2][col].getText().equals(currPlayer.getSymbol())
							&& board[row + 3][col].getText().equals(currPlayer.getSymbol())
							){
						return true;
						}
					}
				}
			return false;
			}
		
		public boolean isWinnerInMainDiag(){ // "\"
			for(int row = 0; row < NUM_ROWS - 3; row++) {
				for(int col = 0; col < NUM_COLMS - 3; col++) {
					if(board[row][col].getText().equals(currPlayer.getSymbol()) 
							&& board[row + 1][col + 1].getText().equals(currPlayer.getSymbol())
							&& board[row + 2][col + 2].getText().equals(currPlayer.getSymbol())
							&& board[row + 3][col + 3].getText().equals(currPlayer.getSymbol())
							){
							return true;
						}
					}
				}
			return false;
			}
		
		public boolean isWinnerInSecondDiag(){ // "/"
			for(int row = 0; row < NUM_ROWS - 3; row++) {
				for(int col = 3; col < NUM_COLMS; col++) {
					if(board[row][col].getText().equals(currPlayer.getSymbol()) 
							&& board[row + 1][col - 1].getText().equals(currPlayer.getSymbol())
							&& board[row + 2][col - 2].getText().equals(currPlayer.getSymbol())
							&& board[row + 3][col - 3].getText().equals(currPlayer.getSymbol())
							){
							return true;
						}
					}
				}
			return false;
			}
		
		@Override
		public boolean playAgain() {
			if (JOptionPane.showConfirmDialog(null, "Would You Like To Play Again?", "Connect Four",JOptionPane.YES_NO_OPTION)
					== JOptionPane.YES_OPTION) { // yes option
				createFile();
				clearBoard();
			} else { // no option
				createFile();
				System.exit(0);
			}
			return false;
		}
		
		@Override
		public void dropDown(int col) {
			int space = 0;
			for(int num_col = 5; num_col >= 0; num_col--) {
				if(board[num_col][col].isEnabled()) {
					board[num_col][col].setEnabled(false);
				
				if(currPlayer == player1) {
					board[num_col][col].setText(currPlayer.getSymbol());
					space = 1;
					break;
				}
				else if(currPlayer == player2){
					board[num_col][col].setText(currPlayer.getSymbol());
					space = 1;
					break;
					}
				}
			}
		}
		
		@Override
		public int getButtonsClicked(JButton jbClicked) {
			for(int i = 0; i < topButtons.length; i++) {
				if(jbClicked.equals(topButtons[i])) {
					return i;
				}
			}
			return -1;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btnClicked = (JButton)e.getSource();
			int col = getButtonsClicked(btnClicked);
			dropDown(col);
			displayCurrPlayer();
			//isWinner
			if(isWinner(currPlayer.getSymbol())){
				displayWinner();
				displayChampion();
				playAgain();
			}
			else if(isFull()){
				JOptionPane.showMessageDialog(null, "DRAW");
				playAgain();
			}
			takeTurn();
		}//end of actionPerformed
	}
}