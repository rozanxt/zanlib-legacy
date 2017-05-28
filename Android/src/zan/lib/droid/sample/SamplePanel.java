package zan.lib.droid.sample;

import android.view.MotionEvent;
import zan.lib.droid.R;
import zan.lib.droid.core.BasePanel;
import zan.lib.droid.gfx.obj.VertexObject;
import zan.lib.droid.gfx.shader.DefaultShader;
import zan.lib.droid.gfx.text.TextManager;
import zan.lib.droid.gfx.view.ViewPort2D;

public class SamplePanel extends BasePanel {

    private DefaultShader shader = new DefaultShader();
    private ViewPort2D viewPort = new ViewPort2D(0, 0, 0, 0);

    private VertexObject object = null;

    private float tick = 0f;

    public SamplePanel() {}

    @Override
    public void init() {
        TextManager.init();
    }

    @Override
    public void destroy() {
        object.destroy();
        shader.destroy();
        TextManager.destroy();
    }

    @Override
    public void onSurfaceCreated() {
        shader.loadProgram();
        shader.enableBlend(true);

        viewPort.showView();
        viewPort.projectView(shader);

        final int[] indices = {0, 1, 2};
        final float[] vertices = {
                -0.6f, -0.4f, 1.0f, 0.0f, 0.0f,
                0.6f, -0.4f, 0.0f, 1.0f, 0.0f,
                0.0f, 0.6f, 0.0f, 0.0f, 1.0f
        };

        object = new VertexObject(vertices, indices);
        object.setAttributes(2, 0, 3, 0);
        object.setDrawMode(VertexObject.GL_TRIANGLES);

        TextManager.loadFont(R.raw.fonts, R.drawable.defont);
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        viewPort.setScreenSize(width, height);
        viewPort.setViewPort(0, 0, width, height);
        viewPort.showView();
        viewPort.projectView(shader);
    }

    @Override
    public void onTouchEvent(MotionEvent e) {

    }

    @Override
    public void update() {
        tick += 3.6;
    }

    @Override
    public void render() {
        shader.bind();
        viewPort.adjustView(shader);

        shader.pushMatrix();
        shader.translate(0f, 0f, 0f);
        shader.rotate(tick, 0f, 1f, 1f);
        shader.scale(1f + 0.2f*(float)Math.sin(0.01f*tick), 1f + 0.2f*(float)Math.sin(0.01f*tick), 1f);
        shader.applyModelMatrix();
        object.render(shader);
        shader.popMatrix();

        shader.pushMatrix();
        shader.translate(-0.3f, 0f, 0f);
        shader.scale(0.1f, 0.1f, 1f);
        shader.applyModelMatrix();
        shader.setColor(0f, 0.5f, 0.8f, 0.8f);
        TextManager.renderText(shader, "ZanLibrary", "defont");
        shader.popMatrix();

        shader.unbind();
    }

}
