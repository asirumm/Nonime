package io.asirum.Entity.Behavior;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Run {
    // ======= Konstanta waktu fisika =======
    private static final float FIXED_DELTA_TIME = 1f / 60f;
    private float maxSpeed;
    private float entityAcceleration;
    private float entityDeacceleration;

    private float accelInAir = 0.5f;
    private float deccelInAir = 0.3f;

    private boolean doConserveMomentum = true; // Apakah momentum akan dipertahankan saat di udara

    public Run(float maxSpeed, float entityAcceleration, float entityDeacceleration) {
        this.maxSpeed = maxSpeed;
        this.entityAcceleration = entityAcceleration;
        this.entityDeacceleration = entityDeacceleration;
    }

    /**
     * saya mendapatkan rumus ini dari tutorial youtube untuk unity
     * tetapi saya minta bantu chatgpt untuk menterjemahkannya
     *
     * Ini memungkinkan user bisa membuat arah jump
     * ketika user klik jump+ right/left
     */
    public void run(boolean moveLeft, boolean moveRight, Body body,boolean onGround) {

        // menentukan arah kekiri atau kanan
        float directionX = 0f;
        if (moveLeft) directionX -= 1f;
        if (moveRight) directionX += 1f;


        // kekuatan kecepatan(acceleration) dan perlambatan (deacceleration)
        float acceleration   = (1f / FIXED_DELTA_TIME) * entityAcceleration / maxSpeed;
        float deacceleration = (1f / FIXED_DELTA_TIME) * entityDeacceleration / maxSpeed;

        // Kecepatan target berdasarkan arah input
        float targetHorizontalSpeed = directionX * maxSpeed;
        float currentHorizontalSpeed = body.getLinearVelocity().x;
        float speedDifference = targetHorizontalSpeed - currentHorizontalSpeed;

        // Pilih akselerasi atau deselerasi tergantung situasi
        float appliedAcceleration;
        if (Math.abs(targetHorizontalSpeed) > 0.01f) {
            appliedAcceleration = onGround ? acceleration : acceleration * accelInAir;
        } else {
            appliedAcceleration = onGround ? deacceleration : deacceleration * deccelInAir;
        }

        // Jika sedang di udara dan meluncur lebih cepat dari target kecepatan, jangan rem
        if (doConserveMomentum &&
            Math.abs(currentHorizontalSpeed) > Math.abs(targetHorizontalSpeed) &&
            Math.signum(currentHorizontalSpeed) == Math.signum(targetHorizontalSpeed) &&
            Math.abs(targetHorizontalSpeed) > 0.01f &&
            !onGround) {
            appliedAcceleration = 0f;
        }

        // Hitung gaya dan terapkan
        float finalMovementForce = speedDifference * appliedAcceleration;
        body.applyForceToCenter(new Vector2(finalMovementForce, 0f), true);
    }

}
