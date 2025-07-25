package io.asirum.Screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.impl.*;
import io.asirum.Entity.Animation.Direction;
import io.asirum.Service.ApplicationContext;
import io.asirum.Service.Log;
import io.asirum.Util.*;
import io.asirum.Widget.StyleVars;
import io.asirum.Screen.HomeMenu.AboutWindow;

import static io.asirum.Constant.VIRTUAL_HEIGHT;
import static io.asirum.Constant.VIRTUAL_WIDTH;


public class HomeScreen extends ManagedScreen {
    private Stage stage;
    private Skin skin;
    private ApplicationContext context;

    private Button play;
    private Button about;

    private AboutWindow window;

    private SpriteBatch batch;

    public HomeScreen() {
        Log.debug(getClass().getName(),"[berhasil switch screen]");

        context = ApplicationContext.getInstance();
        skin    = context.getGameAssets().getWidgetSkin();
        window = new AboutWindow(skin);
        batch = context.getBatch();

        stage = StageHelper.createInstance();

        // wtf transition bug di android
        // jadi aneh ketika di android
        play = ButtonBuilder
            .build(skin, ButtonAction.switchScreen(LevelScreen::new, null));

        about = ButtonBuilder
            .build(skin, StyleVars.ABOUT_BTN,()->window.setVisible(true));

        buttonPosition();

        StageHelper.addActors(stage,play,about,window);
    }

    private void buttonPosition() {
        float posXplay = VIRTUAL_WIDTH / 2f;
        float posYplay = VIRTUAL_HEIGHT / 2f;

        play.setPosition(posXplay,posYplay , Align.center);
        about.setPosition(VIRTUAL_WIDTH, 0, Align.bottomRight);

    }

    private void drawBackground(){

        SpriteBatchHelper.setProjectionMatrixCameraCombined(batch);

        context.getBatch().draw(
            context
                .getGameAssets()
                .getBackgroundAtlas()
                .findRegion("background"),
            0,
            0,
            VIRTUAL_WIDTH,
            VIRTUAL_HEIGHT
        );

        SpriteBatchHelper.batchEnd(batch);

    }

    @Override
    public void render(float delta) {
        drawBackground();
        stage.act(delta);
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
