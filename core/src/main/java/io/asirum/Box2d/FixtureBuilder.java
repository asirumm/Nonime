package io.asirum.Box2d;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;

public class FixtureBuilder {
    private FixtureDef fixtureDef;
    private Shape shape;

    public FixtureBuilder() {
        fixtureDef = new FixtureDef();
    }

    public FixtureBuilder shape(Shape shape) {
        this.shape = shape;
        fixtureDef.shape = shape;
        return this;
    }

    public FixtureBuilder density(float density) {
        fixtureDef.density = density;
        return this;
    }

    public FixtureBuilder friction(float friction) {
        fixtureDef.friction = friction;
        return this;
    }

    public FixtureBuilder restitution(float restitution) {
        fixtureDef.restitution = restitution;
        return this;
    }

    public FixtureBuilder isSensor(boolean isSensor) {
        fixtureDef.isSensor = isSensor;
        return this;
    }

    public FixtureBuilder filter(Filter filter) {
        fixtureDef.filter.set( filter);
        return this;
    }


    public FixtureDef build() {
        return fixtureDef;
    }

    public void dispose() {
        if (shape != null) shape.dispose();
    }
}
