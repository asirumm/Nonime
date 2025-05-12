package io.asirum.Service;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.ScreenManager;
import de.eskalon.commons.screen.transition.ScreenTransition;
import io.asirum.Util.CameraHelper;

import static io.asirum.Constant.VIRTUAL_HEIGHT;
import static io.asirum.Constant.VIRTUAL_WIDTH;

public class ApplicationContext {
    private static ApplicationContext instance;

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private GameAssets gameAssets;
    private AssetLoader assetLoader;
    private ScreenManager<ManagedScreen, ScreenTransition> screenManager;

    private ApplicationContext(){
        camera = new OrthographicCamera();
        batch  = new SpriteBatch();
        assetLoader = new AssetLoader();
        gameAssets = new GameAssets(assetLoader);

        CameraHelper.toOrtho(camera);

        viewport = CameraHelper.fitViewport(camera);
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

    public void pushScreen(ManagedScreen managedScreen,ScreenTransition screenTransition) {
        screenManager.pushScreen(managedScreen,screenTransition);
    }

    public void setScreenManager(ScreenManager<ManagedScreen, ScreenTransition> screenManager) {
        this.screenManager = screenManager;
    }

    public Viewport getViewport() {
        return viewport;
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
