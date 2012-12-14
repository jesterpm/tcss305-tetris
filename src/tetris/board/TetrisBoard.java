/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 - Autumn 2009
 * Tetris Project
 * 17 November 2009
 */

package tetris.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import tetris.model.IntPoint;
import tetris.piece.TetrisPiece;
import tetris.piece.TetrisPieces;

/**
 * Class to represent the tetris board.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 23 November 2009
 */
public class TetrisBoard extends Observable {
  // Public Constants
  /**
   * Standard tetris board width.
   */
  public static final int STANDARD_BOARD_WIDTH = 10;

  /**
   * Standard tetris board height.
   */
  public static final int STANDARD_BOARD_HEIGHT = 20;
  
  /**
   * Size of the above board buffer.
   */
  public static final int NEW_PIECE_BUFFER = TetrisPiece.TETRIS_PIECE_SIZE;

  // Private Constants
  /**
   * Initial progression speed in milliseconds.
   */
  private static final int GAME_SPEED = 1000;
  
  /**
   * Speed change in ms per level.
   */
  private static final int DSPEED_PER_LEVEL = 100;
  
  /**
   * Number of points per line clear.
   */
  private static final int POINTS_PER_LINE = 100;
  
  /**
   * Number of points per line that the piece was hard dropped.
   */
  private static final int POINTS_PER_DROPPED_LINE = 10;
  
  /**
   * Points per level.
   */
  private static final int POINTS_PER_LEVEL = 2000;
  
  /**
   * Y position to start new pieces.
   */
  private static final int INITIAL_Y_COORDINATE = 0;

  // Private Fields
  /**
   * Flag to indicate the game is over.
   */
  private boolean my_game_over;
  
  /**
   * Flag to store if a game is paused.
   */
  private boolean my_game_paused;
  
  /**
   * Current game score.
   */
  private int my_score;

  /**
   * Storage for the board.
   */
  private final List<List<Boolean>> my_board;

  /**
   * Board width.
   */
  private final int my_board_width;

  /**
   * Board height.
   */
  private final int my_board_height;

  /**
   * Future Tetris Pieces.
   */
  private List<TetrisPiece> my_future_pieces;
  
  /**
   * Testing Pieces.
   */
  private List<TetrisPiece> my_testing_pieces;

  /**
   * Current Piece.
   */
  private TetrisPiece my_current_piece;

  /**
   * Create a new tetris board.
   * 
   * @param the_width Board width.
   * @param the_height Board height.
   */
  public TetrisBoard(final int the_width, final int the_height) {
    super();

    // Mark the game as over, since we haven't called newGame() yet
    my_game_over = true;
    my_game_paused = false;
    my_score = 0;

    // Setup the board.
    my_board = new ArrayList<List<Boolean>>();
    my_board_width = the_width;
    my_board_height = the_height;

    final int center_pos = my_board_width / 2 - TetrisPiece.TETRIS_PIECE_SIZE / 2;
    
    // Set a current piece just to keep my_current_piece from being null.
    my_current_piece = TetrisPieces.getRandomPiece(center_pos, INITIAL_Y_COORDINATE);

    // And setup the future piece list.
    my_future_pieces = new ArrayList<TetrisPiece>();
    my_testing_pieces = new ArrayList<TetrisPiece>();
  }

  /**
   * Create a tetris board with from a set of pieces.
   * 
   * @param the_width Board width.
   * @param the_height Board height.
   * @param the_pieces List of pieces to place on board. the_pieces.size() > 1
   */
  public TetrisBoard(final int the_width, final int the_height,
                     final List<TetrisPiece> the_pieces) {
    this(the_width, the_height);

    // Set the current piece
    my_current_piece = the_pieces.get(0);

    // Set the testing pieces.
    my_testing_pieces.addAll(the_pieces);
  }

  /**
   * @return true if the game is over.
   */
  public boolean isGameOver() {
    return my_game_over;
  }
  
  /**
   * @param the_state True if the game is to be paused, false otherwise.
   */
  public void setPaused(final boolean the_state) {
    if (!my_game_over && my_game_paused != the_state) {
      my_game_paused = the_state;
    
      setChanged();
      notifyObservers(new TetrisBoardEvent(EventTypes.PAUSE_CHANGED));
    }
  }
  
  /**
   * @return True if the game is paused, false otherwise.
   */
  public boolean isPaused() {
    return my_game_paused;
  }
  
  /**
   * @return the current score.
   */
  public int getScore() {
    return my_score;
  }
  
  /**
   * @return the current level.
   */
  public int getLevel() {
    return getScore() / POINTS_PER_LEVEL + 1;
  }
  
  /**
   * @return the game speed in ms based off the level.
   */
  public int getSpeed() {
    return Math.max(GAME_SPEED - DSPEED_PER_LEVEL * (getLevel() - 1), DSPEED_PER_LEVEL);
  }
  
  /**
   * Reset the board to the new game state.
   */
  public void newGame() {
    // The game is not over
    my_game_over = false;
    my_game_paused = false;
    my_score = 0;
    
    // Clear the board and recreate it
    my_board.clear();
    for (int y = 0; y < my_board_height + NEW_PIECE_BUFFER; y++) {
      my_board.add(generateTetrisRow());
    }

    final int center_pos = my_board_width / 2 - TetrisPiece.TETRIS_PIECE_SIZE / 2;
    
    // Setup the future pieces
    my_future_pieces.clear();
    my_future_pieces.addAll(my_testing_pieces);
    
    // If we have none...
    if (my_future_pieces.isEmpty()) {
      // Piece the current piece.
      my_current_piece = TetrisPieces.getRandomPiece(center_pos, INITIAL_Y_COORDINATE);
      
      // And the next piece. 
      my_future_pieces.add(TetrisPieces.getRandomPiece(center_pos, INITIAL_Y_COORDINATE));
    
    } else {
      // Set the current piece to the first one...
      my_current_piece = my_future_pieces.get(0);
      my_future_pieces.remove(1);
    }
    
    // Something changed, if you couldn't tell....
    setChanged();
    notifyObservers(new TetrisBoardEvent(EventTypes.NEW_GAME));
  }

  /**
   * Progress the board.
   */
  public void progressBoard() {
    boolean freeze = false;

    // If we're not playing, don't move.
    if (my_game_over || my_game_paused) {
      return;
    }

    // Move the current piece down one.
    final TetrisPiece new_piece = my_current_piece.translate(0, 1);

    if (validatePosition(new_piece)) {
      // If it's a valid move, do it.
      my_current_piece = new_piece;

    } else {
      // Otherwise, we'll freeze the piece here shortly.
      freeze = true;
    }

    // Do we need to freeze the current piece?
    if (freeze) {
      // Check for end of game conditions
      if (my_current_piece.getY() < INITIAL_Y_COORDINATE + NEW_PIECE_BUFFER) {
        // If the piece is even partially above the board, end the game.
        my_game_over = true;
        setChanged();
        notifyObservers(new TetrisBoardEvent(EventTypes.GAME_OVER));
        
        // Game over... nothing else to do here.
        return;
      }

      // Save the starting row of the current piece.
      final int starting_row = my_current_piece.getY();

      // Freeze the current piece.
      freezeCurrentPiece();

      // Check for filled lines
      checkLines(starting_row);
    }

    // Notify the observers
    setChanged();
    notifyObservers(new TetrisBoardEvent(EventTypes.CURRENT_PIECE_UPDATE));
  }

  /**
   * Move the current piece left.
   */
  public void moveLeft() {
    final TetrisPiece new_piece = my_current_piece.translate(-1, 0);

    if (!my_game_paused && !my_game_over && validatePosition(new_piece)) {
      my_current_piece = new_piece;

      setChanged();
      notifyObservers(new TetrisBoardEvent(EventTypes.CURRENT_PIECE_UPDATE));
    }
  }

  /**
   * Move the current piece right.
   */
  public void moveRight() {
    final TetrisPiece new_piece = my_current_piece.translate(1, 0);

    if (!my_game_paused && !my_game_over && validatePosition(new_piece)) {
      my_current_piece = new_piece;

      setChanged();
      notifyObservers(new TetrisBoardEvent(EventTypes.CURRENT_PIECE_UPDATE));
    }
  }

  /**
   * Move the current piece down.
   */
  public void moveDown() {
    final TetrisPiece new_piece = my_current_piece.translate(0, 1);

    if (!my_game_paused && !my_game_over && validatePosition(new_piece)) {
      my_current_piece = new_piece;

      setChanged();
      notifyObservers(new TetrisBoardEvent(EventTypes.CURRENT_PIECE_UPDATE));
    }
  }
  
  /**
   * Drop the piece.
   */
  public void dropPiece() {
    TetrisPiece new_piece = my_current_piece;
    int number_of_moves = 0;
    
    // Don't drop if the game is paused
    if (my_game_paused || my_game_over) {
      return;
    }
    
    // Move the piece until it can't move no more.
    do {
      new_piece = new_piece.translate(0, 1);
      number_of_moves++;
      
    } while (validatePosition(new_piece));
    
    // That last move didn't work out so well, remove it.
    number_of_moves = number_of_moves - 1;
    
    // If the piece could move, move it.
    if (number_of_moves > 0) {
      // Update the piece
      my_current_piece = my_current_piece.translate(0, number_of_moves);
      
      // Update the score based off the number of lines dropped
      incrementScore(number_of_moves * POINTS_PER_DROPPED_LINE);
      
      // Tell the world
      setChanged();
      notifyObservers(new TetrisBoardEvent(EventTypes.CURRENT_PIECE_UPDATE));
      
    }
  }

  /**
   * Rotate the current piece left.
   */
  public void rotateLeft() {
    final TetrisPiece new_piece = my_current_piece.rotateLeft();

    if (!my_game_paused && !my_game_over && validatePosition(new_piece)) {
      my_current_piece = new_piece;

      setChanged();
      notifyObservers(new TetrisBoardEvent(EventTypes.CURRENT_PIECE_UPDATE));
    }
  }

  /**
   * Rotate the current piece right.
   */
  public void rotateRight() {
    final TetrisPiece new_piece = my_current_piece.rotateRight();

    if (!my_game_paused && !my_game_over && validatePosition(new_piece)) {
      my_current_piece = new_piece;

      setChanged();
      notifyObservers(new TetrisBoardEvent(EventTypes.CURRENT_PIECE_UPDATE));
    }
  }

  /**
   * @return the curent tetris piece.
   */
  public TetrisPiece getCurrentPiece() {
    return my_current_piece;
  }

  /**
   * @return the next tetris piece.
   */
  public TetrisPiece getNextPiece() {
    return my_future_pieces.get(0);
  }

  /**
   * @return An array of Point2Ds for each tetris block;
   */
  public List<IntPoint> getTetrisBlocks() {
    final List<IntPoint> points = new ArrayList<IntPoint>();

    // Add the current piece
    final IntPoint[] block_points = my_current_piece.getBoardCoordinates();

    points.addAll(Arrays.asList(block_points));

    // Add the rest of the board pieces.
    for (int y = 0; y < my_board.size(); y++) {
      final List<Boolean> row = my_board.get(y);

      for (int x = 0; x < row.size(); x++) {
        if (row.get(x).booleanValue()) {
          points.add(new IntPoint(x, y));
        }
      }
    }

    return points;
  }

  /**
   * {@inheritDoc}
   */
  public String toString() {
    final StringBuilder sb = new StringBuilder();

    // Iterate through all the rows
    for (int y = 0; y < my_board_height + NEW_PIECE_BUFFER; y++) {
      // Iterate through each column
      for (int x = 0; x < my_board_width; x++) {

        if (my_board.get(y).get(x).booleanValue()) {
          sb.append('X');

        } else {
          sb.append('.');
        }
      }

      // Add a new line at the end of each row.
      sb.append('\n');
    }

    // Add the current piece
    final IntPoint[] points = my_current_piece.getBoardCoordinates();

    for (int i = 0; i < points.length; i++) {
      final int pos = (my_board_width + 1) * points[i].getY() + points[i].getX();
      sb.replace(pos, pos + 1, "O");
    }

    // Now we add a dashed line after the 4th row
    final StringBuilder dashes = new StringBuilder();
    for (int x = 0; x < my_board_width; x++) {
      dashes.append('-');
    }
    dashes.append('\n');

    sb.insert((my_board_width + 1) * NEW_PIECE_BUFFER, dashes);

    // Is the game over?
    if (my_game_over) {
      sb.append("Game Over\n");
    }

    return sb.toString();
  }
  
  /**
   * Increment the tetris score.
   * 
   * @param the_points to add to the score.
   */
  private void incrementScore(final int the_points) { 
    if (getLevel() > GAME_SPEED / DSPEED_PER_LEVEL) {
      /*
       * Multipler: For any level above the one which results in speed 1,
       * multiply the points to add by the number of levels above that
       * threshold.
       */
      final int multiplier = getLevel() - GAME_SPEED / DSPEED_PER_LEVEL + 1;
      
      my_score += the_points * multiplier;
      
    } else {
      my_score += the_points;
    }
    
    setChanged();
    notifyObservers(new TetrisBoardEvent(EventTypes.SCORE_CHANGED));
  }

  /**
   * Creates a row for the tetris piece list.
   * 
   * @return a list containing an empty row.
   */
  private List<Boolean> generateTetrisRow() {
    final List<Boolean> row = new ArrayList<Boolean>();

    // Loop through the columns.
    for (int x = 0; x < my_board_width; x++) {
      row.add(Boolean.FALSE);
    }

    return row;
  }

  /**
   * Checks if a piece can actually occupy the space it's trying to occupy.
   * 
   * @param the_piece The piece in question.
   * @return true if it's cool, false if it's not.
   */
  private boolean validatePosition(final TetrisPiece the_piece) {
    final IntPoint[] points = the_piece.getBoardCoordinates();

    for (int i = 0; i < points.length; i++) {
      // Check that it's on the board
      if (points[i].getX() < 0 || points[i].getX() >= my_board_width || points[i].getY() < 0 ||
          points[i].getY() >= my_board_height + NEW_PIECE_BUFFER) {
        return false;
      }

      // Check that it doesn't hit another piece.
      if (my_board.get(points[i].getY()).get(points[i].getX()).booleanValue()) {
        return false;
      }
    }

    // Each point passed all tests.
    return true;
  }

  /**
   * Freeze the current piece and set a new one.
   */
  private void freezeCurrentPiece() {
    final IntPoint[] points = my_current_piece.getBoardCoordinates();

    for (int i = 0; i < points.length; i++) {
      my_board.get(points[i].getY()).set(points[i].getX(), Boolean.TRUE);
    }

    // Get a new piece.
    my_current_piece = my_future_pieces.get(0);
    my_future_pieces.remove(0);
    if (my_future_pieces.isEmpty()) {
      final int xpos = (int) (my_board_width / 2 - TetrisPiece.TETRIS_PIECE_SIZE / 2);
      my_future_pieces.add(TetrisPieces.getRandomPiece(xpos, INITIAL_Y_COORDINATE));
    }
  }

  /**
   * Check the TETRIS_PIECE_SIZE lines after a certain row.
   * 
   * @param the_starting_row The row to start at.
   */
  private void checkLines(final int the_starting_row) {
    int last_row;
    int lines_cleared = 0;

    if (the_starting_row + TetrisPiece.TETRIS_PIECE_SIZE > my_board.size()) {
      last_row = my_board.size();

    } else {
      last_row = the_starting_row + TetrisPiece.TETRIS_PIECE_SIZE;
    }

    for (int y = the_starting_row; y < last_row; y++) {
      if (!my_board.get(y).contains(Boolean.FALSE)) {
        my_board.remove(y);

        // Add a new row to the top, after the buffer space.
        my_board.add(TetrisPiece.TETRIS_PIECE_SIZE, generateTetrisRow());

        // Increment lines cleared
        lines_cleared++;
        
        setChanged();
      }
    }
    
    // Notify about the cleared lines (has to come before incrementScore)
    notifyObservers(new TetrisBoardEvent(EventTypes.LINES_CLEARED));
    
    // Update Score
    incrementScore(lines_cleared * POINTS_PER_LINE * lines_cleared);
  }
}
