/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 - Autumn 2009
 * Tetris Project
 * 17 November 2009
 */

package tetris.gui.animations;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import tetris.model.IntPoint;


/**
 * Abstract class that processes animations.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 11 Dec 2009
 */
public abstract class Animation {
  // Public Constants
  /**
   * How quickly animation should be updated.
   */
  public static final int FRAME_RATE = 60;
  
  // Private Constants
  /**
   * How much we show in each step.
   */
  private static final int R = 5;
  
  /**
   * The current step in the animation.
   */
  private int my_step;
  
  /**
   * The current position in the animation.
   */
  private IntPoint my_current_pos;
  
  /**
   * Current Step Point.
   */
  private IntPoint my_step_point;
  
  /**
   * Animation Running Flag.
   */
  private boolean my_animation_running;
  
  /**
   * What the animation presently looks like.
   */
  private final BufferedImage my_image;
  
  /**
   * What the animation will look like.
   */
  private final Image my_end_image;
  
  /**
   * Create a new animation.
   * 
   * @param the_end_image End result.
   * @param the_width Animation width.
   * @param the_height Animation height.
   */
  public Animation(final Image the_end_image, final int the_width, final int the_height) {
    my_step = 0;
    my_animation_running = false;
    
    my_current_pos = new IntPoint(0, 0);
    my_step_point = new IntPoint(0, 0);
    
    my_end_image = the_end_image;
    my_image = new BufferedImage(the_width, the_height,
                                 BufferedImage.TYPE_INT_ARGB);
    
  }
  
  /**
   * Start the animation.
   */
  public void start() {
    my_step = 0;
    
    my_current_pos = getStep(0);
    my_step_point = getStep(0);
    
    my_animation_running = true;
  }
  
  /**
   * Stop the animation.
   */
  public void stop() {
    my_animation_running = false;
  }
  
  /**
   * @return true if the animation is running.
   */
  public boolean isRunning() {
    return my_animation_running;
  }
  
  /**
   * Draw the next increment of the animation.
   */
  public void stepAnimation() {
    // If we're near our destination, move to the next step.
    if (
        Math.abs(my_current_pos.getX() - my_step_point.getX()) < R &&
        Math.abs(my_current_pos.getY() - my_step_point.getY()) < R
    ) {     
      // If we're out of steps, stop.
      if (my_step >= stepCount() - 1) {
        my_animation_running = false;
        return;
      }
      
      my_step++;
      my_step_point = getStep(my_step);
    }
    
    // Draw!
    final Graphics2D g2d = my_image.createGraphics();
    
    g2d.drawImage(my_end_image,
                  my_current_pos.getX(), my_current_pos.getY(),
                  my_current_pos.getX() + R, my_current_pos.getY() + R,
                  
                  my_current_pos.getX() % my_end_image.getWidth(null),
                  my_current_pos.getY() % my_end_image.getHeight(null),
                  (my_current_pos.getX() + R) % my_end_image.getWidth(null),
                  (my_current_pos.getY() + R)  % my_end_image.getHeight(null),
                  null);
    
    // Some math to draw in the correct "direction"
    final int x = my_step_point.getX() - my_current_pos.getX();
    final int y = my_step_point.getY() - my_current_pos.getY();
    double theta = Math.asin(y / Math.sqrt(x * x + y * y));
    
    // Correction if we're moving toward the Y axis.
    if (x < 0) {
      theta = -theta + Math.PI; 
    }
    
    // Calculate the next point
    my_current_pos = new IntPoint((int) (my_current_pos.getX() + R * Math.cos(theta)),
                               (int) (my_current_pos.getY() + R * Math.sin(theta)));
    
  }
  
  /**
   * @return the image of the current animation.
   */
  public Image getImage() {
    return my_image;
  }
  
  /**
   * @return the current X in the animation.
   */
  public int getX() {
    return my_current_pos.getX();
  }
  
  /**
   * @return the current Y in the animation.
   */
  public int getY() {
    return my_current_pos.getY();
  }
  
  /**
   * Returns the point at the given step.
   * @param the_step The Step.
   * @return the Point.
   */
  protected abstract IntPoint getStep(final int the_step);
  
  /**
   * @return the number of steps in the animation.
   */
  protected abstract int stepCount();
}
