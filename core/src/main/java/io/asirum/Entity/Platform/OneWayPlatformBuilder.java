package io.asirum.Entity.Platform;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import io.asirum.Box2d.*;
import io.asirum.TmxMap.TmxHelper;


public class OneWayPlatformBuilder extends BaseBox2d {

    public OneWayPlatformBuilder(World world) {
        super(world);
    }

    @Override
    public void build(MapObject object) {
        Rectangle rect = TmxHelper.convertRectangleMapObject(object);

        Vector2 position = positionBox2d(rect);
        Vector2 size     = sizeBox2d(rect);

        BodyBuilder bodyBuilder = new BodyBuilder()
            .type(BodyDef.BodyType.StaticBody)
            .fixRotation(true)
            .position(position.x, position.y);

        body = world.createBody(bodyBuilder.build());

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x,size.y);

        FixtureBuilder fixtureBuilder = new FixtureBuilder()
            .shape(shape)
            .filter(FixtureFilter.filterPlatform());

        Fixture fixture = body.createFixture(fixtureBuilder.build());

        fixture.setFilterData(FixtureFilter.filterPlatform());
        fixture.setUserData(Box2dVars.ONE_WAY_PLATFORM_FIXTURE);

        OneWayPlatform w =new OneWayPlatform();
        w.setHeight(size.y);
        w.setWidth(size.x);
        w.setBody(body);

        body.setUserData(w);


        shape.dispose();
    }

}
