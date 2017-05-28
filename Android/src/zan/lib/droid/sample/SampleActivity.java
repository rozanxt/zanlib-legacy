package zan.lib.droid.sample;

import zan.lib.droid.core.CoreSurfaceView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SampleActivity extends Activity {

    protected CoreSurfaceView coreSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        coreSurfaceView = new CoreSurfaceView(this, new SamplePanel());

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
