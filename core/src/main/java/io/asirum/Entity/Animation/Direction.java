package io.asirum.Entity.Animation;

public enum Direction {
    RIGHT("right"),
    LEFT("left");

    private final String suffix;

    Direction(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }
}
