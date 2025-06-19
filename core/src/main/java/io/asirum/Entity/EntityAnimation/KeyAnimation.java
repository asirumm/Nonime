package io.asirum.Entity.EntityAnimation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import io.asirum.Constant;
import io.asirum.Service.ApplicationContext;
import io.asirum.Service.GameAssets;
import io.asirum.Service.Log;

import static io.asirum.Entity.Animation.AnimationHelper.createAnimationFromAtlas;

public class KeyAnimation extends EntityAnimations{
    private String prefixAtlasName = "key";

    public KeyAnimation() {
        frameDuration = 1f/9f;
    }

    @Override
    public void animationInitialization() {
        Log.debug(getClass().getName(),"deklarasi animasi dijalankan");

        // mendapatkan atlas player
        GameAssets assets = ApplicationContext.getInstance().getGameAssets();
        TextureAtlas atlas = assets.getItemsAtlas();

        TextureAtlas.AtlasRegion texture = atlas.getRegions().get(0);
        width  = texture.getRegionWidth()/ Constant.UNIT_SCALE;
        height = texture.getRegionHeight()/ Constant.UNIT_SCALE;

        Array<TextureRegion> defaultAnim =  createAnimationFromAtlas(prefixAtlasName,atlas );
        manager.addAnimation(prefixAtlasName, new Animation<>(frameDuration, defaultAnim));
    }

    @Override
    public void draw(Body body, float delta) {
        manager.stateTime += delta;

        float x = body.getPosition().x - width / 2f;
        float y = body.getPosition().y - height / 2f;

        manager.renderAnimation(prefixAtlasName,true,new Vector2(x,y),width,height);
    }
}
