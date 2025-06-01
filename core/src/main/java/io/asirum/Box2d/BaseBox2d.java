package io.asirum.Box2d;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import io.asirum.Constant;

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

    public World getWorld(){
        return world;
    }


    /**
     */
    public Vector2 positionBox2d(Rectangle rect){
        float x = (rect.x + rect.width / 2f) / Constant.UNIT_SCALE;
        float y = (rect.y + rect.height / 2f) / Constant.UNIT_SCALE;

        return new Vector2(x,y);
    }

    public Vector2 sizeBox2d(Rectangle rect){
        float halfWidth = rect.width / 2f / Constant.UNIT_SCALE;
        float halfHeight = rect.height / 2f / Constant.UNIT_SCALE;

        return new Vector2(halfWidth,halfHeight);
    }
}
