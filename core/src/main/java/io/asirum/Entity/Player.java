package io.asirum.Entity;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import io.asirum.Box2d.*;
import io.asirum.TmxMap.TmxHelper;

public class Player extends BaseBox2d {

    public Player(World world) {
        super(world);
    }

    @Override
    public void build(MapObject object) {
        Rectangle rect = TmxHelper.convertRectangleMapObject(object);

        Vector2 position = Box2dHelper.positionBox2d(rect);
        Vector2 size     = Box2dHelper.sizeBox2d(rect);

        BodyBuilder bodyBuilder = new BodyBuilder()
            .type(BodyDef.BodyType.DynamicBody)
            .fixRotation(true)
            .gravityScale(3f)
            .position(position.x, position.y);

        body = world.createBody(bodyBuilder.build());

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x,size.y);

        FixtureBuilder fixtureBuilder = new FixtureBuilder()
            .shape(shape)
            .density(1f)
            .friction(0.5f)
            .filter(FixtureFilter.filterPlayer());

        Fixture fixture = body.createFixture(fixtureBuilder.build());

        fixture.setUserData("player");
        body.setUserData(this);

        shape.dispose();
    }
}
