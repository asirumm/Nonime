package io.asirum.TmxMap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import io.asirum.Constant;
import io.asirum.Util.CameraHelper;

public class MapRenderer {
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    public MapRenderer(OrthographicCamera camera) {
        this.camera = camera;
    }

    public void loadMap(String mapPath) {
        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
        params.textureMinFilter = Texture.TextureFilter.Nearest;
        params.textureMagFilter = Texture.TextureFilter.Nearest;

        this.map = TmxHelper.getTiledMap(mapPath,params);
        this.mapRenderer = new OrthogonalTiledMapRenderer(this.map, 1.0f / Constant.UNIT_SCALE);
    }

    public void setBoundaryCamera() {
        MapProperties props = this.map.getProperties();
        int width = ((Integer) props.get("width", Integer.class)).intValue();
        int height = ((Integer) props.get("height", Integer.class)).intValue();
        int tileWidth = ((Integer) props.get("tilewidth", Integer.class)).intValue();
        int tileHeight = ((Integer) props.get("tileheight", Integer.class)).intValue();
        float totalWidth = ((width * tileWidth) * 1.0f) / Constant.UNIT_SCALE;
        float totalHeight = ((height * tileHeight) * 1.0f) / Constant.UNIT_SCALE;
        CameraHelper.boundaryCamera(this.camera, totalWidth, totalHeight);
    }

    public void render() {
        this.camera.update();
        this.mapRenderer.setView(this.camera);
        this.mapRenderer.render();
    }

    public void dispose() {
        this.mapRenderer.dispose();
        this.map.dispose();
    }
}
