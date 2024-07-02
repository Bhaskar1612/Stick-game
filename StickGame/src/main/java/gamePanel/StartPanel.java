package gamePanel;

import gameController.GameController;
import javax.swing.JPanel;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import javax.swing.*;
import java.awt.*;

/**
 * Created by yashardabiran on 6/29/15.
 */
public class StartPanel extends JPanel {
    private GameController controller;

    public StartPanel(GameController controller) {
        this.controller = controller;
        createJavaFXContent();
    }

    private void createJavaFXContent() {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, Images.BACKGROUND_WIDTH, Images.BACKGROUND_HEIGHT);

        java.awt.Button button = new Button();
        ImageView imageView = new ImageView(new javafx.scene.image.Image("file:images/startButton.png"));
        button.setGraphic(imageView);
        button.setOnAction(e -> controller.actionPerformed(null));
        button.setStyle("-fx-background-color: transparent;");

        root.getChildren().add(button);
        setScene(scene);
    }

    // If you still need the overridden paintComponent method
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(Images.background, -450, 0, null);
        g2d.setFont(new Font("Trattatello", Font.BOLD, 100));
        g2d.setColor(Color.darkGray);
        g2d.drawString("STICK", 125, 120);
        g2d.drawString("HERO", 130, 200);
    }
}
