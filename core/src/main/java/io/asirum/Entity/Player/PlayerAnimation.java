package io.asirum.Entity.Player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import io.asirum.Constant;
import io.asirum.Entity.Animation.AnimationComponent;
import io.asirum.Entity.Animation.AnimationConstant;
import io.asirum.Entity.Animation.AnimationState;
import io.asirum.Service.ApplicationContext;
import io.asirum.Service.GameAssets;
import io.asirum.Service.Log;
import io.asirum.Util.SpriteBatchHelper;

import static io.asirum.Entity.Animation.AnimationComponent.createAnimationFrames;

public class PlayerAnimation {
    private AnimationComponent animation;
    // digunakan untuk menentukan arah animasi
    private boolean lastFacingRight = true;
    private float width;
    private float height;


    public PlayerAnimation() {
        animation = new AnimationComponent();

        declareAnimations();
    }

    // body disini, karena di player class kita tidak bisa
    // deklarasi di konstruktor. Karena body baru ada ketika
    // loadBox2d sudah membaca tmx player (urutannya berbeda)
    public void draw(float delta,boolean onGround,Body body) {
        updateAnimationState(delta,onGround,body);
        renderCurrentFrame(body);
    }

    // Method untuk mengatur animasi berdasarkan kondisi pemain
    private void updateAnimationState(float delta,boolean onGround,Body body) {
        // Menambah waktu animasi berdasarkan delta time
        animation.stateTime += delta;

        // Mendeteksi apakah player sedang bergerak
        // Jika kecepatan lebih besar dari 0.1, berarti player bergerak
        boolean isMoving = Math.abs(body.getLinearVelocity().x) > 0.1f;

        // Mengupdate arah hadap player
        if (isMoving) {
            // Jika kecepatan >= 0, player menghadap ke kanan
            // Jika kecepatan < 0, player menghadap ke kiri
            lastFacingRight = body.getLinearVelocity().x >= 0;
        }

        // Menentukan animasi yang akan diputar
        if (!onGround) {
            // Ketika player di udara (melompat)
            if (lastFacingRight) {
                // Animasi lompat menghadap kanan
                animation.play(AnimationConstant.JUMP_RIGHT);
            } else {
                // Animasi lompat menghadap kiri
                animation.play(AnimationConstant.JUMP_LEFT);
            }
        } else if (isMoving) {
            // Ketika player di tanah dan bergerak
            if (lastFacingRight) {
                // Animasi lari ke kanan
                animation.play(AnimationConstant.RUN_RIGHT);
            } else {
                // Animasi lari ke kiri
                animation.play(AnimationConstant.RUN_LEFT);
            }
        } else {
            // Ketika player diam
            if (lastFacingRight) {
                // Animasi idle menghadap kanan
                animation.play(AnimationConstant.IDLE_RIGHT);
            } else {
                // Animasi idle menghadap kiri
                animation.play(AnimationConstant.IDLE_LEFT);
            }
        }
    }

    // Method untuk menggambar frame animasi saat ini ke layar
    private void renderCurrentFrame(Body body) {
        // Mendapatkan frame animasi saat ini
        TextureRegion frame = animation.getCurrentFrame(true);

         // Menghitung posisi sprite
        // Posisi x dan y digeser setengah width dan height agar sprite terpusat
        float x = body.getPosition().x - width / 2f;
        float y = body.getPosition().y - height / 2f;

        // Memulai proses menggambar sprite
        SpriteBatchHelper.setProjectionMatrixCameraCombined();

        // Menggambar sprite dengan sedikit offset ke atas (0.1f) pada sumbu y
        ApplicationContext.getInstance().getBatch().draw(frame, x, y +0.3f, width, height);

        // Mengakhiri proses menggambar
        SpriteBatchHelper.batchEnd();
    }

    private void declareAnimations(){
        Log.debug(getClass().getName(),"deklarasi animasi player");

        GameAssets assets = ApplicationContext.getInstance().getGameAssets();
        TextureAtlas atlas = assets.getPlayerAtlas();

        // Membuat frame untuk setiap state animasi
        Array<TextureRegion> idleFrames = createAnimationFrames(AnimationState.IDLE, atlas);
        Array<TextureRegion> runFrames =  createAnimationFrames(AnimationState.RUN, atlas);
        TextureRegion jumpFrame = atlas.findRegion(AnimationState.JUMP.getPrefixName());

        // Menambahkan animasi menghadap kanan (default)
        addDefaultDirectionAnimations(idleFrames, runFrames, jumpFrame);

        // Membuat dan menambahkan animasi menghadap kiri (flipped)
        addFlippedDirectionAnimations(idleFrames, runFrames, jumpFrame);

        // Mengatur ukuran untuk menggambar
        setAnimationDimensions(jumpFrame);
    }

    private void addDefaultDirectionAnimations(
        Array<TextureRegion> idleFrames,
        Array<TextureRegion> runFrames,
        TextureRegion jumpFrame) {
        animation.addAnimation(
            AnimationConstant.IDLE_RIGHT,
            new Animation<>(AnimationConstant.FRAME_DURATION, idleFrames)
        );
        animation.addAnimation(
            AnimationConstant.RUN_RIGHT,
            new Animation<>(AnimationConstant.FRAME_DURATION, runFrames)
        );
        animation.addAnimation(
            AnimationConstant.JUMP_RIGHT,
            new Animation<>(AnimationConstant.FRAME_DURATION, jumpFrame)
        );
    }

    private void addFlippedDirectionAnimations(
        Array<TextureRegion> idleFrames,
        Array<TextureRegion> runFrames,
        TextureRegion jumpFrame) {
        // Membuat frame yang di-flip
        Array<TextureRegion> idleFlipped = animation.createHorizontalFlippedFrames(idleFrames);
        Array<TextureRegion> runFlipped = animation.createHorizontalFlippedFrames(runFrames);
        TextureRegion jumpFlipped = animation.createFlipHorizontalFrame(jumpFrame);

        // Menambahkan animasi yang di-flip
        animation.addAnimation(
            AnimationConstant.IDLE_LEFT,
            new Animation<>(AnimationConstant.FRAME_DURATION, idleFlipped)
        );
        animation.addAnimation(
            AnimationConstant.RUN_LEFT,
            new Animation<>(AnimationConstant.FRAME_DURATION, runFlipped)
        );
        animation.addAnimation(
            AnimationConstant.JUMP_LEFT,
            new Animation<>(AnimationConstant.FRAME_DURATION, jumpFlipped)
        );
    }

    private void setAnimationDimensions(TextureRegion referenceFrame) {
        width  = referenceFrame.getRegionWidth()/ Constant.UNIT_SCALE;
        height = referenceFrame.getRegionHeight()/ Constant.UNIT_SCALE;
    }
}
