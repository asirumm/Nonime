package io.asirum.Entity.Items;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import io.asirum.Box2d.*;
import io.asirum.TmxMap.TmxHelper;

// TODO Draw key
public class Key extends BaseBox2d {
    private boolean collected;
    private Vector2 size;

    public Key(World world) {
        super(world);
    }

    @Override
    public void build(MapObject object) {
        Rectangle rect = TmxHelper.convertRectangleMapObject(object);

        Vector2 position = Box2dHelper.positionBox2d(rect);
        size     = Box2dHelper.sizeBox2d(rect);

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

        fixture.setUserData(Box2dHelper.KEY_FIXTURE_NAME);
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
