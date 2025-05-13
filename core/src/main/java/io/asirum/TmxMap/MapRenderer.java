package io.asirum.TmxMap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import io.asirum.Constant;
import io.asirum.Service.ApplicationContext;

public class MapRenderer {
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private ApplicationContext context;

    public MapRenderer(){
        context = ApplicationContext.getInstance();

        camera = context.getCamera();
    }

    public void loadMap(String mapPath){
        this.map = TmxHelper.getTiledMap(mapPath);

        mapRenderer = new OrthogonalTiledMapRenderer(this.map, Constant.UNIT_SCALE);
    }

    public void render(){
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void dispose(){
        mapRenderer.dispose();
        map.dispose();
    }
}
