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

// TODO KITA PERLU REFACTOR KELAS INI
/**
 * Handles collision detection and physics interactions between game objects in the Box2D world.
 * This class manages various types of contacts including player-platform collisions,
 * key collection, portal interactions, obstacle collisions, and checkpoint detection.
 */
public class PlayerContactListener implements ContactListener {
    private final GamePlayManager playManager;

    /**
     * Constructs a PlayerContactListener with the specified GamePlayManager.
     * @param playManager The game play manager that handles game state and logic
     */
    public PlayerContactListener(GamePlayManager playManager) {
        this.playManager = playManager;
    }

    @Override
    public void beginContact(Contact contact) {
        footOnPlatformSensor(contact, true);
        playerCollectKey(contact);
        playerKnockThePortal(contact);
        playerCollisionWithObstacle(contact);
        playerCollisionWithCheckpoint(contact);
    }

    @Override
    public void endContact(Contact contact) {
        footOnPlatformSensor(contact, false);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        playerCollisionWithOneWayPlatform(contact);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Implementation not required for this game
    }

    /**
     * Handles collision detection between player's foot sensor and platforms.
     * Updates the player's ground contact state.
     *
     * @param contact The contact object containing collision information
     * @param setOnGround Boolean indicating whether the player is on ground
     */
    private void footOnPlatformSensor(Contact contact, boolean setOnGround) {
        // Handle regular platform collision
        if (ContactListenerHelper.isFootSensor(contact.getFixtureA()) &&
            ContactListenerHelper.isPlatform(contact.getFixtureB())) {
            Player player = (Player) contact.getFixtureA().getBody().getUserData();
            player.setOnGround(setOnGround);
            return;
        }

        if (ContactListenerHelper.isFootSensor(contact.getFixtureB()) &&
            ContactListenerHelper.isPlatform(contact.getFixtureA())) {
            Player player = (Player) contact.getFixtureB().getBody().getUserData();
            player.setOnGround(setOnGround);
            return;
        }

        // Handle one-way platform collision
        if (ContactListenerHelper.isFootSensor(contact.getFixtureA()) &&
            ContactListenerHelper.isOneWayPlatform(contact.getFixtureB())) {
            handleOneWayPlatformFootSensor(
                contact.getFixtureA().getBody(),
                contact.getFixtureB().getBody(),
                setOnGround
            );
            return;
        }

        if (ContactListenerHelper.isFootSensor(contact.getFixtureB()) &&
            ContactListenerHelper.isOneWayPlatform(contact.getFixtureA())) {
            handleOneWayPlatformFootSensor(
                contact.getFixtureB().getBody(),
                contact.getFixtureA().getBody(),
                setOnGround
            );
        }
    }

    /**
     * Helper method to handle foot sensor collision with one-way platform
     * Only sets player as grounded if they are above the platform
     */
    private void handleOneWayPlatformFootSensor(Body playerBody, Body platformBody, boolean setOnGround) {
        Player player = (Player) playerBody.getUserData();
        OneWayPlatform platform = (OneWayPlatform) platformBody.getUserData();

        Vector2 playerPos = playerBody.getPosition();
        Vector2 platformPos = platformBody.getPosition();

        float playerBottom = playerPos.y - (player.getPlayerHeight() / 2f);
        float platformTop = platformPos.y + (platform.getHeight() / 2f);

        // Strict positioning check without margin
        if (playerBottom >= platformTop) {
            // Only allow grounding if player is moving downward or already on ground
            float playerVerticalVelocity = playerBody.getLinearVelocity().y;
            if (playerVerticalVelocity <= 0 || player.isOnGround()) {
                player.setOnGround(setOnGround);
            }
        } else {
            player.setOnGround(false);
        }
    }
    /**
     * Handles key collection by the player.
     * Updates player state and removes collected keys from the game world.
     *
     * @param contact The contact object containing collision information
     */
    private void playerCollectKey(Contact contact) {
        if (ContactListenerHelper.isPlayerBody(contact.getFixtureA()) &&
            ContactListenerHelper.isKeySensor(contact.getFixtureB())) {
            handleKeyCollection(
                (Player) contact.getFixtureA().getBody().getUserData(),
                (Key) contact.getFixtureB().getBody().getUserData()
            );
            return;
        }

        if (ContactListenerHelper.isPlayerBody(contact.getFixtureB()) &&
            ContactListenerHelper.isKeySensor(contact.getFixtureA())) {
            handleKeyCollection(
                (Player) contact.getFixtureB().getBody().getUserData(),
                (Key) contact.getFixtureA().getBody().getUserData()
            );
        }
    }

    /**
     * Helper method to process key collection logic
     */
    private void handleKeyCollection(Player player, Key key) {
        Log.debug(getClass().getName(), "key berhasil di ambil");
        player.setBringKey(true);
        key.setCollected(true);
        ApplicationContext
            .getInstance()
            .getBox2dObjectDestroyer()
            .appendToDestroy(key.getBody());
    }

    /**
     * Handles player collision with the portal.
     * Triggers level completion logic when player reaches the portal.
     *
     * @param contact The contact object containing collision information
     */
    private void playerKnockThePortal(Contact contact) {
        if ((ContactListenerHelper.isPlayerBody(contact.getFixtureA()) &&
            ContactListenerHelper.isPortalSensor(contact.getFixtureB())) ||
            (ContactListenerHelper.isPlayerBody(contact.getFixtureB()) &&
                ContactListenerHelper.isPortalSensor(contact.getFixtureA()))) {
            Log.debug(getClass().getName(), "player ke portal");
            this.playManager.playerFinishLogic();
        }
    }

    /**
     * Handles player collision with obstacles.
     * Triggers player respawn and decreases player lives.
     *
     * @param contact The contact object containing collision information
     */
    private void playerCollisionWithObstacle(Contact contact) {
        Player player = null;

        if (ContactListenerHelper.isPlayerBody(contact.getFixtureA()) &&
            ContactListenerHelper.isObstacle(contact.getFixtureB())) {
            player = (Player) contact.getFixtureA().getBody().getUserData();
        } else if (ContactListenerHelper.isPlayerBody(contact.getFixtureB()) &&
            ContactListenerHelper.isObstacle(contact.getFixtureA())) {
            player = (Player) contact.getFixtureB().getBody().getUserData();
        }

        if (player != null) {
            Log.debug(getClass().getName(), "player kena obstacle");
            player.playerNeedRespawnActive();
            player.decreasePlayerLive();
        }
    }

    /**
     * Handles player collision with checkpoints.
     * Updates player's last checkpoint position.
     *
     * @param contact The contact object containing collision information
     */
    private void playerCollisionWithCheckpoint(Contact contact) {
        if (ContactListenerHelper.isPlayerBody(contact.getFixtureA()) &&
            ContactListenerHelper.isCheckpoint(contact.getFixtureB())) {
            updateCheckpoint(
                (Player) contact.getFixtureA().getBody().getUserData(),
                (Checkpoint) contact.getFixtureB().getBody().getUserData()
            );
            return;
        }

        if (ContactListenerHelper.isPlayerBody(contact.getFixtureB()) &&
            ContactListenerHelper.isCheckpoint(contact.getFixtureA())) {
            updateCheckpoint(
                (Player) contact.getFixtureB().getBody().getUserData(),
                (Checkpoint) contact.getFixtureA().getBody().getUserData()
            );
        }
    }

    /**
     * Helper method to update checkpoint position
     */
    private void updateCheckpoint(Player player, Checkpoint checkpoint) {
        Log.debug(getClass().getName(), "player kena checkpoint");
        player.setLastCheckpoint(checkpoint.getPosition());
    }

    /**
     * Handles player collision with one-way platforms.
     * Enables/disables collision based on player's position relative to the platform.
     *
     * @param contact The contact object containing collision information
     */
    private void playerCollisionWithOneWayPlatform(Contact contact) {
        Body playerBody;
        Body platformBody;
        OneWayPlatform platform;
        Player player;

        if (ContactListenerHelper.isPlayerBody(contact.getFixtureA()) &&
            ContactListenerHelper.isOneWayPlatform(contact.getFixtureB())) {
            playerBody = contact.getFixtureA().getBody();
            platformBody = contact.getFixtureB().getBody();
            platform = (OneWayPlatform) platformBody.getUserData();
            player = (Player) playerBody.getUserData();
        } else if (ContactListenerHelper.isPlayerBody(contact.getFixtureB()) &&
            ContactListenerHelper.isOneWayPlatform(contact.getFixtureA())) {
            playerBody = contact.getFixtureB().getBody();
            platformBody = contact.getFixtureA().getBody();
            platform = (OneWayPlatform) platformBody.getUserData();
            player = (Player) playerBody.getUserData();
        } else {
            return;
        }

        Vector2 playerPos = playerBody.getPosition();
        Vector2 platformPos = platformBody.getPosition();

        float playerBottom = playerPos.y - (player.getPlayerHeight() / 2f);
        float platformTop = platformPos.y + (platform.getHeight() / 2f);

        contact.setEnabled(playerBottom >= platformTop - 0.01f);
    }
}
