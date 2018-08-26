package storybook.oblivion.mainframe.gameData;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.Nullable;

import androidEngine.androidGraphics.AndroidConfig;
import androidEngine.androidGraphics.graphicComponents.GraphicAnimation;
import androidEngine.androidGraphics.graphicComponents.GraphicBackground;
import storybook.oblivion.R;
import storybook.oblivion.mainframe.gameComponents.graphicComponent.CharacterObject;
import storybook.oblivion.mainframe.gameComponents.graphicComponent.SolidObject;

import static storybook.oblivion.mainframe.gameData.GameData.*;

/**
 * Created by andrew on 10/20/17.
 */

public class GameManager {

    //
    //   Creating Objects
    //
    public static Bitmap getImage(int i) {
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeResource(AndroidConfig.getConfig().getActivity().getResources(), imageData[i]);
        } catch (Exception e) {
            bitmap = null;
            e.printStackTrace();
        }
        System.out.println("Bitmap: " + bitmap);
        return bitmap;
    }

    public static CharacterObject createNewCharacter(int charNum) {
        if(charNum < characterData.length && charNum >= 0) {
            GraphicAnimation tempAnim = charImages[charNum];
            tempAnim.setOriginalSheet(getImage((int) characterData[charNum][0]));
            tempAnim.setAnimationData(animationData[(int) characterData[charNum][5]]);
            System.out.println(tempAnim);
            return new CharacterObject.Builder(tempAnim).colorIndex(Color.BLACK).width(characterData[charNum][1])
                    .height(characterData[charNum][2]).position(80, 100).build();
        }
        return null;
    }

    @Nullable
    public static GraphicBackground createNewMap(int mapNum) {
        if (0 <= mapNum && mapNum <= mapNames.length){
            GraphicAnimation tempback = mapImages[mapNum];
            tempback.setOriginalSheet(getImage(1));
            System.out.println("Background" + tempback);
            currentMap = new GraphicBackground.Builder(tempback).name(mapNames[mapNum]).
                    position(maps[mapNum][0], maps[mapNum][1]).width(maps[mapNum][2]).
                    height(maps[mapNum][3]).build();
            currentMap.setFrame(AndroidConfig.getConfig());
            for(int i = 0; i < mapSolidObjects[mapNum].length; i++) {
                try {
                    currentMap.addStaticObject(
                            new SolidObject.Builder(objImages[mapSolidObjects[mapNum][i][5]]).
                            position(mapSolidObjects[mapNum][i][0], mapSolidObjects[mapNum][i][1]).
                            width(mapSolidObjects[mapNum][i][2]).
                            height(mapSolidObjects[mapNum][i][3]).
                            solidity(mapSolidObjects[mapNum][i][4]).
                            colorIndex(mapSolidObjects[mapNum][i][6])
                            .build()
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return currentMap;
        } else {
            return null;
        }
    }

    public static void setActivity(Activity a) {
        activity = a;
    }
}
