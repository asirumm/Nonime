package io.asirum.SchemaObject;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class Region {
    private String name;
    private short cost;
    private String background;
    private ArrayList<GameLevel> levels;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getCost() {
        return cost;
    }

    public void setCost(short cost) {
        this.cost = cost;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public ArrayList<GameLevel> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<GameLevel> levels) {
        this.levels = levels;
    }

    @Override
    public String toString() {
        return "Region{" +
            "name='" + name + '\'' +
            ", cost=" + cost +
            ", background='" + background + '\'' +
            ", levels=" + levels +
            '}';
    }
}
