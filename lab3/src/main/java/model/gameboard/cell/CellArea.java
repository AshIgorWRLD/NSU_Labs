package model.gameboard.cell;

import lombok.Getter;

@Getter
public class CellArea {

    private int startXPoint;
    private int endXPoint;
    private int startYPoint;
    private int endYPoint;

    public CellArea(int boardWidth, int boardHeight, int y, int x) {
        startXPoint = x - 1;
        endXPoint = x + 1;
        startYPoint = y - 1;
        endYPoint = y + 1;
        if (x == 0) {
            startXPoint = x;
            endXPoint = x + 1;
        }
        if (x == boardWidth - 1) {
            startXPoint = x - 1;
            endXPoint = x;
        }
        if (y == 0) {
            startYPoint = y;
            endYPoint = y + 1;
        }
        if (y == boardHeight - 1) {
            startYPoint = y - 1;
            endYPoint = y;
        }
    }
}
