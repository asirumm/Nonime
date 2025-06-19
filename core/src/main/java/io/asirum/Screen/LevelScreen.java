package io.asirum.Screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import de.eskalon.commons.screen.ManagedScreen;
import io.asirum.SchemaObject.Payload;
import io.asirum.SchemaObject.Region;
import io.asirum.SchemaObject.UserData;
import io.asirum.Screen.LevelMenu.*;
import io.asirum.Service.*;
import io.asirum.Util.AudioHelper;
import io.asirum.Util.CameraHelper;
import io.asirum.Util.StageHelper;

public class LevelScreen extends ManagedScreen {
    private Stage stage;
    private ApplicationContext context;

    private Payload payload;
    private UserData userData;

    private Array<RegionContent> regionContents;

    public LevelScreen(){
        Log.debug(getClass().getName(), "[berhasil switch screen]");

        AudioHelper.playMusic();// play music

        // set context
        context = ApplicationContext.getInstance();
        Skin widgetSkin = context.getGameAssets().getWidgetSkin();

        this.userData = context.getUserData();
        this.payload  = context.getPayloadGame();

        // agar ketika dari playgame tidak menggunakan camera box2d
        CameraHelper.setCameraAndViewportNormal(context);
        this.stage   = StageHelper.createInstance();

        // membuat atau menagambil data regionContents dari context
        setupRegionContents(widgetSkin);

        LevelWindow levelWindow = new LevelWindow(widgetSkin,userData.getEnergy(),regionContents);

        stage.addActor(levelWindow);
        stage.addActor(levelWindow.getCostTooltip());
        stage.addActor(levelWindow.getUserEnergyTooltip());

//        StageHelper.debugStage(true,levelScrollPane.getContainer(),levelContentData.getContent());
    }

    /**
     * membuat data region contents
     * apabila dari context sudah ada tidak perlu buat ulang
     */
    private void setupRegionContents(Skin skin) {

        Log.debug(getClass().getCanonicalName(),"membuat instance region contents");

        if (context.getRegionContents()==null) {

            regionContents = new Array<>();

            for (Region region : payload.getRegions()) {

                RegionContent content = new RegionContent(region,userData);
                content.build(skin);
                regionContents.add(content);
            }

//            // inject data
            context.setRegionContents(regionContents);

        }else {
            regionContents = context.getRegionContents();
        }
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }


    @Override
    public Color getClearColor() {
        return Color.valueOf("#9dc2f6");
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
