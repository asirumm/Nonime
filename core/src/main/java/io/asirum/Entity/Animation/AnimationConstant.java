package io.asirum.Entity.Animation;

/**
 * Kelas ini menjadi penamaan animasi ketika in game
 * contoh : animation.play(AnimationConstant.IDLE_RIGHT);
 * maka gunakan kelas ini agar lebih mudah
 */
public class AnimationConstant {
    // Format: STATE_DIRECTION
    public static final String IDLE_RIGHT = AnimationState.IDLE.getPrefixName() + "-" + Direction.RIGHT.getSuffix();
    public static final String IDLE_LEFT = AnimationState.IDLE.getPrefixName() + "-" + Direction.LEFT.getSuffix();
    public static final String RUN_RIGHT = AnimationState.RUN.getPrefixName() + "-" + Direction.RIGHT.getSuffix();
    public static final String RUN_LEFT = AnimationState.RUN.getPrefixName() + "-" + Direction.LEFT.getSuffix();
    public static final String JUMP_RIGHT = AnimationState.JUMP.getPrefixName() + "-" + Direction.RIGHT.getSuffix();
    public static final String JUMP_LEFT = AnimationState.JUMP.getPrefixName() + "-" + Direction.LEFT.getSuffix();

    // Konstanta durasi frame
    public static final float FRAME_DURATION = 0.25f;
}
