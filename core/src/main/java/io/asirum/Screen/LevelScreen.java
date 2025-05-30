package io.asirum.Screen;

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
    private UserEnergyManager userEnergyManager;
    private PreferencesUserDataManager userDataManager;

    private LevelScrollPane scrollPane;
    private Array<RegionContent> regionContents;

    public LevelScreen(){
        Log.debug(getClass().getName(), "[berhasil switch screen]");

        AudioHelper.playMusic();// play music

        userDataManager = new PreferencesUserDataManager();

        // set context
        context = ApplicationContext.getInstance();
        Skin widgetSkin = context.getGameAssets().getWidgetSkin();

        // agar ketika dari playgame tidak menggunakan camera box2d
        CameraHelper.setCameraAndViewportNormal(context);
        this.stage   = StageHelper.createInstance();


        // load data payload
        setPayload();
        // load data user
        setUserData();

        // setting user energy managemet
        setupEnergyUserManagement();

        //
        setupRegionContents(widgetSkin);

        // konsep baca di dokumentasi level menu
        scrollPane = new LevelScrollPane(widgetSkin, regionContents);
        scrollPane.build();

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

    /**
     * proses
     * - membuat instance userEnergyManagement
     * - proses reward energy ke user
     * - inject instance ke app context
     * - save data
     */
    private void setupEnergyUserManagement() {
        Log.debug(getClass().getName(),">>> proses user energy manager");

        userEnergyManager = new UserEnergyManager(userData);

        // proses reward dari beberapa jam off
        userEnergyManager.userEnergyIntervalProcess();

        context.setUserEnergyManager(userEnergyManager);

        userDataManager.saveData(userData);
    }


    /**
     * inisialisasi user data apabila di context belum ada kita load
     */
    private void setUserData() {
        Log.debug(getClass().getName(),">>> set user data");

        if(context.getUserData()==null){
            Log.debug(getClass().getName(),">>> load user data");

            PreferencesUserDataManager manager = new PreferencesUserDataManager();

            // load data
            userData = manager.loadData();

            // inject data ke context
            context.setUserData(userData);

        }else {
            userData =context.getUserData();
        }

    }

    private void setPayload() {
        Log.debug(getClass().getName(),">>> set payload");

        if(context.getPayloadGame()==null){
            Log.debug(getClass().getName(),">>> load payload");

            PayloadJsonManager manager = new PayloadJsonManager();
            manager.load();

            // load data
            payload = manager.getPayload();

            // set ke context
            context.setPayload(payload);

        }else {
            payload =context.getPayloadGame();
        }
    }

    @Override
    public void render(float delta) {
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
