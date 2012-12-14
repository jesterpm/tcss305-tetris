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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import tetris.audio.TetrisSounds;
import tetris.board.TetrisBoard;
import tetris.board.TetrisBoardEvent;
import tetris.gui.events.TetrisKeyListener;
import tetris.gui.images.ImageRes;

/**
 * Tetris UI Main Frame.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 23 November 2009
 */
@SuppressWarnings("serial")
public class TetrisGUI extends JFrame implements Observer {
  /**
   * UI Padding at the top of the frame.
   */
  private static final int NORTH_PADDING = 100;
  
  /**
   * UI Padding on the west side of the frame.
   */
  private static final int WEST_PADDING = 75;

  /**
   * The TetrisBoard we're playing on.
   */
  private final TetrisBoard my_board;
  
  /**
   * The gameplay timer.
   */
  private Timer my_timer;
  
  /**
   * Stores the background image panel.
   */
  private final ImagePanel my_background_panel;
  
  /**
   * Constructor for the tetris UI.
   */
  public TetrisGUI() {
    super("TCSS 305 Tetris");
 
    // Setup Global UI Elements
    my_background_panel = new ImagePanel(ImageRes.loadImage(ImageRes.GAME_BACKGROUND));
    add(my_background_panel); 
      
    // Create the board.
    my_board = new TetrisBoard(TetrisBoard.STANDARD_BOARD_WIDTH,
                               TetrisBoard.STANDARD_BOARD_HEIGHT);

    // We Observe the Board.
    my_board.addObserver(this);
    
    // Add a board UI
    final BoardUI board_ui = new BoardUI(TetrisBoard.STANDARD_BOARD_WIDTH,
                                        TetrisBoard.STANDARD_BOARD_HEIGHT);
    my_background_panel.add(board_ui, BorderLayout.CENTER);
    
    my_board.addObserver(board_ui);
    
    // Add an InfoPanel
    final InfoPanel info = new InfoPanel(my_board);
    my_background_panel.add(info, BorderLayout.EAST);
    
    // Create a tetris sound player
    final TetrisSounds ts = new TetrisSounds();
    my_board.addObserver(ts);
    
    setupFrame();
    setupUIPadding();
    setupListeners();
  }
  
  /**
   * Start the game.
   */
  public void start() {
    my_board.setPaused(false);
    my_timer.start();
  }
  
  /**
   * Pause the game.
   */
  public void pause() {
    my_board.setPaused(true);
    my_timer.stop();
  }
  
  /**
   * Update the level and score.
   * 
   * @param the_observable Should be an instance of TetrisBoard
   * @param the_arg Unused by this method.
   */
  public void update(final Observable the_observable, final Object the_arg) {
    if (the_observable instanceof TetrisBoard) {
      final TetrisBoard tb = (TetrisBoard) the_observable;
      final TetrisBoardEvent event = (TetrisBoardEvent) the_arg;
      
      switch (event.getType()) {
        case NEW_GAME:  
          my_background_panel.setBackgroundImage(ImageRes.loadImage(ImageRes.GAME_BACKGROUND));
          repaint();
          start();
          // Purposefully falling through....

        case SCORE_CHANGED:
          my_timer.setDelay(tb.getSpeed());
          break;
          
        case GAME_OVER:
          my_background_panel.setBackgroundImage(ImageRes.
                                                 loadImage(ImageRes.GAMEOVER_BACKGROUND)); 
          repaint();
          break;
        
        default:
      }
    }
  }
  
  /**
   * Game Entry Point.
   * 
   * @param the_args Unused command line arguments.
   */
  public static void main(final String[] the_args) {
    final TetrisGUI ui = new TetrisGUI();
    
    ui.setVisible(true);
  }
  
  /**
   * Helper method to setup various frame attributes.
   */
  private void setupFrame() {
    // Set frame attributes
    setSize(my_background_panel.getPreferredSize());
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
  
  /**
   * Helper method to setup the padding on the UI.
   */
  private void setupUIPadding() {
    // North Side
    final JPanel north_panel = new JPanel();
    north_panel.setOpaque(false);
    north_panel.setPreferredSize(new Dimension(0, NORTH_PADDING));
    
    // West Side
    final JPanel west_panel = new JPanel();
    west_panel.setOpaque(false);
    west_panel.setPreferredSize(new Dimension(WEST_PADDING, 0));
    
    my_background_panel.add(north_panel, BorderLayout.NORTH);
    my_background_panel.add(west_panel, BorderLayout.WEST);
  }
  
  /**
   * Helper methods to setup event listeners.
   */
  private void setupListeners() {
    // Add Key Listener
    addKeyListener(new TetrisKeyListener(my_board));
    
    // Add Window Listener
    addWindowListener(new TetrisWindowListener());
    
    // Add a timer
    my_timer = new Timer(my_board.getSpeed(), new ActionListener() {
      public void actionPerformed(final ActionEvent the_event) {
        my_board.progressBoard();
      }
    }); 
  }
  
  /**
   * Starts and stops the timer based off of WindowEvents.
   * 
   * @author Jesse Morgan <jesterpm@u.washington.edu>
   * @version 1.0 1 Dec 2009
   */
  private class TetrisWindowListener extends WindowAdapter {
    /**
     * Flag to store if we lost focus.
     */
    private boolean my_lost_focus_flag;
    
    /**
     * Create the TetrisWindowListener.
     */
    public TetrisWindowListener() {
      super();
      my_lost_focus_flag = false;
    }
    
    @Override
    public void windowActivated(final WindowEvent the_event) {
      super.windowActivated(the_event);
      if (my_lost_focus_flag && my_board.isPaused()) {
        start();
        my_lost_focus_flag = false;
      }
    }

    @Override
    public void windowClosed(final WindowEvent the_event) {
      super.windowClosed(the_event);
      pause();
    }

    @Override
    public void windowDeactivated(final WindowEvent the_event) {
      super.windowDeactivated(the_event);
      
      // Don't pause if we're already paused.
      if (!my_board.isPaused()) {
        my_lost_focus_flag = true;
        pause();
      }
    }
  }
}
