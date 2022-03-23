package model.gameboard;

import lombok.Getter;
import lombok.Setter;
import model.GameType;
import model.gameboard.cell.CellMarkStatus;
import model.gameboard.cell.CellMineStatus;
import model.gameboard.cell.CellRevealStatus;
import model.gameboard.cell.ListedCellCoords;
import model.gameboard.cell.CellArea;

import java.util.ArrayList;
import java.util.Random;

@Setter
@Getter
public class GameBoard {

    private static final int NOVICE_MINES_NUMBER = 10;
    private static final int MEDIUM_MINES_NUMBER = 40;
    private static final int EXPERT_MINES_NUMBER = 99;
    private static final int NOVICE_BOARD_WIDTH = 9;
    private static final int MEDIUM_BOARD_WIDTH = 16;
    private static final int EXPERT_BOARD_WIDTH = 30;
    private static final int NOVICE_BOARD_HEIGHT = 9;
    private static final int MEDIUM_BOARD_HEIGHT = 16;
    private static final int EXPERT_BOARD_HEIGHT = 16;

    private final Random random = new Random();

    private int boardWidth;
    private int boardHeight;
    private GameBoardCell[][] board;
    private int numberOfCells;
    private int numberOfMines;
    private int numberOfOpenedCells;
    private int numberOfFlags;
    private GameType gameType;
    private boolean isGameStarted;
    private boolean isGameEnded;

    public GameBoard() {
        setGameType(GameType.NOVICE);
        startNewGame();
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public void startNewGame() {
        isGameStarted = false;
        isGameEnded = false;
        switch (this.gameType) {
            case NOVICE -> {
                boardWidth = NOVICE_BOARD_WIDTH;
                boardHeight = NOVICE_BOARD_HEIGHT;
            }
            case MEDIUM -> {
                boardWidth = MEDIUM_BOARD_WIDTH;
                boardHeight = MEDIUM_BOARD_HEIGHT;
            }
            case EXPERT -> {
                boardWidth = EXPERT_BOARD_WIDTH;
                boardHeight = EXPERT_BOARD_HEIGHT;
            }
        }
        numberOfCells = boardHeight * boardWidth;
        numberOfFlags = 0;
        numberOfOpenedCells = 0;
        numberOfMines = takeMinesAmount();
        board = new GameBoardCell[boardHeight][boardWidth];
        initBoard();
    }

    public void placeMines(int chosenX, int chosenY) {
        ArrayList<ListedCellCoords> cellsList = listCells(chosenX, chosenY);
        mineTheBoard(cellsList);
        placeNumbers();
    }

    public boolean openUnrevealedCell(int y, int x, boolean withFlagsRemove) {
        GameBoardCell tmpCell = board[y][x];
        if (tmpCell.getMarkStatus() == CellMarkStatus.MARKED && withFlagsRemove) {
            tmpCell.setMarkStatus(CellMarkStatus.UNMARKED);
            numberOfFlags--;
        } else if (tmpCell.getMarkStatus() == CellMarkStatus.MARKED && !withFlagsRemove) {
            return false;
        }
        if (tmpCell.getRevealStatus() == CellRevealStatus.REVEALED) {
            return false;
        }
        board[y][x].setRevealStatus(CellRevealStatus.REVEALED);
        numberOfOpenedCells++;
        if (tmpCell.getMineStatus() == CellMineStatus.MINED) {
            setAllUnrevealed();
            return true;
        }
        if (tmpCell.getNumberStatus() == 0) {
            CellArea cellArea = new CellArea(boardWidth, boardHeight, y, x);

            for (int i = cellArea.getStartYPoint(); i < cellArea.getEndYPoint() + 1; i++) {
                for (int j = cellArea.getStartXPoint(); j < cellArea.getEndXPoint() + 1; j++) {
                    if (i == y && j == x) {
                        continue;
                    }
                    if (board[i][j].getRevealStatus() == CellRevealStatus.UNREVEALED) {
                        openUnrevealedCell(i, j, withFlagsRemove);
                    }
                }
            }
        }
        return false;
    }

    private boolean openArea(CellArea cellArea, int y, int x) {
        for (int i = cellArea.getStartYPoint(); i < cellArea.getEndYPoint() + 1; i++) {
            for (int j = cellArea.getStartXPoint(); j < cellArea.getEndXPoint() + 1; j++) {
                if (i == y && j == x) {
                    continue;
                }
                if (board[i][j].getRevealStatus() == CellRevealStatus.UNREVEALED) {
                    if (openUnrevealedCell(i, j, false)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean scanNumber(int y, int x) {
        GameBoardCell tmpCell = board[y][x];
        if (tmpCell.getRevealStatus() == CellRevealStatus.UNREVEALED) {
            return true;
        }
        if (tmpCell.getNumberStatus() > 0) {
            CellArea cellArea = new CellArea(boardWidth, boardHeight, y, x);
            int flagsNumber = countFlagsInArea(cellArea, y, x);
            if (flagsNumber != tmpCell.getNumberStatus()) {
                return true;
            }
            return openArea(cellArea, y, x);
        }
        return true;
    }

    private int countFlagsInArea(CellArea cellArea, int y, int x) {
        int flagsNumber = 0;
        for (int i = cellArea.getStartYPoint(); i < cellArea.getEndYPoint() + 1; i++) {
            for (int j = cellArea.getStartXPoint(); j < cellArea.getEndXPoint() + 1; j++) {
                if (i == y && j == x) {
                    continue;
                }
                if (board[i][j].getMarkStatus() == CellMarkStatus.MARKED) {
                    flagsNumber++;
                }
            }
        }
        return flagsNumber;
    }

    public void placeFlag(int y, int x) {
        if (numberOfMines > numberOfFlags) {
            board[y][x].setMarkStatus(CellMarkStatus.MARKED);
            numberOfFlags++;
        }
    }

    public void removeFlag(int y, int x) {
        if (board[y][x].getMarkStatus() == CellMarkStatus.MARKED) {
            board[y][x].setMarkStatus(CellMarkStatus.UNMARKED);
            numberOfFlags--;
        }
    }

    private void setAllUnrevealed() {
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                if (board[i][j].getMineStatus() == CellMineStatus.MINED) {
                    board[i][j].setRevealStatus(CellRevealStatus.REVEALED);
                    board[i][j].setMarkStatus(CellMarkStatus.UNMARKED);
                }
            }
        }
    }

    private void placeNumbers() {
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                if (board[i][j].getMineStatus() != CellMineStatus.MINED) {
                    board[i][j].setNumberStatus(countMinesAroundAmount(i, j));
                }
            }
        }
    }

    private int countMinesAroundAmount(int y, int x) {
        int countedMinesAmount = 0;

        CellArea cellArea = new CellArea(boardWidth, boardHeight, y, x);

        for (int i = cellArea.getStartYPoint(); i < cellArea.getEndYPoint() + 1; i++) {
            for (int j = cellArea.getStartXPoint(); j < cellArea.getEndXPoint() + 1; j++) {
                if (board[i][j].getMineStatus() == CellMineStatus.MINED) {
                    countedMinesAmount++;
                }
            }
        }

        return countedMinesAmount;
    }

    private ArrayList<ListedCellCoords> listCells(int chosenX, int chosenY) {
        ArrayList<ListedCellCoords> cellsList = new ArrayList<ListedCellCoords>();

        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                if (chosenY != i || chosenX != j) {
                    cellsList.add(new ListedCellCoords(i, j));
                }
            }
        }
        return cellsList;
    }

    private void mineTheBoard(ArrayList<ListedCellCoords> cellsList) {
        int randomRange = boardWidth * boardHeight - 1;
        for (int i = 0; i < numberOfMines; i++) {
            int supposedCellIdx = random.nextInt(randomRange);
            ListedCellCoords tmpCoord = cellsList.remove(supposedCellIdx);
            board[tmpCoord.getYCoord()][tmpCoord.getXCoord()].setMineStatus(CellMineStatus.MINED);
            randomRange--;
        }
    }

    private int takeMinesAmount() {
        switch (gameType) {
            case NOVICE -> {
                return NOVICE_MINES_NUMBER;
            }
            case MEDIUM -> {
                return MEDIUM_MINES_NUMBER;
            }
            case EXPERT -> {
                return EXPERT_MINES_NUMBER;
            }
        }
        return 0;
    }

    private void initBoard() {
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                board[i][j] = new GameBoardCell();
            }
        }
    }
}
