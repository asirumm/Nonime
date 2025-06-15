package io.asirum.Entity.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import io.asirum.Box2d.*;
import io.asirum.Entity.EntityAnimation.KeyAnimation;
import io.asirum.TmxMap.TmxHelper;


public class Key extends BaseBox2d {
    private boolean collected;
    private Vector2 size;
    private KeyAnimation keyAnimation;

    public Key(World world) {
        super(world);
        keyAnimation = new KeyAnimation();
        keyAnimation.animationInitialization();
    }
    public void drawAnimation(float delta){

        if (!collected) {
            keyAnimation.draw(body,delta);
        }

        if (collected) return;
    }
    @Override
    public void build(MapObject object) {
        Rectangle rect = TmxHelper.convertRectangleMapObject(object);

        Vector2 position = positionBox2d(rect);
        size     = sizeBox2d(rect);

        BodyBuilder bodyBuilder = new BodyBuilder()
            .fixRotation(true)
            .position(position.x, position.y);

        body = world.createBody(bodyBuilder.build());

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x,size.y);

        FixtureBuilder fixtureBuilder = new FixtureBuilder()
            .shape(shape)
            .filter(FixtureFilter.filterKey())
            .isSensor(true);

        Fixture fixture = body.createFixture(fixtureBuilder.build());

        fixture.setUserData(Box2dVars.KEY_FIXTURE);
        body.setUserData(this);

        shape.dispose();
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
}
