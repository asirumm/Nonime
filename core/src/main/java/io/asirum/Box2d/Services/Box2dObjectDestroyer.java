package io.asirum.Box2d.Services;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Hold instance body yang perlu di destroy dari world
 */
public class Box2dObjectDestroyer {
    private Array<Body> bodyWantToDestroy = new Array<>();


    public Array<Body> getBodiesWantToDestroy() {
        return bodyWantToDestroy;
    }

    public void appendToDestroy(Body body){
        bodyWantToDestroy.add(body);
    }

    public void clear() {
        bodyWantToDestroy.clear();
    }
}
