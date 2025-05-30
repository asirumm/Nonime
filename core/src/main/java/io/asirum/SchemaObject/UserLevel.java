package io.asirum.SchemaObject;

public class UserLevel {
    private String name;
    private int level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "UserLevel{" +
            "name='" + name + '\'' +
            ", level=" + level +
            '}';
    }
}
