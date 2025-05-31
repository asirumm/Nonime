package io.asirum.Widget.Window;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import io.asirum.Constant;
import io.asirum.Util.ButtonBuilder;

public class BaseWindow extends Window {

    protected Table mainTable;
    protected Button closeButton;

    public BaseWindow(String title, Skin skin) {
        super(title, skin);

        // agar rapih titlenya
        getTitleTable().padTop(getTitleLabel().getHeight()+getTitleTable().getPrefHeight());


        // Window properties
        this.setModal(true);
        this.setMovable(false);
        this.setResizable(false);

        // Set up the main container table
        mainTable = new Table();
        mainTable.padTop(15);

        // Close button
        closeButton = ButtonBuilder.closeButton(this);

        // window size
        this.setSize(Constant.VIRTUAL_WIDTH - 10, Constant.VIRTUAL_HEIGHT - 10);

        // Center the window
        this.setPosition(
            (Constant.VIRTUAL_WIDTH - this.getWidth()) / 2,
            (Constant.VIRTUAL_HEIGHT - this.getHeight()) / 2
        );
    }

    protected void addChildToTable(Actor actor){
        mainTable.add(actor).expand().fill().row();


        // Add close button to main table with specific alignment
        mainTable.add(closeButton).right().row();

        // menambahkan table ke window
        this.add(mainTable).expand().fill();
    }
}
