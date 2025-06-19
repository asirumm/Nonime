package io.asirum.Screen.LevelMenu;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import io.asirum.Constant;
import io.asirum.Util.ButtonAction;
import io.asirum.Screen.HomeScreen;
import io.asirum.Service.ApplicationContext;
import io.asirum.Util.ButtonBuilder;
import io.asirum.Widget.StyleVars;

public class WidgetFooter {
    private Button homeButton;
    private Button musicButton;
    private Skin skin;
    private ImageTextButton userEnergyTextButton;
    private HorizontalGroup group;

    public WidgetFooter(Skin skin,  short userEnergy){
        this.skin = skin;

        group = new HorizontalGroup();

        buildHomeBtn();

        buildMusicBtn();

        buildUserEnergy(String.valueOf(userEnergy));

        group.addActor(homeButton);
        group.addActor(musicButton);
        group.addActor(this.userEnergyTextButton);

    }

    private void buildHomeBtn(){
        homeButton = ButtonBuilder.build(skin, StyleVars.HOME_BUTTON, ButtonAction.switchScreen(HomeScreen::new,null));
    }


    private void buildMusicBtn(){
        musicButton = ButtonBuilder.build(skin, StyleVars.MUSIC_BTN, new Runnable() {
            @Override
            public void run() {
                Music soundMusic =  ApplicationContext.getInstance().getGameAssets().getSoundMusic();

                if (Constant.MUSIC_STATUS) {
                    musicButton.setDisabled(true);
                    soundMusic.pause();
                    Constant.MUSIC_STATUS = false;
                } else {
                    musicButton.setDisabled(false);
                    soundMusic.play();
                    Constant.MUSIC_STATUS = true;
                }
            }
        });
    }

    private void buildUserEnergy(String val){
        userEnergyTextButton = new ImageTextButton(val,skin);
        userEnergyTextButton.getLabel().setAlignment(Align.left);
    }

    public ImageTextButton getUserEnergyTextButton() {
        return userEnergyTextButton;
    }

    public Button getHomeButton() {
        return homeButton;
    }

    public Button getMusicButton() {
        return musicButton;
    }

    public HorizontalGroup getWidgetOnGroup() {
        return group;
    }
}
