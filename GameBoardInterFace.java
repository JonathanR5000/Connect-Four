import javax.swing.JButton;

import javax.swing.JButton;

public interface GameBoardInterface {

	void clearBoard();
	void displayBoard();
	void takeTurn();
	void displayWinner();
	void displayCurrPlayer();
	void displayChampion();
	void createFile();
	void dropDown(int col);
	boolean playAgain();
	boolean isFull();
	boolean isWinner(String currPlayer);
	int getButtonsClicked(JButton jbClicked);
}