package io.asirum.Widget.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.asirum.Constant;

public class BaseDialog extends Dialog {
    public BaseDialog(String title, Skin skin) {
        super(title, skin);
    }

    public BaseDialog(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
    }

    public BaseDialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);
    }

    {
        this.setDebug(true);
        this.setPosition((float) Constant.VIRTUAL_WIDTH/2,(float)Constant.VIRTUAL_HEIGHT/2);
    }
}
