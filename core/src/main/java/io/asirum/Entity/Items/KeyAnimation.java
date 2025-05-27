package io.asirum.Entity.Items;

import com.badlogic.gdx.Gdx;
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
import io.asirum.Util.SpriteBatchHelper;

import static io.asirum.Entity.Animation.AnimationComponent.createAnimationFrames;

public class KeyAnimation {
    private AnimationComponent animation;
    private float width;
    private float height;
    private String prefixAtlasName = "key";

    public KeyAnimation() {
        animation = new AnimationComponent();

        declareAnimations();
    }

    private void declareAnimations() {
        GameAssets assets = ApplicationContext.getInstance().getGameAssets();
        TextureAtlas atlas = assets.getItemsAtlas();

        // Membuat frame untuk setiap state animasi
        Array<TextureRegion> frames = createAnimationFrames(prefixAtlasName, atlas);

        animation.addAnimation(prefixAtlasName,new Animation<>(AnimationConstant.FRAME_DURATION,frames));

        setAnimationDimensions(frames.get(0));
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
        SpriteBatchHelper.projectionCombineBegin();

        ApplicationContext.getInstance().getBatch().draw(frame, x, y, width, height);

        // Mengakhiri proses menggambar
        SpriteBatchHelper.batchEnd();
    }

    public void draw(Body body,float delta){
        animation.stateTime += delta;
        animation.play(prefixAtlasName);

        renderCurrentFrame(body);
    }

    private void setAnimationDimensions(TextureRegion referenceFrame) {
        width  = referenceFrame.getRegionWidth()/ Constant.UNIT_SCALE;
        height = referenceFrame.getRegionHeight()/ Constant.UNIT_SCALE;
    }

}
