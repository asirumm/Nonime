package io.asirum.Screen.PlayMenu;


import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.asirum.Screen.LevelScreen;
import io.asirum.Screen.PlayScreen;
import io.asirum.Service.ApplicationContext;
import io.asirum.Widget.dialogs.BaseDialog;

public class PauseDialog extends BaseDialog {
    public PauseDialog(Skin skin) {
        super("", skin,"pause");

        Button yes = new Button(skin,"yes");
        Button no = new Button(skin,"no");

        button(no,true);
        button(yes,false);
    }

    @Override
    protected void result(Object object) {
        if(object.equals(false)){
            PlayScreen.paused = false;
            ApplicationContext.getInstance().pushScreen(new LevelScreen(),null);
        } else if (object.equals(true)) {
            PlayScreen.paused =false;
        }
    }
}
