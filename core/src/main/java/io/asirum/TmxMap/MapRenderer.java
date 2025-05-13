package io.asirum.TmxMap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import io.asirum.Constant;

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
