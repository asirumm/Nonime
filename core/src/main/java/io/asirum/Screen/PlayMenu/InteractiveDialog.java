package io.asirum.Screen.PlayMenu;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import io.asirum.Constant;
import io.asirum.Widget.Window.BaseWindow;
import io.asirum.Widget.dialogs.BaseDialog;

public class InteractiveDialog extends BaseWindow {
    String text = "We must find the key and go to the portal to finish it!";
    public InteractiveDialog(Skin skin) {
        super(skin,"dialog");

        setSize(Constant.VIRTUAL_WIDTH,100);

        withCloseButton();
        // Content label
        Label information = new Label(text, skin,"dialog");
        information.setWrap(true);
        information.setAlignment(Align.topLeft);


        super.content.add(information).expand().fill().top();

        this.setVisible(true);
    }
}
