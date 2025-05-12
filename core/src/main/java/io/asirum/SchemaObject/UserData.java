package io.asirum.SchemaObject;

/**
 * PERLU DISESUAIKAN JIKA ANDA MERUBAH PAYLOAD
 */
public class UserData {
    private int level;
    private int energy;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    @Override
    public String toString() {
        return "UserData{" +
            "level=" + level +
            ", energy=" + energy +
            '}';
    }
}
