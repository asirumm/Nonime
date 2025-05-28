package io.asirum.Service;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.ScreenManager;
import de.eskalon.commons.screen.transition.ScreenTransition;
import io.asirum.SchemaObject.Payload;
import io.asirum.SchemaObject.UserData;
import io.asirum.Util.CameraHelper;

public class ApplicationContext {
    private static ApplicationContext instance;

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private GameAssets gameAssets;
    private AssetLoader assetLoader;
    private ScreenManager<ManagedScreen, ScreenTransition> screenManager;
    private Payload payload;
    private UserData userData;

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

    public Payload getPayloadGame() {
        return payload;
    }

    public void setPayload(Payload payload) {
        Log.info(getClass().getName(),">>>>> success add payload");
        this.payload = payload;
    }

    public void setScreenManager(ScreenManager<ManagedScreen, ScreenTransition> screenManager) {
        Log.info(getClass().getName(),">>>>> success add screen manager");
        this.screenManager = screenManager;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        Log.info(getClass().getName(),">>>>> success add user data");
        this.userData = userData;
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

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
}
