package io.asirum.Box2d;

import com.badlogic.gdx.physics.box2d.Filter;

public class FixtureFilter {
    public static final short BIT_PLAYER = 0x0001;
    public static final short BIT_PLATFORM = 0x0002;
    public static final short BIT_KEY = 0x0004;
    public static final short BIT_PORTAL = 0x0006;
    // Artinya
    //categoryBits	"Aku ini siapa?" → Menyatakan jenis objek ini (misal: player, musuh, platform)
    //maskBits	"Aku bisa bertabrakan dengan siapa saja?"

    public static Filter filterPlatform() {
        Filter platformFilter = new Filter();
        platformFilter.categoryBits = BIT_PLATFORM;
        platformFilter.maskBits = BIT_PLAYER;

        return platformFilter;
    }

    public static Filter filterPlayer(){
        Filter playerFilter = new Filter();
        playerFilter.categoryBits = BIT_PLAYER;
        playerFilter.maskBits = BIT_PLATFORM|BIT_KEY|BIT_PORTAL;

        return  playerFilter;
    }

    public static Filter filterKey() {
        Filter keyFilter = new Filter();
        keyFilter.categoryBits = BIT_KEY;
        keyFilter.maskBits = BIT_PLAYER;

        return keyFilter;
    }

    public static Filter filterPortal() {
        Filter keyFilter = new Filter();
        keyFilter.categoryBits = BIT_PORTAL;
        keyFilter.maskBits = BIT_PLAYER;

        return keyFilter;
    }
}
