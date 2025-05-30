package io.asirum.SchemaObject;

import com.badlogic.gdx.utils.Array;

/**
 * representasi data json pada game-data
 */
public class Payload {
   private Array<Region> regions;

    public Array<Region> getRegions() {
        return regions;
    }

    public void setRegions(Array<Region> regions) {
        this.regions = regions;
    }

    @Override
    public String toString() {
        return "Payload{" +
            "regions=" + regions +
            '}';
    }
}
