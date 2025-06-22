package io.asirum.Entity.EntityAnimation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import io.asirum.Constant;
import io.asirum.Entity.Animation.AnimationConstant;
import io.asirum.Entity.Animation.AnimationHelper;
import io.asirum.Entity.Player.Player;
import io.asirum.Service.ApplicationContext;
import io.asirum.Service.GameAssets;
import io.asirum.Service.Log;

import static io.asirum.Entity.Animation.AnimationConstant.*;
import static io.asirum.Entity.Animation.AnimationHelper.createAnimationFromAtlas;
import static io.asirum.Entity.Animation.AnimationState.*;

public class PlayerAnimation extends EntityAnimations{
    private boolean lastFacingRight = true;
    private float frameDurationDead = 1f/9f;
    private Player player;

    public PlayerAnimation(Player player) {
        frameDuration = 1f / 12f; // 1f/60f = 60fps
        this.player = player;
    }

    @Override
    public void draw(Body body, float delta) {
        // Menambah waktu animasi berdasarkan delta time
        manager.stateTime += delta;

        // Mendeteksi apakah player sedang bergerak
        // Jika kecepatan lebih besar dari 0.1, berarti player bergerak
        boolean isMoving = Math.abs(body.getLinearVelocity().x) > 0.1f;

        // manager posisi lastFacingRight variable
        animationPositionLeftOrRight(body, isMoving);

        // posisi gambar
        float x = body.getPosition().x - width / 2f;
        // kinda wierd? ya i know,
        // the problem
        // kita buat aset dengan kanvas ex : 64
        // actual size paling 30 atau 40an, disini masalahnya
        // di box2d kita gk buat rectangle 64 tapi sesuai actual size
        float y = body.getPosition().y- height/2f + .3f ;
        Vector2 position = new Vector2(x,y);

        // animasi yang akan dirender
        String animationToRender;

        // Ketika player butuh respawn (biasanya setelah animasi mati selesai)
        if (player.isPlayerNeedRespawn()) {
            animationToRender = lastFacingRight ? DIED_RIGHT : DIED_LEFT;

            manager.renderAnimation(
                animationToRender,false,
                position,width,height);

            // Ketika tidak di tanah, artinya player sedang lompat
        } else if (!player.isOnGround()) {
            animationToRender = lastFacingRight ? JUMP_RIGHT : JUMP_LEFT;

            manager.renderAnimation(
                animationToRender,true,
                position,width,height);

            // Ketika player di tanah dan bergerak
        } else if (isMoving) {
            animationToRender = lastFacingRight ? RUN_RIGHT : RUN_LEFT;

            manager.renderAnimation(
                animationToRender,true,
                position,width,height);

            // Ketika player diam
        } else {
            animationToRender = lastFacingRight ? IDLE_RIGHT : IDLE_LEFT;

            manager.renderAnimation(
                animationToRender,true,
                position,width,height);
        }

    }

    private void animationPositionLeftOrRight(Body body, boolean isMoving) {
        // Mengupdate arah hadap player
        if (isMoving) {
            // Jika kecepatan >= 0, player menghadap ke kanan
            // Jika kecepatan < 0, player menghadap ke kiri
            lastFacingRight = body.getLinearVelocity().x >= 0;
        }
    }

    @Override
    public void animationInitialization() {
        Log.debug(getClass().getName(),"deklarasi animasi dijalankan");

        // mendapatkan atlas player
        GameAssets assets = ApplicationContext.getInstance().getGameAssets();
        TextureAtlas atlasPlayer = assets.getPlayerAtlas();

        TextureAtlas.AtlasRegion texture = atlasPlayer.getRegions().get(0);
        width  = texture.getRegionWidth()/ Constant.UNIT_SCALE;
        height = texture.getRegionHeight()/ Constant.UNIT_SCALE;

        animationRun(atlasPlayer);
        animationJump(atlasPlayer);
        animationIdle(atlasPlayer);
        animationDied(atlasPlayer);

    }

    private void animationDied(TextureAtlas atlas) {
        // membuat animasi berdasarkan atlas
        Array<TextureRegion> diedRight =  createAnimationFromAtlas(DIED.getPrefixName(), atlas);
        Array<TextureRegion> diedLeft  =  AnimationHelper.flipTexture(diedRight, true);

        manager.addAnimation(
            AnimationConstant.DIED_RIGHT,
            new Animation<>(frameDurationDead , diedRight)
        );

        manager.addAnimation(
            AnimationConstant.DIED_LEFT,
            new Animation<>(frameDurationDead, diedLeft)
        );
    }

    private void animationIdle(TextureAtlas atlas) {
        // membuat animasi berdasarkan atlas
        Array<TextureRegion> idleRight =  createAnimationFromAtlas(IDLE.getPrefixName(), atlas);
        Array<TextureRegion> idleLeft  =  AnimationHelper.flipTexture(idleRight, true);

        manager.addAnimation(
            AnimationConstant.IDLE_RIGHT,
            new Animation<>(frameDuration, idleRight)
        );

        manager.addAnimation(
            AnimationConstant.IDLE_LEFT,
            new Animation<>(frameDuration, idleLeft)
        );
    }

    private void animationJump(TextureAtlas atlas) {
        // membuat animasi berdasarkan atlas
        Array<TextureRegion> jumpRight =  createAnimationFromAtlas(JUMP.getPrefixName(), atlas);
        Array<TextureRegion> jumpLeft  =  AnimationHelper.flipTexture(jumpRight, true);

        manager.addAnimation(
            JUMP_RIGHT,
            new Animation<>(frameDuration, jumpRight)
        );

        manager.addAnimation(
            JUMP_LEFT,
            new Animation<>(frameDuration, jumpLeft)
        );
    }

    private void animationRun(TextureAtlas atlas) {
        // membuat animasi berdasarkan atlas
        Array<TextureRegion> runRight =  createAnimationFromAtlas(RUN.getPrefixName(), atlas);
        Array<TextureRegion> runLeft =  AnimationHelper.flipTexture(runRight, true);

        manager.addAnimation(
            RUN_RIGHT,
            new Animation<>(frameDuration, runRight)
        );

        manager.addAnimation(
            AnimationConstant.RUN_LEFT,
            new Animation<>(frameDuration, runLeft)
        );
    }


}
