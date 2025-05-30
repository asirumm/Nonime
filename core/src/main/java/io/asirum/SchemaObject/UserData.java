package io.asirum.SchemaObject;

import com.badlogic.gdx.utils.Array;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * PERLU DISESUAIKAN JIKA ANDA MERUBAH PAYLOAD
 */
public class UserData {
    private Array<UserLevel> level;
    private short energy;
    private String lastPlayedTime;

    public Array<UserLevel> getLevel() {
        return level;
    }

    public void setLevel(Array<UserLevel> level) {
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
