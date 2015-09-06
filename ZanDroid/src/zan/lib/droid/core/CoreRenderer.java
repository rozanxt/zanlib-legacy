package zan.project.core;

import static android.opengl.GLES20.*;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CoreRenderer implements GLSurfaceView.Renderer {

    protected CoreSurfaceView coreSurfaceView;

    public CoreRenderer(CoreSurfaceView surfaceView) {
        coreSurfaceView = surfaceView;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        coreSurfaceView.corePanel.onSurfaceCreated();
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        glViewport(0, 0, width, height);    // TODO
        coreSurfaceView.corePanel.onSurfaceChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        coreSurfaceView.corePanel.update();
        coreSurfaceView.corePanel.render();
        coreSurfaceView.check();
    }

}
