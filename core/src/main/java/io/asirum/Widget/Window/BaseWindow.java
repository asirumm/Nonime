package io.asirum.Widget.Window;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.asirum.Constant;
import io.asirum.Util.ButtonBuilder;
import io.asirum.Widget.StyleVars;

public class BaseWindow extends Window {

    protected Table mainTable;
    protected Table contentTable;
    protected Button closeButton;

    public BaseWindow(String title, Skin skin) {
        super(title, skin);

        // agar rapih titlenya
//        getTitleTable().padTop(getTitleLabel().getHeight()+getTitleTable().getPrefHeight());

        // Window properties
        this.setModal(true);
        this.setMovable(false);
        this.setResizable(false);

        // Close button
        closeButton = ButtonBuilder.closeButton(this);

        contentTable = new Table();

        mainTable = new Table();
        mainTable.pad(0);
        mainTable.add(contentTable).expand().fill().padTop(0).top().row();
        mainTable.add(closeButton).right().row();

        this.add(mainTable).expand().fill();

        // window size
        this.setSize(Constant.VIRTUAL_WIDTH - 10, Constant.VIRTUAL_HEIGHT - 10);

        // Center the window
        this.setPosition(
            (Constant.VIRTUAL_WIDTH - this.getWidth()) / 2,
            (Constant.VIRTUAL_HEIGHT - this.getHeight()) / 2
        );
    }


}
