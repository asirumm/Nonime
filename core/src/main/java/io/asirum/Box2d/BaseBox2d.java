package io.asirum.Box2d;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class BaseBox2d {
    protected World world;
    protected Body body;

    public BaseBox2d(World world) {
        this.world = world;
    }

    public Body getBody() {
        return body;
    }

    public abstract void build(MapObject object);
}
