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
    public static void toOrtho(OrthographicCamera camera) {
        camera.setToOrtho(false, Constant.VIRTUAL_WIDTH, Constant.VIRTUAL_HEIGHT);
    }

    public static FitViewport fitViewport(OrthographicCamera camera) {
        return new FitViewport(Constant.VIRTUAL_WIDTH, Constant.VIRTUAL_HEIGHT, camera);
    }

    public static void resizer(int width, int height) {
        ApplicationContext.getInstance().getViewport().update(width, height, true);
    }

    public static void cameraAndViewportForBox2d(ApplicationContext context) {
        Log.debug("CameraHelper", ">>> konfigurasi kamera untuk box2d");
        float virtualWidth = Constant.VIRTUAL_WIDTH / Constant.UNIT_SCALE;
        float virtualHeight = Constant.VIRTUAL_HEIGHT / Constant.UNIT_SCALE;
        context.getCamera().setToOrtho(false, virtualWidth, virtualHeight);
        context.getViewport().setWorldSize(virtualWidth, virtualHeight);
    }

    public static void setCameraAndViewportNormal(ApplicationContext context) {
        Log.debug("CameraHelper", ">>> mengembalikan camera ke settingan awal");
        toOrtho(context.getCamera());
        fitViewport(context.getCamera());
    }

    /**
     * belum sempat di refactor
     */
    public static void boundaryCamera(OrthographicCamera camera, float widthMap, float heightMap) {
        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;
        float minX = effectiveViewportWidth / 2.0f;
        float minY = effectiveViewportHeight / 2.0f;
        float maxX = widthMap - (effectiveViewportWidth / 2.0f);
        float maxY = heightMap - (effectiveViewportHeight / 2.0f);
        Vector3 position = camera.position;
        position.x = MathUtils.clamp(position.x, minX, maxX);
        position.y = MathUtils.clamp(position.y, minY, maxY);
        camera.position.set(position);
        camera.update();
    }

    // kamera mengikuti body tetapi smooth
    public static void lerpCamera(OrthographicCamera camera, Body playerBody) {
        Vector2 cameraPos = new Vector2(camera.position.x, camera.position.y);
        Vector2 targetPos = new Vector2(playerBody.getPosition().x, playerBody.getPosition().y);
        cameraPos.lerp(targetPos, Interpolation.sine.apply(0.1f));
        camera.position.set(cameraPos.x, cameraPos.y, 0.0f);
        camera.update();
    }
}
