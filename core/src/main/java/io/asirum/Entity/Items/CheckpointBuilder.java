package io.asirum.Entity.Items;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import io.asirum.Box2d.*;
import io.asirum.TmxMap.TmxHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * kelas ini dibuat agar 1 objek checkpoint mengarah pada 1 vector position
 * tujuannya supaya player bisa respawn dibanyak checkpoint
 */
public class CheckpointBuilder extends BaseBox2d {
    private final List<Checkpoint> checkpoints;

    public CheckpointBuilder(World world) {
        super(world);
        checkpoints = new ArrayList<>();
    }

    @Override
    public void build(MapObject object) {
        Rectangle rect = TmxHelper.convertRectangleMapObject(object);

        Vector2 position = Box2dHelper.positionBox2d(rect);
        Vector2 size     = Box2dHelper.sizeBox2d(rect);

        BodyBuilder bodyBuilder = new BodyBuilder()
            .fixRotation(true)
            .position(position.x, position.y);

        body = world.createBody(bodyBuilder.build());

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x,size.y);

        FixtureBuilder fixtureBuilder = new FixtureBuilder()
            .shape(shape)
            .filter(FixtureFilter.filterCheckpoint())
            .isSensor(true);

        Fixture fixture = body.createFixture(fixtureBuilder.build());

        Checkpoint cp = new Checkpoint(position,body);

        fixture.setUserData(Box2dHelper.CHECKPOINT_FIXTURE_NAME);
        body.setUserData(cp);

        checkpoints.add(cp);

        shape.dispose();
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }
}
