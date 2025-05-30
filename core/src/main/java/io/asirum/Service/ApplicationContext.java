package io.asirum.Service;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.ScreenManager;
import de.eskalon.commons.screen.transition.ScreenTransition;
import io.asirum.Constant;
import io.asirum.SchemaObject.Payload;
import io.asirum.SchemaObject.UserData;
import io.asirum.Screen.LevelMenu.RegionContent;
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
    private UserEnergyManager userEnergyManager;
    private Array<RegionContent> regionContents;
    // menahan level menu konten, agar tidak lama selalu load

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
            Log.error(ApplicationContext.class.getName(),"error membuat instance, app context tidak memiliki instance",e);
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
        Log.debug(getClass().getName(),"telah di inject instance payload");
        this.payload = payload;
    }

    public void setScreenManager(ScreenManager<ManagedScreen, ScreenTransition> screenManager) {
        Log.debug(getClass().getName(),"telah di inject instance screen manager");
        this.screenManager = screenManager;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        Log.debug(getClass().getName(),"telah di inject instance user data");
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

    public UserEnergyManager getUserEnergyManager() {
        return userEnergyManager;
    }

    public void setUserEnergyManager(UserEnergyManager userEnergyManager) {
        Log.debug(getClass().getName(),"telah di inject instance user energy manager");
        this.userEnergyManager = userEnergyManager;
    }

    public Array<RegionContent> getRegionContents() {
        return regionContents;
    }

    public void setRegionContents(Array<RegionContent> regionContents) {
        Log.debug(getClass().getName(),"telah di inject instance region contents");
        this.regionContents = regionContents;
    }
}
