package Engine;

import App.Game;

public class EntryPoint {
    public static void main(String[] args) {
        WindowManager.init("Minecraft", 1920, 1080, false);
        Events.init();

        Game game = new Game();
        game.start();

        while (WindowManager.windowOpened()) {
            WindowManager.clearWindow();

            game.update();

            Events.onUpdate();
            WindowManager.onWindowUpdate();
        }
        game.end();
    }
}
