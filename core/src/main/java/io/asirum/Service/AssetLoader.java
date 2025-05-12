package io.asirum.Service;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Disposable;

/**
 * Kelas ini untuk load assets dengan asset manager
 */
public class AssetLoader implements Disposable {
    private AssetManager assetManager;

    public AssetLoader() {
        this.assetManager = new AssetManager();
    }

    public void loadAssets(){

    }

    public AssetManager getAssetManager(){
        return assetManager;
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
