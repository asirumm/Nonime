package io.asirum.Util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static io.asirum.Constant.VIRTUAL_HEIGHT;
import static io.asirum.Constant.VIRTUAL_WIDTH;

public class CameraHelper {
    public static void toOrtho(OrthographicCamera camera){
        camera.setToOrtho(false, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
    }

    public static FitViewport fitViewport(OrthographicCamera camera){
        return new FitViewport(VIRTUAL_WIDTH,VIRTUAL_HEIGHT,camera);
    }
}
