package io.asirum.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import de.eskalon.commons.screen.ManagedScreen;
import io.asirum.Box2d.LoadBox2d;
import io.asirum.EventListener.DesktopInput;
import io.asirum.GameLogic.PlayerEnergyLogic;
import io.asirum.GameLogic.PlayerFinishLogic;
import io.asirum.SchemaObject.Payload;
import io.asirum.SchemaObject.UserData;
import io.asirum.Service.ApplicationContext;
import io.asirum.Service.Log;
import io.asirum.Service.PayloadJsonManager;
import io.asirum.Service.PreferencesUserDataManager;
import io.asirum.TmxMap.MapRenderer;
import io.asirum.Util.CameraHelper;

public class PlayScreen extends ManagedScreen {
    private MapRenderer mapRenderer;
    private LoadBox2d loadBox2d;
    private ApplicationContext context;

    private PlayerFinishLogic playerFinishLogic;
    private PlayerEnergyLogic playerEnergyLogic;


    private OrthographicCamera camera;

    public PlayScreen (){
        Log.debug(getClass().getName(),"[berhasil switch screen]");

        context     = ApplicationContext.getInstance();
        camera = context.getCamera();

        CameraHelper.cameraAndViewportForBox2d(context);
        camera.zoom = 1.5f;

        playerFinishLogic = new PlayerFinishLogic();

        loadBox2d   = new LoadBox2d(camera,playerFinishLogic);
        mapRenderer = new MapRenderer(camera);
        loadBox2d.loadEntity("manifest/map/map.tmx");
        mapRenderer.loadMap("manifest/map/map.tmx");


    }

    @Override
    public void render(float delta) {
        DesktopInput.playerMovementController();

        mapRenderer.setBoundary();

        mapRenderer.render();
        loadBox2d.render();

    }

    @Override
    public void resize(int width, int height) {
        CameraHelper.resizer(width,height);
    }

    @Override
    public void dispose() {
        loadBox2d.dispose();
        mapRenderer.dispose();
    }
}
