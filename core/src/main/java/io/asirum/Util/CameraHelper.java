package io.asirum.Util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.asirum.Constant;
import io.asirum.Service.ApplicationContext;
import io.asirum.Service.Log;

import static io.asirum.Constant.VIRTUAL_HEIGHT;
import static io.asirum.Constant.VIRTUAL_WIDTH;

public class CameraHelper {
    public static void toOrtho(OrthographicCamera camera){
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
    }

    public static FitViewport fitViewport(OrthographicCamera camera){
        return new FitViewport(VIRTUAL_WIDTH,VIRTUAL_HEIGHT,camera);
    }

    /**
     * resize viewport untuk method viewport
     */
    public static void resizer(int width,int height){
        ApplicationContext.getInstance().getViewport().update(width,height,true);
    }


    /**
     * Menyesuaikan kamera dan viewport untuk skala dunia Box2D (meter).
     */
    // TODO : setel ulang kamera ketika sudah tidak menggunakan box2d
    public static void cameraAndViewportForBox2d(ApplicationContext context) {
        Log.debug("CameraHelper", ">>> konfigurasi kamera untuk box2d");

        float virtualWidth = VIRTUAL_WIDTH / Constant.UNIT_SCALE;
        float virtualHeight = VIRTUAL_HEIGHT /Constant.UNIT_SCALE;

        context
            .getCamera()
            .setToOrtho(false, virtualWidth, virtualHeight);
        context
            .getViewport()
            .setWorldSize(virtualWidth, virtualHeight);
    }

    /**
     * digunakan ketika anda sudah tidak menggunakan box2d
     */
    public static void setCameraAndViewportNormal(ApplicationContext context){
        Log.debug("CameraHelper", ">>> mengembalikan camera ke settingan awal");

        toOrtho(context.getCamera());
        fitViewport(context.getCamera());
    }
}
