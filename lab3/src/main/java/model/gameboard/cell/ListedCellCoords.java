package model.gameboard.cell;

import lombok.Getter;

@Getter
public class ListedCellCoords {

    private final int xCoord;
    private final int yCoord;

    public ListedCellCoords(int y, int x) {
        xCoord = x;
        yCoord = y;
    }
}
