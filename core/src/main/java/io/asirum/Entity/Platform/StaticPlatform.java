package io.asirum.Entity.Platform;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.asirum.Box2d.BodyBuilder;
import io.asirum.Box2d.Box2dVars;
import io.asirum.Box2d.FixtureFilter;
import io.asirum.Constant;

public class StaticPlatform extends BasePlatform {

    public StaticPlatform(World world) {
        super(world);
    }

    @Override
    public void build(TiledMap tiledMap) {
        MapLayer layer = tiledMap.getLayers().get(Box2dVars.PLATFORM_LAYER);

        if(layer instanceof TiledMapTileLayer){

            // convert
            TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;

            // ukuran 1 tile pada tmx
            // tilewidth="16" tileheight="16"
            float tileWidth = tileLayer.getTileWidth()/ Constant.UNIT_SCALE;
            float tileHeight = tileLayer.getTileHeight()/ Constant.UNIT_SCALE;

            // Buat 1 body statis
            BodyBuilder bodyBuilder = new BodyBuilder()
                .type(BodyDef.BodyType.StaticBody)
                .fixRotation(true);

            body = world.createBody(bodyBuilder.build());

            // kita akan buat composite collision horizontal
            // maka dari itu kita iterasi dari height/col
            // COLUMN
            for (int y = 0; y < tileLayer.getHeight(); y++) {
                // koordinat 0,0 (row,col) tmx
                // variable ini untuk penanda start dimana
                // cell memiliki data tile/solid/platform
                int tileSolidStartAt = -1;

                // ROW
                for (int x = 0; x <= tileLayer.getWidth(); x++) {

                    // penanda bahwa cell row ada isinya solid/platform
                    boolean cellIsSolid = false;

                    if (x < tileLayer.getWidth()) {
                        // pengecekan cell pada row dan col
                        TiledMapTileLayer.Cell cell = tileLayer.getCell(x, y);

                        // apabila pada current cell kosong
                        // dan apabila pada cell ada tile/gambar platform/ data platform
                        // maka kita beri tanda bahwa cell current ada isinya
                        if (cell!=null && cell.getTile() !=null){
                            cellIsSolid = true;
                        }
                    }

                    // kita cek apabila solid/platform sudah ditemukan
                    // pada cell maka kita berikan nilai
                    // ke tileSolidStart, mendandakan awal untuk
                    // menggambar/ukuran box2d
                    if(cellIsSolid){
                        if (tileSolidStartAt == -1){
                            tileSolidStartAt = x;
                        }else {
                            continue;
                        }

                    // apabila kita sudah mengetahui titik start
                    // dan cellIsSolid sudah false, artinya kita
                    // menemukan cell terakhir yang ada tile/platformya
                    // saatnya membuat box2d
                    }else {

                        // memastikan bahwa kita memiliki titik start
                        if (tileSolidStartAt != -1) {

                            // hitung berapa cell yang memiliki solid/platform
                            int lengthSolidCell = x - tileSolidStartAt;

                            // ukuran shape yang akan dibuat box2d
                            // ada berapa cell lalu kita kali dengan
                            // ukuran tile (pada kasus 16x16)
                            float shapeWidth = lengthSolidCell * tileWidth;
                            float shapeHeight = tileHeight;

                            // mencari titik tengah shape
                            // tileSolid titik start cell memiliki solid/platform
                            // lengthSolid titik akhir yang memiliki solid/platform
                            // pembagi 2f untuk mendapatkan titik tengah * tilewidth
                            // ukuran tile/platform (kasus 16x16)
                            float centerX = (tileSolidStartAt + lengthSolidCell / 2f) * tileWidth;
                            //titik y atau kolom mendapatkan nilai tengah
                            // 0.5??? akupun tak tahu
                            float centerY = (y + 0.5f) * tileHeight;

                            PolygonShape shape = new PolygonShape();
                            shape.setAsBox(
                                shapeWidth / 2f,
                                shapeHeight / 2f,
                                // titik pusat
                                new Vector2(centerX, centerY), 0f);


                            Fixture fixture = body.createFixture(shape, 1.0f);
                            fixture.setFilterData(FixtureFilter.filterPlatform());
                            fixture.setUserData(Box2dVars.STATIC_PLATFORM_FIXTURE);
                            shape.dispose();

                            // kita kembalikan ke awal
                            // indikasi cell kosong/tidak ada tile/platform
                            tileSolidStartAt = -1;
                        }

                    }

                }

            }
        }
    }
}
