package gamePanel;

import gameController.GameController;
import gameEngine.GameEngine;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javax.swing.JPanel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;

public class PlayPanel extends JPanel {
    private static final int STICK_WIDTH = 3;
    private static final int RECT_HEIGHT = 220;
    private static final int RECT_START = 50;

    private GameEngine engine;
    private GameController controller;

    private int backgroundMoveValue = 0;

    private int firstWidth;
    private int secondWidth;
    private int secondRectPos;
    private int moveValue;

    private int rotateDegree;
    private int rotateSpeed;

    private int dest;
    private int marioX;
    private int marioY;
    private int imageCycle;
    private int cycleCnt;

    private boolean isMushroomEaten;

    private java.awt.Canvas canvas;

    public PlayPanel(GameEngine engine, GameController controller) {
        this.engine = engine;
        this.controller = controller;
        init();
    }

    public void init() {
        backgroundMoveValue++;
        moveValue = 0;
        secondRectPos = 600;

        rotateDegree = 0;
        rotateSpeed = 1;

        firstWidth = engine.getFirstRect().getWidth();
        secondWidth = engine.getSecondRect().getWidth();

        marioX = RECT_START + firstWidth - 5 - Images.MARIO_WIDTH;
        marioY = Images.BACKGROUND_HEIGHT - RECT_HEIGHT - Images.MARIO_HEIGHT;

        imageCycle = 0;
        cycleCnt = 0;
        dest = 0;

        isMushroomEaten = false;

        createJavaFXContent();
    }

    private void createJavaFXContent() {
        StackPane root = new StackPane();
        canvas = new Canvas(Images.BACKGROUND_WIDTH, Images.BACKGROUND_HEIGHT);
        root.getChildren().add(canvas);
        setScene(new Scene(root));

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(16), e -> render()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void render() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, Images.BACKGROUND_WIDTH, Images.BACKGROUND_HEIGHT);
        moveBackground(gc);
        gc.setStroke(java.awt.Color.BLACK);

        AffineTransform def = new AffineTransform();

        calcRectMove();
        drawRects(gc);

        // Draw Stick
        calcDegree();
        gc.save();
        gc.rotate(Math.toRadians(rotateDegree), RECT_START + firstWidth - STICK_WIDTH - 2,
                Images.BACKGROUND_HEIGHT - RECT_HEIGHT);
        gc.fillRect(RECT_START + firstWidth - STICK_WIDTH - 2, Images.BACKGROUND_HEIGHT - RECT_HEIGHT - engine.getStickLength(),
                STICK_WIDTH, engine.getStickLength());
        gc.restore();

        calcDest();
        moveMario();
        drawMario(gc);

        checkForMushroomEaten();
        drawMushroom(gc);

        gc.setTransform(def);

        checkForGameOver();

        drawScore(gc);
    }

    private void calcRectMove() {
        if (moveValue >= engine.getDistance() + firstWidth) {
            controller.nextRect(isMushroomEaten);
            init();
        }

        if (rotateDegree == 90 && marioX == dest && !engine.isGameOver()) {
            moveValue += 4;
        }
    }

    private void moveBackground(GraphicsContext gc) {
        if (rotateDegree == 90 && marioX == dest && backgroundMoveValue % 20 != 0 && !engine.isGameOver())
            backgroundMoveValue++;

        gc.save();
        gc.translate(-backgroundMoveValue, 0);
        gc.drawImage(SwingFXUtils.toFXImage(Images.background, null), 0, 0);
        gc.restore();
    }

    private void drawRects(GraphicsContext gc) {
        gc.save();
        gc.translate(-moveValue, 0);
        gc.fillRect(RECT_START, Images.BACKGROUND_HEIGHT - RECT_HEIGHT, firstWidth, RECT_HEIGHT);

        if (!engine.isMoving() && rotateDegree == 0 && secondRectPos != RECT_START + firstWidth + engine.getDistance())
            secondRectPos -= 20;
        if (secondRectPos < RECT_START + firstWidth + engine.getDistance())
            secondRectPos = RECT_START + firstWidth + engine.getDistance();

        gc.fillRect(secondRectPos,
                Images.BACKGROUND_HEIGHT - RECT_HEIGHT, secondWidth, RECT_HEIGHT);
        gc.restore();
    }

    private void calcDegree() {
        if (!engine.isMoving())
            return;

        if (rotateDegree < 90) {
            rotateDegree += rotateSpeed / 5;
            rotateSpeed++;
        } else {
            rotateDegree = 90;
        }
    }

    private void drawMario(GraphicsContext gc) {
        if (marioX <= RECT_START + firstWidth)
            controller.upsideDown = false;

        gc.save();
        gc.translate(marioX, marioY + Images.MARIO_HEIGHT);
        if (engine.isMoving() && controller.isUpsideDown()) {
            gc.scale(1, -1);
        }
        if (rotateDegree == 90 && marioX < dest) {
            switch (imageCycle) {
                case 0:
                    gc.drawImage(SwingFXUtils.toFXImage(Images.walk1, null), 0, -Images.MARIO_HEIGHT);
                    break;
                case 1:
                    gc.drawImage(SwingFXUtils.toFXImage(Images.walk2, null), 0, -Images.MARIO_HEIGHT);
                    break;
                case 2:
                    gc.drawImage(SwingFXUtils.toFXImage(Images.walk3, null), 0, -Images.MARIO_HEIGHT);
                    break;
                case 3:
                    gc.drawImage(SwingFXUtils.toFXImage(Images.walk4, null), 0, -Images.MARIO_HEIGHT);
                    break;
            }

            cycleCnt++;
            cycleCnt %= 8;
            if (cycleCnt % 8 == 0) {
                imageCycle++;
                imageCycle %= 4;
            }
        } else {
            gc.drawImage(SwingFXUtils.toFXImage(Images.stand, null), 0, -Images.MARIO_HEIGHT);
        }

        gc.restore();
    }

    private void calcDest() {
        if (engine.isGameOver() && controller.isUpsideDown())
            return;

        if (engine.isGameOver()) {
            dest = RECT_START + firstWidth - Images.MARIO_WIDTH + engine.getStickLength();
        } else {
            dest = RECT_START + firstWidth + engine.getDistance() + secondWidth - 5 - Images.MARIO_WIDTH;
        }
    }

    private void moveMario() {
        if (rotateDegree == 90 && marioX < dest)
            marioX += 2;

        if (marioX > dest)
            marioX = dest;

        if (marioX == dest && engine.isGameOver())
            marioY += 20;

        if (marioY > Images.BACKGROUND_HEIGHT)
            controller.gameOver();
    }

    private void drawMushroom(GraphicsContext gc) {
        if (!isMushroomEaten) {
            gc.drawImage(SwingFXUtils.toFXImage(Images.mushroom, null), RECT_START + firstWidth + engine.getMushroomPos(),
                    Images.BACKGROUND_HEIGHT - RECT_HEIGHT + 5);
        }
    }

    private void checkForMushroomEaten() {
        if (controller.isUpsideDown() && marioX + Images.MARIO_WIDTH >= RECT_START + firstWidth + engine.getMushroomPos()
                && marioX <= RECT_START + firstWidth + engine.getMushroomPos() + 25)
            isMushroomEaten = true;
    }

    private void checkForGameOver() {
        if (controller.isUpsideDown() && marioX + Images.MARIO_WIDTH >= RECT_START + firstWidth + engine.getDistance()) {
            engine.setGameOver(true);
            dest = marioX;
        }
    }

    private void drawScore(GraphicsContext gc) {
        gc.setFont(new javafx.scene.text.Font("Trattatello", 40));
        gc.setFill(Color.BLACK);
        gc.fillText("Score : " + engine.getScore(), 30, 70);
        gc.fillText("Mushroom : " + engine.getMushroomNum(), 270, 70);
    }
}
