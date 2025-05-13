package io.asirum.Box2d.Collision;

import com.badlogic.gdx.physics.box2d.*;
import io.asirum.Entity.Player.Player;

import static io.asirum.Box2d.Collision.ContactListenerHelper.isFootSensor;
import static io.asirum.Box2d.Collision.ContactListenerHelper.isPlatform;

public class PlayerContactListener implements ContactListener {


    @Override
    public void beginContact(Contact contact) {
        footOnPlatformSensor(contact,true);
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
}
