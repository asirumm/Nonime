package io.asirum.Box2d.Collision;

import com.badlogic.gdx.physics.box2d.Fixture;
import io.asirum.Box2d.Box2dVars;

public class ContactListenerHelper {
    public static boolean isFootSensor(Fixture fixture){
         return fixture.getUserData().equals(Box2dVars.PLAYER_SENSOR_FIXTURE);
    }

    public static boolean isPlatform(Fixture fixture){
        return fixture.getUserData().equals(Box2dVars.STATIC_PLATFORM_FIXTURE);
    }

    public static boolean isOneWayPlatform(Fixture fixture){
        return fixture.getUserData().equals(Box2dVars.ONE_WAY_PLATFORM_FIXTURE);
    }

    public static boolean isKeySensor(Fixture fixture){
        return fixture.getUserData().equals(Box2dVars.KEY_FIXTURE);
    }

    public static boolean isPlayerBody(Fixture fixture){
        return fixture.getUserData().equals(Box2dVars.PLAYER_FIXTURE);
    }

    public static boolean isPortalSensor(Fixture fixture){
        return fixture.getUserData().equals(Box2dVars.PORTAL_FIXTURE);
    }

    public static boolean isObstacle(Fixture fixture){
        return fixture.getUserData().equals(Box2dVars.OBSTACLE_NAME);
    }

    public static boolean isCheckpoint(Fixture fixture){
        return fixture.getUserData().equals(Box2dVars.CHECKPOINT_OBJECT);
    }
}
