/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 - Autumn 2009
 * Tetris Project
 * 17 November 2009
 */

package tetris.gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tetris.board.TetrisBoard;

/**
 * JPanel of info about the game.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 11 December 2009
 */
@SuppressWarnings("serial")
public class InfoPanel extends JPanel implements Observer {
  // Private Constants
  /**
   * The wording of the new game button.
   */
  private static final String NEWGAME_LABEL = "New Game";
  
  /**
   * The wording of the pause button.
   */
  private static final String PAUSE_LABEL = "Pause";
  
  /**
   * The wording of the unpause button.
   */
  private static final String UNPAUSE_LABEL = "Unpause";
  
  // Obnoxious UI layout constants follow
  /**
   * Space between the top of the panel and the Score.
   */
  private static final int UPPER_SPACING = 18;
  
  /**
   * Space between the level and the next piece.
   */
  private static final int MIDDLE_SPACING = 38;
  
  /**
   * Size of the score.
   */
  private static final float SCORE_FONT_SIZE = 32f;
  
  /**
   * Size of the level.
   */
  private static final float LEVEL_FONT_SIZE = 35f;
  
  /**
   * The TetrisBoard we're displaying info for. 
   */
  private final TetrisBoard my_board;
  
  /**
   * The score label.
   */
  private final JLabel my_score_label;
  
  /**
   * The level label.
   */
  private final JLabel my_level_label;
  
  /**
   * Construct the Info Panel.
   * 
   * @param the_board The tetris board we belong to.
   */
  public InfoPanel(final TetrisBoard the_board) {
    super();
    
    my_board = the_board;
    the_board.addObserver(this);
    
    // Setup Panel
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setOpaque(false);
    
    // Create Components
    my_score_label = new JLabel("0");
    my_level_label = new JLabel("Level 1");
    
    setupPanel();
  }
 
  /**
   * Handle notifications.
   * 
   * @param the_observable The TetrisBoard.
   * @param the_arg Unused argument.
   */
  public void update(final Observable the_observable, final Object the_arg) {
    if (the_observable instanceof TetrisBoard) {
      final TetrisBoard tb = (TetrisBoard) the_observable;
      
      my_score_label.setText(String.valueOf(tb.getScore()));
      
      my_level_label.setText("Level " + tb.getLevel());
    }
  }
  
  
  /**
   * Helper method to setup the east side.
   */
  private void setupPanel() {   
    Font base_font;
    
    // Load the font.
    try {
      final InputStream fis = getClass().getResourceAsStream("/tetris/gui/handwriting.ttf");
      base_font = Font.createFont(Font.TRUETYPE_FONT, fis);
      fis.close();
      
    } catch (final FontFormatException the_exception) {
      base_font = Font.getFont(Font.SANS_SERIF);
      
    } catch (final IOException the_exception) {
      base_font = Font.getFont(Font.SANS_SERIF);
    }
   
    // Add a Strut to move everything onto a line.
    add(Box.createVerticalStrut(UPPER_SPACING));
    
    // Display the Score  
    my_score_label.setFont(base_font.deriveFont(SCORE_FONT_SIZE));
    my_score_label.setAlignmentX(CENTER_ALIGNMENT);
    add(my_score_label);

    
    // Display the level
    my_level_label.setFont(base_font.deriveFont(LEVEL_FONT_SIZE));
    my_level_label.setAlignmentX(CENTER_ALIGNMENT);
    add(my_level_label);
    
    // Add a bit of space between the level and the next piece display.
    add(Box.createVerticalStrut(MIDDLE_SPACING));
    
    // Add the next piece component
    final NextPieceDisplay nextpiece = new NextPieceDisplay();
    add(nextpiece);
    nextpiece.setAlignmentX(CENTER_ALIGNMENT);
    my_board.addObserver(nextpiece);
    
 
    
    // New Game Button
    final WrittenButton new_game = new WrittenButton();
    new_game.setText(NEWGAME_LABEL);
    new_game.setAlignmentX(CENTER_ALIGNMENT);
    add(new_game);
   
    // Pause Game Button
    final WrittenButton pause_game = new WrittenButton();
    pause_game.setText(PAUSE_LABEL);
    pause_game.setAlignmentX(CENTER_ALIGNMENT);
    add(pause_game);
    
    // Setup Action Listeners
    
    // New Game
    new_game.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent the_event) {
        my_board.newGame();        
      }
    });
    
    // Pause Game
    pause_game.addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent the_event) {
        final WrittenButton b = (WrittenButton) the_event.getSource();
        
        // Do nothing if the game is over.
        if (my_board.isGameOver()) {
          return;
        }
        
        if (b.getText().equals(PAUSE_LABEL)) {
          my_board.setPaused(true);
          b.setText(UNPAUSE_LABEL);
          
        } else {
          my_board.setPaused(false);
          b.setText(PAUSE_LABEL);
        }
      }
    });
  }
}
