/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 - Autumn 2009
 * Tetris Project
 * 17 November 2009
 */

package tetris.gui.images;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Enumeration of the various image resources.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 23 November 2009
 */
public final class ImageRes {
  /**
   * Game board background image.
   */
  public static final String GAME_BACKGROUND = "background.png";
  
  /**
   * Game over board background image.
   */
  public static final String GAMEOVER_BACKGROUND = "gameover_background.png";
  
  /**
   * Tetris block.
   */
  public static final String TETRIS_BLOCK = "square.png";
  
  /**
   * Game Over wording.
   */
  public static final String GAME_OVER_LABEL = "gameover.png";
  
  /**
   * Game Over wording.
   */
  public static final String PAUSED_LABEL = "paused.png";
  
  /**
   * Writing Pencil.
   */
  public static final String WRITING_PENCIL = "writing_pencil.png";
  
  /**
   * You're not allowed to create an instance of this class.
   */
  private ImageRes() { }
  
  /**
   * Utility to handle loading an image.
   * 
   * @param the_file The filename we need to load.
   * @return The loaded Image or null if the image can't be loaded.
   */
  public static Image loadImage(final String the_file) {
    try {
      return ImageIO.read(ImageRes.class.
                          getResourceAsStream(the_file));
      
    } catch (final IOException the_exception) {
      return null;
    }
  }
}
