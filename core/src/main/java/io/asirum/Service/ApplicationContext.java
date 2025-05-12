package io.asirum.Service;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ApplicationContext {
    private static ApplicationContext instance;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private GameAssets gameAssets;
    private AssetLoader assetLoader;

    private ApplicationContext(){
        camera = new OrthographicCamera();
        batch  = new SpriteBatch();
        assetLoader = new AssetLoader();// butuh di dispose
        gameAssets = new GameAssets(assetLoader);
    }

    public void dispose(){
        assetLoader.dispose();
        batch.dispose();
    }

    public static ApplicationContext getInstance() {
        return instance;
    }

    // static block initialization for exception handling
    static {
        try {
            instance = new ApplicationContext();
        } catch (Exception e) {

            throw new RuntimeException("Instance ApplicationContext kosong");
        }
    }

    public AssetLoader getAssetLoader() {
        return assetLoader;
    }

    public GameAssets getGameAssets() {
        return gameAssets;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
