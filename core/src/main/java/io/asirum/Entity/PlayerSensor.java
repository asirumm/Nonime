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

public class PlayerSensor extends BaseBox2d {
    private Player player;

    public PlayerSensor(World world,Player player) {
        super(world);
        this.player = player;
    }

    @Override
    public void build(MapObject object) {
        Rectangle rect = TmxHelper.convertRectangleMapObject(object);

        Vector2 position = Box2dHelper.positionBox2d(rect);
        Vector2 size     = Box2dHelper.sizeBox2d(rect);

        Vector2 offset = new Vector2(
            position.x - player.getBody().getPosition().x,
            position.y - player.getBody().getPosition().y);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x,size.y,offset,0);

        FixtureBuilder fixtureBuilder = new FixtureBuilder()
            .shape(shape)
            .isSensor(true);

        Fixture fixture =
            player
                .getBody()
                .createFixture(fixtureBuilder.build());

        fixture.setUserData("foot");

        shape.dispose();
    }

}
