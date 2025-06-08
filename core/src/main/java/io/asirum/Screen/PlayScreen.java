package io.asirum.Screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import de.eskalon.commons.screen.ManagedScreen;
import io.asirum.Box2d.Services.Box2dManager;
import io.asirum.Entity.Player.Player;
import io.asirum.Entity.Player.PlayerMovement;
import io.asirum.EventListener.DesktopInput;
import io.asirum.EventListener.InputManager;
import io.asirum.EventListener.InputState;
import io.asirum.EventListener.MobileInput;
import io.asirum.GameLogic.GamePlayManager;
import io.asirum.SchemaObject.GameLevel;
import io.asirum.SchemaObject.Region;
import io.asirum.Screen.PlayMenu.WidgetController;
import io.asirum.Service.ApplicationContext;
import io.asirum.Service.Log;
import io.asirum.TmxMap.MapRenderer;
import io.asirum.TmxMap.TmxHelper;
import io.asirum.Util.AudioHelper;
import io.asirum.Util.CameraHelper;

public class PlayScreen  extends ManagedScreen{
    public static boolean paused = false;

    private OrthographicCamera camera;
    private ApplicationContext context;

    private GamePlayManager gamePlayManager;
    private InputManager inputManager;
    private Box2dManager box2dManager;

    private InputState inputState;
    private MapRenderer mapRenderer;
    private PlayerMovement playerMovement;
    private WidgetController widgetController;

    public PlayScreen(GameLevel gameLevel,Region region) {
        Log.debug(getClass().getName(), "[berhasil switch screen]");

        Log.info(getClass().getName(), "user bermain di region %s level %s",region.getName(),gameLevel.getLevel());

        AudioHelper.stopMusic();

        this.context = ApplicationContext.getInstance();

        gamePlayManager = new GamePlayManager(region,gameLevel);

        cameraBox2dConfiguration(); // konfigurasi kamera untuk box2d

        box2dManagerConfig(gameLevel);// konfigurasi box2dmanager
        Player player = box2dManager.getEntity().getPlayer();

        gamePlayManager.start(player,region.getCost());

        mapRenderInit(gameLevel);// inisialisasi map render

        inputManagerConfig(player);// konfigurasi kontroller

        this.playerMovement = new PlayerMovement(player, this.inputManager, this.inputState);
    }

    @Override
    public void render(float delta) {
        // membatasi kamera agar tidak keluar area tmx
        this.mapRenderer.setBoundaryCamera();

        // map
        this.mapRenderer.render();

        // ketika pause
        if (!paused) {
            this.playerMovement.run();
        }

        // box2d
        this.box2dManager.render(delta);

        // controller
        this.widgetController.render();

        // logika game
        gamePlayManager.play(delta);

    }

    /**
     * membuat instance box2d dan melakukan proses parsing ke box2d
     */
    private void box2dManagerConfig(GameLevel gameLevel) {
        Log.debug(getClass().getCanonicalName(),"inisialisasi Box2dManager");

        TiledMap tiledMap = TmxHelper.getTiledMap(gameLevel.getMap());

        box2dManager = new Box2dManager(camera, tiledMap,gamePlayManager);
        box2dManager.parseTmxToBox2dEntities();
    }

    /**
     * konfigurasi widgetcontroller
     * apakah player menggunakan device desktop atau mobile
     */
    private void inputManagerConfig(Player player) {
        this.widgetController = new WidgetController(player);

        this.inputState = new InputState();

        // memeriksa apakah user menggunakan desktop atau mobile
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            this.inputManager = new DesktopInput(this.inputState);
            this.widgetController.buildDesktopWidget();
            Log.debug(getClass().getCanonicalName(),"user menggunakan desktop");

        } else {
            this.inputManager = new MobileInput(this.inputState);
            this.widgetController.buildMobileWidget((MobileInput) this.inputManager);

            Log.debug(getClass().getCanonicalName(),"user menggunakan mobile device");

        }
    }

    /**
     * membuat instance map render
     * meload map dengan map render
     */
    private void mapRenderInit(GameLevel gameLevel) {
        Log.debug(getClass().getCanonicalName(),"memulai load map");
        this.mapRenderer = new MapRenderer(this.camera);
        this.mapRenderer.loadMap(gameLevel.getMap());
    }

    /**
     * konfigurasi kamera untuk box2d beserta zoom
     */
    private void cameraBox2dConfiguration() {
        Log.debug(getClass().getName(),"start configurasi kamera untuk box2d");
        this.camera = this.context.getCamera();

        CameraHelper.cameraAndViewportForBox2d(this.context);

        this.camera.zoom += 0.1f;
    }

    @Override
    public void resize(int width, int height) {
        this.widgetController.resize(width, height);
        CameraHelper.resizer(width, height);
    }

    @Override
    public void dispose() {
        this.mapRenderer.dispose();
        this.widgetController.dispose();
        box2dManager.dispose();
        Log.debug(getClass().getName(), "[dispose]");
    }
}
