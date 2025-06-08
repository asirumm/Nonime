package io.asirum.Box2d.Collision;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.asirum.Entity.Items.Checkpoint;
import io.asirum.Entity.Items.Key;
import io.asirum.Entity.Platform.OneWayPlatform;
import io.asirum.Entity.Player.Player;
import io.asirum.GameLogic.GamePlayManager;
import io.asirum.Service.ApplicationContext;
import io.asirum.Service.Log;

import java.util.concurrent.ThreadPoolExecutor;

// TODO KITA PERLU REFACTOR KELAS INI
public class PlayerContactListener implements ContactListener {
    private GamePlayManager playManager;

    public PlayerContactListener(GamePlayManager playManager) {
        this.playManager = playManager;
    }

    @Override // com.badlogic.gdx.physics.box2d.ContactListener
    public void beginContact(Contact contact) {
        footOnPlatformSensor(contact, true);
        playerCollectKey(contact);
        playerKnockThePortal(contact);
        playerCollisionWithObstacle(contact);
        playerCollisionWithCheckpoint(contact);
    }

    @Override // com.badlogic.gdx.physics.box2d.ContactListener
    public void endContact(Contact contact) {
        footOnPlatformSensor(contact, false);
    }

    @Override // com.badlogic.gdx.physics.box2d.ContactListener
    public void preSolve(Contact contact, Manifold oldManifold) {
        playerCollisionWithOneWayPlatform(contact);
    }

    @Override // com.badlogic.gdx.physics.box2d.ContactListener
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    private void footOnPlatformSensor(Contact contact, boolean setOnGround) {
        if (ContactListenerHelper.isFootSensor(contact.getFixtureA()) && ContactListenerHelper.isPlatform(contact.getFixtureB())) {
            Player player = (Player) contact.getFixtureA().getBody().getUserData();
            player.setOnGround(setOnGround);
            return;
        }
        if (ContactListenerHelper.isFootSensor(contact.getFixtureB()) && ContactListenerHelper.isPlatform(contact.getFixtureA())) {
            Player player2 = (Player) contact.getFixtureB().getBody().getUserData();
            player2.setOnGround(setOnGround);

        } else if (ContactListenerHelper.isFootSensor(contact.getFixtureA()) && ContactListenerHelper.isOneWayPlatform(contact.getFixtureB())) {
            Player player3 = (Player) contact.getFixtureA().getBody().getUserData();

            player3.setOnGround(setOnGround);
        } else if (ContactListenerHelper.isFootSensor(contact.getFixtureB()) && ContactListenerHelper.isOneWayPlatform(contact.getFixtureA())) {
            Player player4 = (Player) contact.getFixtureB().getBody().getUserData();
            player4.setOnGround(setOnGround);
        }
    }

    private void playerCollectKey(Contact contact) {
        if (ContactListenerHelper.isPlayerBody(contact.getFixtureA()) && ContactListenerHelper.isKeySensor(contact.getFixtureB())) {
            Log.debug(getClass().getName(), "key berhasil di ambil");

            Key key = (Key) contact.getFixtureB().getBody().getUserData();
            Player player = (Player) contact.getFixtureA().getBody().getUserData();

            player.setBringKey(true);
            // agar draw dihentikan
            key.setCollected(true);

            // agar kunci di hapus box2dnya
            ApplicationContext
                .getInstance()
                .getBox2dObjectDestroyer()
                .appendToDestroy(key.getBody());
            return;
        }
        if (ContactListenerHelper.isPlayerBody(contact.getFixtureB()) && ContactListenerHelper.isKeySensor(contact.getFixtureA())) {
            Log.debug(getClass().getName(), "key berhasil di ambil");

            Key key2 = (Key) contact.getFixtureA().getBody().getUserData();
            Player player2 = (Player) contact.getFixtureB().getBody().getUserData();

            player2.setBringKey(true);
            key2.setCollected(true);

            ApplicationContext
                .getInstance()
                .getBox2dObjectDestroyer()
                .appendToDestroy(key2.getBody());

        }
    }

    private void playerKnockThePortal(Contact contact) {
        if (ContactListenerHelper.isPlayerBody(contact.getFixtureA()) && ContactListenerHelper.isPortalSensor(contact.getFixtureB())) {
            Log.debug(getClass().getName(), "player ke portal");

            this.playManager.playerFinishLogic();

        } else if (ContactListenerHelper.isPlayerBody(contact.getFixtureB()) && ContactListenerHelper.isPortalSensor(contact.getFixtureA())) {
            Log.debug(getClass().getName(), "player ke portal");

            this.playManager.playerFinishLogic();
        }
    }

    private void playerCollisionWithObstacle(Contact contact) {
        if (ContactListenerHelper.isPlayerBody(contact.getFixtureA()) && ContactListenerHelper.isObstacle(contact.getFixtureB())) {
            Log.debug(getClass().getName(), "player kena obstacle");
            Player player = (Player) contact.getFixtureA().getBody().getUserData();
            player.setPlayerNeedRespawn(true);
            player.decreasePlayerLive();
            return;
        }
        if (ContactListenerHelper.isPlayerBody(contact.getFixtureB()) && ContactListenerHelper.isObstacle(contact.getFixtureA())) {
            Log.debug(getClass().getName(), "player kena obstacle");
            Player player2 = (Player) contact.getFixtureB().getBody().getUserData();
            player2.setPlayerNeedRespawn(true);
            player2.decreasePlayerLive();
        }
    }

    private void playerCollisionWithCheckpoint(Contact contact) {
        if (ContactListenerHelper.isPlayerBody(contact.getFixtureA()) && ContactListenerHelper.isCheckpoint(contact.getFixtureB())) {
            Log.debug(getClass().getName(), "player kena checkpoint");
            Checkpoint checkpoint = (Checkpoint) contact.getFixtureB().getBody().getUserData();
            Player player = (Player) contact.getFixtureA().getBody().getUserData();
            player.setLastCheckpoint(checkpoint.getPosition());
            return;
        }
        if (ContactListenerHelper.isPlayerBody(contact.getFixtureB()) && ContactListenerHelper.isCheckpoint(contact.getFixtureA())) {
            Log.debug(getClass().getName(), "player kena obstacle");
            Checkpoint checkpoint2 = (Checkpoint) contact.getFixtureA().getBody().getUserData();
            Player player2 = (Player) contact.getFixtureB().getBody().getUserData();
            player2.setLastCheckpoint(checkpoint2.getPosition());
        }
    }

    private void playerCollisionWithOneWayPlatform(Contact contact) {
        if (ContactListenerHelper.isPlayerBody(contact.getFixtureA()) && ContactListenerHelper.isOneWayPlatform(contact.getFixtureB())) {
            Body player = contact.getFixtureA().getBody();
            Body platform = contact.getFixtureB().getBody();

            OneWayPlatform platformHeight = (OneWayPlatform) contact.getFixtureB().getBody().getUserData();
            Player playerHeight = (Player) contact.getFixtureA().getBody().getUserData();

            Vector2 playerPos = player.getPosition();
            Vector2 platformPos = platform.getPosition();

            float playerBottom = playerPos.y - (playerHeight.getPlayerHeight()/2f);
            float platformTop = platformPos.y + (platformHeight.getHeight() /2f);

            if (playerBottom >=  platformTop- 0.01f ) {
                contact.setEnabled(true);
                return;
            } else {
                contact.setEnabled(false);
                return;
            }
        }
        if (ContactListenerHelper.isPlayerBody(contact.getFixtureB()) && ContactListenerHelper.isOneWayPlatform(contact.getFixtureA())) {
            Body player2 = contact.getFixtureB().getBody();
            Body platform2 = contact.getFixtureA().getBody();
            OneWayPlatform platformHeight2 = (OneWayPlatform) contact.getFixtureA().getBody().getUserData();
            Player playerHeight2 = (Player) contact.getFixtureB().getBody().getUserData();
            Vector2 playerPos2 = player2.getPosition();
            Vector2 platformPos2 = platform2.getPosition();
            float playerBottom2 = playerPos2.y - (playerHeight2.getPlayerHeight()/2f);
            float platformTop2 = platformPos2.y + (platformHeight2.getHeight() /2f);
            if (playerBottom2 >=  platformTop2-0.1f) {
                contact.setEnabled(true);
            } else {
                contact.setEnabled(false);
            }
        }
    }
}
