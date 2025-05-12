package io.asirum.Widget.Window;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.asirum.Constant;
import io.asirum.Util.ButtonHelper;
import io.asirum.Util.StageHelper;

public class Abouts extends Window {
    private ScrollPane scrollPane;

    private final String text =
        "made with : libGDX \n" +
            "font : upheavtt, monogram, m5x7 \n" +
            "assets : by me with aseprite\n"+
            "music : a big world by one people entertaiment\n";

    public Abouts(Skin skin){
        super("about", skin);
        // agar rapih titlenya
        this.padTop(getTitleLabel().getPrefHeight()+13);
        this.padLeft(getTitleLabel().getPrefHeight());

        // Window properties
        this.setModal(true);
        this.setMovable(false);
        this.setResizable(false);

        // Set up the main container table
        Table mainTable = new Table();

        // Content label
        Label aboutLabel = new Label(text, skin);
        aboutLabel.setWrap(true);

        // ScrollPane setup
        scrollPane = new ScrollPane(aboutLabel);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);

        scrollPane.setForceScroll(false, true);

        // menambahkan table ke scrollpane
        mainTable.add(scrollPane).expand().fill().row();

        // Close button
        Button closeButton = ButtonHelper.closeButton(this);

        // Add close button to main table with specific alignment
        mainTable.add(closeButton).right();

        // menambahkan table ke window
        this.add(mainTable).expand().fill();

        // window size
        this.setSize(Constant.VIRTUAL_WIDTH - 10, Constant.VIRTUAL_HEIGHT - 10);

        // Center the window
        this.setPosition(
            (Constant.VIRTUAL_WIDTH - this.getWidth()) / 2,
            (Constant.VIRTUAL_HEIGHT - this.getHeight()) / 2
        );

        // Debug
//            scrollPane.setDebug(true);
//            closeButton.setDebug(true);
//            this.setDebug(true);
//            mainTable.setDebug(true);
        StageHelper.debugStage(true,scrollPane,mainTable,this);

        this.setVisible(false);
    }
}
