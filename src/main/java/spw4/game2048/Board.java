package spw4.game2048;

import java.util.Random;

public class Board {
    private int[][] field;
    public Random random;
    private boolean isOver;

    public Board() {
        field = new int[][]{{0, 0, 0, 0},
                            {0, 0, 0, 0},
                            {0, 0, 0, 0},
                            {0, 0, 0, 0}};
        random = new Random();
        isOver = false;
    }

    public void setTile(int row, int col, int value) {
        field[row][col] = value;
    }

    public int getTile(int row, int col) {
        return field[row][col];
    }

    public boolean isOver() {
        return isOver;
    }

    public void placeRandomTile() {
        if (!hasEmptyField()) {
            isOver = true;
            return;
        }

        int x = random.nextInt(4);
        int y = random.nextInt(4);
        while (field[x][y] != 0) {
            x = random.nextInt(4);
            y = random.nextInt(4);
        }

        int poss = random.nextInt(10);
        if (poss < 9)
            field[x][y] = 2;
        else
            field[x][y] = 4;
    }

    private boolean hasEmptyField() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (field[x][y] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public void checkIsOver() {
        if (!hasEmptyField())
            isOver = true;
    }
}
