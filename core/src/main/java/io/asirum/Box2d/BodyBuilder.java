package io.asirum.Box2d;

import com.badlogic.gdx.physics.box2d.BodyDef;

public class BodyBuilder {
    private BodyDef bodyDef;

    public BodyBuilder() {
        bodyDef = new BodyDef();
    }

    public BodyBuilder type(BodyDef.BodyType type) {
        bodyDef.type = type;
        return this;
    }

    public BodyBuilder gravityScale(float val){
        bodyDef.gravityScale = val;
        return this;
    }

    public BodyBuilder fixRotation(boolean rot){
        bodyDef.fixedRotation = rot;
        return this;
    }

    public BodyBuilder position(float x, float y) {
        bodyDef.position.set(x, y);
        return this;
    }

    public BodyDef build() {
        return bodyDef;
    }
}
