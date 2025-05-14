package io.asirum.Entity.Player;

import com.badlogic.gdx.Gdx;
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
    // ======= Parameter Gerakan =======
    private float runMaxSpeed = 7f;           // Kecepatan maksimum saat berlari
    private float runAcceleration = 0.5f;     // Waktu untuk mencapai kecepatan maksimum dari diam
    private float runDecceleration = 0.2f;    // Waktu untuk melambat dari kecepatan maksimum ke diam

    private float accelInAir = 0.5f;          // Pengali akselerasi saat di udara
    private float deccelInAir = 0.3f;         // Pengali deselerasi saat di udara

    private boolean doConserveMomentum = true; // Apakah momentum akan dipertahankan saat di udara

    // Konstanta lompat
    private float jumpForce = 3f;

    private float lastPressedJumpTime = -1f;
    private float lastOnGroundTime = -1f;

    private boolean onGround = false;          // Status apakah player sedang di tanah

    // ======= Konstanta waktu fisika =======
    private static final float FIXED_DELTA_TIME = 1f / 60f;

    public void jump() {
        // Reset "buffer time" agar tidak lompat berulang dari satu tombol tekan
        lastPressedJumpTime = 0f;
        lastOnGroundTime = 0f;

        // Ambil kecepatan vertikal sekarang
        float verticalVelocity = body.getLinearVelocity().y;

        // Hitung gaya lompat
        float force = jumpForce;
        if (verticalVelocity < 0) {
            // Jika sedang jatuh, tambahkan nilai untuk menyeimbangkan
            force -= verticalVelocity; // Minus dikurangi minus = bertambah
        }

        // Terapkan gaya lompat ke atas (y-axis), dengan impuls (bukan gaya konstan)
        body.applyLinearImpulse(new Vector2(0, force), body.getWorldCenter(), true);
    }

    public void run(boolean moveLeft, boolean moveRight) {
        float inputX = 0f;
        if (moveLeft) inputX -= 1f;
        if (moveRight) inputX += 1f;

        float runAccelAmount = (1f / FIXED_DELTA_TIME) * runAcceleration / runMaxSpeed;
        float runDeccelAmount = (1f / FIXED_DELTA_TIME) * runDecceleration / runMaxSpeed;

        float targetSpeed = inputX * runMaxSpeed;
        float speedDiff = targetSpeed - body.getLinearVelocity().x;

        float accelRate = (Math.abs(targetSpeed) > 0.01f)
            ? (onGround ? runAccelAmount : runAccelAmount * accelInAir)
            : (onGround ? runDeccelAmount : runDeccelAmount * deccelInAir);

        if (doConserveMomentum &&
            Math.abs(body.getLinearVelocity().x) > Math.abs(targetSpeed) &&
            Math.signum(body.getLinearVelocity().x) == Math.signum(targetSpeed) &&
            Math.abs(targetSpeed) > 0.01f &&
            !onGround) {

            accelRate = 0f;
        }

        float movementForce = speedDiff * accelRate;
        body.applyForceToCenter(new Vector2(movementForce, 0f), true);
    }


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
            .gravityScale(5f)
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

        fixture.setUserData(Box2dHelper.PLAYER_FIXTURE_NAME);
        body.setUserData(this);

        shape.dispose();
    }



    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

}
