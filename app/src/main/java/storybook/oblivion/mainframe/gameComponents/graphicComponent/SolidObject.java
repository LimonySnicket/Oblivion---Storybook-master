package storybook.oblivion.mainframe.gameComponents.graphicComponent;

import androidEngine.androidGraphics.graphicComponents.Coordinates;
import androidEngine.androidGraphics.graphicComponents.GraphicAnimation;
import androidEngine.androidGraphics.graphicComponents.GraphicObject;
import androidEngine.androidGraphics.graphicComponents.ObjPosition;

/**
 * Created by andrew on 11/19/17.
 */

public class SolidObject extends GraphicObject {
    public static class Builder {
        private final GraphicAnimation image;

        private int width, height;
        private int x, y;
        private int solidity;
        private int colorIndex;

        public Builder(GraphicAnimation image) {
            this.image = image;
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
            return new SolidObject(this);
        }
    }

    //
    //   Constructor
    //   Not Recommended
    //
    public SolidObject(ObjPosition pos, int solid, GraphicAnimation img, int i) {
        super(pos, solid, img, i);
    }

    //
    //   Builder Constructor
    //
    private SolidObject(Builder builder) {
        this(new ObjPosition(builder.x, builder.y, builder.width, builder.height),
                builder.solidity, builder.image, builder.colorIndex);

    }

    //
    //   Collision Methods
    //

    public Coordinates checkCollision(GraphicObject object, Coordinates xy) {
        Coordinates tempCoords = xy;
        if(object.getObjPosition().getRightX() <= position.getLeftX() &&
                tempCoords.getX() + object.getObjPosition().getWidth()
                        >= position.getLeftX()) {
            tempCoords.setX(position.getLeftX() - object.getObjPosition().getWidth());

            object.addStatus(new BlockStatus(BlockStatus.TOUCHING_LEFT_SIDE));
            //System.out.println("Touching LEFT");
        }else if (object.getObjPosition().getLeftX() >= position.getRightX() &&
                tempCoords.getX() <= position.getRightX()) {
            tempCoords.setX(position.getRightX());

            object.addStatus(new BlockStatus(BlockStatus.TOUCHING_RIGHT_SIDE));
            //System.out.println("Touching RIGHT");
        } else if(object.getObjPosition().getY() + object.getObjPosition().getHeight() <=
                position.getY() && tempCoords.getY() + object.getObjPosition().getHeight() >= position.getY()) {
            tempCoords.setY(position.getY() - object.getObjPosition().getHeight());

            object.addStatus(new BlockStatus(BlockStatus.TOUCHING_FLOOR));

        }else if (object.getObjPosition().getY() >= position.getY() + position.getHeight() &&
                tempCoords.getY() <= position.getY() + position.getHeight()) {
            tempCoords.setY(position.getY() + position.getHeight());

            object.addStatus(new BlockStatus(BlockStatus.TOUCHING_CEIL));
        }
        //System.out.println("X: " + tempCoords.getX());
        return tempCoords;
    }

    public void isTouching(GraphicObject object) {
        //   Override method to do an action when it touches the object
        //
        //   Empty
    }
}
