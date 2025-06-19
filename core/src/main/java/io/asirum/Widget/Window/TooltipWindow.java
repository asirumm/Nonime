package io.asirum.Widget.Window;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class TooltipWindow extends Window {
    public TooltipWindow(String text, Skin skin) {
        super("", skin,"tooltip");
        Label label = new Label(text, skin,"dialog");
        this.add(label);
        this.pack();
        this.setMovable(false);
        setVisible(false);
    }

}
