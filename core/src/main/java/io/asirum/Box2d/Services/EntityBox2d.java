package io.asirum.Box2d.Services;

import com.badlogic.gdx.physics.box2d.World;
import io.asirum.Entity.Items.CheckpointBuilder;
import io.asirum.Entity.Items.Key;
import io.asirum.Entity.Items.Portal;
import io.asirum.Entity.Player.Player;
import io.asirum.Entity.Player.PlayerSensor;

public class EntityBox2d {
    private Player player;
    private PlayerSensor playerSensor;
    private Portal portal;
    private Key key;
    private CheckpointBuilder checkpointBuilder;

    public EntityBox2d(World world){
        key   = new Key(world);
        portal = new Portal(world);
        player = new Player(world);
        playerSensor = new PlayerSensor(world,player);
        checkpointBuilder = new CheckpointBuilder(world);
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerSensor getPlayerSensor() {
        return playerSensor;
    }

    public Portal getPortal() {
        return portal;
    }

    public Key getKey() {
        return key;
    }

    public CheckpointBuilder getCheckpointBuilder() {
        return checkpointBuilder;
    }
}
