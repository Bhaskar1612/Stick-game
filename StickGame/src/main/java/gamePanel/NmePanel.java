package gamePanel;

import gameEngine.GameEngine;
import javax.swing.JPanel;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;

import javax.swing.*;
import java.awt.*;

/**
 * Created by yashardabiran on 6/29/15.
 */
public class NamePanel extends JPanel {
    private static final int BORDERS_LENGTH = 2;
    private static final int MAX_SIZE = 100;

    private java.awt.Label nameLabel;

    public NamePanel(final GameEngine engine) {
        initUI(engine);
    }

    private void initUI(final GameEngine engine) {
        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new javafx.geometry.Insets(BORDERS_LENGTH));
        flowPane.setAlignment(javafx.geometry.Pos.CENTER);

        nameLabel = new Label(engine.getName());
        nameLabel.setPadding(new javafx.geometry.Insets(BORDERS_LENGTH));
        nameLabel.setMaxSize(MAX_SIZE, MAX_SIZE);
        nameLabel.setStyle("-fx-font-family: Helvetica; -fx-font-size: 22;");

        flowPane.getChildren().add(nameLabel);

        Scene scene = new Scene(flowPane, Images.BACKGROUND_WIDTH, Images.BACKGROUND_HEIGHT);
        setScene(scene);
    }
}
