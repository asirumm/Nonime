package io.asirum.Entity.EntityAnimation;

import com.badlogic.gdx.physics.box2d.Body;
import io.asirum.Entity.Animation.AnimationManager;

public abstract class EntityAnimations {
    protected float frameDuration;
    protected float height;
    protected float width;
    protected AnimationManager manager;

    public EntityAnimations() {
        manager = new AnimationManager();
    }

    public abstract void animationInitialization();

    public abstract void draw(Body body,float delta);

    /**
     * digunakan apabila animasi kalian tanpa looping
     * ingin mengetahui apakah sudah selesai rendernya
     */
    public boolean isAnimationFinished(){
        return manager.isAnimationFinished();
    }
}
