package androidEngine.androidGraphics;

import android.app.Activity;
import android.util.DisplayMetrics;

public class AndroidConfig{
    static AndroidConfig androidConfig;
    DisplayMetrics displayMetrics = new DisplayMetrics();
    Activity activity;

    int screenHeight;
    int screenWidth;
    double resolutionWidth;
    double resolutionHeight;

    double aspectRatio;

    private AndroidConfig(Activity a) {
        activity = a;
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        System.out.println("DM Height: " + displayMetrics.heightPixels);
        System.out.println("DM Width: " + displayMetrics.widthPixels);

        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
    }

    public static void createAndroidConfig(Activity activity) {
        androidConfig = new AndroidConfig(activity);
    }

    public static AndroidConfig getConfig() {
        if(androidConfig != null) {
            return androidConfig;
        } else {
            System.out.println("No AndroidConfig Present");
            return null;
        }
    }

    public void setResolution(int wid, int hei){
        resolutionHeight = hei;
        resolutionWidth = wid;
        updateAspectRatio();
    }

    public void updateAspectRatio() {
        double widScale = (double) resolutionWidth / screenWidth ;
        double heiScale = (double) resolutionHeight / screenHeight;

        if(widScale < heiScale) {
            resolutionHeight = screenHeight * widScale;
            aspectRatio = 1 / widScale;
        } else {
            resolutionWidth = screenWidth * heiScale;
            aspectRatio = 1 / heiScale;
        }
    }


    public void printConfigData() {
        System.out.println("Screen Height: " + screenHeight);
        System.out.println("Screen Width: " + screenWidth);
        System.out.println("Resolution Height: " + resolutionHeight);
        System.out.println("Resolution Width: " + resolutionWidth);
        System.out.println("Aspect Ratio: " + aspectRatio);
    }
    public double getAspectRatio() {
        return aspectRatio;
    }
    public int getScreenHeight() {
        return screenHeight;
    }
    public int getScreenWidth() {
        return screenWidth;
    }
    public double getResolutionWidth() {
        return resolutionWidth;
    }
    public double getResolutionHeight() {
        return resolutionHeight;
    }
    public Activity getActivity() {
        return activity;
    }
}
