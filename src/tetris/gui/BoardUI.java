/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 � Autumn 2009
 * Tetris Project
 * 17 November 2009
 */

package tetris.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Observable;

import tetris.board.TetrisBoard;
import tetris.gui.images.ImageRes;

/**
 * Panel that draws the tetris board.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 23 November 2009
 */
@SuppressWarnings("serial")
public class BoardUI extends TetrisPieceDisplay {
  /**
   * Flag to indicate game over.
   */
  private boolean my_game_over;
  
  /**
   * Flag to indicate game paused.
   */
  private boolean my_game_paused;
  
  /**
   * The Game Over graphic.
   */
  private final Image my_gameover_graphic;
  
  /**
   * The Paused graphic.
   */
  private final Image my_paused_graphic;
  
  /**
   * Constructor. 
   * 
   * @param the_width Number of columns.
   * @param the_height Number of rows.
   * 
   */
  public BoardUI(final int the_width, final int the_height) {
    // Call parent
    super(the_width, the_height);
    
    my_game_over = false;
    my_game_paused = false;
    
    my_gameover_graphic = ImageRes.loadImage(ImageRes.GAME_OVER_LABEL);
    
    my_paused_graphic = ImageRes.loadImage(ImageRes.PAUSED_LABEL);
    
  }
  
  /**
   * Update the board.
   * 
   * @param the_observable The tetris board we receive updates for.
   * @param the_argument Unused argument.
   */
  public void update(final Observable the_observable, final Object the_argument) {
    if (the_observable instanceof TetrisBoard) {
      final TetrisBoard tb = (TetrisBoard) the_observable;
      
      my_bricks = tb.getTetrisBlocks();
      my_game_over = tb.isGameOver();
      my_game_paused = tb.isPaused();
      
      repaint();
    }
    
    
  }

  /**
   * Paint Game Over or Paused if needed.
   * 
   * @param the_graphics The Graphics to draw on (expected to be a Graphics2D).
   */
  protected void paintComponent(final Graphics the_graphics) {
    super.paintComponent(the_graphics);
    
    if (my_game_over) {
      the_graphics.drawImage(my_gameover_graphic,
                             0,
                             (getHeight() - my_gameover_graphic.getHeight(null)) / 2,
                             null);
      
    } else if (my_game_paused) {
      the_graphics.drawImage(my_paused_graphic,
                             0,
                             (getHeight() - my_paused_graphic.getHeight(null)) / 2,
                             null);
    }
  }

  
 
}
