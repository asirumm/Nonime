package io.asirum.Entity.Animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.asirum.Service.ApplicationContext;
import io.asirum.Util.SpriteBatchHelper;


import java.util.HashMap;

public class AnimationManager {
    private HashMap<String, Animation<TextureRegion>> animations;
    private String currentAnimation;
    public float stateTime;
    protected SpriteBatch batch;

    public AnimationManager() {
        animations = new HashMap<>();
        batch = ApplicationContext.getInstance().getBatch();

    }

    // key param merupakan key pada hashmap
    public void renderAnimation(String key, boolean looping,
                                Vector2 position,float width, float height){
        if (!key.equals(currentAnimation)) {
            currentAnimation = key;
            stateTime = 0f;
        }

        TextureRegion frame = getCurrentFrame(looping);

        SpriteBatchHelper.setProjectionMatrixCameraCombined(batch);

        batch.draw(frame, position.x,position.y, width, height);

        SpriteBatchHelper.batchEnd(batch);
    }

    public TextureRegion getCurrentFrame(boolean looping) {
        if (animations.isEmpty()){
            throw new RuntimeException("data array animasi kosong, silahkan cek kembali data animasi");
        }

        return animations
            .get(currentAnimation)
            .getKeyFrame(stateTime, looping);
    }

    public boolean isAnimationFinished(){
        return animations
            .get(currentAnimation)
            .isAnimationFinished(stateTime);
    }

    public void addAnimation(String name, Animation<TextureRegion> animation){
        animations.put(name,animation);
    }
}
