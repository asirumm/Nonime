package io.asirum.Service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;
import io.asirum.Constant;

/**
 * kelas ini untuk menahan data assets
 * seperti atlas player, skin widget dsb
 */
public class GameAssets {
    private TextureAtlas widgetAtlas;
    private TextureAtlas playerAtlas;
    private Skin widgetSkin;
    private Sound soundLevelControl;
    private Music soundMusic;

    private AssetManager assetManager;

    public GameAssets(AssetLoader assetLoader){
        this.assetManager = assetLoader.getAssetManager();
    }

    // panggil ini ketika asset manager telah selesai load
    public void build(){
        Log.info(getClass().getName(),"memulai build variable asset");

        try{
            widgetAtlas     = assetManager.get(Constant.ASSET_WIDGET_ATLAS);
            widgetSkin      = assetManager.get(Constant.ASSET_WIDGET_SKIN);
            soundLevelControl = assetManager.get(Constant.ASSET_SOUND_CONTROL_ON_CLICK);
            soundMusic      = assetManager.get(Constant.ASSET_SOUND);

        }catch (GdxRuntimeException exception){
            Log.error(getClass().getName(), exception);

            Gdx.app.exit();
        }
    }

    public TextureAtlas getWidgetAtlas() {
        return widgetAtlas;
    }

    public void setWidgetAtlas(TextureAtlas widgetAtlas) {
        this.widgetAtlas = widgetAtlas;
    }

    public TextureAtlas getPlayerAtlas() {
        return playerAtlas;
    }

    public void setPlayerAtlas(TextureAtlas playerAtlas) {
        this.playerAtlas = playerAtlas;
    }

    public Skin getWidgetSkin() {
        return widgetSkin;
    }

    public void setWidgetSkin(Skin widgetSkin) {
        this.widgetSkin = widgetSkin;
    }

    public Sound getSoundLevelControl() {
        return soundLevelControl;
    }

    public void setSoundLevelControl(Sound soundLevelControl) {
        this.soundLevelControl = soundLevelControl;
    }

    public Music getSoundMusic() {
        return soundMusic;
    }

    public void setSoundMusic(Music soundMusic) {
        this.soundMusic = soundMusic;
    }
}
