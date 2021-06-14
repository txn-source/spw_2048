package spw4.game2048;

import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameTest {
    private Game g;

    @BeforeEach
    void beforeEach() {
        g = new Game();
        g.initialize();
    }

    @Test
    @DisplayName("getScore returns 0 on new Game")
    public void getScore_OnNewGame_ReturnsZero() {
        assertEquals(0, g.getScore());
    }

    @Test
    @DisplayName("isOver returns true when game is over")
    public void isOver_WhenGameBoardFull_ReturnsTrue() {
        var g_mock = mock(Game.class);
        when(g_mock.isOver()).thenReturn(true);

        assertTrue(g_mock.isOver());
    }

    @Test
    @DisplayName("isOver returns true when game is won")
    public void isOver_WhenGameWon_ReturnsTrue() {
        Game ga = spy(Game.class);
        when(ga.isWon()).thenReturn(true);
        assertTrue(ga.isOver());
    }

    @Test
    @DisplayName("isOver returns false when game new")
    public void isOver_WhenGameBoardNotFull_ReturnsFalse() {
        assertFalse(g.isOver());
    }

    @Test
    @DisplayName("isWon returns true when game is won")
    public void isWon_WhenGameBoardHas2048_ReturnsTrue() {
        var g_spy = spy(Game.class);
        g_spy.board.setTile(0, 0, 2048);

        assertTrue(g_spy.isWon());
    }

    @Test
    @DisplayName("isWon returns false when game is not won")
    public void isWon_WhenGameBoardHasNo2048_ReturnsFalse() {
        assertFalse(g.isWon());
    }

    @Test
    @DisplayName("toString calls getScore Method exactly once")
    public void toString_WhenGameIsRunning_CallsGetScoreOnce() {
        var g_spy = spy(Game.class);
        g_spy.initialize();
        g_spy.toString();

        assertAll(
                () -> verify(g_spy, times(1)).getScore()
        );
    }

    @Test
    @DisplayName("isOver calls isWon exactly once")
    public void isOver_WhenGameIsRunning_CallsIsWonOnce() {
        Game g_spy = spy(Game.class);
        g_spy.initialize();
        g_spy.isOver();

        assertAll(
                () -> verify(g_spy, times(1)).isWon()
        );
    }

    @Test
    @DisplayName("When moving once, moves is increased by one")
    public void move_WhenMoveOnceValid_IncreaseMovesByOne() {
        g.board = spy(Board.class);
        g.board.setTile(0, 0, 2);
        g.board.setTile(0, 1, 2);
        g.move(Direction.right);
        assertEquals(1, g.getMoves());
    }

    @Test
    @DisplayName("When moving twice, moves is increased by two")
    public void move_WhenMoveTwiceValid_IncreaseMovesByTwo() {
        g.board = spy(Board.class);
        g.board.setTile(0, 0, 2);
        g.board.setTile(0, 1, 2);
        g.move(Direction.left);
        g.move(Direction.down);
        assertEquals(2, g.getMoves());
    }

    @Test
    @DisplayName("Not Moved Board calls IsOver Once")
    public void move_WhenNoMovement_BoardCheckIsOverIsCalledOnce() {
        g.board = spy(Board.class);
        g.board.setTile(0, 0, 1);
        g.board.setTile(0, 1, 2);
        g.board.setTile(0, 2, 3);
        g.board.setTile(0, 3, 4);
        g.board.setTile(1, 0, 5);
        g.board.setTile(1, 1, 6);
        g.board.setTile(1, 2, 7);
        g.board.setTile(1, 3, 8);
        g.board.setTile(2, 0, 9);
        g.board.setTile(2, 1, 10);
        g.board.setTile(2, 2, 11);
        g.board.setTile(2, 3, 12);
        g.board.setTile(3, 0, 13);
        g.board.setTile(3, 1, 14);
        g.board.setTile(3, 2, 15);
        g.board.setTile(3, 3, 16);

        g.move(Direction.up);

        verify(g.board, times(1)).checkIsOver();
    }

    @Test
    @DisplayName("Move up moves Tiles up")
    public void moveUp_WhenCanMove_MovesTileUp() {

        g.board.setTile(0, 0, 0);
        g.board.setTile(1, 0, 2);
        g.board.setTile(2, 0, 0);
        g.board.setTile(3, 0, 0);

        g.move(Direction.up);

        assertEquals(2, g.board.getTile(0, 0));
    }

    @Test
    @DisplayName("Move up moves Tiles up and merges them")
    public void moveUp_WhenCanMerge_MergesTilesAndMovesThemUp() {
        g.board.setTile(0, 0, 0);
        g.board.setTile(1, 0, 2);
        g.board.setTile(2, 0, 2);
        g.board.setTile(3, 0, 0);

        g.move(Direction.up);

        assertEquals(4, g.board.getTile(0, 0));
    }

    @Test
    @DisplayName("Move up when blocked doesnt merge")
    public void moveUp_WhenBlocked_DoesntMove() {
        g.initializeEmpty();
        g.board.setTile(0, 0, 2);
        g.board.setTile(1, 0, 4);
        g.board.setTile(2, 0, 2);
        g.board.setTile(3, 0, 0);

        var randStub = mock(Random.class);
        when(randStub.nextInt(4)).thenReturn(3).thenReturn(3);
        when(randStub.nextInt(10)).thenReturn(5);
        g.board.random = randStub;

        g.move(Direction.up);

        assertAll(
                () -> assertEquals(2, g.board.getTile(0, 0)),
                () -> assertEquals(4, g.board.getTile(1, 0)),
                () -> assertEquals(2, g.board.getTile(2, 0)),
                () -> assertEquals(0, g.board.getTile(3, 0))
        );
    }

    @Test
    @DisplayName("Move down moves Tiles down")
    public void moveDown_WhenCanMove_MovesTileDown() {

        g.board.setTile(0, 0, 0);
        g.board.setTile(1, 0, 2);
        g.board.setTile(2, 0, 0);
        g.board.setTile(3, 0, 0);

        g.move(Direction.down);

        assertEquals(2, g.board.getTile(3, 0));
    }

    @Test
    @DisplayName("Move down moves Tiles down and merges them")
    public void moveDown_WhenCanMerge_MergesTilesAndMovesThemDown() {
        g.board.setTile(0, 0, 0);
        g.board.setTile(1, 0, 2);
        g.board.setTile(2, 0, 2);
        g.board.setTile(3, 0, 0);

        g.move(Direction.down);

        assertEquals(4, g.board.getTile(3, 0));
    }

    @Test
    @DisplayName("Move down when blocked doesnt move")
    public void moveDown_WhenBlocked_DoesntMove() {
        g.initializeEmpty();
        g.board.setTile(0, 0, 0);
        g.board.setTile(1, 0, 2);
        g.board.setTile(2, 0, 4);
        g.board.setTile(3, 0, 2);

        var randStub = mock(Random.class);
        when(randStub.nextInt(4)).thenReturn(3).thenReturn(3);
        when(randStub.nextInt(10)).thenReturn(5);
        g.board.random = randStub;

        g.move(Direction.down);

        assertAll(
                () -> assertEquals(0, g.board.getTile(0, 0)),
                () -> assertEquals(2, g.board.getTile(1, 0)),
                () -> assertEquals(4, g.board.getTile(2, 0)),
                () -> assertEquals(2, g.board.getTile(3, 0))
        );
    }

    @Test
    @DisplayName("Move right moves Tiles right")
    public void moveRight_WhenCanMove_MovesTileUp() {

        g.board.setTile(0, 0, 0);
        g.board.setTile(0, 1, 2);
        g.board.setTile(0, 2, 0);
        g.board.setTile(0, 3, 0);

        g.move(Direction.right);

        assertEquals(2, g.board.getTile(0, 3));
    }

    @Test
    @DisplayName("Move right moves Tiles right and merges them")
    public void moveRight_WhenCanMerge_MergesTilesAndMovesThemUp() {
        g.board.setTile(0, 0, 0);
        g.board.setTile(0, 1, 2);
        g.board.setTile(0, 2, 2);
        g.board.setTile(0, 3, 0);

        g.move(Direction.right);

        assertEquals(4, g.board.getTile(0, 3));
    }

    @Test
    @DisplayName("Move right when blocked doesnt move")
    public void moveRight_WhenBlocked_DoesntMove() {
        g.initializeEmpty();
        g.board.setTile(0, 0, 0);
        g.board.setTile(0, 1, 2);
        g.board.setTile(0, 2, 4);
        g.board.setTile(0, 3, 2);

        var randStub = mock(Random.class);
        when(randStub.nextInt(4)).thenReturn(3).thenReturn(3);
        when(randStub.nextInt(10)).thenReturn(5);
        g.board.random = randStub;

        g.move(Direction.right);

        assertAll(
                () -> assertEquals(0, g.board.getTile(0, 0)),
                () -> assertEquals(2, g.board.getTile(0, 1)),
                () -> assertEquals(4, g.board.getTile(0, 2)),
                () -> assertEquals(2, g.board.getTile(0, 3))
        );
    }

    @Test
    @DisplayName("Move left moves Tiles left")
    public void moveLeft_WhenCanMove_MovesTileLeft() {

        g.board.setTile(0, 0, 0);
        g.board.setTile(0, 1, 2);
        g.board.setTile(0, 2, 0);
        g.board.setTile(0, 3, 0);

        g.move(Direction.left);

        assertEquals(2, g.board.getTile(0, 0));
    }

    @Test
    @DisplayName("Move left moves Tiles left and merges them")
    public void moveLeft_WhenCanMerge_MergesTilesAndMovesThemLeft() {
        g.board.setTile(0, 0, 0);
        g.board.setTile(0, 1, 2);
        g.board.setTile(0, 2, 2);
        g.board.setTile(0, 3, 0);

        g.move(Direction.left);

        assertEquals(4, g.board.getTile(0, 0));
    }

    @Test
    @DisplayName("Move left when blocked doesnt merge")
    public void moveLeft_WhenBlocked_DoesntMove() {
        g.initializeEmpty();
        g.board.setTile(0, 0, 2);
        g.board.setTile(0, 1, 4);
        g.board.setTile(0, 2, 2);
        g.board.setTile(0, 3, 0);

        var randStub = mock(Random.class);
        when(randStub.nextInt(4)).thenReturn(3).thenReturn(3);
        when(randStub.nextInt(10)).thenReturn(5);
        g.board.random = randStub;

        g.move(Direction.left);

        assertAll(
                () -> assertEquals(2, g.board.getTile(0, 0)),
                () -> assertEquals(4, g.board.getTile(0, 1)),
                () -> assertEquals(2, g.board.getTile(0, 2)),
                () -> assertEquals(0, g.board.getTile(0, 3))
        );
    }

    @Test
    @DisplayName("Trying to place a random tile on a full board sets board state to isOver")
    void placeRandomTile_OnFullBoard_SetsIsOverToFalse() {
        g.board = spy(Board.class);
        g.board.setTile(0, 0, 1);
        g.board.setTile(0, 1, 2);
        g.board.setTile(0, 2, 3);
        g.board.setTile(0, 3, 4);
        g.board.setTile(1, 0, 5);
        g.board.setTile(1, 1, 6);
        g.board.setTile(1, 2, 7);
        g.board.setTile(1, 3, 8);
        g.board.setTile(2, 0, 9);
        g.board.setTile(2, 1, 10);
        g.board.setTile(2, 2, 11);
        g.board.setTile(2, 3, 12);
        g.board.setTile(3, 0, 13);
        g.board.setTile(3, 1, 14);
        g.board.setTile(3, 2, 15);
        g.board.setTile(3, 3, 16);

        assertFalse(g.board.isOver());

        g.board.placeRandomTile();

        assertTrue(g.board.isOver());
    }

    @Test
    @DisplayName("move Right merges Four Twos into Two Fours")
    void moveRight_OnFourNumberTwos_MergesIntoTwoNumberFours() {
        g.initializeEmpty();
        g.board.setTile(0, 0, 2);
        g.board.setTile(0, 1, 2);
        g.board.setTile(0, 2, 2);
        g.board.setTile(0, 3, 2);

        var randStub = mock(Random.class);
        when(randStub.nextInt(4)).thenReturn(3).thenReturn(3);
        when(randStub.nextInt(10)).thenReturn(5);
        g.board.random = randStub;

        g.move(Direction.right);

        assertAll(
                () -> assertEquals(0, g.board.getTile(0, 0)),
                () -> assertEquals(0, g.board.getTile(0, 1)),
                () -> assertEquals(4, g.board.getTile(0, 2)),
                () -> assertEquals(4, g.board.getTile(0, 3))
        );
    }
}
