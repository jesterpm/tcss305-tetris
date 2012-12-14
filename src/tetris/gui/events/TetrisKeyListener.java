/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 � Autumn 2009
 * Tetris Project
 * 17 November 2009
 */

package tetris.gui.events;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import tetris.board.TetrisBoard;

/**
 * Keyboard Input handler for Tetris board.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 30 Nov 2009
 */
public class TetrisKeyListener extends KeyAdapter {
  /**
   * The tetris board we update.
   */
  
  private final TetrisBoard my_board;
  
  /**
   * Constructor.
   * 
   * @param the_board The board to update. 
   */
  public TetrisKeyListener(final TetrisBoard the_board) {
    super();
    
    my_board = the_board;
  }
  
  @Override
  public void keyPressed(final KeyEvent the_event) {
    super.keyPressed(the_event);
    
    switch (the_event.getKeyCode()) {
      case KeyEvent.VK_LEFT:
        my_board.moveLeft();
        break;
        
      case KeyEvent.VK_RIGHT:
        my_board.moveRight();
        break;
        
      case KeyEvent.VK_UP:
        my_board.rotateRight();
        break;
        
      case KeyEvent.VK_DOWN:
        my_board.moveDown();
        break;
        
      case KeyEvent.VK_SPACE:
        my_board.dropPiece();
        break;
        
      default:
    }
  }
  
}
