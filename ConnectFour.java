/* Jason Tang
 * 
 * Simulates a game of Connect Four with Processing.
 * If four consecutive pieces exist, the outcome of the game (win/loss) is
 * printed on the console. The applet still continues to draw.
 */
import processing.core.*;
public class ConnectFourProcessing extends PApplet{
	int[][] board = {
			// 0 1 2 3 4 5 6
			{0,0,0,0,0,0,0}, // 0
			{0,0,0,0,0,0,0}, // 1
			{0,0,0,0,0,0,0}, // 2 
			{0,0,0,0,0,0,0}, // 3
			{0,0,0,0,0,0,0}, // 4
			{0,0,0,0,0,0,0}  // 5
	};
	int[] columnTracker = {0, 0, 0, 0, 0, 0, 0};
	int columnX;
	/**
	 * Creates the game window.
	 */
	public void setup() {
		size(700, 600);
	}
	/**
	 * Draws the elements of the game.
	 */
	public void draw() {
		background(255);
		// Draws the lines that separate the rows and columns.
		fill(0);
		for(int i = 100; i <= 600; i += 100) {
			rect(i, 0, 1, 600);
		}
		for(int i = 100; i <= 500; i += 100) {
			rect(0, i, 700, 1);
		}
		columnHighlight();
		// Draws the existing game pieces.
		stroke(0);
		for(int row = 5; row >= 0; row--) {
			for(int col = 0; col <= 6; col++) {
				if(board[row][col] == 1) {
					fill(251, 211, 0);
					ellipse(col * 100 + 50, row * 100 + 50, 80, 80);
				} else if(board[row][col] == 2) {
					fill(99, 118, 203);
					ellipse(col * 100 + 50, row * 100 + 50, 80, 80);
				}
			}
		}
		columnX = mouseX / 100;
	}
	/**
	 * Highlights the column that the mouse cursor is hovering over.
	 */
	public void columnHighlight() {
		noStroke();
		fill(0, 50);
		rect(columnX * 100, 0, 100, 600);
	}
	/**
	 * Initiates one round of the game when the mouse is clicked.
	 */
	public void mousePressed() {
		// Generates the player's piece.
		int columnY;
		boolean check = true;
		do {
			columnY = columnTracker[columnX];
			if(columnY < 6) {
				board[5 - columnY][columnX] = 1;
				check = false;
			}
		} while(check);
		columnTracker[columnX] += 1;
		// Checks if the player has four consecutive pieces.
		if(checkFour(board, 1)) {
			System.out.println("YOU WIN!");
		}
		// Generates the computer's piece at random.
		int randomX;
		check = true;
		do {
			randomX = (int)(Math.random() * 7);
			columnY = columnTracker[randomX];
			if(columnY < 6) {
				board[5 - columnY][randomX] = 2;
				check = false;
			}
		} while(check);
		columnTracker[randomX] += 1;
		// Checks the board for four consecutive pieces.
		if(checkFour(board, 2)) {
			System.out.println("YOU LOSE!");
		}
	}
	/**
	 * Determines whether there are four consecutive elements in a row, 
	 * in a column, or diagonally, in a matrix of integers.
	 * 
	 * @param board - A two-dimensional Array of integers.
	 * @param piece - An integer to search for.
	 * @return - returns true if it occurs 4 times consecutively
               - returns false if the integer is not found 4 times consecutively
	 */
	public static boolean checkFour(int[][] board, int piece) {
		if(checkRows(board, piece)) {
			return true;
		} else if (checkColumns(board, piece)) {
			return true;
		} else if (checkDiagonals(board, piece)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Determines whether there are four consecutive 
	 * elements in a row in a matrix of integers.
	 * 
	 * @param board - A two-dimensional Array of integers.
	 * @param piece - An integer to search for.
	 * @return - returns true if it occurs 4 times consecutively
	 *         - returns false if the integer is not found 4 times consecutively
	 */
	public static boolean checkRows(int[][] board, int piece) {
		int count;
		for(int row = 5; row >= 0; row--) {
			count = 0;
			for(int col = 0; col <= 6; col++) {
				if(board[row][col] == piece) {
					count++;
					if(count == 4) {
						return true;
					}
				} else {
					count = 0;
				}
			}
		}
		return false;
	}
	/**
	 * Determines whether there are four consecutive 
	 * elements in a column in a matrix of integers.
	 * 
	 * @param board - A two-dimensional Array of integers.
	 * @param piece - An integer to search for.
	 * @return - returns true if it occurs 4 times consecutively
	 *         - returns false if the integer is not found 4 times consecutively
	 */
	public static boolean checkColumns(int[][] board, int piece) {
		int count;
		for(int col = 0; col <= 6; col++) {
			count = 0;
			for(int row = 5; row >= 0; row--) {
				if(board[row][col] == piece) {
					count++;
					if(count == 4) {
						return true;
					}
				} else {
					count = 0;
				}
			}
		}
		return false;
	}
	/**
	 * Determines whether there are four consecutive 
	 * elements diagonally in a matrix of integers.
	 * 
	 * @param board - A two-dimensional Array of integers.
	 * @param piece - An integer to search for.
	 * @return - returns true if it occurs 4 times consecutively
	 *         - returns false if the integer is not found 4 times consecutively
	 */
	public static boolean checkDiagonals(int[][] board, int piece) {
		int row, col, count, diagonalLength;
		/* Checks these diagonals:
		 * {0,0,0,X,X,X,0}
		 * {0,0,X,X,X,0,0}
		 * {0,X,X,X,0,0,0}
		 * {X,X,X,0,0,0,0}
		 * {X,X,0,0,0,0,0}
		 * {X,0,0,0,0,0,0}
		 */
		diagonalLength = 4;
		for(int startEdge = 3; startEdge <= 5; startEdge++) {
			row = startEdge;
			col = 0;
			count = 0;
			for(int i = 1; i <= diagonalLength; i++) {
				if(board[row][col] == piece) {
					count++;
					if(count == 4) {
						return true;
					}
				} else {
					count = 0;
				}
				row--;
				col++;
			}
			diagonalLength++;
		}
		/* Checks these diagonals:
		 * {0,0,0,0,0,0,X}
		 * {0,0,0,0,0,X,X}
		 * {0,0,0,0,X,X,X}
		 * {0,0,0,X,X,X,0}
		 * {0,0,X,X,X,0,0}
		 * {0,X,X,X,0,0,0}
		 */
		diagonalLength = 6;
		for(int startEdge = 1; startEdge <= 3; startEdge++) {
			row = 5;
			col = startEdge;
			count = 0;
			for(int i = 1; i <= diagonalLength; i++) {
				if(board[row][col] == piece) {
					count++;
					if(count == 4) {
						return true;
					}
				} else {
					count = 0;
				}
				row--;
				col++;
			}
			diagonalLength--;
		}
		/* Checks these diagonals:
		 * {0,X,X,X,0,0,0}
		 * {0,0,X,X,X,0,0}
		 * {0,0,0,X,X,X,0}
		 * {0,0,0,0,X,X,X}
		 * {0,0,0,0,0,X,X}
		 * {0,0,0,0,0,0,X}
		 */
		diagonalLength = 4;
		for(int startEdge = 3; startEdge <= 5; startEdge++) {
			row = startEdge;
			col = 6;
			count = 0;
			for(int i = 1; i <= diagonalLength; i++) {
				if(board[row][col] == piece) {
					count++;
					if(count == 4) {
						return true;
					}
				} else {
					count = 0;
				}
				row--;
				col--;
			}
			diagonalLength++;
		}
		/* Checks these diagonals:
		 * {X,0,0,0,0,0,0}
		 * {X,X,0,0,0,0,0}
		 * {X,X,X,0,0,0,0}
		 * {0,X,X,X,0,0,0}
		 * {0,0,X,X,X,0,0}
		 * {0,0,0,X,X,X,0}
		 */
		diagonalLength = 6;
		for(int startEdge = 5; startEdge >= 3; startEdge--) {
			row = 5;
			col = startEdge;
			count = 0;
			for(int i = 1; i <= diagonalLength; i++) {
				if(board[row][col] == piece) {
					count++;
					if(count == 4) {
						return true;
					}
				} else {
					count = 0;
				}
				row--;
				col--;
			}
			diagonalLength--;
		}
		return false;
	}
}
