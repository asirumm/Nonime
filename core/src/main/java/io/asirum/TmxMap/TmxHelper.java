package io.asirum.TmxMap;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
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

    public static MapObjects getMapObjects(TiledMap map,String layer){
        return map.getLayers().get(layer).getObjects();
    }

}
