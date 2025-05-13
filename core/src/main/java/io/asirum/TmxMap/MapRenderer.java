package io.asirum.TmxMap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import io.asirum.Constant;
import io.asirum.Util.CameraHelper;

public class MapRenderer {
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    public MapRenderer(OrthographicCamera camera){
        this.camera = camera;
    }

    public void loadMap(String mapPath){
        this.map = TmxHelper.getTiledMap(mapPath);

        mapRenderer = new OrthogonalTiledMapRenderer(this.map, 1/ Constant.UNIT_SCALE);
    }

    public void setBoundary(){
        MapProperties props = map.getProperties();
        int width  = props.get("width",Integer.class);
        int height = props.get("height",Integer.class);
        int tileWidth  = props.get("tilewidth", Integer.class);
        int tileHeight = props.get("tileheight",Integer.class);

        float totalWidth  = (float) width * tileWidth * 1/Constant.UNIT_SCALE;
        float totalHeight = (float) height * tileHeight * 1/Constant.UNIT_SCALE;

        CameraHelper.boundaryCamera(camera,totalWidth,totalHeight);
    }

    public void render(){
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void dispose(){
        mapRenderer.dispose();
        map.dispose();
    }
}
