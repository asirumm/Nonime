package io.asirum.Box2d;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import io.asirum.Box2d.Collision.PlayerContactListener;
import io.asirum.Entity.Items.Checkpoint;
import io.asirum.Entity.Items.CheckpointBuilder;
import io.asirum.Entity.Items.Key;
import io.asirum.Entity.Items.Portal;
import io.asirum.Entity.Obstacle.Stalactite;
import io.asirum.Entity.Platform.SinglePlatform;
import io.asirum.Entity.Platform.StaticPlatform;
import io.asirum.Entity.Player.Player;
import io.asirum.Entity.Player.PlayerMovement;
import io.asirum.Entity.Player.PlayerSensor;
import io.asirum.GameLogic.PlayerFinishLogic;
import io.asirum.Service.Log;
import io.asirum.TmxMap.TmxHelper;
import io.asirum.Util.CameraHelper;

public class LoadBox2d {
    private World world;
    private Player player;
    private PlayerSensor playerSensor;
    private PlayerMovement playerMovement;
    private SinglePlatform singlePlatform;
    private Key key;
    private Portal portal;
    private Stalactite stalactite;
    private CheckpointBuilder checkpointBuilder;

    private StaticPlatform staticPlatform;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    public LoadBox2d(OrthographicCamera camera, PlayerFinishLogic playerFinishLogic){
        world = new World(new Vector2(0,-9.8f),false);
        debugRenderer = new Box2DDebugRenderer();

        player         = new Player(world);
        staticPlatform = new StaticPlatform(world);
        playerSensor   = new PlayerSensor(world,player);
        playerMovement = new PlayerMovement(player);
        key            = new Key(world);
        portal         = new Portal(world,key);
        singlePlatform = new SinglePlatform(world);
        stalactite     = new Stalactite(world);
        checkpointBuilder    = new CheckpointBuilder(world);

        world.setContactListener(new PlayerContactListener(playerFinishLogic));


        this.camera = camera;
    }

    public void render(){

        playerMovement.render();

        world.step(1 / 60f, 6, 2);

        // key hanya sebagai perantara mendapatkan
        // akses ke ToDestroy
        if(!key.getToDestroy().isEmpty()){
            for (Body body : key.getToDestroy()) {
                world.destroyBody(body);
            }
            key.getToDestroy().clear();
        }

        if (player.isPlayerNeedRespawn()){
            player.respawn();
            player.setPlayerNeedRespawn(false);
        };

        debugRenderer.render(world,camera.combined);

        CameraHelper.lerpCamera(camera,player.getBody());

        // TODO draw key
    }

    public void loadEntity(String map){
        TiledMap tiledMap = TmxHelper.getTiledMap(map);

        staticPlatform.build(tiledMap);

        MapObjects objects =
            tiledMap
                .getLayers()
                .get(Box2dHelper.ENTITIES_LAYER)
                .getObjects();

        for (MapObject object:objects){

            switch (object.getName()){
                case Box2dHelper.PLAYER_NAME:
                    Log.debug(getClass().getName(),">>> objects tmx "+ Box2dHelper.PLAYER_NAME + " ditemukan");
                    player.build(object);
                    break;
                case Box2dHelper.PLAYER_SENSOR_NAME:
                    Log.debug(getClass().getName(),">>> objects tmx "+ Box2dHelper.PLAYER_SENSOR_NAME + " ditemukan");
                    playerSensor.build(object);
                    break;
                case Box2dHelper.KEY_SENSOR_NAME:
                    Log.debug(getClass().getName(),">>> objects tmx "+ Box2dHelper.KEY_SENSOR_NAME + " ditemukan");
                    key.build(object);
                    break;
                case Box2dHelper.PORTAL_SENSOR_NAME:
                    Log.debug(getClass().getName(),">>> objects tmx "+ Box2dHelper.PORTAL_SENSOR_NAME + " ditemukan");
                    portal.build(object);
                    break;
                case Box2dHelper.SINGLE_PLATFORM_LAYER:
                    Log.debug(getClass().getName(),">>> objects tmx "+ Box2dHelper.SINGLE_PLATFORM_LAYER + " ditemukan");
                    singlePlatform.build(object);
                    break;
                case Box2dHelper.OBSTACLE_LAYER:
                    Log.debug(getClass().getName(),">>> objects tmx "+ Box2dHelper.OBSTACLE_LAYER + " ditemukan");
                    stalactite.build(object);
                    break;
                case Box2dHelper.CHECKPOINT_LAYER:
                    Log.debug(getClass().getName(),">>> objects tmx "+ Box2dHelper.CHECKPOINT_LAYER + " ditemukan");
                    checkpointBuilder.build(object);
                    break;
            }
        }
    }


    public void dispose(){
        world.dispose();
        debugRenderer.dispose();
    }

    public Player getPlayer() {
        return player;
    }

    public Key getKey() {
        return key;
    }

    public Portal getPortal() {
        return portal;
    }
}
