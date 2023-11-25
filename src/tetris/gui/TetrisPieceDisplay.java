/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 - Autumn 2009
 * Tetris Project
 * 17 November 2009
 */

package tetris.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import javax.swing.JPanel;

import tetris.board.TetrisBoard;
import tetris.gui.images.ImageRes;
import tetris.model.IntPoint;

/**
 * Abstract component capable of drawing out tetris pieces.
 * 
 * @author Jesse Morgan <jesse@jesterpm.net>
 * @version 1.0 1 Dec 2009
 */
@SuppressWarnings("serial")
public abstract class TetrisPieceDisplay extends JPanel implements Observer {
  // Private contants
  /**
   * Pixel size of one tetris piece.
   */
  private static final int BRICK_SIZE = 27;
  
  //Private fields
  /**
   * List of the bricks on the screen.
   */
  protected List<IntPoint> my_bricks;
  
  /**
   * Storage for the brick image.
   */
  private final Image my_brick_image;
  
  /**
   * Constructor. 
   * 
   * @param the_width Number of columns.
   * @param the_height Number of rows.
   */
  public TetrisPieceDisplay(final int the_width, final int the_height) {
    // Call parent
    super();
    
    // Load brick image
    my_brick_image = ImageRes.loadImage(ImageRes.TETRIS_BLOCK);
    
    // Create our brick list
    my_bricks = new ArrayList<IntPoint>();
    
    // Set some hints for the layout manager
    final Dimension d = new Dimension(the_width * my_brick_image.getWidth(null),
                                the_height * my_brick_image.getHeight(null));
    setPreferredSize(d);
    setMinimumSize(d);
    setMaximumSize(d);
    setOpaque(false);
  }

  @Override
  protected void paintComponent(final Graphics the_graphics) {
    super.paintComponent(the_graphics);
   
    for (IntPoint brick : my_bricks) {
      the_graphics.drawImage(my_brick_image,
                             brick.getX() * BRICK_SIZE,
                             (brick.getY() - TetrisBoard.NEW_PIECE_BUFFER) * BRICK_SIZE,
                              null);
    }
  }
}
