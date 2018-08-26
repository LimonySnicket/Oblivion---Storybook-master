package androidEngine.androidGraphics.graphicComponents;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import androidEngine.androidGraphics.AndroidConfig;
import androidEngine.processes.UpdateManager;

/**
 * Created by andrew on 9/24/17.
 */

public class GraphicBackground {
    String title;
    GraphicAnimation mapImage;
    ObjPosition dimensions;
    private ArrayList<GraphicObject> staticMapObjects;
    private ArrayList<GraphicObject> dynamicMapObjects;
    private ArrayList<GraphicObject> coverMapObjects;
    UpdateManager updateManager;
    private Paint paint;

    boolean needsGraphicUpdate;

    //
    //   Builder Class
    //
    public static class Builder {
        private final GraphicAnimation image;

        private int width, height;
        private int x, y;
        private String name;

        public Builder(GraphicAnimation image) {
            this.image = image;
        }
        public Builder name(String n) {
            name = n; return this;
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
        public GraphicBackground build() {
            return new GraphicBackground(this);
        }
    }

    //Class Constructor
    public GraphicBackground(String name, GraphicAnimation img, ObjPosition objPos) {
        title = name;
        mapImage = img;
        dimensions = objPos;
        staticMapObjects = new ArrayList<>();
        dynamicMapObjects = new ArrayList<>();
        coverMapObjects = new ArrayList<>();
        System.out.println("Creating Map");
        paint = new Paint();
        paint.setColor(Color.LTGRAY);
        updateManager = new UpdateManager(staticMapObjects, dynamicMapObjects, coverMapObjects);
        needsGraphicUpdate = true;
    }

    //Recommended
    //Use Builder.build();
    private GraphicBackground(Builder builder) {
        this(builder.name, builder.image, new ObjPosition(builder.x, builder.y, builder.width, builder.height));
    }


    //
    //   Adding Objects
    //
    public void addStaticObject(GraphicObject object) {
        staticMapObjects.add(object);
        //System.out.println("Adding static Object");
        updateManager.sortList(staticMapObjects);
    }

    public void addDynamicObject(GraphicObject object) {
        dynamicMapObjects.add(object);
        //System.out.println("Adding dynamic Object");
        updateManager.sortList(dynamicMapObjects);
    }


    //
    //   Update Objects
    //
    public void updateObjects(double inc) {
        updateManager.updateGame(inc);
    }


    //
    //   Drawing Methods
    //
    public void draw(Canvas canvas){
        try {
            canvas.drawBitmap(mapImage.getFrame(),
                    (float) (dimensions.getxScreen()),
                    (float) (dimensions.getyScreen()),
                    paint);
        } catch (Exception e) {
            canvas.drawRect(
                    getxScreen(),
                    getyScreen(),
                    getxScreen() + getWidthScreen(),
                    getyScreen() + getHeightScreen(),
                    paint);
        }
        if(updateManager.framePresent()) {
            System.out.println(updateManager.getVisibleObjects());
            for(GraphicObject object: updateManager.getVisibleObjects()){
                object.drawRelativeToCoords(canvas, getxScreen(), getyScreen());
            }
        } else {
            for (GraphicObject staticMapObject: staticMapObjects) {
                staticMapObject.drawRelativeToCoords(canvas, getxScreen(), getyScreen());
                //System.out.println("drawing static");
            }
            for (GraphicObject dynamicMapObject :dynamicMapObjects) {
                dynamicMapObject.drawRelativeToCoords(canvas, getxScreen(), getyScreen());
                //System.out.println("drawing dynamic");
            }
        }
    }

    //
    //   Set Map Posititon
    //
    public void setMapPosition(double x, double y, AndroidConfig config) {
        double tempX = x;
        double tempY = y;

        if(tempX - config.getResolutionWidth() < -dimensions.getWidth()) {
            tempX = -dimensions.getWidth() + config.getResolutionWidth();
        } else if(tempX > 0) {
            tempX = 0;
        }
        if(tempY - config.getResolutionHeight() < - dimensions.getHeight()) {
            tempY = -dimensions.getHeight() + config.getResolutionHeight();
        } else if(tempY > 0) {
            tempY = 0;
        }
        dimensions.setPosition(tempX, tempY);
    }

    public void setMapPosition(double x, double y) {
        dimensions.setPosition(x, y);
        updateManager.updateFrame(x, y);
    }


    //
    //   Set rendering frame
    //
    public void setFrame(AndroidConfig config) {
        updateManager.setFrame(new ObjPosition((int)dimensions.getX(), (int)dimensions.getY(),
                config.getScreenWidth(), config.getScreenHeight()));
    }

    public void setFrame(int w, int h) {
        updateManager.setFrame(new ObjPosition((int)dimensions.getX(), (int)dimensions.getY(),
                w, h));
    }

    //
    //   Return Methods
    //
    public int getHeight() {
        return (int) dimensions.getHeight();
    }
    public int getWidth() {
        return (int) dimensions.getWidth();
    }
    public int getxScreen() {
        return (int) dimensions.getxScreen();
    }
    public int getyScreen() {
        return (int) dimensions.getyScreen();
    }
    public int getHeightScreen() {
        return (int) dimensions.getHeightScreen();
    }
    public int getWidthScreen() {
        return (int) dimensions.getWidthScreen();
    }
}
