package io.asirum.TmxMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.AtlasTmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;


public class TmxHelper {
    public static MapObjects getMapObjects(String tileName, TiledMap map){
        MapObjects objects = map.getLayers().get(tileName).getObjects();
        return objects;
    }

    /**
     * Konversi rectangleMapObject ke rectangle
     */
    public static Rectangle convertRectangleMapObject(MapObject object){
        RectangleMapObject rectObject = (RectangleMapObject) object;
        Rectangle rect = rectObject.getRectangle();
        return rect;
    }

    public static TiledMap getTiledMap(String map){
        return new TmxMapLoader().load(map);
    }

    public static TiledMap getTiledMap(String map, TmxMapLoader.Parameters param){
        return new TmxMapLoader().load(map,param);
    }

    public static TiledMap getTiledMapFromAtlas(String map){
        AtlasTmxMapLoader.AtlasTiledMapLoaderParameters params = new AtlasTmxMapLoader.AtlasTiledMapLoaderParameters();
        params.generateMipMaps = false;
        params.convertObjectToTileSpace = false;
        params.textureMinFilter = Texture.TextureFilter.Nearest;
        params.textureMagFilter = Texture.TextureFilter.Nearest;

        TiledMap tiledMap =  new AtlasTmxMapLoader().load(map);

        return tiledMap;
    }

    public static MapObjects getMapObjects(TiledMap map,String layer){
        return map.getLayers().get(layer).getObjects();
    }

}
