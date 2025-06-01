package io.asirum.Screen.HomeMenu;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import io.asirum.Util.StageHelper;
import io.asirum.Widget.StyleVars;
import io.asirum.Widget.Window.BaseWindow;

public class AboutWindow extends BaseWindow {
    private ScrollPane scrollPane;

    private final String text =
        "game info : \n"+
            "> every 3 hours energy will added \n"+
        "made with : libGDX \n" +
            "font : alagard by Hewett Tsoi \n" +
            "assets : by me with aseprite\n"+
            "music : a big world by one people entertaiment\n";

    public AboutWindow(Skin skin){
        super("information", skin);


        getTitleLabel().setAlignment(Align.center);
        getTitleLabel().setStyle(skin.get(StyleVars.TITLE_LIGHT_LABEL, Label.LabelStyle.class));

        // Content label
        Label aboutLabel = new Label(text, skin);
        aboutLabel.setWrap(true);

        // ScrollPane setup
        scrollPane = new ScrollPane(aboutLabel);

        scrollPane.setForceScroll(false, true);

        // menambahkan table ke scrollpane
        contentTable.add(scrollPane).top().expand().fill();

        StageHelper.debugStage(false,scrollPane,mainTable,this);
        mainTable.padTop(15);

        this.setVisible(false);
    }
}
