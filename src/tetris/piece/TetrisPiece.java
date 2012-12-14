/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 - Autumn 2009
 * Tetris Project
 * 17 November 2009
 */

package tetris.piece;

import java.awt.geom.AffineTransform;

import tetris.model.IntPoint;

/**
 * Base Class to Represent a tetris piece.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 17 November 2009
 */
public class TetrisPiece {
  // Public constants
  /**
   * Width and Height of a Tetris piece.
   */
  public static final int TETRIS_PIECE_SIZE = 4;
  
  // Private Constants
  /**
   * Number of characters in each toString line.
   */
  private static final int CHARS_PER_TOSTRING_LINE = 5;
  
  // Private Instance Fields
  /**
   * Storage for the piece points.
   */
  private final double[] my_points;

  /**
   * Our X-coordinate.
   */
  private final int my_x;

  /**
   * Our Y-coordinate.
   */
  private final int my_y;
  
  /**
   * Our X-coordinate for the point of rotation.
   */
  private final double my_rotatex;

  /**
   * Our Y-coordinate for the point of rotation.
   */
  private final double my_rotatey;

  /**
   * Setup the AbstractTetrisPiece.
   * 
   * @param the_x Tetris piece X-coord.
   * @param the_y Tetris piece Y-coord.
   * @param the_points The points that make up this piece.
   * @param the_rotatex X-Coordinate to rotate the piece about.
   * @param the_rotatey Y-Coordinate to rotate the piece about.
   */
  protected TetrisPiece(final int the_x, final int the_y, final double[] the_points,
                     final double the_rotatex, final double the_rotatey) {
    my_x = the_x;
    my_y = the_y;
    
    my_points = the_points.clone();
    my_rotatex = the_rotatex;
    my_rotatey = the_rotatey;
  }

  /**
   * Rotates the tetris piece counter-clockwise 90 degrees.
   * 
   * @return the rotated piece.
   */
  public TetrisPiece rotateLeft() {
    return rotate(-Math.PI / 2);
  }

  /**
   * Rotates the tetris piece clockwise 90 degrees.
   * 
   * @return the rotated piece.
   */
  public TetrisPiece rotateRight() {
    return rotate(Math.PI / 2);
  }

  /**
   * Move the tetris piece.
   * 
   * @param the_dx Movement change along the X-axis.
   * @param the_dy Movement change along the Y-axis.
   * @return the moved piece.
   */
  public TetrisPiece translate(final int the_dx, final int the_dy) {
    return new TetrisPiece(my_x + the_dx, my_y + the_dy, my_points, my_rotatex, my_rotatey);
  }
  
  /**
   * @return The X position.
   */
  public int getX() {
    return my_x;
  }
  
  /**
   * @return The Y position.
   */
  public int getY() {
    return my_y;
  }
  
  /**
   * @return An int[] containing the piece's points in board-space.
   * Sorted x1, y1, x2, y2, ..., xN, yN (N = number of points).
   */
  public IntPoint[] getBoardCoordinates() {
    final IntPoint[] result = new IntPoint[my_points.length / 2];
    
    for (int i = 0; i < my_points.length - 1; i = i + 2) {
      final int x = (int) my_points[i];
      final int y = (int) my_points[i + 1];
      
      // i is always even.
      result[i / 2] = new IntPoint(x + my_x, y + my_y);
    }
    
    return result;
  }
  
  /**
   * {@inheritDoc}
   */
  public String toString() {
    final char[] output = "....\n....\n....\n....".toCharArray();
    
    for (int i = 0; i < my_points.length - 1; i = i + 2) {
      // For each point, mark the coresponding element true
      final int x = (int) my_points[i];
      final int y = (int) my_points[i + 1];
      output[x + CHARS_PER_TOSTRING_LINE * y] = 'X';
    }
    
    return new String(output);
  }
  
  /**
   * Rotates the tetris piece by the_degrees.
   * 
   * @param the_degrees The degrees in radians to rotate the piece.
   * @return the rotated piece.
   */
  private TetrisPiece rotate(final double the_degrees) {
    final AffineTransform trans = AffineTransform.getRotateInstance(the_degrees,
                                                                    my_rotatex, my_rotatey);
    final double[] new_points = new double[my_points.length];

    trans.transform(my_points, 0, new_points, 0, my_points.length / 2);

    return new TetrisPiece(my_x, my_y, new_points, my_rotatex, my_rotatey);
  }
}
