package io.asirum.Entity.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import io.asirum.Box2d.*;
import io.asirum.Entity.Animation.AnimationComponent;
import io.asirum.Entity.Behavior.Jump;
import io.asirum.Entity.Behavior.Run;
import io.asirum.Service.ApplicationContext;
import io.asirum.Service.GameAssets;
import io.asirum.Service.Log;
import io.asirum.TmxMap.TmxHelper;
import io.asirum.Util.SpriteBatchHelper;

public class Player extends BaseBox2d {

    private boolean playerNeedRespawn;
    private Vector2 lastPosition;// untuk checkpoint

    private short playerLive = 3;

    private float playerHeight;// untuk fixture foot sensor

    // ======= Parameter Gerakan =======
    private float runMaxSpeed = 4f;           // Kecepatan maksimum saat berlari
    private float runAcceleration = 0.8f;     // Waktu untuk mencapai kecepatan maksimum dari diam
    private float runDecceleration = 0.2f;    // Waktu untuk melambat dari kecepatan maksimum ke diam

 // Konstanta lompat
    private float jumpForce = 1.03f;
    private float maxJump = 4f;

    private boolean onGround = false;          // Status apakah player sedang di tanah

    private Jump jumpBehavior;
    private Run runBehavior;

    private PlayerAnimation animation;

    public Player(World world) {
        super(world);
        runBehavior  = new Run(runMaxSpeed,runAcceleration,runDecceleration);
        jumpBehavior = new Jump(jumpForce,maxJump);

        animation = new PlayerAnimation();
    }

    public void jump(){
        jumpBehavior.doJump(body);
    }

    public void run(boolean moveLeft,boolean moveRight){
        runBehavior.run(moveLeft,moveRight,body,onGround);
    }

    public void drawAnimation(){
        animation.draw(Gdx.graphics.getDeltaTime(),onGround,body);
    }

    @Override
    public void build(MapObject object) {
        Rectangle rect = TmxHelper.convertRectangleMapObject(object);

        Vector2 position = positionBox2d(rect);
        Vector2 size     = sizeBox2d(rect);

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

        fixture.setUserData(Box2dVars.PLAYER_FIXTURE);
        body.setUserData(this);
        playerHeight = size.y;

        shape.dispose();
    }


    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }


    public void setLastCheckpoint(Vector2 pos){
        lastPosition = pos;
    }

    public void respawn(){
        if(lastPosition==null){
            Log.info(getClass().getName(),"NULL vector respawn");
        }else {
            body.setTransform(lastPosition,0);
        }
    }

    public void setPlayerNeedRespawn(boolean playerNeedRespawn) {
        this.playerNeedRespawn = playerNeedRespawn;
    }

    public boolean isPlayerNeedRespawn() {
        return playerNeedRespawn;
    }

    public void decreasePlayerLive(){
        playerLive -=1;
        Log.debug(getClass().getName(),"kesempatan hidup player "+playerLive );
    }

    public short getPlayerLive(){
        return playerLive;
    }

    public float getPlayerHeight() {
        return playerHeight;
    }
}
