package io.asirum.Widget.Window;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.asirum.Constant;
import io.asirum.Util.ButtonHelper;
import io.asirum.Util.StageHelper;

public class Abouts extends BaseWindow {
    private ScrollPane scrollPane;

    private final String text =
        "made with : libGDX \n" +
            "font : upheavtt, monogram, m5x7 \n" +
            "assets : by me with aseprite\n"+
            "music : a big world by one people entertaiment\n";

    public Abouts(Skin skin){
        super("about", skin);

        // Content label
        Label aboutLabel = new Label(text, skin);
        aboutLabel.setWrap(true);

        // ScrollPane setup
        scrollPane = new ScrollPane(aboutLabel);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        scrollPane.setForceScroll(false, true);

        // menambahkan table ke scrollpane
        addChildToTable(scrollPane);
//        mainTable.add(scrollPane).expand().fill().row();

        StageHelper.debugStage(true,scrollPane,mainTable,this);

        this.setVisible(false);
    }
}
