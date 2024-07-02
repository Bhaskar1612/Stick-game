package gamePanel;

import javafx.scene.image.Image;

/**
 * Created by yashardabiran on 6/30/15.
 */
public class Images {
    public static Image background;
    public static Image startButton;
    public static Image stand;
    public static Image walk1;
    public static Image walk2;
    public static Image walk3;
    public static Image walk4;
    public static Image replay;
    public static Image mushroom;

    public static final int BACKGROUND_WIDTH = 500;
    public static final int BACKGROUND_HEIGHT = 600;
    public static final int MARIO_WIDTH = 25;
    public static final int MARIO_HEIGHT = 50;

    static {
        try {
            background = new Image("file:images/background.png");
            startButton = new Image("file:images/StartButton.png");
            stand = new Image("file:images/stand.png");
            walk1 = new Image("file:images/walk1.png");
            walk2 = new Image("file:images/walk2.png");
            walk3 = new Image("file:images/walk3.png");
            walk4 = new Image("file:images/walk2.png");
            replay = new Image("file:images/replay.png");
            mushroom = new Image("file:images/mushroom.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
