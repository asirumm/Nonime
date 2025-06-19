package io.asirum.Util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.asirum.Service.ApplicationContext;

public class SpriteBatchHelper {
    public static void setProjectionMatrixCameraCombined(SpriteBatch batch){
        OrthographicCamera camera = ApplicationContext.getInstance().getCamera();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
    }

    public static void batchEnd(SpriteBatch batch){
        batch.end();
    }
}
