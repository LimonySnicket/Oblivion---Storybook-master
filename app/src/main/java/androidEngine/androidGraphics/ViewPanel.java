package androidEngine.androidGraphics;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import androidEngine.processes.ThreadManager;

/**
 * Created by andrew on 9/25/17.
 */

public abstract class ViewPanel extends SurfaceView implements SurfaceHolder.Callback {
    protected ThreadManager threads;
    protected SurfaceHolder surfaceHolder;
    protected static Canvas canvas;
    public AndroidConfig androidConfig;

    //
    //
    //
    public ViewPanel(Context context, AndroidConfig android) {
        super(context);
        androidConfig = android;

        setFocusable(true);
        threads = new ThreadManager(this);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        //surfaceHolder.setFixedSize((int)androidConfig.getResolutionWidth(), (int)androidConfig.getResolutionHeight());
        System.out.println("________________");
        System.out.println("width: " + surfaceHolder.getSurfaceFrame().width());
        System.out.println("height: " + surfaceHolder.getSurfaceFrame().height());
        System.out.println("________________");
    }

    //
    //   Check Input and Update Methods
    //            Abstract
    //
    public abstract void checkInput();

    //
    //   Update Each Loop
    //
    public abstract void updateGame(double updateIncrament);

    public void updateGraphics(double updateIncrament) {

        canvas = null;
        try {
            canvas = surfaceHolder.lockCanvas();
            synchronized (surfaceHolder) {
                draw(canvas);
            }

        } catch (Exception e){

        } finally {
            if(canvas != null) {
                try{
                    surfaceHolder.unlockCanvasAndPost(canvas);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



    //
    //   Draw onto Canvas
    //
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    //
    //   Stop game thread
    //
    public void stopLoop() {
        threads.stopThreads();
    }

    public ThreadManager getThreads() {
        return threads;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.width = androidConfig.getScreenWidth();
        lp.height = androidConfig.getScreenHeight();
        setLayoutParams(lp);
    }

    @Override
    public abstract void surfaceChanged(SurfaceHolder holder, int format, int width, int height);

    @Override
    public abstract void surfaceDestroyed(SurfaceHolder holder);
}
