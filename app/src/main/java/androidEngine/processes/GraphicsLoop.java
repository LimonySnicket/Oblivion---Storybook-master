package androidEngine.processes;

/**
 * Created by sn317602 on 1/1/2018.
 */

public class GraphicsLoop extends Thread{
    private int maxFPS;
    private boolean powerSavingMode;
    public double averageMillisPerFrame;
    public int averageFPS;
    private boolean isRunning;

    long currentTime, previousTime;
    double millisPerTick;
    int totalFrames;
    double elapsedTime;
    double totalTime;
    ThreadManager threadManager;

    //Constructors
    public GraphicsLoop(ThreadManager t, int maxFrames, boolean mode) {
        threadManager = t;
        setMaxFPS(maxFrames);
        powerSavingMode = mode;
    }

    public GraphicsLoop(ThreadManager t) {
        threadManager = t;
        setMaxFPS(100);
        powerSavingMode = true;
    }

    //
    //   Start Loop
    //
    public void run() {
        int ghosttick = 0;
        isRunning = true;
        averageFPS = 0;
        previousTime = System.nanoTime();
        totalTime = 0;
        totalFrames = 0;

        //System.out.println("System time: " + System.nanoTime());
        while(isRunning) {
            currentTime = System.nanoTime();
            elapsedTime = toMillis(currentTime - previousTime);
            previousTime = currentTime;
            totalTime += elapsedTime;

            if(Math.ceil(totalTime) >= 1000) {
                averageFPS = totalFrames;
                ghosttick = 0;
                totalTime -= 1000;
                totalFrames = 0;
                //System.out.println("Fps: " + averageFPS);
            }

            averageMillisPerFrame = toAverageMillisPerFrame(totalTime, totalFrames);
            if (powerSavingMode) {
                if (totalFrames < maxFPS) {
                    if (totalTime <= (millisPerTick * totalFrames)) {
                        try {
                            tick(elapsedTime);

                            if ((millisPerTick * (totalFrames + 1)) - totalTime < millisPerTick) {
                                Thread.sleep((int) ((Math.ceil(millisPerTick - elapsedTime))));
                            } else {
                                Thread.sleep((int) Math.ceil(millisPerTick));
                            }
                        } catch (Exception ex) {

                        }
                    } else {
                        tick(elapsedTime);
                    }
                } else {
                    ghosttick += 1;
                    try {
                        Thread.sleep((int)((millisPerTick * totalFrames) - totalTime));
                    } catch (Exception e) {

                    }
                }
            } else {
                tick(elapsedTime);
            }


        }
        try {
            this.join();
        } catch(Exception exc) {

        }
    }

    //
    //   UPDATE Methods
    //
    private void tick(double updateIncrament) {
        totalFrames++;
        threadManager.updateGraphics(updateIncrament / 100);
    }

    //
    //   Loop Settings
    //
    public void togglePowerSavingMode() {
        if(powerSavingMode) powerSavingMode = false;
        else powerSavingMode = true;
    }
    public void setMaxFPS(int maxFrames) {
        if(maxFrames > 1000) {
            maxFPS = 1000;
        }
        if(maxFrames > 20) {
            maxFPS = (maxFrames / 10) * 10;
        } else {
            maxFPS = 20;
            System.out.println("Can't be lower than 20 frames");
        }
        millisPerTick = 1000 / maxFPS;
    }
    public void stopGraphicsLoop() {
        isRunning = false;
    }

    //
    //   Return Methods
    //
    public boolean isSavingPower() {
        return powerSavingMode;
    }

    //
    //   Private Time Calculators
    //
    private double toMillis(long nanotime){
        return (double) nanotime / 1000000.00;

    }
    private double toAverageMillisPerFrame(double time, int totalFrames) {
        return (double) time / totalFrames;
    }
    private double toFrameRate(double time, int totalFrames) {
        return (double) totalFrames * (time/1000);
    }
}
