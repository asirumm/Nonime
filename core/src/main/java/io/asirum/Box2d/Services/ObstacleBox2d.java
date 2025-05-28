package io.asirum.Box2d.Services;

import com.badlogic.gdx.physics.box2d.World;
import io.asirum.Entity.Obstacle.Stalactite;

public class ObstacleBox2d {
    private Stalactite stalactite;

    public ObstacleBox2d(World world) {
        stalactite = new Stalactite(world);
    }

    public Stalactite getStalactite() {
        return stalactite;
    }
}
