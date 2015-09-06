package zan.project.core;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class CoreActivity extends Activity {

    protected CoreSurfaceView coreSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        coreSurfaceView = new CoreSurfaceView(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(coreSurfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        coreSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        coreSurfaceView.onResume();
    }

}
