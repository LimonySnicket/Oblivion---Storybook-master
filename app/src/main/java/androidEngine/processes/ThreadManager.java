package androidEngine.processes;

import androidEngine.androidGraphics.ViewPanel;

/**
 * Created by andrew on 11/19/17.
 */

public class ThreadManager {
    protected GameLoop gameProcess;
    protected GraphicsLoop graphicProcess;
    protected ViewPanel panel;
    public ThreadManager(ViewPanel p) {
        panel = p;
        gameProcess = new GameLoop(this);
        graphicProcess = new GraphicsLoop(this);
    }

    //
    //   Start Thread
    //
    public void start() {
        gameProcess.start();
        graphicProcess.start();
    }

    //
    //   Update Methods
    //
    public void checkInput() {
        panel.checkInput();
    }

    public void updateGame(double inc) {
        panel.updateGame(inc);
    }
    public void updateGraphics(double inc) {
        panel.updateGraphics(inc);
    }

    //
    //   Stop All Threads
    //
    public void stopThreads() {
        gameProcess.stopGameLoop();
        graphicProcess.stopGraphicsLoop();
    }

    //
    //   Return Statistics
    //
    public int averageFPS() {
        return graphicProcess.averageFPS;
    }
    public int updateRate() {
        return gameProcess.averageFPS;
    }
}
