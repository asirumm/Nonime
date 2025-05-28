package io.asirum.Box2d.Services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import io.asirum.Box2d.BaseBox2d;
import io.asirum.Box2d.Box2dVars;
import io.asirum.Box2d.Collision.PlayerContactListener;
import io.asirum.GameLogic.GamePlayManager;
import io.asirum.Service.Log;
import io.asirum.Util.CameraHelper;

import java.util.HashMap;

public class Box2dManager {
    private World world;

    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private TmxObjectReader tmxObjectReader;
    private EntityBox2d entity;
    private PlatformBox2d platform;
    private ObstacleBox2d obstacle;

    public Box2dManager(OrthographicCamera camera, TiledMap map, GamePlayManager playManager) {

        box2dInit();// inisialisasi world dan debug render

        world.setContactListener(new PlayerContactListener(playManager));

        entityInit();// inisialisasi emtity, platform dan obstacle

        tmxObjectReader = new TmxObjectReader(map);

        this.camera = camera;
    }

    // Mengurai data dari file TMX menjadi entitas Box2D
    public void parseTmxToBox2dEntities() {
        tmxObjectReader.initializeMapObjects(Box2dVars.TMX_OBJECTS_LAYERS);

        HashMap<String, BaseBox2d> box2dEntities = new HashMap<>();

        box2dEntities.put(Box2dVars.PLAYER_OBJECT, entity.getPlayer());
        box2dEntities.put(Box2dVars.PLAYER_SENSOR_OBJECT, entity.getPlayerSensor());
        box2dEntities.put(Box2dVars.KEY_SENSOR_OBJECT, entity.getKey());
        box2dEntities.put(Box2dVars.PORTAL_SENSOR_OBJECT, entity.getPortal());
        box2dEntities.put(Box2dVars.ONE_WAY_PLATFORM_OBJECT, platform.getOneWayPlatformBuilder());
        box2dEntities.put(Box2dVars.OBSTACLE_OBJECT, obstacle.getStalactite());

        // Platform statis diproses terpisah karena menggunakan pendekatan composite
        platform.getStaticPlatform().build(tmxObjectReader.getMap());

        tmxObjectReader.parseToBox2d(box2dEntities);
    }

    public void render(float delta){
        world.step(1 / 60f, 6, 2);

        entity.getKey().drawAnimation();
        entity.getPlayer().drawAnimation();

//        debugRenderer.render(world,camera.combined);

        // menghancurkan object yang perlu dihancurkan
        checkObjectToDestroy();

        /// camera mengikuti arah player
        CameraHelper.lerpCamera(camera,entity.getPlayer().getBody());
    }

    private void checkObjectToDestroy() {
        if(!entity.getPlayer().getToDestroy().isEmpty()){
            for (Body body : entity.getPlayer().getToDestroy()) {
                entity.getPlayer().getWorld().destroyBody(body);
            }
            entity.getPlayer().getToDestroy().clear();
        }
    }

    private void entityInit() {
        entity = new EntityBox2d(world);
        platform = new PlatformBox2d( world);
        obstacle = new ObstacleBox2d(world);
    }

    private void box2dInit() {
        world = new World(new Vector2(0,-9.8f),false);
        debugRenderer = new Box2DDebugRenderer();
    }

    public EntityBox2d getEntity() {
        return entity;
    }

    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        Log.debug(getClass().getName(), "[dispose]");
    }
}
