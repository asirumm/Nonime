package io.asirum.Screen.LevelMenu;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import io.asirum.Constant;

public class LevelScrollPane {
    private Table tableContainer;// table penampung konten
    private Skin skin;
    private ScrollPane scrollPane;

    // dilakukan pengurangan agar ukuran tidak memenuhi layar
    private int widthScrollPane = Constant.VIRTUAL_WIDTH - 60;
    private int heightScrollPane = Constant.VIRTUAL_HEIGHT - 20;

    private Array<RegionContent> regionContents;

    public LevelScrollPane(Skin widgetSkin, Array<RegionContent> contents){
        tableContainer = new Table();
        this.regionContents = contents;
        skin = widgetSkin;
    }

    private void buildContent(){
        tableContainer.clear();

        // menambahkan table A,B dan seterusnya
        for (RegionContent content:regionContents){
            tableContainer
                .add(content.getContent())

                // penyesuaian ukuran agar presisi
                .height(heightScrollPane - 16)
                .width(widthScrollPane - 20)
                .padLeft(10)
                .padRight(10);
        }
    }

    public void build(){

        // taro table container ke scrollpane
        scrollPane     = new ScrollPane(tableContainer,skin);

        // tanpa efek fade
        scrollPane.setupFadeScrollBars(0, 0);
        // Jadi jika konten muat di ScrollPane, tetap bisa di scroll horizontal, tapi vertical scrolling dimatikan
        scrollPane.setForceScroll(true, false);
        // tanpa efek bounce saat bergeser
        scrollPane.setOverscroll(false, false);
        // scroll horizontal aktif
        scrollPane.setScrollingDisabled(false, true);
        scrollPane.setScrollbarsOnTop(true);

        scrollPane.setSize(widthScrollPane, heightScrollPane);

        // Atur posisi supaya titik tengah ScrollPane sejajar dengan tengah layar
        scrollPane.setPosition(
            (Constant.VIRTUAL_WIDTH - widthScrollPane) / 2f,
            (Constant.VIRTUAL_HEIGHT - heightScrollPane) / 2f
        );

        //Mengatur posisi awal scroll ke pojok kiri atas (0,0)
        scrollPane.scrollTo(0, 0, 0, 0);

        buildContent();

    }


    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public Table getContainer() {
        return tableContainer;
    }
}
