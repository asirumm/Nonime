package io.asirum.Entity.Platform;

import com.badlogic.gdx.physics.box2d.Body;

public class OneWayPlatform {
    private Body body;
    // JANGAN DIHAPUS BERGUNA UNTUK ONE WAY
    private float height;
    private float width;

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
