package io.asirum.Box2d.Collision;

import com.badlogic.gdx.physics.box2d.*;
import io.asirum.Entity.Items.Checkpoint;
import io.asirum.Entity.Items.Key;
import io.asirum.Entity.Items.Portal;
import io.asirum.Entity.Player.Player;
import io.asirum.GameLogic.GamePlayManager;
import io.asirum.Service.Log;

import static io.asirum.Box2d.Collision.ContactListenerHelper.*;

public class PlayerContactListener implements ContactListener {

    private GamePlayManager playManager;

    public PlayerContactListener(GamePlayManager playManager) {
        this.playManager= playManager;
    }

    @Override
    public void beginContact(Contact contact) {
        footOnPlatformSensor(contact,true);
        playerCollectKey(contact);
        playerKnockThePortal(contact);
        playerCollisionWithObstacle(contact);
        playerCollisionWithCheckpoint(contact);
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

    private void playerKnockThePortal(Contact contact) {
        if (isPlayerBody(contact.getFixtureA()) && isPortalSensor(contact.getFixtureB())) {

            Log.debug(getClass().getName(),"player ke portal");

            Portal portal = (Portal)
                contact
                    .getFixtureB()
                    .getBody()
                    .getUserData();

            playManager.isPlayerCanFinish(portal);

        } else if (isPlayerBody(contact.getFixtureB()) && isPortalSensor(contact.getFixtureA())) {

            Log.debug(getClass().getName(),"player ke portal");


            Portal portal = (Portal)
                contact
                    .getFixtureA()
                    .getBody()
                    .getUserData();

            playManager.isPlayerCanFinish(portal);
        }
    }

    private void playerCollisionWithObstacle(Contact contact) {
        if (isPlayerBody(contact.getFixtureA()) && isObstacle(contact.getFixtureB())) {

            Log.debug(getClass().getName(),"player kena obstacle");


            Player player =
                (Player) contact
                    .getFixtureA()
                    .getBody()
                    .getUserData();

            player.setPlayerNeedRespawn(true);
            player.decreasePlayerLive();

        } else if (isPlayerBody(contact.getFixtureB()) && isObstacle(contact.getFixtureA())) {

            Log.debug(getClass().getName(),"player kena obstacle");


            Player player =
                (Player) contact
                    .getFixtureB()
                    .getBody()
                    .getUserData();

            player.setPlayerNeedRespawn(true);
            player.decreasePlayerLive();

        }
    }

    private void playerCollisionWithCheckpoint(Contact contact) {
        if (isPlayerBody(contact.getFixtureA()) && isCheckpoint(contact.getFixtureB())) {

            Log.debug(getClass().getName(),"player kena checkpoint");

            Checkpoint checkpoint = (Checkpoint)
                contact
                    .getFixtureB()
                    .getBody()
                    .getUserData();

            Player player =
                (Player) contact
                    .getFixtureA()
                    .getBody()
                    .getUserData();

            player.setLastCheckpoint(checkpoint.getPosition());


        } else if (isPlayerBody(contact.getFixtureB()) && isCheckpoint(contact.getFixtureA())) {

            Log.debug(getClass().getName(),"player kena obstacle");

            Checkpoint checkpoint = (Checkpoint)
                contact
                    .getFixtureA()
                    .getBody()
                    .getUserData();

            Player player =
                (Player) contact
                    .getFixtureB()
                    .getBody()
                    .getUserData();

            player.setLastCheckpoint(checkpoint.getPosition());
        }
    }
}
