package io.asirum.Screen.PlayMenu;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.asirum.Screen.PlayScreen;
import io.asirum.Util.ButtonBuilder;
import io.asirum.Widget.StyleVars;

public class HudWidget {
    private Button pauseButton;
    private ImageTextButton playerLive;
    private Table leftTable;
    private Table rightTable;
    private Skin skin;
    private Stage stage;
    private PauseDialog dialog;
    private ImageButton keyIcon;

    public HudWidget(Table hudTable, Skin skin, Stage stage) {
        this.skin = skin;
        this.stage = stage;

        leftTable  = new Table();
        rightTable = new Table();

        hudTable.add(leftTable).expandX().left();
        hudTable.add(rightTable).expandX().right();

        dialog = new PauseDialog(skin);
    }

    public void build(int life){
      buildPauseButton();
      buildPlayerLive(String.valueOf(life));
      buildKeyIcon();
    }

    public void buildPlayerLive(String life){
        playerLive = new ImageTextButton(life,skin,StyleVars.LIFE_ICON);
        leftTable.add(playerLive).left();
    }

    public void buildKeyIcon(){
        keyIcon = new ImageButton(skin);
        leftTable.add(keyIcon).left().padLeft(5);
        keyIconSetVisible(false);
    }

    public void keyIconSetVisible(boolean visible){
        keyIcon.setVisible(visible);
    }

    public void updatePlayerLive(String life){
        playerLive.setText(life);
    }


    public void buildPauseButton(){
        pauseButton = ButtonBuilder.build(skin, StyleVars.PAUSE_BUTTON, new Runnable() {
            @Override
            public void run() {
                PlayScreen.paused= true;
                dialog.show(stage);
            }
        });

        rightTable.add(pauseButton).expandX().right();
    }

}
