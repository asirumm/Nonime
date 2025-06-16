package io.asirum.Entity.Animation;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import io.asirum.Service.Log;

public class AnimationHelper {
    /**
     * Membuat animasi dari atlas, anda tidak perlu memberikan
     * jumlah frame pada atlas.
     *
     * - anda dapat menggunakan animation state untuk membantu
     * prefix penamaan
     */
    public static Array<TextureRegion> createAnimationFromAtlas(String prefix, TextureAtlas atlas) {
        Array<TextureRegion> animationFrames = new Array<>();

        // Coba cari frame dengan index (prefix1, prefix2, ...)
        for (int i = 1; ; i++) {
            TextureRegion region = atlas.findRegion(prefix + i);
            if (region == null) {
                // Jika tidak ditemukan apapun sejak awal (i == 1), coba cari prefix saja (tanpa angka)
                if (i == 1) {
                    TextureRegion singleRegion = atlas.findRegion(prefix);
                    if (singleRegion != null) {
                        animationFrames.add(singleRegion);
                    }
                }
                break;
            }
            animationFrames.add(region);
        }

        // Validasi hasil
        if (animationFrames.isEmpty()) {
            Log.warn(AnimationHelper.class.getCanonicalName(),
                "Gambar dengan prefix '%s' tidak ditemukan pada atlas", prefix);
            throw new RuntimeException("Gagal memuat animasi dari atlas. Periksa kembali nama prefix pada atlas.");
        }

        return animationFrames;
    }


    /**
     * membalik gambar secara horizontal maupun vertikal
     * berdasarkan parameter
     */
    public static Array<TextureRegion> flipTexture(Array<TextureRegion> frames, boolean flipHorizontal){
        Array<TextureRegion> flippedFrames = new Array<>();

        if(flipHorizontal){
            for (TextureRegion currentTexture : frames) {

                // jangan direferensikan, nanti gambar utama malah di flip
                // jadi biarkan buat objek baru
                TextureRegion flipped = new TextureRegion(currentTexture);
                flipped.flip(true, false);
                flippedFrames.add(flipped);
            }
        }else {
            for (TextureRegion currentTexture : frames) {

                // jangan direferensikan, nanti gambar utama malah di flip
                // jadi biarkan buat objek baru
                TextureRegion flipped = new TextureRegion(currentTexture);
                flipped.flip(false, true);
                flippedFrames.add(flipped);
            }
        }


        return flippedFrames;
    }

    public static TextureRegion flipTexture(TextureRegion textureRegion,boolean fliHorizontal){
        TextureRegion temp = new TextureRegion(textureRegion);
        if (fliHorizontal){
            temp.flip(true,false);
        }else {
            temp.flip(false,true);
        }
        return temp;
    }
}
