package io.asirum.Service;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

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
