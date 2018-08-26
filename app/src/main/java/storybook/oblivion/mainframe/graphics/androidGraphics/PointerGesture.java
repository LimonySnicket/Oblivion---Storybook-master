package storybook.oblivion.mainframe.graphics.androidGraphics;

import java.util.ArrayList;

import androidEngine.androidGraphics.graphicComponents.Coordinates;

/**
 * Created by sn317602 on 12/5/2017.
 */

public class PointerGesture {
    private ArrayList<Coordinates> coords;
    private final int POINTER_ID;
    private boolean buttonPressed;
    private GameAction action;
    private boolean mainPointer;

    public PointerGesture(int i) {
        POINTER_ID = i;
        coords = new ArrayList<>();
        buttonPressed = false;
        mainPointer = false;
    }

    public void addCoord(Coordinates coord) {
        coords.add(coord);
    }

    public void setAction(GameAction act) {
        action = act;
    }
    public void setMainPointer() {
        mainPointer = true;
    }
    public void pressButton() {
        buttonPressed = true;
    }

    public ArrayList<Coordinates> getCoords() {
        return coords;
    }
    public GameAction getAction() {
        return action;
    }
    public int getID() {
        return POINTER_ID;
    }
    public boolean buttonPressed() {
        return buttonPressed;
    }
    public boolean isMainPointer() {
        return mainPointer;
    }

    public String toString() {
        return "Pointer: " + POINTER_ID + " | Action: " + action + " | Coords: " + coords;
    }
}
