/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 - Autumn 2009
 * Tetris Project
 * 17 November 2009
 */

package tetris.piece;

/**
 * Class to represent a Tetris T piece.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 17 November 2009
 */
public class TPiece extends TetrisPiece {
  // Private Constants
  /**
   * X Rotation Point.
   */
  private static final double ROTATION_X = 1;
  
  /**
   * Y Rotation Point.
   */
  private static final double ROTATION_Y = 1.5;

  /**
   * Setup the T Piece.
   * 
   * @param the_x Tetris piece X-coord.
   * @param the_y Tetris piece Y-coord.
   */
  public TPiece(final int the_x, final int the_y) {
    super(the_x, the_y, new double[] {1, 1, 0, 2, 1, 2, 2, 2}, ROTATION_X, ROTATION_Y);
  }
}
