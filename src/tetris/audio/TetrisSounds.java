/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 - Autumn 2009
 * Tetris Project
 * 17 November 2009
 */

package tetris.audio;

import java.util.Observable;
import java.util.Observer;

import tetris.board.TetrisBoardEvent;


/**
 * Class to play sounds for a tetris game.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 23 November 2009
 */
public class TetrisSounds implements Observer {
  // Private fields.
  /**
   * The SoundPlayer.
   */
  private final SoundPlayer my_soundplayer;
  
  /**
   * Create a Tetris Sound Effects player.
   */
  public TetrisSounds() {
    my_soundplayer = new SoundPlayer();
  }

  /**
   * Handle notifications.
   * 
   * @param the_observable The TetrisBoard.
   * @param the_arg The Event.
   */
  public void update(final Observable the_observable, final Object the_arg) {
    if (the_arg instanceof TetrisBoardEvent) {
      final TetrisBoardEvent event = (TetrisBoardEvent) the_arg;
      
      try {
        switch (event.getType()) {
          case NEW_GAME:
            my_soundplayer.play("tetris/audio/pencilsharpen.aif");
            break;
            
          case LINES_CLEARED:
            my_soundplayer.play("tetris/audio/pencileraser.wav");
            break;
            
          case GAME_OVER:
            my_soundplayer.play("tetris/audio/papercrumple.wav");
            break;
            
          default:
        }
        
      } catch (final IllegalArgumentException the_exception) {
        // Couldn't play for some reason...
        // Sounds aren't important anyways, gracefully ignore
        return;
      }
    }
  }
  
  
}
