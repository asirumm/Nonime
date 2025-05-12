package io.asirum.SchemaObject;

public class GameLevel {
    private String id;
    private int level;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "GameLevel{" +
            "id='" + id + '\'' +
            ", level=" + level +
            '}';
    }
}
