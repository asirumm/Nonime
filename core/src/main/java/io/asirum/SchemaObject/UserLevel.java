package io.asirum.SchemaObject;

public class UserLevel {
    private String name;
    private short level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
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
