package io.asirum.Box2d;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public abstract class BaseBox2d {
    protected World world;
    protected Body body;
    protected Array<Body> toDestroy = new Array<>();

    public BaseBox2d(World world) {
        this.world = world;
    }

    public Body getBody() {
        return body;
    }

    public Array<Body> getToDestroy() {
        return toDestroy;
    }

    public void appendToDestroy(Body body){
        toDestroy.add(body);
    }

    public abstract void build(MapObject object);
}
