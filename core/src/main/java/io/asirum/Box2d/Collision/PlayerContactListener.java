package io.asirum.Box2d.Collision;

import com.badlogic.gdx.physics.box2d.*;
import io.asirum.Entity.Items.Key;
import io.asirum.Entity.Player.Player;
import io.asirum.Service.Log;

import static io.asirum.Box2d.Collision.ContactListenerHelper.*;

public class PlayerContactListener implements ContactListener {


    @Override
    public void beginContact(Contact contact) {
        footOnPlatformSensor(contact,true);
        playerCollectKey(contact);
    }

    @Override
    public void endContact(Contact contact) {
        footOnPlatformSensor(contact,false);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private void footOnPlatformSensor(Contact contact,boolean setOnGround){
        if (isFootSensor(contact.getFixtureA()) && isPlatform(contact.getFixtureB())) {

            Player player =
                (Player) contact
                    .getFixtureA()
                    .getBody()
                    .getUserData();

            player.setOnGround(setOnGround);

        } else if (isFootSensor(contact.getFixtureB()) && isPlatform(contact.getFixtureA())) {
            Player player =
                (Player) contact
                    .getFixtureB()
                    .getBody()
                    .getUserData();

            player.setOnGround(setOnGround);
        }
    }

    private void playerCollectKey(Contact contact){
        if (isPlayerBody(contact.getFixtureA()) && isKeySensor(contact.getFixtureB())) {

            Log.debug(getClass().getName(),"key collected");

            Key key =
                (Key) contact
                    .getFixtureB()
                    .getBody()
                    .getUserData();

            key.setCollected(true);
            key.appendToDestroy(key.getBody());

        } else if (isPlayerBody(contact.getFixtureB()) && isKeySensor(contact.getFixtureA())) {

            Log.debug(getClass().getName(),"key collected");

            Key key =
                (Key) contact
                    .getFixtureB()
                    .getBody()
                    .getUserData();

            key.setCollected(true);
            key.appendToDestroy(key.getBody());

        }

    }
}
