package storybook.oblivion.mainframe.graphics.androidGraphics;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.ArrayList;

import androidEngine.androidGraphics.ViewPanel;
import androidEngine.androidGraphics.graphicComponents.GraphicObject;
import androidEngine.processes.GameLoop;
import androidEngine.androidGraphics.AndroidConfig;


/**
 * Created by andrew on 9/25/17.
 */

public class MenuPanel extends ViewPanel {
    public ArrayList<GraphicObject> buttons;

    public MenuPanel(SurfaceHolder sh, Context context, AndroidConfig config) {
        super(context, config);

        setFocusable(true);

    }

    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:


        }
        return true;
    }

    public void checkInput() {

    }

    public void updateGame(double updateIncrament) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
