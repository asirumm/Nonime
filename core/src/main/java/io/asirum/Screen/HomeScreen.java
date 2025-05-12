package io.asirum.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import de.eskalon.commons.screen.ManagedScreen;
import io.asirum.EventListener.BaseHandler;
import io.asirum.Service.ApplicationContext;
import io.asirum.Service.Log;
import io.asirum.Util.ButtonHelper;
import io.asirum.Util.CameraHelper;
import io.asirum.Util.SpriteBatchHelper;
import io.asirum.Util.StageHelper;
import io.asirum.Widget.StyleVars;
import io.asirum.Widget.Window.Abouts;

import static io.asirum.Constant.VIRTUAL_HEIGHT;
import static io.asirum.Constant.VIRTUAL_WIDTH;


public class HomeScreen extends ManagedScreen {
    private Stage stage;
    private Skin skin;
    private ApplicationContext context;

    private Button play;
    private Button about;

    private BaseHandler baseHandler;
    private Abouts window;



    public HomeScreen() {
        Log.debug(getClass().getName(),"[berhasil switch screen]");

        context = ApplicationContext.getInstance();
        skin    = context.getGameAssets().getWidgetSkin();
        baseHandler = new BaseHandler();
        window = new Abouts(skin);

        stage = StageHelper.createInstance();


        play = ButtonHelper
            .build(skin,baseHandler.onPush(new MenuScreen(),null));

        about = ButtonHelper
            .build(skin, StyleVars.ABOUT_BTN,()->window.setVisible(true));

        buttonPosition();

        StageHelper.addActors(stage,play,about,window);
    }

    private void buttonPosition() {
        float posXplay = VIRTUAL_WIDTH / 2f;
        float posYplay = VIRTUAL_HEIGHT / 2f;

        play.setPosition(posXplay,posYplay , Align.center);
        about.setPosition(posXplay,posYplay-play.getHeight(),Align.center);

    }

//    private void drawBackground(){
//        SpriteBatchHelper.projectionCombineBegin();
//
//        context.getBatch().draw(
//            context
//                .getGameAssets()
//                .getBackgroundAtlas()
//                .findRegion(BACKGROUND),
//            0,
//            0,
//            VIRTUAL_WIDTH,
//            VIRTUAL_HEIGHT
//        );
//
//        SpriteBatchHelper.batchEnd();
//
//    }

    @Override
    public void render(float delta) {
//        drawBackground();
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
