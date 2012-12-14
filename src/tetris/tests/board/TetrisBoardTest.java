/*
 * Jesse Morgan <jesterpm@u.washington.edu>
 * 
 * TCSS 305 � Autumn 2009
 * Tetris Project
 * 17 November 2009
 */

package tetris.tests.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import tetris.board.TetrisBoard;
import tetris.model.IntPoint;
import tetris.piece.IPiece;
import tetris.piece.JPiece;
import tetris.piece.LPiece;
import tetris.piece.OPiece;
import tetris.piece.SPiece;
import tetris.piece.TPiece;
import tetris.piece.TetrisPiece;
import tetris.piece.ZPiece;

/**
 * Test class for the tetris board.
 * @author Jesse Morgan <jesterpm@u.washington.edu>
 * @version 1.0 30 November 2009.
 */
public class TetrisBoardTest {
  /**
   * Width and Height of a Tetris piece.
   */
  private static final int TETRIS_PIECE_SIZE = 4;
  
  /**
   * 
   */
  private static final String TEST1_FILENAME =
    "tetris.tests.board.TetrisBoardTest.expected-output.txt";
  
  /**
   * The test pieces.
   */
  private static final TetrisPiece[] TEST1 = {
    new TPiece(0, 0),
    new OPiece(2, 0),
    new IPiece(5, 0).rotateLeft().translate(-1, 0),
    new OPiece(5, 0),
    
    new IPiece(0, 0).rotateLeft().translate(-1, 0),
    new ZPiece(2, 0).rotateRight(),
    
    new OPiece(7, 0),
    
    // Bottom two lines have disappeared now.
    
    new OPiece(0, 0),
    new JPiece(1, 0).rotateLeft(),
    new IPiece(6, 0),
    new LPiece(4, 0).rotateRight(),
    new SPiece(7, 0),
    new TPiece(6, 0).rotateLeft().rotateLeft(),
    
    // Third (5th) row has now disappeared.
    
    new OPiece(3, 0),
    new SPiece(6, 0),
    new LPiece(1, 0),
    new TPiece(4, 0),
    new LPiece(4, 0).rotateLeft().rotateLeft(),
    new ZPiece(2, 0).rotateLeft(),
    new JPiece(0, 0).rotateRight(),
   
    new IPiece(6, 0).rotateLeft().translate(-1, 0),
    new LPiece(7, 0).rotateLeft().rotateLeft(),
    new JPiece(7, 0).rotateRight(),
    
    new OPiece(-1, 0), // 1 square buffer on the either side
    new JPiece(1, 0).rotateLeft(),
    new IPiece(3, 0),
    new IPiece(7, 0).rotateLeft().translate(-1, 0),
    new TPiece(7, 0).rotateLeft().rotateLeft(),
    new SPiece(5, 0),
    new ZPiece(5, 0).rotateLeft(),
    
    new TPiece(4, 0),
    new LPiece(4, 0).rotateLeft(),
    new LPiece(4, 0)
  };
  
  /**
   * The expected output of test 1.
   */
  private static String my_test1_output;
  
  /**
   * This method loads the expected output into a file.
   */
  @BeforeClass
  public static void loadExpectedOutput() {
    try {
      final BufferedReader reader =
          new BufferedReader(new FileReader(TEST1_FILENAME));
  
      final StringBuilder sb = new StringBuilder();
      String line;
  
      try {
        line = reader.readLine();
        while (line != null) {
          sb.append(line);
          sb.append("\n");
    
          line = reader.readLine();
        }
    
        my_test1_output = sb.toString();
        
        reader.close();
        
      } catch (final IOException the_exception) {
        fail("Could not load file: " + the_exception.getMessage());
      }
    
    } catch (final FileNotFoundException the_exception) {
      fail("Could not find file: " + the_exception.getMessage());
    }
    
  }
  
  
  /**
   * JUnit test to check that the board functions as expected.
   */
  @Test
  public void testTheBoard() {
    final TetrisBoard tb = new TetrisBoard(TetrisBoard.STANDARD_BOARD_WIDTH,
                                     TetrisBoard.STANDARD_BOARD_HEIGHT,
                                     Arrays.asList(TEST1));
    
    tb.newGame();
    
    // Loop through game play
    final int moves_per_piece = TetrisBoard.STANDARD_BOARD_HEIGHT * TETRIS_PIECE_SIZE;
    
    for (int i = 0; i <  TEST1.length * moves_per_piece && !tb.isGameOver(); i++) {
      tb.progressBoard();
    }

    
    assertEquals("Tetris board output not as expected.", my_test1_output, tb.toString());
  }
  
  /**
   * JUnit test for the moving functions.
   */
  @Test
  public void testMoves() {
    final List<TetrisPiece> pieces = new ArrayList<TetrisPiece>();
    
    pieces.add(new LPiece(-1, 0));
    pieces.add(new LPiece(-1, 0));
    
    final TetrisBoard tb = new TetrisBoard(TetrisBoard.STANDARD_BOARD_WIDTH,
                                           TetrisBoard.STANDARD_BOARD_HEIGHT,
                                           pieces);
    tb.newGame();
    
    final IntPoint[] points =
    {new IntPoint(0, 0), new IntPoint(0, 1), new IntPoint(0, 2), new IntPoint(1, 2)};
    
    // Test inital
    checkPoints(new IntPoint(0, 0), points, tb.getTetrisBlocks());
    
    // Test Right
    tb.moveRight();
    checkPoints(new IntPoint(1, 0), points, tb.getTetrisBlocks());

    // Test Left
    tb.moveLeft();
    checkPoints(new IntPoint(1 - 1, 0), points, tb.getTetrisBlocks());
    
    // Test Down
    tb.moveDown();
    checkPoints(new IntPoint(0, 1), points, tb.getTetrisBlocks());
  }
  
  /**
   * Checks that an array of expected points match a List actual points.
   * @param the_modifier A point to modify the other points by.
   * @param the_expected The expected points.
   * @param the_actual The actual points.  
   */
  private void checkPoints(final IntPoint the_modifier,
                           final IntPoint[] the_expected, final List<IntPoint> the_actual) {
    
    assertEquals("Extraneous points on the board.", the_expected.length, the_actual.size());
    
    for (int i = 0; i < the_expected.length; i++) {
      final IntPoint modded = new IntPoint(the_expected[i].getX() + the_modifier.getX(),
                                     the_expected[i].getY() + the_modifier.getY());
      
      assertTrue("Missing a point.", the_actual.contains(modded));
    }
  }
}
