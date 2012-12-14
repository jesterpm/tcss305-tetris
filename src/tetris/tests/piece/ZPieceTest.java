/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 Ð Autumn 2009
 * Tetris Project
 * 17 November 2009
 */

package tetris.tests.piece;

import tetris.piece.TetrisPiece;
import tetris.piece.ZPiece;

/**
 * JUnit Test for the Z Piece.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 17 November 2009
 */
public class ZPieceTest extends TetrisPieceTest {
  /**
   * Array of the string representations of various rotations.
   */
  private static final String[] ROTATION_STRINGS =
      new String[] {"....\nXX..\n.XX.\n....", ".X..\nXX..\nX...\n...."};

  @Override
  protected int[] getOriginalPoints() {
    return new int[] {0, 1, 1, 1, 1, 2, 2, 2}; // Copied from the class.
  }

  @Override
  protected TetrisPiece getPiece(final int the_x, final int the_y) {
    return new ZPiece(the_x, the_y);
  }

  @Override
  protected String[] getRotationStrings() {
    return ROTATION_STRINGS.clone();
  }
  
}
