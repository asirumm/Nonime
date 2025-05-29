package io.asirum.SchemaObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * PERLU DISESUAIKAN JIKA ANDA MERUBAH PAYLOAD
 */
public class UserData {
    private int level;
    private short energy;
    private String lastPlayedTime;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public short getEnergy() {
        return energy;
    }

    public String getLastPlayedTime() {
        return lastPlayedTime;
    }

    public void setLastPlayedTime(String lastPlayedTime) {
        this.lastPlayedTime = lastPlayedTime;
    }

    public void setEnergy(short energy) {
        this.energy = energy;
    }

    @Override
    public String toString() {
        return "UserData{" +
            "level=" + level +
            ", energy=" + energy +
            ", lastPlayGame="+lastPlayedTime+
            '}';
    }
}
