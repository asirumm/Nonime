package io.asirum.Box2d;

import com.badlogic.gdx.physics.box2d.Filter;

public class FixtureFilter {
    public static final short CATEGORY_PLAYER = 0x0001;
    public static final short MASK_PLAYER = 0x0002 | 0x0004;
    public static final short CATEGORY_PLATFORM = 0x0002;
    public static final short MASK_PLATFORM = CATEGORY_PLAYER;
    // Artinya
    //categoryBits	"Aku ini siapa?" → Menyatakan jenis objek ini (misal: player, musuh, platform)
    //maskBits	"Aku bisa bertabrakan dengan siapa saja?"

    public static Filter filterPlatform() {
        Filter platformFilter = new Filter();
        platformFilter.categoryBits = CATEGORY_PLATFORM;
        platformFilter.maskBits = MASK_PLATFORM;

        return platformFilter;
    }

    public static Filter filterPlayer(){
        Filter playerFilter = new Filter();
        playerFilter.maskBits = MASK_PLAYER;
        playerFilter.categoryBits = CATEGORY_PLAYER;

        return  playerFilter;
    }

}
