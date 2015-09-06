package zan.lib.droid.core;

import android.view.MotionEvent;

public abstract class BasePanel {

    public abstract void init();
    public abstract void destroy();
    public abstract void update();
    public abstract void render();

    public BasePanel changePanel() {return null;}

    public abstract void onTouchEvent(MotionEvent e);

    public abstract void onSurfaceCreated();
    public abstract void onSurfaceChanged(int width, int height);

}
