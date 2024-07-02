package gamePanel;

import gameController.GameController;
import gameEngine.GameEngine;
import javax.swing.JPanel;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameOverPanel extends JPanel {
    private GameEngine engine;
    private Button replayButton;

    public GameOverPanel(final GameEngine engine, final GameController controller) {
        this.engine = engine;
        initUI(controller);
    }

    private void initUI(final GameController controller) {
        StackPane root = new StackPane();
        Canvas canvas = new Canvas(Images.BACKGROUND_WIDTH, Images.BACKGROUND_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        draw(gc);

        replayButton = new Button("Replay");
        replayButton.setStyle("-fx-font-size: 20;");
        replayButton.setOnAction(e -> controller.replay());

        root.getChildren().addAll(canvas, replayButton);

        Scene scene = new Scene(root, Images.BACKGROUND_WIDTH, Images.BACKGROUND_HEIGHT);
        setScene(scene);
    }

    private void draw(GraphicsContext gc) {
        gc.drawImage(Images.background, -450, 0);
        gc.setFont(new Font("Trattatello", 85));
        gc.setFill(Color.BLACK);
        gc.fillText("GAME OVER", 35, 120);

        gc.setFont(new Font("Trattatello", 60));
        gc.fillText("SCORE : " + engine.getScore(), 130, 250);
        gc.fillText("MUSHROOMS : " + engine.getMushroomNum(), 40, 330);
    }
}
