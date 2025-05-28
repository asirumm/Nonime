package io.asirum.Screen.PlayMenu;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.asirum.Screen.PlayScreen;
import io.asirum.Util.ButtonHelper;
import io.asirum.Widget.StyleVars;

public class HudWidget {
    private Button pauseButton;
    private TextButton playerLive;
    private Table leftTable;
    private Table rightTable;
    private Skin skin;
    private Stage stage;
    private PauseDialog dialog;

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
    }

    public void buildPlayerLive(String life){
        playerLive = new TextButton(life,skin);
        leftTable.add(playerLive).left();
    }

    public void updatePlayerLive(String life){
        playerLive.setText(life);
    }


    public void buildPauseButton(){
        pauseButton = ButtonHelper.build(skin, StyleVars.PAUSE_BUTTON, new Runnable() {
            @Override
            public void run() {
                PlayScreen.paused= true;
                dialog.show(stage);
            }
        });

        rightTable.add(pauseButton).expandX().right();
    }

}
