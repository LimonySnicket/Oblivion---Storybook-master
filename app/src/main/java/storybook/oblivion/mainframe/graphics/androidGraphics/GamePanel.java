package storybook.oblivion.mainframe.graphics.androidGraphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.ArrayList;

import androidEngine.androidGraphics.ViewPanel;
import androidEngine.androidGraphics.graphicComponents.Coordinates;
import androidEngine.androidGraphics.graphicComponents.GraphicBackground;
import androidEngine.androidGraphics.graphicComponents.GraphicObject;
import androidEngine.androidGraphics.graphicComponents.ObjPosition;
import storybook.oblivion.mainframe.gameComponents.graphicComponent.Button;
import storybook.oblivion.mainframe.gameComponents.graphicComponent.CharacterObject;
import storybook.oblivion.mainframe.gameData.GameManager;
import androidEngine.androidGraphics.AndroidConfig;

/**
 * Created by andrew on 10/22/17.
 */

public class GamePanel extends ViewPanel {
    GraphicBackground currentMap;
    Paint paint;
    CharacterObject character;
    boolean focusOnPlayer;
    ArrayList<PointerGesture> pointerGestures;
    ArrayList<Button> buttons;

    //
    //   Constructor
    //
    public GamePanel(Context context, AndroidConfig android) {
        super(context, android);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        paint.setAntiAlias(true);
        paint.setFakeBoldText(true);
        pointerGestures = new ArrayList<>();
        buttons = new ArrayList<>();

        setMap(0);
        character = GameManager.createNewCharacter(0);
        currentMap.addDynamicObject(character);
        focusOnPlayer = true;
    }

    //
    //  Setting new Map
    //
    public void setMap(int mapNum) {
        currentMap = GameManager.createNewMap(mapNum);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ArrayList<PointerGesture> tempGestures = new ArrayList<>(pointerGestures);
        PointerGesture gesture = null;
        for(int i = 0; i < event.getPointerCount(); i++) {
            Coordinates pointerCoords = new Coordinates(event.getX(i), event.getY(i));
            //System.out.println("Pointer: " + event.getPointerId(i) + " Coords: " + pointerCoords);
            for (PointerGesture p : tempGestures) {
                try {
                    if (event.getPointerId(i) == p.getID()) {
                        gesture = p;
                    }
                } catch (Exception e) {
                    System.out.println("POINTER ERROR");
                }
            }
            if (gesture == null) {
                //System.out.println("Pointer: " + event.getPointerId(i));
                gesture = new PointerGesture(event.getPointerId(i));
                tempGestures.add(gesture);
                gesture.addCoord(pointerCoords);
                updateGesture(gesture);
            }
            //System.out.println(tempGestures);

            if (Math.abs(gesture.getCoords().get(gesture.getCoords().size() - 1).getX() - pointerCoords.getX()) > 10 ||
                    Math.abs(gesture.getCoords().get(gesture.getCoords().size() - 1).getY() - pointerCoords.getY()) > 10) {
                gesture.addCoord(pointerCoords);
                updateGesture(gesture);
            }
        }
        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_UP:
                tempGestures.remove(gesture);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                tempGestures.clear();
                break;
        }
        try {
            if (!tempGestures.get(0).isMainPointer()) {
                tempGestures.get(0).setMainPointer();
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        pointerGestures = tempGestures;
        return true;
    }

    public void updateGesture(PointerGesture gesture) {
        Coordinates one = gesture.getCoords().get(0);
        Coordinates two = gesture.getCoords().get(gesture.getCoords().size() - 1);
        if(!one.equals(two)){
            if(one.getY() - two.getY() > 50) {
                gesture.setAction(GameAction.SLIDE_UP);
                return;
            } else if(one.getY() - two.getY() < -50) {
                gesture.setAction(GameAction.SLIDE_DOWN);
                return;
            }

            if(two.getX() - one.getX() > 50) {
                if(gesture.getAction() == GameAction.RIGHT) {
                    gesture.setAction(GameAction.SLIDE_RIGHT);
                }
                return;
            } else if(two.getX() - one.getX() < -50) {
                if(gesture.getAction() == GameAction.LEFT) {
                    gesture.setAction(GameAction.SLIDE_LEFT);
                }
                return;
            }
        }

        if(one.getX() < androidConfig.getScreenWidth() / 2) {
            gesture.setAction(GameAction.LEFT);
            return;
        } else if(one.getX() > (androidConfig.getScreenWidth() / 2)) {
            gesture.setAction(GameAction.RIGHT);
            return;
        } else {

        }
    }

    @Override
    public void checkInput() {
        character.checkInput(pointerGestures);
    }

    public void updateGame(double inc) {
        currentMap.updateObjects(inc);
        if(focusOnPlayer) {
            currentMap.setMapPosition(-(character.getX() + character.getWidth()/2)+ androidConfig.getResolutionWidth()/2,
                    -(character.getY() + character.getHeight()/2) + androidConfig.getResolutionHeight()/2, androidConfig);
        }
    }

    //
    //   Draw onto Canvas
    //
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.CYAN);
        currentMap.draw(canvas);
        canvas.drawText("FPS: " + threads.averageFPS(), 10, 60, paint);
        canvas.drawText("Update Rate: " + threads.updateRate(), 10, 105, paint);
        canvas.drawText("Touching Floor: " + character.touchingFloor(), 1000, 60, paint);
        canvas.drawText("Y Vel: " + character.getYVel() + "  Accel: " + character.acceleration(), 1000, 105, paint);



    }

    //SurfaceHolder Methods
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        System.out.println("Changing Surface");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        threads.stopThreads();
    }
}
