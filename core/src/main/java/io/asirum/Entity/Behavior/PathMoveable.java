package io.asirum.Entity.Behavior;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class PathMoveable {
    private String name ;
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
