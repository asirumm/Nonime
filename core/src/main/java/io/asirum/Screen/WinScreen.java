package io.asirum.Screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.eskalon.commons.screen.ManagedScreen;
import io.asirum.Constant;
import io.asirum.Service.ApplicationContext;
import io.asirum.Service.Log;
import io.asirum.Util.AudioHelper;
import io.asirum.Util.ButtonAction;
import io.asirum.Util.CameraHelper;
import io.asirum.Util.StageHelper;
import io.asirum.Widget.Window.BaseWindow;
import io.asirum.Widget.Window.SimpleWindow;

public class WinScreen extends ManagedScreen {
    private Stage stage;
    private ApplicationContext context;


    public WinScreen(){
        Log.debug(getClass().getName(), "[berhasil switch screen]");

        if(Constant.MUSIC_STATUS){
            AudioHelper.playMusic();// play music
        }

        context = ApplicationContext.getInstance();
        Skin skin = context.getGameAssets().getWidgetSkin();

        CameraHelper.setCameraAndViewportNormal(context);
        stage = StageHelper.createInstance();

        Label label = new Label("back to home",skin);
        label.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                context.pushScreen(new LevelScreen(),null);
                return true;
            }
        });


        SimpleWindow window = new SimpleWindow(skin,"success");

        window.getContentTable().add(label).center();

        stage.addActor(window);

    }

    @Override
    public void render(float v) {
        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        CameraHelper.resizer(width,height);
    }

    @Override
    public void dispose() {
        stage.dispose();
        Log.debug(getClass().getName(),"[dispose]");
    }
}
