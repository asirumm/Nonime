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
    private TextureAtlas itemsAtlas;
    private TextureAtlas backgroundAtlas;
    private Skin widgetSkin;
    private Sound soundLevelControl;
    private Sound soundKey;
    private Sound soundDie;
    private Music soundMusic;

    private AssetManager assetManager;

    public GameAssets(AssetLoader assetLoader){
        this.assetManager = assetLoader.getAssetManager();
    }

    // panggil ini ketika asset manager telah selesai load
    public void build(){
        Log.debug(getClass().getName(),"Memulai inject variable asset");

        try{
            widgetAtlas         = assetManager.get(Constant.ASSET_WIDGET_ATLAS);
            itemsAtlas          = assetManager.get(Constant.ASSET_ITEMS_ATLAS);
            backgroundAtlas     = assetManager.get(Constant.BACKGROUND_ATLAS);
            playerAtlas         = assetManager.get(Constant.ASSET_PLAYER_ATLAS);
            widgetSkin      = assetManager.get(Constant.ASSET_WIDGET_SKIN);
            soundLevelControl = assetManager.get(Constant.ASSET_SOUND_CONTROL_ON_CLICK);
            soundMusic      = assetManager.get(Constant.ASSET_SOUND);
            soundKey        = assetManager.get(Constant.ASSET_KEY);
            soundDie        = assetManager.get(Constant.ASSET_DIE);

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

    public Sound getSoundKey() {
        return soundKey;
    }

    public Sound getSoundDie() {
        return soundDie;
    }

    public TextureAtlas getItemsAtlas() {
        return itemsAtlas;
    }

    public TextureAtlas getBackgroundAtlas() {
        return backgroundAtlas;
    }
}
