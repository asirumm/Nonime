package io.asirum.Entity.Animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;


import java.util.HashMap;

public class AnimationComponent {
    private HashMap<String, Animation<TextureRegion>> animations;
    private String currentAnimation;
    public float stateTime;

    public AnimationComponent(){
        animations = new HashMap<>();
    }
    public void play(String key) {
        if (!key.equals(currentAnimation)) {
            currentAnimation = key;
            stateTime = 0f;
        }
    }

    public TextureRegion getCurrentFrame(boolean looping) {
        if (animations.isEmpty()){
            throw new RuntimeException("Animasi kosong, silahkan cek kembali data animasi");
        }

        return animations.get(currentAnimation).getKeyFrame(stateTime, looping);
    }

    public void addAnimation(String name, Animation<TextureRegion> animation){
        animations.put(name,animation);
    }

    /**
     * Membuat array animasi dari region atlas berdasarkan nama prefix.
     * Metode ini akan mencari region bernama `prefixName1`, `prefixName2`, dan seterusnya
     * hingga region dengan nama tersebut tidak ditemukan dalam atlas.
     *
     */
    public static Array<TextureRegion> createAnimationFrames( AnimationState state, TextureAtlas atlas) {
        Array<TextureRegion> animationFrames = new Array<>();

        for (int i = 1; ; i++) {
            TextureRegion region = atlas.findRegion(state.getPrefixName() + i);
            if (region == null)
            {
                break;
            };
            animationFrames.add(region);
        }

        return animationFrames;
    }

    public Array<TextureRegion> createHorizontalFlippedFrames(Array<TextureRegion> originalFrames) {
        Array<TextureRegion> flippedFrames = new Array<>();

        for (TextureRegion frame : originalFrames) {
            // jangan direferensikan, nanti gambar utama malah di flip
            // jadi biarkan buat objek baru
            TextureRegion flipped = new TextureRegion(frame);
            flipped.flip(true, false);
            flippedFrames.add(flipped);
        }

        return flippedFrames;
    }

    public TextureRegion createFlipHorizontalFrame(TextureRegion textureRegion){
        TextureRegion temp = new TextureRegion(textureRegion);
        temp.flip(true,false);
        return temp;
    }
}
