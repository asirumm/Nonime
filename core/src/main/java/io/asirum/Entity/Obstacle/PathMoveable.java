package io.asirum.Entity.Obstacle;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Representasi polyline path pada tiled tmx
 * digunakan untuk jalur jalan crusher
 */
public class PathMoveable {
    private String name ;
    // setiap path memiliki nama object berbeda, untuk identifikasi milik
    // crusher/object siapa. Pada crusher akan diberikan props nilai
    // nama path
    private ArrayList<Vector2> paths=new ArrayList<>();

    public void addPath(Vector2 path){
        paths.add(path);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Vector2> getPaths() {
        return paths;
    }

    public void setPaths(ArrayList<Vector2> paths) {
        this.paths = paths;
    }
}
