/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 - Autumn 2009
 * Tetris Project
 * 17 November 2009
 */

package tetris.board;

/**
 * Enumeration of the various event types a TetrisBoard generates.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 11 December 2009.
 */
public enum EventTypes {
  /**
   * New Game Event.
   */
  NEW_GAME,
  
  /**
   * Game Over Event.
   */
  GAME_OVER,
  
  /**
   * Game Paused/Unpaused.
   */
  PAUSE_CHANGED,
  
  /**
   * Lines Cleared.
   */
  LINES_CLEARED,
  
  /**
   * Current piece updated.
   */
  CURRENT_PIECE_UPDATE,
  
  /**
   * Points Changed.
   */
  SCORE_CHANGED
}
