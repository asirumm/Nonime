package io.asirum.Util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
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

    /**
     * membuat batas kamera untuk melihat, sehingga
     * tidak melihat ruang kosong di ujung map
     */
    public static void boundaryCamera(OrthographicCamera camera, float widthMap, float heightMap) {
        // Hitung batas dengan mempertimbangkan zoom camera
        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        // Batas minimum (kiri dan bawah)
        float minX = effectiveViewportWidth / 2;
        float minY = effectiveViewportHeight / 2;

        // Batas maximum (kanan dan atas)
        float maxX = widthMap - (effectiveViewportWidth / 2);
        float maxY = heightMap - (effectiveViewportHeight / 2);

        // Terapkan batas
        Vector3 position = camera.position;

        // Batas horizontal
        position.x = MathUtils.clamp(position.x, minX, maxX);

        // Batas vertical
        position.y = MathUtils.clamp(position.y, minY, maxY);

        camera.position.set(position);
        camera.update();
    }

    // TODO lerp camera
    public static void lerpCamera(OrthographicCamera camera, Body playerBody){
        Vector2 cameraPos = new Vector2(camera.position.x,camera.position.y);
        Vector2 targetPos = new Vector2(playerBody.getPosition().x,playerBody.getPosition().y);

        cameraPos.lerp(targetPos, Interpolation.sine.apply(0.1f));

        camera.position.set(cameraPos.x, cameraPos.y, 0);
        camera.update();
    }
}
