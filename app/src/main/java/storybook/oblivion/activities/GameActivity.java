package storybook.oblivion.activities;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidEngine.androidGraphics.AndroidConfig;
import storybook.oblivion.mainframe.graphics.androidGraphics.GamePanel;

public class GameActivity extends Activity {
    GamePanel panel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        panel = new GamePanel(this, AndroidConfig.getConfig());

        setContentView(panel);
        panel.requestFocus();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        System.out.println(getWindow().getCurrentFocus());
        System.out.println("Menu Screen");

    }
    @Override
    protected void onStart() {
        super.onStart();
        panel.getThreads().start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Resuming Menu Activity");
    }
    @Override
    protected void onPause() {
        super.onPause();

        System.out.println("Pausing Menu Activity");
    }



    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("Resuming Menu Activity");
        try {
            panel.getThreads().stopThreads();
        } catch (Exception e) {

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            panel.getThreads().stopThreads();
        } catch(Exception e) {
            System.out.println("No Panel");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("Menu Activity Destroyed");
    }
}
