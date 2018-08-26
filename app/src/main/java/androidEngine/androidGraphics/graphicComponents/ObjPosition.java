package androidEngine.androidGraphics.graphicComponents;

import androidEngine.androidGraphics.AndroidConfig;

/**
 * Created by andrew on 9/23/17.
 */

public class ObjPosition extends Coordinates{
    public static AndroidConfig androidConfig;
    protected double xScreen, yScreen;
    protected double height, width;
    protected double heightScreen, widthScreen;
    protected double radius;

    //
    //   Setting Config Data
    //   Only relevant for Graphics
    //
    public static void setAndroidConfig(AndroidConfig a) {
        androidConfig = a;
    }

    //
    //   Constructor
    //
    public ObjPosition(int x, int y, int w, int h) {
        super(x, y);
        setPosition(x, y);
        setDimension(w, h);
    }

    //
    //   Setting the game coordinates and dimensions
    //
    public void setPosition(double x, double y) {
        xPos = x;
        yPos = y;
        try {
            xScreen = xPos * androidConfig.getAspectRatio();
            yScreen = yPos * androidConfig.getAspectRatio();
        } catch (Exception e) {
            xScreen = xPos;
            yScreen = yPos;
        }
    }
    public void setDimension(int w, int h) {
        height = h;
        width = w;
        try {
            heightScreen = height * androidConfig.getAspectRatio();
            widthScreen = width * androidConfig.getAspectRatio();
        } catch (Exception e) {
            heightScreen = height;
            widthScreen = width;
        }
        radius = Math.sqrt((height * height) + (width * width)) / 2;
    }
    //
    //   Return Methods
    //
    public double getRadius() {
        return radius;
    }
    public double getxScreen() {
        return xScreen;
    }
    public double getyScreen() {
        return yScreen;
    }
    public double getHeight() {
        return height;
    }
    public double getHeightScreen() {
        return heightScreen;
    }
    public double getWidth() {
        return width;
    }
    public double getWidthScreen() {
        return widthScreen;
    }

    public double getRightX() {
        return xPos + width;
    }
    public double getLeftX() {
        return xPos + width;
    }
    public double getTopY() {
        return yPos + height;
    }
    public double getBottomY() {
        return yPos + height;
    }
}
