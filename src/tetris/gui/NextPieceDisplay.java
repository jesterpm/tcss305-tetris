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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.Timer;

import tetris.board.TetrisBoard;
import tetris.gui.animations.TetrisPieceAnimation;
import tetris.gui.images.ImageRes;
import tetris.model.IntPoint;
import tetris.piece.TPiece;
import tetris.piece.TetrisPiece;

/**
 * Panel that draws the tetris board.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 1 Dec 2009
 */
@SuppressWarnings("serial")
public class NextPieceDisplay extends TetrisPieceDisplay {
  /**
   * The Pencil Graphic.
   */
  private static final Image PENCIL = ImageRes.loadImage(ImageRes.WRITING_PENCIL);
  
  
  /**
   * The next piece.
   */
  private TetrisPiece my_next_piece;
  
  /**
   * Our animator.
   */
  private TetrisPieceAnimation my_animation;
  
  /**
   * Animation timer.
   */
  private final Timer my_timer;
  
 
  /**
   * Constructor. 
   */
  public NextPieceDisplay() {
    // Call parent
    super(TetrisPiece.TETRIS_PIECE_SIZE, TetrisPiece.TETRIS_PIECE_SIZE);
    
    my_animation = new TetrisPieceAnimation(new TPiece(0, 0));
    
    my_timer = new Timer(TetrisPieceAnimation.FRAME_RATE, new ActionListener() {
      public void actionPerformed(final ActionEvent the_event) {
        my_animation.stepAnimation();
        repaint();
      }
    });
  }
  
  /**
   * Update the next piece display.
   * 
   * @param the_observable The tetris board we receive updates for.
   * @param the_argument Unused argument.
   */
  public void update(final Observable the_observable, final Object the_argument) {
    if (the_observable instanceof TetrisBoard) {
      final TetrisBoard tb = (TetrisBoard) the_observable;
      
      // If the next piece we know about is not the same next piece...
      if (tb.getNextPiece() != my_next_piece) {
        my_next_piece = tb.getNextPiece();
        my_bricks.clear();
        
        for (IntPoint p : my_next_piece.getBoardCoordinates()) {
          my_bricks.add(new IntPoint(p.getX() - my_next_piece.getX(),
                                     p.getY() - my_next_piece.getY() +
                                     TetrisBoard.NEW_PIECE_BUFFER));
        }
        
        my_animation = new TetrisPieceAnimation(my_next_piece);
        startAnimation();
      }
    
      repaint();
    }
  }
  
  @Override
  protected void paintComponent(final Graphics the_graphics) {
    super.paintComponent(the_graphics);
    
    if (my_animation.isRunning()) {
      final int x = my_animation.getX();
      final int y = my_animation.getY() - PENCIL.getHeight(null);
      the_graphics.drawImage(PENCIL, x, y, null);
      // Normally you'd draw the animation image here, but we just want the pencil to move.
    }
  }
  
  /**
   * Start the animation and timer.
   */
  private void startAnimation() {
    my_animation.start();
    my_timer.start();
  }

  
 
}
