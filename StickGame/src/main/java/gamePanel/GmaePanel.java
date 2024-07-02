package gamePanel;

import gameController.GameController;
import gameEngine.GameEngine;
import javax.swing.JPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class GamePanel extends JPanel {
    private PlayPanel playPanel;
    private StartPanel startPanel;
    private NamePanel namePanel;
    private GameOverPanel gameOverPanel;

    private GameEngine engine;
    private GameController controller;

    public void init(GameEngine engine, GameController controller) {
        this.engine = engine;
        this.controller = controller;

        gameOverPanel = new GameOverPanel(engine, controller);
        namePanel = new NamePanel(engine);
        startPanel = new StartPanel(controller);
        playPanel = new PlayPanel(engine, controller);
        playPanel.setOnMouseClicked(event -> controller.mouseClicked(event));

        BorderPane root = new BorderPane();
        root.setCenter(startPanel);

        Scene scene = new Scene(root, Images.BACKGROUND_WIDTH, Images.BACKGROUND_HEIGHT);
        setScene(scene);
    }

    public void goToGame() {
        BorderPane root = new BorderPane();
        root.setCenter(playPanel);
        setScene(new Scene(root, Images.BACKGROUND_WIDTH, Images.BACKGROUND_HEIGHT));
    }

    public void gameOver() {
        BorderPane root = new BorderPane();
        root.setCenter(gameOverPanel);
        setScene(new Scene(root, Images.BACKGROUND_WIDTH, Images.BACKGROUND_HEIGHT));
    }

    public void replay(GameEngine engine, GameController controller) {
        init(engine, controller);
    }
}
