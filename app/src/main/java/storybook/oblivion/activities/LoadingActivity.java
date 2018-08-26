package storybook.oblivion.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;



import androidEngine.androidGraphics.AndroidConfig;
import androidEngine.androidGraphics.graphicComponents.GraphicAnimation;
import androidEngine.androidGraphics.graphicComponents.ObjPosition;
import storybook.oblivion.R;
import storybook.oblivion.mainframe.gameData.FileDataManager;
import storybook.oblivion.mainframe.gameData.GameManager;

public class LoadingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_loading);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GameManager.setActivity(this);
        AndroidConfig.createAndroidConfig(this);
        ObjPosition.setAndroidConfig(AndroidConfig.getConfig());
        AndroidConfig.getConfig().setResolution(960, 700);
        AndroidConfig.getConfig().printConfigData();

        Intent nextActivity = new Intent(this, GameActivity.class);
        GraphicAnimation.setConfig(AndroidConfig.getConfig());
        startActivity(nextActivity);
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("Pausing Loading Activity");

    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Resuming Loading Activity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("Stopping Loading Activity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("Loading Activity Destroyed");
    }

}
