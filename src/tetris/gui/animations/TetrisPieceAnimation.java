/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 - Autumn 2009
 * Tetris Project
 * 17 November 2009
 */

package tetris.gui.animations;

import tetris.gui.images.ImageRes;
import tetris.model.IntPoint;
import tetris.piece.TetrisPiece;

/**
 * Animation that knows how to animate a tetris piece.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 11 December 2009
 */
public class TetrisPieceAnimation extends Animation {
  /**
   * Size of a brick.
   */
  private static final int BRICK_SIZE = 30;
  
  
  /**
   * Size of the animation area.
   */
  private static final int ANIMATION_SIZE = BRICK_SIZE * 4;
  
  /**
   * The steps of a brick animation.
   */
  private static final IntPoint[] STEPS = {
    new IntPoint(0, 0),
    new IntPoint(26, 0),
    new IntPoint(26, 26),
    new IntPoint(0, 26),
    new IntPoint(0, 0),
    
    new IntPoint(8, 0),
    new IntPoint(0, 8),
    new IntPoint(3, 8),
    new IntPoint(16, 2),
    new IntPoint(4, 20),
    new IntPoint(20, 4),
    new IntPoint(12, 20),
    new IntPoint(24, 14),
    new IntPoint(24, 20),
    new IntPoint(20, 26),
  };
  
  /**
   * A list of tetris piece points.
   */
  private final IntPoint[] my_bricks;
  
  /**
   * Animate the drawing of a tetris piece.
   * 
   * @param the_piece the piece to draw.
   */
  public TetrisPieceAnimation(final TetrisPiece the_piece) {
    super(ImageRes.loadImage(ImageRes.TETRIS_BLOCK), ANIMATION_SIZE, ANIMATION_SIZE);
    
    my_bricks = the_piece.translate(-the_piece.getX(),
                                    -the_piece.getY()).getBoardCoordinates();
  }
  
  @Override
  protected IntPoint getStep(final int the_step) {
    final IntPoint ani_step = STEPS[the_step % STEPS.length];
    final int brick = the_step / STEPS.length;
    
    return  new IntPoint(ani_step.getX() + my_bricks[brick].getX() * BRICK_SIZE,
                        ani_step.getY() + my_bricks[brick].getY() * BRICK_SIZE);
  }

  @Override
  protected int stepCount() {
    return my_bricks.length * STEPS.length;
  }

}
