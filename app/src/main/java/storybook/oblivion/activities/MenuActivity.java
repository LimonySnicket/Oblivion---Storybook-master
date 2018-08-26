package storybook.oblivion.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import storybook.oblivion.R;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
