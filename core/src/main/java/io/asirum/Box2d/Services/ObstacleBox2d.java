package io.asirum.Box2d.Services;

import com.badlogic.gdx.physics.box2d.World;
import io.asirum.Entity.Obstacle.CrusherBuilder;
import io.asirum.Entity.Obstacle.Stalactite;

/**
 * saya menyatukan semua entitas obstacle disini
 */
public class ObstacleBox2d {
    private Stalactite stalactite;
    private CrusherBuilder crusherBuilder;

    public ObstacleBox2d(World world) {
        stalactite = new Stalactite(world);
        crusherBuilder = new CrusherBuilder(world);
    }

    public Stalactite getStalactite() {
        return stalactite;
    }

    public CrusherBuilder getCrusherBuilder() {
        return crusherBuilder;
    }
}
