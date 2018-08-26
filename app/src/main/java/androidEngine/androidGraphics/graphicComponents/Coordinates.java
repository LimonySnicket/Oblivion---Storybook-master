package androidEngine.androidGraphics.graphicComponents;

/**
 * Created by andrew on 11/19/17.
 */

public class Coordinates {
    //Small Class to Organize Data
    protected double xPos, yPos;
    public Coordinates(double x, double y) {
        xPos = x;
        yPos = y;
    }

    public void setX(double x) {
        xPos = x;
    }
    public void setY(double y) {
        yPos = y;
    }
    public double getX() {
        return xPos;
    }
    public double getY() {
        return yPos;
    }
    public String toString() {
        return xPos + ", " + yPos;
    }
}
