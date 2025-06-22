package io.asirum.SchemaObject;

public class GameLevel {
    private short level;
    private String map;

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "GameLevel{" +
            "map = "+map+
            ", level=" + level +
            '}';
    }
}
