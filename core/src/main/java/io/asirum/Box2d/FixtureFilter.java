package io.asirum.Box2d;

import com.badlogic.gdx.physics.box2d.Filter;

public class FixtureFilter {
    public static final short CATEGORY_PLAYER = 0x0001;
    public static final short MASK_PLAYER = 0x0002 | 0x0004;

    public static Filter filterPlayer(){
        Filter playerFilter = new Filter();
        playerFilter.maskBits = MASK_PLAYER;
        playerFilter.categoryBits = CATEGORY_PLAYER;

        return  playerFilter;
    }

}
