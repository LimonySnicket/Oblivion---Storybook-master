package storybook.oblivion.mainframe.gameComponents.graphicComponent;

import android.graphics.Canvas;

import java.util.ArrayList;

import androidEngine.androidGraphics.graphicComponents.Coordinates;
import androidEngine.androidGraphics.graphicComponents.GraphicAnimation;
import androidEngine.androidGraphics.graphicComponents.GraphicObject;
import androidEngine.androidGraphics.graphicComponents.ObjPosition;
import androidEngine.androidGraphics.graphicComponents.ObjectStatus;
import storybook.oblivion.mainframe.graphics.androidGraphics.GameAction;
import storybook.oblivion.mainframe.graphics.androidGraphics.PointerGesture;


/**
 * Created by andrew on 11/19/17.
 */

public class CharacterObject extends GraphicObject {
    protected boolean touchingFloor;
    protected double acceleration;
    protected double gravity;
    ArrayList<GameAction> playerActionList;

    //
    //   Character Stats
    //
    double jumpHeight;

    public static class Builder {
        private final GraphicAnimation image;

        private int width, height;
        private int x, y;
        private double xCol, yCol;
        private int colorIndex;

        public Builder(GraphicAnimation image) {
            this.image = image;
            //colorIndex = 0;
            x = 0;
            y = 0;
        }

        public Builder colorIndex(int i) {
            colorIndex = i; return this;
        }
        public Builder width(int w) {
            width = w; return this;
        }
        public Builder height(int h) {
            height = h; return this;
        }
        public Builder position(int x, int y){
            this.x = x; this.y = y; return this;
        }
        public Builder collisiontRatio(double xCol, double yCol) {
            this.xCol = xCol; this.yCol = yCol; return this;
        }
        public CharacterObject build() {
            return new CharacterObject(this);
        }
    }

    //
    //   Constructor
    //   Not Recommended
    //
    public CharacterObject(ObjPosition pos, GraphicAnimation img, int i, double xCol, double yCol) {
        super(pos, 2, img, i, xCol, yCol);
        touchingFloor = false;
        acceleration = 0;
        needsGameUpdate = true;
        jumpHeight = 150;
        gravity = 2.5;

        playerActionList = new ArrayList<>();
    }

    //
    //   Builder Constructor
    //
    private CharacterObject(Builder builder) {
        this(new ObjPosition(builder.x, builder.y, builder.width, builder.height),
                builder.image, builder.colorIndex, builder.xCol, builder.yCol);

    }

    public void addAction(GameAction action) {
        if(!playerActionList.contains(action)) {
            playerActionList.add(action);
        }
    }

    public void removeAction(GameAction action) {
        playerActionList.remove(action);
    }

    public void emptyList() {
        playerActionList.clear();
    }

    //
    //   Movement
    //
    public void jump() {
        if(touchingFloor) {
            yVel = -jumpHeight;
        }
    }

    //
    //   Update Methods
    //
    public void checkInput(ArrayList<PointerGesture> gesture) {
        try {
            for (PointerGesture pointer : gesture) {
                if(pointer.isMainPointer()) {
                    switch (pointer.getAction()) {
                        case SLIDE_UP:
                            jump();
                            gesture.remove(pointer);
                            break;
                        case SLIDE_DOWN:
                            gesture.remove(pointer);
                            break;
                        case SLIDE_LEFT:
                            gesture.remove(pointer);
                            setVelocity(-50, yVel);
                            facingRight = false;
                            break;
                        case SLIDE_RIGHT:
                            gesture.remove(pointer);
                            setVelocity(50, yVel);
                            facingRight = true;
                            break;
                        case LEFT:
                            if(!(xVel < -20)) {
                                setVelocity(-20, yVel);
                            }
                            facingRight = false;
                            break;
                        case RIGHT:
                            if(!(xVel > 20)) {
                                setVelocity(20, yVel);
                            }
                            facingRight = true;
                            break;
                    }
                } else {
                    switch (pointer.getAction()) {
                        case SLIDE_UP:
                            jump();
                            gesture.remove(pointer);
                            break;
                        case SLIDE_DOWN:
                            gesture.remove(pointer);
                            break;
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void update(double inc, ArrayList<GraphicObject>[] objects) {
        int tempNumOfCollisions = 0;
        listOfStatus.clear();

        image.updateAnimation(inc);
        if(!touchingFloor) {
            acceleration += inc;
            yVel += acceleration * gravity;
            if(yVel > 150) {
                yVel = 150;
            }
        }

        Coordinates tempCoords = new Coordinates(position.getX() + xVel * inc, position.getY() + yVel * inc);
        for(ArrayList<GraphicObject> list : objects) {
            for (GraphicObject object: list) {
                if(!object.equals(this)) {
                    if (willTouch(object, tempCoords.getX(), tempCoords.getY())) {
                        numOfCollisions += 1;

                        tempCoords = object.checkCollision(this, tempCoords);
                    } else if (position.getX() > object.getObjPosition().getX() + object.getObjPosition().getWidth() + position.getWidth()) {
                        break;
                    }
                }
            }
        }


        checkStatus();
        if(Math.abs(xVel) > 0.05) {
            setVelocity(xVel - (xVel * (0.3 * inc)), yVel);
        }else
            xVel = 0;
        position.setPosition(tempCoords.getX(), tempCoords.getY());
        numOfCollisions = tempNumOfCollisions;
    }

    @Override
    public void checkStatus() {
        //
        //   Reset all Variables related to Status
        //
        touchingFloor = false;

        //
        //   Check Each Status and reset Variable accordingly
        for(ObjectStatus status : listOfStatus){
            String statusCode = status.getValue();
            switch(statusCode) {
                case BlockStatus.TOUCHING_FLOOR:
                    touchingFloor = true;
                    acceleration = 0;
                    yVel = 0.1;
                    break;
                case BlockStatus.TOUCHING_CEIL:
                    yVel = 0.1;
                    break;
            }
        }
    }

    //
    //   Collision Methods
    //
    public Coordinates checkCollision(GraphicObject object, Coordinates xy) {
        Coordinates tempCoords = xy;
        /*
        if(object.getObjPosition().getX() + object.getObjPosition().getWidth() <= position.getX() &&
                tempCoords.getX() + object.getObjPosition().getWidth() >= position.getX()) {
            tempCoords.setX(position.getX() - object.getObjPosition().getWidth());
        }else if (object.getObjPosition().getX() >= position.getX() + position.getWidth() &&
                tempCoords.getX() <= position.getX() + position.getWidth()) {
            tempCoords.setX(position.getX() + position.getWidth());
        }
        if(object.getObjPosition().getY() + object.getObjPosition().getHeight() <
                position.getY() && tempCoords.getY() + object.getObjPosition().getHeight() > position.getY()) {
            tempCoords.setY(position.getY() - object.getObjPosition().getHeight());

        }else if (object.getObjPosition().getY() > position.getY() + position.getHeight() &&
                tempCoords.getY() < position.getY() + position.getHeight()) {
            tempCoords.setY(position.getX() + position.getWidth());

        }
        */
        //System.out.println("X: " + tempCoords.getX());
        return tempCoords;
    }

    public void isTouching(GraphicObject object) {
        //   Override method to do an action when it touches the object
        //
        //   Empty
    }

    //
    //   Return Methods
    //
    public boolean touchingFloor() {
        return touchingFloor;
    }
    public int numOfCollision() {
        return numOfCollisions;
    }
    public double acceleration() {
        return acceleration;
    }
}
