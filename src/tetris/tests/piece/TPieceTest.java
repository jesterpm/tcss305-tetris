/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 Ð Autumn 2009
 * Tetris Project
 * 17 November 2009
 */

package tetris.tests.piece;

import tetris.piece.TPiece;
import tetris.piece.TetrisPiece;

/**
 * JUnit Test for the T Piece.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 17 November 2009
 */
public class TPieceTest extends TetrisPieceTest {
  /**
   * Array of the string representations of various rotations.
   */
  private static final String[] ROTATION_STRINGS =
      new String[] {"....\n.X..\nXXX.\n....", ".X..\nXX..\n.X..\n....",
        "....\nXXX.\n.X..\n....", "X...\nXX..\nX...\n...."};

  @Override
  protected int[] getOriginalPoints() {
    return new int[] {1, 1, 0, 2, 1, 2, 2, 2}; // Copied from the class.
  }

  @Override
  protected TetrisPiece getPiece(final int the_x, final int the_y) {
    return new TPiece(the_x, the_y);
  }

  @Override
  protected String[] getRotationStrings() {
    return ROTATION_STRINGS.clone();
  }
 
}
