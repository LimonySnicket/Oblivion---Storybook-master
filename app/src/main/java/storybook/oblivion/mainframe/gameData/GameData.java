package storybook.oblivion.mainframe.gameData;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;

import androidEngine.androidGraphics.graphicComponents.GraphicAnimation;
import androidEngine.androidGraphics.graphicComponents.GraphicBackground;
import storybook.oblivion.R;

/**
 * Created by andrew on 11/23/17.
 */

public class GameData {
    static GraphicBackground currentMap;
    static Activity activity;
    //
    //   Image Data
    //
    static int[] imageData = {
            -1,
            R.drawable.bg1,
            R.drawable.boy
    };
    static int[][][] animationData = {
            {
                    {300, 0, 4},
                    {200, 4, 8},
                    {150, 12, 10},
                    {200, 22, 4}
            }
    };

    // Image ID, Collision x, Collision y, width, height, animationData ID
    static int[][] characterData = {
            {2, 80, 100, 0}
    };
    static GraphicAnimation[] mapImages = {
            new GraphicAnimation(null, 100, 100, 1, 1)
    };
    static GraphicAnimation[] objImages = {
            new GraphicAnimation(null, 100, 100, 1, 1)
    };
    static GraphicAnimation[] charImages = {
            new GraphicAnimation(null, 80, 100, 27, 6),
    };
    static GraphicAnimation[] guiImages;

    //
    //   info to be put into file
    //
    public static String[] mapNames = {
            "Test Map"
    };

    public static int[][] maps = {
            {0, 0, 10000, 800}
    };

    // x-coord, y-coord, width, height, solidity, image Position
    public static int[][][] mapSolidObjects = {
            {
                    {-30, -100, 10, 2000, 2, 0, Color.RED},
                    {0, 750, 10000, 50, 2, 0, Color.RED},
                    {10030, -100, 10, 20, 2, 0, Color.RED},

                    {500, 200, 200, 50, 2, 0, Color.RED},
                    {500, 400, 500, 100, 2, 0, Color.RED},
                    {100, 250, 400, 50, 2, 0, Color.RED},
                    {700, 700, 400, 50, 2, 0, Color.RED},
                    {900, 650, 400, 50, 2, 0, Color.RED},
                    {1100, 600, 400, 50, 2, 0, Color.RED},
                    {1400, 550, 400, 50, 2, 0, Color.RED},
                    {1700, 750, 400, 50, 2, 0, Color.RED},
                    {2000, 700, 400, 50, 2, 0, Color.RED},
                    {2300, 300, 400, 50, 2, 0, Color.RED},
                    {2300, 100, 400, 50, 2, 0, Color.RED},
                    {2700, 550, 400, 50, 2, 0, Color.RED},
            }
    };
}
