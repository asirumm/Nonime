package io.asirum.Entity.Platform;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import io.asirum.Box2d.BaseBox2d;

public abstract class BasePlatform extends BaseBox2d {
    public BasePlatform(World world) {
        super(world);
    }

    /**
     * We doesnt use this at this time
     */
    @Override
    public void build(MapObject object) {

    }

    public abstract void build(TiledMap tiledMap);
}
