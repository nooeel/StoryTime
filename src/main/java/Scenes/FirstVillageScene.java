package Scenes;

import engine.Window;

public class FirstVillageScene extends Scene {

    public FirstVillageScene() {
        System.out.println("In FirstVillageScene - 1");
        Window.get().r = 1;
        Window.get().g = 0;
        Window.get().b = 0;
    }

    @Override
    public void update(float dt) {

    }
}
