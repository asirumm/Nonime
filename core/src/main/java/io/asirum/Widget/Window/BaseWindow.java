package io.asirum.Widget.Window;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import io.asirum.Constant;
import io.asirum.Util.ButtonBuilder;

public class BaseWindow extends Window {

    protected Table mainTable;
    protected Table contentTable;
    protected Button closeButton;

    public BaseWindow(String title, Skin skin) {
        super(title, skin);

        getTitleLabel().setAlignment(Align.center);

        // Window properties
        this.setModal(true);
        this.setMovable(false);
        this.setResizable(false);

        // Close button
        closeButton = ButtonBuilder.closeButton(this);
        this.getTitleTable().add(closeButton).right();


        contentTable = new Table();

        this.add(contentTable).expand().fill();

        // window size
        this.setSize(Constant.VIRTUAL_WIDTH - 10, Constant.VIRTUAL_HEIGHT - 10);

        // Center the window
        this.setPosition(
            (Constant.VIRTUAL_WIDTH - this.getWidth()) / 2,
            (Constant.VIRTUAL_HEIGHT - this.getHeight()) / 2
        );
    }


}
