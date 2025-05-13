package io.asirum.Box2d.Collision;

import com.badlogic.gdx.physics.box2d.Fixture;
import io.asirum.Box2d.Box2dHelper;

public class ContactListenerHelper {
    public static boolean isFootSensor(Fixture fixture){
         return fixture.getUserData().equals(Box2dHelper.PLAYER_SENSOR_FIXTURE_NAME);
    }

    public static boolean isPlatform(Fixture fixture){
        return fixture.getUserData().equals(Box2dHelper.STATIC_PLATFORM_FIXTURE_NAME);
    }
}
