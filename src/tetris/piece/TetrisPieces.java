/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 Ð Autumn 2009
 * Tetris Project
 * 17 November 2009
 */

package tetris.piece;

import java.util.Random;

/**
 * Enumeration of the various tetris pieces.
 * 
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 23 November 2009
 */
public enum TetrisPieces {
  // Enumeration definition.
  /**
   * The I Piece.
   */
  I_PIECE,
  
  /**
   * The J Piece.
   */
  J_PIECE,
  
  /**
   * The L Piece.
   */
  L_PIECE,
  
  /**
   * The O Piece.
   */
  O_PIECE,
  
  /**
   * The S Piece.
   */
  S_PIECE,
  
  /**
   * The T Piece.
   */
  T_PIECE,
  
  /**
   * The Z Piece.
   */
  Z_PIECE;
  
  // Private Constants
  /**
   * A Random that we use for generating random pieces.
   */
  private static final Random RANDOM = new Random();
  
  /**
   * @param the_x X coordinate for this piece.
   * @param the_y Y coordinate for this piece.
   * @return a random tetris piece.
   */
  public static TetrisPiece getRandomPiece(final int the_x, final int the_y) {
    TetrisPiece piece;
   
    switch (values()[RANDOM.nextInt(values().length)]) {
      case I_PIECE:
        piece = new IPiece(the_x, the_y);
        break;
     
      case J_PIECE:
        piece = new JPiece(the_x, the_y);
        break;
      
      case L_PIECE:
        piece = new LPiece(the_x, the_y);
        break;
      
      case O_PIECE:
        piece = new OPiece(the_x, the_y);
        break;
      
      case S_PIECE:
        piece = new SPiece(the_x, the_y);
        break;
      
      case T_PIECE:
        piece = new TPiece(the_x, the_y);
        break;
      
      case Z_PIECE:
      default: // This is a fail-safe should something very bizarre happen.
        piece = new ZPiece(the_x, the_y);
        break;
    }
    
    return piece;
  }
}
