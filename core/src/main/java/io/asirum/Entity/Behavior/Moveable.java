package io.asirum.Entity.Behavior;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.ArrayList;

// bergerak mengikuti polyline
// behavior bergerak mengikuti polyline
// kasus chrusher
public class Moveable {
    private ArrayList<Vector2> path;
    private int currentIndex = 0;
    private float speed = 2f; // meter per detik
    private boolean forward = true;

    public void update(Body body) {
        if (path == null || path.size() < 2) return;

        Vector2 currentPosition = body.getPosition();
        Vector2 targetPoint = path.get(currentIndex);

        //Kita ingin tahu arah dari posisi sekarang (currentPosition) ke titik tujuan (targetPoint).
        //
        //new Vector2(targetPoint) membuat salinan posisi target.
        //
        //.sub(currentPosition) artinya dikurangkan dengan posisi sekarang → hasilnya adalah vektor arah.
        //
        //Bayangkan kamu berdiri di (3, 2), dan ingin menuju ke (5, 6).
        //
        //(5, 6) - (3, 2) = (2, 4) → itu arah yang harus ditempuh.
        Vector2 direction = new Vector2(targetPoint).sub(currentPosition);
        // direction.len() = panjang dari vektor arah tadi → ini adalah jarak dari posisi sekarang ke target.
        //
        //Jadi kalau direction = (2, 4), maka panjangnya adalah √(2² + 4²) = √20 ≈ 4.47.
        float distance = direction.len();

        // jika jaraknya masih lebih besar dari 0.1
        if (distance < 0.1f) {

            // Sudah dekat dengan titik tujuan, lanjut ke titik berikutnya
            if (forward) {
                currentIndex++;
                if (currentIndex >= path.size()) {
                    currentIndex = path.size() - 2;
                    forward = false;
                }
            } else {
                currentIndex--;
                if (currentIndex < 0) {
                    currentIndex = 1;
                    forward = true;
                }
            }
        }

        // Hitung ulang arah ke target yang baru
        targetPoint = path.get(currentIndex);
        // (sub) Mengurangi direction dengan currentPosition.
        // .nor()
        //Menormalkan vektor arah, artinya mengubah panjangnya menjadi 1 unit, tapi arah tetap sama.
        // .scl(speed)
        //Mengalikan vektor arah yang sudah dinormalisasi dengan kecepatan.
        direction.set(targetPoint).sub(currentPosition).nor().scl(speed);

        body.setLinearVelocity(direction);
    }


    public void setPath(ArrayList<Vector2> path){
        this.path =     path;
    }

}
