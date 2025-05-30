package io.asirum.SchemaObject;

import java.util.ArrayList;

/**
 * PERLU DISESUAIKAN JIKA ANDA MERUBAH PAYLOAD
 */
public class UserData {
    private ArrayList<UserLevel> level=new ArrayList<>();
    private short energy;
    private String lastPlayedTime;

    public ArrayList<UserLevel> getLevel() {
        return level;
    }

    public void setLevel(ArrayList<UserLevel> level) {
        this.level = level;
    }

    public void addLevel(UserLevel userLevel){
        this.level.add(userLevel);
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
