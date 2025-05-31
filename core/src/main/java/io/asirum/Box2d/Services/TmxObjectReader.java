package io.asirum.Box2d.Services;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import io.asirum.Box2d.BaseBox2d;
import io.asirum.Service.Log;

import java.util.HashMap;

// membaca layer object dan parse ke box2d
public class TmxObjectReader {
    private TiledMap map;
    private MapObjects mapObjects;

    public TmxObjectReader(TiledMap map) {
        this.map = map;
    }

    public void getMapObjectsFromLayers(String objectLayers){
        mapObjects = map.getLayers().get(objectLayers).getObjects();
    }

    /**
     * parsing dari object ke box2d
     */
    public void parseToBox2d(HashMap<String, BaseBox2d> data){

        for (MapObject object : mapObjects){
            if(data.containsKey(object.getName())){

                Log.debug(getClass().getName(),"object %s tmx berhasil di parse",object.getName());

                data.get(object.getName()).build(object);
            }
        }
    }

    public TiledMap getMap() {
        return map;
    }

}
