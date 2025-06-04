package io.asirum.Screen.HomeMenu;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import io.asirum.Util.StageHelper;
import io.asirum.Widget.StyleVars;
import io.asirum.Widget.Window.BaseWindow;

import javax.swing.*;

public class AboutWindow extends BaseWindow {
    private ScrollPane scrollPane;

    private final String text =
        "framework - libGDX\n"+"font - algard by Hawett Tsoi";

    public AboutWindow(Skin skin){
        super( skin);
        withCloseButton();

        Label titleLabel = new Label("credits",skin,StyleVars.TITLE_UNDERLINE);
        setTitleLabel(titleLabel);

        // Content label
        Label aboutLabel = new Label(text, skin);
        aboutLabel.setWrap(true);
        aboutLabel.setAlignment(Align.topLeft);

        // ScrollPane setup
        scrollPane = new ScrollPane(aboutLabel);

        scrollPane.setForceScroll(false, true);

        // menambahkan table ke scrollpane
        content.add(scrollPane).top().expand().fill();

        StageHelper.debugStage(false,scrollPane,this);


        this.setVisible(false);
    }
}
