/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 - Autumn 2009 Tetris Project 17 November 2009
 */

package tetris.tests.piece;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import tetris.model.IntPoint;
import tetris.piece.TetrisPiece;

/**
 * Parent JUnit test class for Tetris Pieces.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 17 November 2009
 */
public abstract class TetrisPieceTest {
  // Private Constants
  /**
   * Random X position we use to test.
   */
  private static final int X_POSITION = 5;
  
  /**
   * Random Y position we use to test.
   */
  private static final int Y_POSITION = 3;
  
  /**
   * Number of rotations require to rotate a piece 360 degrees.
   */
  private static final int NUMBER_OF_ROTATIONS = 4;
  
  /**
   * Test that a piece is created properly.
   */
  @Test
  public void testPieceConstruction() {
    final TetrisPiece piece = getPiece(5, 3);
    
    // Test the X and Y coords.
    assertEquals("Inital X", X_POSITION, piece.getX());
    assertEquals("Inital Y", Y_POSITION, piece.getY());
    
    // Test Board Position
    assertTrue("Initial Board Positon Fails",
               testBoardPosition(piece, X_POSITION, Y_POSITION));
  }
  
  /**
   * Test that a piece is the same after four rotations.
   */
  @Test
  public void testPieceAfterRotations() {
    final TetrisPiece before = getPiece(5, 3);
    TetrisPiece after;
    
    // Left rotations
    after = getPiece(X_POSITION, Y_POSITION);
    for (int i = 0; i < NUMBER_OF_ROTATIONS; i++) {
      after = after.rotateLeft();
    }
    
    assertEquals("Pieces differ after 4 left rotations", before.toString(), after.toString());
    
    // Right rotations
    after = getPiece(X_POSITION, Y_POSITION);
    for (int i = 0; i < NUMBER_OF_ROTATIONS; i++) {
      after = after.rotateRight();
    }
    
    assertEquals("Pieces differ after 4 right rotations", before.toString(), after.toString());
  }
  
  /**
   * Test Translations.
   */
  @Test
  public void testTranslations() {
    TetrisPiece piece = getPiece(X_POSITION, Y_POSITION);
    
    // Negative Translation
    piece = piece.translate(-1, -1);
    assertTrue("Negative Translations Fail",
               testBoardPosition(piece, X_POSITION - 1 , Y_POSITION - 1));
    
    // Positive Translation
    piece = piece.translate(1, 1);
    assertTrue("Positive Translations Fail",
               testBoardPosition(piece, X_POSITION - 1 + 1, Y_POSITION - 1 + 1));
  }
  
  /**
   * Test each left rotation String.
   */
  @Test
  public void testLeftRotations() {
    TetrisPiece piece = getPiece(1, 0);

    final String[] rotations = getRotationStrings();
    
    for (int i = 0; i <= NUMBER_OF_ROTATIONS; i++) {
      assertEquals(i + "th Left Rotation Failed",
                   rotations[i % rotations.length], piece.toString());
      
      piece = piece.rotateLeft();
    }
  }
  
  /**
   * Test each right rotation String.
   */
  @Test
  public void testRightRotations() {
    TetrisPiece piece = getPiece(1, 0);

    final String[] rotations = getRotationStrings();
    
    for (int i = NUMBER_OF_ROTATIONS; i >= 0; i--) {
      assertEquals((i - NUMBER_OF_ROTATIONS) + "th Right Rotation Failed",
                   rotations[i % rotations.length], piece.toString());
      
      piece = piece.rotateRight();
    }
  }
  
  /**
   * Tests the board position.
   * @param the_piece The tetris piece.
   * @param the_x Board X.
   * @param the_y Board y.
   * @return true if all points are correct, false otherwise.
   */
  protected boolean testBoardPosition(final TetrisPiece the_piece,
                                   final int the_x, final int the_y) {
    boolean result = true;
    
    final int[] orig_positions = getOriginalPoints();
    final IntPoint[] cur_positions = the_piece.getBoardCoordinates();
    
    // Both sets should contain the same number of points.
    assertSame("Original and Current point counts differ.",
               orig_positions.length, 2 * cur_positions.length);
    
    for (int i = 0; i < orig_positions.length - 1; i = i + 2) {
      // Check that each X and Y was shifted properly.
      if (orig_positions[i] + the_x != cur_positions[i / 2].getX()) {
        result = false;
      }
      
      if (orig_positions[i + 1] + the_y != cur_positions[i / 2].getY()) {
        result = false;
      }
    }
    
    return result;
  }

  /**
   * @param the_x X-coord.
   * @param the_y X-coord.
   * @return Return a tetris piece of the type we're testing.
   */
  protected abstract TetrisPiece getPiece(final int the_x, final int the_y);
  
  /**
   * @return int[] containing the orignal points for the piece.
   */
  protected abstract int[] getOriginalPoints();
  
  /**
   * @return int[] containing the string representation of various rotation states
   */
  protected abstract String[] getRotationStrings();
}
