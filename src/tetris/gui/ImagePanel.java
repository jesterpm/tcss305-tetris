/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 - Autumn 2009
 * Tetris Project
 * 17 November 2009
 */

package tetris.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * Custom panel that displays an image in the background.
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 4 Dec 2009
 */
@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
  /**
   * The image that contains the background.
   */
  private Image my_image;
  
  /**
   * Constructor. Defaults to BorderLayout.
   * @param the_image The background Image.
   */
  public ImagePanel(final Image the_image) {
    super();
    
    setLayout(new BorderLayout());
    
    my_image = the_image;
    setPreferredSize(new Dimension(the_image.getWidth(null), the_image.getHeight(null)));
  }
  
  /**
   * @return the current background Image.
   */
  public Image getBackgroundImage() {
    return my_image;
  }
  
  /**
   * Set a new background Image.
   * 
   * @param the_image the new Image.
   */
  public void setBackgroundImage(final Image the_image) {
    my_image = the_image;
    setPreferredSize(new Dimension(the_image.getWidth(null), the_image.getHeight(null)));
  }
  
  /**
   * Draws the background image.
   * 
   * @param the_graphics The Graphics Context.
   */
  public void paintComponent(final Graphics the_graphics) {
    setOpaque(false);
    the_graphics.drawImage(my_image, 0, 0, null);
    super.paintComponent(the_graphics);
  }
}