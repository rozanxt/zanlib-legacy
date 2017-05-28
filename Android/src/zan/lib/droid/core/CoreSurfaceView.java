package zan.lib.droid.core;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import zan.lib.droid.core.BasePanel;
import zan.lib.droid.util.Utility;

public class CoreSurfaceView extends GLSurfaceView {

    protected BasePanel corePanel;
    protected CoreRenderer coreRenderer;

    public CoreSurfaceView(Context context, BasePanel panel) {
        super(context);
        super.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        setEGLContextClientVersion(2);

        Utility.init(context);

        corePanel = panel;
        coreRenderer = new CoreRenderer(this);
        setRenderer(coreRenderer);

        corePanel.init();
    }

    protected void check() {
        BasePanel nextPanel = corePanel.changePanel();
        if (nextPanel != null) {
            corePanel.destroy();
            corePanel = nextPanel;
            corePanel.init();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        corePanel.onTouchEvent(e);
        return true;
    }

}
