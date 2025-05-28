package io.asirum.Screen.PlayMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.asirum.Screen.PlayScreen;
import io.asirum.Widget.dialogs.BaseDialog;

public class PauseDialog extends BaseDialog {
    public PauseDialog(Skin skin) {
        super("pause", skin);
        button("continue",true);
        button("home",false);
    }

    @Override
    protected void result(Object object) {
        if(object.equals(false)){
            Gdx.app.exit();
        } else if (object.equals(true)) {
            PlayScreen.paused =false;
        }
    }
}
