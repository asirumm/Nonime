package io.asirum.Screen.PlayMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.asirum.Constant;
import io.asirum.Entity.Player.Player;
import io.asirum.EventListener.MobileInput;
import io.asirum.Service.ApplicationContext;
import io.asirum.Service.Log;
import io.asirum.Util.StageHelper;

public class WidgetPlayManager {
    private Table rootTable;
    private Table bottomTable;// controller
    private Table topTable;// hud
    private Table middleTable; // untuk konten kosong ditengah

    private HudWidget hudWidget;

    private MobileController mobileController;

    private Skin skin;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;

    private Player player;

    public WidgetPlayManager(Player player) {
        this.player = player;
        initHudCamera();

        this.stage = StageHelper.createInstance(viewport);

        this.skin = ApplicationContext.getInstance().getGameAssets().getWidgetSkin();

        tableConfiguration();

        hudWidget = new HudWidget(topTable,skin,stage);

        hudWidget.build(player.getPlayerLive());


        stage.addActor(rootTable);
    }

    private void tableConfiguration() {
        rootTable = new Table();
        topTable = new Table();
        bottomTable = new Table();
        middleTable = new Table();

        rootTable.setFillParent(true);
        rootTable.top();
        rootTable.add(topTable).top().expandX().fillX().row();
        rootTable.add(middleTable).expand().fill().row();
        rootTable.add(bottomTable).expandX().fillX().bottom();

        StageHelper.debugStage(false,rootTable,middleTable,bottomTable);
    }

    private void initHudCamera(){
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constant.VIRTUAL_WIDTH,Constant.VIRTUAL_HEIGHT);
        viewport = new FitViewport(Constant.VIRTUAL_WIDTH,Constant.VIRTUAL_HEIGHT,camera);
        Log.debug(getClass().getCanonicalName(),"HUD camera berhasil di inisialisasi");
    }

    public void render(){
        stage.act(Gdx.graphics.getDeltaTime());
        hudWidget.updatePlayerLive(String.valueOf(player.getPlayerLive()));
        stage.draw();

        // ketika player sudah mendapatkan key maka tampilkan icon key
        if(player.isBringKey()){
            hudWidget.keyIconSetVisible(true);
        }
    }

    public void dispose(){
        stage.dispose();
        Log.debug(getClass().getName(),"[dispose]");
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    // desktop menggunakan keyboard
    public void buildDesktopWidget(){

    }

    public void buildMobileWidget(MobileInput mobileInput){
        mobileController = new MobileController(skin,mobileInput);

        // membuat button control
        mobileController.buildMobileControllerButton();

        // menambahkan ke buttom tabel
        bottomTable.add(mobileController.getMobileControllerTable()).fillX().expandX();
    }

    public void playDialog(){
        InteractiveDialog dialog = new InteractiveDialog(skin);
        stage.addActor(dialog);
    }

}
