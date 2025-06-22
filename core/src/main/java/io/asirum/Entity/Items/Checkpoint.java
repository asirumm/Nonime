package io.asirum.Entity.Items;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;


public class Checkpoint {
    private Vector2 position;
    private Body body;

    public Checkpoint( Vector2 position, Body body) {
         this.position = position;
        this.body = body;
    }

    public Vector2 getPosition() {
        return position;
    }
}
