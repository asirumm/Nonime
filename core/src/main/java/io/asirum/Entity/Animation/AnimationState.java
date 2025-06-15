package io.asirum.Entity.Animation;

public enum AnimationState {
    IDLE("idle"),
    RUN("run"),
    JUMP("jump"),
    DIED("die");

    private final String prefixName;

    AnimationState(String prefixName) {
        this.prefixName = prefixName;
    }

    public String getPrefixName() {
        return prefixName;
    }
}
