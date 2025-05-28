package io.asirum.Entity.Platform;

import com.badlogic.gdx.physics.box2d.Body;

public class OneWayPlatform {
    private Body body;
    private float height;

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
