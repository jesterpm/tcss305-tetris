/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 - Autumn 2009
 * Tetris Project
 * 17 November 2009
 */

package tetris.piece;

/**
 * Class to represent a Tetris I piece.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 17 November 2009
 */
public class IPiece extends TetrisPiece {
  // Private Constants
  /**
   * X Rotation Point.
   */
  private static final double ROTATION_X = 1.5;
  
  /**
   * Y Rotation Point.
   */
  private static final double ROTATION_Y = 2;
  
  /**
   * Array of of points for the I Piece (since it contains a 3).
   */
  private static final double[] POINTS = new double[] {0, 2, 1, 2, 2, 2, 3, 2};
  
  /**
   * Setup the I Piece.
   * 
   * @param the_x Tetris piece X-coord.
   * @param the_y Tetris piece Y-coord.
   */
  public IPiece(final int the_x, final int the_y) {
    super(the_x, the_y, POINTS, ROTATION_X, ROTATION_Y);
  }
}
