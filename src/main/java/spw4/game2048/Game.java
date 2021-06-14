package spw4.game2048;

import java.util.Random;

public class Game {
    private int score;
    private int moves;
    public Board board;

    public Game() {
        board = new Board();
    }

    public int getScore() {
        return score;
    }

    public boolean isOver() {
        if (isWon())
            return true;
        else
            return board.isOver();
    }

    public boolean isWon() {
        for(int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (board.getTile(x, y) == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getMoves() {
        return moves;
    }

    public int getValueAt(int x, int y) { return board.getTile(x, y);}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Moves: ").append(moves).append("\tScore: ").append(getScore()).append("\n");
        for(int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (board.getTile(x, y) == 0)
                    sb.append(".\t");
                else
                    sb.append(board.getTile(x, y)).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void initialize() {
        board = new Board();
        score = 0;
        moves = 0;

        board.placeRandomTile();
        board.placeRandomTile();
    }

    public void initializeEmpty() {
        board = new Board();
        score = 0;
        moves = 0;
    }

    public void move(Direction direction) {
        boolean hasMoved = false;
        switch (direction) {
            case up: {
                for (int currentCellCol = 0; currentCellCol < 4; currentCellCol++) {
                    for (int currentCellRow = 0; currentCellRow < 4; currentCellRow++) {
                        for (int checkRow = currentCellRow + 1; checkRow < 4; checkRow++) {
                            if (board.getTile(currentCellRow, currentCellCol) == 0) {
                                if (board.getTile(checkRow, currentCellCol) != 0) {
                                    board.setTile(currentCellRow, currentCellCol, board.getTile(checkRow, currentCellCol));
                                    board.setTile(checkRow, currentCellCol, 0);
                                    hasMoved = true;
                                }
                            } else {
                                if (board.getTile(currentCellRow, currentCellCol) == board.getTile(checkRow, currentCellCol)) {
                                    board.setTile(currentCellRow, currentCellCol, board.getTile(currentCellRow, currentCellCol) * 2);
                                    score += board.getTile(currentCellRow, currentCellCol);
                                    board.setTile(checkRow, currentCellCol, 0);
                                    hasMoved = true;
                                    break;
                                } else if (board.getTile(checkRow, currentCellCol) != 0) {
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            }
            case down: {
                for (int currentCellCol = 3; currentCellCol >= 0; currentCellCol--) {
                    for (int currentCellRow = 3; currentCellRow >= 0; currentCellRow--) {
                        for (int checkRow = currentCellRow - 1; checkRow >= 0; checkRow--) {
                            if (board.getTile(currentCellRow, currentCellCol) == 0) {
                                if (board.getTile(checkRow, currentCellCol) != 0) {
                                    board.setTile(currentCellRow, currentCellCol, board.getTile(checkRow, currentCellCol));
                                    board.setTile(checkRow, currentCellCol, 0);
                                    hasMoved = true;
                                }
                            } else {
                                if (board.getTile(currentCellRow, currentCellCol) == board.getTile(checkRow, currentCellCol)) {
                                    board.setTile(currentCellRow, currentCellCol, board.getTile(currentCellRow, currentCellCol) * 2);
                                    board.setTile(checkRow, currentCellCol, 0);
                                    score+= board.getTile(currentCellRow, currentCellCol);
                                    hasMoved = true;
                                    break;
                                } else if (board.getTile(checkRow, currentCellCol) != 0) {
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            }
            case left: {
                for (int currentCellRow = 0; currentCellRow < 4; currentCellRow++) {
                    for (int currentCellCol = 0; currentCellCol < 4; currentCellCol++) {
                        for (int checkCol = currentCellCol + 1; checkCol < 4; checkCol++) {
                            if (board.getTile(currentCellRow, currentCellCol) == 0) {
                                if (board.getTile(currentCellRow, checkCol) != 0) {
                                    board.setTile(currentCellRow, currentCellCol, board.getTile(currentCellRow, checkCol));
                                    board.setTile(currentCellRow, checkCol, 0);
                                    hasMoved = true;
                                }
                            } else {
                                if (board.getTile(currentCellRow, currentCellCol) == board.getTile(currentCellRow, checkCol)) {
                                    board.setTile(currentCellRow, currentCellCol, board.getTile(currentCellRow, currentCellCol) * 2);
                                    score += board.getTile(currentCellRow, currentCellCol);
                                    board.setTile(currentCellRow, checkCol, 0);
                                    hasMoved = true;
                                    break;
                                } else if (board.getTile(currentCellRow, checkCol) != 0) {
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            }
            case right: {
                for (int currentCellRow = 3; currentCellRow >= 0; currentCellRow--) {
                    for (int currentCellCol = 3; currentCellCol >= 0; currentCellCol--) {
                        for (int checkCol = currentCellCol - 1; checkCol >= 0; checkCol--) {
                            if (board.getTile(currentCellRow, currentCellCol) == 0) {
                                if (board.getTile(currentCellRow, checkCol) != 0) {
                                    board.setTile(currentCellRow, currentCellCol, board.getTile(currentCellRow, checkCol));
                                    board.setTile(currentCellRow, checkCol, 0);
                                    hasMoved = true;
                                }
                            } else {
                                if (board.getTile(currentCellRow, currentCellCol) == board.getTile(currentCellRow, checkCol)) {
                                    board.setTile(currentCellRow, currentCellCol, board.getTile(currentCellRow, currentCellCol) * 2);
                                    score += board.getTile(currentCellRow, currentCellCol);
                                    board.setTile(currentCellRow, checkCol, 0);
                                    hasMoved = true;
                                    break;
                                } else if (board.getTile(currentCellRow, checkCol) != 0) {
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            }
        }

        if (hasMoved) {
            board.placeRandomTile();
            moves++;
        } else {
            board.checkIsOver();
        }
    }
}
