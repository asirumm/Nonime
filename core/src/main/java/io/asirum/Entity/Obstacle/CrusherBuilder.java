package io.asirum.Entity.Obstacle;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import io.asirum.Box2d.*;
import io.asirum.TmxMap.TmxHelper;

import java.util.ArrayList;

public class CrusherBuilder extends BaseBox2d {
    ArrayList<Crusher> crushers=new ArrayList<>();

    public CrusherBuilder(World world) {
        super(world);
    }

    @Override
    public void build(MapObject object) {
        String pathProps = object.getProperties().get("path",String.class);

        Rectangle rect = TmxHelper.convertRectangleMapObject(object);

        Vector2 position = positionBox2d(rect);
        Vector2 size     = sizeBox2d(rect);

        BodyBuilder bodyBuilder = new BodyBuilder()
            .type(BodyDef.BodyType.KinematicBody)
            .fixRotation(true)
            .position(position.x, position.y);

        body = world.createBody(bodyBuilder.build());

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x,size.y);

        FixtureBuilder fixtureBuilder = new FixtureBuilder()
            .shape(shape)
            .filter(FixtureFilter.filterPlatform());

        Fixture fixture = body.createFixture(fixtureBuilder.build());

        fixture.setFilterData(FixtureFilter.filterObstacle());
        fixture.setUserData(Box2dVars.OBSTACLE_NAME);

        Crusher w =new Crusher(body);
        w.setSize(size);
        w.setPathProps(pathProps);

        crushers.add(w);

        shape.dispose();
    }

    public ArrayList<Crusher> getCrushers() {
        return crushers;
    }
}
