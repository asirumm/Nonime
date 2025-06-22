package io.asirum.Box2d.Collision;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import io.asirum.Box2d.Box2dVars;
import io.asirum.Entity.Platform.OneWayPlatform;
import io.asirum.Entity.Player.Player;

import java.util.Optional;

public class ContactListenerHelper {

    public static boolean matchTypes(Fixture a, Fixture b, String type1, String type2) {
        String aType = (String) a.getUserData();
        String bType = (String) b.getUserData();

        return (type1.equals(aType) && type2.equals(bType)) ||
            (type2.equals(aType) && type1.equals(bType));
    }

    public static Player getPlayer(Fixture a, Fixture b) {
        if (a.getBody().getUserData() instanceof Player) {
            return (Player) a.getBody().getUserData();
        } else if (b.getBody().getUserData() instanceof Player) {
            return (Player) b.getBody().getUserData();
        }
        return null;
    }

    public static Body getPlayerBody(Fixture a, Fixture b) {
        if (a.getBody().getUserData() instanceof Player) {
            return a.getBody();
        } else if (b.getBody().getUserData() instanceof Player) {
            return b.getBody();
        }
        return null;
    }

    public static Body getPlatformBody(Fixture a, Fixture b) {
        if (a.getBody().getUserData() instanceof OneWayPlatform) {
            return a.getBody();
        } else if (b.getBody().getUserData() instanceof OneWayPlatform) {
            return b.getBody();
        }
        return null;
    }

    public static OneWayPlatform getPlatform(Fixture a, Fixture b) {
        if (a.getBody().getUserData() instanceof OneWayPlatform) {
            return (OneWayPlatform) a.getBody().getUserData();
        } else if (b.getBody().getUserData() instanceof OneWayPlatform) {
            return (OneWayPlatform) b.getBody().getUserData();
        }
        return null;
    }


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
