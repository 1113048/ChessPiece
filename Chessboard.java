import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Chessboard {

	private Boolean isgameRunning;
	private Piece[][] chessboard = new Piece[numOfRowsAndCols][numOfRowsAndCols];// [# of row][# ofcolumns]
	Scanner scn = new Scanner(System.in);
	private static final int numOfRowsAndCols = 8;
	private static int srcRow, srcCol, destRow, destCol;
	private static int whiteScore = 0, blackScore = 0;
	private static Boolean whitesTurnToMove;

	// Set to true if move is invalid. Asks for user input again in move()
	// method.
	private static Boolean invalidMove = false;

	/* Constructs a Chessboard object and populates it with pieces */

	public Chessboard() {

		initializeBoard(chessboard);
		isgameRunning = true;

	}

	public Boolean getGameRunning() {
		return this.isgameRunning;
	}

	private static void initializeBoard(Piece[][] chessboard) {
		// rows 0 and 1 are black
		// rows 6 and 7 are white

		for (int row = 0; row < chessboard.length; row++) {
			for (int col = 0; col < chessboard[row].length; col++) {
				if (row == 0) {

					switch (col) {
						case 0:
							chessboard[row][col] = new Queen(true);
							break;

					}
				} else {
					chessboard[row][col] = null;
				}
			}
		}

		// Randomly assign whether black or white starts first
		Random rand = new Random();
		whitesTurnToMove = false;

	}

	// print out board based off unicode symbol of piece and position

	public void printBoard() {

		System.out.println("\ta\tb\tc\td\te\tf\tg\th");
		for (int row = 0; row < chessboard.length; row++) {
			System.out.print(8 - row + ".\t");
			for (int col = 0; col < chessboard[row].length; col++) {
				if (chessboard[row][col] != null) {
					chessboard[row][col].draw();
					System.out.print("\t");

				} else {
					System.out.print("\t");
				}
			}
			System.out.println();
		}
	}

	private boolean moveValid() {

		// invalid if the move origin or destination is outside the board

		if (srcRow < 0 || srcRow > 7 || srcCol < 0 || srcCol > 7 || destRow < 0
				|| destRow > 7 || destCol < 0 || destCol > 7) {
			System.out.println("Move is outside the board");
			return false;
		}

		// Invalid if origin is null
		if (chessboard[srcRow][srcCol] == null) {
			System.err.println("Origin is empty");
			return false;
		}



		// return false if specific piece rules are not obeyed
		if (!chessboard[srcRow][srcCol].isMoveValid(srcRow, srcCol, destRow,
				destCol)) {
			System.err.println("This piece doesn't move like that");
			return false;
		}

		// this statement stops the statement for checking if white lands on
		// white from performing isWhite() on a null space
		if (chessboard[destRow][destCol] == null) {
			return true;
		}




		return true;

	}

	/**
	 * A private method called to update the score of whoever's turn it is after
	 * they take an opposing piece
	 */
	
	public void move() {
    Scanner userInput = new Scanner(System.in);
	


		String move = scn.nextLine();


		// convert to lower case
		String lowerCase = move.toLowerCase();
		// parse move string into components
		String[] components = lowerCase.split(" ");

		// if you assume that move is "e1 to e5" then
		// components[0].chartAt(0) = 'e'
		// components [0].charAt (1) = '1'

		// use chars in components to set the array coordinates of the
		// move origin and move destination

		srcRow = 7 - (components[0].charAt(1) - '1');
		srcCol = components[0].charAt(0) - 'a';
		destRow = 7 - (components[2].charAt(1) - '1');
		destCol = components[2].charAt(0) - 'a';

		if (moveValid()) {
			// put piece in destination
			chessboard[destRow][destCol] = chessboard[srcRow][srcCol];
			// empty the origin of the move
			chessboard[srcRow][srcCol] = null;
			whitesTurnToMove = !whitesTurnToMove;
		} else {
			invalidMove = true;
			move();

		}

	}

}
