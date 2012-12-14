/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 - Autumn 2009
 * Tetris Project
 * 17 November 2009
 */

package tetris.board;

/**
 * Base class for tetris board events.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 11 December 2009.
 */
public class TetrisBoardEvent {
  /**
   * The type of this event.
   */
  private final EventTypes my_event;
  
  /**
   * Create an event.
   * @param the_event The event type.
   */
  public TetrisBoardEvent(final EventTypes the_event) {
    my_event = the_event;
  }
  
  /**
   * @return the associated EventType.
   */
  public EventTypes getType() {
    return my_event;
  }
}
