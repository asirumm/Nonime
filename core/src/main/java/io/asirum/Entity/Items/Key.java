package io.asirum.Entity.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import io.asirum.Box2d.*;
import io.asirum.Constant;
import io.asirum.Service.ApplicationContext;
import io.asirum.Service.GameAssets;
import io.asirum.TmxMap.TmxHelper;
import io.asirum.Util.SpriteBatchHelper;

// TODO Draw key
public class Key extends BaseBox2d {
    private boolean collected;
    private Vector2 size;
    private Animation<TextureRegion> keyTexture;
    private float stateTime = 0f;

    public Key(World world) {
        super(world);
        GameAssets gm = ApplicationContext
            .getInstance().getGameAssets();

        Array<TextureRegion> tr = new Array<>();
        tr.add(gm.getItemsAtlas().findRegion("key1"));
        tr.add(gm.getItemsAtlas().findRegion("key2"));

        keyTexture = new Animation<>(0.25f,tr);

    }
    public void draw(){

        if (!collected) {
            stateTime += Gdx.graphics.getDeltaTime();
        }

        if (collected) return;

        TextureRegion currentFrame = keyTexture.getKeyFrame(stateTime, true);

        Vector2 pos = body.getPosition();

        SpriteBatchHelper.projectionCombineBegin();
        ApplicationContext.getInstance().getBatch().draw(currentFrame,
            pos.x - size.x ,
            pos.y - size.y ,
            1f,  1f);// ukuran pas untuk gambar saat ini
        SpriteBatchHelper.batchEnd();
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
