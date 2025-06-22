package io.asirum.Entity.Obstacle;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.asirum.Entity.Behavior.Moveable;

import java.util.ArrayList;

public class Crusher{
    private String pathProps;
    private Moveable moveable;
    private Vector2 size;
    private Body body;

    public Crusher(Body body) {
        moveable = new Moveable();
        this.body =body;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public void setPathProps(String pathProps) {
        this.pathProps = pathProps;
    }

    public void runBehavior(){
        moveable.update(body);
    }

    public void setPath(ArrayList<Vector2> path){
        moveable.setPath(path);
    }

    public String getPathProps() {
        return pathProps;
    }
}
