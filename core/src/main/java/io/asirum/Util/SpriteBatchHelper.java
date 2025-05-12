package io.asirum.Util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.asirum.Service.ApplicationContext;

public class SpriteBatchHelper {
    public static void projectionCombineBegin(){
        SpriteBatch batch = ApplicationContext.getInstance().getBatch();
        OrthographicCamera camera = ApplicationContext.getInstance().getCamera();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
    }

    public static void batchEnd(){
        SpriteBatch batch = ApplicationContext.getInstance().getBatch();
        batch.end();
    }
}
