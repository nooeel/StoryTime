package Scenes;

import engine.KeyListener;
import engine.Window;

import java.awt.event.KeyEvent;

public class TestScene extends Scene {

    private boolean changingScene = false;
    private float timeToChangeScene = 5.0f;

    public TestScene() {
        System.out.println("In TestScene - 0");
    }

    @Override
    public void update(float dt) {


        if (!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            changingScene = true;
        }

        if (changingScene && timeToChangeScene > 0) {
            timeToChangeScene -= dt;
            Window.get().r -= dt * 0.5f;
            Window.get().g -= dt * 0.5f;
            Window.get().b -= dt * 0.5f;

        } else if (changingScene) {
            Window.changeScene(1); // zu First Village
        }
    }
}
