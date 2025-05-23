package io.asirum.SchemaObject;

import com.badlogic.gdx.utils.Array;

/**
 * representasi data json pada game-data
 */
public class Payload {
    private String tema;
    private int cost;
    private Array<GameLevel> level;

    public String getTema() {
        return tema;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Array<GameLevel> getLevel() {
        return level;
    }

    public void setLevel(Array<GameLevel> level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Payload{" +
            "tema='" + tema + '\'' +
            ", cost=" + cost +
            ", level=" + level +
            '}';
    }
}
