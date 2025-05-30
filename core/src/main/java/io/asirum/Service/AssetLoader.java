package io.asirum.Service;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

import static io.asirum.Constant.*;

/**
 * Kelas ini untuk load assets dengan asset manager
 */
public class AssetLoader implements Disposable {
    private AssetManager assetManager;

    public AssetLoader() {
        this.assetManager = new AssetManager();
    }

    public void loadAssets(){
        Log.info(getClass().getName(),"Memulai load game asset");

        loadSkin();
        loadAtlases();
        loadMusic();
    }

    private void loadAtlases() {
        assetManager.load(ASSET_WIDGET_ATLAS, TextureAtlas.class);
        assetManager.load(ASSET_ITEMS_ATLAS, TextureAtlas.class);
        assetManager.load(ASSET_PLAYER_ATLAS, TextureAtlas.class);
    }

    private void loadMusic(){
        assetManager.load(ASSET_SOUND_CONTROL_ON_CLICK, Sound.class);
        assetManager.load(ASSET_SOUND, Music.class);
    }

    private void loadSkin(){
        assetManager.load(ASSET_WIDGET_SKIN, Skin.class);
    }

    /**
     * Check if load assets is complete
     */
    public boolean update() {
        return assetManager.update();
    }

    public AssetManager getAssetManager(){
        return assetManager;
    }

    @Override
    public void dispose() {
        assetManager.dispose();

        Log.debug(getClass().getName(),"[dispose]");
    }
}
