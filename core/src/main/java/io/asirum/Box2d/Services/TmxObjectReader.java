package io.asirum.Box2d.Services;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import io.asirum.Box2d.BaseBox2d;
import io.asirum.Box2d.Box2dVars;
import io.asirum.Constant;

import io.asirum.Entity.Obstacle.PathMoveable;
import io.asirum.Entity.Obstacle.Crusher;
import io.asirum.Entity.Obstacle.CrusherBuilder;
import io.asirum.Service.Log;

import java.util.ArrayList;
import java.util.HashMap;

// membaca layer object dan parse ke box2d
public class TmxObjectReader {
    private TiledMap map;
    private MapObjects mapObjects;
    private ArrayList<PathMoveable> listPathMoveable = new ArrayList<>();

    public TmxObjectReader(TiledMap map) {
        this.map = map;
    }

    public void getMapObjectsFromLayers(String objectLayers){
        mapObjects = map.getLayers().get(objectLayers).getObjects();
    }

    /**
     * parsing dari object ke box2d
     */
    public void parseToBox2d(HashMap<String, BaseBox2d> entity){

        // apabila layer objects null
        if(mapObjects.get(0)==null){
            Log.warn(getClass().getCanonicalName(),"Layer tidak memiliki data object. Periksa kembali tilemap");
            throw new RuntimeException("Layer tidak memiliki object data, periksa kembali tile map");
        }

        for (MapObject object : mapObjects){

            Log.debug(getClass().getName(),"object tmx saat ini : %s",object.getName());

            // pembuat garis jalan untuk crusher
            if (object instanceof PolylineMapObject){
                Log.debug(getClass().getName(),"object polyline ditemukan ");

                polyLineShape(object);
            }

            // apbila pada entity terdapat nama sesuai dengan name tmx
            if(entity.containsKey(object.getName())){

                Log.debug(getClass().getName(),"object %s tmx berhasil di parse",object.getName());

                // kita ambil class child basebox2d dan build object
                entity.get(object.getName()).build(object);
            }
        }

        // menambahkan path way ke crsuher
        setPathToCrusher((CrusherBuilder) entity.get(Box2dVars.CRUSHER_OBJECT));
    }

    /**
     *  crusher sendiri merupakan bagian dari object
     * dan pathnya menggunakan polyline agar dapat memiliki jalur
     *  masing masing crusher.
     *
     *  Crusher dengan path diberikan variable nama yang sesuai
     *  pada crusher terdapat props path
     *  Ex : crusher ada props path = path1
     */
    public void setPathToCrusher(CrusherBuilder crusherBuilder){
        Log.debug(getClass().getName(),"memulai menambahkan path ke crusher");

        ArrayList<Crusher> listCrusher = crusherBuilder.getCrushers();

       for (int i=0;i<listCrusher.size();i++){
           String pathProps = listCrusher.get(i).getPathProps();

           for (PathMoveable pathMoveable: listPathMoveable){
               if (pathMoveable.getName().equals(pathProps)){
                   Log.debug(getClass().getName(),"ditemukan path sesuai crusher name props %s",pathProps);
                   listCrusher.get(i).setPath(pathMoveable.getPaths());
               }
           }

       }
    }

    /**
     * membuat path crusher menjadi box2d polyline
     * path diberi nama Ex : path1, path2 etc
     */
    public void polyLineShape(MapObject object){
        if (object instanceof PolylineMapObject){

            PolylineMapObject polylineObject = (PolylineMapObject) object;
            Polyline polyline = polylineObject.getPolyline();

            float[] vertices = polyline.getTransformedVertices();

            PathMoveable pathMoveable = new PathMoveable();
            pathMoveable.setName(object.getName());


            // float[] vertices = {x1, y1, x2, y2, x3, y3, ..., xn, yn};
            // vertices[0] = x1
            // vertices[1] = y1
            for (int i = 0; i < vertices.length; i +=1) {

                // vertices merupakan titik yang membentuk garis
                Vector2 point =
                    new Vector2(
                        // X
                        vertices[i]/Constant.UNIT_SCALE,
                        // Y
                        vertices[i + 1]/Constant.UNIT_SCALE);
                pathMoveable.addPath(point);
            }

            listPathMoveable.add(pathMoveable);
        }
    }

    public TiledMap getMap() {
        return map;
    }

}
