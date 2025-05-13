package io.asirum.Box2d;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.asirum.Constant;

public class Box2dHelper {

    public static final String PLATFORM_LAYER = "platform";
    public static final String ENTITIES_LAYER = "objects";
    public static final String PLAYER_NAME = "player";
    public static final String PLAYER_SENSOR_NAME = "foot-sensor";

    public static final String PLAYER_SENSOR_FIXTURE_NAME = "foot";
    public static final String STATIC_PLATFORM_FIXTURE_NAME = "static_platform";
    public static final String PLAYER_FIXTURE_NAME = "player";

    /**
     */
    public static Vector2 positionBox2d(Rectangle rect){
        float x = (rect.x + rect.width / 2f) / Constant.UNIT_SCALE;
        float y = (rect.y + rect.height / 2f) / Constant.UNIT_SCALE;

        return new Vector2(x,y);
    }

    public static Vector2 sizeBox2d(Rectangle rect){
        float halfWidth = rect.width / 2f / Constant.UNIT_SCALE;
        float halfHeight = rect.height / 2f / Constant.UNIT_SCALE;

        return new Vector2(halfWidth,halfHeight);
    }

}
