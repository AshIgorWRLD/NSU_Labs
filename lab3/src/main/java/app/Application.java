package app;

import controller.GameController;
import model.gameboard.GameBoard;
import view.windows.MainWindow;

public class Application {
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        MainWindow mainWindow = new MainWindow();
        GameController gameController = new GameController(gameBoard, mainWindow);
    }
}
