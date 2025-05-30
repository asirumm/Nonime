package io.asirum.Screen.LevelMenu;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import io.asirum.Constant;
import io.asirum.Util.ButtonActionHelper;
import io.asirum.Screen.HomeScreen;
import io.asirum.Service.ApplicationContext;
import io.asirum.Util.ButtonHelper;
import io.asirum.Widget.StyleVars;

public class WidgetController {
    private Button home;
    private Button music;
    private Skin skin;
    private ImageTextButton userEnergy;
    private ButtonActionHelper buttonActionHelper;
    private Button leftControl;
    private Button rightControl;

    private ScrollPane scrollPane;


    public WidgetController(Skin skin, ScrollPane scrollPane,short userEnergy){
        this.scrollPane = scrollPane;
        buttonActionHelper = new ButtonActionHelper();
        this.skin = skin;

        // agar posisi button presisi didalam scrollpane
        // Posisi vertikal elemen dalam ScrollPane
        float verticalOffset = scrollPane.getY() + 10;

        buildHomeBtn(scrollPane.getWidth(),verticalOffset);

        buildMusicBtn(verticalOffset);

        buildUserEnergy(String.valueOf(userEnergy),verticalOffset);

        // Posisi horizontal elemen dalam ScrollPane
        float horizontalOffset = scrollPane.getX() + 10;

        buildLeftControl(horizontalOffset,scrollPane.getY());

        buildRightControl(scrollPane.getWidth(),scrollPane.getY());
    }

    private void buildHomeBtn(float scrollPaneWidth,float scrollPaneY){
        home = ButtonHelper.build(skin, StyleVars.HOME_BUTTON, buttonActionHelper.onPush(HomeScreen::new,null));

        home.setPosition(
            ( scrollPaneWidth / 2)-30,scrollPaneY
        );
    }

    private void buildLeftControl(float scrollpaneX,float scrollPaneY){
        leftControl = ButtonHelper.build(skin, StyleVars.LEFT_CONTROL, new Runnable() {
            @Override
            public void run() {
                float currentScroll = scrollPane.getScrollX();
                scrollPane.scrollTo(currentScroll - scrollPane.getWidth(), 0, scrollPane.getWidth(), scrollPane.getHeight());
            }
        });
        leftControl.setPosition(scrollpaneX,scrollPaneY+70);
    }

    private void buildRightControl(float scrollpaneWidth,float scrollPaneY){
        rightControl =  ButtonHelper.build(skin, StyleVars.RIGHT_CONTROL, new Runnable() {
            @Override
            public void run() {
                float currentScroll = scrollPane.getScrollX();
                scrollPane.scrollTo(currentScroll + scrollPane.getWidth(), 0, scrollPane.getWidth(), scrollPane.getHeight());
            }
        });
        rightControl.setPosition(scrollpaneWidth,scrollPaneY+70);
    }

    private void buildMusicBtn(float scrollPaneY){
        music = ButtonHelper.build(skin, StyleVars.MUSIC_BTN, new Runnable() {
            @Override
            public void run() {
                Music soundMusic =  ApplicationContext.getInstance().getGameAssets().getSoundMusic();

                if (Constant.MUSIC_STATUS) {
                    music.setDisabled(true);
                    soundMusic.pause();
                    Constant.MUSIC_STATUS = false;
                } else {
                    music.setDisabled(false);
                    soundMusic.play();
                    Constant.MUSIC_STATUS = true;
                }
            }
        });

        music.setPosition(
            home.getX() +home.getWidth(),scrollPaneY
        );
    }

    private void buildUserEnergy(String val,float scrollPaneY){
        userEnergy = new ImageTextButton(val,skin);
        userEnergy.getLabel().setAlignment(Align.left);
        userEnergy.setPosition(music.getX() +music.getWidth(),scrollPaneY);
    }

    public ImageTextButton getUserEnergy() {
        return userEnergy;
    }

    public Button getHome() {
        return home;
    }

    public Button getMusic() {
        return music;
    }

    public Button getLeftControl() {
        return leftControl;
    }

    public Button getRightControl() {
        return rightControl;
    }
}
