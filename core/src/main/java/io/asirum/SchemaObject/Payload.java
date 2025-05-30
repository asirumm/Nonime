package io.asirum.SchemaObject;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/**
 * representasi data json pada game-data
 */
public class Payload {
   private ArrayList<Region> regions;

    public ArrayList<Region> getRegions() {
        return regions;
    }

    public void setRegions(ArrayList<Region> regions) {
        this.regions = regions;
    }

    @Override
    public String toString() {
        return "Payload{" +
            "regions=" + regions +
            '}';
    }
}
