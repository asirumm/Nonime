package io.asirum.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import de.eskalon.commons.screen.ManagedScreen;
import io.asirum.SchemaObject.Payload;
import io.asirum.SchemaObject.Region;
import io.asirum.SchemaObject.UserData;
import io.asirum.Screen.LevelMenu.LevelScrollPane;
import io.asirum.Screen.LevelMenu.RegionContent;
import io.asirum.Screen.LevelMenu.WidgetController;
import io.asirum.Service.*;
import io.asirum.Util.AudioHelper;
import io.asirum.Util.CameraHelper;
import io.asirum.Util.StageHelper;

public class LevelScreen extends ManagedScreen {
    private Stage stage;
    private ApplicationContext context;

    private Payload payload;
    private UserData userData;

    private LevelScrollPane scrollPane;
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

        // konsep baca di dokumentasi level menu
        scrollPane = new LevelScrollPane(widgetSkin, regionContents);
        scrollPane.build();

        // membuat kontroller home,music etc
        WidgetController buttonScreen =
            new WidgetController(
                widgetSkin, scrollPane.getScrollPane(),userData.getEnergy());

        // menambahkan ke stage
        StageHelper.addActors(stage,
            scrollPane.getScrollPane(),
            buttonScreen.getHome(),
            buttonScreen.getMusic(),
            buttonScreen.getUserEnergy(),
            buttonScreen.getLeftControl(),
            buttonScreen.getRightControl()
        );

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

                RegionContent content = new RegionContent(skin);
                content.build(region, userData);

                regionContents.add(content);
            }

            // inject data
            context.setRegionContents(regionContents);

        }else {
            regionContents = context.getRegionContents();
        }
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
        System.out.println("FPS : "+Gdx.graphics.getFramesPerSecond());

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
