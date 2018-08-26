package androidEngine.androidGraphics.graphicComponents;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Comparator;

import storybook.oblivion.mainframe.gameComponents.GameCharacter;

/**
 * Created by andrew on 9/24/17.
 */

public class GraphicObject {
    protected ObjPosition position;
    //
    //  solidity
    //  0 = no, 1 = semi, 2 = yes
    //
    protected int solidity;
    protected GraphicAnimation image;
    protected int color;
    protected Paint paint;
    protected int numOfCollisions;
    protected ArrayList<ObjectStatus> listOfStatus;

    protected boolean facingRight;
    //
    //   Object Data
    //
    protected boolean needsGameUpdate;
    protected double xVel, yVel;

    //
    //   Builder
    //   Organize instantiation data
    //
    public static class Builder {
        private final GraphicAnimation image;

        private int width, height;
        private int x, y;
        private double xCol, yCol;
        private int solidity;
        private int colorIndex;

        public Builder(GraphicAnimation image) {
            this.image = image;
            this.xCol = 0.0;
            this.yCol = 0.0;
            //colorIndex = 0;
        }

        public Builder colorIndex(int i) {
            colorIndex = i; return this;
        }
        public Builder solidity(int s) {
            solidity = s; return this;
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
        public GraphicObject build() {
            return new GraphicObject(this);
        }
    }

    //
    //   Constructor
    //   Not Recommended
    //
    public GraphicObject(ObjPosition pos, int solid, GraphicAnimation img, int colorIndex) {
        position = pos;
        solidity = solid;
        image = img;
        color = colorIndex;
        xVel = 0;
        yVel = 0;
        listOfStatus = new ArrayList<ObjectStatus>();
        paint = new Paint();
        paint.setColor(color);
        System.out.println("Creating Object");

        facingRight = true;
    }

    //
    //   Builder Constructor
    //
    private GraphicObject(Builder builder) {
        this(new ObjPosition(builder.x, builder.y, builder.width, builder.height),
                builder.solidity, builder.image, builder.colorIndex);

    }


    //
    //   Setter Methods
    //
    public void setVelocity(double x, double y) {
        needsGameUpdate = true;
        xVel = x;
        yVel = y;
    }

    public void setPosition(double x, double y) {
        needsGameUpdate = true;
        position.setPosition(x, y);
    }

    //   Add Status to the Game;
    public void addStatus(ObjectStatus status) {
        listOfStatus.add(status);
    }

    //
    //   Update Methods
    //
    public void update(double inc, ArrayList<GraphicObject>[] objects) {
        needsGameUpdate = false;
        int tempCollisions = 0;
        ArrayList<ObjectStatus> tempStatuses = new ArrayList<>(listOfStatus);
        tempStatuses.clear();

        Coordinates tempCoords = new Coordinates(position.getX() + xVel * inc, position.getY() + yVel * inc);
        for(ArrayList<GraphicObject> list : objects) {
            for (GraphicObject object: list) {
                if(!object.equals(this)) {
                    if (willTouch(object, tempCoords.getX(), tempCoords.getY())) {
                        tempCollisions += 1;
                        tempCoords = object.checkCollision(this, tempCoords);
                    } else if (position.getWidth() > object.getObjPosition().getX() + object.getObjPosition().getWidth()) {
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
        numOfCollisions = tempCollisions;
        listOfStatus = tempStatuses;
        position.setPosition(tempCoords.getX(), tempCoords.getY());
    }

    public void checkStatus() {
        //
        //    Method to Check Object Status;
        //    To be Implemented
        //

    }

    //
    //   Collision Methods
    //

    public boolean checkCoordinateCollision(Coordinates coords) {
        if(coords.getX() > position.getLeftX() && coords.getX() < position.getRightX()) {
            if(coords.getY() > position.getTopY() && coords.getY() < position.getBottomY()) {
                return true;
            }
        }
        return false;
    }

    public boolean willTouch(GraphicObject object, double x, double y) {
        if(x + position.getWidth() >= object.getObjPosition().getLeftX() &&
                x <= object.getObjPosition().getRightX()) {
            if(y + position.getHeight() >= object.getObjPosition().getTopY() &&
                    y <= object.getObjPosition().getBottomY()) {
                return true;
            }
        }
        /*
            System.out.println(object + ",  " + this);
            System.out.println("Width: " + position.getCollisionWidth());
            System.out.println("Side: " + position.getSpaceFromSide());
            System.out.println("Height: " + position.getCollisionHeight());
            System.out.println("Top: " + position.getSpaceFromTop());
            System.out.println(x + position.getCollisionWidth() + position.getSpaceFromSide() + ", " + object.getObjPosition().getLeftX());
            System.out.println(x + position.getSpaceFromSide() + ", " + object.getObjPosition().getRightX());
            System.out.println(y + position.getCollisionHeight() + position.getSpaceFromTop() + ", " + object.getObjPosition().getTopY());
            System.out.println(y + position.getSpaceFromTop() + ", " + object.getObjPosition().getBottomY());
        */
        return false;
    }

    public Coordinates checkCollision(GraphicObject object, Coordinates xy) {
        object.isTouching(this);
        //   Override method to add collision detection and object interaction
        //
        //   returns temp coords
        //
        return xy;
    }

    public void isTouching(GraphicObject object) {
        //   Override method to do an action when it touches the object
        //
        //   Empty
    }


    //
    //   Draw Images onto Canvas
    //
    public void draw(Canvas canvas){
        try {
            if(facingRight) {
                canvas.drawBitmap(image.getFrame(),
                        (float) (position.getxScreen()),
                        (float) (position.getyScreen()),
                        paint);
            } else {
                canvas.drawBitmap(image.getFlippedFrame(),
                        (float) (position.getxScreen()),
                        (float) (position.getyScreen()),
                        paint);
            }
        } catch (Exception e) {
            //System.out.println("Drawing IMAGE");
            canvas.drawRect(
                    getxScreen(),
                    getyScreen(),
                    getxScreen() + getWidthScreen(),
                    getyScreen() + getHeightScreen(),
                    paint);
        }
    }
    public void drawRelativeToCoords(Canvas canvas, int x, int y){
        try {
            if(facingRight) {
                canvas.drawBitmap(image.getFrame(),
                        (float) (position.getxScreen()) + x,
                        (float) (position.getyScreen()) + y,
                        paint);
            } else {
                canvas.drawBitmap(image.getFlippedFrame(),
                        (float) (position.getxScreen()) + x,
                        (float) (position.getyScreen()) + y,
                        paint);
            }
        } catch (Exception e) {
            //System.out.println("Drawing IMAGE");
            canvas.drawRect(
                    getxScreen() + x,
                    getyScreen() + y,
                    getxScreen() + getWidthScreen() + x,
                    getyScreen() + getHeightScreen() + y,
                    paint);
        }
    }



    //
    //   Comparators
    //
    public static Comparator<GraphicObject> XValComparator = new Comparator<GraphicObject>() {
        public int compare(GraphicObject obj1, GraphicObject obj2) {
            int x1 = obj1.getX();
            int x2 = obj2.getX();

            return x1 - x2;
        }
    };

    public static Comparator<GraphicObject> XWidthValComparator = new Comparator<GraphicObject>() {
        public int compare(GraphicObject obj1, GraphicObject obj2) {
            int x1 = obj1.getX() + obj1.getWidth();
            int x2 = obj2.getX() + obj2.getWidth();

            return x2 - x1;
        }
    };

    //
    //   Return Methods
    //
    public String toString() {
        return "GraphicObject X: " + getX() + " Y: " + getY() +
                " width: " + position.getWidth() + " height: " + position.getHeight();
    }
    public int objectIsSolid() {
        return solidity;
    }
    public int getX() {
        return (int) position.getX();
    }
    public int getY() {
        return (int) position.getY();
    }
    public int getWidth() {
        return (int) position.getWidth();
    }
    public int getHeight() {
        return (int) position.getHeight();
    }
    public int getxScreen() {
        return (int) position.getxScreen();
    }
    public int getyScreen() {
        return (int) position.getyScreen();
    }
    public int getHeightScreen() {
        return (int) position.getHeightScreen();
    }
    public int getWidthScreen() {
        return (int) position.getWidthScreen();
    }
    public ObjPosition getObjPosition() {
        return position;
    }
    public boolean needsGameUpdate() {
        return needsGameUpdate;
    }
    public double getXVel() {
        return xVel;
    }
    public double getYVel() {
        return yVel;
    }
}
