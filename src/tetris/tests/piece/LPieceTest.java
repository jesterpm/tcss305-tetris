/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 - Autumn 2009
 * Tetris Project
 * 17 November 2009
 */

package tetris.tests.piece;

import tetris.piece.LPiece;
import tetris.piece.TetrisPiece;

/**
 * JUnit Test for the L Piece.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 17 November 2009
 */
public class LPieceTest extends TetrisPieceTest {
  /**
   * Array of the string representations of various rotations.
   */
  private static final String[] ROTATION_STRINGS =
      new String[] {".X..\n.X..\n.XX.\n....", "..X.\nXXX.\n....\n....",
        "XX..\n.X..\n.X..\n....", "....\nXXX.\nX...\n...."};
  
  @Override
  protected int[] getOriginalPoints() {
    return new int[] {1, 0, 1, 1, 1, 2, 2, 2}; // Copied from the class.
  }

  @Override
  protected TetrisPiece getPiece(final int the_x, final int the_y) {
    return new LPiece(the_x, the_y);
  }

  @Override
  protected String[] getRotationStrings() {
    return ROTATION_STRINGS.clone();
  }
 
}
