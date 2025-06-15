package io.asirum.Entity.Player;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import io.asirum.Box2d.*;
import io.asirum.Entity.Behavior.Jump;
import io.asirum.Entity.Behavior.Run;
import io.asirum.Entity.EntityAnimation.PlayerAnimation;
import io.asirum.Service.*;
import io.asirum.TmxMap.TmxHelper;

public class Player extends BaseBox2d {

    private boolean playerNeedRespawn;
    private Vector2 lastPosition;// untuk checkpoint
    private boolean bringKey;

    private short playerLive = 3;

    // JANGAN DIHAPUS BERGUNA UNTUK ONE WAY
    private float playerHeight;// untuk fixture foot sensor

    // ======= Parameter Gerakan =======
    private float runMaxSpeed = 7f;           // Kecepatan maksimum saat berlari
    private float runAcceleration = 2f;     // Waktu untuk mencapai kecepatan maksimum dari diam
    private float runDecceleration = 1f;    // Waktu untuk melambat dari kecepatan maksimum ke diam

 // Konstanta lompat
    private float jumpForce = 5f;
    private float maxJump = 7f;

    private boolean onGround = false;          // Status apakah player sedang di tanah

    private Jump jumpBehavior;
    private Run runBehavior;

    private PlayerAnimation animation;

    public Player(World world) {
        super(world);
        runBehavior  = new Run(runMaxSpeed,runAcceleration,runDecceleration);
        jumpBehavior = new Jump(jumpForce,maxJump);

        animation = new PlayerAnimation(this);
        animation.animationInitialization();
    }

    public void jump(){
        jumpBehavior.doJump(body);
    }

    public void run(boolean moveLeft,boolean moveRight){
        runBehavior.run(moveLeft,moveRight,body,onGround);
    }

    public void drawAnimation(float delta){
        animation.draw(body,delta);
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
        if(animation.isAnimationFinished()){
            body.setTransform(lastPosition,0);
            playerNeedRespawn =false;
        }
    }

    public void playerNeedRespawnActive() {
        this.playerNeedRespawn = true;
    }

    public boolean isPlayerNeedRespawn() {
        return playerNeedRespawn;
    }

    public void decreasePlayerLive(){
        playerLive -=1;
        Log.debug(getClass().getName(),"kesempatan hidup player %s",playerLive );
    }

    public short getPlayerLive(){
        return playerLive;
    }

    public float getPlayerHeight() {
        return playerHeight;
    }

    public boolean isBringKey() {
        return bringKey;
    }

    public void setBringKey(boolean bringKey) {
        Log.debug(getClass().getCanonicalName(),"player telah membawa kunci");
        this.bringKey = bringKey;
    }
}
