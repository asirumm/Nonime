package io.asirum.Entity.Behavior;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Jump {
    private float jumpForce;
    private float maxJump;

    public Jump(float jumpForce,float maxJump) {
        this.jumpForce = jumpForce;
        this.maxJump   = maxJump;
    }

    public void doJump(Body body) {
        // Ambil kecepatan vertikal sekarang
        float verticalVelocity = body.getLinearVelocity().y;

        // Hitung gaya lompat
        float force = jumpForce;

        // menerapkan jatuh cepat saat keadaan player jatuh
        if (verticalVelocity < 0) {
            force -= verticalVelocity;
        }

        // menerapkan maks jump
        force = Math.min(force, maxJump);

        body.applyLinearImpulse(new Vector2(0, force), body.getWorldCenter(), true);
    }
}
