package tetris.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.AbstractButton;
import javax.swing.JLabel;

import tetris.gui.images.ImageRes;

/**
 * WrittenButton is a button with a handwritten font and a pencil roll-over.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 8 December 2009.
 */
@SuppressWarnings("serial")
public class WrittenButton extends AbstractButton {
  /**
   * The pencil icon used in the roll-over.
   */
  private static final Image PENCIL = ImageRes.loadImage(ImageRes.WRITING_PENCIL);
  
  /**
   * Default font size.
   */
  private static final float DEFAULT_FONT_SIZE = 36;
  
  /**
   * How much pencil to show.
   */
  private static final int PENCIL_WIDTH = 50;
  
  /**
   * An adjustment to the Y of the pencil.
   */
  private static final int PENCIL_ADJUSTMENT = 10;
  
  /**
   * The button label.
   */
  private final JLabel my_label; 
  
  /**
   * Is the mouse over the button?
   */
  private boolean my_rolledover;
  
  /**
   * Default constrcutor.
   */  
  public WrittenButton() {
    super();
    
    my_rolledover = false;
    
    // Setup Button
    addMouseListener(new MouseHandler());
    setOpaque(false);
    
    // Setup Label
    my_label = new JLabel();
    add(my_label);
  }

  @Override
  public void setText(final String the_label) {
    super.setText(the_label);

    Font base_font;
    
    try {
      final InputStream fis = getClass().getResourceAsStream("/tetris/gui/handwriting.ttf");
      base_font = Font.createFont(Font.TRUETYPE_FONT, fis);
      fis.close();
      
    } catch (final FontFormatException the_exception) {
      base_font = Font.getFont(Font.SANS_SERIF);
      
    } catch (final IOException the_exception) {
      base_font = Font.getFont(Font.SANS_SERIF);
    }
    
    my_label.setText(the_label);
    my_label.setFont(base_font.deriveFont(DEFAULT_FONT_SIZE));
    
    // Set some hints for the layout manager.
    final Dimension ls = my_label.getPreferredSize();
    ls.width += PENCIL_WIDTH;
    setPreferredSize(ls);
    setMinimumSize(ls);
    setMaximumSize(ls);
  }
  
  @Override
  public String getText() {
    return my_label.getText();
  }
  
  @Override
  protected void paintComponent(final Graphics the_graphics) {
    super.paintComponent(the_graphics);
    
    if (my_rolledover) {
      final int x = my_label.getX() + my_label.getWidth();
      final int y = my_label.getY() + my_label.getHeight() -
                    PENCIL.getHeight(null) - PENCIL_ADJUSTMENT;

      the_graphics.drawImage(PENCIL, x, y, null);
    }
  }
  
  /**
   * Mouse handler for the button.
   * 
   * @author Jesse Morgan <jesterpm@u.washington.edu>
   * @version 1.0 8 Dec 2009
   */
  private class MouseHandler extends MouseAdapter {
    @Override
    public void mouseEntered(final MouseEvent the_event) {
      super.mouseEntered(the_event);
      
      my_rolledover = true;
      repaint();
    }
    
    @Override
    public void mouseExited(final MouseEvent the_event) {
      super.mouseExited(the_event);
      
      my_rolledover = false;
      repaint();
    }
    
    @Override
    public void mouseClicked(final MouseEvent the_event) {
      super.mouseClicked(the_event);
      
      final ActionEvent e = new ActionEvent(WrittenButton.this,
                                      ActionEvent.ACTION_PERFORMED,
                                      getText());
      
      WrittenButton.this.fireActionPerformed(e);
    }
  }
}
