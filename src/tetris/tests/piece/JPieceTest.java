/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 - Autumn 2009
 * Tetris Project
 * 17 November 2009
 */

package tetris.tests.piece;

import tetris.piece.JPiece;
import tetris.piece.TetrisPiece;

/**
 * JUnit Test for the J Piece.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 17 November 2009
 */
public class JPieceTest extends TetrisPieceTest {
  /**
   * Array of the string representations of various rotations.
   */
  private static final String[] ROTATION_STRINGS =
      new String[] {".X..\n.X..\nXX..\n....", "....\nXXX.\n..X.\n....",
        ".XX.\n.X..\n.X..\n....", "X...\nXXX.\n....\n...."};
  
  @Override
  protected int[] getOriginalPoints() {
    return new int[] {1, 0, 1, 1, 1, 2, 0, 2}; // Copied from the class.
  }

  @Override
  protected TetrisPiece getPiece(final int the_x, final int the_y) {
    return new JPiece(the_x, the_y);
  }

  @Override
  protected String[] getRotationStrings() {
    return ROTATION_STRINGS.clone();
  }
}
