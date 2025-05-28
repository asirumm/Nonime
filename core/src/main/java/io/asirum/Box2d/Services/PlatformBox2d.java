package io.asirum.Box2d.Services;

import com.badlogic.gdx.physics.box2d.World;
import io.asirum.Entity.Platform.OneWayPlatformBuilder;
import io.asirum.Entity.Platform.StaticPlatform;

public class PlatformBox2d {
    private OneWayPlatformBuilder oneWayPlatformBuilder;
    private StaticPlatform staticPlatform;

    public PlatformBox2d(World world) {
        oneWayPlatformBuilder = new OneWayPlatformBuilder(world);
        staticPlatform = new StaticPlatform(world);
    }

    public OneWayPlatformBuilder getOneWayPlatformBuilder() {
        return oneWayPlatformBuilder;
    }

    public StaticPlatform getStaticPlatform() {
        return staticPlatform;
    }
}
