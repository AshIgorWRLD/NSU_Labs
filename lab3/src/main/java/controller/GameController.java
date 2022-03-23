package controller;

import model.GameType;
import model.gameboard.GameBoardCell;
import model.gameboard.cell.CellMarkStatus;
import model.gameboard.cell.CellMineStatus;
import model.gameboard.cell.CellRevealStatus;
import model.gameboard.GameBoard;
import model.records.Record;
import model.records.RecordsTable;
import view.GameImage;
import view.windows.*;

import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {

    private static final int NOVICE_INFO_PLACE = 0;
    private static final int MEDIUM_INFO_PLACE = 1;
    private static final int EXPERT_INFO_PLACE = 2;
    private static final int TIMER_SECOND = 1000;

    private final GameBoard gameBoard;
    private final MainWindow mainWindow;
    private final RecordsTable recordsTable;

    private int time;
    private Timer timer;

    public GameController(GameBoard gameBoard, MainWindow mainWindow) {
        this.gameBoard = gameBoard;
        this.mainWindow = mainWindow;
        recordsTable = new RecordsTable();
        mainWindow.createGameField(gameBoard.getBoardHeight(), gameBoard.getBoardWidth());
        mainWindow.setBombsCount(gameBoard.getNumberOfMines());
        mainWindow.setNewGameMenuAction(new NewGameListener());
        mainWindow.setAboutMenuAction(new AboutMenuListener());
        mainWindow.setSettingsMenuAction(new SettingsMenuListener());
        mainWindow.setExitMenuAction(new ExitListener());
        mainWindow.setHighScoresMenuAction(new HighScoreMenuListener());
        addListenersToMines();
        this.mainWindow.setVisible(true);
    }

    private void addListenersToMines() {
        for (int i = 0; i < gameBoard.getBoardHeight(); i++) {
            for (int j = 0; j < gameBoard.getBoardWidth(); j++) {
                mainWindow.setMouseListenerToCell(i, j, new ClicksListener(i, j));
            }
        }
    }

    private void repaintGameBoard() {
        for (int i = 0; i < gameBoard.getBoardHeight(); i++) {
            for (int j = 0; j < gameBoard.getBoardWidth(); j++) {
                GameBoardCell tmpCell = gameBoard.getBoard()[i][j];
                if (tmpCell.getMarkStatus() == CellMarkStatus.MARKED) {
                    mainWindow.setCellImage(j, i, GameImage.MARKED);
                } else if (tmpCell.getRevealStatus() == CellRevealStatus.UNREVEALED) {
                    mainWindow.setCellImage(j, i, GameImage.CLOSED);
                } else if (tmpCell.getMineStatus() == CellMineStatus.MINED) {
                    mainWindow.setCellImage(j, i, GameImage.BOMB);
                } else {
                    switch (tmpCell.getNumberStatus()) {
                        case 0 -> {
                            mainWindow.setCellImage(j, i, GameImage.EMPTY);
                        }
                        case 1 -> {
                            mainWindow.setCellImage(j, i, GameImage.NUM_1);
                        }
                        case 2 -> {
                            mainWindow.setCellImage(j, i, GameImage.NUM_2);
                        }
                        case 3 -> {
                            mainWindow.setCellImage(j, i, GameImage.NUM_3);
                        }
                        case 4 -> {
                            mainWindow.setCellImage(j, i, GameImage.NUM_4);
                        }
                        case 5 -> {
                            mainWindow.setCellImage(j, i, GameImage.NUM_5);
                        }
                        case 6 -> {
                            mainWindow.setCellImage(j, i, GameImage.NUM_6);
                        }
                        case 7 -> {
                            mainWindow.setCellImage(j, i, GameImage.NUM_7);
                        }
                        case 8 -> {
                            mainWindow.setCellImage(j, i, GameImage.NUM_8);
                        }
                    }
                }
            }
        }
        mainWindow.repaint();
    }

    private void generateLoseWindow() {
        LoseWindow loseWindow = new LoseWindow(mainWindow);
        loseWindow.setNewGameListener(new NewGameListener());
        loseWindow.setExitListener(new ExitListener());
        loseWindow.setVisible(true);
    }

    private void generateWinWindow() {
        WinWindow winWindow = new WinWindow(mainWindow);
        gameBoard.setGameEnded(true);
        winWindow.setNewGameListener(new NewGameListener());
        winWindow.setExitListener(new ExitListener());
        winWindow.setVisible(true);
    }

    private void generateRecordWindow() {
        RecordsWindow recordsWindow = new RecordsWindow(mainWindow);
        recordsWindow.setNameListener(new RecordNameListener(time, recordsWindow));
        recordsWindow.setVisible(true);
    }

    private void initTimer() {
        MyTimerTask timerTask = new MyTimerTask();
        timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, TIMER_SECOND);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private class MyTimerTask extends TimerTask {

        public MyTimerTask() {
            time = 0;
        }

        @Override
        public void run() {
            mainWindow.setTimerValue(time);
            time++;
        }
    }

    private class NewGameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            stopTimer();
            gameBoard.startNewGame();
            mainWindow.createGameField(gameBoard.getBoardHeight(), gameBoard.getBoardWidth());
            mainWindow.setBombsCount(gameBoard.getNumberOfMines());
            addListenersToMines();
        }
    }

    private class AboutMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AboutWindow aboutWindow = new AboutWindow(mainWindow);
            aboutWindow.setVisible(true);
        }
    }

    private class ExitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            mainWindow.dispose();
            System.exit(0);
        }
    }

    private class SettingsMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            SettingsWindow settingsWindow = new SettingsWindow(mainWindow);
            settingsWindow.setGameTypeChoiceListener(new GameTypeChoiceListener(settingsWindow));
            settingsWindow.setVisible(true);
        }
    }

    private class HighScoreMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            HighScoresWindow highScoresWindow = new HighScoresWindow(mainWindow);
            Record[] records = recordsTable.getRecords();
            highScoresWindow.setNoviceRecord(records[NOVICE_INFO_PLACE].getName(),
                    records[NOVICE_INFO_PLACE].getValue());
            highScoresWindow.setMediumRecord(records[MEDIUM_INFO_PLACE].getName(),
                    records[MEDIUM_INFO_PLACE].getValue());
            highScoresWindow.setExpertRecord(records[EXPERT_INFO_PLACE].getName(),
                    records[EXPERT_INFO_PLACE].getValue());
            highScoresWindow.setVisible(true);
        }
    }

    private class GameTypeChoiceListener implements ActionListener {

        SettingsWindow settingsWindow;

        public GameTypeChoiceListener(SettingsWindow settingsWindow) {
            this.settingsWindow = settingsWindow;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            GameType tmpGameType = settingsWindow.getAnswer();
            if (tmpGameType != gameBoard.getGameType()) {
                stopTimer();
                gameBoard.setGameType(tmpGameType);
                gameBoard.startNewGame();
                mainWindow.createGameField(gameBoard.getBoardHeight(), gameBoard.getBoardWidth());
                mainWindow.setBombsCount(gameBoard.getNumberOfMines());
                addListenersToMines();
            }
        }
    }

    private class RecordNameListener implements ActionListener {

        private final RecordsWindow recordsWindow;
        private final int value;

        public RecordNameListener(int value, RecordsWindow recordsWindow) {
            this.recordsWindow = recordsWindow;
            this.value = value;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = recordsWindow.takeTextFromNameField();
            if (name != null) {
                recordsTable.refreshRecordTable(name, value, gameBoard.getGameType());
            }
        }
    }

    private class ClicksListener implements MouseListener {

        private final int y;
        private final int x;

        public ClicksListener(int y, int x) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            GameBoardCell tmpCell = gameBoard.getBoard()[y][x];
            switch (e.getButton()) {
                case MouseEvent.BUTTON1 -> {
                    if (gameBoard.isGameEnded()) {
                        break;
                    }
                    if (!gameBoard.isGameStarted()) {
                        gameBoard.placeMines(x, y);
                        gameBoard.setGameStarted(true);
                        initTimer();
                    }
                    if (tmpCell.getMarkStatus() == CellMarkStatus.MARKED) {
                        break;
                    }
                    if (gameBoard.openUnrevealedCell(y, x, true)) {
                        gameBoard.setGameEnded(true);
                    }
                    repaintGameBoard();
                    if (gameBoard.isGameEnded()) {
                        stopTimer();
                        generateLoseWindow();
                    }
                    if (gameBoard.getNumberOfCells() - gameBoard.getNumberOfOpenedCells() ==
                            gameBoard.getNumberOfMines()) {
                        stopTimer();
                        if (recordsTable.isItRecord(time, gameBoard.getGameType())) {
                            generateRecordWindow();
                        }
                        generateWinWindow();
                    }
                    mainWindow.setBombsCount(gameBoard.getNumberOfMines() -
                            gameBoard.getNumberOfFlags());
                }
                case MouseEvent.BUTTON2 -> {
                    if (gameBoard.isGameEnded()) {
                        break;
                    }
                    if (tmpCell.getRevealStatus() == CellRevealStatus.UNREVEALED) {
                        break;
                    }
                    if (tmpCell.getNumberStatus() == 0) {
                        break;
                    }
                    if (!gameBoard.scanNumber(y, x)) {
                        gameBoard.setGameEnded(true);
                    }
                    repaintGameBoard();
                    if (gameBoard.isGameEnded()) {
                        stopTimer();
                        generateLoseWindow();
                    }
                    if (gameBoard.getNumberOfCells() - gameBoard.getNumberOfOpenedCells() ==
                            gameBoard.getNumberOfMines()) {
                        stopTimer();
                        if (recordsTable.isItRecord(time, gameBoard.getGameType())) {
                            generateRecordWindow();
                        }
                        generateWinWindow();
                    }
                }
                case MouseEvent.BUTTON3 -> {
                    if (gameBoard.isGameEnded()) {
                        break;
                    }
                    if (tmpCell.getRevealStatus() == CellRevealStatus.UNREVEALED) {
                        if (tmpCell.getMarkStatus() == CellMarkStatus.UNMARKED) {
                            gameBoard.placeFlag(y, x);
                        } else {
                            gameBoard.removeFlag(y, x);
                        }
                        mainWindow.setBombsCount(gameBoard.getNumberOfMines() -
                                gameBoard.getNumberOfFlags());
                        repaintGameBoard();
                    }
                }
                default -> {
                }
                // Other mouse buttons are ignored
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}
