/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 - Autumn 2009
 * Tetris Project
 * 17 November 2009
 */

package tetris.model;

/**
 * Class to represent an int point in two-space.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 17 November 2009
 */
public class IntPoint {
  /**
   * The x-coordinate.
   */
  private final int my_x;
  
  /**
   * The y-coordinate.
   */
  private final int my_y;
  
  /**
   * Create a new point.
   * 
   * @param the_x The x-coordinate.
   * @param the_y The y-coordinate.
   */
  public IntPoint(final int the_x, final int the_y) {
    my_x = the_x;
    my_y = the_y;
  }
  
  /**
   * @return The x-coordinate.
   */
  public int getX() {
    return my_x;
  }
  
  /**
   * @return The y-coordinate.
   */
  public int getY() {
    return my_y;
  }

  /**
   * Compare IntPoint to another object.
   * @param the_other The object to compare to.
   * @return True if x and y are the same. False otherwise.
   */
  public boolean equals(final Object the_other) {
    boolean result = false;

    if (the_other != null && the_other.getClass() == getClass()) {
      final IntPoint other_point = (IntPoint) the_other;

      if (getX() == other_point.getX() && getY() == other_point.getY()) {
        result = true;
      }
    }

    return result;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  @Override
  public String toString() {
    return "(" + getX() + "," + getY() + ")";
  }
  
  
}
